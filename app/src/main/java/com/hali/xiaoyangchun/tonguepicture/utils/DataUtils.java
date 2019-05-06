package com.hali.xiaoyangchun.tonguepicture.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    public static String getSimpleDate(long timeStamp, String format) {
        String dateString = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        try {
            Date date = new Date(timeStamp);
            dateString = simpleDateFormat.format(date);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return dateString;
    }
}
