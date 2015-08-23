package co.kaush.rem.util;

import android.test.suitebuilder.annotation.SmallTest;
import hirondelle.date4j.DateTime;
import org.junit.Test;

import static co.kaush.rem.util.CoreDateUtils.TimeUnit.DAY;
import static co.kaush.rem.util.CoreDateUtils.TimeUnit.MONTH;
import static co.kaush.rem.util.CoreDateUtils.TimeUnit.WEEK;
import static co.kaush.rem.util.CoreDateUtils.isSameDay;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class CoreDateUtilsTest {

    @Test
    public void DateFormatting_WhenPatternProvidedForDate() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);
        assertThat(CoreDateUtils.format("MMM D [WWW]", dt)).isEqualTo("Apr 3 [Tue]");
    }

    @Test
    public void DiffUnit_WhenDateIsMonthApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(60))).isEqualTo(MONTH);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(60))).isEqualTo(MONTH);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(31))).isEqualTo(MONTH);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(31))).isEqualTo(MONTH);
    }

    @Test
    public void DiffUnit_WhenDateIsWeeksApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(7))).isEqualTo(WEEK);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(7))).isEqualTo(WEEK);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(14))).isEqualTo(WEEK);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(14))).isEqualTo(WEEK);
    }


    @Test
    public void DiffUnit_WhenDateIsDaysApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(1))).isEqualTo(DAY);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(1))).isEqualTo(DAY);

        assertThat(CoreDateUtils.getDiffUnit(dt, dt.plusDays(6))).isEqualTo(DAY);
        assertThat(CoreDateUtils.getDiffUnit(dt, dt.minusDays(6))).isEqualTo(DAY);
    }

    @Test
    public void SameDayComparison_ShouldReturnTrue_IfSameDayRegardlessOfTime() {
        DateTime dt1 = new DateTime(1985, 3, 20, 9, 0, 0, 0);
        DateTime dt2 = new DateTime(1985, 3, 20, 11, 0, 0, 0);

        assertThat(isSameDay(dt1, dt2)).isTrue();

        dt2 = new DateTime(1985, 3, 20, 5, 0, 0, 0);
        assertThat(isSameDay(dt1, dt2)).isTrue();

        dt2 = new DateTime(1985, 3, 20, 9, 0, 0, 0);
        assertThat(isSameDay(dt1, dt2)).isTrue();
    }

    @Test
    public void SameDayComparison_ShouldReturnFalse_IfNotSameDay() {
        DateTime dt1 = new DateTime(1985, 3, 20, 9, 0, 0, 0);

        DateTime dt2 = new DateTime(1985, 3, 19, 9, 0, 0, 0);
        assertThat(isSameDay(dt1, dt2)).isFalse();

        dt2 = new DateTime(1985, 4, 20, 9, 0, 0, 0);
        assertThat(isSameDay(dt1, dt2)).isFalse();

        dt2 = new DateTime(1986, 3, 20, 9, 0, 0, 0);
        assertThat(isSameDay(dt1, dt2)).isFalse();
    }
}