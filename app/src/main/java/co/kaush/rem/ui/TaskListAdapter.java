package co.kaush.rem.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter
      extends RecyclerView.Adapter<TaskViewHolder>{

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
        holder.bindContent(_tasks.get(position), _controller);
    }

    @Override
    public int getItemCount() {
        return _tasks.size();
    }

    public void updateTasks(List<Task> tasks) {
        _tasks = tasks;
        notifyDataSetChanged();
    }
}