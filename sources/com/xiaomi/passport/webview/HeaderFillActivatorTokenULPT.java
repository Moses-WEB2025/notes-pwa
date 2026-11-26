package com.xiaomi.passport.webview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.passport.utils.ActivatorTokenSign;
import java.lang.ref.WeakReference;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class HeaderFillActivatorTokenULPT implements UrlLoadPrepareTask {
    public static final Parcelable.Creator<HeaderFillActivatorTokenULPT> CREATOR = new Parcelable.Creator<HeaderFillActivatorTokenULPT>() { // from class: com.xiaomi.passport.webview.HeaderFillActivatorTokenULPT.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeaderFillActivatorTokenULPT createFromParcel(Parcel parcel) {
            return new HeaderFillActivatorTokenULPT(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeaderFillActivatorTokenULPT[] newArray(int i) {
            return new HeaderFillActivatorTokenULPT[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    @Override // com.xiaomi.passport.webview.UrlLoadPrepareTask
    public void execute(Context context, WeakReference<Context> weakReference, Map<String, String> map, Map<String, String> map2) {
        ActivatorTokenSign.fillHeaders(context, map);
    }

    public HeaderFillActivatorTokenULPT() {
    }

    protected HeaderFillActivatorTokenULPT(Parcel parcel) {
    }
}
