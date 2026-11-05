#!/data/data/com.termux/files/usr/bin/bash
set -e

cd "$(dirname "$0")/.."

GRADLE_FILE="app/build.gradle"
OUTPUT_DIR="$HOME/storage/shared/AVESON_PROJECTS"
RELEASE_DIR="$OUTPUT_DIR/APK_RELEASES"

mkdir -p "$RELEASE_DIR"

OLD_NAME=$(sed -n "s/.*versionName '\([^']*\)'.*/\1/p" "$GRADLE_FILE" | head -n 1)
OLD_CODE=$(sed -n "s/.*versionCode \([0-9]*\).*/\1/p" "$GRADLE_FILE" | head -n 1)

if [ -z "$OLD_NAME" ] || [ -z "$OLD_CODE" ]; then
    echo "Version sozlamalarini o'qib bo'lmadi."
    exit 1
fi

IFS='.' read -r MAJOR MINOR PATCH <<< "$OLD_NAME"
PATCH=$((PATCH + 1))

NEW_NAME="$MAJOR.$MINOR.$PATCH"
NEW_CODE=$((OLD_CODE + 1))

CURRENT_APK="$OUTPUT_DIR/AVESON_ARTIST.apk"
OLD_ARCHIVE="$RELEASE_DIR/AVESON_ARTIST_v${OLD_NAME}.apk"
VERSIONED_APK="$RELEASE_DIR/AVESON_ARTIST_v${NEW_NAME}.apk"

if [ -e "$VERSIONED_APK" ]; then
    echo "Bu versiyadagi APK allaqachon mavjud: $VERSIONED_APK"
    echo "Eski APK ustidan yozilmadi."
    exit 1
fi

# Root papkadagi avvalgi versiyali APKlarni o'chirmasdan arxivga nusxalash.
for file in "$OUTPUT_DIR"/AVESON_ARTIST_v*.apk; do
    [ -f "$file" ] || continue
    name=$(basename "$file")
    destination="$RELEASE_DIR/$name"

    if [ ! -e "$destination" ]; then
        cp -p "$file" "$destination"
    fi
done

# Joriy APKni eski versiya sifatida saqlash.
if [ -f "$CURRENT_APK" ] && [ ! -f "$OLD_ARCHIVE" ]; then
    cp -p "$CURRENT_APK" "$OLD_ARCHIVE"
fi

# Yangi versiya raqamlarini vaqtincha o'rnatish.
sed -i "s/versionCode $OLD_CODE/versionCode $NEW_CODE/" "$GRADLE_FILE"
sed -i "s/versionName '$OLD_NAME'/versionName '$NEW_NAME'/" "$GRADLE_FILE"

echo "AVESON ARTIST v$NEW_NAME yig'ilmoqda..."

if ! "$HOME/gradle-8.7/bin/gradle" assembleDebug; then
    sed -i "s/versionCode $NEW_CODE/versionCode $OLD_CODE/" "$GRADLE_FILE"
    sed -i "s/versionName '$NEW_NAME'/versionName '$OLD_NAME'/" "$GRADLE_FILE"

    echo "Build xato bilan tugadi."
    echo "Versiya sozlamalari avvalgi holatiga qaytarildi."
    exit 1
fi

BUILT_APK="app/build/outputs/apk/debug/app-debug.apk"

if [ ! -f "$BUILT_APK" ]; then
    echo "Yig'ilgan APK topilmadi."
    exit 1
fi

cp -p "$BUILT_APK" "$VERSIONED_APK"
cp "$BUILT_APK" "$CURRENT_APK"

echo
echo "BUILD SUCCESSFUL"
echo "Versiya: $NEW_NAME"
echo "versionCode: $NEW_CODE"
echo "Versiyali APK: $VERSIONED_APK"
echo "Oxirgi APK: $CURRENT_APK"
