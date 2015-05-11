package co.kaush.rem;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import dagger.ObjectGraph;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp
      extends Application {

    private static MyApp _instance;
    private ObjectGraph _objectGraph;
    private RefWatcher _refWatcher;

    public static MyApp get() {
        return _instance;
    }

    public static RefWatcher getRefWatcher() {
        return MyApp.get()._refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = (MyApp) getApplicationContext();

        _objectGraph = ObjectGraph.create(Modules.list(this));
        inject(this);

        Timber.plant(new CrashReportingTree());

        _initializeDefaultFont();

        _refWatcher = LeakCanary.install(this);
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
