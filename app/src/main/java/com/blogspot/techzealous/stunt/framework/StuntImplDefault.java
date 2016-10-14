package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

public class StuntImplDefault implements StuntInterface {

    private boolean mIsLoggingEnabled = true;

    public StuntImplDefault() {
        super();
    }

    /* StuntInterface */
//    connection.setRequestProperty("Content-Encoding", "gzip");
    @Override
    public void report(String aString) {

    }

    @Override
    public void report(Bitmap aBitmap) {

    }

    @Override
    public void reportFile(String aFilePath) {

    }

    @Override
    public void report(File aFile) {

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
    public void log(String aTag, String aMessage, Bitmap aBitmap) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aBitmap);
    }

    @Override
    public void log(String aTag, String aMessage, String aFilePath) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aFilePath);
    }

    @Override
    public void log(String aTag, String aMessage, File aFile) {
        if(mIsLoggingEnabled) {
            Log.i(aTag, aMessage);
        }
        report(aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aMessage);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aBitmap);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, File aFile) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        report(aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath) {
        if(mIsLoggingEnabled) {
            Log.println(aLogLevel, aTag, aMessage);
        }
        reportFile(aFilePath);
    }


}
