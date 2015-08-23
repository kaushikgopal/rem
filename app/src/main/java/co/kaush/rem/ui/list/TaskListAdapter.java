package co.kaush.rem.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.kaush.rem.R;

public class TaskListAdapter
      extends RecyclerView.Adapter<TaskViewHolder> {

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
        holder.bindContent(_controller);
    }

    @Override
    public int getItemCount() {
        return _controller.getTaskListSize();
    }
}