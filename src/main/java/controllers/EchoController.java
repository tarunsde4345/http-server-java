package controllers;

import http.HttpResponse;

public class EchoController implements Controller {
    @Override
    public http.HttpResponse get(http.HttpRequest request) {
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
        return HttpResponse.builder()
                .status(200)
                .text(body)
                .build();
    }
}
