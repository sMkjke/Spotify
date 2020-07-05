package com.github.smkjke.spotify.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    HELP("help", new HelpCommandHandler()),
    EXIT("exit", new ExitCommandHandler()),
    FEATURED("featured", new FeaturedCommandHandler()),
    NEW("new", new GetNewReleasesCommandHandler()),
    CATEGORIES("categories", new CategoriesCommandHandler()),
    AUTH("auth", new AuthCommandHandler()),
    PLAYLISTS("playlists", new PlaylistsCommandHandler());


    public static final Map<String, CommandType> map = new HashMap<>();

    static {
        for (CommandType i : values()) {
            map.put(i.command, i);
        }
    }

    private final String command;
    private ACommandHandler input;

    CommandType(String command, ACommandHandler input) {
        this.command = command;
        this.input = input;
    }

    public static CommandType getByCommand(String command) {
        return map.get(command);
    }

    public ACommandHandler getHandler(int pageSize) {
        if (input == null) {
            System.out.println("No handler found for type" + this);
        }
        input.pager = new Paging(pageSize);
        return this.input;
    }
}

