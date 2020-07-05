package com.github.smkjke.spotify.engine;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerEngine {

    final static String WAITING_CODE = "waiting for code...";
    final static String GOT_CODE = "Got the code. Return back to your program.";
    final static String CODE_RECEIVED = "code received";
    final static String MAKING_REQUEST = "making http request for access_token...";
    final static String CODE_NOT_RECEIVED = "Not found authorization code. Try again";

    private String parseCode(final String query) {
        String[] queryArr = query.split("&");
        for (String i : queryArr) {
            if (i.contains("code=")) {
                String[] splitArr = i.split("=");
                return splitArr[1];
            }
        }
        return null;
    }

    public ServerEngine(SpotifyEngine spotify) {

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        if (exchange.getRequestURI().getQuery() != null) {
                            System.out.println(WAITING_CODE);
                            exchange.sendResponseHeaders(200, GOT_CODE.length());
                            exchange.getResponseBody().write(GOT_CODE.getBytes());
                            exchange.getResponseBody().close();
                            System.out.println(CODE_RECEIVED);

                            try {
                                System.out.println(MAKING_REQUEST);
                                String code = parseCode(exchange.getRequestURI().getQuery());
                                spotify.initWithCode(code);
                                server.stop(1);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            exchange.sendResponseHeaders(200, CODE_NOT_RECEIVED.length());
                            exchange.getResponseBody().write(CODE_NOT_RECEIVED.getBytes());
                            exchange.getResponseBody().close();
                        }
                    }
            );
//            Thread.sleep(5);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}