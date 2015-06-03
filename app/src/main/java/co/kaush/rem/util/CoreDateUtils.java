package co.kaush.rem.util;

import hirondelle.date4j.DateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jetbrains.annotations.NotNull;

public class CoreDateUtils {

    private Locale _locale;

    public CoreDateUtils() {
        _locale = Locale.getDefault();
    }

    public CoreDateUtils(Locale locale) {
        _locale = locale;
    }

    /**
     * This is a non-cached version of UTC (depending on the timezone of the user)
     * http://developer.android.com/reference/java/util/TimeZone.html
     *
     * @return DateTimeZone
     */
    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }

    public boolean isAfterToday(DateTime dt1) {
        return dt1.getMilliseconds(getUtcTimeZone()) > now().getMilliseconds(getUtcTimeZone());
    }

    public TimeUnit getDiffUnit(DateTime fromDate, DateTime toDate) {
        long diffInMs = Math.abs(toDate.getMilliseconds(getUtcTimeZone()) -
                                 fromDate.getMilliseconds(getUtcTimeZone()));

        // 1 month ~= 2628000000ms
        if (diffInMs >= 2628000000l) {
            return TimeUnit.MONTH;
        } else if (diffInMs >= 604800000) {
            return TimeUnit.WEEK;
        } else if (diffInMs >= 86400000) {
            return TimeUnit.DAY;
        } else {
            return TimeUnit.HOUR;
        }
    }

    // -----------------------------------------------------------------------------------
    // Wrappers

    public long getTimeFor(DateTime dt) {
        return dt.getMilliseconds(getUtcTimeZone());
    }

    public Date getDateFor(DateTime dt) {
        return new Date(getTimeFor(dt));
    }

    public DateTime now() {
        return DateTime.now(TimeZone.getDefault());
    }

    //  java.util.Date < -- > date4j.DateTime
    public DateTime getDateTimeFor(Date dt) {
        return DateTime.forInstant(dt.getTime(), getUtcTimeZone());
    }

    // -----------------------------------------------------------------------------------
    // String conversions

    public String format(@NotNull String pattern, @NotNull DateTime dateTime) {
        return dateTime.format(pattern, _locale);
    }

    public enum IncreaseOrDecrease {
        PLUS, MINUS
    }

    public enum TimeUnit {
        MONTH, WEEK, DAY, HOUR
    }
}
