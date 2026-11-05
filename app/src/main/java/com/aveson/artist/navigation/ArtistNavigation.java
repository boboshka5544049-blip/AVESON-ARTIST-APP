package com.aveson.artist.navigation;

import java.util.ArrayList;
import java.util.List;

public class ArtistNavigation {

    private final List<ArtistPage> history = new ArrayList<>();
    private ArtistPage currentPage;

    public void navigate(ArtistPage page) {
        if (currentPage != null) {
            history.add(currentPage);
        }
        currentPage = page;
    }

    public void selectTab(ArtistPage page) {
        history.clear();
        currentPage = page;
    }

    public ArtistPage goBack() {
        if (history.isEmpty()) {
            return null;
        }

        currentPage = history.remove(history.size() - 1);
        return currentPage;
    }

    public ArtistPage getCurrentPage() {
        return currentPage;
    }

    public boolean canGoBack() {
        return !history.isEmpty();
    }
}
