package co.kaush.rem;

import dagger.Module;

@Module(
    injects = { MyApp.class },
    library = true)
public class RemModule {

  private final MyApp _app;

  RemModule(MyApp app) {
    _app = app;
  }
}
