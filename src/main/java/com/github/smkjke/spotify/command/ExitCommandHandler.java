package com.github.smkjke.spotify.command;

import com.github.smkjke.spotify.engine.SpotifyEngine;

public class ExitCommandHandler extends ACommandHandler {

    private static final String exitMsg = "---GOODBYE!---";

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) {
        System.out.println(exitMsg);
        spotifyEngine.setUserLogged(false);
    }

    @Override
    public boolean hasPaging() {
        return false;
    }
}
