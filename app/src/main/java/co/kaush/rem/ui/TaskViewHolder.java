package co.kaush.rem.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;

public class TaskViewHolder
      extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_task_desc) TextView _taskDescription;
    @InjectView(R.id.item_task_due_display) TextView _dueDateDisplay;
    @InjectView(R.id.item_task_delete) ImageView _btnDeleteTask;
    @InjectView(R.id.item_task_complete) ImageView _btnCompleteTask;
    @InjectView(R.id.item_task_container) RelativeLayout _taskContainer;

    public TaskViewHolder(View parentView) {
        super(parentView);
        ButterKnife.inject(this, parentView);
    }

    public void bindContent(final Task t, final TaskListHandler handler) {

        _taskDescription.setText(t.description);
        _dueDateDisplay.setText(CoreDateUtils.format(CoreDateUtils.DUE_DATE_FORMAT, t.dueDate));

        if (t.isOverdue()) {
            _dueDateDisplay.setTextColor(_dueDateDisplay.getResources().getColor(R.color.orange_1));
        } else {
            _dueDateDisplay.setTextColor(_dueDateDisplay.getResources().getColor(R.color.gray_2));
        }

        _btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeTask(getPosition());
            }
        });

        _btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.completeTask(getPosition());
            }
        });

        _taskContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.editTask(t);
            }
        });
    }

    public interface TaskListHandler {
        public void removeTask(int position);

        public void completeTask(int position);

        public void editTask(Task task);
    }
}