package com.akvelon.crashsample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
    public final static String APP_SECRET = null;

    private TextView tvCrashLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCrashLog = (TextView) findViewById(R.id.tvCrashLog);

        if (null == APP_SECRET) {
            createNoAppSecretDialog();
            return;
        }
        MobileCenter.start(getApplication(), APP_SECRET,
                Crashes.class);

        Crashes.getLastSessionCrashReport(new ResultCallback<ErrorReport>() {
            @Override
            public void onResult(ErrorReport data) {
                if (data != null) {
                    tvCrashLog.setText(Log.getStackTraceString(data.getThrowable()));
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

    private void createNoAppSecretDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.no_app_secret));
        alertDialog.setMessage(getString(R.string.please_provide_app_secret));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();                    }
                });
        alertDialog.show();
    }
}
