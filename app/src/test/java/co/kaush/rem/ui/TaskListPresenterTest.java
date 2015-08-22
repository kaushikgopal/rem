package co.kaush.rem.ui;

import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static co.kaush.rem.entity.Task.TaskBuilder.aTask;
import static co.kaush.rem.util.CoreDateUtils.getDateFor;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TaskListPresenterTest {

    private static CoreDateUtils _coreDateUtils = spy(new CoreDateUtils());
    private TaskListPresenter _presenter;

    @BeforeClass
    public static void setUpOnce() {
        when(_coreDateUtils.now()).thenReturn(new DateTime(2013, 3, 23, 19, 12, 20, 0));
        // today = "Mar 23 2013 7:12 pm"
    }

    @Before
    public void setUp() throws Exception {
        _presenter = new TaskListPresenter(_coreDateUtils);
    }

    @Test
    public void DueDayTimeText_ShouldShowYear_WhenYearNotCurrent() {
        Task task = aTask().withDueDate(getDateFor(new DateTime(1985, 3, 20, 9, 0, 0, 0))).build();
        assertThat(_presenter.getDueDateTextFor(task)).isEqualTo("[Wed] 9:00 AM [1985]");

        task = aTask().withDueDate(getDateFor(new DateTime(2013, 3, 20, 9, 0, 0, 0))).build();
        assertThat(_presenter.getDueDateTextFor(task)).isEqualTo("[Wed] 9:00 AM");

        task = aTask().withDueDate(getDateFor(new DateTime(2015, 3, 20, 9, 0, 0, 0))).build();
        assertThat(_presenter.getDueDateTextFor(task)).isEqualTo("[Fri] 9:00 AM [2015]");
    }
}