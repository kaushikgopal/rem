package co.kaush.rem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.kaush.rem.entity.Task;

final class DbOpenHelper
      extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, "co_kaush_rem_remme.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(""
                   + "CREATE TABLE " + Task.TABLE + "("
                   + Task.ID + " INTEGER NOT NULL PRIMARY KEY,"
                   + Task.DESCRIPTION + " TEXT NOT NULL,"
                   + Task.STATUS + " INTEGER,"
                   + Task.DUE_DATE + " DATE NOT NULL,"
                   + Task.SNOOZE_COUNT + " INTEGER,"
                   + Task.SNOOZE_INTERVAL + " INTEGER"
                   + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
