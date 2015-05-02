package co.kaush.rem.ui;

public class TaskCreateController {

  private ITalkToTaskCreate _talkToTaskCreate;

  public TaskCreateController(ITalkToTaskCreate talkToTaskList) {
    _talkToTaskCreate = talkToTaskList;
  }

  public interface ITalkToTaskCreate {

  }
}
