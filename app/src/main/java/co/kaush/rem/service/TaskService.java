package co.kaush.rem.service;

import co.kaush.rem.entity.Task;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton
public class TaskService {
    public static final String LIST_QUERY = "SELECT * FROM " + Task.TABLE +
                                            " ORDER BY " + Task.DUE_DATE +
                                            " ASC";

    private BriteDatabase _db;

    @Inject
    public TaskService(BriteDatabase db) {
        _db = db;
    }

    public Observable<List<Task>> getTaskList() {
        return _db.createQuery(Task.TABLE, LIST_QUERY).map(Task.MAP);
    }
}
