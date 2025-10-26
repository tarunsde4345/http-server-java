package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class UserAgentController implements Controller {

    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponse.builder()
                .status(200)
                .text(request.getHeaders().getOrDefault("User-Agent", "User-Agent not found"))
                .build();
    }
}
