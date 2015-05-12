package co.kaush.rem.ui;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.R;
import co.kaush.rem.util.CoreDateUtils;
import javax.inject.Inject;

public class TaskCreateFragment
      extends BaseFragment
      implements TaskCreateController.ITalkToTaskCreateScreen {

    public static final String FRAG_TAG = "co.kaush.rem.ui.TaskCreateFragment";

    @InjectView(R.id.btn_task_due_date_display) TextView _dueDisplay;

    @Inject CoreDateUtils _coreDateUtils;

    private TaskCreateController _taskCreateController;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _taskCreateController = new TaskCreateController(TaskCreateFragment.this,
              _coreDateUtils,
              TaskCreateController.NEW_TASK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_task_create, container, false);
        ButterKnife.inject(this, layout);
        return layout;
    }

    // -----------------------------------------------------------------------------------
    // interface implementations

    @Override
    public void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText) {
        _dueDisplay.setText(_getFormattedDueDateDisplayText(dueDateText, dueDiffDisplayText));
    }

    // -----------------------------------------------------------------------------------

    private Spannable _getFormattedDueDateDisplayText(String dueDateText,
                                                      String dueDateDisplayText) {
        Spannable sb = new SpannableString(dueDateText + " " + dueDateDisplayText);
        sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_3)),
              dueDateText.length(),
              sb.length(),
              Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
