package com.miui.notes.component;

import android.text.TextPaint;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.doodle.document.Layer;
import com.xiaomi.onetrack.api.ba;
import java.lang.ref.WeakReference;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: AsyncLayout.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001!B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J>\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018J\u0006\u0010\u001c\u001a\u00020\u0010R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00030\u00030\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0012\u0010\u001d\u001a\u00020\u001eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006\""}, d2 = {"Lcom/miui/notes/component/AsyncLayout;", "Lkotlinx/coroutines/CoroutineScope;", "_previewTextView", "Lcom/miui/notes/component/PreviewTextView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/component/PreviewTextView;)V", "rf", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", ba.e, "Lkotlinx/coroutines/Job;", "getJob", "()Lkotlinx/coroutines/Job;", "setJob", "(Lkotlinx/coroutines/Job;)V", "layoutText", "", "layout", "Lcom/miui/notes/component/PreviewTextView$AbstractLayout;", "text", "", "paint", "Landroid/text/TextPaint;", "titleStyle", "", "bodyStyle", "stubStyle", Layer.KEY_WIDTH, ToDoStat.CANCEL, "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "LayoutCallBack", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AsyncLayout implements CoroutineScope {
    private final /* synthetic */ CoroutineScope $$delegate_0;
    private Job job;
    private WeakReference<PreviewTextView> rf;

    /* JADX INFO: compiled from: AsyncLayout.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/notes/component/AsyncLayout$LayoutCallBack;", "", "callBack", "", "text", "", "rawText", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface LayoutCallBack {
        void callBack(CharSequence text, CharSequence rawText);
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    public AsyncLayout(PreviewTextView _previewTextView) {
        Intrinsics.checkNotNullParameter(_previewTextView, "_previewTextView");
        this.$$delegate_0 = CoroutineScopeKt.MainScope();
        this.rf = new WeakReference<>(_previewTextView);
    }

    public final Job getJob() {
        return this.job;
    }

    public final void setJob(Job job) {
        this.job = job;
    }

    /* JADX INFO: renamed from: com.miui.notes.component.AsyncLayout$layoutText$1, reason: invalid class name */
    /* JADX INFO: compiled from: AsyncLayout.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.component.AsyncLayout$layoutText$1", f = "AsyncLayout.kt", i = {}, l = {28}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bodyStyle;
        final /* synthetic */ PreviewTextView.AbstractLayout $layout;
        final /* synthetic */ TextPaint $paint;
        final /* synthetic */ int $stubStyle;
        final /* synthetic */ CharSequence $text;
        final /* synthetic */ int $titleStyle;
        final /* synthetic */ PreviewTextView $view;
        final /* synthetic */ int $width;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(PreviewTextView previewTextView, CharSequence charSequence, PreviewTextView.AbstractLayout abstractLayout, TextPaint textPaint, int i, int i2, int i3, int i4, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$view = previewTextView;
            this.$text = charSequence;
            this.$layout = abstractLayout;
            this.$paint = textPaint;
            this.$titleStyle = i;
            this.$bodyStyle = i2;
            this.$stubStyle = i3;
            this.$width = i4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$view, this.$text, this.$layout, this.$paint, this.$titleStyle, this.$bodyStyle, this.$stubStyle, this.$width, continuation);
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
                this.label = 1;
                obj = BuildersKt.withContext(Dispatchers.getIO(), new AsyncLayout$layoutText$1$result$1(this.$layout, this.$view, this.$text, this.$paint, this.$titleStyle, this.$bodyStyle, this.$stubStyle, this.$width, null), this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$view.callBack((CharSequence) obj, this.$text);
            return Unit.INSTANCE;
        }
    }

    public final void layoutText(PreviewTextView.AbstractLayout layout, CharSequence text, TextPaint paint, int titleStyle, int bodyStyle, int stubStyle, int width) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(paint, "paint");
        PreviewTextView previewTextView = this.rf.get();
        if (previewTextView == null) {
            return;
        }
        this.job = BuildersKt__Builders_commonKt.launch$default(this, null, null, new AnonymousClass1(previewTextView, text, layout, paint, titleStyle, bodyStyle, stubStyle, width, null), 3, null);
    }

    public final void cancel() {
        Job job = this.job;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
    }
}
