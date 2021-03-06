package co.kaush.rem.ui.list;

import co.kaush.rem.entity.Task;
import co.kaush.rem.service.TaskService;
import co.kaush.rem.util.CoreDateUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class TaskListController {

    private TaskService _taskService;

    private ITalkToTaskListScreen _talkToTaskList;
    private TaskListPresenter _taskListPresenter;

    private List<Task> _tasks = new ArrayList<>();
    private Subscription _subscription;

    @Inject
    public TaskListController(ITalkToTaskListScreen talkToTaskList,
                              TaskService taskService,
                              CoreDateUtils coreDateUtils) {
        _talkToTaskList = talkToTaskList;
        _taskService = taskService;
        _taskListPresenter = new TaskListPresenter(coreDateUtils);
        _subscription = null;
    }

    // -----------------------------------------------------------------------------------

    public TaskListPresenter getTaskListPresenter() {
        return _taskListPresenter;
    }

    public List<Task> getTasks() {
        return _tasks;
    }

    public int getTaskListSize() {
        return _tasks.size();
    }

    public Task getTask(int position) {
        int positionInTaskList = _taskListPresenter.getPositionInTaskListFromViewPosition(_tasks,
              position);
        return _tasks.get(positionInTaskList);
    }

    public int getPositionForTodaySeparator() {
        return _taskListPresenter.getPositionForTodaySeparator(_tasks);
    }

    // -----------------------------------------------------------------------------------
    // actions

    public void onAddTaskClicked() {
        _talkToTaskList.moveToCreateNewTask();
    }

    public void refreshTaskList() {
        _subscription = null;
        _subscription = _taskService.getTaskList()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<List<Task>>() {
                  @Override
                  public void call(List<Task> tasks) {
                      Timber.d("%d tasks", tasks.size());
                      _tasks = tasks;
                      _talkToTaskList.updateTaskList(tasks);
                      _taskListPresenter.resetCachedSeparator();
                      _subscription.unsubscribe();
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      Timber.e(throwable, "Error in retrieving tasks from Database");
                  }
              });

    }

    public void removeTask(final int position) {
        Timber.d("TODO: remove task");
    }

    public void completeTask(int position) {
        Timber.d("TODO: complete task");
    }

    public void editTask(Task task) {
        Timber.d("TODO: edit task");
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskListScreen {
        void moveToCreateNewTask();

        void updateTaskList(List<Task> tasks);
    }
}
