package controllers;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    HttpResponse handle(HttpRequest reqeust);
}
