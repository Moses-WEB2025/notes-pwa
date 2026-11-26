package miui.cloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.Field;
import com.market.sdk.utils.Language;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.SystemUtils;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import miui.cloud.os.SystemProperties;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes5.dex */
public class CloudRequestUtils {
    private static final String TAG = "CloudRequestUtils";
    public static final String URL_CALL_LOG_BASE;
    public static final String URL_CONTACT_BASE;
    public static final String URL_DEV_BASE;
    public static final String URL_DEV_SETTING = "/api/user/device/setting";
    public static final String URL_FIND_DEVICE_BASE;
    public static final String URL_GALLERY_BASE;
    public static final String URL_MICARD_BASE;
    public static final String URL_MICLOUD_STATUS_BASE;
    public static final String URL_MMS_BASE;
    public static final String URL_MUSIC_BASE;
    public static final String URL_NOTE_BASE;
    public static final String URL_RICH_MEDIA_BASE;
    public static final String URL_WIFI_BASE;
    public static final String URL_WIFI_SHARE_BASE;
    private static String sUserAgent;

    static {
        URL_RICH_MEDIA_BASE = MiCloudConstants.USE_PREVIEW ? "http://api.micloud.preview.n.xiaomi.net" : "https://fileapi.micloud.xiaomi.net";
        URL_CONTACT_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://contactapi.micloud.xiaomi.net";
        URL_MICARD_BASE = MiCloudConstants.USE_PREVIEW ? "http://micardapi.micloud.preview.n.xiaomi.net" : "https://micardapi.micloud.xiaomi.net";
        URL_MMS_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://smsapi.micloud.xiaomi.net";
        URL_GALLERY_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://galleryapi.micloud.xiaomi.net";
        URL_FIND_DEVICE_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "http://findapi.micloud.xiaomi.net";
        URL_WIFI_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://wifiapi.micloud.xiaomi.net";
        URL_NOTE_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://noteapi.micloud.xiaomi.net";
        URL_MUSIC_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://musicapi.micloud.xiaomi.net";
        URL_CALL_LOG_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://phonecallapi.micloud.xiaomi.net";
        URL_WIFI_SHARE_BASE = MiCloudConstants.USE_PREVIEW ? "http://micloud.preview.n.xiaomi.net" : "https://wifisharingapi.micloud.xiaomi.net";
        URL_DEV_BASE = MiCloudConstants.USE_PREVIEW ? "http://api.device.preview.n.xiaomi.net" : "https://api.device.xiaomi.net";
        URL_MICLOUD_STATUS_BASE = MiCloudConstants.USE_PREVIEW ? "http://statusapi.micloud.preview.n.xiaomi.net" : "https://statusapi.micloud.xiaomi.net";
    }

    public static String getUserAgent() {
        if (sUserAgent == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Build.MODEL + InternalZipConstants.ZIP_FILE_SEPARATOR);
            String str = SystemProperties.get("ro.product.mod_device");
            if (!TextUtils.isEmpty(str)) {
                sb.append(str);
            } else {
                sb.append(Build.MODEL);
            }
            sb.append("; MIUI/");
            sb.append(Build.VERSION.INCREMENTAL);
            sb.append(" E/");
            sb.append(SystemUtils.getMiSystemVersionName());
            sb.append(" B/");
            if (miui.os.Build.IS_ALPHA_BUILD) {
                sb.append(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
            } else if (miui.os.Build.IS_DEVELOPMENT_VERSION) {
                sb.append(Field.DOUBLE_SIGNATURE_PRIMITIVE);
            } else if (miui.os.Build.IS_STABLE_VERSION) {
                sb.append("S");
            } else {
                sb.append("null");
            }
            sb.append(" L/");
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            if (language != null) {
                sb.append(convertObsoleteLanguageCodeToNew(language));
                String country = locale.getCountry();
                if (country != null) {
                    sb.append("-");
                    sb.append(country.toUpperCase());
                }
            } else {
                sb.append("EN");
            }
            sb.append(" LO/");
            String region = miui.os.Build.getRegion();
            if (!TextUtils.isEmpty(region)) {
                sb.append(region.toUpperCase());
            } else {
                sb.append("null");
            }
            sUserAgent = sb.toString();
        }
        return sUserAgent;
    }

    private static String convertObsoleteLanguageCodeToNew(String str) {
        if (str == null) {
            return null;
        }
        if ("iw".equals(str)) {
            return "he";
        }
        if (Language.LA_IN.equals(str)) {
            return "id";
        }
        return "ji".equals(str) ? "yi" : str;
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static void ensureNotOnMainThread(Context context) {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null && looperMyLooper == context.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    public static void waitUntilNetworkConnected(Context context) throws InterruptedException {
        ensureNotOnMainThread(context);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        ConnectivityResumedReceiver connectivityResumedReceiver = new ConnectivityResumedReceiver(context);
        context.registerReceiver(connectivityResumedReceiver, intentFilter);
        try {
            connectivityResumedReceiver.await();
        } catch (ExecutionException unused) {
        } finally {
            context.unregisterReceiver(connectivityResumedReceiver);
        }
    }

    private static class ConnectivityResumedReceiver extends BroadcastReceiver {
        private final Context mContext;
        private final AsyncFuture<Boolean> mFuture;

        private static final class AsyncFuture<V> extends FutureTask<V> {
            public AsyncFuture() {
                super(new Callable<V>() { // from class: miui.cloud.CloudRequestUtils.ConnectivityResumedReceiver.AsyncFuture.1
                    @Override // java.util.concurrent.Callable
                    public V call() throws Exception {
                        throw new IllegalStateException("this should never be called");
                    }
                });
            }

            public void setResult(V v) {
                set(v);
            }
        }

        private ConnectivityResumedReceiver(Context context) {
            this.mFuture = new AsyncFuture<>();
            this.mContext = context;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("noConnectivity", false)) {
                return;
            }
            Log.i(CloudRequestUtils.TAG, "connectivity resumed");
            this.mFuture.setResult(true);
        }

        public void await() throws ExecutionException, InterruptedException {
            if (CloudRequestUtils.isNetworkConnected(this.mContext)) {
                this.mFuture.setResult(true);
            }
            this.mFuture.get();
        }
    }
}
