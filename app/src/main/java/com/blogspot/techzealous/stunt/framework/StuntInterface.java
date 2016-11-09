package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;

import java.io.File;

public interface StuntInterface {

    public void report(String aString);
    public void report(String aMessage, Bitmap aBitmap, String aFileName);
    public void reportFile(String aMessage, String aFilePath, String aFileName);
    public void report(String aMessage, File aFile);

    public void setLoggingEnabled(boolean aIsEnabled);
    public boolean getLoggingEnabled();
    public void log(String aTag, String aMessage);
    public void log(String aTag, String aMessage, Bitmap aBitmap, String aFileName);
    public void log(String aTag, String aMessage, String aFilePath, String aFileName);
    public void log(String aTag, String aMessage, File aFile);
    public void log(int aLogLevel, String aTag, String aMessage);
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap, String aFileName);
    public void log(int aLogLevel, String aTag, String aMessage, File aFile);
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath, String aFileName);

}
