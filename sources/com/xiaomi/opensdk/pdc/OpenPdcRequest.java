package com.xiaomi.opensdk.pdc;

import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.classic.spi.CallerData;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import defpackage.R2;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OpenPdcRequest {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final int DEFAULT_RETRY_TIME = 300000;
    private static final String LOCATION = "Location";
    private static final String ORIGIN = "Origin";
    private static final String REDIRECT_COUNT = "X-XIAOMI-REDIRECT-COUNT";
    private static final String RETRY_AFTER = "Retry-After";
    private static final String SUPPORT_REDIRECT = "X-XIAOMI-SUPPORT-REDIRECT";
    private static final String TAG = "OpenPdcRequest";
    private static final int TIMEOUT = 30000;
    private static final String USER_AGENT = "User-Agent";
    private static final String USER_COUNTRY = "X-XIAOMI-USER-COUNTRY";
    private static final String UTF8 = "UTF-8";

    public static class SyncOperationResult {
        public int code;
        public JSONObject data;
        public String description;
        public Constants.ErrorType errorType;
        public boolean isSuccess;
        public int responseCode;
        public String result;
        public long retryTime;

        public SyncOperationResult() {
        }

        public SyncOperationResult(int i, JSONObject jSONObject) throws JSONException {
            this.responseCode = i;
            if (jSONObject != null) {
                this.result = jSONObject.optString("reason");
                this.description = jSONObject.optString("description");
                int iOptInt = jSONObject.optInt("code", -1);
                this.code = iOptInt;
                Constants.ErrorType errorTypeConvertErrorCode = OpenPdcRequest.convertErrorCode(i, iOptInt);
                this.errorType = errorTypeConvertErrorCode;
                this.isSuccess = errorTypeConvertErrorCode == Constants.ErrorType.OK;
                if (jSONObject.optBoolean("retriable")) {
                    this.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                    this.retryTime = DesktopRecommendInfo.DEFAULT_CACHE_TIME;
                }
                this.data = jSONObject.optJSONObject("data");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Constants.ErrorType convertErrorCode(int i, int i2) {
        if (i == 200) {
            return Constants.ErrorType.OK;
        }
        if (i != 403) {
            if (i == 400) {
                switch (i2) {
                    case R2.drawable.miuix_appcompat_action_mode_button_bg_single_disable_light /* 11000 */:
                        return Constants.ErrorType.UNRETRIABLE_ERROR;
                    case 11001:
                        return Constants.ErrorType.UNRETRIABLE_ERROR;
                    case R2.drawable.miuix_appcompat_action_mode_button_bg_single_normal_light /* 11002 */:
                        return Constants.ErrorType.UNRETRIABLE_ERROR;
                    case R2.drawable.miuix_appcompat_action_mode_button_bg_single_pressed_dark /* 11003 */:
                        return Constants.ErrorType.NEED_RESYNC;
                    case R2.drawable.miuix_appcompat_action_mode_button_bg_single_pressed_light /* 11004 */:
                        return Constants.ErrorType.UNRETRIABLE_ERROR;
                    default:
                        switch (i2) {
                            case R2.id.sharedValueSet /* 14001 */:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                            case R2.id.sharedValueUnset /* 14002 */:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                            case R2.id.sharp_view /* 14003 */:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                            case R2.id.shortcut /* 14004 */:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                            case R2.id.showCustom /* 14005 */:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                            default:
                                return Constants.ErrorType.UNRETRIABLE_ERROR;
                        }
                }
            }
            if (i != 401) {
                if (i == 500) {
                    return Constants.ErrorType.RETRIABLE_ERROR;
                }
                if (i == 501) {
                    return Constants.ErrorType.RETRIABLE_ERROR;
                }
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            }
        }
        switch (i2) {
            case 96001:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96002:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96003:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96004:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96005:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96006:
            case 96009:
            default:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96007:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96008:
                return Constants.ErrorType.AUTH_FAILED;
            case 96010:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96011:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96012:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
            case 96013:
                return Constants.ErrorType.UNRETRIABLE_ERROR;
        }
    }

    public static SyncOperationResult request(OpenPdcSyncServerAdapter.Environment environment, String str, String str2, Map<String, String> map, Map<String, String> map2, String str3) throws SyncException {
        int iIntValue;
        Log.v(TAG, "request started at " + str + " as " + str2);
        String strAppendUrl = appendUrl(str, map);
        int i = 0;
        while (true) {
            HttpsURLConnection httpsURLConnection = null;
            try {
                try {
                    try {
                        HttpsURLConnection httpsURLConnectionMakeConn = makeConn(strAppendUrl, map2);
                        httpsURLConnectionMakeConn.setRequestMethod(str2);
                        httpsURLConnectionMakeConn.setInstanceFollowRedirects(false);
                        httpsURLConnectionMakeConn.setRequestProperty("X-XIAOMI-SUPPORT-REDIRECT", "true");
                        httpsURLConnectionMakeConn.setRequestProperty("Origin", "Default");
                        httpsURLConnectionMakeConn.setRequestProperty("Content-Type", "application/json");
                        if (i > 0) {
                            httpsURLConnectionMakeConn.setRequestProperty("X-XIAOMI-REDIRECT-COUNT", String.valueOf(i));
                        }
                        if (environment != null) {
                            httpsURLConnectionMakeConn.setRequestProperty(USER_COUNTRY, environment.getUserCountry());
                            if (environment.getUserAgent() != null) {
                                httpsURLConnectionMakeConn.setRequestProperty("User-Agent", environment.getUserAgent());
                            }
                        }
                        httpsURLConnectionMakeConn.connect();
                        if (!TextUtils.isEmpty(str3)) {
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpsURLConnectionMakeConn.getOutputStream());
                            try {
                                bufferedOutputStream.write(str3.getBytes("UTF-8"));
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException unused) {
                                }
                            } catch (Throwable th) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException unused2) {
                                }
                                throw th;
                            }
                        }
                        int responseCode = httpsURLConnectionMakeConn.getResponseCode();
                        if (responseCode != 301 && responseCode != 302 && responseCode != 307) {
                            if (responseCode == 503) {
                                String headerField = httpsURLConnectionMakeConn.getHeaderField("Retry-After");
                                if (TextUtils.isEmpty(headerField)) {
                                    iIntValue = 300;
                                } else {
                                    try {
                                        iIntValue = Integer.valueOf(headerField).intValue();
                                    } catch (NumberFormatException unused3) {
                                        iIntValue = 300;
                                    }
                                }
                                Log.w(TAG, "Got retry after " + iIntValue + " seconds");
                                SyncOperationResult syncOperationResult = new SyncOperationResult();
                                syncOperationResult.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                                syncOperationResult.retryTime = iIntValue * 1000;
                                if (httpsURLConnectionMakeConn != null) {
                                    httpsURLConnectionMakeConn.disconnect();
                                }
                                return syncOperationResult;
                            }
                            InputStream inputStream = responseCode == 200 ? httpsURLConnectionMakeConn.getInputStream() : httpsURLConnectionMakeConn.getErrorStream();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i2 = inputStream.read(bArr);
                                if (i2 == -1) {
                                    break;
                                }
                                byteArrayOutputStream.write(bArr, 0, i2);
                            }
                            String str4 = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                            SyncOperationResult syncOperationResult2 = new SyncOperationResult(responseCode, new JSONObject(str4));
                            if (!syncOperationResult2.isSuccess) {
                                Log.w(TAG, "Got error: status " + responseCode + " with body " + str4, new Throwable());
                            }
                            if (httpsURLConnectionMakeConn != null) {
                                httpsURLConnectionMakeConn.disconnect();
                            }
                            return syncOperationResult2;
                        }
                        String headerField2 = httpsURLConnectionMakeConn.getHeaderField("Location");
                        Log.w(TAG, "Got " + responseCode + " redirection to " + headerField2);
                        if (environment != null) {
                            try {
                                environment.setHost(new URI(headerField2).getHost());
                                i++;
                                if (i > 15) {
                                    throw new SyncException("Redirect Times Exceed 15");
                                }
                            } catch (URISyntaxException unused4) {
                            }
                        }
                        if (httpsURLConnectionMakeConn != null) {
                            httpsURLConnectionMakeConn.disconnect();
                        }
                        strAppendUrl = headerField2;
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "request error", e);
                        throw new SyncException(e);
                    } catch (JSONException e2) {
                        Log.e(TAG, "request error", e2);
                        throw new SyncException(e2);
                    }
                } catch (Throwable th2) {
                    if (0 != 0) {
                        httpsURLConnection.disconnect();
                    }
                    throw th2;
                }
            } catch (IOException e3) {
                Log.e(TAG, "request error", e3);
                if (!RetriableException.isRetriableException(e3)) {
                    throw new SyncException(e3);
                }
                SyncOperationResult syncOperationResult3 = new SyncOperationResult();
                syncOperationResult3.isSuccess = false;
                syncOperationResult3.errorType = Constants.ErrorType.RETRIABLE_ERROR;
                syncOperationResult3.result = SNSAuthProvider.VALUE_SNS_ERROR;
                syncOperationResult3.retryTime = DesktopRecommendInfo.DEFAULT_CACHE_TIME;
                if (0 != 0) {
                    httpsURLConnection.disconnect();
                }
                return syncOperationResult3;
            }
        }
    }

    protected static HttpsURLConnection makeConn(String str, Map<String, String> map) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
        httpsURLConnection.setInstanceFollowRedirects(false);
        httpsURLConnection.setConnectTimeout(30000);
        httpsURLConnection.setReadTimeout(30000);
        httpsURLConnection.setUseCaches(false);
        if (map != null) {
            httpsURLConnection.setRequestProperty("Cookie", makeCookie(map));
        }
        return httpsURLConnection;
    }

    private static void joinMap(Map<String, String> map, String str, boolean z, StringBuilder sb) {
        String key;
        if (map == null) {
            return;
        }
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i > 0) {
                sb.append(str);
            }
            if (z) {
                try {
                    key = URLEncoder.encode(entry.getKey(), "UTF-8");
                } catch (UnsupportedEncodingException unused) {
                    Log.e(TAG, "Cannot encode map values!");
                }
            } else {
                key = entry.getKey();
            }
            String strEncode = z ? URLEncoder.encode(entry.getValue(), "UTF-8") : entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(strEncode);
            i++;
        }
    }

    private static String appendUrl(String str, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("origin is not allowed null");
        }
        if (map.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        if (!str.contains(CallerData.NA)) {
            sb.append(CallerData.NA);
        } else {
            sb.append("&");
        }
        joinMap(map, "&", true, sb);
        return sb.toString();
    }

    private static String makeCookie(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        joinMap(map, "; ", false, sb);
        return sb.toString();
    }
}
