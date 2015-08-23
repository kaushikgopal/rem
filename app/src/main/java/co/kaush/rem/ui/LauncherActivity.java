package co.kaush.rem.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import co.kaush.rem.R;
import co.kaush.rem.RemModule;
import co.kaush.rem.ui.create.TaskCreateFragment;
import co.kaush.rem.ui.list.TaskListController;
import co.kaush.rem.ui.list.TaskListFragment;
import co.kaush.rem.util.ColorFilterCache;
import dagger.Module;
import dagger.Provides;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

public class LauncherActivity
      extends BaseActivity {

    @Inject TaskListFragment _taskListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                  .replace(android.R.id.content, _taskListFragment)
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
        return Collections.singletonList((Object) new LauncherModule());
    }

    // -----------------------------------------------------------------------------------

    private void _changeColorOfAddButton(MenuBuilder menu) {
        Drawable add = menu.getActionItems().get(0).getIcon();
        add.setColorFilter(ColorFilterCache.getWhiteColorFilter());
    }

    @Module(injects = { LauncherActivity.class,
                        TaskListFragment.class,
                        TaskCreateFragment.class },
          addsTo = RemModule.class,
          library = true)
    public class LauncherModule {

        @Provides
        @Singleton
        public TaskListFragment provideTaskListFragment() {
            return TaskListFragment.newInstance();
        }

        @Provides
        @Singleton
        public TaskListController.ITalkToTaskListScreen provideTaskListTalker(TaskListFragment fragment) {
            return fragment;
        }
    }
}
