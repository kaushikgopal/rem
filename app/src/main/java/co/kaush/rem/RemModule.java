package co.kaush.rem;

import co.kaush.rem.db.DbModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(
    injects = { MyApp.class },
    includes = { DbModule.class },
    library = true)
public class RemModule {

  private final MyApp _app;

  RemModule(MyApp app) {
    _app = app;
  }

  @Provides
  @Singleton
  MyApp provideApplication() {
    return _app;
  }
}
