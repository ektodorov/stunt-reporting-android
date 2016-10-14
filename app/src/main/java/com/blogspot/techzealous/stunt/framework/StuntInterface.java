package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;

import java.io.File;

public interface StuntInterface {

    public void report(String aString);
    public void report(Bitmap aBitmap);
    public void reportFile(String aFilePath);
    public void report(File aFile);

    public void setLoggingEnabled(boolean aIsEnabled);
    public boolean getLoggingEnabled();
    public void log(String aTag, String aMessage);
    public void log(String aTag, String aMessage, Bitmap aBitmap);
    public void log(String aTag, String aMessage, String aFilePath);
    public void log(String aTag, String aMessage, File aFile);
    public void log(int aLogLevel, String aTag, String aMessage);
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap);
    public void log(int aLogLevel, String aTag, String aMessage, File aFile);
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath);

}
