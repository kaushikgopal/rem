package co.kaush.rem.util;

import hirondelle.date4j.DateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
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

    public DateTime now() {
        return DateTime.now(TimeZone.getDefault());
    }

    public String format(@NotNull String pattern, @NotNull DateTime dateTime) {
        return dateTime.format(pattern, _locale);
    }

    public Date getDateFor(DateTime dt) {
        return new Date(dt.getMilliseconds(getUtcTimeZone()));
    }

    // -----------------------------------------------------------------------------------
    //  java.util.Date < -- > date4j.DateTime

    public DateTime getDateTimeFor(Date dt) {
        return DateTime.forInstant(dt.getTime(), getUtcTimeZone());
    }

    public enum IncreaseOrDecrease {
        PLUS, MINUS
    }
}
