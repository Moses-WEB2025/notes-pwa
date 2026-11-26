package miuix.pickerwidget.widget.Calendar;

import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class SpecialHolidayParser {
    private static final String FATHERS_DAY = "父亲节";
    private static final String MOTHERS_DAY = "母亲节";
    private static final String NEW_YEARS_EVE = "除夕";
    private static final String THANKS_GIVING_DAY = "感恩节";

    SpecialHolidayParser() {
    }

    static String parseSpecialHoliday(int i, int i2, int i3, Calendar calendar) {
        int i4 = calendar.get(5);
        int i5 = calendar.get(6);
        if (i4 == 4 && i == 1 && i2 == 6) {
            return MOTHERS_DAY;
        }
        if (i4 == 5 && i == 2 && i2 == 6) {
            return FATHERS_DAY;
        }
        if (i4 == 10 && i2 == 3) {
            if ((i3 < 4 ? 3 : 4) == i) {
                return THANKS_GIVING_DAY;
            }
        }
        if (i5 != 11) {
            return null;
        }
        if (calendar.get(10) == calendar.getActualMaximum(10)) {
            return NEW_YEARS_EVE;
        }
        return null;
    }
}
