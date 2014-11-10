package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Calendar;

public class Server {

    public static boolean multiMove;

    /**
     * Constructs a server that listens on a port.
     *
     * @param port the port to listen on
     */
    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Listening on port " + port);

            MatchRoom matchRoom = new MatchRoom();

            while (true) {
                new Player(serverSocket.accept(), matchRoom).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8900;

        boolean invalidOptions = false;

        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                invalidOptions = true;
            }
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("true")) {
                multiMove = true;
            } else if (args[1].equalsIgnoreCase("false")) {
                multiMove = false;
            } else {
                invalidOptions = true;
            }
        }
        if (invalidOptions) {
            System.out.println("Syntax: <port> <multi move enabled>");
            System.exit(-1);
        }
        new Server(port);
    }

}
