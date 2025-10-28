package middlewares;

import http.HttpRequest;
import http.HttpResponse;

import java.util.List;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private int index = 0;

    public MiddlewareChain(List<Middleware> middlewares) {
        this.middlewares = middlewares;
    }

    public void next(HttpRequest request, HttpResponse response) {
        if (index < middlewares.size()) {
            Middleware current = middlewares.get(index++);
            current.handle(request, response, this);
        }
    }
}
