package advisor.engine;

import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyEngine {

    public static final String SUCCESS_RESP = "---SUCCESS---";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String RESPONSE_TYPE = "response_type";

    private static final String client_id = "d633032e562d419499af2def7645e89f";
    private static final String secret_id = "05757781b1df4e369ce6e506880f2122";
    private static final String redirect_uri = "http://localhost:8080";

    private boolean userLogged = false;
    private String code = "";
    private String apiServer;
    private String tokenUriServer;
    private String accessToken;
    private int pageSize;

    public SpotifyEngine(final String tokenUriServer, final String apiServer, int pageSize) {
        this.tokenUriServer = tokenUriServer;
        this.apiServer = apiServer;
        this.pageSize = pageSize;
    }

    public boolean isUserLogged() {
        return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
        this.userLogged = userLogged;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getAuthorizationUrl() throws URISyntaxException {

        System.out.println("use this link to request the access code: ");
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("accounts.spotify.com").setPath("/authorize")
                .setParameter(CLIENT_ID, client_id)
                .setParameter(REDIRECT_URI, redirect_uri)
                .setParameter(RESPONSE_TYPE, "code");
        URI uri = uriBuilder.build();

        return uri.toString();
    }

    public String makeGetRequest(final String url) throws IOException, InterruptedException {
        HttpRequest requestForPlaylists = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiServer + url.replaceAll(apiServer,"")))
                .GET()
                .build();

        final HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(requestForPlaylists, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    protected void initWithCode(final String code) throws IOException, InterruptedException {
        this.code = code;
        refreshAccessToken();
    }

    private void refreshAccessToken() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(tokenUriServer))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code&code="
                                + code
                                + "&client_id=" + client_id
                                + "&client_secret=" + secret_id
                                + "&redirect_uri=" + redirect_uri))
                .build();

        final HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            userLogged = true;
            System.out.println(SUCCESS_RESP);
        }

        JsonObject jsonObject = com.google.gson.JsonParser.parseString(response.body()).getAsJsonObject();
        accessToken = jsonObject.get("access_token").getAsString();
    }

}

