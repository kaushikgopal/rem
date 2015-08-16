package co.kaush.rem.entity;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Task {

    @IntDef({ STATUS_SNOOZED, STATUS_COMPLETED, STATUS_DELETED })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {}

    public static final int STATUS_SNOOZED = 1;
    public static final int STATUS_COMPLETED = 2;
    public static final int STATUS_DELETED = 3;

    public static final String TABLE = "tasks";
    public static final String ID = "_id";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String DUE_DATE = "due_date";
    public static final String SNOOZE_INTERVAL = "snooze_interval";
    public static final String SNOOZE_COUNT = "snooze_count";
}