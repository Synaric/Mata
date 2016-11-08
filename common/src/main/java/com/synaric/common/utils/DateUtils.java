package com.synaric.common.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程安全的时间日期转换工具。
 * <br/><br/>Created by Synaric on 2016/11/2 0002.
 */
public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<>();

    @SuppressLint("SimpleDateFormat")
    private static DateFormat getInstance() {
        DateFormat dateFormat = threadLocal.get();
        if(dateFormat == null) {
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(dateFormat);
        }

        return dateFormat;
    }

    public static Date parse(String textDate) throws ParseException {
        try {
            return getInstance().parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
