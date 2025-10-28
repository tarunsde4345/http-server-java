package utils;

import middlewares.HttpCompressionMiddleware;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;


public class CompressionUtil {

    public static byte[] compress(byte[] data, HttpCompressionMiddleware.ContentEncoding encoding) throws IOException {
        switch (encoding) {
            case GZIP:
                return gzip(data);
            case DEFLATE:
                return deflate(data);
            case NONE:
            default:
                return data;
        }
    }

    private static byte[] gzip(byte[] data) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
            gzip.finish();
            return bos.toByteArray();
        }
    }

    private static byte[] deflate(byte[] data) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DeflaterOutputStream def = new DeflaterOutputStream(bos)) {
            def.write(data);
            def.finish();
            return bos.toByteArray();
        }
    }
}
