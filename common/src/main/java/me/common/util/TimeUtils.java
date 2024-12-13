package me.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeUtils {
    private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static ZoneId SystemZoneId = ZoneId.systemDefault();
    public static DateTimeFormatter DateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
    public static DateTimeFormatter DateFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DateTimeFormatter DateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static DateTimeFormatter DateFormatter4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static DateTimeFormatter DateFormatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DateFormatter6 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static DateTimeFormatter DateFormatter7 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static DateTimeFormatter DateFormatter8 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static DateTimeFormatter DateFormatter9 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter DateFormatter10 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static DateTimeFormatter DateFormatter11 = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    public static DateTimeFormatter DateFormatter12 = DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS");
    public static DateTimeFormatter DateFormatter13 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter DateFormatter14 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter DateFormatter15 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static DateTimeFormatter DateFormatter16 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
    public static DateTimeFormatter DateFormatter17 = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static DateTimeFormatter DateFormatter18 = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
    public static DateTimeFormatter DateFormatter19 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static DateTimeFormatter DateFormatter20 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static DateTimeFormatter TimeFormatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter TimeFormatter2 = DateTimeFormatter.ofPattern("HH:mm");

    public static Long MillisecondsInDay = 24 * 60 * 60 * 1000L;
    public static Long MillisecondsInMinute = 60 * 1000L;
    public static Long MillisecondsInHour = 60 * MillisecondsInMinute;
    public static Long MillisecondsInYear = 365 * MillisecondsInDay;
    public static Integer SecondsInMinute = 60;
    public static Integer SecondsInDay = 24 * 60 * 60;

    public static Long startOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static Long getTimeInMillisecond(Integer year, Integer month, Integer date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0L);
        c.set(year, month - 1, date, 0, 0, 0);
        return c.getTimeInMillis();
    }

    public static String isoLocalDateTime(Long millis) {
        return format(millis, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String isoLocalDate(Long millis) {
        return format(millis, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static Long parseIsoLocalDate(String str) {
        return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay()
                .atZone(SystemZoneId)
                .toInstant()
                .toEpochMilli();
    }

    public static Long parseIsoLocalDateTime(String str) {
        return parse(str, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String fullLocalDateTime(Long millis, DateTimeFormatter dateFormatter) {
        if (dateFormatter == null) {
            dateFormatter = DateFormatter1;
        }
        return format(millis, dateFormatter);
    }

    public static String format(Long millis, DateTimeFormatter f) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), SystemZoneId);
        return ldt.format(f);
    }

    public static Long parse(String str, DateTimeFormatter f) {
        return LocalDateTime.parse(str, f)
                .atZone(SystemZoneId)
                .toInstant()
                .toEpochMilli();
    }

    public static Long parse2(String str, DateTimeFormatter f) {
        return LocalDate.parse(str, f).atStartOfDay()
                .atZone(SystemZoneId)
                .toInstant()
                .toEpochMilli();
    }

    /**
     * Gets start of date milliseconds of a timestamp.
     *
     * @param millis provided timestamp in milliseconds
     * @return milliseconds of the start of date
     */
    public static Long getStartOfDateMillisecond(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        return Date.from(localDate.atStartOfDay(SystemZoneId).toInstant()).getTime();
    }

    /**
     * Gets start of month milliseconds of a timestamp.
     *
     * @param millis provided timestamp in milliseconds
     * @return milliseconds of the start of month
     */
    public static Long getStartOfMonthMillisecond(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        LocalDate startOfMonth = localDate.withDayOfMonth(1);
        return Date.from(startOfMonth.atStartOfDay(SystemZoneId).toInstant()).getTime();
    }

    public static Long getEndOfDateMillisecond(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        //Add one day to current day = next day => atStartOfDay(nextDay) = atEndOfDay(curDay)
        return Date.from(localDate.atStartOfDay(SystemZoneId).plusDays(1).toInstant()).getTime();
    }

    public static Long getBeginOfHourMillisecond(Long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.MINUTE, 0); // set minutes to zero
        cal.set(Calendar.SECOND, 0); //set seconds to zero
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static Long dateDiff(Long from, Long to) {
        return (to - from) / (24 * 60 * 60 * 1000);
    }

    public static Integer getYear(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.get(Calendar.YEAR);
    }

    public static Boolean checkCurrentIsInTime(LocalDateTime minTime, LocalDateTime maxTime) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            return currentTime.isAfter(minTime) && currentTime.isBefore(maxTime);
        } catch (Exception e) {
            return false;
        }
    }

    public static Long addBlockDay(Long beginTime, Integer nDay) {
        Long beginTimeFormatted = getStartOfDateMillisecond(beginTime);
        return beginTimeFormatted + nDay * MillisecondsInDay;
    }

    public static Long getEndOfYear(Long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        return Date.from(localDate.with(java.time.temporal.TemporalAdjusters.lastDayOfYear()).atStartOfDay(SystemZoneId).plusDays(1).toInstant()).getTime() - 1;
    }

    public static Long getEndOfMonth(Long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        return Date.from(localDate.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth()).atStartOfDay(SystemZoneId).plusDays(1).toInstant()).getTime() - 1;
    }

    public static Long getBeginOfMonth(Long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDate localDate = instant.atZone(SystemZoneId).toLocalDate();
        return Date.from(localDate.with(java.time.temporal.TemporalAdjusters.firstDayOfMonth()).atStartOfDay(SystemZoneId).toInstant()).getTime();
    }

    public static Long addYearTime(Long beginTime, Integer nYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(beginTime);
        cal.add(Calendar.YEAR, nYear);
        return cal.getTimeInMillis();
    }

    public static Long roundTimestampSecond(Long timestamp) {
        return (timestamp / 1000) * 1000;
    }

    public static Long hourDiff(Long from, Long to) {
        return (to - from) / (60 * 60 * 1000);
    }

    public static Float getAge(Long dob) {
        if (dob == null) return null;
        LocalDate birthday = Instant.ofEpochMilli(dob).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthday, today);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        return ((((float) months * 30) + (float) days) / 366) + years;
    }

    public static Float getAge(Long dob, Long dateToCheck) {
        LocalDate birthday = Instant.ofEpochMilli(dob).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkDate = Instant.ofEpochMilli(dateToCheck).atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(birthday, checkDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        return ((((float) months * 30) + (float) days) / 366) + years;
    }


    public static Float genAboveAge(String dobCheck, String dateCheck) {
        Long dobCheckTime = parse2(dobCheck, DateFormatter2);
        Long dateCheckTime = parse2(dateCheck, DateFormatter2);
        return getAge(dobCheckTime, dateCheckTime);
    }

    public static DateIC extractDateIC(String date) {
        try {
            String[] value = date.split("[-/]");
            DateIC dateIC;
            if (value.length == 3) {
                dateIC = new DateIC();
                int d = Integer.parseInt(value[0]);
                int m = Integer.parseInt(value[1]);
                int y = Integer.parseInt(value[2]);
                dateIC.setDay(d);
                dateIC.setMonth(m);
                dateIC.setYear(y);
                return dateIC;
            }

            if (value.length == 1 && value[0].length() == 4) {
                dateIC = new DateIC();
                dateIC.setDay(1);
                dateIC.setMonth(1);
                dateIC.setYear(Integer.parseInt(value[0]));
                return dateIC;
            }
        } catch (Exception var6) {
            logger.error(String.format("Cannot extract DateIC: %s - %s", date, var6.getMessage()), var6);
        }

        return null;
    }

    private static class ExtractDobData {
        public Integer dd;
        public Integer mm;
        public Integer yyyy;

        public ExtractDobData(Integer dd, Integer mm, Integer yyyy) {
            this.dd = dd;
            this.mm = mm;
            this.yyyy = yyyy;
        }
    }

    private static ExtractDobData extractDobDDMMYYYY(String dob) {
        try {
            List<String> value = Arrays.asList(dob.split("-|\\/"));
            if (value.size() == 3) {
                return new ExtractDobData(Integer.parseInt(value.get(0)), Integer.parseInt(value.get(1)), Integer.parseInt(value.get(2)));
            } else if (value.size() == 1 && value.get(0).length() == 4) {
                return new ExtractDobData(1, 1, Integer.parseInt(value.get(0)));
            } else {
                return new ExtractDobData(-1, -1, -1);
            }
        } catch (Exception e) {
            return new ExtractDobData(-1, -1, -1);
        }

    }

    public static Long parseDOBStrDDMMYYYY(String dob) {
        try {
            ExtractDobData extractDobData = extractDobDDMMYYYY(dob);
            if (extractDobData.dd == -1 && extractDobData.mm == -1 && extractDobData.yyyy == -1) {
                return null;
            } else {
                return getTimeInMillisecond(extractDobData.yyyy, extractDobData.mm, extractDobData.dd);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Long addDayTime(Long beginTime, Integer nDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(beginTime);
        cal.add(Calendar.DATE, nDay);
        return cal.getTimeInMillis();
    }

    public static class DateIC {
        Integer day;
        Integer month;
        Integer year;

        public Long toMillis() {
            return this.day != null && this.month != null && this.year != null ? TimeUtils.getTimeInMillisecond(this.year, this.month, this.day) : null;
        }

        public DateIC() {
        }

        public Integer getDay() {
            return this.day;
        }

        public Integer getMonth() {
            return this.month;
        }

        public Integer getYear() {
            return this.year;
        }

        public void setDay(final Integer day) {
            this.day = day;
        }

        public void setMonth(final Integer month) {
            this.month = month;
        }

        public void setYear(final Integer year) {
            this.year = year;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof DateIC)) {
                return false;
            } else {
                DateIC other = (DateIC)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    label47: {
                        Object this$day = this.getDay();
                        Object other$day = other.getDay();
                        if (this$day == null) {
                            if (other$day == null) {
                                break label47;
                            }
                        } else if (this$day.equals(other$day)) {
                            break label47;
                        }

                        return false;
                    }

                    Object this$month = this.getMonth();
                    Object other$month = other.getMonth();
                    if (this$month == null) {
                        if (other$month != null) {
                            return false;
                        }
                    } else if (!this$month.equals(other$month)) {
                        return false;
                    }

                    Object this$year = this.getYear();
                    Object other$year = other.getYear();
                    if (this$year == null) {
                        if (other$year != null) {
                            return false;
                        }
                    } else if (!this$year.equals(other$year)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof DateIC;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            Object $day = this.getDay();
            result = result * 59 + ($day == null ? 43 : $day.hashCode());
            Object $month = this.getMonth();
            result = result * 59 + ($month == null ? 43 : $month.hashCode());
            Object $year = this.getYear();
            result = result * 59 + ($year == null ? 43 : $year.hashCode());
            return result;
        }

        public String toString() {
            return "TimeUtils.DateIC(day=" + this.getDay() + ", month=" + this.getMonth() + ", year=" + this.getYear() + ")";
        }
    }

    public static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    public static Integer getDaysBetween(Long from, Long to) {
        if (from == null || to == null) {
            return null;
        } else {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(from);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(to);
            return (int)((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 86400000L);
        }
    }
}
