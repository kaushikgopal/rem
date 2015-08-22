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
    @InjectView(R.id.item_task_due_display_date) TextView _dueDateDisplayDay;
    @InjectView(R.id.item_task_due_display_month) TextView _dueDateDisplayMonth;
    @InjectView(R.id.item_task_due_display_day_time) TextView _dueDayTime;
    @InjectView(R.id.item_task_delete) ImageView _btnDeleteTask;
    @InjectView(R.id.item_task_complete) ImageView _btnCompleteTask;
    @InjectView(R.id.item_task_container) RelativeLayout _taskContainer;

    public TaskViewHolder(View parentView) {
        super(parentView);
        ButterKnife.inject(this, parentView);
    }

    public void bindContent(final Task t,
                            final TaskListHandler handler,
                            final TaskListController controller) {

        _taskDescription.setText(t.description);
        _dueDateDisplayDay.setText(CoreDateUtils.format(CoreDateUtils.DUE_DATE_DAY, t.dueDate));
        _dueDateDisplayMonth.setText(CoreDateUtils.format(CoreDateUtils.DUE_DATE_MONTH, t.dueDate));
        _dueDayTime.setText(controller.getDueDayTextFor(t));

        //if (t.isOverdue()) {
        //    _dueDateDisplay.setTextColor(_dueDateDisplay.getResources().getColor(R.color.orange_1));
        //} else {
        //    _dueDateDisplay.setTextColor(_dueDateDisplay.getResources().getColor(R.color.gray_2));
        //}

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
        void removeTask(int position);

        void completeTask(int position);

        void editTask(Task task);
    }
}