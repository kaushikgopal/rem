package co.kaush.rem.ui;

import co.kaush.rem.entity.Task;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TaskListController {

    private static final String LIST_QUERY = "SELECT * FROM " + Task.TABLE +
                                             " ORDER BY " + Task.DUE_DATE +
                                             " ASC";

    private ITalkToTaskListScreen _talkToTaskList;
    private BriteDatabase _db;

    public TaskListController(ITalkToTaskListScreen talkToTaskList, BriteDatabase db) {
        _talkToTaskList = talkToTaskList;
        _db = db;
    }

    public void onAddTaskClicked() {
        _talkToTaskList.moveToCreateNewTask();
    }

    public void refreshTaskList() {
        _db.createQuery(Task.TABLE, LIST_QUERY)
              .map(Task.MAP)
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
