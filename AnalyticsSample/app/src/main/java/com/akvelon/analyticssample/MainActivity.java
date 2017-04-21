package com.akvelon.analyticssample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.analytics.Analytics;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {


    //This is your app's secret key you obtain from the mobile center
    public final static String APP_SECRET = null;
    private final int HOME = 0, SECOND = 1, THIRD =2 ;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openScreen(HOME);
                    return true;
                case R.id.navigation_dashboard:
                    openScreen(SECOND);
                    return true;
                case R.id.navigation_notifications:
                    openScreen(THIRD);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == APP_SECRET) {
            createNoAppSecretDialog();
            return;
        }

        MobileCenter.start(getApplication(), APP_SECRET,
                Analytics.class);

        fragmentManager = getSupportFragmentManager();
        openScreen(HOME);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void openScreen(int id){
        Fragment fragment = null;
        Map<String,String> properties = new HashMap<>();
        String page = "";

        switch (id) {
            case HOME:
                fragment = new HomeFragment();
                page = Event.HOME_PAGE;
                break;
            case SECOND:
                fragment = new SecondFragment();
                page = Event.SECOND_PAGE;
                break;
            case THIRD:
                fragment = new ThirdFragment();
                page = Event.THIRD_PAGE;
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        properties.put(Event.PAGE_KEY, page);
        Analytics.trackEvent(Event.PAGE_OPENED, properties);
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
