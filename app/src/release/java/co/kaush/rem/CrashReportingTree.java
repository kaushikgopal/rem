package co.kaush.rem;

import timber.log.Timber;

public class CrashReportingTree
      extends Timber.HollowTree {

    @Override
    public void i(String message, Object... args) {
        //Crashlytics.log(String.format(message, args));
    }

    @Override
    public void e(Throwable t, String message, Object... args) {
        e(message, args);
        //Crashlytics.logException(t);
    }
}
