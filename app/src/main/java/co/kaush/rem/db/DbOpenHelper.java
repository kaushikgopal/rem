package co.kaush.rem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.kaush.rem.entity.Task;
import timber.log.Timber;

final class DbOpenHelper
      extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, "co_kaush_rem_remme.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.i("Creating DB tables first time");
        db.execSQL(""
                   + "CREATE TABLE " + Task.TABLE + "("
                   + Task.ID + " INTEGER NOT NULL PRIMARY KEY,"
                   + Task.DESCRIPTION + " TEXT NOT NULL,"
                   + Task.STATUS + " INTEGER DEFAULT "+ Task.STATUS_CREATED +","
                   + Task.DUE_DATE + " DATE NOT NULL,"
                   + Task.SNOOZE_COUNT + " INTEGER DEFAULT 0,"
                   + Task.SNOOZE_INTERVAL + " INTEGER NULL"
                   + ")");

        _seedData(db);
    }

    private void _seedData(SQLiteDatabase db) {
        Timber.i("Seed data to database");

        db.execSQL("INSERT INTO "+Task.TABLE +
                   "(DESCRIPTION, DUE_DATE) VALUES ('Welcome to Remme', '2016-04-04 09:00:00');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
