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
        DateTime now = _coreDateUtils.now();

        // Check if max diff is in Months
        diffValue = _dueDateTime.getMonth() - now.getMonth();
        if (Math.abs(diffValue) > 1) {
            return _getPluralizedDiffText("Mth", diffValue);
        }

        // Check if max diff is in Weeks
        diffValue = _dueDateTime.getWeekIndex() - now.getWeekIndex();
        if (Math.abs(diffValue) > 1) {
            return _getPluralizedDiffText("Week", diffValue);
        }

        // Check if max diff is in Days
        diffValue = _dueDateTime.getDayOfYear() - now.getDayOfYear();
        if (Math.abs(diffValue) > 1) {
            return _getPluralizedDiffText("Dy", diffValue);
        }

        // Check if max diff is in Hours
        diffValue = _dueDateTime.getHour() - now.getHour();
        if (diffValue != 0) {
            if (_dueDateTime.isSameDayAs(now)) {
                return _getPluralizedDiffText("Hr", diffValue);
            } else {

                if (_dueDateTime.isInTheFuture(_coreDateUtils.getUtcTimeZone())) {
                    return _getPluralizedDiffText("Hr", diffValue + 24);
                } else {
                    return _getPluralizedDiffText("Hr", diffValue - 24);
                }
            }
        }

        return "now";
    }

    /**
     * 1. Takes into account if the text needs to be pluralized
     * 2. if due date is after now then prepends "in X Hr(s)" else "X Hr(s) before"
     *
     * @param diffValue real number of hours between due date and now (-v if due is before now)
     */
    private String _getPluralizedDiffText(String unit, int diffValue) {
        boolean pluralize = Math.abs(diffValue) > 1;

        if (diffValue > 0) {
            return String.format("in %d %s%s", Math.abs(diffValue), unit, (pluralize) ? "s" : "");
        } else {
            return String.format("%d %s%s before",
                  Math.abs(diffValue),
                  unit,
                  (pluralize) ? "s" : "");
        }
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskCreateScreen {
        void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText);
    }
}
