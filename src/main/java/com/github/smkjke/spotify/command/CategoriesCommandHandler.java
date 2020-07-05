package com.github.smkjke.spotify.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.github.smkjke.spotify.engine.SpotifyEngine;

import java.io.IOException;

public class CategoriesCommandHandler extends ACommandHandler {

    private static final String urlCategories = "/v1/browse/categories";

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) throws IOException, InterruptedException {
        String categoriesAsJson = spotifyEngine.makeGetRequest(
                getApiUri(urlCategories));

        JsonObject pagingJsonObject = JsonParser.parseString(categoriesAsJson)
                .getAsJsonObject()
                .getAsJsonObject("categories");
        if (pager.getTotalAvailableItems() == 0) {
            pager.setTotalAvailableItems(pagingJsonObject.get("total").getAsInt());
        }
        printPage(pagingJsonObject);
    }

    public void printPage(JsonObject joCategories) {
        for (JsonElement item : joCategories.getAsJsonArray("items")) {
            String categoryName = item.getAsJsonObject()
                    .get("name")
                    .getAsString();
            System.out.println(categoryName);
        }
        printCurrentPagingState();
    }
}
