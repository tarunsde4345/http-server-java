package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class UserAgentController implements Controller {

    @Override
    public void get(HttpRequest request, HttpResponse response) {
        response.status(200)
                .text(request.getHeaders().getOrDefault("User-Agent", "User-Agent not found"));
    }
}
