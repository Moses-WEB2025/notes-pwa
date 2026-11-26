package com.xiaomi.opensdk.pdc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.PrefUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultEnvironment implements OpenPdcSyncServerAdapter.Environment {
    private static final String TAG = "DefaultEnvironment";
    private final Context mContext;
    private String mUserAgent;

    public DefaultEnvironment(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaomi.opensdk.pdc.OpenPdcSyncServerAdapter.Environment
    public String getHost() {
        String string = PrefUtils.getString(this.mContext, OpenPdcConsts.OPEN_PDC_HOST_KEY);
        return TextUtils.isEmpty(string) ? "micpdc.openapi.xiaomi.net" : string;
    }

    @Override // com.xiaomi.opensdk.pdc.OpenPdcSyncServerAdapter.Environment
    public void setHost(String str) {
        PrefUtils.putString(this.mContext, OpenPdcConsts.OPEN_PDC_HOST_KEY, str);
    }

    @Override // com.xiaomi.opensdk.pdc.OpenPdcSyncServerAdapter.Environment
    public String getUserCountry() {
        return Locale.getDefault().getCountry();
    }

    @Override // com.xiaomi.opensdk.pdc.OpenPdcSyncServerAdapter.Environment
    public String getUserAgent() {
        if (this.mUserAgent == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Build.MODEL);
            sb.append("; MIUI/");
            sb.append(Build.VERSION.INCREMENTAL);
            try {
                if (((Boolean) Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                    sb.append(' ');
                    sb.append("ALPHA");
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception in getUserAgent()", e);
            }
            this.mUserAgent = sb.toString();
        }
        return this.mUserAgent;
    }
}
