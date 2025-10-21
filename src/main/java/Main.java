import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

     try (ServerSocket serverSocket = new ServerSocket(4221)) {
         serverSocket.setReuseAddress(true);

         Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
         System.out.println("accepted new connection");

         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         String requestLine = in.readLine();
         URI uri = null;
         if (requestLine != null) {
             String[] parts = requestLine.split(" ");
             if (parts.length > 2) {
                 uri = new URI(parts[1]);
                 System.out.println("Request path: " + uri.getPath());
             }
             System.out.println("Received request: " + requestLine);
         }

         OutputStream out = clientSocket.getOutputStream();
         String response = "";
         if (uri != null && "/".equals(uri.getPath()))
            response = "HTTP/1.1 200 OK\r\n\r\n";
         else
             response = "HTTP/1.1 404 Not Found\r\n\r\n";
         out.write(response.getBytes());
         out.flush();
         clientSocket.close();
     } catch (IOException | URISyntaxException e) {
       System.out.println("Exception: " + e.getMessage());
     }
  }
}
