package com.aveson.artist;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aveson.artist.ui.ArtistUI;

public class ArtistDiscovery {

    public interface Navigation {
        void open(String type, String title);
    }

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI ui;
    private final Navigation navigation;

    private final int cardColor;
    private final int blue;
    private final int white;
    private final int muted;
    private final int border;

    public ArtistDiscovery(
        Activity activity,
        LinearLayout content,
        ArtistUI ui,
        Navigation navigation,
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
        this.cardColor = cardColor;
        this.blue = blue;
        this.white = white;
        this.muted = muted;
        this.border = border;
    }

    public void render() {
        addText("EXPLORE THE COMMUNITY", 12, blue, true);
        addText("DISCOVERY", 27, white, true);
        addText(
            "Find artists, discover music and explore new talent.",
            14, muted, false
        );

        addQuickCard("★", "POPULAR ARTISTS",
            "Artists gaining attention", "POPULAR ARTISTS");

        addQuickCard("♫", "MOST LISTENED",
            "Discover popular music", "MOST LISTENED");

        addQuickCard("↗", "RISING ARTISTS",
            "New and growing talent", "RISING ARTISTS");

        addQuickCard("✦", "FEATURED ARTISTS",
            "Artists selected by AVESON", "FEATURED ARTISTS");

        addQuickCard("◎", "MY RECOMMENDATIONS",
            "Artists recommended for you", "MY RECOMMENDATIONS");
    }

    private void addText(
        String value,
        int size,
        int color,
        boolean bold
    ) {
        ui.addText(content, value, size, color, bold);
    }

    private void addQuickCard(
        String icon,
        String title,
        String description,
        String destination
    ) {
        LinearLayout card = ui.makeCard(cardColor, border);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);

        TextView iconView = ui.makeText(icon, 23, blue, true);
        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(
            ui.roundDrawable(cardColor, 13, border)
        );

        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(ui.dp(48), ui.dp(48));
        iconParams.setMargins(0, 0, ui.dp(13), 0);
        card.addView(iconView, iconParams);

        LinearLayout textArea = new LinearLayout(activity);
        textArea.setOrientation(LinearLayout.VERTICAL);
        textArea.addView(ui.makeText(title, 14, white, true));

        TextView detail = ui.makeText(
            description, 11, muted, false
        );

        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, ui.dp(4), 0, 0);
        textArea.addView(detail, detailParams);

        card.addView(
            textArea,
            new LinearLayout.LayoutParams(0, -2, 1)
        );

        TextView arrow = ui.makeText("›", 27, blue, true);
        card.addView(arrow);

        card.setOnClickListener(v ->
            navigation.open("ROOM", destination)
        );

        ui.addCardToContent(content, card);
    }
}
