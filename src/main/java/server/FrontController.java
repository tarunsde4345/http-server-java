package server;

import controllers.Controller;
import http.HttpRequest;
import http.HttpResponse;

public class FrontController {
    //gets message, has router to help decide where to send it
    private final Router router;

    public FrontController(Router router) {
        this.router = router;
    }

    public HttpResponse handleRequest(HttpRequest reqeust) {
        try {
            Controller controller = router.resolve(reqeust.getPath()); //method should also be considered

            if (controller == null) {
                return new HttpResponse(404, "Not Found");
            }
            return controller.handle(reqeust);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to handle request: " + e.getMessage());
            return new HttpResponse(500, "Internal Server Error");
        }

    }
}
