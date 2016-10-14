package com.blogspot.techzealous.stunt.framework;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Stunt extends Object implements StuntInterface {

    private static final String TAG = "Stunt";

    private Stunt mInstance;
    private StuntInterface mImpl;
    private WeakReference<Activity> mWeakContext;
    private ExecutorService mExecutorService;
    private Handler mHandlerUI;
    private boolean mIsReportingEnabled;
    private String mApiKey;
    private int mReportRate;
    private int mReportRateMilliseconds;
    private int mReportRateEventCount;

    private Stunt() {
        super();
    }

    private Stunt(Activity aActivity) {
        super();
        mWeakContext = new WeakReference<Activity>(aActivity);
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandlerUI = new Handler(Looper.getMainLooper());

        try {
            ApplicationInfo ai = aActivity.getPackageManager().getApplicationInfo(aActivity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            mApiKey = bundle.getString(StuntConst.API_KEY);
            mIsReportingEnabled = bundle.getBoolean(StuntConst.API_KEY_ENABLED);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound=" + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer=" + e.getMessage());
        }
    }

    public synchronized Stunt getInstance(Activity aActivity) {
        if(mIsReportingEnabled) {
            if(mImpl == null || mImpl instanceof StuntImplBlank) {
                mImpl = new StuntImplDefault();
            }
        } else {
            if(mImpl == null || mImpl instanceof StuntImplDefault) {
                mImpl = new StuntImplBlank();
            }
        }
        return mInstance;
    }

    public synchronized void setReportingEnabled(boolean aIsReportingEnabled) {
        if(aIsReportingEnabled) {
            if(mImpl == null || mImpl instanceof StuntImplBlank) {
                mImpl = new StuntImplDefault();
            }
        } else {
            if(mImpl == null || mImpl instanceof StuntImplDefault) {
                mImpl = new StuntImplBlank();
            }
        }
    }

    public void reportRate(int aRate, int aMilliseconds, int aEventCount) {
        mReportRate = aRate;
        if(StuntConst.REPORTRATE_INSTANT == aRate) {
            mReportRateMilliseconds = 0;
            mReportRateEventCount = 0;
        } else if(StuntConst.REPORTRATE_TIME == aRate) {
            mReportRateEventCount = 0;
        } else if(StuntConst.REPORTRATE_ON_EVENT_COUNT == aRate) {
            mReportRateMilliseconds = 0;
        }
    }

    public String getApiKey(String aApiKey) {
        return mApiKey;
    }

    /* StuntInterface */
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
        mImpl.setLoggingEnabled(aIsEnabled);
    }

    @Override
    public boolean getLoggingEnabled() {
        return mImpl.getLoggingEnabled();
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
