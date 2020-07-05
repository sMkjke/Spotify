package com.github.smkjke.spotify.command;

import com.github.smkjke.spotify.engine.SpotifyEngine;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlaylistsCommandHandler extends ACommandHandler {

    private static final String urlCategories = "/v1/browse/categories/";
    private static final String urlPlaylists = "/playlists";

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) {
        if (pager.getCurrentParams() != null || parameters != null) {
            pager.setCurrentParams(parameters);
            try {
                String categoriesAsJson = spotifyEngine.makeGetRequest(
                        getApiUri(urlCategories
                                + pager.getCurrentParams().toLowerCase().replaceAll("\\s+", "")
                                + urlPlaylists)
                );
                JsonObject pagingJsonObject = JsonParser.parseString(categoriesAsJson)
                        .getAsJsonObject()
                        .getAsJsonObject("playlists");
                if (pager.getTotalAvailableItems() == 0) {
                    pager.setTotalAvailableItems(pagingJsonObject.get("total").getAsInt());
                }
                printPage(pagingJsonObject);
            } catch (Exception e) {
                System.out.println("Unknown category name.");
            }
        } else System.out.println("Missing Parameter");
    }

    public void printPage(JsonObject joPlaylistsInCategory) {

        for (JsonElement pl : joPlaylistsInCategory.getAsJsonArray("items")) {
            String playListName = pl.getAsJsonObject()
                    .get("name")
                    .getAsString();

            String playlistLink = pl.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            System.out.println(playListName);
            System.out.println(playlistLink);
            System.out.println();
        }
        printCurrentPagingState();
    }
}

