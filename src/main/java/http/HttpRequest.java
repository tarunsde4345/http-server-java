package http;

import java.util.Collections;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;

    public HttpRequest(String method, String path, String httpVersion,
                       Map<String, String> headers,
                       Map<String, String> queryParams,
                       String body) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers != null ? Map.copyOf(headers) : Collections.emptyMap();
        this.queryParams = queryParams != null ? Map.copyOf(queryParams) : Collections.emptyMap();
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return method + " "+ path + " " + httpVersion +
                "\n headers: " + headers +
                "\n queryParams: " + queryParams +
                "\n body: \n" + body;
    }
}
