package com.aveson.artist.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArtistUI {

    private final Activity activity;

    public ArtistUI(Activity activity) {
        this.activity = activity;
    }

    public int dp(int value) {
        return (int) (
            value * activity.getResources()
                .getDisplayMetrics().density + 0.5f
        );
    }

    public TextView makeText(
        String value,
        int size,
        int color,
        boolean bold
    ) {
        TextView text = new TextView(activity);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);

        if (bold) {
            text.setTypeface(null, Typeface.BOLD);
        }

        return text;
    }

    public Button makeButton(
        String value,
        int bg,
        int fg
    ) {
        Button button = new Button(activity);
        button.setText(value);
        button.setTextColor(fg);
        button.setTextSize(12);
        button.setAllCaps(false);
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setPadding(dp(8), 0, dp(8), 0);
        button.setStateListAnimator(null);
        button.setBackground(roundDrawable(bg, 13, bg));

        return button;
    }

    public GradientDrawable roundDrawable(
        int color,
        int radius,
        int strokeColor
    ) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
        shape.setStroke(dp(1), strokeColor);

        return shape;
    }

    public LinearLayout makeCard(
        int background,
        int border
    ) {
        LinearLayout card = new LinearLayout(activity);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(15), dp(15), dp(15), dp(15));
        card.setBackground(roundDrawable(background, 18, border));

        return card;
    }

    public void addText(
        LinearLayout content,
        String value,
        int size,
        int color,
        boolean bold
    ) {
        TextView text = makeText(value, size, color, bold);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(dp(3), dp(7), dp(3), dp(7));
        content.addView(text, params);
    }

    public void addCardToContent(
        LinearLayout content,
        LinearLayout card
    ) {
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, dp(7), 0, dp(7));
        content.addView(card, params);
    }
}
