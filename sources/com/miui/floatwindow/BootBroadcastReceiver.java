package com.miui.floatwindow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.floatwindow.frame.AppInstallBroadcastReceiver;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: BootBroadcastReceiver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/floatwindow/BootBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "dispatcher", "Lkotlinx/coroutines/CoroutineScope;", "getDispatcher", "()Lkotlinx/coroutines/CoroutineScope;", "dispatcher$delegate", "Lkotlin/Lazy;", "TAG", "", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BootBroadcastReceiver extends BroadcastReceiver {

    /* JADX INFO: renamed from: dispatcher$delegate, reason: from kotlin metadata */
    private final Lazy dispatcher = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.BootBroadcastReceiver$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BootBroadcastReceiver.dispatcher_delegate$lambda$0();
        }
    });
    private final String TAG = "BootBroadcastReceiver";

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineScope dispatcher_delegate$lambda$0() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    public final CoroutineScope getDispatcher() {
        return (CoroutineScope) this.dispatcher.getValue();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Logger.INSTANCE.d(this.TAG, intent.getAction() + StringUtils.SPACE);
        if (intent.getAction() != null) {
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.BOOT_COMPLETED") && Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance())) {
                BuildersKt__Builders_commonKt.launch$default(getDispatcher(), null, null, new AnonymousClass1(context, this, null), 3, null);
            } else if (Intrinsics.areEqual(intent.getAction(), AppInstallBroadcastReceiver.PACKAGE_REMOVED_ACTION) || Intrinsics.areEqual(intent.getAction(), AppInstallBroadcastReceiver.PACKAGE_REPLACED_ACTION)) {
                BuildersKt__Builders_commonKt.launch$default(getDispatcher(), null, null, new AnonymousClass2(context, null), 3, null);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.BootBroadcastReceiver$onReceive$1, reason: invalid class name */
    /* JADX INFO: compiled from: BootBroadcastReceiver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.BootBroadcastReceiver$onReceive$1", f = "BootBroadcastReceiver.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;
        final /* synthetic */ BootBroadcastReceiver this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, BootBroadcastReceiver bootBroadcastReceiver, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$context = context;
            this.this$0 = bootBroadcastReceiver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$context, this.this$0, continuation);
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
            if (PreferenceUtils.getEnableFloatMode(this.$context)) {
                FloatService.INSTANCE.startExcerptService(this.$context, this.this$0.TAG);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.BootBroadcastReceiver$onReceive$2, reason: invalid class name */
    /* JADX INFO: compiled from: BootBroadcastReceiver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.BootBroadcastReceiver$onReceive$2", f = "BootBroadcastReceiver.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Context context, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (PreferenceUtils.getEnableFloatMode(this.$context)) {
                FloatWindowManagerAgent.updateDockMode();
            }
            return Unit.INSTANCE;
        }
    }
}
