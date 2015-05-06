package co.kaush.rem.util;

import hirondelle.date4j.DateTime;
import java.util.Date;
import java.util.TimeZone;

public class CoreDateUtils {

  // ---------------------------------------------------------------------------------------
  // Utility methods

  /**
   * This is a non-cached version of UTC (depending on the timezone of the user)
   * http://developer.android.com/reference/java/util/TimeZone.html
   *
   * @return DateTimeZone
   */
  public static TimeZone getUtcTimeZone() {
    return TimeZone.getTimeZone("UTC");
  }

  public static Date getDateFor(DateTime dt) {
    return new Date(dt.getMilliseconds(getUtcTimeZone()));
  }

  public static DateTime getDateTimeFor(Date dt) {
    return DateTime.forInstant(dt.getTime(), getUtcTimeZone());
  }

}
