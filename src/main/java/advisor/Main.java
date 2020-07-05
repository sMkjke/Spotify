package advisor;

import advisor.engine.ServerEngine;
import advisor.engine.SpotifyEngine;
import advisor.manager.CommandManager;

import java.util.Scanner;

/**
 * -access argument should provide authorization server path. The default value should be https://accounts.spotify.com .
 * -resource argument should provide api server path. The default value should be https://api.spotify.com .
 * -page. If ths argument isn't set, you should use the default value 5.
 *
 * To use app:
 *
 * Typo: "auth" for authorization;
 * Available commands:
 *     HELP
 *     EXIT
 *     FEATURED
 *     NEW
 *     CATEGORIES
 *     PLAYLISTS
 *
 *     NEXT
 *     PREVIOUS
 *
 */
public class Main {

    CommandManager commandManager;
    ServerEngine serverEngine;
    SpotifyEngine spotifyEngine;
    //    Properties appConfig;

    public Main(String tokenUriServer, String apiServer, int pageSize) {
        Scanner scanner = new Scanner(System.in);
        spotifyEngine = new SpotifyEngine(tokenUriServer, apiServer, pageSize);
        serverEngine = new ServerEngine(spotifyEngine);
        commandManager = new CommandManager(scanner, spotifyEngine);
        //this.appConfig = loadConfig();
    }

    public static void main(String[] args) {

        String tokenUriServer = "https://accounts.spotify.com/api/token";
        String apiServer = "https://api.spotify.com";
        int pageSize = 5;

        for (int i = 0; i < args.length; i += 2) {
            if (i + 1 < args.length) {
                if ("-access".equals(args[i])) {
                    tokenUriServer = args[i + 1];
                } else if ("-resource".equals(args[i])) {
                    apiServer = args[i + 1];
                } else if ("-page".equals(args[i])) {
                    pageSize = Integer.parseInt(args[i + 1]);
                }
            }
        }
        Main application = new Main(tokenUriServer, apiServer, pageSize);
        application.start();
    }

    public void start() {
        commandManager.startInput(spotifyEngine);
    }
}