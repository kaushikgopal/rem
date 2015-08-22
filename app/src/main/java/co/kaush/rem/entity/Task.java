package co.kaush.rem.entity;

import android.database.Cursor;
import android.support.annotation.IntDef;
import co.kaush.rem.db.DbUtils;
import co.kaush.rem.util.CoreDateUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class Task {

    @IntDef({ STATUS_SNOOZED, STATUS_COMPLETED, STATUS_DELETED })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {}

    public static final int STATUS_CREATED = 0;
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

    public Long id;
    public String description;
    public Integer status;
    public Date dueDate;
    public Integer snoozeCount;
    public Integer snoozeInterval; // in minutes

    private Task(Long id,
                 String description,
                 Integer status,
                 Date dueDate,
                 Integer snoozeCount,
                 Integer snoozeInterval) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.snoozeCount = snoozeCount;
        this.snoozeInterval = snoozeInterval;
    }

    public static final Func1<Query, List<Task>> MAP = new Func1<Query, List<Task>>() {
        @Override
        public List<Task> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<Task> values = new ArrayList<>(cursor.getCount());

                while (cursor.moveToNext()) {
                    long id = DbUtils.getLong(cursor, ID);
                    String description = DbUtils.getString(cursor, DESCRIPTION);
                    Date dueDate = DbUtils.getDate(cursor, DUE_DATE);
                    int status = DbUtils.getInt(cursor, STATUS);
                    int snoozeCount = DbUtils.getInt(cursor, SNOOZE_COUNT);
                    int snoozeInterval = DbUtils.getInt(cursor, SNOOZE_INTERVAL);

                    Task t = TaskBuilder.aTask()
                          .withId(id)
                          .withDescription(description)
                          .withDueDate(dueDate)
                          .withSnoozeCount(snoozeCount)
                          .withSnoozeInterval(snoozeInterval)
                          .withStatus(status)
                          .build();

                    values.add(t);
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public boolean isOverdue(CoreDateUtils coreDateUtils) {
        return !coreDateUtils.isInTheFuture(dueDate);
    }

    public static class TaskBuilder {
        public Long id;
        public String description;
        public Integer status;
        public Date dueDate;
        public Integer snoozeCount;
        public Integer snoozeInterval; // in minutes

        private TaskBuilder() {}

        public static TaskBuilder aTask() { return new TaskBuilder();}

        public TaskBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public TaskBuilder withDueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskBuilder withSnoozeCount(Integer snoozeCount) {
            this.snoozeCount = snoozeCount;
            return this;
        }

        public TaskBuilder withSnoozeInterval(Integer snoozeInterval) {
            this.snoozeInterval = snoozeInterval;
            return this;
        }

        public Task build() {
            return new Task(id, description, status, dueDate, snoozeCount, snoozeInterval);
        }
    }
}