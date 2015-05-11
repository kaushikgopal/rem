package co.kaush.rem.ui;

public class TaskListController {

    private ITalkToTaskList _talkToTaskList;

    public TaskListController(ITalkToTaskList talkToTaskList) {
        _talkToTaskList = talkToTaskList;
    }

    public void onAddTaskClicked() {
        _talkToTaskList.moveToCreateNewTask();
    }

    public interface ITalkToTaskList {
        void moveToCreateNewTask();
    }
}
