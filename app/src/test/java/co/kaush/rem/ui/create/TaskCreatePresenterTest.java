package co.kaush.rem.ui.create;

import co.kaush.rem.ui.create.TaskCreatePresenter;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TaskCreatePresenterTest {

    private CoreDateUtils _coreDateUtils = spy(new CoreDateUtils());
    private TaskCreatePresenter _taskCreatePresenter;
    private DateTime _now;

    @Before
    public void setUp() {
        _now = new DateTime(1979, 4, 3, 19, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);
        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);
    }

    @Test
    public void DueDateDiffText_ShouldShowNow_IfDueNow() {
        assertThat(_taskCreatePresenter.getDueDateDiffText(_now)).isEqualTo("now");
    }

    @Test
    public void DueDateDiffText_WheDueDateIs3MonthsBeforeToday() {
        assertThat(_taskCreatePresenter.getDueDateDiffText(_now.minusDays(90)))//
              .isEqualTo("(~3 Mths back)");
    }

    @Test
    public void DueDateDiffText_ShouldShowWeeksOnly_IfDueDateMaxDiffIsWithinAMonth() {
        assertThat(_taskCreatePresenter.getDueDateDiffText(_now.plusDays(25)))//
              .isEqualTo("(~3 Weeks)");
    }

    @Test
    public void IncreaseBy_6HRS() {
        DateTime dueDate = _now.plus(0, 0, 0, 6, 0, 0, 0, DateTime.DayOverflow.LastDay);
        assertThat(_taskCreatePresenter.getDueDateText(dueDate)).isEqualTo("Apr 4 [Wed] 1:12 AM");
        assertThat(_taskCreatePresenter.getDueDateDiffText(dueDate)).isEqualTo("(~6 Hrs)");
    }

    @Test
    public void DecreaseBy_20HRs() {
        DateTime dueDate = _now.minus(0, 0, 0, 20, 0, 0, 0, DateTime.DayOverflow.LastDay);
        assertThat(_taskCreatePresenter.getDueDateDiffText(dueDate)).isEqualTo("(~20 Hrs back)");
    }


    @Test
    public void DueDateDiffText_IncreaseBy_5HRs_ShiftsToNextDay() {
        _now = new DateTime(2015, 5, 27, 20, 0, 0, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        DateTime dueDate = _now.plus(0, 0, 0, 5, 0, 0, 0, DateTime.DayOverflow.LastDay);
        assertThat(_taskCreatePresenter.getDueDateText(dueDate)).isEqualTo("May 28 [Thu] 1:00 AM");
        assertThat(_taskCreatePresenter.getDueDateDiffText(dueDate)).isEqualTo("(~5 Hrs)");
    }

    @Test
    public void DueDateDiffText_WheDueDateIs3MonthsFromNow_AndCrossesIntoNextYear() {
        _now = new DateTime(2015, 12, 25, 7, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(_now.plusDays(90)))//
              .isEqualTo("(~3 Mths)");
    }

    @Test
    public void DueDateDiffText_WheDueDateIs3MonthsBeforeToday_AndCrossesIntoPreviousYear() {
        _now = new DateTime(2015, 12, 25, 7, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(_now.minusDays(90)))//
              .isEqualTo("(~3 Mths back)");
    }

    @Test
    public void DueDateDiffText_ShouldShowWeeksOnly_WhenDueDateIsADiffMonth_ButWithin4Weeks() {
        _now = new DateTime(2015, 5, 27, 7, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(_now.plusDays(15)))//
              .isEqualTo("(~2 Weeks)");
    }

    @Test
    public void DueDateDiffText_ShouldShowMinutesOnly_IfIncreasedBy15Minutes_AndCrossesToNextHour() {
        _now = new DateTime(2015, 12, 25, 4, 50, 0, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(//
              _now.plus(0, 0, 0, 0, 15, 0, 0, DateTime.DayOverflow.LastDay)))//
              .isEqualTo("(~15 Mts)");
    }

    @Test
    public void DueDateDiffText_ShouldShowMinutesOnly_IfDecreasedBy15Minutes_AndCrossesToPreviousHour() {
        _now = new DateTime(2015, 12, 25, 9, 0, 0, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(//
              _now.minus(0, 0, 0, 0, 15, 0, 0, DateTime.DayOverflow.LastDay)))//
              .isEqualTo("(~15 Mts back)");
    }

    @Test
    public void DueDateDiffText_ShouldShow1Day_IfDecreasedBy24HrsExactly() {
        _now = new DateTime(1979, 4, 3, 19, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(//
              _now.minus(0, 0, 0, 24, 0, 0, 0, DateTime.DayOverflow.LastDay)))//
              .isEqualTo("(~1 Dy back)");
    }

    @Test
    public void DueDateDiffText_ShouldShow1Day_IfIncreasedBy24HrsExactly() {
        _now = new DateTime(1979, 4, 3, 19, 12, 20, 0);
        when(_coreDateUtils.now()).thenReturn(_now);

        _taskCreatePresenter = new TaskCreatePresenter(_coreDateUtils);

        assertThat(_taskCreatePresenter.getDueDateDiffText(//
              _now.plus(0, 0, 0, 24, 0, 0, 0, DateTime.DayOverflow.LastDay)))//
              .isEqualTo("(~1 Dy)");
    }
}