package com.xiaomi.phonenum.innetdate;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.UrlConnHttpFactory;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.phone.PhoneUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public class InNetDateHelper {
    private static final long DEFAULT_IN_NET_TIME = 0;
    private static final String TAG = "InNetTimeHelper";
    private HttpFactory httpFactory;
    private PhoneUtil phoneUtil;

    public InNetDateHelper(Context context) {
        this.httpFactory = new UrlConnHttpFactory(context);
        this.phoneUtil = PhoneInfo.get(context);
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("yyyyMdHHmmssS").format(new Date());
    }

    private long parseInNetTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            AccountLogger.log(TAG, "ParseException", e);
        }
        return 0L;
    }
}
