package cn.kuaipan.kss.implement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.appmaster.RequestDownloadParse;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.HttpUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class KssDownloadFile {
    public static HttpClientProxyHelper getDownloadProxyHelper(Context context, String str, ExtendedAuthToken extendedAuthToken) throws BadPaddingException, JSONException, CloudServerException, IllegalBlockSizeException, URISyntaxException, IOException {
        return null;
    }

    public KssDef.KssAPIResult downloadFile(Context context, HttpClientProxyHelper httpClientProxyHelper, KssDef.OnUpDownload onUpDownload, JSONObject jSONObject, String str, boolean z) {
        return downloadFile(context, httpClientProxyHelper, onUpDownload, jSONObject, new File(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [cn.kuaipan.kss.implement.HttpClientDownload] */
    /* JADX WARN: Type inference failed for: r7v5, types: [cn.kuaipan.kss.implement.HttpClientDownload] */
    /* JADX WARN: Type inference failed for: r7v6, types: [cn.kuaipan.kss.implement.HttpClientDownload] */
    /* JADX WARN: Type inference failed for: r7v8, types: [cn.kuaipan.kss.implement.HttpClientDownload] */
    public KssDef.KssAPIResult downloadFile(Context context, HttpClientProxyHelper httpClientProxyHelper, KssDef.OnUpDownload onUpDownload, JSONObject jSONObject, File file) throws Throwable {
        Throwable th;
        KPDownloadTransControl kPDownloadTransControl;
        KssDef.KssAPIResult kssAPIResult = KssDef.KssAPIResult.Error;
        KPDownloadTransControl kPDownloadTransControl2 = null;
        try {
            try {
                RequestDownloadParse requestDownloadParse = new RequestDownloadParse();
                requestDownloadParse.parseRequestDownloadInfo(jSONObject.toString());
                jSONObject = new HttpClientDownload(HttpUtils.getHttpClient(), httpClientProxyHelper);
                try {
                    jSONObject.init(requestDownloadParse, onUpDownload, getNetState(context));
                    kPDownloadTransControl = new KPDownloadTransControl();
                    try {
                        kPDownloadTransControl.init(file, true);
                        KssDef.KssAPIResult kssAPIResultDownload = jSONObject.download(kPDownloadTransControl);
                        kPDownloadTransControl.close();
                        jSONObject.terminal();
                        return kssAPIResultDownload;
                    } catch (FileNotFoundException unused) {
                        kPDownloadTransControl2 = kPDownloadTransControl;
                        KssDef.KssAPIResult kssAPIResult2 = KssDef.KssAPIResult.Error;
                        if (kPDownloadTransControl2 != null) {
                            kPDownloadTransControl2.close();
                        }
                        if (jSONObject != 0) {
                            jSONObject.terminal();
                        }
                        return kssAPIResult2;
                    } catch (Exception unused2) {
                        kPDownloadTransControl2 = kPDownloadTransControl;
                        KssDef.KssAPIResult kssAPIResult3 = KssDef.KssAPIResult.Error;
                        if (kPDownloadTransControl2 != null) {
                            kPDownloadTransControl2.close();
                        }
                        if (jSONObject != 0) {
                            jSONObject.terminal();
                        }
                        return kssAPIResult3;
                    } catch (Throwable th2) {
                        th = th2;
                        if (kPDownloadTransControl != null) {
                            kPDownloadTransControl.close();
                        }
                        if (jSONObject != 0) {
                            jSONObject.terminal();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                } catch (Exception unused4) {
                }
            } catch (FileNotFoundException unused5) {
                jSONObject = 0;
            } catch (Exception unused6) {
                jSONObject = 0;
            } catch (Throwable th3) {
                jSONObject = 0;
                th = th3;
                kPDownloadTransControl = null;
            }
        } catch (Throwable th4) {
            kPDownloadTransControl = null;
            th = th4;
        }
    }

    private static KssDef.NetState getNetState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            return null;
        }
        NetworkInfo.State state = connectivityManager.getNetworkInfo(1).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return KssDef.NetState.Wifi;
        }
        return KssDef.NetState.MOBILE;
    }
}
