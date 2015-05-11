package co.kaush.rem.util;

import android.test.suitebuilder.annotation.SmallTest;
import hirondelle.date4j.DateTime;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;

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
}