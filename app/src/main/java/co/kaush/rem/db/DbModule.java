package co.kaush.rem.db;

import android.database.sqlite.SQLiteOpenHelper;
import co.kaush.rem.MyApp;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import timber.log.Timber;

@Module(complete = false, library = true)
public final class DbModule {

    @Provides
    @Singleton
    SQLiteOpenHelper provideOpenHelper(MyApp app) {
        Timber.d("provide open helper");
        return new DbOpenHelper(app);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create();
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        Timber.d("provide BriteDb");
        return sqlBrite.wrapDatabaseHelper(helper);
    }
}