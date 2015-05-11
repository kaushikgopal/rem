package co.kaush.rem.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import co.kaush.rem.MyApp;
import com.squareup.leakcanary.RefWatcher;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment
      extends Fragment {

    protected CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    /*
    1.  it's necessary to add the initialization again, in cases where we hit the back button in frags
        onResumption the subscription might have been un subscribed

    2.  we place the subscription re-initialization logic here vs onCreateView because
        it's common to override onCreateView and not call super.
    */
        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscriptions.unsubscribe();
    }
}