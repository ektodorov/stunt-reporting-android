package com.blogspot.techzealous.stunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.techzealous.stunt.framework.Stunt;
import com.blogspot.techzealous.stunt.framework.StuntConst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private View mViewRoot;
    private Toolbar mToolbar;
    private Button mButtonSendReportString;
    private Button mButtonSendReportBitmap;
    private Button mButtonSendReportFile;
    private Button mButtonSendClientInfo;
    private TextView mTextViewTime;

    private AlertDialog mAlertDialogServiceUrl;
    private EditText mEditTextServiceUrl;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewRoot = (View)findViewById(R.id.scrollViewRoot);
        mToolbar = (Toolbar)findViewById(R.id.toolbarMain);
        mButtonSendReportString = (Button)findViewById(R.id.buttonSendReportString);
        mButtonSendReportBitmap = (Button)findViewById(R.id.buttonSendReportBitmap);
        mButtonSendReportFile = (Button)findViewById(R.id.buttonSendReportFile);
        mButtonSendClientInfo = (Button)findViewById(R.id.buttonSendClientInfo);
        mTextViewTime = (TextView)findViewById(R.id.textViewTime);

        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.serviceurlMenuMain) {
                    mAlertDialogServiceUrl.show();
                    return true;
                }
                return false;
            }
        });

        mButtonSendReportString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stunt stunt = Stunt.getInstance(MainActivity.this);
                setTime(System.currentTimeMillis());
                stunt.report("Button send report string was clicked, mCount=" + mCount);
                mCount++;
            }
        });

        mButtonSendReportBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(System.currentTimeMillis());

                Stunt stunt = Stunt.getInstance(MainActivity.this);
                mViewRoot.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(mViewRoot.getDrawingCache());
                mViewRoot.setDrawingCacheEnabled(false);
                stunt.report("Screenshot of MainActivity", bitmap, "screenshot.png");
            }
        });

        mButtonSendReportFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileText = new File(Environment.getExternalStorageDirectory(), "text.txt");
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(fileText);
                    pw.println("Button send report file was clicked, mCount=" + mCount);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if(pw != null) {pw.close();}
                }
                if(!fileText.exists()) {return;}

                Stunt stunt = Stunt.getInstance(MainActivity.this);
                stunt.report("Saved text.txt", fileText);
                mCount++;
            }
        });

        mButtonSendClientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stunt stunt = Stunt.getInstance(MainActivity.this);
                setTime(System.currentTimeMillis());
                stunt.reportClientInfo(Build.MANUFACTURER + Build.MODEL, Build.MANUFACTURER, Build.MODEL, Build.SERIAL);
                mCount++;
            }
        });

        LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.serviceurl_layout, (ViewGroup)mViewRoot, false);
        mEditTextServiceUrl = (EditText)view.findViewById(R.id.editTextServiceUrl);
        mEditTextServiceUrl.setText(StuntConst.URL_service);
        Button buttonNegative = (Button)view.findViewById(R.id.buttonNegativeServiceUrl);
        Button buttonPositive = (Button)view.findViewById(R.id.buttonPositiveServiceUrl);
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogServiceUrl.dismiss();
            }
        });
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuntConst.URL_service = mEditTextServiceUrl.getText().toString();
                mAlertDialogServiceUrl.dismiss();
            }
        });

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle(StuntConst.STR_Change_service_url);
        adb.setView(view);
        mAlertDialogServiceUrl = adb.create();
    }

    public void setTime(long aTimeMillis) {
        mTextViewTime.setText(String.valueOf(aTimeMillis));
    }
}
