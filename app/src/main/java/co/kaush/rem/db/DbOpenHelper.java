package co.kaush.rem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import timber.log.Timber;

import static co.kaush.rem.util.CoreDateUtils.DATETIME_FORMAT_DB;
import static co.kaush.rem.util.CoreDateUtils.format;

final class DbOpenHelper
      extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, "co_kaush_rem_remme.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.i("Creating DB tables first time");
        db.execSQL("" +
                   "CREATE TABLE " +
                   Task.TABLE +
                   "(" +
                   Task.ID +
                   " INTEGER NOT NULL PRIMARY KEY," +
                   Task.DESCRIPTION +
                   " TEXT NOT NULL," +
                   Task.STATUS +
                   " INTEGER DEFAULT " +
                   Task.STATUS_CREATED +
                   "," +
                   Task.DUE_DATE +
                   " DATE NOT NULL," +
                   Task.SNOOZE_COUNT +
                   " INTEGER DEFAULT 0," +
                   Task.SNOOZE_INTERVAL +
                   " INTEGER NULL" +
                   ")");

        _seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void _seedData(SQLiteDatabase db) {
        Timber.i("Seed data to database");

        DateTime today = DateTime.now(CoreDateUtils.getUtcTimeZone());

        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Figure out why I can never get anything done', '" +
                   format(DATETIME_FORMAT_DB, today.minusDays(15)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Meditate on the problem', '" +
                   format(DATETIME_FORMAT_DB, today.minusDays(10)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Install plugin that blocks Reddit', '" +
                   format(DATETIME_FORMAT_DB, today.minusDays(8)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Solution found: need reminder app', '" +
                   format(DATETIME_FORMAT_DB, today.minusDays(3)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('All current reminder apps suck', '" +
                   format(DATETIME_FORMAT_DB, today) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Come up with idea for Remme', '" +
                   format(DATETIME_FORMAT_DB, today) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Accepted for DroidCon NYC!', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(6)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Start Remme', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(7)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Panic that DroidCon talk not made', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(8)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Cancel all appointments', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(9)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Really make talk', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(19)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Give actual talk', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(21)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Party!', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(33)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Signup for future talks', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(58)) +
                   "');");
        db.execSQL("INSERT INTO " +
                   Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Procrastinate until second last day', '" +
                   format(DATETIME_FORMAT_DB, today.plusDays(68)) +
                   "');");
    }
}
