package co.kaush.rem.service;

import com.squareup.sqlbrite.BriteDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public class ServiceModule {

    @Provides
    @Singleton
    TaskService provideTaskService(BriteDatabase db) {
        return new TaskService(db);
    }
}
