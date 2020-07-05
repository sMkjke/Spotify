package advisor.manager;

import advisor.command.ACommandHandler;
import advisor.command.CommandType;
import advisor.engine.SpotifyEngine;

import java.sql.SQLOutput;
import java.util.Scanner;

public class CommandManager {

    public static final String UNKNOWN_PARAM = "Unknown command";
    public static final String PROVIDE_PARAM = "Please, provide access for application.";

    private final Scanner scanner;
    final SpotifyEngine spotifyEngine;
    ACommandHandler handler;

    public CommandManager(Scanner scanner, SpotifyEngine spotifyEngine) {
        this.spotifyEngine = spotifyEngine;
        this.scanner = scanner;
    }

    public void startInput(SpotifyEngine spotifyEngine) {
        System.out.println("Hello, this is Spotify Advisor. Please log in to continue... (*TYPO: auth*)\n");
        System.out.println(" Login: smirk06@yandex.ru\n");
        System.out.println(" Pass: Qwerty112233!\n");

        while (scanner.hasNext()) {
            String parameters = null;
            String nextLine = scanner.nextLine();
            String[] parts = nextLine.split("\\s", 2);
            String type = parts[0];

            if (parts.length > 1) {
                parameters = parts[1];
            }
            try {
                if ((type.equals("next") || type.equals("prev"))
                        && handler.hasPaging() && handler != null && spotifyEngine.isUserLogged()) {
                    handler.handlePage(spotifyEngine, type);
                } else {
                    handler = CommandType.getByCommand(type).getHandler(spotifyEngine.getPageSize());
                    if (!handler.isLoginRequired() || spotifyEngine.isUserLogged()) {
                        handler.handle(spotifyEngine, parameters);
                    } else {
                        System.out.println(PROVIDE_PARAM);
                    }
                }
            } catch (Exception e) {
                System.out.println(UNKNOWN_PARAM + ": " + type);
            }
        }
    }
}