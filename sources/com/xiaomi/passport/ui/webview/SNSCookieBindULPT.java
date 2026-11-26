package com.xiaomi.passport.ui.webview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.UrlLoadPrepareTask;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SNSCookieBindULPT implements UrlLoadPrepareTask {
    public static final Parcelable.Creator<SNSCookieBindULPT> CREATOR = new Parcelable.Creator<SNSCookieBindULPT>() { // from class: com.xiaomi.passport.ui.webview.SNSCookieBindULPT.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCookieBindULPT createFromParcel(Parcel parcel) {
            return new SNSCookieBindULPT(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCookieBindULPT[] newArray(int i) {
            return new SNSCookieBindULPT[i];
        }
    };
    public final String snsTokenPh;
    public final String snsWeixinOpenId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SNSCookieBindULPT(String str, String str2) {
        this.snsTokenPh = str;
        this.snsWeixinOpenId = str2;
    }

    @Override // com.xiaomi.passport.webview.UrlLoadPrepareTask
    public void execute(Context context, WeakReference<Context> weakReference, Map<String, String> map, Map<String, String> map2) {
        HashMap map3 = new HashMap();
        map3.put("sns_token_ph", this.snsTokenPh);
        map3.put("sns_weixin_openId", this.snsWeixinOpenId);
        HttpCookies.set(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, map3);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.snsTokenPh);
        parcel.writeString(this.snsWeixinOpenId);
    }

    protected SNSCookieBindULPT(Parcel parcel) {
        this.snsTokenPh = parcel.readString();
        this.snsWeixinOpenId = parcel.readString();
    }
}
