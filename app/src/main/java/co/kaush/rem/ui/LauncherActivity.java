package co.kaush.rem.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import co.kaush.rem.R;
import co.kaush.rem.RemModule;
import co.kaush.rem.util.ColorFilterCache;
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
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.task_list_menu, menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    _changeColorOfAddButton((MenuBuilder) menu);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  protected List<Object> getActivitySpecificModules() {
    return Arrays.<Object>asList(new LauncherModule());
  }

  // -----------------------------------------------------------------------------------

  private void _changeColorOfAddButton(MenuBuilder menu) {
    Drawable add = menu.getActionItems().get(0).getIcon();
    add.setColorFilter(ColorFilterCache.getWhiteColorFilter());
  }

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

    @Provides
    public TaskCreateFragment provideTaskCreateFragment() {
      return new TaskCreateFragment();
    }

    @Provides
    public TaskCreateController provideTaskCreateController(TaskCreateFragment fragment) {
      return new TaskCreateController(fragment);
    }
  }
}
