package co.kaush.rem.ui.create;

import co.kaush.rem.util.CoreDateUtils;
import co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease;
import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;

public class TaskCreateController {

    private CoreDateUtils _coreDateUtils;

    private ITalkToTaskCreateScreen _talkToTaskCreate;
    private TaskCreatePresenter _taskCreatePresenter;

    private DateTime _dueDateTime = null;

    @Inject
    public TaskCreateController(ITalkToTaskCreateScreen talkToTaskList,
                                CoreDateUtils coreDateUtils) {
        _talkToTaskCreate = talkToTaskList;
        _coreDateUtils = coreDateUtils;
        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);
        resetDueDateToNow();
    }

    public void resetDueDateToNow() {
        _talkToTaskCreate.updateDueDateDisplay(//
              CoreDateUtils.format(CoreDateUtils.DUE_DATE_FORMAT, _coreDateUtils.now()), "now");
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
              _taskCreatePresenter.getDueDateText(_dueDateTime),
              _taskCreatePresenter.getDueDateDiffText(_dueDateTime));
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
              _taskCreatePresenter.getDueDateText(_dueDateTime),
              _taskCreatePresenter.getDueDateDiffText(_dueDateTime));
    }

    public void onCancelButtonClicked() {
        _talkToTaskCreate.closeCreateScreen();
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskCreateScreen {
        void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText);

        void closeCreateScreen();
    }
}
