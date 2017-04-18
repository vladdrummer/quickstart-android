package com.akvelon.crashsample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.ResultCallback;
import com.microsoft.azure.mobile.crashes.Crashes;
import com.microsoft.azure.mobile.crashes.model.ErrorReport;

public class MainActivity extends AppCompatActivity {

    //This is your app's secret key you obtain from the mobile center
    public final static String APP_SECRET = "99094070-d1bb-4c90-bc12-1b25c5fd6a5a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileCenter.start(getApplication(), APP_SECRET,
                Crashes.class);

        Crashes.getLastSessionCrashReport(new ResultCallback<ErrorReport>() {
            @Override
            public void onResult(ErrorReport data) {
                if (data != null) {
                    ((TextView) findViewById(R.id.tvCrashLog)).
                            setText(Log.getStackTraceString(data.getThrowable()));
                }
            }
        });
        View.OnClickListener crashButtonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btnGeneratedError:
                        Crashes.generateTestCrash();
                        break;
                    case R.id.btnNullPointerError:
                        ((Object)null).toString();
                        break;
                    case R.id.btnIndexOutOfBounds:
                        int[] array = {1};
                        int doOutOfBoundsError = array[1];
                        break;
                    case R.id.btnDivideByZero:
                        int doDivideByZeroError = 1 / 0;
                        break;

                }
            }
        };

        findViewById(R.id.btnGeneratedError).setOnClickListener(crashButtonsListener);
        findViewById(R.id.btnNullPointerError).setOnClickListener(crashButtonsListener);
        findViewById(R.id.btnIndexOutOfBounds).setOnClickListener(crashButtonsListener);
        findViewById(R.id.btnDivideByZero).setOnClickListener(crashButtonsListener);

    }

}
