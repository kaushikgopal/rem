package co.kaush.rem.ui;

import co.kaush.rem.util.CoreDateUtils;
import co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease;
import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;
import java.util.concurrent.TimeUnit;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;

public class TaskCreateController {

    public static final int NEW_TASK = -1;
    private static final String DUE_DATE_FORMAT = "MMM D [WWW] h12:mm a";

    private ITalkToTaskCreateScreen _talkToTaskCreate;
    private CoreDateUtils _coreDateUtils;
    private DateTime _dueDateTime = null;

    public TaskCreateController(ITalkToTaskCreateScreen talkToTaskList,
                                CoreDateUtils coreDateUtils,
                                int initialTaskId) {
        _talkToTaskCreate = talkToTaskList;
        _coreDateUtils = coreDateUtils;

        if (initialTaskId == NEW_TASK) {
            _talkToTaskCreate.updateDueDateDisplay(//
                  _coreDateUtils.format(DUE_DATE_FORMAT, _coreDateUtils.now()), "now");
        }
    }

    public void changeDueDateBy(IncreaseOrDecrease increaseOrDecrease,
                                TimeUnit timeUnit,
                                int quantity) {

        DayOverflow overflow = DayOverflow.LastDay;
        int dys = 0, hrs = 0, mts = 0;

        switch (timeUnit) {
            case DAYS:
                dys += quantity;
                break;
            case MINUTES:
                mts += quantity;
                break;
            case HOURS:
                hrs += quantity;
        }

        if (_dueDateTime == null) {
            _dueDateTime = _coreDateUtils.now();
        }

        if (increaseOrDecrease == MINUS) {
            _dueDateTime = _dueDateTime.minus(0, 0, dys, hrs, mts, 0, 0, overflow);
        } else {
            _dueDateTime = _dueDateTime.plus(0, 0, dys, hrs, mts, 0, 0, overflow);
        }

        _talkToTaskCreate.updateDueDateDisplay(//
              _coreDateUtils.format(DUE_DATE_FORMAT, _dueDateTime), _getDueDateDiffText());
    }

    // -----------------------------------------------------------------------------------

    private String _getDueDateDiffText() {
        int diffValue;

        // Check if max diff is in Months
        diffValue = _dueDateTime.getMonth() - _coreDateUtils.now().getMonth();
        if (Math.abs(diffValue) > 1) {
            return _getDiffText("Mth", diffValue);
        }

        // Check if max diff is in Weeks
        diffValue = _dueDateTime.getWeekIndex() - _coreDateUtils.now().getWeekIndex();
        if (Math.abs(diffValue) > 1) {
            return _getDiffText("Week", diffValue);
        }

        // Check if max diff is in Days
        diffValue = _dueDateTime.getDayOfYear() - _coreDateUtils.now().getDayOfYear();
        if (diffValue != 0) {
            return _getDiffText("Dy", diffValue);
        }

        // Check if max diff is in Hours
        diffValue = _dueDateTime.getHour() - _coreDateUtils.now().getHour();
        if (diffValue != 0) {
            return _getDiffText("Hr", diffValue);
        }

        return "now";
    }

    private String _getDiffText(String unit, int diffValue) {
        if (diffValue > 0) {
            return String.format("in %d %s%s",
                  Math.abs(diffValue),
                  unit,
                  (diffValue > 1) ? "s" : "");
        } else {
            return String.format("%d %s%s before",
                  Math.abs(diffValue),
                  unit,
                  (diffValue > 1) ? "s" : "");
        }
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskCreateScreen {
        void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText);
    }
}
