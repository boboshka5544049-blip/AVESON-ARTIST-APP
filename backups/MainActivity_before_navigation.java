package com.aveson.artist;

import android.app.Activity;
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

public class MainActivity extends Activity {

    private LinearLayout content;
    private String currentPage = "HOME";

    private final int BG = Color.rgb(8, 8, 18);
    private final int CARD = Color.rgb(22, 20, 42);
    private final int BLUE = Color.rgb(35, 145, 255);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(165, 165, 185);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.getStartedButton);
        start.setOnClickListener(v -> showHome());
    }

    private void showHome() {
        currentPage = "HOME";
        createScreen();

        addText("Welcome to AVESON ARTIST", 25, WHITE, true);
        addText("Your creative journey starts here.", 16, MUTED, false);
        addText("Your artist platform is ready.", 17, WHITE, false);
    }

    private void createScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        root.setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(
                12,
                insets.getSystemWindowInsetTop() + 8,
                12,
                insets.getSystemWindowInsetBottom() + 8
            );
            return insets;
        });

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setOrientation(LinearLayout.HORIZONTAL);

        Button back = makeButton("←", CARD, BLUE);
        back.setTextSize(23);
        back.setVisibility(
            currentPage.equals("HOME") ? View.INVISIBLE : View.VISIBLE
        );
        back.setOnClickListener(v -> handleBack());

        LinearLayout.LayoutParams backParams =
            new LinearLayout.LayoutParams(dp(56), dp(48));
        backParams.setMargins(0, 0, dp(6), 0);
        top.addView(back, backParams);

        TextView title = makeText("AVESON ARTIST", 17, WHITE, true);
        title.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams titleParams =
            new LinearLayout.LayoutParams(0, dp(48), 1);
        top.addView(title, titleParams);

        Button menu = makeButton("MENU", CARD, WHITE);
        menu.setTextSize(13);
        menu.setOnClickListener(v -> showMenu());

        LinearLayout.LayoutParams menuParams =
            new LinearLayout.LayoutParams(dp(76), dp(48));
        menuParams.setMargins(dp(5), 0, dp(9), 0);
        top.addView(menu, menuParams);

        Button bell = makeButton("🔔", BLUE, WHITE);
        bell.setTextSize(20);
        bell.setOnClickListener(v -> showNotifications());

        LinearLayout.LayoutParams bellParams =
            new LinearLayout.LayoutParams(dp(50), dp(48));
        top.addView(bell, bellParams);

        root.addView(top);

        View divider = new View(this);
        divider.setBackgroundColor(CARD);

        LinearLayout.LayoutParams dividerParams =
            new LinearLayout.LayoutParams(-1, 2);
        dividerParams.setMargins(0, 10, 0, 10);
        root.addView(divider, dividerParams);

        ScrollView scroll = new ScrollView(this);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(4, 8, 4, 8);

        scroll.addView(content);

        root.addView(
            scroll,
            new LinearLayout.LayoutParams(-1, 0, 1)
        );

        setContentView(root);
        root.requestApplyInsets();
    }

    private void showMenu() {
        currentPage = "MENU";
        createScreen();

        addText("MAIN MENU", 23, WHITE, true);
        addText(
            "Choose one of the two separate rooms.",
            15,
            MUTED,
            false
        );

        String[] workspaceItems = {
            "ARTIST PROFILE",
            "MY RELEASES",
            "MY MUSIC",
            "ROYALTY & EARNINGS",
            "ANALYTICS",
            "COLLABORATION",
            "EVENTS & SCHEDULE"
        };

        String[] serviceItems = {
            "DOCUMENTS & CONTRACTS",
            "SUPPORT CENTER",
            "MY CAREER",
            "NOTIFICATIONS CENTER",
            "LANGUAGE SETTINGS"
        };

        addCategoryCard(
            "ARTIST WORKSPACE",
            "Artistning kundalik faoliyati va ijodiy ishlari.",
            workspaceItems
        );

        addCategoryCard(
            "ARTIST SERVICES",
            "Hujjatlar, yordam va qo‘shimcha xizmatlar.",
            serviceItems
        );
    }

    private void addCategoryCard(
        String title,
        String description,
        String[] items
    ) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(CARD);
        shape.setCornerRadius(dp(18));
        card.setBackground(shape);

        TextView heading = makeText(title, 18, WHITE, true);
        card.addView(heading);

        TextView detail = makeText(description, 13, MUTED, false);

        LinearLayout.LayoutParams detailParams =
            new LinearLayout.LayoutParams(-1, -2);
        detailParams.setMargins(0, dp(7), 0, dp(12));
        card.addView(detail, detailParams);

        Button open = makeButton("OPEN ROOM  →", BLUE, WHITE);
        open.setTextSize(13);
        open.setOnClickListener(v -> showCategory(title, items));

        card.addView(
            open,
            new LinearLayout.LayoutParams(-1, dp(46))
        );

        LinearLayout.LayoutParams cardParams =
            new LinearLayout.LayoutParams(-1, -2);
        cardParams.setMargins(0, dp(9), 0, dp(9));

        content.addView(card, cardParams);
    }

    private void showCategory(String title, String[] items) {

        currentPage = title;
        createScreen();

        addText(title, 22, WHITE, true);
        addText("Choose a section.", 15, MUTED, false);

        for (String item : items) {

            Button button = makeButton(item, CARD, WHITE);

            button.setOnClickListener(v -> {
                if (item.equals("NOTIFICATIONS CENTER")) {
                    showNotifications();
                } else {
                    showRoom(item);
                }
            });

            content.addView(button, buttonParams());
        }
    }

    private void showNotifications() {

        currentPage = "NOTIFICATIONS";
        createScreen();

        addText("NOTIFICATIONS CENTER", 22, BLUE, true);
        addText("No new notifications yet.", 17, MUTED, false);

        addText(
            "Release approvals, rejections and updates will appear here.",
            15,
            WHITE,
            false
        );
    }

    private void showRoom(String room) {

        currentPage = room;
        createScreen();

        addText(room, 22, WHITE, true);

        addText(
            "This section will be developed step by step.",
            16,
            MUTED,
            false
        );
    }

    private void handleBack() {

        if (currentPage.equals("ARTIST WORKSPACE")
                || currentPage.equals("ARTIST SERVICES")) {
            showMenu();
        } else if (!currentPage.equals("HOME")) {
            showHome();
        } else {
            super.onBackPressed();
        }
    }

    private void addText(
        String value,
        int size,
        int color,
        boolean bold
    ) {

        TextView text = makeText(value, size, color, bold);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 12, 0, 12);
        content.addView(text, params);
    }

    private TextView makeText(
        String value,
        int size,
        int color,
        boolean bold
    ) {

        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);

        if (bold) {
            text.setTypeface(null, Typeface.BOLD);
        }

        return text;
    }

    private Button makeButton(
        String value,
        int bg,
        int fg
    ) {

        Button button = new Button(this);
        button.setText(value);
        button.setTextColor(fg);
        button.setTextSize(12);
        button.setAllCaps(false);
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setPadding(4, 0, 4, 0);
        button.setStateListAnimator(null);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(bg);
        shape.setCornerRadius(dp(12));

        button.setBackground(shape);

        return button;
    }

    private LinearLayout.LayoutParams buttonParams() {

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, dp(56));

        params.setMargins(0, 5, 0, 5);

        return params;
    }

    private int dp(int value) {
        return (int) (
            value * getResources().getDisplayMetrics().density + 0.5f
        );
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }
}
