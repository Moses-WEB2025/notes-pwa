package com.xiaomi.micloudsdk.request.utils;

import android.util.Base64;
import android.util.Log;
import com.market.sdk.reflect.Field;
import com.xiaomi.micloudsdk.exception.CipherException;
import com.xiaomi.micloudsdk.utils.AESWithIVCoder;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CloudAESWithIVCoder extends AESWithIVCoder {
    private static final String TAG = "CloudAESWithIVCoder";

    public CloudAESWithIVCoder(String str) {
        super(str);
    }

    @Override // com.xiaomi.micloudsdk.utils.AESWithIVCoder, com.xiaomi.micloudsdk.utils.CryptCoder
    public String decrypt(String str) throws CipherException {
        checkThreadIdThrow();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(Field.DOUBLE_SIGNATURE_PRIMITIVE);
            String[] strArrSplit = jSONObject.getString("S").split(":");
            if (strArrSplit.length != 2) {
                throw new SecurityException("not 2 columns in security info");
            }
            String str2 = strArrSplit[0];
            String str3 = strArrSplit[1];
            String strEncodeToString = Base64.encodeToString(encryptHMACSha1(String.format("%s:%s", str2, new String(string)).getBytes("UTF-8"), Base64.decode(this.aesKey, 0)), 11);
            if (str3.equals(strEncodeToString)) {
                return super.decrypt(string);
            }
            throw new CipherException("server hash " + str3 + " and local hash " + strEncodeToString + " dismatch");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "decrypt error", e);
            throw new CipherException("decrypt error", e);
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "decrypt error", e2);
            throw new CipherException("decrypt error", e2);
        } catch (NullPointerException e3) {
            Log.e(TAG, "decrypt error", e3);
            throw new CipherException("decrypt error", e3);
        } catch (JSONException e4) {
            Log.e(TAG, "decrypt error", e4);
            throw new CipherException("decrypt error", e4);
        }
    }

    public static byte[] encryptHMACSha1(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            mac.update(bArr);
            return mac.doFinal();
        } catch (InvalidKeyException e) {
            Log.e(TAG, "encryptHMACSha1 error", e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Log.e(TAG, "encryptHMACSha1 error", e2);
            return null;
        }
    }
}
