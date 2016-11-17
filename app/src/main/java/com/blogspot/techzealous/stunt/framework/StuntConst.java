package com.blogspot.techzealous.stunt.framework;

import android.graphics.Bitmap;
import android.util.Log;

import com.blogspot.techzealous.stunt.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class StuntConst {

    private static WeakReference<MainActivity> mWeakActivity;
    private static final String TAG = "StuntConst";

    //Currently it is not used. Client is authenticated with the ApiKey, in order to skip registration and getting a token.
    //In future we may switch to token authentication.
    private static String sAuthToken;
    private static String sApiKey;
    private static String sClientId;
    private static long sSequence;

    public static String URL_service = "http://192.168.0.102:8080";
    private static String URL_message = "/message";
    private static String URL_clientinfo = "/sendclientinfo";
    private static String URL_uploadimage = "/uploadimage";
    private static String URL_uploadfile = "/uploadfile";

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
    public static final String STR_symbol_forward_slash = "/";
    public static final String STR_boundary = "*************************7d41b838504d8";
    public static final String STR_Connection = "Connection";
    public static final String STR_Keep_Alive = "Keep-Alive";
    public static final String STR_multipart_form_data = "multipart/form-data";
    public static final String STR_multipart_form_data_boundary = "multipart/form-data;boundary=" + STR_boundary;
    public static final String STR_ENCTYPE = "ENCTYPE";
    public static final String STR_application_json_charset_utf8 = "application/json; charset=utf-8";
    public static final String STR_Change_service_url = "Change service URL";

    public static final String API_KEY = "apikey";
    public static final String API_KEY_STUNT_API_KEY = "stunt_api_key";
    public static final String API_KEY_ENABLED = "stunt_enabled";
    public static final String API_KEY_image = "image";
    public static final String API_KEY_file = "file";
    public static final String API_KEY_sequence = "sequence";
    public static final String API_KEY_time = "time";
    public static final String API_KEY_message = "message";
    public static final String API_KEY_clientid = "clientid";
    public static final String API_KEY_clientinfo_name = "name";
    public static final String API_KEY_clientinfo_manufacturer = "manufacturer";
    public static final String API_KEY_clientinfo_model = "model";
    public static final String API_KEY_clientinfo_deviceid = "deviceid";

    private StuntConst() {
        super();
    }

    public static String getAuthToken() {
        return sAuthToken;
    }

    public static void setAuthToken(String aAuthToken) {
        sAuthToken = aAuthToken;
    }

    public static String getApiKey() {
        return sApiKey;
    }

    public static void setApiKey(String aApiKey) {
        sApiKey = aApiKey;
    }

    public static String getClientId() {
        return sClientId;
    }

    public static void setClientId(String aClientId) {
        sClientId = aClientId;
    }

    public static long getSequence() {
        return sSequence;
    }

    public static String getUrlMessage() {
        return URL_service + URL_message;
    }

    public static String getUrlClientInfo() {
        return URL_service + URL_clientinfo;
    }

    public static String getUrlUploadImage() {
        return URL_service + URL_uploadimage;
    }

    public static String getUrlUploadFile() {
        return URL_service + URL_uploadfile;
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

    public static String uploadBitmap(URL aUrl, String aMessage, Bitmap aBitmap, String aFileName) {
        String fileName = aFileName;
        if(fileName == null) {
            fileName = aBitmap.getWidth() + "x" + aBitmap.getHeight() + ".png";
        }

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
            conn.addRequestProperty(STR_Authorization, sAuthToken);

            //write message
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_message + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);
            sSequence++;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put(API_KEY, sApiKey);
            jsonBody.put(API_KEY_sequence, sSequence);
            jsonBody.put(API_KEY_time, (System.currentTimeMillis() / 1000));
            jsonBody.put(API_KEY_message, aMessage);
            jsonBody.put(API_KEY_clientid, sClientId);
            String strMessage = jsonBody.toString();
            dos.writeBytes(strMessage);
            dos.writeBytes(STR_symbol_lineFeed);

            //write bitmap
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_image + "\";filename=\"" + fileName + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);
            bos = new ByteArrayOutputStream();
            aBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bytesBitmap = bos.toByteArray();
            //dos.write(bytesBitmap, 0, bytesBitmap.length);
            dos.write(bytesBitmap);
            dos.writeBytes(STR_symbol_lineFeed);

            // send multipart form data necessary at end of message
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

    public static String uploadFile(URL aUrl, String aMessage, String aFilePath, String aFileName) {
        String fileName = aFileName;
        if(fileName == null) {
            int index = aFilePath.lastIndexOf(File.pathSeparator) + 1;
            fileName = aFilePath.substring(index);
        }

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
            conn.addRequestProperty(STR_Authorization, sAuthToken);

            //write message
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_message + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);
            sSequence++;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put(API_KEY, sApiKey);
            jsonBody.put(API_KEY_sequence, sSequence);
            jsonBody.put(API_KEY_time, (System.currentTimeMillis() / 1000));
            jsonBody.put(API_KEY_message, aMessage);
            jsonBody.put(API_KEY_clientid, sClientId);
            String strMessage = jsonBody.toString();
            dos.writeBytes(strMessage);
            dos.writeBytes(STR_symbol_lineFeed);

            //write file
            dos.writeBytes(STR_symbol_hyphen + STR_symbol_hyphen + STR_boundary + STR_symbol_lineFeed);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + API_KEY_file + "\";filename=\"" + fileName + "\"" + STR_symbol_lineFeed);
            dos.writeBytes(STR_symbol_lineFeed);
            fileInputStream = new FileInputStream(new File(aFilePath));
            byte[] byteBuffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(byteBuffer)) != - 1) {
                dos.write(byteBuffer, 0, bytesRead);
            }
            dos.writeBytes(STR_symbol_lineFeed);

            // send multipart form data necessary at end of message
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
        String strBody = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(STR_POST);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.addRequestProperty(STR_Content_Type, STR_application_json_charset_utf8);
            urlConnection.addRequestProperty(STR_Authorization, sAuthToken);

            if(aMessage != null) {
                sSequence++;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put(API_KEY, sApiKey);
                jsonBody.put(API_KEY_sequence, sSequence);
                jsonBody.put(API_KEY_time, (System.currentTimeMillis() / 1000));
                jsonBody.put(API_KEY_message, aMessage);
                jsonBody.put(API_KEY_clientid, sClientId);
                strBody = jsonBody.toString();

                urlConnection.addRequestProperty(STR_Content_Length, Integer.toString(strBody.getBytes().length));
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
                dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.writeBytes(strBody);
                //dataOutputStream.write(aMessage.getBytes());
                dataOutputStream.flush();
            } else {
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
            }

            Log.i(TAG, "url=" + url);
            Log.i(TAG, "body=" + strBody);

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
        } catch (JSONException e) {
            e.printStackTrace();
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

    public static String getResponse(URL url, JSONObject aJsonBody) throws IOException {
        HttpURLConnection urlConnection = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        IOException ioException = null;
        String strBody = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(STR_POST);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.addRequestProperty(STR_Content_Type, STR_application_json_charset_utf8);
            urlConnection.addRequestProperty(STR_Authorization, sAuthToken);

            if(aJsonBody != null) {
                sSequence++;
                strBody = aJsonBody.toString();

                urlConnection.addRequestProperty(STR_Content_Length, Integer.toString(strBody.getBytes().length));
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
                dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.writeBytes(strBody);
                //dataOutputStream.write(aMessage.getBytes());
                dataOutputStream.flush();
            } else {
                Log.i(TAG, "Headers=\n" + getRequestHeaders(urlConnection));
            }

            Log.i(TAG, "url=" + url);
            Log.i(TAG, "body=" + strBody);

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

    public static void setMainActivity(MainActivity aActivity) {
        mWeakActivity = new WeakReference<MainActivity>(aActivity);
    }

    public static MainActivity getMainActivity() {
        return mWeakActivity.get();
    }
}
