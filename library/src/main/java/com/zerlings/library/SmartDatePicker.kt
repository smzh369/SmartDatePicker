package com.zerlings.library

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class SmartDatePicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val calendar = Calendar.getInstance()

    private var mYear = calendar.get(Calendar.YEAR)

    private var mMonth = calendar.get(Calendar.MONTH) + 1

    private var mDay = calendar.get(Calendar.DATE)

    private var mTimeStamp = System.currentTimeMillis() / 1000

    private var mDateStr = DateUtils.timeStamp2Date(mTimeStamp.toString())

    private val sdf: SimpleDateFormat

    private val format: String

    private val style: Int

    private val dialog: DatePickerDialog

    private var onDatePickListener: ((datePicker: DatePicker, year: Int, month: Int, day: Int, timeStamp: Long, dateStr: String) -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartDatePicker)
        format = typedArray.getString(R.styleable.SmartDatePicker_format) ?: "yyyy-MM-dd"
        sdf = SimpleDateFormat(format)
        style = typedArray.getInt(R.styleable.SmartDatePicker_style, 3)
        dialog = DatePickerDialog(context, style, DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, year: Int, month: Int, day: Int ->
            mYear = year
            mMonth = month + 1
            mDay = day
            calendar.set(year, Calendar.YEAR)
            calendar.set(month, Calendar.MONTH)
            calendar.set(day, Calendar.DATE)
            mTimeStamp = calendar.timeInMillis / 1000
            mDateStr = sdf.format(calendar.time)
            onDatePickListener?.invoke(datePicker, mYear, mMonth, mDay, mTimeStamp, mDateStr)
        }, mYear, mMonth-1, mDay)
        typedArray.recycle()
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
}