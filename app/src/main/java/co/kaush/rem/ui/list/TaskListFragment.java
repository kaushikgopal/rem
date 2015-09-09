package co.kaush.rem.ui.list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import co.kaush.rem.ui.BaseFragment;
import co.kaush.rem.ui.create.TaskCreateFragment;
import co.kaush.rem.util.ColorFilterCache;

public class TaskListFragment
      extends BaseFragment
      implements TaskListController.ITalkToTaskListScreen {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.task_list) RecyclerView taskList;

    @Inject TaskListController _taskListController;

    private TaskListAdapter _adapter;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

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
        ButterKnife.bind(this, layout);
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

    @Override
    public void onResume() {
        super.onResume();

        _adapter = new TaskListAdapter(_taskListController);

        taskList.setAdapter(_adapter);
        taskList.setHasFixedSize(true);
        taskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskList.setItemAnimator(new DefaultItemAnimator());

        _taskListController.refreshTaskList();
    }

    // -----------------------------------------------------------------------------------
    // ITalkToTaskList implementation

    @Override
    public void updateTaskList(List<Task> tasks) {
        _adapter.notifyDataSetChanged();
    }

    @Override
    public void moveToCreateNewTask() {
        getActivity().getSupportFragmentManager()
              .beginTransaction()
              .hide(this)
              .add(android.R.id.content,
                    TaskCreateFragment.newInstance(),
                    TaskCreateFragment.FRAG_TAG)
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
