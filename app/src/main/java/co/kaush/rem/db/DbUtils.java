package co.kaush.rem.db;

import android.database.Cursor;
import co.kaush.rem.util.CoreDateUtils;
import java.text.ParseException;
import java.util.Date;
import timber.log.Timber;

public class DbUtils {
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static Date getDate(Cursor cursor, String columnName) {
        String dateFromDbAsString = cursor.getString(cursor.getColumnIndexOrThrow(columnName));
        try {
            return CoreDateUtils.DateFrom(dateFromDbAsString, CoreDateUtils.DATE_FORMAT_DB);
        } catch (ParseException e) {
            Timber.e(e, "Error");
            throw new AssertionError("Date incorectly formatted!");
        }
    }

    private DbUtils() {
        throw new AssertionError("No instances.");
    }
}