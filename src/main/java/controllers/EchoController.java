package controllers;

import http.HttpRequest;
import http.HttpResponse;

public class EchoController implements Controller {
    @Override
    public void get(HttpRequest request, HttpResponse response) {
        String body = "You requested: " + request.getPath();
        if (request.getQueryParams().get("sleep") != null) {
            try {
                int sleepTime = Integer.parseInt(request.getQueryParams().get("sleep"));
                Thread.sleep(sleepTime);
                body += "\nSlept for " + sleepTime + " milliseconds.";
            } catch (NumberFormatException | InterruptedException e) {
                body += "\nInvalid sleep parameter.";
            }
        }
        response.status(200)
                .text(body);

    }
}
