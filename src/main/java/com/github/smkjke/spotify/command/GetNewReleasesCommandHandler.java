package com.github.smkjke.spotify.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.github.smkjke.spotify.engine.SpotifyEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetNewReleasesCommandHandler extends ACommandHandler {

    private static final String urlNew = "/v1/browse/new-releases";


    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) throws IOException, InterruptedException {
        String categoriesAsJson = spotifyEngine.makeGetRequest(
                getApiUri(urlNew));
        JsonObject pagingJsonObject = JsonParser.parseString(categoriesAsJson)
                .getAsJsonObject()
                .getAsJsonObject("albums");
        if (pager.getTotalAvailableItems() == 0) {
            pager.setTotalAvailableItems(pagingJsonObject.get("total").getAsInt());
        }
        printPage(pagingJsonObject);
    }

    public void printPage(JsonObject joCategories) {
        for (JsonElement item : joCategories.getAsJsonArray("items")) {
            String albumName = item.getAsJsonObject().get("name").getAsString();
            System.out.println(albumName);

            List<String> artistsOfOneAlbum = new ArrayList<>();
            for (JsonElement artist : item.getAsJsonObject().getAsJsonArray("artists")) {
                artistsOfOneAlbum.add(artist.getAsJsonObject().get("name").getAsString());
            }
            System.out.println(Arrays.toString(artistsOfOneAlbum.toArray()));

            String albumLink = item.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            System.out.println(albumLink);
            System.out.println();
        }
        printCurrentPagingState();
    }
}