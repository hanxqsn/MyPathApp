package com.example.asus.pathapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class CalendarActivity extends AppCompatActivity {

    int year, month, day;
    DatePicker datePicker;
    String TAG = "CalendaActivity";
    final String[] timeChoose = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        datePicker = findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                CalendarActivity.this.year = year;
                CalendarActivity.this.month = monthOfYear;
                CalendarActivity.this.day = dayOfMonth;

                if (CalendarActivity.this.month <= 8 && CalendarActivity.this.day <= 9) {
                    timeChoose[0] = CalendarActivity.this.year + "年" + "0"
                            + (CalendarActivity.this.month + 1) + "月" + "0"
                            + CalendarActivity.this.day + "日";

                } else if (CalendarActivity.this.month <= 8 && CalendarActivity.this.day > 9) {
                    timeChoose[0] = CalendarActivity.this.year + "年" + "0"
                            + (CalendarActivity.this.month + 1) + "月" + CalendarActivity.this.day + "日";

                } else if (CalendarActivity.this.month > 8 && CalendarActivity.this.day <= 9) {
                    timeChoose[0] = CalendarActivity.this.year + "年"
                            + (CalendarActivity.this.month + 1) + "月" + "0" + CalendarActivity.this.day + "日";

                } else if (CalendarActivity.this.month > 8 && CalendarActivity.this.day > 9) {
                    timeChoose[0] = CalendarActivity.this.year + "年"
                            + (CalendarActivity.this.month + 1) + "月" + CalendarActivity.this.day + "日";

                }
                Log.i(TAG, "onDateChanged: timeChoose=" + timeChoose[0]);
                Intent intent = new Intent(CalendarActivity.this, ShowNameActivity.class);
                intent.putExtra("chooseDate", timeChoose[0]);
                startActivity(intent);




            }
        });

    }
}
