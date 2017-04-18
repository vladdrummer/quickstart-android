package com.akvelon.analyticssample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microsoft.azure.mobile.analytics.Analytics;

/**
 * Created by vladislav.alekseev on 4/18/2017.
 */

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.btnPushMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Analytics.trackEvent(Event.HOME_BUTTON_PUSHED);
            }
        });

        return view;
    }
}
