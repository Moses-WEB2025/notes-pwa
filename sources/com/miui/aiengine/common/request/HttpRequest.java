package com.miui.aiengine.common.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.ExtendedAuthToken;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.RequestManager;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.utils.Logger;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HttpRequest.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u000256B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u00101\u001a\u000202J\b\u00103\u001a\u00020\u0005H\u0016J\b\u00104\u001a\u0004\u0018\u00010+R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR:\u0010\u0010\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0011j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR:\u0010\u001d\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0011j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0014\"\u0004\b\u001f\u0010\u0016R:\u0010 \u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0011j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0014\"\u0004\b\"\u0010\u0016R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R(\u0010)\u001a\u0010\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020,\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u00067"}, d2 = {"Lcom/miui/aiengine/common/request/HttpRequest;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "url", "", "getUrl", "()Ljava/lang/String;", "setUrl", "(Ljava/lang/String;)V", "httpMethod", "Lcom/miui/aiengine/common/request/HttpMethod;", "getHttpMethod$net_release", "()Lcom/miui/aiengine/common/request/HttpMethod;", "setHttpMethod$net_release", "(Lcom/miui/aiengine/common/request/HttpMethod;)V", "header", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getHeader$net_release", "()Ljava/util/HashMap;", "setHeader$net_release", "(Ljava/util/HashMap;)V", "httpBodyEntity", "Lcom/miui/aiengine/common/request/HttpBodyEntity;", "getHttpBodyEntity$net_release", "()Lcom/miui/aiengine/common/request/HttpBodyEntity;", "setHttpBodyEntity$net_release", "(Lcom/miui/aiengine/common/request/HttpBodyEntity;)V", "cookie", "getCookie$net_release", "setCookie$net_release", "multiPartMedias", "getMultiPartMedias$net_release", "setMultiPartMedias$net_release", "authToken", "Lcom/miui/aiengine/common/ExtendedAuthToken;", "getAuthToken$net_release", "()Lcom/miui/aiengine/common/ExtendedAuthToken;", "setAuthToken$net_release", "(Lcom/miui/aiengine/common/ExtendedAuthToken;)V", "sseCallback", "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "", "getSseCallback", "()Lkotlin/jvm/functions/Function1;", "setSseCallback", "(Lkotlin/jvm/functions/Function1;)V", "getSSEMode", "", "toString", "execute", "Builder", "IRequestBuilder", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HttpRequest extends Logger {
    private ExtendedAuthToken authToken;
    private HashMap<String, String> cookie;
    private HashMap<String, String> header;
    private HttpBodyEntity httpBodyEntity;
    private HttpMethod httpMethod;
    private HashMap<String, String> multiPartMedias;
    private Function1<? super Result, Unit> sseCallback;
    public String url;

    public /* synthetic */ HttpRequest(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private HttpRequest() {
        super(NetConfig.TAG);
        this.httpMethod = HttpMethod.GET.INSTANCE;
    }

    public final String getUrl() {
        String str = this.url;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("url");
        return null;
    }

    public final void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    /* JADX INFO: renamed from: getHttpMethod$net_release, reason: from getter */
    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final void setHttpMethod$net_release(HttpMethod httpMethod) {
        Intrinsics.checkNotNullParameter(httpMethod, "<set-?>");
        this.httpMethod = httpMethod;
    }

    public final HashMap<String, String> getHeader$net_release() {
        return this.header;
    }

    public final void setHeader$net_release(HashMap<String, String> map) {
        this.header = map;
    }

    /* JADX INFO: renamed from: getHttpBodyEntity$net_release, reason: from getter */
    public final HttpBodyEntity getHttpBodyEntity() {
        return this.httpBodyEntity;
    }

    public final void setHttpBodyEntity$net_release(HttpBodyEntity httpBodyEntity) {
        this.httpBodyEntity = httpBodyEntity;
    }

    public final HashMap<String, String> getCookie$net_release() {
        return this.cookie;
    }

    public final void setCookie$net_release(HashMap<String, String> map) {
        this.cookie = map;
    }

    public final HashMap<String, String> getMultiPartMedias$net_release() {
        return this.multiPartMedias;
    }

    public final void setMultiPartMedias$net_release(HashMap<String, String> map) {
        this.multiPartMedias = map;
    }

    /* JADX INFO: renamed from: getAuthToken$net_release, reason: from getter */
    public final ExtendedAuthToken getAuthToken() {
        return this.authToken;
    }

    public final void setAuthToken$net_release(ExtendedAuthToken extendedAuthToken) {
        this.authToken = extendedAuthToken;
    }

    public final Function1<Result, Unit> getSseCallback() {
        return this.sseCallback;
    }

    public final void setSseCallback(Function1<? super Result, Unit> function1) {
        this.sseCallback = function1;
    }

    public final boolean getSSEMode() {
        return this.sseCallback != null;
    }

    public String toString() {
        return "Request@" + hashCode() + " ,url:" + getUrl() + ", method:" + this.httpMethod + ",header:" + this.header + ",body:" + this.httpBodyEntity + ",medias:" + this.multiPartMedias + ",cookie:" + this.cookie + ",authToken:" + this.authToken + ",sseCallback:" + this.sseCallback;
    }

    public final Result execute() {
        logDebug("execute " + this);
        return RequestManager.INSTANCE.execute(this);
    }

    /* JADX INFO: compiled from: HttpRequest.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0016JX\u0010\u0010\u001a\u00020\u00012&\u0010\u0011\u001a\"\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u00010\u0012j\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u0001`\u00132&\u0010\u0014\u001a\"\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u00010\u0012j\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u0001`\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J0\u0010\u0018\u001a\u00020\u00012&\u0010\u0019\u001a\"\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u00010\u0012j\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u0001`\u0013H\u0016J\u001e\u0010\u001a\u001a\u00020\u00012\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001cH\u0016J\u0012\u0010\u001f\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0005H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006#"}, d2 = {"Lcom/miui/aiengine/common/request/HttpRequest$Builder;", "Lcom/miui/aiengine/common/request/HttpRequest$IRequestBuilder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", StatConstants.Event.REQUEST, "Lcom/miui/aiengine/common/request/HttpRequest;", "getRequest", "()Lcom/miui/aiengine/common/request/HttpRequest;", "request$delegate", "Lkotlin/Lazy;", "setUrl", "url", "", "setHttpMethod", "httpMethod", "Lcom/miui/aiengine/common/request/HttpMethod;", "setHeader", "header", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "cookie", "setBodyEntity", "entity", "Lcom/miui/aiengine/common/request/HttpBodyEntity;", "setMultiPartMedias", "medias", "setSSEResponseMode", "sseCallback", "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "", "setEncrypt", "authToken", "Lcom/miui/aiengine/common/ExtendedAuthToken;", "build", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder implements IRequestBuilder {

        /* JADX INFO: renamed from: request$delegate, reason: from kotlin metadata */
        private final Lazy request = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.request.HttpRequest$Builder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return HttpRequest.Builder.request_delegate$lambda$0();
            }
        });

        private final HttpRequest getRequest() {
            return (HttpRequest) this.request.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final HttpRequest request_delegate$lambda$0() {
            return new HttpRequest(null);
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setUrl(String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            getRequest().setUrl(url);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setHttpMethod(HttpMethod httpMethod) {
            Intrinsics.checkNotNullParameter(httpMethod, "httpMethod");
            getRequest().setHttpMethod$net_release(httpMethod);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setHeader(HashMap<String, String> header, HashMap<String, String> cookie) {
            getRequest().setHeader$net_release(header);
            getRequest().setCookie$net_release(cookie);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setBodyEntity(HttpBodyEntity entity) {
            Intrinsics.checkNotNullParameter(entity, "entity");
            getRequest().setHttpBodyEntity$net_release(entity);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setMultiPartMedias(HashMap<String, String> medias) {
            getRequest().setMultiPartMedias$net_release(medias);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setSSEResponseMode(Function1<? super Result, Unit> sseCallback) {
            getRequest().setSseCallback(sseCallback);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public IRequestBuilder setEncrypt(ExtendedAuthToken authToken) {
            getRequest().setAuthToken$net_release(authToken);
            return this;
        }

        @Override // com.miui.aiengine.common.request.HttpRequest.IRequestBuilder
        public HttpRequest build() {
            return getRequest();
        }
    }

    /* JADX INFO: compiled from: HttpRequest.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H&J\u0010\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H&JX\u0010\b\u001a\u00020\u00002&\u0010\t\u001a\"\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\nj\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u000b2&\u0010\f\u001a\"\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\nj\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u000bH&J\u0010\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fH&J0\u0010\u0010\u001a\u00020\u00002&\u0010\u0011\u001a\"\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\nj\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u000bH&J\u001e\u0010\u0012\u001a\u00020\u00002\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0014H&J\u0014\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019H&J\b\u0010\u001a\u001a\u00020\u001bH&¨\u0006\u001cÀ\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/request/HttpRequest$IRequestBuilder;", "", "setUrl", "url", "", "setHttpMethod", "httpMethod", "Lcom/miui/aiengine/common/request/HttpMethod;", "setHeader", "header", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "cookie", "setBodyEntity", "entity", "Lcom/miui/aiengine/common/request/HttpBodyEntity;", "setMultiPartMedias", "medias", "setSSEResponseMode", "sseCallback", "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "", "setEncrypt", "authToken", "Lcom/miui/aiengine/common/ExtendedAuthToken;", "build", "Lcom/miui/aiengine/common/request/HttpRequest;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IRequestBuilder {
        HttpRequest build();

        IRequestBuilder setBodyEntity(HttpBodyEntity entity);

        IRequestBuilder setEncrypt(ExtendedAuthToken authToken);

        IRequestBuilder setHeader(HashMap<String, String> header, HashMap<String, String> cookie);

        IRequestBuilder setHttpMethod(HttpMethod httpMethod);

        IRequestBuilder setMultiPartMedias(HashMap<String, String> medias);

        IRequestBuilder setSSEResponseMode(Function1<? super Result, Unit> sseCallback);

        IRequestBuilder setUrl(String url);

        /* JADX INFO: compiled from: HttpRequest.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        public static final class DefaultImpls {
        }

        static /* synthetic */ IRequestBuilder setEncrypt$default(IRequestBuilder iRequestBuilder, ExtendedAuthToken extendedAuthToken, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setEncrypt");
            }
            if ((i & 1) != 0) {
                extendedAuthToken = null;
            }
            return iRequestBuilder.setEncrypt(extendedAuthToken);
        }
    }
}
