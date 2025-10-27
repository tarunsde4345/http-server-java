package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class RootController implements Controller {

    @Override
    public HttpResponse get(HttpRequest reqeust) {
        return HttpResponse.builder()
                .text("Welcome to the Home Page!")
                .build();
    }
}
