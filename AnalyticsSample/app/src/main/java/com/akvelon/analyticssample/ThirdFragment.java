package com.akvelon.analyticssample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.azure.mobile.analytics.Analytics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislav.alekseev on 4/18/2017.
 */

public class ThirdFragment extends Fragment implements View.OnClickListener {

    private final int DEFAULT_AGE = 18;
    private final int MIN_AGE = 5;
    private final int MAX_AGE = 99;
    private int age;
    private TextView tvAge;
    private int buttonIds[] = {R.id.btnMinus, R.id.btnPlus, R.id.btnCancel, R.id.btnOk};
    private EditText etName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_third, container, false);

        tvAge = (TextView) view.findViewById(R.id.tvAge);
        etName = (EditText) view.findViewById(R.id.etName);
        for (int buttonId : buttonIds){
            view.findViewById(buttonId).setOnClickListener(this);
        }

        resetDefaults();
        return view;
    }

    private void resetDefaults(){
        age = DEFAULT_AGE;
        updateAge();
        etName.setText("");
    }

    private void updateAge() {
        tvAge.setText(((age<10) ? "0" : "") +  age);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnMinus:
                if (age <= MIN_AGE) return;
                age --;
                updateAge();
                break;
            case R.id.btnPlus:
                if (age >= MAX_AGE) return;
                age ++;
                updateAge();
                break;
            case R.id.btnCancel: {
                resetDefaults();
                Map<String, String> properties = new HashMap<>();
                properties.put(Event.SURVEY_RESULT_RECEIVED, Event.SURVEY_CANCELED);
                Analytics.trackEvent(Event.SURVEY_RECEIVED, properties);
            }
                break;
            case R.id.btnOk: {
                String toastMessage = "";
                Map<String, String> properties = new HashMap<>();
                if (etName.getText().toString().length() < 2) {
                    toastMessage = getString(R.string.fill_name);
                    properties.put(Event.SURVEY_RESULT_RECEIVED, Event.SURVEY_ATTEMPTED_TO_SEND_EMPTY_NAME);
                } else {
                    toastMessage = getString(R.string.survey_accepted);
                    properties.put(Event.SURVEY_RESULT_RECEIVED, Event.SURVEY_RECEIVED);
                    properties.put(Event.SURVEY_NAME, etName.getText().toString());
                    properties.put(Event.SURVEY_AGE, "" + age);
                    resetDefaults();
                }
                Toast.makeText(getContext(),
                        toastMessage,
                        Toast.LENGTH_SHORT).show();
                Analytics.trackEvent(Event.SURVEY_RECEIVED, properties);
            }
                break;
        }
    }
}
