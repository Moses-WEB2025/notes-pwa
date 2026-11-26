package com.miui.notes.feature.notelist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;
import java.lang.ref.WeakReference;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: AsyncLoadTheme.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/feature/notelist/AsyncLoadTheme;", "", "weakReference", "Ljava/lang/ref/WeakReference;", "Landroid/view/ViewGroup;", "themeRef", "Lcom/miui/richeditor/theme/Theme;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/ref/WeakReference;Ljava/lang/ref/WeakReference;)V", "disposable", "Lkotlinx/coroutines/Job;", "load", "", "destroy", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AsyncLoadTheme {
    private Job disposable;
    private final WeakReference<Theme> themeRef;
    private final WeakReference<ViewGroup> weakReference;

    public AsyncLoadTheme(WeakReference<ViewGroup> weakReference, WeakReference<Theme> themeRef) {
        Intrinsics.checkNotNullParameter(weakReference, "weakReference");
        Intrinsics.checkNotNullParameter(themeRef, "themeRef");
        this.weakReference = weakReference;
        this.themeRef = themeRef;
    }

    public final void load() {
        Logger.INSTANCE.d("AsyncLoadTheme", "load->" + hashCode() + ",disposable:" + this.disposable);
        Job job = this.disposable;
        if (job != null) {
            Intrinsics.checkNotNull(job);
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.disposable = BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.feature.notelist.AsyncLoadTheme$load$1, reason: invalid class name */
    /* JADX INFO: compiled from: AsyncLoadTheme.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.feature.notelist.AsyncLoadTheme$load$1", f = "AsyncLoadTheme.kt", i = {}, l = {68}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncLoadTheme.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.notes.feature.notelist.AsyncLoadTheme$load$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AsyncLoadTheme.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/graphics/drawable/Drawable;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.feature.notelist.AsyncLoadTheme$load$1$1", f = "AsyncLoadTheme.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, l = {67}, m = "invokeSuspend", n = {"$this$flow", "viewGroup", "theme", CoreConstants.CONTEXT_SCOPE_VALUE, "gridStyle", "drawable", "bgWidth", "bgHeight", "minHeight", "maxHeight"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "I$0", "I$1", "I$2", "I$3"})
        static final class C00911 extends SuspendLambda implements Function2<FlowCollector<? super Drawable>, Continuation<? super Unit>, Object> {
            int I$0;
            int I$1;
            int I$2;
            int I$3;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            int label;
            final /* synthetic */ AsyncLoadTheme this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00911(AsyncLoadTheme asyncLoadTheme, Continuation<? super C00911> continuation) {
                super(2, continuation);
                this.this$0 = asyncLoadTheme;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00911 c00911 = new C00911(this.this$0, continuation);
                c00911.L$0 = obj;
                return c00911;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super Drawable> flowCollector, Continuation<? super Unit> continuation) {
                return ((C00911) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Logger.INSTANCE.d("AsyncLoadTheme", "launch->" + flowCollector.hashCode());
                    ViewGroup viewGroup = (ViewGroup) this.this$0.weakReference.get();
                    Theme theme = (Theme) this.this$0.themeRef.get();
                    if (viewGroup == null || theme == null) {
                        return Unit.INSTANCE;
                    }
                    Context applicationContext = viewGroup.getContext().getApplicationContext();
                    Theme.GridStyle gridStyle = theme.getGridStyle(applicationContext);
                    int width = viewGroup.getWidth();
                    int height = viewGroup.getHeight();
                    int dimension = (int) applicationContext.getResources().getDimension(R.dimen.v12_anim_theme_grid_bg_min_height);
                    int dimension2 = (int) applicationContext.getResources().getDimension(R.dimen.v12_anim_theme_grid_bg_max_height);
                    if (height < dimension) {
                        height = dimension;
                    }
                    int i2 = height > dimension2 ? dimension2 : height;
                    int iMax = Math.max(1, width);
                    Intrinsics.checkNotNull(applicationContext);
                    Drawable drawableDrawableToRound = UIUtils.drawableToRound(applicationContext, gridStyle.mBackground, iMax, i2, applicationContext.getResources().getDimension(R.dimen.v12_grid_item_shape_radius), gridStyle.mBorderColor, gridStyle.mBorderWidth);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                    this.L$1 = SpillingKt.nullOutSpilledVariable(viewGroup);
                    this.L$2 = SpillingKt.nullOutSpilledVariable(theme);
                    this.L$3 = SpillingKt.nullOutSpilledVariable(applicationContext);
                    this.L$4 = SpillingKt.nullOutSpilledVariable(gridStyle);
                    this.L$5 = SpillingKt.nullOutSpilledVariable(drawableDrawableToRound);
                    this.I$0 = iMax;
                    this.I$1 = i2;
                    this.I$2 = dimension;
                    this.I$3 = dimension2;
                    this.label = 1;
                    if (flowCollector.emit(drawableDrawableToRound, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowFlowOn = FlowKt.flowOn(FlowKt.m4091catch(FlowKt.flow(new C00911(AsyncLoadTheme.this, null)), new AnonymousClass2(null)), Dispatchers.getIO());
                final AsyncLoadTheme asyncLoadTheme = AsyncLoadTheme.this;
                this.label = 1;
                if (flowFlowOn.collect(new FlowCollector() { // from class: com.miui.notes.feature.notelist.AsyncLoadTheme.load.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((Drawable) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(Drawable drawable, Continuation<? super Unit> continuation) {
                        ViewGroup viewGroup = (ViewGroup) asyncLoadTheme.weakReference.get();
                        if (viewGroup != null) {
                            viewGroup.setBackground(drawable);
                        }
                        return Unit.INSTANCE;
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.feature.notelist.AsyncLoadTheme$load$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: AsyncLoadTheme.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/graphics/drawable/Drawable;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.feature.notelist.AsyncLoadTheme$load$1$2", f = "AsyncLoadTheme.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super Drawable>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super Drawable> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                anonymousClass2.L$0 = th;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Throwable th = (Throwable) this.L$0;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.d("AsyncLoadTheme", "catch an exception:" + th);
                return Unit.INSTANCE;
            }
        }
    }

    public final void destroy() {
        Logger.INSTANCE.d("AsyncLoadTheme", "destroy->" + hashCode() + ",disposable:" + this.disposable);
        Job job = this.disposable;
        if (job != null) {
            Intrinsics.checkNotNull(job);
            job.cancel(new CancellationException("canceled by destroy"));
        }
    }
}
