
package com.aveson.artist;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aveson.artist.ui.ArtistUI;

public class ArtistHome {

    public interface Navigation {
        void open(String type, String title);
    }

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI ui;
    private final Navigation navigation;

    private final int BG = Color.rgb(8, 11, 20);
    private final int SURFACE = Color.rgb(17, 26, 43);
    private final int CARD = Color.rgb(23, 34, 56);
    private final int BLUE = Color.rgb(37, 143, 255);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(165, 178, 200);
    private final int BORDER = Color.rgb(41, 59, 87);

    public ArtistHome(
        Activity activity,
        LinearLayout content,
        ArtistUI ui,
        Navigation navigation
    ) {
        this.activity = activity;
        this.content = content;
        this.ui = ui;
        this.navigation = navigation;
    }

    public void render() {
        addText("WELCOME BACK", 12, BLUE, true);
        addText("Your creative world.", 27, WHITE, true);
        addText(
            "Everything you need for your music career.",
            14, MUTED, false
        );

        addFeatureCard(
            "✦",
            "YOUR ARTIST SPACE",
            "Manage your music, profile and creative journey.",
            "PROFILE",
            "PROFILE"
        );

        addSectionTitle("QUICK ACCESS");

        addQuickCard(
            "♫",
            "MY MUSIC",
            "Your personal music library",
            "MY_MUSIC",
            "MY MUSIC"
        );

        addQuickCard(
            "◎",
            "DISCOVERY",
            "Explore artists and new talent",
            "DISCOVERY",
            "DISCOVERY"
        );

        addQuickCard(
            "↑",
            "UPLOAD MUSIC",
            "Submit your music for review",
            "UPLOAD",
            "UPLOAD MUSIC"
        );
    }

    private void addSectionTitle(String title) {
        TextView heading = ui.makeText(
            title, 15, WHITE, true
        );

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(
            dp(3), dp(18), 0, dp(8)
        );

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

        TextView iconView = ui.makeText(
            icon, 23, BLUE, true
        );

        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(
            roundDrawable(SURFACE, 13, BORDER)
        );

        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(
                dp(48), dp(48)
            );

        iconParams.setMargins(
            0, 0, dp(13), 0
        );

        card.addView(iconView, iconParams);

        LinearLayout textArea =
            new LinearLayout(activity);

        textArea.setOrientation(
            LinearLayout.VERTICAL
        );

        textArea.addView(
            ui.makeText(title, 14, WHITE, true)
        );

        TextView detail = ui.makeText(
            description, 11, MUTED, false
        );

        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);

        detailParams.setMargins(
            0, dp(4), 0, 0
        );

        textArea.addView(detail, detailParams);

        card.addView(
            textArea,
            new LinearLayout.LayoutParams(0, -2, 1)
        );

        TextView arrow = ui.makeText(
            "›", 27, BLUE, true
        );

        card.addView(arrow);

        card.setOnClickListener(v ->
            navigation.open(type, destination)
        );

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

        card.setBackground(
            roundDrawable(SURFACE, 20, BLUE)
        );

        TextView symbol = ui.makeText(
            icon, 30, BLUE, true
        );

        card.addView(symbol);

        TextView heading = ui.makeText(
            title, 18, WHITE, true
        );

        LinearLayout.LayoutParams headingParams =
            new LinearLayout.LayoutParams(-1, -2);

        headingParams.setMargins(
            0, dp(8), 0, dp(6)
        );

        card.addView(heading, headingParams);

        card.addView(
            ui.makeText(description, 13, MUTED, false)
        );

        if (type != null && destination != null) {
            card.setOnClickListener(v ->
                navigation.open(type, destination)
            );
        }

        addCardToContent(card);
    }

    private LinearLayout makeCard() {
        return ui.makeCard(CARD, BORDER);
    }

    private void addCardToContent(LinearLayout card) {
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

    private GradientDrawable roundDrawable(
        int color,
        int radius,
        int strokeColor
    ) {
        return ui.roundDrawable(
            color, radius, strokeColor
        );
    }

    private int dp(int value) {
        return ui.dp(value);
    }
}
