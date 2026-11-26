package com.miui.aiengine.common.cloud;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.ExtendedAuthToken;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.SUCCESS;
import com.miui.aiengine.common.cipher.CipherException;
import com.miui.aiengine.common.cipher.CryptCoder;
import com.miui.aiengine.common.extension.ExtensionKt;
import com.miui.aiengine.common.interceptor.Interceptor;
import com.miui.aiengine.common.interceptor.RealInterceptorChain;
import com.miui.aiengine.common.request.HttpBodyEntity;
import com.miui.aiengine.common.request.HttpBodyEntityType;
import com.miui.aiengine.common.request.HttpBodyEntityTypeKt;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.aiengine.common.request.HttpRequest;
import com.miui.aiengine.common.utils.HttpUtils;
import com.miui.aiengine.common.utils.Logger;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ah;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.lingala.zip4j.util.InternalZipConstants;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: RequestHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006JÐ\u0001\u0010\u0016\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\"\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u001cj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006`\u001d2&\u0010\u001e\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001cj\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u001d2\"\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u001cj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006`\u001d2&\u0010 \u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001cj\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u001d2\u0014\u0010!\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r\u0018\u00010\"J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0012H\u0002J2\u0010&\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006H\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/miui/aiengine/common/cloud/RequestHelper;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "taskList", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lokhttp3/Call;", "interceptorsOfClient", "", "Lcom/miui/aiengine/common/interceptor/Interceptor;", "interceptorsOfRequest", "addInterceptor", "", "interceptor", "startChain", "Lcom/miui/aiengine/common/Result;", "originRequest", "Lcom/miui/aiengine/common/request/HttpRequest;", "cancelTask", "", "url", "doRequest", XMLWriter.METHOD, "Lcom/miui/aiengine/common/request/HttpMethod;", "authToken", "Lcom/miui/aiengine/common/ExtendedAuthToken;", "additionalHeaders", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "cookies", "encodedParams", "multipartMedias", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "executeInternal", "Lokhttp3/Response;", StatConstants.Event.REQUEST, "statGetDownloadFileUrlsFailedEvent", "httpMethod", "httpStatusCode", "", "exceptionName", ah.H, "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RequestHelper extends Logger {
    private static final List<Interceptor> interceptorsOfRequest;
    public static final RequestHelper INSTANCE = new RequestHelper();
    private static final ConcurrentHashMap<String, Call> taskList = new ConcurrentHashMap<>();
    private static final List<Interceptor> interceptorsOfClient = new ArrayList();

    private RequestHelper() {
        super(null, 1, null);
    }

    static {
        ArrayList arrayList = new ArrayList();
        interceptorsOfRequest = arrayList;
        arrayList.add(new Interceptor() { // from class: com.miui.aiengine.common.cloud.RequestHelper.1
            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public String getName() {
                return "okio interceptor";
            }

            @Override // com.miui.aiengine.common.interceptor.Interceptor
            public Result intercept(Interceptor.Chain chain) throws BadPaddingException, IllegalBlockSizeException, IOException, CipherException, CloudServerException {
                InputStream inputStreamByteStream;
                GZIPInputStream gZIPInputStreamByteStream;
                CryptCoder cryptCoder;
                String strInputStreamToString;
                Intrinsics.checkNotNullParameter(chain, "chain");
                RequestHelper.INSTANCE.logDebug("okio interceptor ,intercept..");
                HttpRequest request = chain.getRequest();
                Response responseExecuteInternal = RequestHelper.INSTANCE.executeInternal(request);
                int iCode = responseExecuteInternal.code();
                String strInputStreamToString2 = null;
                if (iCode == 200) {
                    RequestHelper.INSTANCE.logDebug("okio interceptor ,response state code 200..");
                    String str = responseExecuteInternal.headers().get("Content-Encoding");
                    String str2 = responseExecuteInternal.headers().get(HttpBodyEntityTypeKt.CONTENT_TYPE);
                    RequestHelper.INSTANCE.logError("encrypt interceptor ,response/content-encode: " + str + ",content-type: " + str2);
                    if (str == null || !StringsKt.equals(str, "gzip", true)) {
                        ResponseBody responseBodyBody = responseExecuteInternal.body();
                        Intrinsics.checkNotNull(responseBodyBody);
                        gZIPInputStreamByteStream = responseBodyBody.byteStream();
                    } else {
                        ResponseBody responseBodyBody2 = responseExecuteInternal.body();
                        Intrinsics.checkNotNull(responseBodyBody2);
                        gZIPInputStreamByteStream = new GZIPInputStream(responseBodyBody2.byteStream());
                    }
                    if (request.getAuthToken() != null) {
                        HttpUtils httpUtils = HttpUtils.INSTANCE;
                        String url = request.getUrl();
                        ExtendedAuthToken authToken = request.getAuthToken();
                        Intrinsics.checkNotNull(authToken);
                        cryptCoder = httpUtils.getCryptCoder(url, authToken.getSecurity());
                    } else {
                        cryptCoder = null;
                    }
                    if (StringsKt.equals(str2, SDKConstants.MimeType.EVENT_STREAM, true)) {
                        ExtensionKt.checkNotNull(request.getSseCallback(), "callback should be not null when SSE enable");
                        if (NetConfig.INSTANCE.getSSEProtocolPrivate()) {
                            Function1<Result, Unit> sseCallback = request.getSseCallback();
                            Intrinsics.checkNotNull(sseCallback);
                            ExtensionKt.handleSSEInput(gZIPInputStreamByteStream, cryptCoder, sseCallback);
                        } else {
                            ResponseBody responseBodyBody3 = responseExecuteInternal.body();
                            Intrinsics.checkNotNull(responseBodyBody3);
                            BufferedSource bodySource = responseBodyBody3.getBodySource();
                            Function1<Result, Unit> sseCallback2 = request.getSseCallback();
                            Intrinsics.checkNotNull(sseCallback2);
                            ExtensionKt.handleSSEInput(bodySource, cryptCoder, sseCallback2);
                        }
                        return null;
                    }
                    if (cryptCoder != null && (strInputStreamToString = cryptCoder.decrypt(ExtensionKt.inputStreamToString(gZIPInputStreamByteStream))) != null) {
                        RequestHelper.INSTANCE.logInfo("encrypt interceptor ,result:" + strInputStreamToString);
                    } else {
                        strInputStreamToString = ExtensionKt.inputStreamToString(gZIPInputStreamByteStream);
                        RequestHelper.INSTANCE.logInfo("encrypt interceptor ,result:" + strInputStreamToString);
                    }
                    SUCCESS success = new SUCCESS(strInputStreamToString, null, 2, null);
                    RequestHelper.INSTANCE.logInfo("encrypt interceptor ,return result " + success);
                    return success;
                }
                if (CloudServerException.INSTANCE.isCloudServerException(iCode)) {
                    RequestHelper requestHelper = RequestHelper.INSTANCE;
                    int iCode2 = responseExecuteInternal.code();
                    ResponseBody responseBodyBody4 = responseExecuteInternal.body();
                    if (responseBodyBody4 != null && (inputStreamByteStream = responseBodyBody4.byteStream()) != null) {
                        strInputStreamToString2 = ExtensionKt.inputStreamToString(inputStreamByteStream);
                    }
                    requestHelper.logDebug("CloudServerException: " + iCode + StringUtils.SPACE + iCode2 + "," + strInputStreamToString2);
                    throw new CloudServerException(iCode, responseExecuteInternal.message());
                }
                RequestHelper.INSTANCE.logError("Server error: " + iCode + StringUtils.SPACE + responseExecuteInternal.code());
                throw new IOException("Server error: " + iCode + StringUtils.SPACE + responseExecuteInternal.code() + ",msg: " + responseExecuteInternal.message());
            }
        });
    }

    public final void addInterceptor(Interceptor interceptor) {
        Intrinsics.checkNotNullParameter(interceptor, "interceptor");
        interceptorsOfClient.add(interceptor);
    }

    private final Result startChain(HttpRequest originRequest) throws IllegalStateException {
        Result resultProceed = new RealInterceptorChain(CollectionsKt.plus((Collection) interceptorsOfClient, (Iterable) interceptorsOfRequest), originRequest, 0).proceed(originRequest);
        if (resultProceed == null) {
            return null;
        }
        INSTANCE.logInfo("first chain  result " + resultProceed);
        return resultProceed;
    }

    public final boolean cancelTask(String url) {
        if (url == null) {
            Collection<Call> collectionValues = taskList.values();
            Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
            Iterator<T> it = collectionValues.iterator();
            while (it.hasNext()) {
                ((Call) it.next()).cancel();
            }
            taskList.clear();
            return true;
        }
        Call callRemove = taskList.remove(url);
        if (callRemove != null) {
            callRemove.cancel();
        } else {
            callRemove = null;
        }
        return callRemove != null;
    }

    public final Result doRequest(HttpMethod method, String url, ExtendedAuthToken authToken, HashMap<String, String> additionalHeaders, HashMap<String, String> cookies, HashMap<String, String> encodedParams, HashMap<String, String> multipartMedias, Function1<? super Result, Unit> callback) throws IOException, CloudServerException {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(additionalHeaders, "additionalHeaders");
        Intrinsics.checkNotNullParameter(encodedParams, "encodedParams");
        logDebug("doRequest(method: " + method + InternalZipConstants.ZIP_FILE_SEPARATOR + url + "/cookie=,additionHeaders: " + additionalHeaders + ",params:" + encodedParams + ",medias:" + multipartMedias + ",coder:" + authToken + ",sseCallback:" + callback);
        HttpBodyEntityType httpBodyEntityTypeString2ContentType = null;
        if (Intrinsics.areEqual(method, HttpMethod.POST.INSTANCE)) {
            HashMap<String, String> map = multipartMedias;
            if (map != null && !map.isEmpty()) {
                httpBodyEntityTypeString2ContentType = HttpBodyEntityType.FORM;
            } else if (additionalHeaders.containsKey(HttpBodyEntityTypeKt.CONTENT_TYPE)) {
                String str = additionalHeaders.get(HttpBodyEntityTypeKt.CONTENT_TYPE);
                if (str != null) {
                    httpBodyEntityTypeString2ContentType = HttpBodyEntityTypeKt.string2ContentType(str);
                }
            } else {
                httpBodyEntityTypeString2ContentType = HttpBodyEntityType.FORM_URLENCODED;
            }
        }
        HttpRequest httpRequestBuild = new HttpRequest.Builder().setHttpMethod(method).setUrl(url).setHeader(additionalHeaders, cookies).setBodyEntity(new HttpBodyEntity(httpBodyEntityTypeString2ContentType, encodedParams)).setSSEResponseMode(callback).setEncrypt(authToken).setMultiPartMedias(multipartMedias).build();
        logInfo("build request:" + httpRequestBuild);
        return startChain(httpRequestBuild);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:50:0x0192 A[Catch: IOException -> 0x01fb, TryCatch #0 {IOException -> 0x01fb, blocks: (B:3:0x0006, B:6:0x0025, B:8:0x002b, B:9:0x0031, B:11:0x0037, B:12:0x0053, B:14:0x005f, B:16:0x0077, B:18:0x0089, B:20:0x008f, B:21:0x0095, B:23:0x009b, B:24:0x00b7, B:52:0x0199, B:54:0x01ca, B:56:0x01d1, B:25:0x00c2, B:27:0x00d4, B:29:0x00da, B:30:0x00e0, B:32:0x00e6, B:33:0x0102, B:35:0x0108, B:37:0x010e, B:38:0x0114, B:40:0x011a, B:41:0x014e, B:42:0x015d, B:44:0x0163, B:46:0x0172, B:49:0x0179, B:51:0x0196, B:50:0x0192), top: B:61:0x0006 }] */
    public final Response executeInternal(HttpRequest request) throws BadPaddingException, IllegalBlockSizeException, IOException {
        String url;
        Set<Map.Entry<String, String>> setEntrySet;
        Set<Map.Entry<String, String>> setEntrySet2;
        Set<Map.Entry<String, String>> setEntrySet3;
        Set<Map.Entry<String, String>> setEntrySet4;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            logInfo("executeInternal with request:" + request);
            Request.Builder builder = new Request.Builder();
            HashMap<String, String> header$net_release = request.getHeader$net_release();
            if (header$net_release != null && (setEntrySet4 = header$net_release.entrySet()) != null) {
                Iterator<T> it = setEntrySet4.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
                    Object value = entry.getValue();
                    Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
                    builder.addHeader((String) key, (String) value);
                }
            }
            if (Intrinsics.areEqual(request.getHttpMethod(), HttpMethod.POST.INSTANCE)) {
                builder.url(request.getUrl());
                HttpBodyEntity httpBodyEntity = request.getHttpBodyEntity();
                Intrinsics.checkNotNull(httpBodyEntity);
                int i = 1;
                Charset charset = null;
                byte b = 0;
                byte b2 = 0;
                byte b3 = 0;
                if (httpBodyEntity.getType() != HttpBodyEntityType.FORM) {
                    FormBody.Builder builder2 = new FormBody.Builder(charset, i, b3 == true ? 1 : 0);
                    HttpBodyEntity httpBodyEntity2 = request.getHttpBodyEntity();
                    Intrinsics.checkNotNull(httpBodyEntity2);
                    HashMap<String, String> elements = httpBodyEntity2.getElements();
                    if (elements != null && (setEntrySet3 = elements.entrySet()) != null) {
                        Iterator<T> it2 = setEntrySet3.iterator();
                        while (it2.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it2.next();
                            Object key2 = entry2.getKey();
                            Intrinsics.checkNotNullExpressionValue(key2, "<get-key>(...)");
                            Object value2 = entry2.getValue();
                            Intrinsics.checkNotNullExpressionValue(value2, "<get-value>(...)");
                            builder2.add((String) key2, (String) value2);
                        }
                    }
                    builder.post(builder2.build());
                } else {
                    MultipartBody.Builder builder3 = new MultipartBody.Builder(b2 == true ? 1 : 0, i, b == true ? 1 : 0);
                    HttpBodyEntity httpBodyEntity3 = request.getHttpBodyEntity();
                    Intrinsics.checkNotNull(httpBodyEntity3);
                    HashMap<String, String> elements2 = httpBodyEntity3.getElements();
                    if (elements2 != null && (setEntrySet2 = elements2.entrySet()) != null) {
                        Iterator<T> it3 = setEntrySet2.iterator();
                        while (it3.hasNext()) {
                            Map.Entry entry3 = (Map.Entry) it3.next();
                            Object key3 = entry3.getKey();
                            Intrinsics.checkNotNullExpressionValue(key3, "<get-key>(...)");
                            Object value3 = entry3.getValue();
                            Intrinsics.checkNotNullExpressionValue(value3, "<get-value>(...)");
                            builder3.addFormDataPart((String) key3, (String) value3);
                        }
                    }
                    HashMap<String, String> multiPartMedias$net_release = request.getMultiPartMedias$net_release();
                    if (multiPartMedias$net_release != null && (setEntrySet = multiPartMedias$net_release.entrySet()) != null) {
                        Iterator<T> it4 = setEntrySet.iterator();
                        while (it4.hasNext()) {
                            Map.Entry entry4 = (Map.Entry) it4.next();
                            File file = new File((String) entry4.getValue());
                            Object key4 = entry4.getKey();
                            Intrinsics.checkNotNullExpressionValue(key4, "<get-key>(...)");
                            builder3.addFormDataPart((String) key4, file.getName(), RequestBody.INSTANCE.create(file, MediaType.INSTANCE.get(HttpBodyEntityTypeKt.toHeaderString(HttpBodyEntityType.BINARY))));
                        }
                    }
                    builder3.setType(MultipartBody.FORM);
                    builder.post(builder3.build());
                }
            } else {
                if (request.getHttpBodyEntity() != null) {
                    HttpBodyEntity httpBodyEntity4 = request.getHttpBodyEntity();
                    Intrinsics.checkNotNull(httpBodyEntity4);
                    HashMap<String, String> elements3 = httpBodyEntity4.getElements();
                    if (elements3 == null || elements3.isEmpty()) {
                        url = request.getUrl();
                    } else {
                        HttpUtils httpUtils = HttpUtils.INSTANCE;
                        String url2 = request.getUrl();
                        HttpBodyEntity httpBodyEntity5 = request.getHttpBodyEntity();
                        Intrinsics.checkNotNull(httpBodyEntity5);
                        HashMap<String, String> elements4 = httpBodyEntity5.getElements();
                        Intrinsics.checkNotNull(elements4);
                        url = httpUtils.appendUrl(url2, elements4);
                    }
                } else {
                    url = request.getUrl();
                }
                builder.url(url);
            }
            Call callNewCall = HttpUtils.INSTANCE.getHttpClient().newCall(builder.build());
            ConcurrentHashMap<String, Call> concurrentHashMap = taskList;
            concurrentHashMap.put(request.getUrl(), callNewCall);
            Response responseExecute = callNewCall.execute();
            concurrentHashMap.remove(request.getUrl());
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            ResponseBody responseBodyBody = responseExecute.body();
            long contentLength = responseBodyBody != null ? responseBodyBody.getContentLength() : 0L;
            int iCode = responseExecute.code();
            CloudStatManager.INSTANCE.addHttpEvent(request.getUrl(), jCurrentTimeMillis2, contentLength, iCode, null);
            NetEventStatInjector.INSTANCE.getInstance().addNetSuccessEvent(new NetSuccessStatParam(request.getUrl(), jCurrentTimeMillis, jCurrentTimeMillis2, contentLength, iCode, 0));
            return responseExecute;
        } catch (IOException e) {
            CloudStatManager.INSTANCE.addHttpEvent(request.getUrl(), -1L, 0L, -1, e.getClass().getSimpleName());
            NetEventStatInjector.INSTANCE.getInstance().addNetFailedEvent(new NetFailedStatParam(request.getUrl(), jCurrentTimeMillis, System.currentTimeMillis() - jCurrentTimeMillis, e, 0));
            String url3 = request.getUrl();
            String name = request.getHttpMethod().getName();
            String simpleName = e.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
            statGetDownloadFileUrlsFailedEvent(url3, name, -1, simpleName, "");
            throw e;
        }
    }

    private final void statGetDownloadFileUrlsFailedEvent(String url, String httpMethod, int httpStatusCode, String exceptionName, String response) {
        if (NetEventStatInjector.INSTANCE.getInstance().isGetDownloadFileRequestUrl(url, httpMethod)) {
            NetEventStatInjector companion = NetEventStatInjector.INSTANCE.getInstance();
            GetDownloadFileUrlFailedStatParam getDownloadFileUrlFailedStatParamCreateGetDownloadFileUrlFailedStatParam = GetDownloadFileUrlFailedStatParam.createGetDownloadFileUrlFailedStatParam(url, httpStatusCode, exceptionName, response);
            Intrinsics.checkNotNullExpressionValue(getDownloadFileUrlFailedStatParamCreateGetDownloadFileUrlFailedStatParam, "createGetDownloadFileUrlFailedStatParam(...)");
            companion.addGetDownloadFileUrlsFailedEvent(getDownloadFileUrlFailedStatParamCreateGetDownloadFileUrlFailedStatParam);
        }
    }
}
