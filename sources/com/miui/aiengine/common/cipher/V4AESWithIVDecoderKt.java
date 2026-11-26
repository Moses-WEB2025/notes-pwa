package com.miui.aiengine.common.cipher;

import android.util.Log;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;

/* JADX INFO: compiled from: V4AESWithIVDecoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\u001a\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"HMAC_SHA1", "", "encryptHMACSha1", "", "data", "key", "net_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class V4AESWithIVDecoderKt {
    public static final String HMAC_SHA1 = "HmacSHA1";

    public static final byte[] encryptHMACSha1(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            mac.update(bArr);
            return mac.doFinal();
        } catch (InvalidKeyException e) {
            Log.e("V4AESWithIVDecoder", "encryptHMACSha1 error", e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Log.e("V4AESWithIVDecoder", "encryptHMACSha1 error", e2);
            return null;
        }
    }
}
