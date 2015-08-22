package co.kaush.rem.ui;

import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;

import static co.kaush.rem.util.CoreDateUtils.DUE_DATE_DAYTIME;
import static co.kaush.rem.util.CoreDateUtils.DUE_DATE_DAYTIME_WITH_YEAR;
import static co.kaush.rem.util.CoreDateUtils.format;
import static co.kaush.rem.util.CoreDateUtils.getDateTimeFor;

public class TaskListPresenter {
    private CoreDateUtils _coreDateUtils;

    TaskListPresenter(CoreDateUtils coreDateUtils) {
        _coreDateUtils = coreDateUtils;
    }

    public String getDueDateTextFor(Task task) {
        DateTime dueDate = getDateTimeFor(task.dueDate);
        if (dueDate.getYear().intValue() == _coreDateUtils.now().getYear().intValue()) {
            return format(DUE_DATE_DAYTIME, dueDate);
        } else {
            return format(DUE_DATE_DAYTIME_WITH_YEAR, dueDate);
        }
    }

    public int getDueDayTimeColorIdFor(Task task) {
        if (task.isOverdue(_coreDateUtils)) {
            return R.color.orange_1;
        }

        return R.color.gray_2;
    }
}
