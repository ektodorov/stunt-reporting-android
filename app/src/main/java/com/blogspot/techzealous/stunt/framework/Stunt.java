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
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Stunt extends Object implements StuntInterface {

    private static final String TAG = "Stunt";

    private static Stunt mInstance;
    private static StuntInterface mImpl;
    private static boolean mIsReportingEnabled = true;

    private WeakReference<Activity> mWeakContext;
    private ExecutorService mExecutorService;
    private Handler mHandlerUI;
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
            StuntConst.setApiKey(bundle.getString(StuntConst.API_KEY_STUNT_API_KEY));
            mIsReportingEnabled = bundle.getBoolean(StuntConst.API_KEY_ENABLED);
            StuntConst.setClientId(UUID.randomUUID().toString());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound=" + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer=" + e.getMessage());
        }

        if(mIsReportingEnabled) {
            if(mImpl == null || mImpl instanceof StuntImplBlank) {
                mImpl = new StuntImplDefault();
            }
        } else {
            if(mImpl == null || mImpl instanceof StuntImplDefault) {
                mImpl = new StuntImplBlank();
            }
        }
    }

    public static synchronized Stunt getInstance(Activity aActivity) {
        if(mInstance == null) {
            mInstance = new Stunt(aActivity);
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

    public String getApiKey() {
        return StuntConst.getApiKey();
    }

    public void setApiKey(String aApiKey) {
        StuntConst.setApiKey(aApiKey);
    }

    /* StuntInterface */
    @Override
    public void report(final String aString) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mImpl.report(aString);
            }
        });
    }

    @Override
    public void report(final String aMessage, final Bitmap aBitmap, final String aFileName) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mImpl.report(aMessage, aBitmap, aFileName);
            }
        });
    }

    @Override
    public void reportFile(final String aMessage, final String aFilePath, final String aFileName) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mImpl.reportFile(aMessage, aFilePath, aFileName);
            }
        });
    }

    @Override
    public void report(final String aMessage, final File aFile) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mImpl.report(aMessage, aFile);
            }
        });
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
        mImpl.log(aTag, aMessage);
    }

    @Override
    public void log(String aTag, String aMessage, Bitmap aBitmap, String aFileName) {
        mImpl.log(aTag, aMessage, aBitmap, aFileName);
    }

    @Override
    public void log(String aTag, String aMessage, String aFilePath, String aFileName) {
        mImpl.log(aTag, aMessage, aFilePath, aFileName);
    }

    @Override
    public void log(String aTag, String aMessage, File aFile) {
        mImpl.log(aTag, aMessage, aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage) {
        mImpl.log(aLogLevel, aTag, aMessage);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, Bitmap aBitmap, String aFileName) {
        mImpl.log(aLogLevel, aTag, aMessage, aBitmap, aFileName);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, File aFile) {
        mImpl.log(aLogLevel, aTag, aMessage, aFile);
    }

    @Override
    public void log(int aLogLevel, String aTag, String aMessage, String aFilePath, String aFileName) {
        mImpl.log(aLogLevel, aTag, aMessage, aFilePath, aFileName);
    }

}
