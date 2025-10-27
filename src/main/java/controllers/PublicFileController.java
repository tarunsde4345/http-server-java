package controllers;

import http.HttpRequest;
import http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

public class PublicFileController implements Controller {
    private static final Path PUBLIC_DIR = Path.of("public");
    @Override
    public HttpResponse get(HttpRequest request) {
        String path = request.getPath();

        Path filePath = PUBLIC_DIR.resolve(request.getPath().substring(8)).normalize();
        if (filePath.toString().contains("..")) {
            return HttpResponse.builder()
                    .status(400)
                    .build();
        }
        if (!filePath.startsWith(PUBLIC_DIR)) {
            return HttpResponse.builder()
                    .status(403)
                    .text("Forbidden")
                    .build();
        }
        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            return HttpResponse.builder()
                    .status(200)
                    .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(fileBytes)
                    .build();
        } catch (java.io.IOException e) {
            return HttpResponse.builder()
                    .status(404)
                    .text("File Not Found")
                    .build();
        }
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        String path = request.getPath();

        Path filePath = PUBLIC_DIR.resolve(request.getPath().substring(8)).normalize();
        if (filePath.toString().contains("..")) {
            return HttpResponse.builder()
                    .status(400)
                    .build();
        }
        if (!filePath.startsWith(PUBLIC_DIR)) {
            return HttpResponse.builder()
                    .status(403)
                    .text("Forbidden")
                    .build();
        }
        try {
            Files.writeString(filePath, request.getBody());
            return HttpResponse.builder()
                    .status(201)
                    .build();
        } catch (java.io.IOException e) {
            return HttpResponse.builder()
                    .status(404)
                    .build();
        }
    }
}
