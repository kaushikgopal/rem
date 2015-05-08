package co.kaush.rem.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import co.kaush.rem.R;

public class TaskCreateFragment
    extends BaseFragment
    implements TaskCreateController.ITalkToTaskCreate {

    public static final String FRAG_TAG = "co.kaush.rem.ui.TaskCreateFragment";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_task_create, container, false);
        ButterKnife.inject(this, layout);
        return layout;
    }
}
