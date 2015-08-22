package co.kaush.rem.ui;

import android.support.annotation.ColorRes;
import co.kaush.rem.entity.Task;
import co.kaush.rem.service.TaskService;
import co.kaush.rem.util.CoreDateUtils;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class TaskListController {

    private ITalkToTaskListScreen _talkToTaskList;
    private TaskService _taskService;
    private TaskListPresenter _taskListPresenter;

    @Inject
    public TaskListController(ITalkToTaskListScreen talkToTaskList,
                              TaskService taskService,
                              CoreDateUtils coreDateUtils) {
        _talkToTaskList = talkToTaskList;
        _taskService = taskService;
        _taskListPresenter = new TaskListPresenter(coreDateUtils);
    }

    public void onAddTaskClicked() {
        _talkToTaskList.moveToCreateNewTask();
    }

    public void refreshTaskList() {
        _taskService.getTaskList()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<List<Task>>() {
                  @Override
                  public void call(List<Task> tasks) {
                      Timber.d("%d tasks", tasks.size());
                      _talkToTaskList.updateTaskList(tasks);
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
    // Delegate these to presenter

    public String getDueDayTextFor(Task task) {
        return _taskListPresenter.getDueDateTextFor(task);
    }

    @ColorRes public int getDueDayTimeColorIdFor(Task task) {
        return _taskListPresenter.getDueDayTimeColorIdFor(task);
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskListScreen {
        void moveToCreateNewTask();

        void updateTaskList(List<Task> tasks);
    }
}
