package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = reader.readLine();

        // parse request line
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("Raw request cannot be null or empty");
        }

        String[] requestLineParts = requestLine.split(" ");
        if (requestLineParts.length != 3) {
            throw new IllegalArgumentException("Invalid HTTP request line");
        }
        String method = requestLineParts[0];
        String fullPath = requestLineParts[1];
        String httpVersion = requestLineParts[2];

        //parse query params
        String path;
        Map<String, String> queryParams = new HashMap<>();

        int queryIndex = fullPath.indexOf("?");
        if (queryIndex != -1) {
            path = fullPath.substring(0, queryIndex);
            String queryString = fullPath.substring(queryIndex + 1);
            queryParams = parseQueryParams(queryString);
        } else {
            path = fullPath;
        }

        //Parse Request Headers
        Map<String, String> headers = new HashMap<>();
        String line;
        while((line = reader.readLine()) != null && !line.isEmpty()) {
            int separatorIndex = line.indexOf(":");
            if (separatorIndex > 0) {
                String headerName = line.substring(0, separatorIndex).trim();
                String headerValue = line.substring(separatorIndex + 1).trim();
                headers.put(headerName, headerValue);
            }
        }

        String body = "";
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] bodyChars = new char[contentLength];
            int totalRead = 0;
            while(totalRead < contentLength) {
                int bytesRead = reader.read(bodyChars, totalRead, contentLength - totalRead);
                if (bytesRead == -1) {
                    break; // End of stream
                }
                totalRead += bytesRead;
            }
            body = new String(bodyChars, 0, totalRead);
        } else {
            StringBuilder bodyBuilder = new StringBuilder();
            while (reader.ready() && (line = reader.readLine()) != null) {
                bodyBuilder.append(line).append("\n");
            }
            body = bodyBuilder.toString().trim();
        }
        return new HttpRequest(method, path, httpVersion, headers, queryParams, body);
    }

    private static Map<String, String> parseQueryParams(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                queryParams.put(key, value);
            } else if (!pair.isEmpty()) {
                queryParams.put(URLDecoder.decode(pair, StandardCharsets.UTF_8), "");
            }
        }
        return queryParams;
    }
}
