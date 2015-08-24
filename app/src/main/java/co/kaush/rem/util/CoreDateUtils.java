package co.kaush.rem.util;

import hirondelle.date4j.DateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jetbrains.annotations.NotNull;

public class CoreDateUtils {

    // Date
    public static final String DATE_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";

    // DateTime
    public static final String DATETIME_FORMAT_DB = "YYYY-MM-DD hh:mm:ss";
    public static final String DATETIME_FORMAT_DATE_ONLY = "YYYY-MM-DD";
    public static final String DUE_DATE_MONTH = "MMM";
    public static final String DUE_DATE_FORMAT = "MMM D [WWW] h12:mm a";
    public static final String DUE_DATE_DAY = "D";
    public static final String DUE_DATE_DAYTIME = "[WWW] h12:mm a";
    public static final String DUE_DATE_DAYTIME_WITH_YEAR = "[WWW] h12:mm a [YYYY]";

    public CoreDateUtils() { }

    /**
     * This is a non-cached version of UTC (depending on the timezone of the user)
     * http://developer.android.com/reference/java/util/TimeZone.html
     *
     * @return DateTimeZone
     */
    public static TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }

    public static TimeUnit getDiffUnit(DateTime fromDate, DateTime toDate) {
        long diffInMs = Math.abs(toDate.getMilliseconds(getUtcTimeZone()) -
                                 fromDate.getMilliseconds(getUtcTimeZone()));

        if (diffInMs >= 2628000000l) {  // ~1 month
            return TimeUnit.MONTH;
        } else if (diffInMs >= 604800000) {
            return TimeUnit.WEEK;
        } else if (diffInMs >= 86340000) { // 23 hours, 59 minutes
            return TimeUnit.DAY;
        } else if (diffInMs >= 3540000) { // 59 minutes
            return TimeUnit.HOUR;
        } else {
            return TimeUnit.MINUTE;
        }
    }

    public static int getLastDayOfTheYear(DateTime dt) {
        return new DateTime(dt.getYear(), 12, 31, 23, 59, 59, 59).getDayOfYear();
    }

    // -----------------------------------------------------------------------------------
    // Wrappers

    public static long getTimeFor(DateTime dt) {
        return dt.getMilliseconds(getUtcTimeZone());
    }

    public static Date getDateFor(DateTime dt) {
        return new Date(getTimeFor(dt));
    }

    public static Date DateFrom(String dateAsString, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dateAsString);
    }

    //  java.util.Date -- > date4j.DateTime
    public static DateTime getDateTimeFrom(Date dt) {
        return DateTime.forInstant(dt.getTime(), getUtcTimeZone());
    }

    // -----------------------------------------------------------------------------------
    // String conversions

    public static String format(@NotNull String pattern, @NotNull Date date) {
        return format(pattern, getDateTimeFrom(date));
    }

    public static String format(@NotNull String pattern, @NotNull DateTime dateTime) {
        return dateTime.format(pattern, Locale.getDefault());
    }

    // -----------------------------------------------------------------------------------
    // Current time - non-static to allow convenient testing

    public static boolean isSameDay(@NotNull DateTime dt1, @NotNull DateTime dt2) {
        return format(DATETIME_FORMAT_DATE_ONLY, dt1)//
              .equalsIgnoreCase(format(DATETIME_FORMAT_DATE_ONLY, dt2));
    }

    public DateTime now() {
        return DateTime.now(TimeZone.getDefault());
    }

    public boolean isInTheFuture(Date date) {
        return isInTheFuture(getDateTimeFrom(date));
    }

    public boolean isInTheFuture(DateTime dt1) {
        return dt1.getMilliseconds(getUtcTimeZone()) > now().getMilliseconds(getUtcTimeZone());
    }

    public boolean isSameDayAsToday(Date dt) {
        return isSameDay(now(), getDateTimeFrom(dt));
    }

    public enum IncreaseOrDecrease {
        PLUS, MINUS
    }

    public enum TimeUnit {
        MONTH, WEEK, DAY, HOUR, MINUTE
    }
}
