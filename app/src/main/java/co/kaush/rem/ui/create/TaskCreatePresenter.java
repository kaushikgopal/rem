package co.kaush.rem.ui.create;

import co.kaush.rem.util.CoreDateUtils;
import hirondelle.date4j.DateTime;

public class TaskCreatePresenter {

    private CoreDateUtils _coreDateUtils;

    TaskCreatePresenter(CoreDateUtils coreDateUtils) {
        _coreDateUtils = coreDateUtils;
    }

    public String getDueDateText(DateTime _dueDateTime) {
        return CoreDateUtils.format(CoreDateUtils.DUE_DATE_FORMAT, _dueDateTime);
    }

    public String getDueDateDiffText(DateTime _dueDateTime) {
        DateTime now = _coreDateUtils.now();
        int diffValue;

        switch (CoreDateUtils.getDiffUnit(now, _dueDateTime)) {
            case MONTH:
                if (_coreDateUtils.isInTheFuture(_dueDateTime)) {
                    diffValue = _dueDateTime.getMonth() - now.getMonth();
                    if (diffValue < 0) {
                        diffValue += 12;
                    }
                } else {
                    diffValue = _dueDateTime.getMonth() - now.getMonth();
                }
                return _getPluralizedDiffText("Mth", diffValue);

            case WEEK:
                // If you hit a date before Sunday, January 2, 2000 you're probably going to get funky results courtesy: date4j
                diffValue = _dueDateTime.getWeekIndex() - now.getWeekIndex();
                return _getPluralizedDiffText("Week", diffValue);

            case DAY:
                if (_coreDateUtils.isInTheFuture(_dueDateTime)) {
                    diffValue = _dueDateTime.getDayOfYear() - now.getDayOfYear();
                    if (diffValue < 0) {
                        diffValue += CoreDateUtils.getLastDayOfTheYear(_dueDateTime);
                    }
                } else {
                    diffValue = _dueDateTime.getDayOfYear() - now.getDayOfYear();
                }

                return _getPluralizedDiffText("Dy", diffValue);

            case HOUR:
                if (_coreDateUtils.isInTheFuture(_dueDateTime)) {
                    diffValue = _dueDateTime.getHour() - now.getHour();
                    if (diffValue < 0) {
                        diffValue += 24;
                    }
                } else {
                    if (_dueDateTime.isSameDayAs(_coreDateUtils.now())) {
                        diffValue = _dueDateTime.getHour() - now.getHour();
                    } else {
                        diffValue = _dueDateTime.getHour() - now.getHour() - 24;
                    }
                }

                return _getPluralizedDiffText("Hr", diffValue);
        }

        diffValue = _dueDateTime.getMinute() - now.getMinute();

        if (!_dueDateTime.getHour().equals(now.getHour())) {

            if (_coreDateUtils.isInTheFuture(_dueDateTime)) {
                diffValue += 60;
            } else {
                diffValue -= 60;
            }
        }

        if (diffValue == 0) {
            return "now";
        } else {
            return _getPluralizedDiffText("Mt", diffValue);
        }
    }

    /**
     * 1. Takes into account if the text needs to be pluralized
     * 2. if due date is after now then prepends "in X Hr(s)" else "X Hr(s) before"
     *
     * @param diffValue real number of hours between due date and now (-v if due is before now)
     */
    private String _getPluralizedDiffText(String unit, int diffValue) {
        boolean pluralize = Math.abs(diffValue) > 1;

        if (diffValue > 0) {
            return String.format("(~%d %s%s)", Math.abs(diffValue), unit, (pluralize) ? "s" : "");
        } else {
            return String.format("(~%d %s%s back)",
                  Math.abs(diffValue),
                  unit,
                  (pluralize) ? "s" : "");
        }
    }
}
