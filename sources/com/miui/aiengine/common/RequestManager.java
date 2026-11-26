package com.miui.aiengine.common;

import android.app.Application;
import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.cloud.CloudServerException;
import com.miui.aiengine.common.extension.ExtensionKt;
import com.miui.aiengine.common.request.HttpBodyEntity;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.aiengine.common.request.HttpRequest;
import com.miui.aiengine.common.request.Request;
import com.miui.aiengine.common.utils.Logger;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import java.io.IOException;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RequestManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\nJ\u0012\u0010\u000b\u001a\u00020\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/aiengine/common/RequestManager;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ctx", "Landroid/app/Application;", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "getContext", "Landroid/content/Context;", "cancelTask", "url", "", "execute", "Lcom/miui/aiengine/common/Result;", StatConstants.Event.REQUEST, "Lcom/miui/aiengine/common/request/HttpRequest;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RequestManager extends Logger {
    public static final RequestManager INSTANCE = new RequestManager();
    private static Application ctx;

    private RequestManager() {
        super(NetConfig.TAG);
    }

    public final void init(Application context) {
        Intrinsics.checkNotNullParameter(context, "context");
        logDebug("init with app " + context + "},staging:" + XMPassport.USE_PREVIEW);
        ctx = context;
    }

    public final Context getContext() throws NullPointerException {
        ExtensionKt.checkNotNull(ctx, "should init context when application on create!!");
        Application application = ctx;
        Intrinsics.checkNotNull(application);
        Context applicationContext = application.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return applicationContext;
    }

    public static /* synthetic */ void cancelTask$default(RequestManager requestManager, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        requestManager.cancelTask(str);
    }

    public final void cancelTask(String url) {
        Request.INSTANCE.cancelTask(url);
    }

    public final Result execute(HttpRequest request) throws BadPaddingException, IllegalBlockSizeException, IOException, CloudServerException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(request, "request");
        Request request2 = Request.INSTANCE;
        String url = request.getUrl();
        HttpMethod httpMethod$net_release = request.getHttpMethod();
        HashMap<String, String> header$net_release = request.getHeader$net_release();
        HttpBodyEntity httpBodyEntity$net_release = request.getHttpBodyEntity();
        return request2.execute(url, httpMethod$net_release, header$net_release, httpBodyEntity$net_release != null ? httpBodyEntity$net_release.getElements() : null, request.getCookie$net_release(), request.getMultiPartMedias$net_release(), request.getAuthToken(), request.getSseCallback());
    }
}
