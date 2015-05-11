package co.kaush.rem.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import co.kaush.rem.MyApp;
import dagger.ObjectGraph;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity
      extends AppCompatActivity {

    static final String KEY_CURRENT_FRAG = "FragmentThatsCurrentlyBeingShown";

    int currentFragment;

    private ObjectGraph _activityObjectGraph;

    /**
     * A list of modules to use for the individual activity graph.
     * <p/>
     * Subclasses override this method to .plus activity specific modules
     * onto the application graph.
     */
    protected abstract List<Object> getActivitySpecificModules();

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        _activityObjectGraph.inject(object);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _activityObjectGraph = MyApp.get()
              .getObjectGraph()
              .plus(getActivitySpecificModules().toArray());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        _activityObjectGraph.inject(this);

        //_initializeToolbar();
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as soon as possible.
        _activityObjectGraph = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CURRENT_FRAG, currentFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //private void _initializeToolbar() {
    //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //  if (toolbar != null) {
    //    setSupportActionBar(toolbar);
    //  }
    //}
}

