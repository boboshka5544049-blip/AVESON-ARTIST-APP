package com.aveson.artist;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aveson.artist.ui.ArtistUI;

public class ArtistUpload {
    public interface Navigation {
        void open(String type, String title);
    }

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI ui;
    private final Navigation navigation;
    private final int surface, cardColor, blue, white, muted, border;

    public ArtistUpload(
        Activity activity, LinearLayout content, ArtistUI ui,
        Navigation navigation, int surface, int cardColor,
        int blue, int white, int muted, int border
    ) {
        this.activity = activity;
        this.content = content;
        this.ui = ui;
        this.navigation = navigation;
        this.surface = surface;
        this.cardColor = cardColor;
        this.blue = blue;
        this.white = white;
        this.muted = muted;
        this.border = border;
    }

    public void render() {
        addText("CREATE AND SUBMIT", 12, blue, true);
        addText("UPLOAD MUSIC", 27, white, true);
        addFeatureCard(
            "↑", "YOUR MUSIC. YOUR JOURNEY.",
            "Submit your music to AVESON for review and distribution."
        );
        addSectionTitle("UPLOAD PROCESS");
        addQuickCard("01", "SELECT AUDIO",
            "Choose your music file", "SELECT AUDIO");
        addQuickCard("02", "ADD COVER ART",
            "Choose your release artwork", "ADD COVER ART");
        addQuickCard("03", "ADD DETAILS",
            "Enter your song and artist information", "ADD DETAILS");
        addQuickCard("04", "SUBMIT FOR REVIEW",
            "Send your release to AVESON ARTIST CONTROL",
            "SUBMIT FOR REVIEW");
        addText(
            "IMPORTANT: Uploaded music does not become public automatically. " +
            "AVESON ARTIST CONTROL must review and approve it first.",
            13, muted, false
        );
    }

    private void addSectionTitle(String title) {
        TextView heading = ui.makeText(title, 15, white, true);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(ui.dp(3), ui.dp(18), 0, ui.dp(8));
        content.addView(heading, params);
    }

    private void addQuickCard(
        String icon, String title, String description, String destination
    ) {
        LinearLayout card = ui.makeCard(cardColor, border);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);

        TextView iconView = ui.makeText(icon, 23, blue, true);
        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(ui.roundDrawable(surface, 13, border));
        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(ui.dp(48), ui.dp(48));
        iconParams.setMargins(0, 0, ui.dp(13), 0);
        card.addView(iconView, iconParams);

        LinearLayout textArea = new LinearLayout(activity);
        textArea.setOrientation(LinearLayout.VERTICAL);
        textArea.addView(ui.makeText(title, 14, white, true));

        TextView detail = ui.makeText(description, 11, muted, false);
        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, ui.dp(4), 0, 0);
        textArea.addView(detail, detailParams);
        card.addView(textArea, new LinearLayout.LayoutParams(0, -2, 1));

        TextView arrow = ui.makeText("›", 27, blue, true);
        card.addView(arrow);
        card.setOnClickListener(v -> navigation.open("ROOM", destination));
        ui.addCardToContent(content, card);
    }

    private void addFeatureCard(String icon, String title, String description) {
        LinearLayout card = ui.makeCard(cardColor, border);
        card.setBackground(ui.roundDrawable(surface, 20, blue));
        card.addView(ui.makeText(icon, 30, blue, true));

        TextView heading = ui.makeText(title, 18, white, true);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, ui.dp(8), 0, ui.dp(6));
        card.addView(heading, params);
        card.addView(ui.makeText(description, 13, muted, false));
        ui.addCardToContent(content, card);
    }

    private void addText(String value, int size, int color, boolean bold) {
        ui.addText(content, value, size, color, bold);
    }
}
