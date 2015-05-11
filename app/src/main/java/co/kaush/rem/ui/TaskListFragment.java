package co.kaush.rem.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.R;
import co.kaush.rem.util.ColorFilterCache;

public class TaskListFragment
      extends BaseFragment
      implements TaskListController.ITalkToTaskListScreen {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    private TaskListController _taskListController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  // enable action bar items
        _taskListController = new TaskListController(this);
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
    // ITalkToTaskList implementation

    @Override
    public void moveToCreateNewTask() {
        getActivity().getSupportFragmentManager()
              .beginTransaction()
              .hide(this)
              .add(android.R.id.content, new TaskCreateFragment(), TaskCreateFragment.FRAG_TAG)
              .addToBackStack(TaskCreateFragment.FRAG_TAG)
              .commit();
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

        // add navigation icon
        Drawable nav = getResources().getDrawable(R.drawable.ic_menu);
        nav.setColorFilter(ColorFilterCache.getWhiteColorFilter());
        toolbar.setNavigationIcon(nav);
    }
}
