package com.miui.notes.mishow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.backup.NoteManager;
import com.miui.notes.backup.TodoManager;
import com.xiaomi.onetrack.api.ba;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* JADX INFO: compiled from: MiShowReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/mishow/MiShowReceiver;", "Landroid/content/BroadcastReceiver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "worker", "Lkotlinx/coroutines/CoroutineScope;", "getWorker", "()Lkotlinx/coroutines/CoroutineScope;", "worker$delegate", "Lkotlin/Lazy;", ba.e, "Lkotlinx/coroutines/Job;", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiShowReceiver extends BroadcastReceiver {
    public static final String ACTION_MISHOW_CLEAR_DATA = "com.xiaomi.mihomemanager.resetAppDataOrSetting";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String TAG = "MiShowReceiver";
    private Job job;

    /* JADX INFO: renamed from: worker$delegate, reason: from kotlin metadata */
    private final Lazy worker = LazyKt.lazy(new Function0() { // from class: com.miui.notes.mishow.MiShowReceiver$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MiShowReceiver.worker_delegate$lambda$0();
        }
    });

    private final CoroutineScope getWorker() {
        return (CoroutineScope) this.worker.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineScope worker_delegate$lambda$0() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (!INSTANCE.isMiShowInstalled(context)) {
            Logger.INSTANCE.e(TAG, "mi-show not installed!!!");
            return;
        }
        String action = intent.getAction();
        if (action != null && action.hashCode() == -437041681 && action.equals(ACTION_MISHOW_CLEAR_DATA)) {
            com.miui.notes.base.utils.Logger.INSTANCE.i(TAG, "Received clear broadcast");
            BuildersKt__Builders_commonKt.launch$default(getWorker(), null, null, new AnonymousClass1(context, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.mishow.MiShowReceiver$onReceive$1, reason: invalid class name */
    /* JADX INFO: compiled from: MiShowReceiver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.mishow.MiShowReceiver$onReceive$1", f = "MiShowReceiver.kt", i = {}, l = {39}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MiShowReceiver.this.new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Job job = MiShowReceiver.this.job;
                if (job != null) {
                    this.label = 1;
                    if (JobKt.cancelAndJoin(job, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            MiShowReceiver.INSTANCE.clearNotes(this.$context);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: MiShowReceiver.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/notes/mishow/MiShowReceiver$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "ACTION_MISHOW_CLEAR_DATA", "clearNotes", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "isMiShowInstalled", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void clearNotes(Context context) {
            try {
                if (context == null) {
                    com.miui.notes.base.utils.Logger.INSTANCE.e(MiShowReceiver.TAG, "clearNotes:context == null  ");
                    return;
                }
                com.miui.notes.base.utils.Logger.INSTANCE.e(MiShowReceiver.TAG, "delete database");
                new NoteManager(context).deleteAllData();
                new TodoManager(context).deleteAllData();
            } catch (Exception e) {
                com.miui.notes.base.utils.Logger.INSTANCE.e(MiShowReceiver.TAG, "clearNotes: " + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isMiShowInstalled(Context context) {
            PackageInfo packageInfo;
            try {
                packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.mihomemanager", 0);
            } catch (Exception unused) {
                packageInfo = null;
            }
            return packageInfo != null && TextUtils.equals(packageInfo.sharedUserId, "android.uid.system");
        }
    }
}
