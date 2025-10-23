import server.HttpServer;

public class Application {
    public static void main(String[] args) {
        int port = 8080;
        HttpServer server = new HttpServer(port);
        server.start();
    }
}
