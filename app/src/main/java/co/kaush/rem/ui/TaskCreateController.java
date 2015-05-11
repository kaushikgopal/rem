package co.kaush.rem.ui;

public class TaskCreateController {

    private ITalkToTaskCreateScreen _talkToTaskCreate;

    public TaskCreateController(ITalkToTaskCreateScreen talkToTaskList) {
        _talkToTaskCreate = talkToTaskList;
    }

    public interface ITalkToTaskCreateScreen {

    }
}
