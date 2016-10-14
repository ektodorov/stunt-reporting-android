package com.blogspot.techzealous.stunt.framework;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class StuntConst {

    public static final int REPORTRATE_INSTANT = 0;
    public static final int REPORTRATE_TIME = 1;
    public static final int REPORTRATE_ON_EVENT_COUNT = 2;

    public static final String STR_Content_Encoding = "Content-Encoding";
    public static final String STR_gzip = "gzip";

    public static final String API_KEY = "stunt_api_key";
    public static final String API_KEY_ENABLED = "stunt_enabled";

    private StuntConst() {
        super();
    }

    public static ByteArrayOutputStream gzip(byte[] input) throws Exception {
        GZIPOutputStream gzipOS = null;
        ByteArrayOutputStream byteArrayOS = null;
        try {
            byteArrayOS = new ByteArrayOutputStream();
            gzipOS = new GZIPOutputStream(byteArrayOS);
            gzipOS.write(input);
            gzipOS.flush();
            gzipOS.close();
            gzipOS = null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (gzipOS != null) {
                try { gzipOS.close(); } catch (Exception ignored) {}
            }
        }
        return byteArrayOS;
    }
}
