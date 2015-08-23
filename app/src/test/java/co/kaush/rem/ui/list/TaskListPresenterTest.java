package co.kaush.rem.ui.list;

import co.kaush.rem.R;
import co.kaush.rem.entity.Task;
import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static co.kaush.rem.entity.Task.TaskBuilder.aTask;
import static co.kaush.rem.util.CoreDateUtils.getDateFor;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TaskListPresenterTest {

    public static final DateTime NOW = new DateTime(2013, 3, 23, 19, 12, 20, 0);
    private static CoreDateUtils _coreDateUtils = spy(new CoreDateUtils());
    private TaskListPresenter _presenter;

    @BeforeClass
    public static void setUpOnce() {
        when(_coreDateUtils.now()).thenReturn(NOW);
        // today = "Mar 23 2013 7:12 pm"
    }

    @Before
    public void setUp() throws Exception {
        _presenter = new TaskListPresenter(_coreDateUtils);
    }

    @Test
    public void DueDayTimeText_ShouldShowYear_WhenYearNotCurrent() {

        assertThat(_presenter.getDueDayTimeTextFor(getTaskOverdue())).isEqualTo(
              "[Wed] 9:00 AM [1985]");

        Task task = getTaskDueToday();
        assertThat(_presenter.getDueDayTimeTextFor(task)).isEqualTo("[Sat] 11:12 PM");

        task = aTask().withDueDate(getDateFor(new DateTime(2015, 3, 20, 9, 0, 0, 0))).build();
        assertThat(_presenter.getDueDayTimeTextFor(task)).isEqualTo("[Fri] 9:00 AM [2015]");
    }

    @Test
    public void DueDateTimeColor_ShouldBeRed_WhenTaskIsOverdue() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(getTaskOverdue());
        tasks.add(getTaskDueToday());

        assertThat(_presenter.getDueDayTimeColorIdFor(tasks, 0)).isEqualTo(R.color.orange_1);
        assertThat(_presenter.getDueDayTimeColorIdFor(tasks, 1)).isNotEqualTo(R.color.orange_1);
    }

    @Test
    public void DueDateTimeColor_ShouldAlternateBetweenConsecutiveWeeks() {
        DateTime someFutureDate = new DateTime(2013, 3, 23, 23, 12, 20, 0);

        List<Task> tasks = new ArrayList<>();

        tasks.add(aTask().withDueDate(getDateFor(someFutureDate)).build());
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(8))).build());

        int firstColor = _presenter.getDueDayTimeColorIdFor(tasks, 0);
        assertThat(_presenter.getDueDayTimeColorIdFor(tasks, 1)).isNotEqualTo(firstColor);
    }

    @Test
    public void DueDateTimeColor_ShouldAlternateBetweenDifferent_NotNecessarilyConsecutiveWeeks() {
        DateTime someFutureDate = new DateTime(2013, 3, 23, 23, 12, 20, 0);

        List<Task> tasks = new ArrayList<>();

        tasks.add(aTask().withDueDate(getDateFor(someFutureDate)).build());             // t1
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(6))).build()); // t2
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(7))).build()); // t3
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(8))).build()); // t4
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(9))).build()); // t5
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(12))).build());// t6
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(13))).build());// t7
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(14))).build());// t8
        tasks.add(aTask().withDueDate(getDateFor(someFutureDate.plusDays(28))).build());// t9

        int taskColor1 = _presenter.getDueDayTimeColorIdFor(tasks, 0);
        int taskColor2 = _presenter.getDueDayTimeColorIdFor(tasks, 1);
        int taskColor3 = _presenter.getDueDayTimeColorIdFor(tasks, 2);
        int taskColor4 = _presenter.getDueDayTimeColorIdFor(tasks, 3);
        int taskColor5 = _presenter.getDueDayTimeColorIdFor(tasks, 4);
        int taskColor6 = _presenter.getDueDayTimeColorIdFor(tasks, 5);
        int taskColor7 = _presenter.getDueDayTimeColorIdFor(tasks, 6);
        int taskColor8 = _presenter.getDueDayTimeColorIdFor(tasks, 7);
        int taskColor9 = _presenter.getDueDayTimeColorIdFor(tasks, 8);

        assertThat(taskColor1).isEqualTo(taskColor2);

        assertThat(taskColor2).isNotEqualTo(taskColor3);

        assertThat(taskColor3).isEqualTo(taskColor4);
        assertThat(taskColor4).isEqualTo(taskColor5);
        assertThat(taskColor5).isEqualTo(taskColor6);
        assertThat(taskColor6).isEqualTo(taskColor7);

        assertThat(taskColor7).isNotEqualTo(taskColor8);

        assertThat(taskColor8).isNotEqualTo(taskColor9);
    }

    // -----------------------------------------------------------------------------------

    @Test
    public void TodaySeparatorPosition_ShouldBe0_When1FutureDueTaskPresent() {
        List<Task> tasks = Collections.singletonList(getFutureTask());
        assertThat(_presenter.getPositionForTodaySeparator(tasks)).isEqualTo(0);
    }

    @Test
    public void TodaySeparatorPosition_ShouldBe1_When1PastDueTaskPresent() {
        List<Task> tasks = Collections.singletonList(getTaskOverdue());
        assertThat(_presenter.getPositionForTodaySeparator(tasks)).isEqualTo(1);
    }

    @Test
    public void TodaySeparatorPosition_ShouldBe1_WhenPastAndFutureTaksPresent() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(getTaskOverdue());
        tasks.add(getFutureTask());
        assertThat(_presenter.getPositionForTodaySeparator(tasks)).isEqualTo(1);
    }

    // -----------------------------------------------------------------------------------
    // fixtures

    private Task getTaskOverdue() {
        return aTask().withDueDate(getDateFor(new DateTime(1985, 3, 20, 9, 0, 0, 0))).build();
    }

    private Task getTaskDueToday() {
        return aTask().withDueDate(getDateFor(new DateTime(2013, 3, 23, 23, 12, 20, 0))).build();
    }

    private Task getFutureTask() {
        return aTask().withDueDate(getDateFor(NOW.plusDays(20))).build();
    }
}