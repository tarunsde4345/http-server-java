package middlewares;

import http.HttpRequest;
import http.HttpResponse;

public interface Middleware {
    void handle(HttpRequest request, HttpResponse response, MiddlewareChain chain);
}
