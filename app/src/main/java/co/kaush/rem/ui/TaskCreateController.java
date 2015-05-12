package co.kaush.rem.ui;

import co.kaush.rem.util.CoreDateUtils;

public class TaskCreateController {

    public static final int NEW_TASK = -1;
    private static final String DUE_DATE_FORMAT = "MMM D [WWW] h12:mm a";

    private ITalkToTaskCreateScreen _talkToTaskCreate;
    private CoreDateUtils _coreDateUtils;

    public TaskCreateController(ITalkToTaskCreateScreen talkToTaskList,
                                CoreDateUtils coreDateUtils,
                                int initialTaskId) {
        _talkToTaskCreate = talkToTaskList;
        _coreDateUtils = coreDateUtils;

        if (initialTaskId == NEW_TASK) {
            _talkToTaskCreate.updateDueDateDisplay(_coreDateUtils.format(DUE_DATE_FORMAT,
                  _coreDateUtils.now()), "");
        }
    }

    // -----------------------------------------------------------------------------------

    public interface ITalkToTaskCreateScreen {
        void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText);
    }
}
