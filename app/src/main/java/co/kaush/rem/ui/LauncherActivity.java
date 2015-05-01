package co.kaush.rem.ui;

import android.os.Bundle;
import co.kaush.rem.RemModule;
import dagger.Module;
import java.util.Arrays;
import java.util.List;

public class LauncherActivity
    extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(android.R.id.content, new TaskListFragment())
          .commit();
    }
  }

  @Override
  protected List<Object> getActivitySpecificModules() {
    return Arrays.<Object>asList(new LauncherModule());
  }

  @Module(injects = { LauncherActivity.class, TaskListFragment.class },
      addsTo = RemModule.class,
      library = true)
  public class LauncherModule {}
}
