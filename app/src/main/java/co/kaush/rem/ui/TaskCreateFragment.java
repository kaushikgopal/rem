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
import butterknife.OnClick;
import co.kaush.rem.R;
import co.kaush.rem.util.CoreDateUtils;
import javax.inject.Inject;

import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.MINUS;
import static co.kaush.rem.util.CoreDateUtils.IncreaseOrDecrease.PLUS;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class TaskCreateFragment
      extends BaseFragment
      implements TaskCreateController.ITalkToTaskCreateScreen {

    public static final String FRAG_TAG = "co.kaush.rem.ui.TaskCreateFragment";

    @InjectView(R.id.btn_task_due_date_display) TextView _dueDisplay;

    @Inject CoreDateUtils _coreDateUtils;

    private TaskCreateController _taskCreateController;

    public static TaskCreateFragment newInstance() {
        return new TaskCreateFragment();
    }

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

    @OnClick(R.id.btn_task_cancel)
    public void onCancelButtonClicked() {
        _taskCreateController.onCancelButtonClicked();
    }

    // -----------------------------------------------------------------------------------
    // Button click listeners
    // TODO: DRY this code up

    @OnClick(R.id.increase_hr)
    public void onIncreaseBy1HrClicked() {
        _taskCreateController.changeDueDateBy(PLUS, HOURS, 1);
    }

    @OnClick(R.id.decrease_hr)
    public void onDecreaseBy1HrClicked() {
        _taskCreateController.changeDueDateBy(MINUS, HOURS, 1);
    }

    @OnClick(R.id.increase_3_hr)
    public void onIncreaseBy3HrClicked() {
        _taskCreateController.changeDueDateBy(PLUS, HOURS, 3);
    }

    @OnClick(R.id.decrease_3_hr)
    public void onDecreaseBy3HrClicked() {
        _taskCreateController.changeDueDateBy(MINUS, HOURS, 3);
    }

    @OnClick(R.id.increase_10_mt)
    public void increase_10_Mt() {
        _taskCreateController.changeDueDateBy(PLUS, MINUTES, 10);
    }

    @OnClick(R.id.decrease_10_mt)
    public void decrease_10_Mt() {
        _taskCreateController.changeDueDateBy(MINUS, MINUTES, 10);
    }

    @OnClick(R.id.increase_1_dy)
    public void increase_1_Dy() {
        _taskCreateController.changeDueDateBy(PLUS, DAYS, 1);
    }

    @OnClick(R.id.decrease_1_dy)
    public void decrease_1_Dy() {
        _taskCreateController.changeDueDateBy(MINUS, DAYS, 1);
    }

    @OnClick(R.id.set_8_am)
    public void set_8_am() {
        _taskCreateController.setTimeTo(8, 0);
    }

    @OnClick(R.id.set_1030_am)
    public void set_10_30_am() {
        _taskCreateController.setTimeTo(10, 30);
    }

    @OnClick(R.id.set_11_pm)
    public void set_11_pm() {
        _taskCreateController.setTimeTo(23, 0);
    }

    @OnClick(R.id.reset)
    public void resetDueDate() {
        _taskCreateController.resetDueDateToNow();
    }

    // -----------------------------------------------------------------------------------
    // interface implementations

    @Override
    public void updateDueDateDisplay(String dueDateText, String dueDiffDisplayText) {
        _dueDisplay.setText(_getFormattedDueDateDisplayText(dueDateText, dueDiffDisplayText));
    }

    @Override
    public void closeCreateScreen() {
        getFragmentManager().popBackStack();
    }

    // -----------------------------------------------------------------------------------

    private Spannable _getFormattedDueDateDisplayText(String dueDateText,
                                                      String dueDateDisplayText) {
        Spannable sb = new SpannableString(dueDateText + " " + dueDateDisplayText);
        sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_light)),
              dueDateText.length(),
              sb.length(),
              Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
