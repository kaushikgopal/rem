package co.kaush.rem.ui;

import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;
import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.PLUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskCreateControllerTest {

    private static TaskCreateController.ITalkToTaskCreateScreen _talkToTCSMock;
    private static CoreDateUtils _coreDateUtils;
    private TaskCreateController _controller;

    @BeforeClass
    public static void setupOnceBeforeAllTests() {
        _coreDateUtils = spy(new CoreDateUtils());
        when(_coreDateUtils.now()).thenReturn(new DateTime(1979, 4, 3, 7, 12, 20, 0));
        // "Apr 3 [Tue] 7:12 AM"

    }

    @Before
    public void setUpBeforeEveryTest() throws Exception {
        _talkToTCSMock = mock(TaskCreateController.ITalkToTaskCreateScreen.class);

        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);
    }

    @Test
    public void updateDisplay_WithCurrentInstantDateTime_AndDiffTextNow_WhenNewTaskIdPassed() {
        verify(_talkToTCSMock).updateDueDateDisplay("Apr 3 [Tue] 7:12 AM", "now");
    }

    @Test
    public void IncreaseBy_1HR() {
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 1);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 8:12 AM",
              "in 1 Hr");
    }

    @Test
    public void IncreaseBy_5HRS() {
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 5);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 12:12 PM",
              "in 5 Hrs");
    }

    @Test
    public void DecreaseBy_1HR() {
        ArgumentCaptor<String> dueDateTextCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dueDateDiffTextCaptor = ArgumentCaptor.forClass(String.class);

        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, times(2)).updateDueDateDisplay(//
              dueDateTextCaptor.capture(), dueDateDiffTextCaptor.capture());
        assertThat(dueDateTextCaptor.getValue()).isEqualToIgnoringCase("Apr 3 [Tue] 6:12 AM");
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualToIgnoringCase("1 Hr before");
    }

    @Test
    public void SubsequentChanges_3HRS_Increase_Then1HR_Decrease() {
        ArgumentCaptor<String> dueDateTextCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dueDateDiffTextCaptor = ArgumentCaptor.forClass(String.class);

        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 3);
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, times(3)).updateDueDateDisplay(//
              dueDateTextCaptor.capture(), dueDateDiffTextCaptor.capture());

        // argument captor by default checks last value
        assertThat(dueDateTextCaptor.getValue()).isEqualToIgnoringCase("Apr 3 [Tue] 9:12 AM");
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualToIgnoringCase("in 2 Hrs");
    }

    @Test
    public void DueDateDiffText_ShouldShowDaysOnly_IfDueDateIsWithin7Days() {
        ArgumentCaptor<String> dueDateTextCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dueDateDiffTextCaptor = ArgumentCaptor.forClass(String.class);

        _controller.changeDueDateBy(PLUS, TimeUnit.DAYS, 6);
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);

        verify(_talkToTCSMock, times(3)).updateDueDateDisplay(//
              dueDateTextCaptor.capture(), dueDateDiffTextCaptor.capture());

        assertThat(dueDateTextCaptor.getValue()).isEqualToIgnoringCase("Apr 9 [Mon] 6:12 AM");
        assertThat(dueDateDiffTextCaptor.getValue()).isEqualToIgnoringCase("in 6 Dys");
    }
}