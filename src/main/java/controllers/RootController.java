package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class RootController implements Controller {

    @Override
    public HttpResponse handle(HttpRequest reqeust) {
        return new HttpResponse(200, "Welcome to the Home Page!");
    }
}
