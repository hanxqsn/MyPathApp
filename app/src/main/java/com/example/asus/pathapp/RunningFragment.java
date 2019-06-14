package com.example.asus.pathapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RunningFragment extends Fragment {

    int year, month, day;
    DatePicker datePicker;
    String TAG = "RunningFragment";
    final String[] timeChoose = new String[1];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_running, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datePicker = getView().findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                RunningFragment.this.year=year;
                RunningFragment.this.month = monthOfYear;
                RunningFragment.this.day = dayOfMonth;

                if (RunningFragment.this.month <= 8 && RunningFragment.this.day <= 9) {
                    timeChoose[0] = RunningFragment.this.year + "年" + "0"
                            + (RunningFragment.this.month + 1) + "月" + "0"
                            + RunningFragment.this.day + "日";

                } else if (RunningFragment.this.month <= 8 && RunningFragment.this.day > 9) {
                    timeChoose[0] = RunningFragment.this.year + "年" + "0"
                            + (RunningFragment.this.month + 1) + "月" + RunningFragment.this.day + "日";

                } else if (RunningFragment.this.month > 8 && RunningFragment.this.day <= 9) {
                    timeChoose[0] = RunningFragment.this.year + "年"
                            + (RunningFragment.this.month + 1) + "月" + "0" + RunningFragment.this.day + "日";

                } else if (RunningFragment.this.month > 8 && RunningFragment.this.day > 9) {
                    timeChoose[0] = RunningFragment.this.year + "年"
                            + (RunningFragment.this.month + 1) + "月" + RunningFragment.this.day + "日";

                }
                Log.i(TAG, "onDateChanged: timeChoose=" + timeChoose[0]);
                Intent intent = new Intent(getActivity(), ShowNameActivity.class);
                intent.putExtra("chooseDate", timeChoose[0]);
                startActivity(intent);


            }
        });
    }
}