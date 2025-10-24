package server;

import controllers.EchoController;
import controllers.RootController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpServer {
    private final int port;
    private final FrontController frontController;
    private boolean running = false;

    public HttpServer(int port) {
        this.port = port;

        Router router = new Router();
        router.register("/", new RootController());
        router.register("/echo", new EchoController());

        this.frontController = new FrontController(router);
    }

    public void start() {
        running = true;

        //create front controller
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            serverSocket.setReuseAddress(true);
            System.out.println("Listening on port " + this.port + "...");
            // Print important ServerSocket parameters
            System.out.println("ServerSocket info:");
            System.out.println("  Local Port: " + serverSocket.getLocalPort());
            System.out.println("  InetAddress: " + serverSocket.getInetAddress());

            while(running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted new connection");
                // Print important Client Socket parameters
                System.out.println("ClientSocket info:");
                System.out.println("  Remote Socket Address: " + clientSocket.getRemoteSocketAddress());
                System.out.println("  Local Socket Address: " + clientSocket.getLocalSocketAddress());

                HttpConnection connection = new HttpConnection(clientSocket, frontController);
                connection.handle();

            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
