/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateTimeUtils {
    private static final int _7HOUR_IN_SEC = 7 * 60 * 60;
    public static String defaultTimePattern = "dd/MM/yyyy";
    private static final String timePattern = "yyyy-MM-dd HH:mm:ss";
    public static final String dateTimePattern1 = "dd-MM-yyyy HH:mm:ss";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern).withZone(ZoneId.systemDefault());
    public static final int _1DAY_IN_SEC = 86400;
    public static final long _1DAY_IN_MILLIS = _1DAY_IN_SEC * 1000L;

    public static final int _1HOUR_IN_SEC = 1 * 60 * 60;
    public static final long _1HOUR_IN_MILLIS = _1HOUR_IN_SEC * 1000L;

    public static final int _1MINUTE_IN_SEC =  60;
    public static final long _1MINUTE_IN_MILLIS = _1MINUTE_IN_SEC * 1000L;

    //1s = 1 000 000 000 nanoseconds
    public static long getNanoTime() {
        return System.nanoTime();
    }

    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static int getTomorrowTimestamp() {
        int currentTime = getTimestamp();
        return currentTime + (int) TimeUnit.DAYS.toSeconds(1L);
    }

    public static int getTimestampDayAgo() {
        int currentTime = getTimestamp();
        return currentTime - (int) TimeUnit.DAYS.toSeconds(1L);
    }

    public static int getDateBefore(int time, int numberDays) {
        if (ValidateUtils.isNullOrEmpty(numberDays)) {
            numberDays = 1;
        }
        return time - (int) (numberDays * TimeUnit.DAYS.toSeconds(1L));
    }

    public static int getDateAfter(int time, int numberDays) {
        if (ValidateUtils.isNullOrEmpty(numberDays)) {
            numberDays = 1;
        }
        return time + (int) (numberDays * TimeUnit.DAYS.toSeconds(1L));
    }

    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String format(String format, int timestamp) {
        return new SimpleDateFormat(format).format(new Date((long) timestamp * 1000));
    }

    public static int format(String strFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parseDate = dateFormat.parse(strFormat);
            int time = (int) (parseDate.getTime() / 1000L);
            return time;
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeUtils.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static int daysBetween(int startDate, int endDate) {
        return (endOfDay(endDate) - beginOfDay(startDate)) / 60 * 60 * 24;
    }

    public static int beginOfMin(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) timestamp) * 1000);
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int endOfMin(int timestamp) {
        int begin = beginOfMin(timestamp);
        return begin + 59;
    }

    public static int beginOfHour(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) timestamp) * 1000);
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static long getBeginOfHour(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getEndOfHour(long timestamp) {
        long begin = getBeginOfHour(timestamp);
        return begin + 3600000L - 1L;
    }

    public static int endOfHour(int timestamp) {
        int begin = beginOfHour(timestamp);
        return begin + 3600 - 1;
    }

    public static int beginOfDay(int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) time) * 1000);
        cal.set(Calendar.HOUR_OF_DAY, 0); //set hours to zero
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static long getBeginOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0); //set hours to zero
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);//set seconds to zero
        return cal.getTimeInMillis();
    }

    public static int endOfDay(int milis) {
        int begin = beginOfDay(milis);
        return begin + (24 * 60 * 60) - 1;
    }

    public static long getEndOfDay(long milis) {
        long begin = getBeginOfDay(milis);
        return begin + (24 * 60 * 60 * 1000L) - 1;
    }

    public static int beginOfMonth(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) timestamp) * 1000);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0); //set hours to zero
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int endOfMonth(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) timestamp) * 1000);
        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDate);
        cal.set(Calendar.HOUR_OF_DAY, 23); //set hours to zero
        cal.set(Calendar.MINUTE, 59); // set minutes to zero
        cal.set(Calendar.SECOND, 59); //set seconds to zero
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getWeekDay(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) timestamp) * 1000);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static long timestampToDays(int timestamp) {
        return TimeUnit.SECONDS.toDays(timestamp);
    }

    public static int getCurrentDate() {
        return beginOfDay(getTimestamp());
    }


    public static Date getNow() {
        long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis);
    }

    public static int convertStringToTimeStamp(String format, String timeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date parseDate = dateFormat.parse(timeStr);
            int time = (int) (parseDate.getTime() / 1000L);
            return time;
        } catch (Exception ex) {
            return 0;
        }
    }

    public static long getBeginOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0); //set hours to zero
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //set seconds to zero
        return cal.getTimeInMillis();
    }

    public static long getEndOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDate);
        cal.set(Calendar.HOUR_OF_DAY, 23); //set hours to zero
        cal.set(Calendar.MINUTE, 59); // set minutes to zero
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        //set seconds to zero
        return cal.getTimeInMillis();
    }

    public static int getBeginOfPartUTC(int timestamp, int partTime) {
        final int i = timestamp / partTime;
        return i * partTime;
    }

    public static int getBeginOfPartTimeZoneVN(int timestamp, int partTime) {
        final int i = (timestamp + _7HOUR_IN_SEC) / partTime;
        return i * partTime - _7HOUR_IN_SEC;
    }


    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        final int i = cal.get(Calendar.YEAR);
        return i;
    }


    public static int getMonth(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.YEAR);

    }

    public static String parseTimestampToStringDefault(Integer timestamp) {
        if (timestamp != null) {
            return parseTimestampToStringDefault((long) timestamp * 1000);
        }
        return null;
    }

    public static String parseTimestampToStringDefault(Long timestamp) {
        if (timestamp != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            return timeFormatter.format(localDateTime);
        }
        return null;
    }

    public static String parseTimeInFormat(Integer timestamp, String pattern) {
        DateTimeFormatter defaultTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) timestamp * 1000), ZoneId.systemDefault());
        return defaultTimeFormatter.format(localDateTime);
    }

    public static String parseTimeToHHMMSS(Long ms) {
        Duration duration = Duration.ofMillis(ms);
        long seconds = duration.getSeconds();

        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        return timeInHHMMSS;
    }

    public static void main(String[] args) {
        final int beginOfPartTimeZoneVN = getBeginOfPartTimeZoneVN(1599222280, 60);
        System.out.println(beginOfPartTimeZoneVN + ": " + (beginOfPartTimeZoneVN + 60 - 1));

        System.out.println(parseTimeInFormat(897238800, defaultTimePattern));
    }

    public static String getCurrentDateStr() {
        return format("yyyy-MM-dd", getTimestamp());
    }
}
