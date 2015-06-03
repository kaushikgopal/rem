package co.kaush.rem.util;

import android.test.suitebuilder.annotation.SmallTest;
import hirondelle.date4j.DateTime;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;

import static co.kaush.rem.util.CoreDateUtils.TimeUnit.DAY;
import static co.kaush.rem.util.CoreDateUtils.TimeUnit.HOUR;
import static co.kaush.rem.util.CoreDateUtils.TimeUnit.MONTH;
import static co.kaush.rem.util.CoreDateUtils.TimeUnit.WEEK;
import static org.assertj.core.api.Assertions.assertThat;

@SmallTest
public class CoreDateUtilsTest {

    private CoreDateUtils _cdt;

    @Before
    public void setUp() throws Exception {
        _cdt = new CoreDateUtils(Locale.US);
    }

    @Test
    public void DateFormatting_WhenPatternProvidedForDate() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);
        assertThat(_cdt.format("MMM D [WWW]", dt)).isEqualToIgnoringCase("Apr 3 [Tue]");
    }

    @Test
    public void DiffUnit_WhenDateIsMonthApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(60))).isEqualTo(MONTH);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(60))).isEqualTo(MONTH);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(31))).isEqualTo(MONTH);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(31))).isEqualTo(MONTH);
    }
    
    @Test
    public void DiffUnit_WhenDateIsWeeksApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(7))).isEqualTo(WEEK);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(7))).isEqualTo(WEEK);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(14))).isEqualTo(WEEK);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(14))).isEqualTo(WEEK);
    }


    @Test
    public void DiffUnit_WhenDateIsDaysApart() {
        DateTime dt = DateTime.forDateOnly(1979, 4, 3);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(1))).isEqualTo(DAY);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(1))).isEqualTo(DAY);

        assertThat(_cdt.getDiffUnit(dt, dt.plusDays(6))).isEqualTo(DAY);
        assertThat(_cdt.getDiffUnit(dt, dt.minusDays(6))).isEqualTo(DAY);
    }
}