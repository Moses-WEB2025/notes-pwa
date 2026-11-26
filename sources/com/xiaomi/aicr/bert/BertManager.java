package com.xiaomi.aicr.bert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes4.dex */
public class BertManager {
    public static final Uri uri = Uri.parse("content://com.xiaomi.aicr.provider.NLSCapabilityProvider");
    private final Context mContext;
    private IBertService mService;

    public BertManager(Context context) {
        this.mContext = context;
        try {
            Bundle bundleCall = context.getContentResolver().call(uri, "get_bert_binder", context.getPackageName(), new Bundle());
            if (bundleCall != null) {
                this.mService = IBertService.Stub.asInterface(bundleCall.getBinder(CallMethod.RESULT_BINDER));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected() {
        IBertService iBertService = this.mService;
        if (iBertService != null) {
            try {
                IBinder iBinderAsBinder = iBertService.asBinder();
                if (iBinderAsBinder.pingBinder() && iBinderAsBinder.isBinderAlive()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Bundle bundleCall = this.mContext.getContentResolver().call(uri, "get_bert_binder", this.mContext.getPackageName(), new Bundle());
            if (bundleCall == null) {
                return false;
            }
            IBertService iBertServiceAsInterface = IBertService.Stub.asInterface(bundleCall.getBinder(CallMethod.RESULT_BINDER));
            this.mService = iBertServiceAsInterface;
            return iBertServiceAsInterface != null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int initModel(String str) {
        if (!isConnected()) {
            return -1;
        }
        try {
            return this.mService.initModel(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int[] get_tokenized_e5(String str) {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.getTokenizedE5(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String get_tokenized_nlu() {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.getTokenizedNlu();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public float[] Berte5_encode(int[] iArr) {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.Berte5Encode(iArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bundle Bertnlu_encode(String str, float f, float f2, float f3, float f4) {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.BertnluEncode(str, f, f2, f3, f4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bundle Bertnlu_get_scope_results_string() {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.BertnluGetScopeResultsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bundle Bertnlu_get_scope_results_integer() {
        if (!isConnected()) {
            return null;
        }
        try {
            return this.mService.BertnluGetScopeResultsInteger();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int release(String str) {
        if (!isConnected()) {
            return -1;
        }
        try {
            return this.mService.releaseModel(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int extractTextFeature(int[] iArr, float[] fArr) {
        if (!isConnected()) {
            return -1;
        }
        try {
            return this.mService.getExtractTextFeature(iArr, fArr);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
