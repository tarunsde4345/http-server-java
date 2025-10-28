package server;

import http.HttpRequest;
import http.HttpResponse;
import server.router.PathMatchResult;
import server.router.Router;

public class FrontController {
    //gets message, has router to help decide where to send it
    private final Router router;

    public FrontController(Router router) {
        this.router = router;
    }

    public void handleRequest(HttpRequest request, HttpResponse response) {
        try {
            PathMatchResult resolution = router.resolve(request); //method should also be considered
            switch (resolution) {
                case PathMatchResult.Found f -> {
                    f.route().getController().handle(request, response);
                }
                case PathMatchResult.MethodNotAllowed m -> {
                    response.status(405)
                            .header("Allow", String.join(", ", m.allowed()));
                }
                case PathMatchResult.NotFound n -> {
                    response.status(404);
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to handle request: " + e.getMessage());
            response.status(500);
        }

    }
}
