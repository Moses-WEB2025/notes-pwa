package cn.kuaipan.android.http.client;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class IgnoreCertificationTrustManger implements X509TrustManager {
    private X509Certificate[] certificates;

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (this.certificates == null) {
            this.certificates = x509CertificateArr;
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (this.certificates == null) {
            this.certificates = x509CertificateArr;
        }
    }
}
