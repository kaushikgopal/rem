package co.kaush.rem.db;

import android.database.sqlite.SQLiteOpenHelper;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import timber.log.Timber;

@Module(complete = false, library = true, overrides = true)
public final class DebugDbModule {

  @Provides
  @Singleton
  SqlBrite provideSqlBrite(SQLiteOpenHelper openHelper) {
    SqlBrite db = SqlBrite.create(openHelper);

    db.setLogger(new SqlBrite.Logger() {
      @Override
      public void log(String message) {
        Timber.tag("Database").v(message);
      }
    });
    db.setLoggingEnabled(true);

    return db;
  }
}

