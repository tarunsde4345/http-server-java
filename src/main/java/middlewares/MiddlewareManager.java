package middlewares;

import http.HttpRequest;
import http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class MiddlewareManager {
    private final List<Middleware> middlewares = new ArrayList<>();

    public void use(Middleware middleware) {
        middlewares.add(middleware);
    }

    public void execute(HttpRequest request, HttpResponse response) {
        MiddlewareChain chain = new MiddlewareChain(middlewares);
        chain.next(request, response);
    }
}
