package com.aveson.artist;

import android.app.Activity;
import android.widget.LinearLayout;

import com.aveson.artist.ui.ArtistUI;

public class ArtistMyMusic {

    public interface Navigation {
        void open(String type, String title);
    }

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI ui;
    private final Navigation navigation;

    private final int surface;
    private final int cardColor;
    private final int blue;
    private final int white;
    private final int muted;
    private final int border;

    public ArtistMyMusic(
        Activity activity,
        LinearLayout content,
        ArtistUI ui,
        Navigation navigation,
        int surface,
        int cardColor,
        int blue,
        int white,
        int muted,
        int border
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
        addText("YOUR COLLECTION", 12, blue, true);
        addText("MY MUSIC", 27, white, true);
        addText(
            "Your music and releases will be organized here.",
            14, muted, false
        );

        addFeatureCard(
            "♫",
            "YOUR MUSIC LIBRARY",
            "Your uploaded songs, approved releases and music details."
        );

        addQuickCard(
            "♫",
            "MY RELEASES",
            "Manage your submitted releases",
            "MY RELEASES"
        );
    }

    private void addQuickCard(
        String icon,
        String title,
        String description,
        String destination
    ) {
        LinearLayout card = ui.makeCard(cardColor, border);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(android.view.Gravity.CENTER_VERTICAL);

        android.widget.TextView iconView =
            ui.makeText(icon, 23, blue, true);
        iconView.setGravity(android.view.Gravity.CENTER);
        iconView.setBackground(
            ui.roundDrawable(surface, 13, border)
        );

        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(ui.dp(48), ui.dp(48));
        iconParams.setMargins(0, 0, ui.dp(13), 0);
        card.addView(iconView, iconParams);

        LinearLayout textArea = new LinearLayout(activity);
        textArea.setOrientation(LinearLayout.VERTICAL);
        textArea.addView(ui.makeText(title, 14, white, true));

        android.widget.TextView detail =
            ui.makeText(description, 11, muted, false);
        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, ui.dp(4), 0, 0);
        textArea.addView(detail, detailParams);

        card.addView(
            textArea,
            new LinearLayout.LayoutParams(0, -2, 1)
        );

        card.addView(ui.makeText("›", 27, blue, true));

        card.setOnClickListener(v ->
            navigation.open("ROOM", destination)
        );

        ui.addCardToContent(content, card);
    }

    private void addFeatureCard(
        String icon,
        String title,
        String description
    ) {
        LinearLayout card = ui.makeCard(cardColor, border);
        card.setBackground(
            ui.roundDrawable(surface, 20, blue)
        );

        card.addView(ui.makeText(icon, 30, blue, true));

        android.widget.TextView heading =
            ui.makeText(title, 18, white, true);
        LinearLayout.LayoutParams headingParams =
            new LinearLayout.LayoutParams(-1, -2);
        headingParams.setMargins(0, ui.dp(8), 0, ui.dp(6));
        card.addView(heading, headingParams);

        card.addView(ui.makeText(description, 13, muted, false));
        ui.addCardToContent(content, card);
    }

    private void addText(
        String value,
        int size,
        int color,
        boolean bold
    ) {
        ui.addText(content, value, size, color, bold);
    }
}
