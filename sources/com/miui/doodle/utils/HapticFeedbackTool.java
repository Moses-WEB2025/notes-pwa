package com.miui.doodle.utils;

import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
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
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import miuix.util.HapticFeedbackCompat;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: compiled from: HapticFeedbackTool.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/miui/doodle/utils/HapticFeedbackTool;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNewHapticFeedbackUtil", "Lmiuix/util/HapticFeedbackCompat;", "performHapticFeedback", "", "view", "Landroid/view/View;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HapticFeedbackTool {
    public static final HapticFeedbackTool INSTANCE = new HapticFeedbackTool();
    private static HapticFeedbackCompat mNewHapticFeedbackUtil;

    private HapticFeedbackTool() {
    }

    /* JADX INFO: renamed from: com.miui.doodle.utils.HapticFeedbackTool$performHapticFeedback$1, reason: invalid class name */
    /* JADX INFO: compiled from: HapticFeedbackTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.utils.HapticFeedbackTool$performHapticFeedback$1", f = "HapticFeedbackTool.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ View $view;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(View view, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$view = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$view, continuation);
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
            if (!HapticCompat.doesSupportHaptic("2.0")) {
                if (HapticFeedbackTool.mNewHapticFeedbackUtil == null) {
                    HapticFeedbackTool hapticFeedbackTool = HapticFeedbackTool.INSTANCE;
                    HapticFeedbackTool.mNewHapticFeedbackUtil = new HapticFeedbackCompat(this.$view.getContext().getApplicationContext());
                }
                HapticFeedbackCompat hapticFeedbackCompat = HapticFeedbackTool.mNewHapticFeedbackUtil;
                Intrinsics.checkNotNull(hapticFeedbackCompat);
                if (hapticFeedbackCompat.supportLinearMotor()) {
                    HapticCompat.performHapticFeedback(this.$view, HapticFeedbackConstants.MIUI_TAP_LIGHT);
                } else {
                    this.$view.performHapticFeedback(4);
                }
            } else {
                HapticCompat.performHapticFeedback(this.$view, HapticFeedbackConstants.MIUI_GEAR_LIGHT);
            }
            return Unit.INSTANCE;
        }
    }

    public final void performHapticFeedback(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass1(view, null), 2, null);
    }
}
