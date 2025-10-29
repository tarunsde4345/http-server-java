package http;

import middlewares.HttpCompressionMiddleware;
import middlewares.MiddlewareManager;
import server.FrontController;

public class HttpExchange {
    public final HttpRequest request;
    public final HttpResponse response;
    public final FrontController frontController;

    public HttpExchange(HttpRequest request, HttpResponse response, FrontController frontController) {
        this.request = request;
        this.response = response;
        this.frontController = frontController;
    }

    public void handle() {
        System.out.println("Received request: " + request);
        frontController.handleRequest(request, response);
        MiddlewareManager responseMiddlewareManager = new MiddlewareManager();
        responseMiddlewareManager.use(new HttpCompressionMiddleware());
        responseMiddlewareManager.execute(request, response);
    }
}
