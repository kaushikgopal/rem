package co.kaush.rem.ui.create;

import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;
import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.PLUS;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskCreateControllerTest {

    private static TaskCreateController.ITalkToTaskCreateScreen _talkToTCSMock;
    private static CoreDateUtils _coreDateUtils = spy(new CoreDateUtils());

    private final ArgumentCaptor<String> dueDateTextCaptor = ArgumentCaptor.forClass(String.class);
    private final ArgumentCaptor<String> dueDateDiffTextCaptor = ArgumentCaptor.forClass(String.class);

    private TaskCreateController _controller;

    @Before
    public void setUpBeforeEveryTest() throws Exception {
        when(_coreDateUtils.now()).thenReturn(new DateTime(1979, 4, 3, 19, 12, 20, 0));
        // today = "Apr 3 [Tue] 7:12 PM"

        _talkToTCSMock = mock(TaskCreateController.ITalkToTaskCreateScreen.class);
        _controller = new TaskCreateController(_talkToTCSMock, _coreDateUtils);
    }

    @Test
    public void DueDateDiffText_ShouldBe_CurrentInstantDateTime_AndDiffTextNow_WhenNewTaskIdPassed() {
        verify(_talkToTCSMock).updateDueDateDisplay("Apr 3 [Tue] 7:12 PM", "now");
    }

    // -----------------------------------------------------------------------------------

    @Test
    public void IncreaseBy_1HR() {
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 8:12 PM", "(~1 Hr)");
    }

    @Test
    public void DecreaseBy_1HR() {
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay(//
              dueDateTextCaptor.capture(), dueDateDiffTextCaptor.capture());

        // argument captor by default checks last value
        assertThat(dueDateTextCaptor.getValue()).isEqualTo("Apr 3 [Tue] 6:12 PM");
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualTo("(~1 Hr back)");
    }

    @Test
    public void increaseBy1HrThenResetToNowThenIncreaseBy3Hours_ShouldShow3HourDifference() {
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 4);
        _controller.resetDueDateToNow();
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 8:12 PM",
              "(~1 Hr)");
    }

    // -----------------------------------------------------------------------------------
    // Due Date Diff Text

    @Test
    public void DueDateDiffText_ShouldShowMinutesOnly_IfIncreasedByFewMinutes() {
        _controller.changeDueDateBy(PLUS, TimeUnit.MINUTES, 10);

        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay(anyString(), dueDateDiffTextCaptor.capture());
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualTo("(~10 Mts)");
    }

    @Test
    public void DueDateDiffText_ShouldShowDaysOnly_IfDueDateIsWithin7Days() {
        _controller.changeDueDateBy(PLUS, TimeUnit.DAYS, 6);
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, atLeast(2)).updateDueDateDisplay(anyString(), dueDateDiffTextCaptor.capture());
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualTo("(~6 Dys)");
    }

    @Test
    public void DueDateDiffText_ShouldShowNow_IfDifferenceIs0() {
        _controller.changeDueDateBy(PLUS, TimeUnit.MINUTES, 15);
        _controller.changeDueDateBy(MINUS, TimeUnit.MINUTES, 15);

        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay(anyString(), dueDateDiffTextCaptor.capture());
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualTo("now");
    }
}