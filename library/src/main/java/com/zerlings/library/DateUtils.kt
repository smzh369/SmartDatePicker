package com.zerlings.library

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object{

        /**
         * 时间戳转换成日期格式字符串
         * @param seconds 精确到秒的字符串
         * @param format
         * @return 日期字符串
         */
        @JvmOverloads
        @SuppressLint("SimpleDateFormat")
        fun timeStamp2Date(seconds: String, format: String = "yyyy-MM-dd"): String {
            var mFormat = format
            if (seconds.isEmpty() || seconds == "null") {
                return ""
            }
            if (format.isEmpty()) {
                mFormat = "yyyy-MM-dd"
            }
            val sdf = SimpleDateFormat(mFormat)
            return sdf.format(Date(java.lang.Long.valueOf(seconds + "000")))
        }

        /**
         * 日期字符串转换成时间戳格式
         * @param date 日期字符串
         * @param format
         * @return 时间戳
         */
        @JvmOverloads
        @SuppressLint("SimpleDateFormat")
        fun date2TimeStamp(date: String, format: String = "yyyy-MM-dd"): Long {
            try {
                val sdf = SimpleDateFormat(format)
                return (sdf.parse(date)?.time ?: 0) / 1000
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
    }
}