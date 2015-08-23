package co.kaush.rem.ui.list;

import android.support.annotation.ColorRes;
import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import java.util.List;

import static co.kaush.rem.util.CoreDateUtils.DUE_DATE_DAYTIME;
import static co.kaush.rem.util.CoreDateUtils.DUE_DATE_DAYTIME_WITH_YEAR;
import static co.kaush.rem.util.CoreDateUtils.format;
import static co.kaush.rem.util.CoreDateUtils.getDateTimeFrom;

public class TaskListPresenter {
    private CoreDateUtils _coreDateUtils;

    TaskListPresenter(CoreDateUtils coreDateUtils) {
        _coreDateUtils = coreDateUtils;
    }

    public String getDueDayTimeTextFor(Task task) {
        DateTime dueDate = getDateTimeFrom(task.dueDate);
        if (dueDate.getYear().intValue() == _coreDateUtils.now().getYear().intValue()) {
            return format(DUE_DATE_DAYTIME, dueDate);
        } else {
            return format(DUE_DATE_DAYTIME_WITH_YEAR, dueDate);
        }
    }

    @ColorRes
    public int getDueDayTimeColorIdFor(List<Task> tasks, int position) {
        Task task = tasks.get(position);

        if (task.isOverdue(_coreDateUtils)) {
            return R.color.orange_1;
        }

        boolean zebraColorFlag = false;
        int lastWeekIndex = -1;

        DateTime dateRefForWeekIndex = getDateTimeFrom(tasks.get(0).dueDate);
        DateTime dueDateForTaskInLoop;

        for (int i = position; i >= 0; i--) {
            dueDateForTaskInLoop = getDateTimeFrom(tasks.get(i).dueDate);

            int weekIndexForThisTaskDueDate = dueDateForTaskInLoop.getWeekIndex(dateRefForWeekIndex);
            if (lastWeekIndex != weekIndexForThisTaskDueDate) {
                lastWeekIndex = weekIndexForThisTaskDueDate;
                zebraColorFlag = !zebraColorFlag;
            }
        }

        if (!zebraColorFlag) {
            return R.color.blue;
        }

        return R.color.gray_2;
    }
}
