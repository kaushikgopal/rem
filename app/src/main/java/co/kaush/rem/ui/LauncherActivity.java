package co.kaush.rem.ui;

import android.os.Bundle;
import co.kaush.rem.RemModule;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class LauncherActivity
    extends BaseActivity {

  @Inject TaskListFragment _taskListFrag;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(android.R.id.content, _taskListFrag)
          .commit();
    }
  }

  @Override
  protected List<Object> getActivitySpecificModules() {
    return Arrays.<Object>asList(new LauncherModule());
  }

  // -----------------------------------------------------------------------------------

  @Module(injects = { LauncherActivity.class, TaskListFragment.class },
      addsTo = RemModule.class,
      library = true)
  public class LauncherModule {

    @Provides
    public TaskListFragment provideTaskListFragment() {
      return new TaskListFragment();
    }

    @Provides
    public TaskListController provideTaskListController(TaskListFragment fragment) {
      return new TaskListController(fragment);
    }
  }
}
