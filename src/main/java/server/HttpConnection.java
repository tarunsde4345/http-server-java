package server;

import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnection {
    private final Socket clientSocket;
    private final FrontController frontController;

    public HttpConnection(Socket clientSocket, FrontController frontController) {
        this.clientSocket = clientSocket;
        this.frontController = frontController;
    }

    public void handle() throws IOException {
        // Handle the connection (read request, process it, send response)
        try (
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
        ) {
            HttpRequest request = HttpRequestParser.parse(in);
            System.out.println("Received request: " + request);

            HttpResponse response = frontController.handleRequest(request);

            out.write(response.toBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println("[I/O ERROR] Problem handling client: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected issue: " + e.getMessage());
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close(); // Optional, ensures full cleanup
                } catch (IOException e) {
                    System.err.println("[ERROR] Failed to close socket: " + e.getMessage());
                }
            }
        }

    }
}
