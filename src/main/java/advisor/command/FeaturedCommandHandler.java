package advisor.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import advisor.engine.SpotifyEngine;

import java.io.IOException;


public class FeaturedCommandHandler extends ACommandHandler {

    private static final String urlPlaylists = "/v1/browse/featured-playlists";

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) throws IOException, InterruptedException {
        String categoriesAsJson = spotifyEngine.makeGetRequest(
                getApiUri(urlPlaylists));

        JsonObject pagingJsonObject = JsonParser.parseString(categoriesAsJson)
                .getAsJsonObject()
                .getAsJsonObject("playlists");
        if (pager.getTotalAvailableItems() == 0) {
            pager.setTotalAvailableItems(pagingJsonObject.get("total").getAsInt());
        }
        printPage(pagingJsonObject);
    }


    public void printPage(JsonObject joCategories) {
        for (JsonElement item : joCategories.getAsJsonArray("items")) {
            String playListName = item.getAsJsonObject()
                    .get("name")
                    .getAsString();
            String playlistLink = item.getAsJsonObject()
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
