package http;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpResponseWriter {

    private HttpResponseWriter() {}

    public static void write(ImmutableHttpResponse response, OutputStream out) throws IOException {
        // Write status line + headers
        ByteArrayOutputStream headerBuffer = new ByteArrayOutputStream(256);

        try (var headerWriter = new OutputStreamWriter(headerBuffer, StandardCharsets.US_ASCII);
             var pw = new PrintWriter(headerWriter, false)) {

            pw.printf("%s %d %s\r\n", response.getHttpVersion(), response.getStatusCode(), response.getReasonPhrase());
            for (Map.Entry<String, String> h : response.getHeaders().entrySet()) {
                pw.printf("%s: %s\r\n", h.getKey(), h.getValue());
            }
            pw.print("\r\n");
            pw.flush(); // push headers
        }

        // Write body (zero copy)
        out.write(headerBuffer.toByteArray());
        if (response.getBody().length > 0) {
            out.write(response.getBody());
        }
        out.flush();
        System.out.println(response.toString());
    }

}
