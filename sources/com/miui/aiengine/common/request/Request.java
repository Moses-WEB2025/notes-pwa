package com.miui.aiengine.common.request;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.ExtendedAuthToken;
import com.miui.aiengine.common.RequestManager;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.cipher.CipherException;
import com.miui.aiengine.common.cipher.CryptCoder;
import com.miui.aiengine.common.cloud.CloudCoder;
import com.miui.aiengine.common.cloud.CloudServerException;
import com.miui.aiengine.common.cloud.RequestHelper;
import com.miui.aiengine.common.interceptor.Interceptor;
import com.miui.aiengine.common.utils.HttpUtils;
import com.miui.aiengine.common.utils.Logger;
import com.miui.aiengine.common.utils.MiAccountManager;
import com.miui.aiengine.common.utils.OsUtils;
import com.miui.aiengine.common.utils.PrivacyLogFilter;
import com.miui.aiengine.common.utils.SyncRequestParam;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: Request.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bJÚ\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u00122&\u0010\u0013\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00152&\u0010\u0016\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00152&\u0010\u0017\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00152&\u0010\u0018\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001d"}, d2 = {"Lcom/miui/aiengine/common/request/Request;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PARAM_SYNC_SIGNATURE", "", "cancelTask", "", "url", "addInterceptor", "interceptor", "Lcom/miui/aiengine/common/interceptor/Interceptor;", "userAgent", "getUserAgent", "()Ljava/lang/String;", "execute", "Lcom/miui/aiengine/common/Result;", XMLWriter.METHOD, "Lcom/miui/aiengine/common/request/HttpMethod;", "header", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "paras", "cookies", "multiPartMedias", "auth", "Lcom/miui/aiengine/common/ExtendedAuthToken;", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Request extends Logger {
    public static final Request INSTANCE;
    private static final String PARAM_SYNC_SIGNATURE = "signature";
    private static final String userAgent;

    private Request() {
        super(null, 1, null);
    }

    public final void cancelTask(String url) {
        RequestHelper.INSTANCE.cancelTask(url);
    }

    static {
        Request request = new Request();
        INSTANCE = request;
        request.addInterceptor(new Interceptor() { // from class: com.miui.aiengine.common.request.Request.1
            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public String getName() {
                return "header interceptor";
            }

            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public Result intercept(Interceptor.Chain chain) {
                KVPair<String, String> cookie;
                Intrinsics.checkNotNullParameter(chain, "chain");
                HttpRequest httpRequestRequest = chain.getRequest();
                Request.INSTANCE.logDebug("header interceptor ,insert common info to header " + httpRequestRequest);
                HttpUtils httpUtils = HttpUtils.INSTANCE;
                HashMap<String, String> header$net_release = httpRequestRequest.getHeader$net_release();
                Intrinsics.checkNotNull(header$net_release);
                HttpBodyEntity httpBodyEntity = httpRequestRequest.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity);
                HashMap<String, String> elements = httpBodyEntity.getElements();
                Intrinsics.checkNotNull(elements);
                httpUtils.insertBasePackageInfo(header$net_release, elements);
                SyncRequestParam syncRequestParam = SyncRequestParam.INSTANCE;
                HashMap<String, String> header$net_release2 = httpRequestRequest.getHeader$net_release();
                Intrinsics.checkNotNull(header$net_release2);
                HttpBodyEntity httpBodyEntity2 = httpRequestRequest.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity2);
                HashMap<String, String> elements2 = httpBodyEntity2.getElements();
                Intrinsics.checkNotNull(elements2);
                syncRequestParam.addParam(header$net_release2, elements2);
                if (httpRequestRequest.getAuthToken() == null) {
                    cookie = null;
                } else {
                    HttpUtils httpUtils2 = HttpUtils.INSTANCE;
                    String encryptUserId = MiAccountManager.INSTANCE.getEncryptUserId(RequestManager.INSTANCE.getContext());
                    ExtendedAuthToken authToken = httpRequestRequest.getAuthToken();
                    Intrinsics.checkNotNull(authToken);
                    cookie = httpUtils2.getCookie(encryptUserId, authToken.getAuthToken(), httpRequestRequest.getCookie$net_release());
                }
                HttpUtils httpUtils3 = HttpUtils.INSTANCE;
                HashMap<String, String> header$net_release3 = httpRequestRequest.getHeader$net_release();
                Intrinsics.checkNotNull(header$net_release3);
                HttpUtils.insertHeadConfig2Headers$default(httpUtils3, header$net_release3, cookie, null, 0, true, false, false, 104, null);
                Result resultProceed = chain.proceed(httpRequestRequest);
                Request.INSTANCE.logDebug("header interceptor ,do nothing for  response " + resultProceed);
                return resultProceed;
            }
        });
        request.addInterceptor(new Interceptor() { // from class: com.miui.aiengine.common.request.Request.2
            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public String getName() {
                return "encrypt interceptor";
            }

            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public Result intercept(Interceptor.Chain chain) throws CipherException, UnsupportedEncodingException {
                Intrinsics.checkNotNullParameter(chain, "chain");
                HttpRequest httpRequestRequest = chain.getRequest();
                Request.INSTANCE.logDebug("encrypt interceptor ,handle request " + httpRequestRequest);
                CryptCoder cryptCoder = null;
                String strGenerateNonce = HttpUtils.INSTANCE.isV4Url(httpRequestRequest.getUrl()) ? CloudCoder.INSTANCE.generateNonce() : null;
                if (httpRequestRequest.getAuthToken() != null) {
                    HttpUtils httpUtils = HttpUtils.INSTANCE;
                    String url = httpRequestRequest.getUrl();
                    ExtendedAuthToken authToken = httpRequestRequest.getAuthToken();
                    Intrinsics.checkNotNull(authToken);
                    cryptCoder = httpUtils.getCryptCoder(url, authToken.getSecurity());
                }
                HttpUtils httpUtils2 = HttpUtils.INSTANCE;
                HttpBodyEntity httpBodyEntity = httpRequestRequest.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity);
                HashMap<String, String> mapEncodeParameters = httpUtils2.encodeParameters(cryptCoder, strGenerateNonce, httpBodyEntity.getElements());
                if (cryptCoder != null) {
                    if (mapEncodeParameters.containsKey("signature")) {
                        Request.INSTANCE.logError("warning!!encode params container invalid params!!");
                        mapEncodeParameters.remove("signature");
                    }
                    HttpUtils httpUtils3 = HttpUtils.INSTANCE;
                    HttpMethod httpMethod = httpRequestRequest.getHttpMethod();
                    String url2 = httpRequestRequest.getUrl();
                    ExtendedAuthToken authToken2 = httpRequestRequest.getAuthToken();
                    Intrinsics.checkNotNull(authToken2);
                    String signature = httpUtils3.getSignature(httpMethod, url2, mapEncodeParameters, authToken2.getSecurity());
                    Intrinsics.checkNotNull(signature);
                    mapEncodeParameters.put("signature", signature);
                }
                HttpBodyEntity httpBodyEntity2 = httpRequestRequest.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity2);
                HashMap<String, String> elements = httpBodyEntity2.getElements();
                Intrinsics.checkNotNull(elements);
                elements.clear();
                HttpBodyEntity httpBodyEntity3 = httpRequestRequest.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity3);
                HashMap<String, String> elements2 = httpBodyEntity3.getElements();
                Intrinsics.checkNotNull(elements2);
                elements2.putAll(mapEncodeParameters);
                Result resultProceed = chain.proceed(httpRequestRequest);
                Request.INSTANCE.logDebug("encrypt interceptor ,handle response do nothing...");
                return resultProceed;
            }
        });
        userAgent = OsUtils.INSTANCE.getUA();
    }

    public final void addInterceptor(Interceptor interceptor) {
        Intrinsics.checkNotNullParameter(interceptor, "interceptor");
        RequestHelper.INSTANCE.addInterceptor(interceptor);
    }

    public final String getUserAgent() {
        return userAgent;
    }

    public final Result execute(String url, HttpMethod method, HashMap<String, String> header, HashMap<String, String> paras, HashMap<String, String> cookies, HashMap<String, String> multiPartMedias, ExtendedAuthToken auth, Function1<? super Result, Unit> callback) throws BadPaddingException, IllegalBlockSizeException, IOException, CloudServerException, IllegalArgumentException {
        HashMap<String, String> map;
        HashMap<String, String> map2;
        String str;
        Intrinsics.checkNotNullParameter(method, "method");
        HashMap<String, String> map3 = null;
        logDebug("execute(url:" + url + ",method:" + method + ",headers:" + header + ",params:" + paras + ",authToken:" + (auth != null ? auth.toPlain() : null) + " cookie " + cookies);
        HashMap<String, String> map4 = multiPartMedias;
        if (map4 != null && !map4.isEmpty() && Intrinsics.areEqual(method, HttpMethod.GET.INSTANCE)) {
            throw new IllegalArgumentException("multipartMedia only suit for POST method");
        }
        if (map4 != null && !map4.isEmpty() && (map2 = header) != null && !map2.isEmpty() && (str = header.get(HttpBodyEntityTypeKt.CONTENT_TYPE)) != null && !str.equals(HttpBodyEntityTypeKt.toHeaderString(HttpBodyEntityType.FORM))) {
            throw new IllegalArgumentException("multipartMedia not support for " + ((Object) header.get(HttpBodyEntityTypeKt.CONTENT_TYPE)));
        }
        HashMap<String, String> map5 = new HashMap<>();
        HashMap<String, String> map6 = new HashMap<>();
        if (header != null && (!header.isEmpty())) {
            map5.putAll(header);
        }
        if (paras != null && (!paras.isEmpty())) {
            map6.putAll(paras);
        }
        if (map4 == null || map4.isEmpty()) {
            map = null;
        } else {
            HashMap<String, String> map7 = new HashMap<>();
            map7.putAll(map4);
            map = map7;
        }
        HashMap<String, String> map8 = cookies;
        if (map8 != null && !map8.isEmpty()) {
            map3 = new HashMap<>();
            map3.putAll(map8);
        }
        try {
            RequestHelper requestHelper = RequestHelper.INSTANCE;
            Intrinsics.checkNotNull(url);
            Result resultDoRequest = requestHelper.doRequest(method, url, auth, map5, map3, map6, map, callback);
            INSTANCE.logDebug("return " + resultDoRequest);
            return resultDoRequest;
        } catch (CipherException e) {
            PrivacyLogFilter privacyLogFilter = PrivacyLogFilter.INSTANCE;
            String stackTraceString = Log.getStackTraceString(e);
            Intrinsics.checkNotNullExpressionValue(stackTraceString, "getStackTraceString(...)");
            logError("CipherException: " + privacyLogFilter.filterPrivacyLog(stackTraceString));
            throw new CloudServerException(0, e.toString());
        } catch (CloudServerException e2) {
            PrivacyLogFilter privacyLogFilter2 = PrivacyLogFilter.INSTANCE;
            String stackTraceString2 = Log.getStackTraceString(e2);
            Intrinsics.checkNotNullExpressionValue(stackTraceString2, "getStackTraceString(...)");
            logError("CloudServer Exception: " + privacyLogFilter2.filterPrivacyLog(stackTraceString2));
            throw e2;
        } catch (IOException e3) {
            throw e3;
        }
    }
}
