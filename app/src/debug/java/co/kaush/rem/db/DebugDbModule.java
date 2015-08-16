package co.kaush.rem.db;

import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import timber.log.Timber;

@Module(complete = false, library = true, overrides = true)
public final class DebugDbModule {

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        Timber.d("provide SqlBrite");
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("SqlBriteDB").v(message);
            }
        });
    }
}

