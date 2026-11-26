package com.xiaomi.accountsdk.utils;

import android.util.Base64;
import com.xiaomi.account.passportsdk.account_sso.BuildConfig;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class FidNonce extends FidNonceBase {
    private static final String CHARSET_NAME_UTF8 = "UTF-8";
    private static final String TAG = "FidNonce";

    public enum Type {
        NATIVE,
        WEB_VIEW
    }

    public FidNonce(String str, String str2) {
        super(str, str2);
    }

    public static class Builder {
        public FidNonce build(Type type) {
            return new Builder().build(type, ServerTimeUtil.getComputer(), FidSigningUtil.getFidSigner());
        }

        FidNonce build(Type type, ServerTimeUtil.IServerTimeComputer iServerTimeComputer, FidSigningUtil.IFidSigner iFidSigner) {
            if (type == null) {
                throw new IllegalArgumentException("type == null");
            }
            if (iServerTimeComputer == null || iFidSigner == null) {
                return null;
            }
            try {
                if (!iFidSigner.canSign()) {
                    return null;
                }
                String strBuildPlain = buildPlain(type == Type.NATIVE ? "n" : "wb", generateNonce(iServerTimeComputer.computeServerTime()), getVersion());
                try {
                    String strEncodeToString = Base64.encodeToString(strBuildPlain.getBytes("UTF-8"), 10);
                    try {
                        byte[] bArrSign = iFidSigner.sign(strBuildPlain.getBytes("UTF-8"));
                        if (bArrSign == null) {
                            return null;
                        }
                        try {
                            return new FidNonce(strEncodeToString, new String(Base64.encode(bArrSign, 10), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            AccountLogger.log(FidNonce.TAG, e);
                            return null;
                        }
                    } catch (FidSigningUtil.FidSignException e2) {
                        AccountLogger.log(FidNonce.TAG, e2);
                        return null;
                    } catch (UnsupportedEncodingException e3) {
                        AccountLogger.log(FidNonce.TAG, e3);
                        return null;
                    }
                } catch (UnsupportedEncodingException e4) {
                    AccountLogger.log(FidNonce.TAG, e4);
                    return null;
                }
            } catch (FidSigningUtil.FidSignException e5) {
                AccountLogger.log(FidNonce.TAG, e5);
                return null;
            }
        }

        String buildPlain(String str, String str2, String str3) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("tp", str);
                jSONObject.put(Constants.NONCE, str2);
                jSONObject.put("v", str3);
                return jSONObject.toString();
            } catch (JSONException unused) {
                throw new IllegalStateException("should not happen");
            }
        }

        String getVersion() {
            return BuildConfig.VERSION_NAME;
        }

        String generateNonce(long j) {
            return NonceCoder.generateNonce(j);
        }
    }
}
