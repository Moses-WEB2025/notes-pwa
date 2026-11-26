package com.xiaomi.accountsdk.request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes4.dex */
public class TlsCompatSocketFactory extends SSLSocketFactory {
    private static final String[] PROTOCOL_ARRAY = {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"};
    final SSLSocketFactory delegate;

    public TlsCompatSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.delegate = sSLSocketFactory;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.delegate.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return patch(this.delegate.createSocket(socket, str, i, z));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        return patch(this.delegate.createSocket(str, i));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return patch(this.delegate.createSocket(str, i, inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return patch(this.delegate.createSocket(inetAddress, i));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return patch(this.delegate.createSocket(inetAddress, i, inetAddress2, i2));
    }

    private Socket patch(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(PROTOCOL_ARRAY);
        }
        return socket;
    }

    public static SSLSocketFactory sslSocketFactory() throws IllegalStateException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        SSLContext sSLContext = SSLContext.getInstance(cn.kuaipan.android.http.client.SSLSocketFactory.TLS);
        sSLContext.init(null, new TrustManager[]{systemDefaultTrustManager()}, null);
        return new TlsCompatSocketFactory(sSLContext.getSocketFactory());
    }

    private static X509TrustManager systemDefaultTrustManager() throws IllegalStateException, NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length == 1) {
            TrustManager trustManager = trustManagers[0];
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
    }
}
