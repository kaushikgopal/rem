package co.kaush.rem.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.kaush.rem.R;

public class TaskListAdapter
      extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final TaskListController _controller;

    private static final int LIST_VIEW_TYPE_TASK = 0;
    private static final int LIST_VIEW_TYPE_TODAY_SEPARATOR = 1;

    public TaskListAdapter(TaskListController taskListController) {
        _controller = taskListController;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LIST_VIEW_TYPE_TODAY_SEPARATOR) {
            return new TodaySeparatorViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_today_separator, parent, false));
        }

        View itemRowView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemRowView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() != LIST_VIEW_TYPE_TODAY_SEPARATOR) {
            ((TaskViewHolder) holder).bindContent(_controller);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == _controller.getPositionForTodaySeparator()) {
            return LIST_VIEW_TYPE_TODAY_SEPARATOR;
        }

        return LIST_VIEW_TYPE_TASK;
    }

    @Override
    public int getItemCount() {

        int taskListSize = _controller.getTaskListSize();

        if (taskListSize > 0) {
            return taskListSize + 1;
        }

        return 0;
    }
}