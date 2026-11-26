package com.xiaomi.passport.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.snscorelib.internal.entity.PassportSnsConstant;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.UrlInterceptor;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SNSCookieBindUrlInterceptor implements UrlInterceptor {
    public static final Parcelable.Creator<SNSCookieBindUrlInterceptor> CREATOR = new Parcelable.Creator<SNSCookieBindUrlInterceptor>() { // from class: com.xiaomi.passport.ui.webview.SNSCookieBindUrlInterceptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCookieBindUrlInterceptor createFromParcel(Parcel parcel) {
            return new SNSCookieBindUrlInterceptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCookieBindUrlInterceptor[] newArray(int i) {
            return new SNSCookieBindUrlInterceptor[i];
        }
    };
    public static final String EXTRA_PARCEL_ACCOUNT_INFO = "account_info";
    public static final String TAG = "SNSCookieBindUrlInterceptor";
    public final Boolean hasLocalChannel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public void release() {
    }

    public SNSCookieBindUrlInterceptor(Boolean bool) {
        this.hasLocalChannel = bool;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public boolean shouldIntercept(Context context, String str) {
        CookieManager cookieManagerOrNull = HttpCookies.getCookieManagerOrNull();
        Map<String, String> map = HttpCookies.parse(cookieManagerOrNull != null ? cookieManagerOrNull.getCookie(str) : null);
        if (map.containsKey("sns-bind-step")) {
            String str2 = map.get("sns-bind-step");
            if (PassportSnsConstant.SNS_BIND_FINISH.equals(str2) || PassportSnsConstant.SNS_BIND_CANCEL.equals(str2)) {
                String str3 = map.get("passToken");
                String str4 = map.get("userId");
                if (TextUtils.isEmpty(str4) || TextUtils.isEmpty(str3)) {
                    AccountLogger.log(TAG, "UnExpected null userid or passToken");
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.setResult(0);
                        activity.finish();
                    }
                    return true;
                }
                AccountInfo accountInfoBuild = new AccountInfo.Builder().userId(str4).passToken(str3).hasLocalChannel(this.hasLocalChannel).build();
                XiaomiAccountManager.get(context).addAccountOrUpdatePassToken(accountInfoBuild);
                if (context instanceof Activity) {
                    Activity activity2 = (Activity) context;
                    Intent intent = new Intent();
                    intent.putExtra("account_info", accountInfoBuild);
                    activity2.setResult(-1, intent);
                    activity2.finish();
                }
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.hasLocalChannel);
    }

    protected SNSCookieBindUrlInterceptor(Parcel parcel) {
        this.hasLocalChannel = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
    }
}
