package co.kaush.rem.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class TaskListAdapter
      extends RecyclerView.Adapter<TaskViewHolder>
      implements TaskViewHolder.TaskListHandler {

    private List<Task> _tasks = new ArrayList<>();
    private final TaskListController _controller;

    public TaskListAdapter(TaskListController taskListController) {
        _controller = taskListController;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRowView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemRowView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // TODO: remove taskListAdapter, and just send it to the controller
        holder.bindContent(_tasks.get(position), TaskListAdapter.this, _controller);
    }

    @Override
    public int getItemCount() {
        return _tasks.size();
    }

    @Override
    public void removeTask(final int position) {
        Timber.d("TODO: remove task");
    }

    @Override
    public void completeTask(int position) {
        Timber.d("TODO: complete task");
    }

    @Override
    public void editTask(Task task) {
        Timber.d("TODO: edit task");
    }

    public void updateTasks(List<Task> tasks) {
        _tasks = tasks;
        notifyDataSetChanged();
    }
}