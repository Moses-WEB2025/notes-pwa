package com.xiaomi.passport.servicetoken;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceTokenUIResponse implements Parcelable {
    public static final Parcelable.Creator<ServiceTokenUIResponse> CREATOR = new Parcelable.Creator<ServiceTokenUIResponse>() { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUIResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenUIResponse createFromParcel(Parcel parcel) {
            return new ServiceTokenUIResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenUIResponse[] newArray(int i) {
            return new ServiceTokenUIResponse[i];
        }
    };
    private static final String TAG = "ServiceTokenUIResponse";
    private IServiceTokenUIResponse mResponse;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ServiceTokenUIResponse(IServiceTokenUIResponse iServiceTokenUIResponse) {
        this.mResponse = iServiceTokenUIResponse;
    }

    public ServiceTokenUIResponse(Parcel parcel) {
        this.mResponse = IServiceTokenUIResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle bundle) {
        bundle.keySet();
        AccountLogger.log(TAG, "AccountAuthenticatorResponse.onResult");
        try {
            this.mResponse.onResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public void onRequestContinued() {
        AccountLogger.log(TAG, "AccountAuthenticatorResponse.onRequestContinued");
        try {
            this.mResponse.onRequestContinued();
        } catch (RemoteException unused) {
        }
    }

    public void onError(int i, String str) {
        AccountLogger.log(TAG, "AccountAuthenticatorResponse.onError: " + i + ", " + str);
        try {
            this.mResponse.onError(i, str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mResponse.asBinder());
    }
}
