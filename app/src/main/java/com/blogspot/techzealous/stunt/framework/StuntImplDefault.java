package com.blogspot.techzealous.stunt.framework;


import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
    public void report(Bitmap aBitmap) {
        try {
            URL url = new URL(StuntConst.URL_uploadimage);
            String strResponse = StuntConst.uploadBitmap(url, aBitmap);

//            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "temp.png");
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(file.getAbsolutePath());
//                aBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                Log.i(TAG, "saved bitmap, file.absolutePath=" + file.getAbsolutePath());
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            URL url = new URL("http://192.168.0.102:8080/uploadimage");
//            String strResponse = StuntConst.uploadFile(url, file.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportFile(String aFilePath) {
        try {
            URL url = new URL(StuntConst.URL_uploadfile);
            String strResponse = StuntConst.uploadFile(url, aFilePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void report(File aFile) {
        try {
            URL url = new URL(StuntConst.URL_uploadfile);
            String strResponse = StuntConst.uploadFile(url, aFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
