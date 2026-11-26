package com.xiaomi.passport.ui.logiccontroller;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CountryNameChooser {
    private static final int REQ_CODE_CHOOSE_COUNTRY = 8880;
    private final Intent chooserIntent;

    public static class ChooseNoneException extends Exception {
    }

    public static class NotChooseActionException extends Exception {
    }

    public CountryNameChooser(Intent intent) {
        this.chooserIntent = intent;
    }

    public void startActivityForGetCountryNameResult(Activity activity) {
        activity.startActivityForResult(this.chooserIntent, 8880);
    }

    public String getCountryNameWhenActivityResult(int i, int i2, Intent intent) throws ChooseNoneException, NotChooseActionException {
        if (i != 8880) {
            throw new NotChooseActionException();
        }
        if (i2 == -1) {
            String stringExtra = intent.getStringExtra("countryName");
            if (!TextUtils.isEmpty(stringExtra)) {
                return stringExtra;
            }
        }
        throw new ChooseNoneException();
    }
}
