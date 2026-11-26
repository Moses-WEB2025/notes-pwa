package com.xiaomi.passport.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.passport.webview.UrlInterceptor;

/* JADX INFO: loaded from: classes4.dex */
public class SNSCodeUrlInterceptor implements UrlInterceptor {
    public static final Parcelable.Creator<SNSCodeUrlInterceptor> CREATOR = new Parcelable.Creator<SNSCodeUrlInterceptor>() { // from class: com.xiaomi.passport.ui.webview.SNSCodeUrlInterceptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCodeUrlInterceptor createFromParcel(Parcel parcel) {
            return new SNSCodeUrlInterceptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSCodeUrlInterceptor[] newArray(int i) {
            return new SNSCodeUrlInterceptor[i];
        }
    };

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

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public boolean shouldIntercept(Context context, String str) {
        String queryParameter = Uri.parse(str).getQueryParameter("code");
        if (queryParameter == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.putExtra("code", queryParameter);
        if (!(context instanceof Activity)) {
            return true;
        }
        Activity activity = (Activity) context;
        activity.setResult(-1, intent);
        activity.finish();
        return true;
    }

    public SNSCodeUrlInterceptor() {
    }

    protected SNSCodeUrlInterceptor(Parcel parcel) {
    }
}
