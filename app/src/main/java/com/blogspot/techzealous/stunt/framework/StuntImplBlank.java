package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;

import java.io.File;

public class StuntImplBlank implements StuntInterface {

    @Override
    public void report(String aString) {

    }

    @Override
    public void report(String aMessage, Bitmap aBitmap, String aFileName) {

    }

    @Override
    public void reportFile(String aMessage, String aFilePath, String aFileName) {

    }

    @Override
    public void report(String aMessage, File aFile) {

    }

    @Override
    public void setLoggingEnabled(boolean aIsEnabled) {

    }

    @Override
    public boolean getLoggingEnabled() {
        return false;
    }

    @Override
    public void log(String aTag, String aMessage) {

    }

    @Override
    public void log(String aTag, String aMessage, Bitmap aBitmap, String aFileName) {

    }

    @Override
    public void log(String aTag, String aMessage, String aFilePath, String aFileName) {

    }

    @Override
    public void log(String aTag, String aMessage, File aFile) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap, String aFileName) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, File aFile) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath, String aFileName) {

    }

}
