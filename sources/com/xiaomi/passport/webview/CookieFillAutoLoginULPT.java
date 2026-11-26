package com.xiaomi.passport.webview;

import android.accounts.Account;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.utils.STSCookies;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class CookieFillAutoLoginULPT implements UrlLoadPrepareTask {
    public static final Parcelable.Creator<CookieFillAutoLoginULPT> CREATOR = new Parcelable.Creator<CookieFillAutoLoginULPT>() { // from class: com.xiaomi.passport.webview.CookieFillAutoLoginULPT.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieFillAutoLoginULPT createFromParcel(Parcel parcel) {
            return new CookieFillAutoLoginULPT(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieFillAutoLoginULPT[] newArray(int i) {
            return new CookieFillAutoLoginULPT[i];
        }
    };
    private final String autoLoginServiceId;
    private final String cookieUrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CookieFillAutoLoginULPT(String str, String str2) {
        this.autoLoginServiceId = str;
        this.cookieUrl = str2;
    }

    @Override // com.xiaomi.passport.webview.UrlLoadPrepareTask
    public void execute(Context context, WeakReference<Context> weakReference, Map<String, String> map, Map<String, String> map2) {
        Account xiaomiAccount = XiaomiAccountManager.get(context).getXiaomiAccount();
        if (xiaomiAccount == null) {
            return;
        }
        HashMap map3 = new HashMap();
        map2.put("userId", xiaomiAccount.name);
        STSCookies.requestAndFill(context, this.autoLoginServiceId, map3);
        HttpCookies.set(this.cookieUrl, map3);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.autoLoginServiceId);
        parcel.writeString(this.cookieUrl);
    }

    protected CookieFillAutoLoginULPT(Parcel parcel) {
        this.autoLoginServiceId = parcel.readString();
        this.cookieUrl = parcel.readString();
    }
}
