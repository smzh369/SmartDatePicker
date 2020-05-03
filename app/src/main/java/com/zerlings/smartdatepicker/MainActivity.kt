package com.zerlings.smartdatepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //date_picker.setDate("2021-07-01")
        date_picker.setOnDatePickListener { year, month, day, timeStamp, dateStr ->
            Log.i("SmartDatePicker", "year : $year")
            Log.i("SmartDatePicker", "month : $month")
            Log.i("SmartDatePicker", "day : $day")
            Log.i("SmartDatePicker", "dateStr : $dateStr")
            Log.i("SmartDatePicker", "timeStamp : $timeStamp")
        }
    }
}
