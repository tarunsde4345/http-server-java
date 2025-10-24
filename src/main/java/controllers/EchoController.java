package controllers;

public class EchoController implements Controller {
    @Override
    public http.HttpResponse handle(http.HttpRequest request) {
        String body = "You requested: " + request.getPath();
        return new http.HttpResponse(200, body);
    }
}
