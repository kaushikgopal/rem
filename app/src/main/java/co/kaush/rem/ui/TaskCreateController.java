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
              _coreDateUtils.format(DUE_DATE_FORMAT, _dueDateTime), "");
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskCreateScreen {
        void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText);
    }
}
