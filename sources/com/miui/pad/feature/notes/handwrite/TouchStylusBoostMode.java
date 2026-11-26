package com.miui.pad.feature.notes.handwrite;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.onetrack.api.ba;
import java.lang.reflect.Method;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: TouchStylusBoostMode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/TouchStylusBoostMode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", ba.e, "Lkotlinx/coroutines/Job;", "getJob", "()Lkotlinx/coroutines/Job;", "setJob", "(Lkotlinx/coroutines/Job;)V", "setMode", "", "mode", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TouchStylusBoostMode {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<TouchStylusBoostMode> sInstance$delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TouchStylusBoostMode.sInstance_delegate$lambda$0();
        }
    });
    private Job job;

    /* JADX INFO: compiled from: TouchStylusBoostMode.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/TouchStylusBoostMode$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sInstance", "Lcom/miui/pad/feature/notes/handwrite/TouchStylusBoostMode;", "getSInstance", "()Lcom/miui/pad/feature/notes/handwrite/TouchStylusBoostMode;", "sInstance$delegate", "Lkotlin/Lazy;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TouchStylusBoostMode getSInstance() {
            return (TouchStylusBoostMode) TouchStylusBoostMode.sInstance$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TouchStylusBoostMode sInstance_delegate$lambda$0() {
        return new TouchStylusBoostMode();
    }

    public final Job getJob() {
        return this.job;
    }

    public final void setJob(Job job) {
        this.job = job;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode$setMode$1, reason: invalid class name */
    /* JADX INFO: compiled from: TouchStylusBoostMode.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode$setMode$1", f = "TouchStylusBoostMode.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $mode;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(int i, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$mode = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$mode, continuation);
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
            try {
                Class<?> cls = Class.forName("miui.util.ITouchFeature");
                Method method = cls.getMethod("getInstance", new Class[0]);
                int i = cls.getField("TOUCH_STYLUS_BOOST_MODE").getInt(null);
                Method method2 = cls.getMethod("setTouchMode", Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object objInvoke = method.invoke(null, new Object[0]);
                Logger.INSTANCE.d("TouchStylusBoostMode", "invoke instance:" + objInvoke + " touchMode:" + i + " mode:" + this.$mode);
                method2.invoke(objInvoke, Boxing.boxInt(0), Boxing.boxInt(i), Boxing.boxInt(this.$mode));
            } catch (Exception e) {
                Logger.INSTANCE.d("TouchStylusBoostMode", e.toString());
                e.printStackTrace();
            }
            return Unit.INSTANCE;
        }
    }

    public final void setMode(int mode) {
        Job job = this.job;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.job = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, null, null, new AnonymousClass1(mode, null), 3, null);
    }
}
