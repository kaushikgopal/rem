package co.kaush.rem.db;

import android.database.sqlite.SQLiteOpenHelper;
import co.kaush.rem.MyApp;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public final class DbModule {

  @Provides
  @Singleton
  SQLiteOpenHelper provideOpenHelper(MyApp app) {
    return new DbOpenHelper(app);
  }

  @Provides
  @Singleton
  SqlBrite provideSqlBrite(SQLiteOpenHelper openHelper) {
    return SqlBrite.create(openHelper);
  }
}