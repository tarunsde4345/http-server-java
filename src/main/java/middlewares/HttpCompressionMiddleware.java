package middlewares;

import constants.HttpConstants;
import http.HttpRequest;
import http.HttpResponse;
import utils.CompressionUtil;

import java.io.IOException;

public class HttpCompressionMiddleware implements Middleware {
    public void handle(HttpRequest reqeust, HttpResponse response, MiddlewareChain chain) {
        if (response.getBody() == null || response.getBody().length == 0) {
            return;
        }
        String acceptEncoding = reqeust.getHeader(HttpConstants.Headers.ACCEPT_ENCODING);

        ContentEncoding encoding = determineEncoding(acceptEncoding);

        try {
            byte[] compressed = CompressionUtil.compress(response.getBody(), encoding);
            response.body(compressed)
                    .header(HttpConstants.Headers.CONTENT_ENCODING, encoding.headerValue())
                    .header(HttpConstants.Headers.VARY, HttpConstants.Headers.ACCEPT_ENCODING);
        } catch (IOException e) {
            e.printStackTrace(); // or log and fallback silently
        }
        chain.next(reqeust, response);
    }

    public ContentEncoding determineEncoding(String acceptEncoding) {
        if (acceptEncoding == null || acceptEncoding.isEmpty()) {
            return ContentEncoding.NONE;
        } else if (acceptEncoding.contains(ContentEncoding.GZIP.headerValue())) {
            return ContentEncoding.GZIP;
        } else if (acceptEncoding.contains(ContentEncoding.DEFLATE.headerValue())) {
            return ContentEncoding.DEFLATE;
        } else {
            return ContentEncoding.NONE;
        }
    }


    public enum ContentEncoding {
        NONE(""),
        GZIP("gzip"),
        DEFLATE("deflate");

        private final String headerValue;

        ContentEncoding(String headerValue) {
            this.headerValue = headerValue;
        }

        public String headerValue() {
            return headerValue;
        }
    }
}

