package com.xiaomi.passport.webview;

import android.accounts.Account;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.xiaomi.accountsdk.utils.FidNonce;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.utils.HttpCookies;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class CookieFillAccountDeviceParamsULPT implements UrlLoadPrepareTask {
    public static final Parcelable.Creator<CookieFillAccountDeviceParamsULPT> CREATOR = new Parcelable.Creator<CookieFillAccountDeviceParamsULPT>() { // from class: com.xiaomi.passport.webview.CookieFillAccountDeviceParamsULPT.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieFillAccountDeviceParamsULPT createFromParcel(Parcel parcel) {
            return new CookieFillAccountDeviceParamsULPT(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieFillAccountDeviceParamsULPT[] newArray(int i) {
            return new CookieFillAccountDeviceParamsULPT[i];
        }
    };
    public final String userAgent;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CookieFillAccountDeviceParamsULPT(String str) {
        this.userAgent = str;
    }

    @Override // com.xiaomi.passport.webview.UrlLoadPrepareTask
    public void execute(Context context, WeakReference<Context> weakReference, Map<String, String> map, Map<String, String> map2) {
        HashMap map3 = new HashMap();
        HttpCookies.fillDeviceId(map3);
        HttpCookies.fillFidNonce(map3, FidNonce.Type.WEB_VIEW);
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount != null) {
            String passToken = xiaomiAccountManager.getPassToken(xiaomiAccount);
            map3.put("userId", xiaomiAccount.name);
            map3.put("passToken", passToken);
        }
        map3.put("NativeUserAgent", Base64.encodeToString(this.userAgent.getBytes(), 2));
        HttpCookies.set(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, map3);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userAgent);
    }

    protected CookieFillAccountDeviceParamsULPT(Parcel parcel) {
        this.userAgent = parcel.readString();
    }
}
