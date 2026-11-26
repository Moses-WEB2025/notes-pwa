package com.xiaomi.passport.webview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class RedirectControlInterceptor implements UrlInterceptor {
    public static final Parcelable.Creator<RedirectControlInterceptor> CREATOR = new Parcelable.Creator<RedirectControlInterceptor>() { // from class: com.xiaomi.passport.webview.RedirectControlInterceptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RedirectControlInterceptor createFromParcel(Parcel parcel) {
            return new RedirectControlInterceptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RedirectControlInterceptor[] newArray(int i) {
            return new RedirectControlInterceptor[i];
        }
    };
    private static final String TAG = "RedirectControlInterceptor";
    private final UrlValidator mUrlValidator;

    public interface UrlValidator {
        boolean isUrlValid(String str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public void release() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public RedirectControlInterceptor(UrlValidator urlValidator) {
        this.mUrlValidator = urlValidator;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public boolean shouldIntercept(Context context, String str) {
        UrlValidator urlValidator = this.mUrlValidator;
        if (urlValidator == null) {
            return false;
        }
        return !urlValidator.isUrlValid(str);
    }

    protected RedirectControlInterceptor(Parcel parcel) {
        this.mUrlValidator = null;
    }
}
