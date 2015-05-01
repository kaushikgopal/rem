package co.kaush.rem;

import android.app.Application;
import dagger.ObjectGraph;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp
    extends Application {

  private static MyApp _instance;
  private ObjectGraph _objectGraph;

  public static MyApp get() {
    return _instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    _instance = (MyApp) getApplicationContext();

    _objectGraph = ObjectGraph.create(Modules.list(this));
    inject(this);

    Timber.plant(new CrashReportingTree());

    _initializeDefaultFont();

  }

  public ObjectGraph getObjectGraph() {
    return _objectGraph;
  }

  public void inject(Object object) {
    getObjectGraph().inject(object);
  }

  private void _initializeDefaultFont() {
    CalligraphyConfig.initDefault(//
        new CalligraphyConfig.Builder()//
            //.setDefaultFontPath("fonts/Actor-Regular.ttf")//
            .setDefaultFontPath("fonts/SourceSansPro-Regular.ttf")//
            .setFontAttrId(R.attr.fontPath)//
            .build());
  }
}
