package co.kaush.rem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final class DbOpenHelper
    extends SQLiteOpenHelper {
  private static final int VERSION = 1;

  public DbOpenHelper(Context context) {
    super(context, "co_kaush_rem_remme.db", null /* factory */, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    /*db.execSQL(""
               + "CREATE TABLE " + TodoList.TABLE + "("
               + TodoList.ID + " INTEGER NOT NULL PRIMARY KEY,"
               + TodoList.NAME + " TEXT NOT NULL,"
               + TodoList.ARCHIVED + " INTEGER NOT NULL DEFAULT 0"
               + ")");

    db.execSQL("CREATE INDEX item_list_id ON " + TodoItem.TABLE + " (" + TodoItem.LIST_ID + ")");*/
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
