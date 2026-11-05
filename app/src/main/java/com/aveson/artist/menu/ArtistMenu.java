package com.aveson.artist.menu;

import com.aveson.artist.navigation.ArtistNavigation;
import com.aveson.artist.navigation.ArtistPage;

public class ArtistMenu {

    private final ArtistNavigation navigation;

    public ArtistMenu(ArtistNavigation navigation) {
        this.navigation = navigation;
    }

    public void openMenu() {
        navigation.navigate(
            new ArtistPage("MENU", "MAIN MENU", null)
        );
    }

    public void openCategory(String title, String[] items) {
        navigation.navigate(
            new ArtistPage("CATEGORY", title, items)
        );
    }
}
