package com.github.smkjke.spotify.command;

import com.github.smkjke.spotify.engine.SpotifyEngine;

public class HelpCommandHandler extends ACommandHandler {

    public HelpCommandHandler() {
        this.loginRequired = false;
    }

    private static final String helpMessage = "The program is connecting to com.github.smkjke.spotify" +
            " API to get info about your favourite music";

    @Override
    public void handle(final SpotifyEngine spotifyEngine, final String parameters) {
        System.out.println(helpMessage);
    }

    @Override
    public boolean hasPaging() {
        return false;
    }
}