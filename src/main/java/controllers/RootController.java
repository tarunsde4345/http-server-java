package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class RootController implements Controller {

    @Override
    public void get(HttpRequest request, HttpResponse response) {
        response.text("Welcome to the Home Page!");
    }
}
