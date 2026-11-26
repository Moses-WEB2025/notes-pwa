package com.miui.floatwindow.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.action.Action;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: AppInstallBroadcastReceiver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0006\u0010\u0013\u001a\u00020\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/floatwindow/frame/AppInstallBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "intentFilter", "Landroid/content/IntentFilter;", "getIntentFilter", "()Landroid/content/IntentFilter;", Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "mainScope", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "unregister", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AppInstallBroadcastReceiver extends BroadcastReceiver {
    public static final String PACKAGE_REMOVED_ACTION = "android.intent.action.PACKAGE_REMOVED";
    public static final String PACKAGE_REPLACED_ACTION = "android.intent.action.PACKAGE_REPLACED";
    private static final String TAG = "intentFilter.addAction(PACKAGE_REPLACED_ACTION)\n        intentFilter.addDataScheme(\"package\")";
    private final IntentFilter intentFilter;
    private final CoroutineScope mainScope;
    private final CoroutineScope scope;

    public AppInstallBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(PACKAGE_REMOVED_ACTION);
        intentFilter.addAction(PACKAGE_REPLACED_ACTION);
        intentFilter.addDataScheme("package");
        this.intentFilter = intentFilter;
        this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.mainScope = CoroutineScopeKt.MainScope();
    }

    public final IntentFilter getIntentFilter() {
        return this.intentFilter;
    }

    public final CoroutineScope getScope() {
        return this.scope;
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.frame.AppInstallBroadcastReceiver$onReceive$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppInstallBroadcastReceiver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.frame.AppInstallBroadcastReceiver$onReceive$1", f = "AppInstallBroadcastReceiver.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ Intent $intent;
        int label;
        final /* synthetic */ AppInstallBroadcastReceiver this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Intent intent, Context context, AppInstallBroadcastReceiver appInstallBroadcastReceiver, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$intent = intent;
            this.$context = context;
            this.this$0 = appInstallBroadcastReceiver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$intent, this.$context, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            String action = this.$intent.getAction();
            if ((Intrinsics.areEqual(action, AppInstallBroadcastReceiver.PACKAGE_REMOVED_ACTION) || Intrinsics.areEqual(action, AppInstallBroadcastReceiver.PACKAGE_REPLACED_ACTION)) && PreferenceUtils.getEnableFloatMode(this.$context)) {
                BuildersKt__Builders_commonKt.launch$default(this.this$0.mainScope, null, null, new C00481(null), 3, null);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.frame.AppInstallBroadcastReceiver$onReceive$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AppInstallBroadcastReceiver.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.frame.AppInstallBroadcastReceiver$onReceive$1$1", f = "AppInstallBroadcastReceiver.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00481 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;

            C00481(Continuation<? super C00481> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00481(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00481) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                FloatWindowManagerAgent.updateDockMode();
                return Unit.INSTANCE;
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Logger.INSTANCE.d("AppInstallBroadcastReceiver", intent.getAction() + StringUtils.SPACE);
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(intent, context, this, null), 3, null);
    }

    public final void unregister() {
        CoroutineScopeKt.cancel$default(this.mainScope, null, 1, null);
        CoroutineScopeKt.cancel$default(this.scope, null, 1, null);
    }
}
