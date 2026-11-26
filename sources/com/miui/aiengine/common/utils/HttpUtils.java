package com.miui.aiengine.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.classic.spi.CallerData;
import com.google.common.net.HttpHeaders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.LogFilterLevel;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.RequestManager;
import com.miui.aiengine.common.cipher.AESCoder;
import com.miui.aiengine.common.cipher.CipherException;
import com.miui.aiengine.common.cipher.CryptCoder;
import com.miui.aiengine.common.cipher.V4AESWithIVDecoder;
import com.miui.aiengine.common.cloud.OkHttpClientAgent;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.aiengine.common.request.KVPair;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: HttpUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J4\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u00072\"\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u0017JH\u0010\u0018\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00072\"\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u0007J\u0018\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0007JN\u0010\"\u001a\u00020#2\"\u0010$\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u00172\"\u0010%\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u0017J<\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070'2\b\u0010(\u001a\u0004\u0018\u00010\u00072\b\u0010)\u001a\u0004\u0018\u00010\u00072\u0014\u0010*\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010+J^\u0010,\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u00172\b\u0010-\u001a\u0004\u0018\u00010.2\b\u0010/\u001a\u0004\u0018\u00010\u00072&\u0010%\u001a\"\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0016j\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\u0017J\u008e\u0001\u00100\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u00172\"\u00101\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0016j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007`\u00172\u0014\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010'2\b\u00103\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u00104\u001a\u00020 2\b\b\u0002\u00105\u001a\u00020\u00052\b\b\u0002\u00106\u001a\u00020\u00052\b\b\u0002\u00107\u001a\u00020\u0005J\u000e\u00108\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0007J\u0018\u00109\u001a\u00020.2\u0006\u0010\u001b\u001a\u00020\u00072\b\u0010\u001d\u001a\u0004\u0018\u00010\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006:"}, d2 = {"Lcom/miui/aiengine/common/utils/HttpUtils;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DBG", "", "HEADER_PARAM_REQUEST_APK_VERSION", "", "HEADER_PARAM_REQUEST_APK_VERSION_CODE", "UA", "APK_VERSION_", "APK_VERSION_CODE_", "HEADER_COOKIE", "httpClient", "Lokhttp3/OkHttpClient;", "getHttpClient", "()Lokhttp3/OkHttpClient;", "httpClient$delegate", "Lkotlin/Lazy;", "appendUrl", HttpHeaders.ReferrerPolicyValues.ORIGIN, "nameValuePairs", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getSignature", "httpMethod", "Lcom/miui/aiengine/common/request/HttpMethod;", "url", "paramList", "security", "isHttpStatusCodeOKButCloudResponseCodeError", "httpStatusCode", "", "cloudResponse", "insertBasePackageInfo", "", "headerParams", "params", "getCookie", "Lcom/miui/aiengine/common/request/KVPair;", "encryptedUserId", "serviceToken", "cookies", "", "encodeParameters", "coder", "Lcom/miui/aiengine/common/cipher/CryptCoder;", Constants.NONCE, "insertHeadConfig2Headers", "header", "cookie", "contentType", "redirectCount", "gzip", "json", "sse", "isV4Url", "getCryptCoder", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HttpUtils extends Logger {
    private static final String APK_VERSION_ = "_apkversion";
    private static final String APK_VERSION_CODE_ = "_apkversioncode";
    private static final boolean DBG = true;
    private static final String HEADER_COOKIE = "Cookie";
    private static final String HEADER_PARAM_REQUEST_APK_VERSION = "apk-version";
    private static final String HEADER_PARAM_REQUEST_APK_VERSION_CODE = "apk-version-code";
    private static final String UA = "User-Agent";
    public static final HttpUtils INSTANCE = new HttpUtils();

    /* JADX INFO: renamed from: httpClient$delegate, reason: from kotlin metadata */
    private static final Lazy httpClient = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.utils.HttpUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return HttpUtils.httpClient_delegate$lambda$2();
        }
    });

    /* JADX INFO: compiled from: HttpUtils.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogFilterLevel.values().length];
            try {
                iArr[LogFilterLevel.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogFilterLevel.DETAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LogFilterLevel.BASIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LogFilterLevel.BODY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private HttpUtils() {
        super(NetConfig.TAG);
    }

    public final OkHttpClient getHttpClient() {
        return (OkHttpClient) httpClient.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient httpClient_delegate$lambda$2() {
        HttpLoggingInterceptor.Level level;
        OkHttpClientAgent okHttpClientAgentNewInstance = OkHttpClientAgent.INSTANCE.newInstance();
        OkHttpClient.Builder mProxy = okHttpClientAgentNewInstance.getMProxy();
        mProxy.connectTimeout(NetConfig.INSTANCE.getTimeOutConfig().getConnectTimeout(), TimeUnit.SECONDS);
        mProxy.readTimeout(NetConfig.INSTANCE.getTimeOutConfig().getReadTimeout(), TimeUnit.SECONDS);
        mProxy.writeTimeout(NetConfig.INSTANCE.getTimeOutConfig().getWriteTimeout(), TimeUnit.SECONDS);
        mProxy.callTimeout(NetConfig.INSTANCE.getTimeOutConfig().getCallTimeout(), TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.miui.aiengine.common.utils.HttpUtils$httpClient$2$1$loggingInterceptor$1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                LogUtils.INSTANCE.logDebug(NetConfig.TAG, "OkHttpClientAgent", "okio:" + message);
            }
        });
        int i = WhenMappings.$EnumSwitchMapping$0[NetConfig.INSTANCE.getNET_WORK_LOG_LEVEL().ordinal()];
        if (i == 1) {
            level = HttpLoggingInterceptor.Level.NONE;
        } else if (i == 2) {
            level = HttpLoggingInterceptor.Level.HEADERS;
        } else if (i == 3) {
            level = HttpLoggingInterceptor.Level.BASIC;
        } else {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            level = HttpLoggingInterceptor.Level.BODY;
        }
        httpLoggingInterceptor.level(level);
        mProxy.addInterceptor(httpLoggingInterceptor);
        return okHttpClientAgentNewInstance.getMProxy().build();
    }

    public final String appendUrl(String origin, HashMap<String, String> nameValuePairs) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(nameValuePairs, "nameValuePairs");
        logInfo("host:" + origin + " ,append:" + nameValuePairs);
        if (origin == null) {
            throw new NullPointerException("origin is not allowed null");
        }
        StringBuilder sb = new StringBuilder(origin);
        if (!StringsKt.contains$default((CharSequence) origin, (CharSequence) CallerData.NA, false, 2, (Object) null) && !nameValuePairs.isEmpty()) {
            sb.append(CallerData.NA);
        }
        Set<Map.Entry<String, String>> setEntrySet = nameValuePairs.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        List list = CollectionsKt.toList(setEntrySet);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append("&");
            }
            Object key = ((Map.Entry) list.get(i)).getKey();
            Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
            Object value = ((Map.Entry) list.get(i)).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            sb.append((String) key);
            sb.append("=");
            sb.append(URLEncoder.encode((String) value, "UTF-8"));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        logInfo("result url length is: " + string.length() + ",url:" + string);
        return string;
    }

    public final String getSignature(HttpMethod httpMethod, String url, HashMap<String, String> paramList, String security) throws UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(httpMethod, "httpMethod");
        Intrinsics.checkNotNullParameter(paramList, "paramList");
        TreeMap treeMap = new TreeMap();
        Set<Map.Entry<String, String>> setEntrySet = paramList.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
            if (((CharSequence) key).length() > 0) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        return CloudCoderUtils.INSTANCE.generateSignature(httpMethod.getName(), url, treeMap, security);
    }

    public final boolean isHttpStatusCodeOKButCloudResponseCodeError(int httpStatusCode, String cloudResponse) {
        int iOptInt = -1;
        if (cloudResponse == null) {
            cloudResponse = "";
        }
        try {
            iOptInt = new JSONObject(cloudResponse).optInt("code", -1);
        } catch (JSONException e) {
            Log.e("HttpUtils", "isHttpStatusCodeOKButCloudResponseCodeError: ", e);
        }
        return httpStatusCode == 200 && iOptInt != 0;
    }

    public final void insertBasePackageInfo(HashMap<String, String> headerParams, HashMap<String, String> params) {
        Intrinsics.checkNotNullParameter(headerParams, "headerParams");
        Intrinsics.checkNotNullParameter(params, "params");
        Context context = RequestManager.INSTANCE.getContext();
        String packageName = context.getPackageName();
        String versionCode = ApkUtils.INSTANCE.getVersionCode(context);
        String versionName = ApkUtils.INSTANCE.getVersionName(context);
        try {
            if (TextUtils.isEmpty(headerParams.get(HEADER_PARAM_REQUEST_APK_VERSION_CODE))) {
                headerParams.put(HEADER_PARAM_REQUEST_APK_VERSION_CODE, versionCode);
            }
            if (TextUtils.isEmpty(headerParams.get(HEADER_PARAM_REQUEST_APK_VERSION))) {
                headerParams.put(HEADER_PARAM_REQUEST_APK_VERSION, versionName);
            }
            if (TextUtils.isEmpty(headerParams.get(HEADER_PARAM_REQUEST_APK_VERSION_CODE))) {
                headerParams.put(HEADER_PARAM_REQUEST_APK_VERSION_CODE, versionCode);
            }
            if (TextUtils.isEmpty(headerParams.get("User-Agent"))) {
                headerParams.put("User-Agent", OsUtils.INSTANCE.getUA());
            }
            if (TextUtils.isEmpty(params.get("_apkversion"))) {
                params.put("_apkversion", versionName);
            }
            if (TextUtils.isEmpty(params.get("_apkversioncode"))) {
                params.put("_apkversioncode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Request", packageName + " get apk version error —— Exception: " + e.getMessage());
        }
    }

    public final KVPair<String, String> getCookie(String encryptedUserId, String serviceToken, Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();
        if (serviceToken != null) {
            sb.append("serviceToken=").append(serviceToken);
        }
        if (encryptedUserId != null) {
            sb.append(sb.length() == 0 ? "cUserId=" : "; cUserId=");
            sb.append(encryptedUserId);
        }
        if (cookies != null && cookies.containsKey("userId")) {
            sb.append(sb.length() == 0 ? "userId=" : "; userId=");
            sb.append(cookies.get("userId"));
        }
        if (cookies != null && (!cookies.isEmpty())) {
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                String key = entry.getKey();
                if (!Intrinsics.areEqual("cUserId", key) && !Intrinsics.areEqual("userId", key) && !Intrinsics.areEqual("serviceToken", key) && !TextUtils.isEmpty(key)) {
                    sb.append(sb.length() == 0 ? "" : "; ");
                    sb.append(key).append("=");
                    sb.append(entry.getValue());
                }
            }
        }
        String string = sb.toString();
        INSTANCE.logVerbose("cookie:" + string);
        return new KVPair<>("Cookie", string);
    }

    public final HashMap<String, String> encodeParameters(CryptCoder coder, String nonce, HashMap<String, String> params) throws CipherException {
        HashMap<String, String> map;
        logDebug("coder " + coder);
        HashMap<String, String> map2 = params;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        if (nonce != null && !map2.containsKey("_nonce")) {
            map = new HashMap<>(map2.size() + 1);
            map.put("_nonce", nonce);
        } else {
            map = new HashMap<>(map2.size());
        }
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            HashMap<String, String> map3 = map;
            if (!StringsKt.startsWith$default(key, "_", false, 2, (Object) null) && coder != null) {
                value = coder.encrypt(value);
            }
            map3.put(key, value);
        }
        return map;
    }

    public static /* synthetic */ HashMap insertHeadConfig2Headers$default(HttpUtils httpUtils, HashMap map, KVPair kVPair, String str, int i, boolean z, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = 0;
        }
        if ((i2 & 16) != 0) {
            z = true;
        }
        if ((i2 & 32) != 0) {
            z2 = true;
        }
        if ((i2 & 64) != 0) {
            z3 = false;
        }
        return httpUtils.insertHeadConfig2Headers(map, kVPair, str, i, z, z2, z3);
    }

    public final HashMap<String, String> insertHeadConfig2Headers(HashMap<String, String> header, KVPair<String, String> cookie, String contentType, int redirectCount, boolean gzip, boolean json, boolean sse) {
        Intrinsics.checkNotNullParameter(header, "header");
        logDebug("insert common header with config");
        if (json) {
            header.put("Accept", "application/json ");
        }
        if (sse) {
            header.put("Content-Type", "text/event-stream; charset=utf-8");
        } else if (contentType != null) {
            header.put("Content-Type", contentType);
        }
        if (gzip) {
            header.put("Accept-Encoding", "gzip");
        }
        if (redirectCount > 0) {
            header.put(MiCloudConstants.REQUEST.X_XIAOMI_REDIRECT_COUNT, String.valueOf(redirectCount));
        }
        if (!header.containsKey("Accept-Language")) {
            header.put("Accept-Language", Locale.getDefault().toString());
        }
        if (cookie != null) {
            header.put(cookie.getKey(), cookie.getValue());
        }
        header.put(MiCloudConstants.REQUEST.X_XIAOMI_SUPPORT_REDIRECT, "true, https");
        return header;
    }

    public final boolean isV4Url(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        return StringsKt.contains$default((CharSequence) url, (CharSequence) "/v4", false, 2, (Object) null);
    }

    public final CryptCoder getCryptCoder(String url, String security) {
        CryptCoder aESCoder;
        Intrinsics.checkNotNullParameter(url, "url");
        if (isV4Url(url)) {
            Intrinsics.checkNotNull(security);
            aESCoder = new V4AESWithIVDecoder(security);
        } else {
            Intrinsics.checkNotNull(security);
            aESCoder = new AESCoder(security);
        }
        return aESCoder;
    }
}
