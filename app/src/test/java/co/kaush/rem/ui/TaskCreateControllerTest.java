package co.kaush.rem.ui;

import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskCreateControllerTest {

    private TaskCreateController _controller;
    private static TaskCreateController.ITalkToTaskCreateScreen _talkToTCSMock;
    private static CoreDateUtils _coreDateUtils;

    @BeforeClass
    public static void setup1Time() {
        _coreDateUtils = spy(new CoreDateUtils());
        when(_coreDateUtils.now()).thenReturn(new DateTime(1979, 4, 3, 7, 12, 20, 0));
        _talkToTCSMock = mock(TaskCreateController.ITalkToTaskCreateScreen.class);
    }

    @Test
    public void updateDisplay_WithCurrentInstantDateTime_AndDiffTextNow_WhenNewTaskIdPassed() {
        _controller = new TaskCreateController(_talkToTCSMock,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);

        verify(_talkToTCSMock).updateDueDateDisplay("Apr 3 [Tue] 7:12 AM", "now");
    }
}