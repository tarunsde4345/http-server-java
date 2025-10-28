package http;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ImmutableHttpResponse {
    private final String httpVersion;
    private final int statusCode;
    private final String reasonPhrase;
    private final Map<String, String> headers;
    private final byte[] body;

    public ImmutableHttpResponse(HttpResponse response) {
        this.httpVersion = response.getHttpVersion();
        this.statusCode = response.getStatusCode();
        this.reasonPhrase = response.getReasonPhrase();
        this.headers = Map.copyOf(response.getHeaders());
        this.body = response.getBody() != null ? response.getBody() : new byte[0];
    }

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

    @Override
    public String toString() {
        return "HttpResponse: " +
                httpVersion + " " + statusCode + " " + reasonPhrase +
                "\n headers=" + headers +
                "\n body=" + body.length + " bytes" +
                (body.length > 0 ? ", preview='" + previewBody() + "'" : "");
    }

    private String previewBody() {
        if (body.length == 0) return "";
        try {
            String text = new String(body, StandardCharsets.UTF_8);
            return text.length() > 100
                    ? text.substring(0, 97) + "..."
                    : text;
        } catch (Exception e) {
            return "[binary or invalid UTF-8]";
        }
    }
}
