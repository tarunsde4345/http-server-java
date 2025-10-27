package controllers;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    default HttpResponse get(HttpRequest request) {
        return HttpResponse.builder().status(405).build();
    }

    default HttpResponse post(HttpRequest request) {
        return HttpResponse.builder().status(405).build();
    }

    default HttpResponse put(HttpRequest request) {
        return HttpResponse.builder().status(405).build();
    }

    default HttpResponse delete(HttpRequest request) {
        return HttpResponse.builder().status(405).build();
    }

    default HttpResponse handle(HttpRequest request) {
        // dispatcher within handler
        return switch (request.getMethod()) {
            case "GET" -> get(request);
            case "POST" -> post(request);
            case "PUT" -> put(request);
            case "DELETE" -> delete(request);
            default -> HttpResponse.builder().status(405).build();
        };
    }
}
