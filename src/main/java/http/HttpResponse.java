package http;

import constants.HttpConstants.Headers;
import constants.ServerConstants;
import utils.HttpDateFormatUtil;
import utils.JSONUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private String httpVersion = "HTTP/1.1";
    private int statusCode = 200;
    private String reasonPhrase = "OK";
    private final Map<String, String> headers = new LinkedHashMap<>();
    private byte[] body = new byte[0];

    private static final Map<Integer, String> REASONS = Map.ofEntries(
            Map.entry(100, "Continue"),
            Map.entry(101, "Switching Protocols"),

            Map.entry(200, "OK"),
            Map.entry(201, "Created"),
            Map.entry(202, "Accepted"),
            Map.entry(204, "No Content"),
            Map.entry(206, "Partial Content"),

            Map.entry(300, "Multiple Choices"),
            Map.entry(301, "Moved Permanently"),
            Map.entry(302, "Found"),
            Map.entry(303, "See Other"),
            Map.entry(304, "Not Modified"),
            Map.entry(307, "Temporary Redirect"),
            Map.entry(308, "Permanent Redirect"),

            Map.entry(400, "Bad Request"),
            Map.entry(401, "Unauthorized"),
            Map.entry(403, "Forbidden"),
            Map.entry(404, "Not Found"),
            Map.entry(405, "Method Not Allowed"),
            Map.entry(406, "Not Acceptable"),
            Map.entry(408, "Request Timeout"),
            Map.entry(409, "Conflict"),
            Map.entry(410, "Gone"),
            Map.entry(413, "Payload Too Large"),
            Map.entry(414, "URI Too Long"),
            Map.entry(415, "Unsupported Media Type"),
            Map.entry(429, "Too Many Requests"),

            Map.entry(500, "Internal Server Error"),
            Map.entry(501, "Not Implemented"),
            Map.entry(502, "Bad Gateway"),
            Map.entry(503, "Service Unavailable"),
            Map.entry(504, "Gateway Timeout"),
            Map.entry(505, "HTTP Version Not Supported")
    );

    public HttpResponse() {}

    public String getHttpVersion() {
        return httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public HttpResponse version(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public HttpResponse status(int statusCode) {
        this.statusCode = statusCode;
        this.reasonPhrase = reason(statusCode);
        return this;
    }

    public HttpResponse status(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    public HttpResponse header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public HttpResponse contentType(String contentType) {
        return header("Content-Type", contentType);
    }

    public HttpResponse body(String text) {
        this.body = text.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public HttpResponse body(byte[] data) {
        this.body = data != null ? data : new byte[0];
        return this;
    }

    public HttpResponse text(String text) {
        return contentType("text/plain; charset=utf-8")
                .body(text.getBytes(StandardCharsets.UTF_8));
    }

    public HttpResponse json(Object pojo) {
        try {
            byte[] jsonByte = JSONUtil.writeValueAsBytes(pojo);
            return contentType("application/json; charset=utf-8")
                    .body(jsonByte);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    private static String reason(int code) {
        return REASONS.getOrDefault(code, "Unknown Status");
    }


    public ImmutableHttpResponse toImmutable() {
        headers.put(Headers.CONTENT_LENGTH, String.valueOf(body.length));
        headers.putIfAbsent(Headers.DATE, HttpDateFormatUtil.format());
        headers.putIfAbsent(Headers.SERVER, ServerConstants.SERVER_NAME);
        headers.putIfAbsent(Headers.CONNECTION, "keep-alive");
        return new ImmutableHttpResponse(this);
    }
}

