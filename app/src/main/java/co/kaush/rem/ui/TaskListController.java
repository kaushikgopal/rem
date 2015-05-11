package co.kaush.rem.ui;

public class TaskListController {

    private ITalkToTaskListScreen _talkToTaskList;

    public TaskListController(ITalkToTaskListScreen talkToTaskList) {
        _talkToTaskList = talkToTaskList;
    }

    public void onAddTaskClicked() {
        _talkToTaskList.moveToCreateNewTask();
    }

    public interface ITalkToTaskListScreen {
        void moveToCreateNewTask();
    }
}
