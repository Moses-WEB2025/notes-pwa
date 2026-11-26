package com.miui.notes.ai.request;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.content.Context;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.Error;
import com.miui.aiengine.common.ExtendedAuthToken;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.cloud.CloudServerException;
import com.miui.aiengine.common.request.HttpBodyEntity;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.aiengine.common.request.HttpRequest;
import com.miui.aiengine.common.utils.MiAccountManager;
import com.miui.notes.ai.FinishException;
import com.miui.notes.base.utils.Logger;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: AiNoteRequest.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tJd\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00052\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00132\u0016\b\u0002\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00132\u0016\b\u0002\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/ai/request/AiNoteRequest;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "RETRY_COUNT", "", "app", "Landroid/content/Context;", "isInited", "", "init", "", "doRequest", "Lkotlinx/coroutines/flow/Flow;", "Lcom/miui/aiengine/common/Result;", "url", "params", "Ljava/util/HashMap;", "header", "sseCallback", "Lkotlin/Function1;", XMLWriter.METHOD, "Lcom/miui/aiengine/common/request/HttpMethod;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiNoteRequest {
    public static final AiNoteRequest INSTANCE = new AiNoteRequest();
    private static final int RETRY_COUNT = 3;
    public static final String TAG = "AiNoteRequest";
    private static Context app;
    private static boolean isInited;

    private AiNoteRequest() {
    }

    public final void init(Context app2) {
        Intrinsics.checkNotNullParameter(app2, "app");
        if (isInited) {
            return;
        }
        app = app2;
        isInited = true;
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.request.AiNoteRequest$doRequest$1, reason: invalid class name */
    /* JADX INFO: compiled from: AiNoteRequest.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/miui/aiengine/common/Result;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.request.AiNoteRequest$doRequest$1", f = "AiNoteRequest.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super Result>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HashMap<String, String> $header;
        final /* synthetic */ HttpMethod $method;
        final /* synthetic */ HashMap<String, String> $params;
        final /* synthetic */ Function1<Result, Unit> $sseCallback;
        final /* synthetic */ String $url;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(String str, HashMap<String, String> map, HttpMethod httpMethod, HashMap<String, String> map2, Function1<? super Result, Unit> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$url = str;
            this.$header = map;
            this.$method = httpMethod;
            this.$params = map2;
            this.$sseCallback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$url, this.$header, this.$method, this.$params, this.$sseCallback, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super Result> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Exception {
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Logger.INSTANCE.d("AiNoteRequest", "doRequest->url:" + this.$url + ",headers:" + this.$header + ",method:" + this.$method + ",app " + AiNoteRequest.app);
            int i = 0;
            do {
                try {
                    MiAccountManager miAccountManager = MiAccountManager.INSTANCE;
                    Context context = AiNoteRequest.app;
                    Intrinsics.checkNotNull(context);
                    String authToken$default = MiAccountManager.getAuthToken$default(miAccountManager, context, null, 2, null);
                    if (authToken$default == null) {
                        producerScope.mo4054trySendJP2dKIU(new Error(new AuthenticatorException("auth error")));
                        return Unit.INSTANCE;
                    }
                    HttpRequest httpRequestBuild = new HttpRequest.Builder().setUrl(this.$url).setHttpMethod(this.$method).setHeader(this.$header, null).setBodyEntity(new HttpBodyEntity(null, this.$params, 1, null)).setEncrypt(ExtendedAuthToken.INSTANCE.parse(authToken$default)).setSSEResponseMode(this.$sseCallback).build();
                    if (httpRequestBuild.getSSEMode()) {
                        final Function1<Result, Unit> function1 = this.$sseCallback;
                        httpRequestBuild.setSseCallback(new Function1() { // from class: com.miui.notes.ai.request.AiNoteRequest$doRequest$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return AiNoteRequest.AnonymousClass1.invokeSuspend$lambda$0(function1, producerScope, (Result) obj2);
                            }
                        });
                    }
                    Result resultExecute = httpRequestBuild.execute();
                    if (resultExecute != null) {
                        ChannelResult.m4069boximpl(producerScope.mo4054trySendJP2dKIU(resultExecute));
                    }
                    producerScope.mo4054trySendJP2dKIU(new Error(new FinishException("finish")));
                    SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                    return Unit.INSTANCE;
                } catch (CloudServerException e) {
                    if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                        e.printStackTrace();
                        i++;
                        Logger.INSTANCE.e("AiNoteRequest", "request failed with " + e + " invalid and retry " + i);
                        AccountManager.get(AiNoteRequest.app).invalidateAuthToken("com.xiaomi", null);
                    } else {
                        e.printStackTrace();
                        throw e;
                    }
                } catch (SocketTimeoutException e2) {
                    e2.printStackTrace();
                    if (i < 3) {
                        i++;
                        Logger.INSTANCE.e("AiNoteRequest", "request failed, invalid and retry " + i + " with exception " + e2);
                    } else {
                        e2.printStackTrace();
                        throw e2;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    Logger.INSTANCE.w("AiNoteRequest", "request failed," + e3.getMessage());
                    throw e3;
                }
            } while (i <= 3);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(Function1 function1, ProducerScope producerScope, Result result) {
            Logger.INSTANCE.d("AiNoteRequest", "on result   " + (function1 != null ? Integer.valueOf(function1.hashCode()) : null));
            Intrinsics.checkNotNull(function1);
            function1.invoke(result);
            producerScope.mo4054trySendJP2dKIU(result);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Flow doRequest$default(AiNoteRequest aiNoteRequest, String str, HashMap map, HashMap map2, Function1 function1, HttpMethod httpMethod, int i, Object obj) {
        if ((i & 4) != 0) {
            map2 = null;
        }
        if ((i & 8) != 0) {
            function1 = null;
        }
        if ((i & 16) != 0) {
            httpMethod = HttpMethod.POST.INSTANCE;
        }
        return aiNoteRequest.doRequest(str, map, map2, function1, httpMethod);
    }

    public final Flow<Result> doRequest(String url, HashMap<String, String> params, HashMap<String, String> header, Function1<? super Result, Unit> sseCallback, HttpMethod method) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        return FlowKt.callbackFlow(new AnonymousClass1(url, header, method, params, sseCallback, null));
    }
}
