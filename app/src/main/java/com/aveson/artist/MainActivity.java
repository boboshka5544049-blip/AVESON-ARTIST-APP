package com.aveson.artist;

import android.app.Activity;
import com.aveson.artist.ui.ArtistUI;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArtistUI artistUI;

    private LinearLayout content;
    private Page currentPage;
    private final ArrayList<Page> history = new ArrayList<>();

    private final int BG = Color.rgb(8, 11, 20);
    private final int SURFACE = Color.rgb(17, 26, 43);
    private final int CARD = Color.rgb(23, 34, 56);
    private final int BLUE = Color.rgb(37, 143, 255);
    private final int PURPLE = Color.rgb(134, 92, 255);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(165, 178, 200);
    private final int BORDER = Color.rgb(41, 59, 87);

    private static class Page {
        String type;
        String title;
        String[] items;

        Page(String type, String title, String[] items) {
            this.type = type;
            this.title = title;
            this.items = items;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistUI = new ArtistUI(this);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.getStartedButton);
        start.setOnClickListener(v -> showHome());
    }

    private void showHome() {
        history.clear();
        render(new Page("HOME", "HOME", null));
    }

    private void navigate(Page page) {
        if (currentPage != null) {
            history.add(currentPage);
        }
        render(page);
    }

    private void selectTab(String type, String title) {
        history.clear();
        render(new Page(type, title, null));
    }

    private void goBack() {
        if (!history.isEmpty()) {
            Page previous = history.remove(history.size() - 1);
            render(previous);
        } else {
            super.onBackPressed();
        }
    }

    private void render(Page page) {
        currentPage = page;
        createScreen();

        switch (page.type) {
            case "HOME":
                showHomeContent();
                break;
            case "MENU":
                showMenuContent();
                break;
            case "CATEGORY":
                showCategoryContent(page);
                break;
            case "NOTIFICATIONS":
                showNotifications();
                break;
            case "DISCOVERY":
                showDiscovery();
                break;
            case "UPLOAD":
                showUpload();
                break;
            case "MY_MUSIC":
                showMyMusic();
                break;
            case "PROFILE":
                showProfile();
                break;
            case "ROOM":
                showRoom(page);
                break;
        }
    }

    private void createScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        root.setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(
                dp(14),
                insets.getSystemWindowInsetTop() + dp(8),
                dp(14),
                insets.getSystemWindowInsetBottom() + dp(5)
            );
            return insets;
        });

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        boolean isHome = currentPage.type.equals("HOME");

        if (!isHome) {
            Button back = makeButton("‹", SURFACE, WHITE);
            back.setTextSize(29);
            back.setOnClickListener(v -> goBack());

            top.addView(back, new LinearLayout.LayoutParams(dp(44), dp(46)));
        }

        Button menu = makeButton("☰", SURFACE, WHITE);
        menu.setTextSize(23);
        menu.setOnClickListener(v ->
            navigate(new Page("MENU", "MAIN MENU", null))
        );

        LinearLayout.LayoutParams menuParams =
            new LinearLayout.LayoutParams(dp(46), dp(46));
        menuParams.setMargins(dp(5), 0, dp(7), 0);
        top.addView(menu, menuParams);

        TextView title = makeText("AVESON ARTIST", 17, WHITE, true);
        title.setGravity(Gravity.CENTER);

        top.addView(title, new LinearLayout.LayoutParams(0, dp(46), 1));

        Button bell = makeButton("🔔", BLUE, WHITE);
        bell.setTextSize(22);
        bell.setOnClickListener(v ->
            navigate(new Page("NOTIFICATIONS", "NOTIFICATIONS", null))
        );

        top.addView(bell, new LinearLayout.LayoutParams(dp(46), dp(46)));
        root.addView(top);

        View divider = new View(this);
        divider.setBackgroundColor(BORDER);

        LinearLayout.LayoutParams dividerParams =
            new LinearLayout.LayoutParams(-1, dp(1));
        dividerParams.setMargins(0, dp(12), 0, dp(8));
        root.addView(divider, dividerParams);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(2), dp(8), dp(2), dp(16));

        scroll.addView(content);

        root.addView(
            scroll,
            new LinearLayout.LayoutParams(-1, 0, 1)
        );

        createBottomNavigation(root);

        setContentView(root);
        root.requestApplyInsets();
    }

    private void createBottomNavigation(LinearLayout root) {
        LinearLayout dock = new LinearLayout(this);
        dock.setOrientation(LinearLayout.HORIZONTAL);
        dock.setGravity(Gravity.CENTER);
        dock.setPadding(dp(6), dp(6), dp(6), dp(6));
        dock.setBackground(roundDrawable(SURFACE, 25, PURPLE));

        addTab(dock, "⌂", "HOME", "HOME");
        addTab(dock, "⌖", "DISCOVERY", "DISCOVERY");

        LinearLayout upload = new LinearLayout(this);
        upload.setOrientation(LinearLayout.VERTICAL);
        upload.setGravity(Gravity.CENTER);
        upload.setBackground(roundDrawable(BLUE, 18, BLUE));
        upload.setOnClickListener(v ->
            selectTab("UPLOAD", "UPLOAD MUSIC")
        );

        TextView plus = makeText("+", 25, WHITE, true);
        plus.setGravity(Gravity.CENTER);
        upload.addView(plus);

        TextView uploadLabel = makeText("UPLOAD", 8, WHITE, true);
        uploadLabel.setGravity(Gravity.CENTER);
        upload.addView(uploadLabel);

        LinearLayout.LayoutParams uploadParams =
            new LinearLayout.LayoutParams(dp(65), dp(68));
        uploadParams.setMargins(dp(3), 0, dp(3), 0);
        dock.addView(upload, uploadParams);

        addTab(dock, "♫", "MY_MUSIC", "MY MUSIC");
        addTab(dock, "◉", "PROFILE", "PROFILE");

        LinearLayout.LayoutParams dockParams =
            new LinearLayout.LayoutParams(-1, dp(80));
        dockParams.setMargins(0, dp(7), 0, dp(3));
        root.addView(dock, dockParams);
    }

    private void addTab(
        LinearLayout dock,
        String icon,
        String type,
        String title
    ) {
        boolean selected = currentPage.type.equals(type);

        LinearLayout tab = new LinearLayout(this);
        tab.setOrientation(LinearLayout.VERTICAL);
        tab.setGravity(Gravity.CENTER);
        tab.setPadding(dp(1), dp(4), dp(1), dp(4));

        tab.setBackground(
            selected
                ? roundDrawable(CARD, 16, BLUE)
                : roundDrawable(SURFACE, 16, SURFACE)
        );

        TextView iconView = makeText(
            icon, 22, selected ? BLUE : MUTED, true
        );
        iconView.setGravity(Gravity.CENTER);
        tab.addView(iconView);

        TextView label = makeText(
            title, 8, selected ? BLUE : MUTED, true
        );
        label.setGravity(Gravity.CENTER);
        tab.addView(label);

        tab.setOnClickListener(v -> selectTab(type, title));

        dock.addView(
            tab,
            new LinearLayout.LayoutParams(0, dp(68), 1)
        );
    }
    private void showHomeContent() {
        ArtistHome artistHome = new ArtistHome(
            this,
            content,
            artistUI,
            (type, title) -> selectTab(type, title)
        );

        artistHome.render();
    }

    private void showMenuContent() {
        addText("EXPLORE", 12, BLUE, true);
        addText("MAIN MENU", 27, WHITE, true);
        addText(
            "Your complete artist management space.",
            14, MUTED, false
        );

        String[] workspace = {
            "ARTIST PROFILE",
            "MY RELEASES",
            "MY MUSIC",
            "ROYALTY & EARNINGS",
            "ANALYTICS",
            "COLLABORATION",
            "EVENTS & SCHEDULE"
        };

        String[] services = {
            "DOCUMENTS & CONTRACTS",
            "SUPPORT CENTER",
            "MY CAREER",
            "NOTIFICATIONS CENTER",
            "LANGUAGE SETTINGS"
        };

        addRoomCard(
            "01",
            "ARTIST WORKSPACE",
            "Your music, profile, releases and career.",
            workspace
        );

        addRoomCard(
            "02",
            "ARTIST SERVICES",
            "Support, documents and artist services.",
            services
        );
    }

    private void addRoomCard(
        String number,
        String title,
        String description,
        String[] items
    ) {
        LinearLayout card = makeCard();

        TextView numberView = makeText(number, 12, BLUE, true);
        card.addView(numberView);

        card.addView(makeText(title, 19, WHITE, true));

        TextView detail = makeText(description, 13, MUTED, false);
        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, dp(7), 0, dp(15));
        card.addView(detail, detailParams);

        Button open = makeButton("OPEN ROOM     →", BLUE, WHITE);
        open.setTextSize(13);
        open.setOnClickListener(v ->
            navigate(new Page("CATEGORY", title, items))
        );

        card.addView(open, new LinearLayout.LayoutParams(-1, dp(47)));
        addCardToContent(card);
    }

    private void showCategoryContent(Page page) {
        addText("ARTIST SPACE", 12, BLUE, true);
        addText(page.title, 24, WHITE, true);
        addText("Choose a section to continue.", 14, MUTED, false);

        if (page.items == null) return;

        for (int i = 0; i < page.items.length; i++) {
            String item = page.items[i];

            addQuickCard(
                "✦",
                item,
                "Open " + item.toLowerCase(),
                "ROOM",
                item
            );
        }
    }

    private void showDiscovery() {
        addText("EXPLORE THE COMMUNITY", 12, BLUE, true);
        addText("DISCOVERY", 27, WHITE, true);
        addText(
            "Find artists, discover music and explore new talent.",
            14, MUTED, false
        );

        addQuickCard("★", "POPULAR ARTISTS",
            "Artists gaining attention", "ROOM", "POPULAR ARTISTS");

        addQuickCard("♫", "MOST LISTENED",
            "Discover popular music", "ROOM", "MOST LISTENED");

        addQuickCard("↗", "RISING ARTISTS",
            "New and growing talent", "ROOM", "RISING ARTISTS");

        addQuickCard("✦", "FEATURED ARTISTS",
            "Artists selected by AVESON", "ROOM", "FEATURED ARTISTS");

        addQuickCard("◎", "MY RECOMMENDATIONS",
            "Artists recommended for you", "ROOM", "MY RECOMMENDATIONS");
    }

    private void showUpload() {
        addText("CREATE AND SUBMIT", 12, BLUE, true);
        addText("UPLOAD MUSIC", 27, WHITE, true);

        addFeatureCard(
            "↑",
            "YOUR MUSIC. YOUR JOURNEY.",
            "Submit your music to AVESON for review and distribution.",
            null,
            null
        );

        addSectionTitle("UPLOAD PROCESS");

        addQuickCard("01", "SELECT AUDIO",
            "Choose your music file", "ROOM", "SELECT AUDIO");

        addQuickCard("02", "ADD COVER ART",
            "Choose your release artwork", "ROOM", "ADD COVER ART");

        addQuickCard("03", "ADD DETAILS",
            "Enter your song and artist information", "ROOM", "ADD DETAILS");

        addQuickCard("04", "SUBMIT FOR REVIEW",
            "Send your release to AVESON ARTIST CONTROL",
            "ROOM", "SUBMIT FOR REVIEW");

        addText(
            "IMPORTANT: Uploaded music does not become public automatically. " +
            "AVESON ARTIST CONTROL must review and approve it first.",
            13, MUTED, false
        );
    }

    private void showMyMusic() {
        addText("YOUR COLLECTION", 12, BLUE, true);
        addText("MY MUSIC", 27, WHITE, true);
        addText(
            "Your music and releases will be organized here.",
            14, MUTED, false
        );

        addFeatureCard(
            "♫",
            "YOUR MUSIC LIBRARY",
            "Your uploaded songs, approved releases and music details.",
            null,
            null
        );

        addQuickCard("♫", "MY RELEASES",
            "Manage your submitted releases", "ROOM", "MY RELEASES");
    }

    private void showProfile() {
        addText("YOUR IDENTITY", 12, BLUE, true);
        addText("ARTIST PROFILE", 27, WHITE, true);

        addFeatureCard(
            "●",
            "PRIVATE ARTIST PROFILE",
            "Manage your artist information and choose what can be shown publicly.",
            null,
            null
        );

        addQuickCard("✎", "EDIT PROFILE",
            "Update your artist information", "ROOM", "EDIT PROFILE");

        addQuickCard("◎", "PUBLIC PROFILE",
            "Manage information visible to others", "ROOM", "PUBLIC PROFILE");
    }

    private void showNotifications() {
        addText("UPDATES AND ACTIVITY", 12, BLUE, true);
        addText("NOTIFICATIONS", 25, WHITE, true);

        addFeatureCard(
            "♢",
            "YOU'RE ALL CAUGHT UP",
            "Release approvals, review feedback, distribution updates, payments and AVESON announcements will appear here.",
            null,
            null
        );
    }

    private void showRoom(Page page) {
        addText("ARTIST WORKSPACE", 12, BLUE, true);
        addText(page.title, 25, WHITE, true);

        addFeatureCard(
            "✦",
            page.title,
            "This section will be developed step by step.",
            null,
            null
        );
    }

    private void addSectionTitle(String title) {
        TextView heading = makeText(title, 15, WHITE, true);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(dp(3), dp(18), 0, dp(8));
        content.addView(heading, params);
    }

    private void addQuickCard(
        String icon,
        String title,
        String description,
        String type,
        String destination
    ) {
        LinearLayout card = makeCard();
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);

        TextView iconView = makeText(icon, 23, BLUE, true);
        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(roundDrawable(SURFACE, 13, BORDER));

        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(dp(48), dp(48));
        iconParams.setMargins(0, 0, dp(13), 0);
        card.addView(iconView, iconParams);

        LinearLayout textArea = new LinearLayout(this);
        textArea.setOrientation(LinearLayout.VERTICAL);

        textArea.addView(makeText(title, 14, WHITE, true));
        TextView detail = makeText(description, 11, MUTED, false);

        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, dp(4), 0, 0);
        textArea.addView(detail, detailParams);

        card.addView(textArea, new LinearLayout.LayoutParams(0, -2, 1));

        TextView arrow = makeText("›", 27, BLUE, true);
        card.addView(arrow);

        card.setOnClickListener(v -> {
            if (type.equals("ROOM")) {
                navigate(new Page("ROOM", destination, null));
            } else {
                selectTab(type, destination);
            }
        });

        addCardToContent(card);
    }

    private void addFeatureCard(
        String icon,
        String title,
        String description,
        String type,
        String destination
    ) {
        LinearLayout card = makeCard();
        card.setBackground(roundDrawable(SURFACE, 20, BLUE));

        TextView symbol = makeText(icon, 30, BLUE, true);
        card.addView(symbol);

        TextView heading = makeText(title, 18, WHITE, true);
        LinearLayout.LayoutParams headingParams =
            new LinearLayout.LayoutParams(-1, -2);
        headingParams.setMargins(0, dp(8), 0, dp(6));
        card.addView(heading, headingParams);

        card.addView(makeText(description, 13, MUTED, false));

        if (type != null && destination != null) {
            card.setOnClickListener(v ->
                selectTab(type, destination)
            );
        }

        addCardToContent(card);
    }

    private LinearLayout makeCard() {
        return artistUI.makeCard(CARD, BORDER);
    }

    private void addCardToContent(LinearLayout card) {
        artistUI.addCardToContent(content, card);
    }

    private void addText(
        String value,
        int size,
        int color,
        boolean bold
    ) {
        artistUI.addText(content, value, size, color, bold);
    }

    private TextView makeText(
        String value,
        int size,
        int color,
        boolean bold
    ) {
        return artistUI.makeText(value, size, color, bold);
    }

    private Button makeButton(
        String value,
        int bg,
        int fg
    ) {
        return artistUI.makeButton(value, bg, fg);
    }

    private GradientDrawable roundDrawable(
        int color,
        int radius,
        int strokeColor
    ) {
        return artistUI.roundDrawable(color, radius, strokeColor);
    }

    private int dp(int value) {
        return artistUI.dp(value);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
