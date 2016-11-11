package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_clientid;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_clientinfo_deviceid;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_clientinfo_manufacturer;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_clientinfo_model;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_clientinfo_name;
import static com.blogspot.techzealous.stunt.framework.StuntConst.API_KEY_sequence;

public class StuntImplDefault implements StuntInterface {

    private static final String TAG = "StuntImplDefault";
    private boolean mIsLoggingEnabled = true;

    public StuntImplDefault() {
        super();
    }

    /* StuntInterface */
//    connection.setRequestProperty("Content-Encoding", "gzip");
    @Override
    public void report(String aString) {
        try {
            URL url = new URL(StuntConst.URL_message);
            String strResponse = StuntConst.getResponse(url, aString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void report(String aMessage, Bitmap aBitmap, String aFileName) {
        try {
            URL url = new URL(StuntConst.URL_uploadimage);
            String strResponse = StuntConst.uploadBitmap(url, aMessage, aBitmap, aFileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportFile(String aMessage, String aFilePath, String aFileName) {
        try {
            URL url = new URL(StuntConst.URL_uploadfile);
            String strResponse = StuntConst.uploadFile(url, aMessage, aFilePath, aFileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void report(String aMessage, File aFile) {
        try {
            URL url = new URL(StuntConst.URL_uploadfile);
            String strResponse = StuntConst.uploadFile(url, aMessage, aFile.getAbsolutePath(), aFile.getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportClientInfo(String aName, String aManufacturer, String aModel, String aDeviceId) {
        try {
            URL url = new URL(StuntConst.URL_clientinfo);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put(API_KEY, StuntConst.getApiKey());
            jsonBody.put(API_KEY_clientid, StuntConst.getClientId());
            jsonBody.put(API_KEY_clientinfo_name, aName);
            jsonBody.put(API_KEY_clientinfo_manufacturer, aManufacturer);
            jsonBody.put(API_KEY_clientinfo_model, aModel);
            jsonBody.put(API_KEY_clientinfo_deviceid, aDeviceId);
            jsonBody.put(API_KEY_sequence, StuntConst.getSequence());
            String strResponse = StuntConst.getResponse(url, jsonBody);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLoggingEnabled(boolean aIsEnabled) {
        mIsLoggingEnabled = aIsEnabled;
    }

    @Override
    public boolean getLoggingEnabled() {
        return mIsLoggingEnabled;
    }

    @Override
    public void log(String aTag, String aMessage) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aMessage);
    }

    @Override
    public void log(String aTag, String aMessage, Bitmap aBitmap, String aFileName) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aMessage, aBitmap, aFileName);
    }

    @Override
    public void log(String aTag, String aMessage, String aFilePath, String aFileName) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        reportFile(aMessage, aFilePath, aFileName);
    }

    @Override
    public void log(String aTag, String aMessage, File aFile) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aMessage, aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aMessage);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap, String aFileName) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aMessage, aBitmap, aFileName);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, File aFile) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aMessage, aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath, String aFileName) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        reportFile(aMessage, aFilePath, aFileName);
    }


}
