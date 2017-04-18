package com.akvelon.analyticssample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.azure.mobile.analytics.Analytics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislav.alekseev on 4/18/2017.
 */

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);

        ListView lvMain = (ListView) view.findViewById(R.id.lvMain);
        final String[] phoneManufacturers = {"Samsung", "Apple", "LG", "HTC", "Sony", "Motorola", "Huawei",
                "Nokia", "Xiaomi", "Alcatel", "Oppo", "ZTE", "Asus", "OnePlus", "Honor", "Acer", "Meizu"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, phoneManufacturers);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phoneManufacturer = phoneManufacturers[position];
                Toast.makeText(getContext(),
                        String.format(getString(R.string.element_sent_to_analytics), phoneManufacturer),
                        Toast.LENGTH_SHORT).show();
                Map<String,String> properties = new HashMap<>();
                properties.put(Event.PHONE_MANUFACTURER, phoneManufacturer);
                Analytics.trackEvent(Event.PHONE_MANUFACTURER_CHOSEN, properties);
            }
        });
        
        return view;
    }
}
