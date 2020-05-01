package com.zerlings.library

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
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

    private var mDay = calendar.get(Calendar.DAY_OF_MONTH)

    private var mTimeStamp = 0L

    private var mDateStr = ""

    private val sdf: SimpleDateFormat

    private val format: String

    private val style: Int

    private val timeMillis: Boolean

    private val dialog: DatePickerDialog

    private var onDatePickListener: ((year: Int, month: Int, day: Int, timeStamp: Long, dateStr: String) -> Unit)? = null

    private var onDismissListener: (() -> Unit)? = null

    private var onCancelListener: (() -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartDatePicker)
        format = typedArray.getString(R.styleable.SmartDatePicker_format) ?: "yyyy-MM-dd"
        val minDate = typedArray.getString(R.styleable.SmartDatePicker_minDate)
        val maxDate = typedArray.getString(R.styleable.SmartDatePicker_maxDate)
        style = typedArray.getInt(R.styleable.SmartDatePicker_style, 3)
        timeMillis = typedArray.getBoolean(R.styleable.SmartDatePicker_timeMills, false)
        typedArray.recycle()
        sdf = SimpleDateFormat(format)
        dialog = DatePickerDialog(context, style, DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, day: Int ->
            mYear = year
            mMonth = month + 1
            mDay = day
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
            mDateStr = sdf.format(calendar.time)
            text = mDateStr
            onDatePickListener?.invoke(mYear, mMonth, mDay, mTimeStamp, mDateStr)
        }, mYear, mMonth-1, mDay)
        /*if (!format.contains("yy") && style < 4){
            ((dialog.datePicker.getChildAt(0) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1).visibility = View.GONE
        }
        if (!format.contains("MM") && style < 4){
            ((dialog.datePicker.getChildAt(0) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1).visibility = View.GONE
        }
        if (!format.contains("dd") && style < 4){
            ((dialog.datePicker.getChildAt(0) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1).visibility = View.GONE
        }*/
        minDate?.apply { sdf.parse(this)?.let { dialog.datePicker.minDate = it.time } }
        maxDate?.apply { sdf.parse(this)?.let { dialog.datePicker.maxDate = it.time } }
        mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
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

    fun show() = dialog.show()

    fun dismiss() = dialog.dismiss()

    fun cancel() = dialog.cancel()

    fun setDate(year: Int, month: Int, day: Int){
        mYear = year
        mMonth = month
        mDay = day
        calendar.set(year, month-1, day)
        mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
        mDateStr = sdf.format(calendar.time)
        text = mDateStr
        dialog.datePicker.updateDate(year, month-1, day)
    }
    
    fun setDate(timeMills: Long){
        calendar.timeInMillis = timeMills
        mTimeStamp = if (timeMillis) timeMills else timeMills / 1000
        mDateStr = sdf.format(calendar.time)
        text = mDateStr
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH) + 1
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        dialog.datePicker.updateDate(mYear, mMonth-1, mDay)
    }
    
    fun setDate(date: String){
        mDateStr = date
        text = date
        calendar.time = sdf.parse(date)
        mTimeStamp = if (timeMillis) calendar.timeInMillis else calendar.timeInMillis / 1000
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH) + 1
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        dialog.datePicker.updateDate(mYear, mMonth-1, mDay)
    }
    
    fun setMinDate(minDate: String) = sdf.parse(minDate)?.let { dialog.datePicker.minDate = it.time }
    
    fun setMinDate(minTimeMills: Long) { dialog.datePicker.minDate = minTimeMills }

    fun setMaxDate(maxDate: String) = sdf.parse(maxDate)?.let { dialog.datePicker.maxDate = it.time }

    fun setMaxDate(maxTimeMills: Long) { dialog.datePicker.maxDate = maxTimeMills }

    fun getDate() = mDateStr

    fun getTimeStamp() = mTimeStamp

    fun getYear() = mYear

    fun getMonth() = mMonth

    fun getDay() = mDay

    fun getDatePicker() = dialog.datePicker

    fun getDialog() = dialog

    fun setOnDatePickListener(listener: (Int, Int, Int, Long, String) -> Unit){
        onDatePickListener = listener
    }

    fun setOnShowListener(listener: () -> Unit){
        onDismissListener = listener
    }

    fun setOnCancelListener(listener: () -> Unit){
        onCancelListener = listener
    }
}