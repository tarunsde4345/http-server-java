package constants;

public final class HttpConstants {

    private HttpConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final class Methods {
        private Methods() { throw new UnsupportedOperationException(); }

        public static final String GET     = "GET";
        public static final String POST    = "POST";
        public static final String PUT     = "PUT";
        public static final String DELETE  = "DELETE";
        public static final String HEAD    = "HEAD";
        public static final String OPTIONS = "OPTIONS";
        public static final String PATCH   = "PATCH";
        public static final String TRACE   = "TRACE";
        public static final String CONNECT = "CONNECT";
    }

    public static final class Headers {
        private Headers() { throw new UnsupportedOperationException(); }

        public static final String CONTENT_TYPE        = "Content-Type";
        public static final String CONTENT_LENGTH      = "Content-Length";
        public static final String ACCEPT              = "Accept";
        public static final String ACCEPT_ENCODING     = "Accept-Encoding";
        public static final String CONTENT_ENCODING    = "Content-Encoding";
        public static final String VARY                = "Vary";
        public static final String HOST                = "Host";
        public static final String CONNECTION          = "Connection";
        public static final String USER_AGENT          = "User-Agent";
        public static final String AUTHORIZATION       = "Authorization";
        public static final String CACHE_CONTROL       = "Cache-Control";
        public static final String TRANSFER_ENCODING   = "Transfer-Encoding";
        public static final String LOCATION            = "Location";
        public static final String SET_COOKIE          = "Set-Cookie";
    }

    public static final class MimeTypes {
        private MimeTypes() { throw new UnsupportedOperationException(); }

        public static final String TEXT_PLAIN          = "text/plain";
        public static final String TEXT_HTML           = "text/html";
        public static final String TEXT_CSS            = "text/css";
        public static final String APPLICATION_JSON    = "application/json";
        public static final String APPLICATION_XML     = "application/xml";
        public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
        public static final String IMAGE_PNG           = "image/png";
        public static final String IMAGE_JPEG          = "image/jpeg";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";

        /** Helper to build a full Content-Type with charset */
        public static String jsonUtf8()   { return APPLICATION_JSON + "; charset=utf-8"; }
        public static String htmlUtf8()   { return TEXT_HTML    + "; charset=utf-8"; }
        public static String plainUtf8()  { return TEXT_PLAIN   + "; charset=utf-8"; }
    }

    public static final class Misc {
        private Misc() { throw new UnsupportedOperationException(); }

        public static final String CHARSET_UTF_8 = "UTF-8";
        public static final String CRLF          = "\r\n";
        public static final String EMPTY_STRING  = "";
    }
}
