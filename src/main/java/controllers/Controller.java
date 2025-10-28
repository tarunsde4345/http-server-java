package controllers;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    default void get(HttpRequest request, HttpResponse response) {
        response.status(405);
    }

    default void post(HttpRequest request, HttpResponse response) {
        response.status(405);
    }

    default void put(HttpRequest request, HttpResponse response) {
        response.status(405);
    }

    default void delete(HttpRequest request, HttpResponse response) {
        response.status(405);
    }

    default void handle(HttpRequest request, HttpResponse response) {
        // dispatcher within handler
        switch (request.getMethod()) {
            case "GET" -> get(request, response);
            case "POST" -> post(request, response);
            case "PUT" -> put(request, response);
            case "DELETE" -> delete(request, response);
            default -> response.status(405);
        };
    }
}
