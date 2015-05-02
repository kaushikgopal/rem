package co.kaush.rem.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.MyApp;
import co.kaush.rem.R;
import co.kaush.rem.util.ColorFilterCache;
import javax.inject.Inject;

public class TaskListFragment
    extends BaseFragment
    implements TaskListController.ITalkToTaskList {

  @InjectView(R.id.toolbar) Toolbar toolbar;

  @Inject TaskListController _taskListController;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);  // enable action bar items
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_task_list, container, false);
    ButterKnife.inject(this, layout);
    _initializeToolbar();
    return layout;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.task_list_menu, menu);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    _changeColorOfAddButton((MenuBuilder) menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {

      case R.id.menu_add:
        _taskListController.onAddTaskClicked();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
  // -----------------------------------------------------------------------------------

  @Override
  public void moveToCreateNewTask() {
    Toast.makeText(MyApp.get(), "create new task", Toast.LENGTH_SHORT).show();
  }

  // -----------------------------------------------------------------------------------

  private void _initializeToolbar() {
    // enable the actionbar (so menu items get hooked on)
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    // hide title of actionbar
    ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayShowTitleEnabled(false);
    }

    // -------------------------
    // style action bar

    // add navigation icon
    Drawable nav = getResources().getDrawable(R.drawable.ic_menu);
    nav.setColorFilter(ColorFilterCache.getWhiteColorFilter());
    toolbar.setNavigationIcon(nav);
  }

  private void _changeColorOfAddButton(MenuBuilder menu) {
    Drawable add = menu.getActionItems().get(0).getIcon();
    add.setColorFilter(ColorFilterCache.getWhiteColorFilter());
  }
}
