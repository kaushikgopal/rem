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
            resetDueDateToNow();
        }
    }

    public void resetDueDateToNow() {
        _talkToTaskCreate.updateDueDateDisplay(//
              _coreDateUtils.format(DUE_DATE_FORMAT, _coreDateUtils.now()), "now");
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

    public void setTimeTo(int hour, int minute) {
        if (_dueDateTime == null) {
            _dueDateTime = _coreDateUtils.now();
        }

        _dueDateTime = new DateTime(_dueDateTime.getYear(),
              _dueDateTime.getMonth(),
              _dueDateTime.getDay(),
              hour,
              minute,
              0,
              0);

        _talkToTaskCreate.updateDueDateDisplay(//
              _coreDateUtils.format(DUE_DATE_FORMAT, _dueDateTime), _getDueDateDiffText());
    }

    // -----------------------------------------------------------------------------------

    private String _getDueDateDiffText() {
        DateTime now = _coreDateUtils.now();
        int diffValue;

        switch (_coreDateUtils.getDiffUnit(now, _dueDateTime)) {
            case MONTH:
                if (_coreDateUtils.isAfterNow(_dueDateTime)) {
                    diffValue = _dueDateTime.getMonth() - now.getMonth();
                    if (diffValue < 0) {
                        diffValue += 12;
                    }
                } else {
                    diffValue = _dueDateTime.getMonth() - now.getMonth();
                }
                return _getPluralizedDiffText("Mth", diffValue);

            case WEEK:
                // If you hit a date before Sunday, January 2, 2000 you're probably going to get funky results courtesy: date4j
                diffValue = _dueDateTime.getWeekIndex() - now.getWeekIndex();
                return _getPluralizedDiffText("Week", diffValue);

            case DAY:
                if (_coreDateUtils.isAfterNow(_dueDateTime)) {
                    diffValue = _dueDateTime.getDayOfYear() - now.getDayOfYear();
                    if (diffValue < 0) {
                        diffValue += _coreDateUtils.getLastDayOfTheYear(_dueDateTime);
                    }
                } else {
                    diffValue = _dueDateTime.getDayOfYear() - now.getDayOfYear();
                }

                return _getPluralizedDiffText("Dy", diffValue);

            case HOUR:
                if (_coreDateUtils.isAfterNow(_dueDateTime)) {
                    diffValue = _dueDateTime.getHour() - now.getHour();
                    if (diffValue < 0) {
                        diffValue += 24;
                    }
                } else {
                    if (_dueDateTime.isSameDayAs(_coreDateUtils.now())) {
                        diffValue = _dueDateTime.getHour() - now.getHour();
                    } else {
                        diffValue = _dueDateTime.getHour() - now.getHour() - 24;
                    }
                }

                return _getPluralizedDiffText("Hr", diffValue);
        }

        diffValue = _dueDateTime.getMinute() - now.getMinute();

        if (!_dueDateTime.getHour().equals(now.getHour())) {

            if (_coreDateUtils.isAfterNow(_dueDateTime)) {
                diffValue += 60;
            } else {
                diffValue -= 60;
            }
        }

        return _getPluralizedDiffText("Mt", diffValue);
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
            return String.format("(~%d %s%s)", Math.abs(diffValue), unit, (pluralize) ? "s" : "");
        } else {
            return String.format("(~%d %s%s back)",
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
