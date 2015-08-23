package co.kaush.rem.ui.list;

import co.kaush.rem.entity.Task;
import co.kaush.rem.entity.Task.TaskBuilder;
import co.kaush.rem.service.TaskService;
import co.kaush.rem.ui.list.TaskListController.ITalkToTaskListScreen;
import co.kaush.rem.util.CoreDateUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static co.kaush.rem.util.CoreDateUtils.getDateFor;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
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

        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaTestSchedulerHook());
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidTestSchedulerHook());

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
        // cool but possibly flaky for future (checking that the only method called was updateTaskList)
        verify(_talkToTLSMock, only()).updateTaskList(_tasks);
    }

    @Test
    public void refreshingTaskList_ShouldResetCachedLineSeparatorPosition() {
        _controller = new TaskListController(_talkToTLSMock, _taskService, new CoreDateUtils());

        assertThat(_controller.getTaskListPresenter().cachedTodayLineSeparatorPosition)//
              .isEqualTo(-1);

        _controller.refreshTaskList();
        assertThat(_controller.getTaskListPresenter().getPositionForTodaySeparator(_tasks))//
              .isNotEqualTo(-1);
        assertThat(_controller.getTaskListPresenter().cachedTodayLineSeparatorPosition)//
              .isNotEqualTo(-1);
        _controller.refreshTaskList();
        assertThat(_controller.getTaskListPresenter().cachedTodayLineSeparatorPosition)//
              .isEqualTo(-1);
    }

    @Ignore
    @Test
    public void editingTask_ShouldResetCachedLineSeparatorPosition() {
    }

    @Ignore
    @Test
    public void removingTask_ShouldResetCachedLineSeparatorPosition() {
    }

    @Ignore
    @Test
    public void completingTask_ShouldResetCachedLineSeparatorPosition() {
    }

    private static class RxAndroidTestSchedulerHook
          extends RxAndroidSchedulersHook {
        @Override
        public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    }

    private static class RxJavaTestSchedulerHook
          extends RxJavaSchedulersHook {
        @Override
        public Scheduler getComputationScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }
    }
}