package com.blogspot.techzealous.stunt;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blogspot.techzealous.stunt.framework.Stunt;

public class MainActivity extends AppCompatActivity {

    private View mViewRoot;
    private Button mButtonSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewRoot = (View)findViewById(R.id.relativeLayoutRootMain);
        mButtonSendReport = (Button)findViewById(R.id.buttonSendReport);
        mButtonSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stunt stunt = Stunt.getInstance(MainActivity.this);

                //send message
//                stunt.report("This is my report");

                //send bitmap
                mViewRoot.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(mViewRoot.getDrawingCache());
                mViewRoot.setDrawingCacheEnabled(false);

                stunt.report(bitmap);
            }
        });
    }
}
