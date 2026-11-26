package com.xiaomi.ai.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.miai.SDKConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes4.dex */
public class g {
    private PackageManager a;
    private String b;
    private String c;
    private String d;
    private String e;

    public g(Context context) {
        this.a = context.getPackageManager();
        this.b = context.getPackageName();
        Signature[] signatureArr = new Signature[0];
        try {
            signatureArr = this.a.getPackageInfo(context.getPackageName(), 64).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (signatureArr.length == 0) {
            return;
        }
        Signature signature = signatureArr[0];
        this.c = a("MD5", signature.toByteArray());
        this.d = a("SHA-1", signature.toByteArray());
        this.e = a(SDKConstants.DigestAlgorithm.SHA_256, signature.toByteArray());
    }

    private static String a(String str, byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            messageDigest.reset();
            messageDigest.update(bArr);
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString);
            if (i < bArr.length - 1) {
                sb.append(CoreConstants.COLON_CHAR);
            }
        }
        return sb.toString();
    }

    public String a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.e;
    }

    public String d() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.b);
        stringBuffer.append(";");
        stringBuffer.append(this.c);
        stringBuffer.append(";");
        stringBuffer.append(this.e);
        return stringBuffer.toString();
    }
}
