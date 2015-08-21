package co.kaush.rem.ui;

import co.kaush.rem.entity.Task;
import co.kaush.rem.service.TaskService;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TaskListController {

    private ITalkToTaskListScreen _talkToTaskList;
    private TaskService _taskService;

    public TaskListController(ITalkToTaskListScreen talkToTaskList, TaskService taskService) {
        _talkToTaskList = talkToTaskList;
        _taskService = taskService;
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

    public interface ITalkToTaskListScreen {
        void moveToCreateNewTask();
        void updateTaskList(List<Task> tasks);
    }
}
