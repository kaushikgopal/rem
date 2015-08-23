package co.kaush.rem.ui;

import co.kaush.rem.entity.Task;
import co.kaush.rem.entity.Task.TaskBuilder;
import co.kaush.rem.service.TaskService;
import co.kaush.rem.ui.TaskListController.ITalkToTaskListScreen;
import co.kaush.rem.util.CoreDateUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.Observable;

import static co.kaush.rem.util.CoreDateUtils.getDateFor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskListControllerTest {

    private static ITalkToTaskListScreen _talkToTLSMock;

    private static List<Task> _tasks;

    private TaskService _taskService;
    private TaskListController _controller;

    @BeforeClass
    public static void setUpOnce() {
        _tasks = new ArrayList<>();
        Task t = TaskBuilder.aTask()
              .withId(1l)
              .withDescription("Train with Bruce Lee")
              .withDueDate(getDateFor(new CoreDateUtils().now()))
              .build();
        _tasks.add(t);

        t = TaskBuilder.aTask()
              .withId(2l)
              .withDescription("Defeat Chuck Norris")
              .withDueDate(getDateFor(new CoreDateUtils().now()))
              .build();

        _tasks.add(t);
    }

    @Before
    public void setUp() throws Exception {
        _talkToTLSMock = mock(ITalkToTaskListScreen.class);
        _taskService = mock(TaskService.class);
        when(_taskService.getTaskList()).thenReturn(Observable.just(_tasks));
    }

    @Test
    public void refreshingTaskList_ShouldUpdateTasksOnView() {
        _controller = new TaskListController(_talkToTLSMock, _taskService, new CoreDateUtils());
        _controller.refreshTaskList();

        verify(_talkToTLSMock, times(1)).updateTaskList(_tasks);
    }
}