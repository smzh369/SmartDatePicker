package com.zerlings.library

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class SmartDatePicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private var mYear = calendar.get(Calendar.YEAR)

    private var mMonth = calendar.get(Calendar.MONTH) + 1

    private var mDay = calendar.get(Calendar.DATE)

    private var mTimeStamp = calendar.timeInMillis

    private var mDateStr = ""

    private val sdf: SimpleDateFormat

    private val format: String

    private val minDate: String?

    private val maxDate: String?

    private val style: Int

    private val timeMillis: Boolean

    private val dialog: DatePickerDialog

    private var onDatePickListener: ((datePicker: DatePicker, year: Int, month: Int, day: Int, timeStamp: Long, dateStr: String) -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartDatePicker)
        format = typedArray.getString(R.styleable.SmartDatePicker_format) ?: "yyyy-MM-dd"
        minDate = typedArray.getString(R.styleable.SmartDatePicker_minDate)
        maxDate = typedArray.getString(R.styleable.SmartDatePicker_maxDate)
        style = typedArray.getInt(R.styleable.SmartDatePicker_style, 3)
        timeMillis = typedArray.getBoolean(R.styleable.SmartDatePicker_timeMills, false)
        typedArray.recycle()
        sdf = SimpleDateFormat(format)
        dialog = DatePickerDialog(context, style, DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, year: Int, month: Int, day: Int ->
            mYear = year
            mMonth = month + 1
            mDay = day
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, day)
            mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
            mDateStr = sdf.format(calendar.time)
            text = mDateStr
            onDatePickListener?.invoke(datePicker, mYear, mMonth, mDay, mTimeStamp, mDateStr)
        }, mYear, mMonth-1, mDay)
        if (!format.contains("dd")){
            ((dialog.datePicker.getChildAt(0) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(2).visibility = View.GONE
        }
        minDate?.apply { sdf.parse(this)?.let { dialog.datePicker.minDate = it.time } }
        maxDate?.apply { sdf.parse(this)?.let { dialog.datePicker.maxDate = it.time } }
        mDateStr = sdf.format(mTimeStamp)
        text = mDateStr
        isClickable = true
        Log.i("SmartDatePicker", "year : $mYear")
        Log.i("SmartDatePicker", "month : $mMonth")
        Log.i("SmartDatePicker", "day : $mDay")
        Log.i("SmartDatePicker", "dateStr : $mDateStr")
        Log.i("SmartDatePicker", "timeStamp : $mTimeStamp")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled && event.action == MotionEvent.ACTION_UP){
            if (!dialog.isShowing){
                dialog.show()
            }else{
                dialog.dismiss()
            }
        }
        return super.onTouchEvent(event)
    }

    fun setStartDate(year: Int, month: Int, day: Int){
        mYear = year
        mMonth = month
        mDay = day
        calendar.set(year, month-1, day)
        mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
        mDateStr = sdf.format(calendar.time)
        text = mDateStr
        dialog.datePicker.updateDate(year, month, day)
    }

    fun setOnDatePickListener(listener: (DatePicker, Int, Int, Int, Long, String) -> Unit){
        onDatePickListener = listener
    }
}