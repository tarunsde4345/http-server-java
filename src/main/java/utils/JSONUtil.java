package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for JSON serialization.
 * Thread-safe. Uses Jackson (industry standard).
 */
public final class JSONUtil {

    // One shared, thread-safe mapper
    private static final ObjectMapper MAPPER = createObjectMapper();

    private JSONUtil() {
        // Prevent instantiation
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Support Java 8+ time types (LocalDateTime, etc.)
        mapper.registerModule(new JavaTimeModule());
        // Do not write dates as timestamps
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // Optional: pretty print in dev (disable in prod)
        // mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        return mapper;
    }

    /**
     * Serializes any object to UTF-8 JSON bytes.
     *
     * @param obj the object to serialize (POJO, Map, List, String, etc.)
     * @return JSON as byte array (UTF-8)
     * @throws RuntimeException if serialization fails
     */
    public static byte[] writeValueAsBytes(Object obj) {
        if (obj == null) {
            return new byte[0]; // or throw? up to you
        }
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON: " + obj.getClass().getName(), e);
        }
    }

    /**
     * Serializes object to JSON string (for logging/debugging).
     */
    public static String writeValueAsString(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON string", e);
        }
    }

    // Optional: Get the mapper (for advanced use)
    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
