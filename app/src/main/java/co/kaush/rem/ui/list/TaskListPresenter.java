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

    int cachedTodayLineSeparatorPosition = -1;
    private CoreDateUtils _coreDateUtils;

    TaskListPresenter(CoreDateUtils coreDateUtils) {
        _coreDateUtils = coreDateUtils;
    }

    public String getTaskDescription(Task task) {
        return task.description;
    }

    public String getDueDayTextFor(Task task) {
        return CoreDateUtils.format(CoreDateUtils.DUE_DATE_DAY, task.dueDate);
    }

    public String getDueDayTimeTextFor(Task task) {
        DateTime dueDate = getDateTimeFrom(task.dueDate);
        if (dueDate.getYear().intValue() == _coreDateUtils.now().getYear().intValue()) {
            return format(DUE_DATE_DAYTIME, dueDate);
        } else {
            return format(DUE_DATE_DAYTIME_WITH_YEAR, dueDate);
        }
    }

    public int getPositionInTaskListFromViewPosition(List<Task> tasks, int viewPosition) {
        if (viewPosition > getPositionForTodaySeparator(tasks)) {
            return viewPosition - 1;
        }
        return viewPosition;
    }

    @ColorRes
    public int getDueDayTimeColorIdFor(List<Task> tasks, int position) {
        Task task = tasks.get(position);

        if (task.isOverdue(_coreDateUtils)) {
            return R.color.orange_1;
        }

        boolean zebraColorFlag = false;
        int lastWeekIndex = -1;


        int dayOfWeek = _coreDateUtils.now().getWeekDay();
        DateTime dateRefForWeekIndex = _coreDateUtils.now();
        while (dayOfWeek == 1) { // 1 = SUNDAY
            dateRefForWeekIndex = dateRefForWeekIndex.minusDays(1);
            dayOfWeek--;
        }


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

    public String getDueMonthTextFor(Task task) {
        return CoreDateUtils.format(CoreDateUtils.DUE_DATE_MONTH, task.dueDate);
    }

    public void resetCachedSeparator() {
        cachedTodayLineSeparatorPosition = -1;
    }

    public int getPositionForTodaySeparator(List<Task> sortedTaskList) {

        if (cachedTodayLineSeparatorPosition == -1) {
            int todaySeparatorPosition = 0;

            for (Task task : sortedTaskList) {
                if (_coreDateUtils.isInTheFuture(task.dueDate) ||
                    _coreDateUtils.isSameDayAsToday(task.dueDate)) {
                    break;
                } else {
                    todaySeparatorPosition++;
                }
            }

            cachedTodayLineSeparatorPosition = todaySeparatorPosition;
        }

        return cachedTodayLineSeparatorPosition;
    }
}
