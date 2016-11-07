package com.blogspot.techzealous.stunt;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.techzealous.stunt.framework.Stunt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private View mViewRoot;
    private Button mButtonSendReportString;
    private Button mButtonSendReportBitmap;
    private Button mButtonSendReportFile;
    private TextView mTextViewTime;

    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewRoot = (View)findViewById(R.id.relativeLayoutRootMain);
        mButtonSendReportString = (Button)findViewById(R.id.buttonSendReportString);
        mButtonSendReportBitmap = (Button)findViewById(R.id.buttonSendReportBitmap);
        mButtonSendReportFile = (Button)findViewById(R.id.buttonSendReportFile);
        mTextViewTime = (TextView)findViewById(R.id.textViewTime);

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
                stunt.report(bitmap);
            }
        });

        mButtonSendReportFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileText = new File(Environment.getExternalStorageDirectory() + "text.txt");
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
                stunt.report(fileText);
                mCount++;
            }
        });
    }

    public void setTime(long aTimeMillis) {
        mTextViewTime.setText(String.valueOf(aTimeMillis));
    }
}
