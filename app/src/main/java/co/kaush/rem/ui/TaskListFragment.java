package co.kaush.rem.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kaush.rem.R;
import co.kaush.rem.util.ColorFilterCache;

public class TaskListFragment
    extends BaseFragment {

  @InjectView(R.id.toolbar) Toolbar toolbar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // enable action bar items
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_task_list, container, false);
    ButterKnife.inject(this, layout);
    _initializeToolbar();
    return layout;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.task_list_menu, menu);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);

    Drawable add = ((MenuBuilder) menu).getActionItems().get(0).getIcon();
    add.setColorFilter(ColorFilterCache.getWhiteColorFilter());
  }

  private void _initializeToolbar() {
    // enable the actionbar (so menu items get hooked on)
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    // hide title of actionbar
    ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayShowTitleEnabled(false);
    }

    // -----------------------------------------------------------------------------------
    // style action bar

    // add navigation icon
    Drawable nav = getResources().getDrawable(R.drawable.ic_menu);
    nav.setColorFilter(ColorFilterCache.getWhiteColorFilter());
    toolbar.setNavigationIcon(nav);
  }
}
