package com.aveson.artist.menu;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aveson.artist.ui.ArtistUI;

public class ArtistMenuContent {

    public interface Listener {
        void openCategory(String title, String[] items);
    }

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI ui;
    private final Listener listener;

    private final int CARD;
    private final int BLUE;
    private final int WHITE;
    private final int MUTED;
    private final int BORDER;

    public ArtistMenuContent(
        Activity activity,
        LinearLayout content,
        ArtistUI ui,
        Listener listener,
        int card,
        int blue,
        int white,
        int muted,
        int border
    ) {
        this.activity = activity;
        this.content = content;
        this.ui = ui;
        this.listener = listener;

        this.CARD = card;
        this.BLUE = blue;
        this.WHITE = white;
        this.MUTED = muted;
        this.BORDER = border;
    }

    public void render() {
        addText("EXPLORE", 12, BLUE, true);
        addText("MAIN MENU", 27, WHITE, true);
        addText(
            "Your complete artist management space.",
            14,
            MUTED,
            false
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
        LinearLayout card = ui.makeCard(CARD, BORDER);

        TextView numberView = ui.makeText(
            number,
            12,
            BLUE,
            true
        );
        card.addView(numberView);

        card.addView(
            ui.makeText(title, 19, WHITE, true)
        );

        TextView detail = ui.makeText(
            description,
            13,
            MUTED,
            false
        );

        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);

        detailParams.setMargins(
            0,
            ui.dp(7),
            0,
            ui.dp(15)
        );

        card.addView(detail, detailParams);

        Button open = ui.makeButton(
            "OPEN ROOM     →",
            BLUE,
            WHITE
        );

        open.setTextSize(13);
        open.setOnClickListener(v ->
            listener.openCategory(title, items)
        );

        card.addView(
            open,
            new LinearLayout.LayoutParams(-1, ui.dp(47))
        );

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
