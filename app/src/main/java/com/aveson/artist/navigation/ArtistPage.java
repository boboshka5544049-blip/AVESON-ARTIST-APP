package com.aveson.artist.navigation;

public class ArtistPage {

    public final String type;
    public final String title;
    public final String[] items;

    public ArtistPage(String type, String title, String[] items) {
        this.type = type;
        this.title = title;
        this.items = items;
    }
}
