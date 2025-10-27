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

    public HttpResponse handleRequest(HttpRequest reqeust) {
        try {
            PathMatchResult resolution = router.resolve(reqeust); //method should also be considered
            switch (resolution) {
                case PathMatchResult.Found f -> {
                    return f.route().getController().handle(reqeust);
                }
                case PathMatchResult.MethodNotAllowed m -> {
                    return HttpResponse.builder()
                            .status(405)
                            .header("Allow", String.join(", ", m.allowed()))
                            .build();
                }
                case PathMatchResult.NotFound n -> {
                    return HttpResponse.builder()
                            .status(404)
                            .build();
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to handle request: " + e.getMessage());
            return HttpResponse.builder()
                    .status(500)
                    .build();
        }

    }
}
