package advisor.command;

import advisor.engine.SpotifyEngine;

import java.net.URISyntaxException;

public class AuthCommandHandler extends ACommandHandler {

    @Override
    public boolean isLoginRequired() {
        return false;
    }

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) throws URISyntaxException {
        System.out.println(spotifyEngine.getAuthorizationUrl());
    }

    @Override
    public boolean hasPaging() {
        return false;
    }
}