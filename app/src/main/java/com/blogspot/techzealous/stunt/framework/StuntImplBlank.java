package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;

import java.io.File;

public class StuntImplBlank implements StuntInterface {

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

    }

    @Override
    public boolean getLoggingEnabled() {
        return false;
    }

    @Override
    public void log(String aTag, String aMessage) {

    }

    @Override
    public void log(String aTag, String aMessage, Bitmap aBitmap) {

    }

    @Override
    public void log(String aTag, String aMessage, String aFilePath) {

    }

    @Override
    public void log(String aTag, String aMessage, File aFile) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, File aFile) {

    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath) {

    }

}