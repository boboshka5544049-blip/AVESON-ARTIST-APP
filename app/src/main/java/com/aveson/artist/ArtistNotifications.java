
package com.aveson.artist;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aveson.artist.ui.ArtistUI;

public class ArtistNotifications {

    private final Activity activity;
    private final LinearLayout content;
    private final ArtistUI artistUI;

    private final int surface;
    private final int cardColor;
    private final int blue;
    private final int white;
    private final int muted;
    private final int border;

    public ArtistNotifications(
        Activity activity,
        LinearLayout content,
        ArtistUI artistUI,
        int surface,
        int cardColor,
        int blue,
        int white,
        int muted,
        int border
    ) {
        this.activity = activity;
        this.content = content;
        this.artistUI = artistUI;

        this.surface = surface;
        this.cardColor = cardColor;
        this.blue = blue;
        this.white = white;
        this.muted = muted;
        this.border = border;
    }

    public void render() {

        artistUI.addText(
            content,
            "UPDATES AND ACTIVITY",
            12,
            blue,
            true
        );

        artistUI.addText(
            content,
            "NOTIFICATIONS",
            25,
            white,
            true
        );

        LinearLayout card = artistUI.makeCard(
            cardColor,
            border
        );

        card.setBackground(
            artistUI.roundDrawable(
                surface,
                20,
                blue
            )
        );

        TextView symbol = artistUI.makeText(
            "♢",
            30,
            blue,
            true
        );

        card.addView(symbol);

        TextView heading = artistUI.makeText(
            "YOU'RE ALL CAUGHT UP",
            18,
            white,
            true
        );

        LinearLayout.LayoutParams headingParams =
            new LinearLayout.LayoutParams(-1, -2);

        headingParams.setMargins(
            0,
            artistUI.dp(8),
            0,
            artistUI.dp(6)
        );

        card.addView(heading, headingParams);

        TextView description = artistUI.makeText(
            "Release approvals, review feedback, distribution updates, payments and AVESON announcements will appear here.",
            13,
            muted,
            false
        );

        card.addView(description);

        artistUI.addCardToContent(
            content,
            card
        );
    }
}
