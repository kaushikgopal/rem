package co.kaush.rem.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;

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

    public void bindContent(final TaskListController controller) {
        final Task task = controller.getTask(getPosition());
        TaskListPresenter presenter = controller.getTaskListPresenter();

        int realPositionInTaskList = presenter.getPositionInTaskListFromViewPosition(//
              controller.getTasks(), getPosition());
        int colorId = presenter.getDueDayTimeColorIdFor(controller.getTasks(),
              realPositionInTaskList);

        _dueDayTime.setText(presenter.getDueDayTimeTextFor(task));
        _dueDayTime.setTextColor(_dueDayTime.getResources().getColor(colorId));
        _taskDescription.setText(presenter.getTaskDescription(task));
        _dueDateDisplayDay.setText(presenter.getDueDayTextFor(task));
        _dueDateDisplayMonth.setText(presenter.getDueMonthTextFor(task));

        _btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.removeTask(getPosition());
            }
        });

        _btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.completeTask(getPosition());
            }
        });

        _taskContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.editTask(task);
            }
        });
    }
}