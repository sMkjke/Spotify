package advisor.command;

import advisor.engine.SpotifyEngine;

public class HelpCommandHandler extends ACommandHandler {

    public HelpCommandHandler() {
        this.loginRequired = false;
    }

    private static final String helpMessage = "The program is connecting to spotify" +
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