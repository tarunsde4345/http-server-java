package controllers;

import http.HttpRequest;
import http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

public class PublicFileController implements Controller {
    private static final Path PUBLIC_DIR = Path.of("public");
    @Override
    public void get(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        Path filePath = PUBLIC_DIR.resolve(request.getPath().substring(8)).normalize();
        if (filePath.toString().contains("..")) {
            response.status(400);
            return;
        }
        if (!filePath.startsWith(PUBLIC_DIR)) {
            response.status(403)
                    .text("Forbidden");
        }
        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            response.header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(fileBytes);
        } catch (java.io.IOException e) {
            response.status(404)
                    .text("File Not Found");
        }
    }

    @Override
    public void post(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        Path filePath = PUBLIC_DIR.resolve(request.getPath().substring(8)).normalize();
        if (filePath.toString().contains("..")) {
            response.status(400);
            return;
        }
        if (!filePath.startsWith(PUBLIC_DIR)) {
            response.status(403)
                    .text("Forbidden");
            return;
        }
        try {
            Files.writeString(filePath, request.getBody());
            response.status(201);
        } catch (java.io.IOException e) {
            response.status(404);
        }
    }
}
