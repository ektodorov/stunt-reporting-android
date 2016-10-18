package com.blogspot.techzealous.stunt.framework;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class StuntConst {

    private static final String TAG = "StuntConst";

    private static String sApiKey;

    public static final String URL_service = "http://192.168.0.102:8080";
    public static final String URL_echo = URL_service + "/echo";
    public static final String URL_message = URL_service + "/message";
    public static final String URL_uploadimage = URL_service + "/uploadimage";

    public static final int REPORTRATE_INSTANT = 0;
    public static final int REPORTRATE_TIME = 1;
    public static final int REPORTRATE_ON_EVENT_COUNT = 2;

    public static final String STR_Content_Encoding = "Content-Encoding";
    public static final String STR_gzip = "gzip";
    public static final String STR_POST = "POST";
    public static final String STR_Content_Length = "Content-Length";
    public static final String STR_Content_Type = "Content-Type";
    public static final String STR_Authorization = "Authorization";
    public static final String STR_symbol_colon = ":";
    public static final String STR_symbol_carriage_return = "\r";
    public static final String STR_symbol_newline = "\n";
    public static final String STR_symbol_lineFeed = STR_symbol_carriage_return + STR_symbol_newline;
    public static final String STR_symbol_hyphen = "-";
    public static final String STR_boundary = "*************************7d41b838504d8";
    public static final String STR_Connection = "Connection";
    public static final String STR_Keep_Alive = "Keep-Alive";
    public static final String STR_multipart_form_data = "multipart/form-data";
    public static final String STR_multipart_form_data_boundary = "multipart/form-data;boundary=" + STR_boundary;
    public static final String STR_ENCTYPE = "ENCTYPE";
    public static final String STR_application_json_charset_utf8 = "application/json; charset=utf-8";

    public static final String API_KEY = "stunt_api_key";
    public static final String API_KEY_ENABLED = "stunt_enabled";
    public static final String API_KEY_uploadimage = "uploadimage";

    private StuntConst() {
        super();
    }

    public static String getApiKey() {
        return sApiKey;
    }

    public static void setApiKey(String aApiKey) {
        sApiKey = aApiKey;
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

    public static String uploadBitmap(URL aUrl, Bitmap aBitmap) {
        String fileName = "screenshot.png";

        int responseCode = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream bos = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = (HttpURLConnection) aUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(STR_POST);
            conn.setRequestProperty(STR_Connection, STR_Keep_Alive);
            conn.setRequestProperty(STR_ENCTYPE, STR_multipart_form_data);
            conn.setRequestProperty(STR_Content_Type, STR_multipart_form_data_boundary);
            conn.setRequestProperty(API_KEY_uploadimage, fileName);

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_uploadimage + "\";filename=\"" + fileName + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);

            // create a buffer of  maximum size
            bos = new ByteArrayOutputStream();
            aBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bytesBitmap = bos.toByteArray();
            //dos.write(bytesBitmap, 0, bytesBitmap.length);
            dos.write(bytesBitmap);
            // send multipart form data necesssary after file data...
            dos.writeBytes(STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_hyphen + STR_symbol_hyphen + STR_symbol_lineFeed);

            responseCode = conn.getResponseCode();
            Log.i(TAG, "uploadBitmap, responseCode=" + responseCode);
            if(HttpURLConnection.HTTP_OK <= responseCode && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            Log.i(TAG, "uploadBitmap, response=" + sb.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e(TAG, "uploadBitmap, MalformedURLException=" + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "uploadBitmap, Exception=" + e.getMessage(), e);
        } finally {
            try {
                if(dos != null) {
                    dos.flush();
                    dos.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String uploadFile(URL aUrl, String aFilePath) {
        String fileName = "screenshot.png";

        int responseCode = 0;
        HttpURLConnection conn = null;
        FileInputStream fileInputStream = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream bos = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = (HttpURLConnection) aUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(STR_POST);
            conn.setRequestProperty(STR_Connection, STR_Keep_Alive);
            conn.setRequestProperty(STR_ENCTYPE, STR_multipart_form_data);
            conn.setRequestProperty(STR_Content_Type, STR_multipart_form_data_boundary);
            conn.setRequestProperty(API_KEY_uploadimage, fileName);

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_uploadimage + "\";filename=\"" + fileName + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);

            fileInputStream = new FileInputStream(new File(aFilePath));
            byte[] byteBuffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(byteBuffer)) != - 1) {
                dos.write(byteBuffer, 0, bytesRead);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_hyphen + STR_symbol_hyphen + STR_symbol_lineFeed);

            responseCode = conn.getResponseCode();
            Log.i(TAG, "uploadFile, responseCode=" + responseCode);
            if(HttpURLConnection.HTTP_OK <= responseCode && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            Log.i(TAG, "uploadFile, response=" + sb.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e(TAG, "uploadFile, MalformedURLException=" + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "uploadFile, Exception=" + e.getMessage(), e);
        } finally {
            try {
                if(dos != null) {
                    dos.flush();
                    dos.close();
                }
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getResponse(URL url, String aMessage) throws IOException {
        HttpURLConnection urlConnection = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        IOException ioException = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(STR_POST);
            urlConnection.setDoInput(true);
            if(aMessage != null) {urlConnection.setDoOutput(true);}
            urlConnection.addRequestProperty(STR_Content_Type, STR_application_json_charset_utf8);
            urlConnection.addRequestProperty(STR_Authorization, sApiKey);

            if(aMessage != null) {
                urlConnection.addRequestProperty(STR_Content_Length, Integer.toString(aMessage.getBytes().length));
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
                dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                //dataOutputStream.writeBytes(body);
                dataOutputStream.write(aMessage.getBytes());
                dataOutputStream.flush();
            } else {
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
            }

            Log.i(TAG, "url=" + url);
            Log.i(TAG, "body=" + aMessage);

            int responseCode = urlConnection.getResponseCode();
            if(HttpURLConnection.HTTP_OK <= responseCode && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            }
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            ioException = e;
        } finally {
            if(dataOutputStream != null) {
                try {dataOutputStream.close();} catch (IOException e) {/* do nothing */}
            }
            if(bufferedReader != null) {
                try {bufferedReader.close();} catch (IOException e) {/* do nothing */}
            }
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(ioException != null) {
                throw ioException;
            }
        }
        String strResponse = sb.toString();
        Log.i(TAG, "response=" + strResponse);
        return strResponse;
    }

    public static String getRequestHeaders(HttpURLConnection connection) {
        StringBuilder headers = new StringBuilder();
        Map<String, List<String>> mapHeaders = connection.getRequestProperties();
        for(String header : mapHeaders.keySet()) {
            if(header != null) {
                for(String value : mapHeaders.get(header)) {
                    headers.append(header).append(STR_symbol_colon).append(value).append(STR_symbol_newline);
                }
            }
        }
        return headers.toString();
    }
}
