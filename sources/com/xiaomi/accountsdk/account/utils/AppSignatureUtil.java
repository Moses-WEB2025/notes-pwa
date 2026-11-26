package com.xiaomi.accountsdk.account.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.accountsdk.utils.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes4.dex */
public class AppSignatureUtil {
    public static AppSignatureHash getValidSignatureHash(Context context, String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            Signature signature = getSignature(context, str);
            if (signature != null) {
                byte[] byteArray = signature.toByteArray();
                byteArrayInputStream = new ByteArrayInputStream(byteArray);
                try {
                    ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream)).checkValidity();
                    AppSignatureHash appSignatureHashBuild = new AppSignatureHash.Builder().sha1(CloudCoder.getDataSha1Digest(byteArray).toUpperCase()).md5(CloudCoder.getDataMd5Digest(byteArray).toUpperCase()).build();
                    IOUtils.closeQuietly(byteArrayInputStream);
                    return appSignatureHashBuild;
                } catch (CertificateException unused) {
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream2 = byteArrayInputStream;
                    IOUtils.closeQuietly(byteArrayInputStream2);
                    throw th;
                }
            } else {
                IOUtils.closeQuietly((InputStream) null);
                return null;
            }
        } catch (CertificateException unused2) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        IOUtils.closeQuietly(byteArrayInputStream);
        return null;
    }

    private static Signature getSignature(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 64).signatures[0];
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }
}
