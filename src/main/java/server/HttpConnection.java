package server;

import constants.HttpConstants;
import constants.ServerConstants;
import http.*;
import middlewares.HttpCompressionMiddleware;
import middlewares.MiddlewareManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class HttpConnection implements Runnable {
    private final Socket clientSocket;
    private final FrontController frontController;

    public HttpConnection(Socket clientSocket, FrontController frontController) {
        this.clientSocket = clientSocket;
        this.frontController = frontController;
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
            System.err.println("HttpConnection.run(): [I/O ERROR] Problem handling client: " + e.getMessage());
        }
    }

    public void handle() throws IOException {
        // Handle the connection (read request, process it, send response, process next request if keep-alive)
        try (
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
        ) {
            boolean keepAlive = true, firstRequest = true;

            while(keepAlive) {
                if (firstRequest) {
                    clientSocket.setSoTimeout(ServerConstants.READ_TIMEOUT_MS);
                } else {
                    clientSocket.setSoTimeout(ServerConstants.KEEP_ALIVE_TIMEOUT_MS);
                }

                HttpRequest request = HttpRequestParser.parse(in);
                HttpResponse response = new HttpResponse();
                HttpExchange exchange = new HttpExchange(request, response, frontController);
                exchange.handle();
                HttpResponseWriter.write(response.toImmutable(), out);

                //keep-alive logic
                String reqConnection = request.getHeader(HttpConstants.Headers.CONNECTION);
                String resConnection = response.getHeader(HttpConstants.Headers.CONNECTION);

                boolean reqeustClose = reqConnection != null && reqConnection.equalsIgnoreCase("close");
                boolean responseClose = resConnection != null && resConnection.equalsIgnoreCase("close");

                if (reqeustClose || responseClose || clientSocket.isClosed()) {
                    keepAlive = false;
                }
                firstRequest = false;

            }

        } catch (SocketTimeoutException e) {
            System.err.println("Keep-Alive timeout: closing idle connection");
        } catch (IOException e) {
            System.err.println("HttpConnection.handle() [I/O ERROR] Problem handling client: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Remote Socket Address: " + clientSocket.getRemoteSocketAddress());
            System.err.println("HttpConnection.handle() [ERROR] Unexpected issue: " + e.getMessage());
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close(); // Optional, ensures full cleanup
                } catch (IOException e) {
                    System.err.println("HttpConnection.handle() [ERROR] Failed to close socket: " + e.getMessage());
                }
            }
        }

    }
}
