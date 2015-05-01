package co.kaush.rem.ui;

import android.os.Bundle;
import butterknife.ButterKnife;
import co.kaush.rem.R;
import co.kaush.rem.RemModule;
import dagger.Module;
import java.util.Arrays;
import java.util.List;

public class LauncherActivity
    extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @Override
  protected List<Object> getActivitySpecificModules() {
    return Arrays.<Object>asList(new LauncherModule());
  }

  @Module(injects = { LauncherActivity.class },
      addsTo = RemModule.class,
      library = true)
  public class LauncherModule {}
}
