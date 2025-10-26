package utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HttpDateFormatUtil {
    private static final DateTimeFormatter HTTP_DATE = DateTimeFormatter
            .ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
            .withZone(ZoneOffset.UTC);

    public static String format() {
        return HTTP_DATE.format(ZonedDateTime.now(ZoneOffset.UTC));
    }
}
