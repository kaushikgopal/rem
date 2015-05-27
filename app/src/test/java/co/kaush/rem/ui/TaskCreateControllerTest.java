package co.kaush.rem.ui;

import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;
import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.PLUS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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

    }

    @Test
    public void updateDisplay_WithCurrentInstantDateTime_AndDiffTextNow_WhenNewTaskIdPassed() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);

        verify(_talkToTCSMock).updateDueDateDisplay("Apr 3 [Tue] 7:12 AM", "now");
    }

    @Test
    public void IncreaseBy_1HR() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 1);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 8:12 AM",
              "in 1 Hr");
    }

    @Ignore
    @Test
    public void IncreaseBy_5HRS() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);
        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 5);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 12:12 PM",
              "in 5 Hrs");
    }

    @Ignore
    @Test
    public void DecreaseBy_1HR() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 6:12 AM", "");
        //verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 6:12 AM", "1 Hr before");
    }

    @Ignore
    @Test
    public void SubsequentChanges_3HRS_Then1HR_Decrease() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);

        _controller.changeDueDateBy(PLUS, TimeUnit.HOURS, 3);
        _controller.changeDueDateBy(MINUS, TimeUnit.HOURS, 1);
        verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 9:12 AM", "");
        //verify(_talkToTCSMock, atLeastOnce()).updateDueDateDisplay("Apr 3 [Tue] 9:12 AM", "in 2 Hrs");
    }

}