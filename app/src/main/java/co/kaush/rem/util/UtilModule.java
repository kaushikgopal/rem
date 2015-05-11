package co.kaush.rem.util;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false)
public final class UtilModule {

    @Provides
    @Singleton
    public CoreDateUtils provideCoreDateUtils() {
        return new CoreDateUtils();
    }
}
