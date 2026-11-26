package com.xiaomi.passport.ui.action;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.CookieManager;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.passport.LocalFeatures.LocalFeaturesManagerResponse;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener;

/* JADX INFO: loaded from: classes4.dex */
public class QRCodeAuthPJWPLL implements PassportJsbWebPageLifecycleListener {
    public static final Parcelable.Creator<QRCodeAuthPJWPLL> CREATOR = new Parcelable.Creator<QRCodeAuthPJWPLL>() { // from class: com.xiaomi.passport.ui.action.QRCodeAuthPJWPLL.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QRCodeAuthPJWPLL createFromParcel(Parcel parcel) {
            return new QRCodeAuthPJWPLL(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QRCodeAuthPJWPLL[] newArray(int i) {
            return new QRCodeAuthPJWPLL[i];
        }
    };
    public final String qrCodeAuthUrl;
    public final LocalFeaturesManagerResponse response;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
    public void onPageEntered(Activity activity) {
    }

    @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
    public void onPageHidden(Activity activity) {
    }

    @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
    public void onPageShown(Activity activity) {
    }

    public QRCodeAuthPJWPLL(String str, LocalFeaturesManagerResponse localFeaturesManagerResponse) {
        this.qrCodeAuthUrl = str;
        this.response = localFeaturesManagerResponse;
    }

    private boolean isQRCodeAuthSuccess() {
        String cookie = CookieManager.getInstance().getCookie(this.qrCodeAuthUrl);
        if (cookie == null) {
            return false;
        }
        return String.valueOf(0).equals(HttpCookies.parse(cookie).get("scanInfo"));
    }

    @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
    public void onPageExited(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", isQRCodeAuthSuccess());
        XiaomiAccountManager.get(XMPassportSettings.getApplicationContext()).handleAccountAuthenticatorResponse(this.response, bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.qrCodeAuthUrl);
        parcel.writeParcelable(this.response, i);
    }

    protected QRCodeAuthPJWPLL(Parcel parcel) {
        this.qrCodeAuthUrl = parcel.readString();
        this.response = (LocalFeaturesManagerResponse) parcel.readParcelable(LocalFeaturesManagerResponse.class.getClassLoader());
    }
}
