package com.miui.floatwindow;

import android.content.res.Configuration;
import android.provider.Settings;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inspector.WindowInspector;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.floatwindow.feature.root.FloatFullCoverView;
import com.miui.floatwindow.feature.root.FloatFullWindow;
import com.miui.floatwindow.utils.DockUtils;
import com.miui.notes.excerpt.ExcerptInfo;
import com.xiaomi.onetrack.api.ba;
import defpackage.R2;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: FloatWindowManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0018\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 X2\u00020\u0001:\u0001XB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#¢\u0006\u0002\u0010$J\u0006\u0010%\u001a\u00020!J\u0010\u0010&\u001a\u00020!2\b\u0010'\u001a\u0004\u0018\u00010\fJ\u000e\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\u0016J\u0006\u0010*\u001a\u00020!J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-H\u0002J\u0015\u0010.\u001a\u0004\u0018\u00010!2\u0006\u0010/\u001a\u00020\u0016¢\u0006\u0002\u00100J\r\u00101\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\u0015\u00103\u001a\u0004\u0018\u00010!2\u0006\u00104\u001a\u00020\u0016¢\u0006\u0002\u00100J\u0006\u00105\u001a\u00020!J\u0015\u00106\u001a\u0004\u0018\u00010!2\u0006\u00107\u001a\u00020\u0016¢\u0006\u0002\u00100J\u0015\u00108\u001a\u0004\u0018\u00010!2\u0006\u00109\u001a\u00020\u0016¢\u0006\u0002\u00100J\u001f\u0010:\u001a\u0004\u0018\u00010!2\b\u0010;\u001a\u0004\u0018\u00010\u001f2\u0006\u0010<\u001a\u00020=¢\u0006\u0002\u0010>J\u0015\u0010?\u001a\u0004\u0018\u00010!2\u0006\u0010@\u001a\u00020A¢\u0006\u0002\u0010BJ\r\u0010C\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\r\u0010D\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\r\u0010H\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\u0015\u0010I\u001a\u0004\u0018\u00010!2\u0006\u0010J\u001a\u00020\u0016¢\u0006\u0002\u00100J\r\u0010K\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\u0006\u0010N\u001a\u00020\u0016J\r\u0010O\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102J\u0018\u0010P\u001a\u00020!2\b\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010S\u001a\u00020\u0016J\r\u0010T\u001a\u0004\u0018\u00010!¢\u0006\u0002\u00102R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010E\u001a\u00020=8F¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0011\u0010L\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0016\u0010U\u001a\u0004\u0018\u00010\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bV\u0010W¨\u0006Y"}, d2 = {"Lcom/miui/floatwindow/FloatWindowManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mFloatFullWindow", "Lcom/miui/floatwindow/feature/root/FloatFullWindow;", "mWindowManager", "Landroid/view/WindowManager;", "mExcerptSavedStatus", "Lcom/miui/floatwindow/feature/root/FloatFullWindow$ExcerptSavedState;", "mAddViewFlow", "Lkotlinx/coroutines/flow/Flow;", "", "getMAddViewFlow", "()Lkotlinx/coroutines/flow/Flow;", "mAddViewFlow$delegate", "Lkotlin/Lazy;", "mFloatFullCoverView", "Lcom/miui/floatwindow/feature/root/FloatFullCoverView;", "mWindowGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "isShowCoverView", "", "mainScope", "Lkotlinx/coroutines/CoroutineScope;", "getMainScope", "()Lkotlinx/coroutines/CoroutineScope;", "mainScope$delegate", ba.e, "Lkotlinx/coroutines/Job;", "TAG", "", "onServiceConfigChanged", "", "newConfig", "Landroid/content/res/Configuration;", "(Landroid/content/res/Configuration;)Lkotlin/Unit;", "createFloatTodoWindow", "addFloatTodoWindow", "dockStatus", "hideOrShowCoverView", "isShow", "removeFloatTodoWindow", "removeView", "view", "Landroid/view/View;", "updateScreenOn", "keepScreenOn", "(Z)Lkotlin/Unit;", "changeFullLayoutToGuide", "()Lkotlin/Unit;", "showQuickNoteUI", "show", "updateDockMode", "onDockModeChanged", "enterDock", "changeGuideVisible", "visible", "showToast", "content", "time", "", "(Ljava/lang/String;J)Lkotlin/Unit;", "excerptInfoChange", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "(Lcom/miui/notes/excerpt/ExcerptInfo;)Lkotlin/Unit;", "startContinuousExcerpt", "stopContinuousExcerpt", "excerptId", "getExcerptId", "()J", "pauseContinuousExcerpt", "setRecordState", "isInRecord", "resumeContinuousExcerpt", "isContinuousExcerpt", "()Z", "clickExcerptNote", "resetClickExcerptNote", "saveSingleExcerpt", "uuid", "Ljava/util/UUID;", "isFromCardClick", "notifyDataChanged", "windowManager", "getWindowManager", "()Landroid/view/WindowManager;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatWindowManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static FloatWindowManager sFloatWindowManager;
    private final String TAG;
    private boolean isShowCoverView;
    private Job job;

    /* JADX INFO: renamed from: mAddViewFlow$delegate, reason: from kotlin metadata */
    private final Lazy mAddViewFlow;
    private FloatFullWindow.ExcerptSavedState mExcerptSavedStatus;
    private FloatFullCoverView mFloatFullCoverView;
    private FloatFullWindow mFloatFullWindow;
    private ViewTreeObserver.OnGlobalLayoutListener mWindowGlobalLayoutListener;
    private WindowManager mWindowManager;

    /* JADX INFO: renamed from: mainScope$delegate, reason: from kotlin metadata */
    private final Lazy mainScope;

    public /* synthetic */ FloatWindowManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private FloatWindowManager() {
        this.mAddViewFlow = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.FloatWindowManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FloatWindowManager.mAddViewFlow_delegate$lambda$0();
            }
        });
        this.isShowCoverView = true;
        this.mainScope = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.FloatWindowManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CoroutineScopeKt.MainScope();
            }
        });
        this.TAG = "FloatWindowManager";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Flow<boolean[]> getMAddViewFlow() {
        return (Flow) this.mAddViewFlow.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow mAddViewFlow_delegate$lambda$0() {
        return FlowKt.onEach(FlowKt.m4091catch(FlowKt.flowOn(FlowKt.flow(new FloatWindowManager$mAddViewFlow$2$1(null)), Dispatchers.getIO()), new FloatWindowManager$mAddViewFlow$2$2(null)), new FloatWindowManager$mAddViewFlow$2$3(null));
    }

    private final CoroutineScope getMainScope() {
        return (CoroutineScope) this.mainScope.getValue();
    }

    public final Unit onServiceConfigChanged(Configuration newConfig) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        Intrinsics.checkNotNull(newConfig);
        floatFullWindow.onServiceConfigChanged(newConfig);
        return Unit.INSTANCE;
    }

    public final void createFloatTodoWindow() {
        Logger.INSTANCE.d(this.TAG, "createFloatTodoWindow");
        Job job = this.job;
        if (job != null) {
            job.cancel(new CancellationException("new task coming.."));
        }
        this.job = BuildersKt__Builders_commonKt.launch$default(getMainScope(), null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.FloatWindowManager$createFloatTodoWindow$1, reason: invalid class name */
    /* JADX INFO: compiled from: FloatWindowManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.FloatWindowManager$createFloatTodoWindow$1", f = "FloatWindowManager.kt", i = {0}, l = {71}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = FloatWindowManager.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScopeKt.ensureActive(coroutineScope);
                Flow mAddViewFlow = FloatWindowManager.this.getMAddViewFlow();
                final FloatWindowManager floatWindowManager = FloatWindowManager.this;
                this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                this.label = 1;
                if (mAddViewFlow.collect(new FlowCollector() { // from class: com.miui.floatwindow.FloatWindowManager.createFloatTodoWindow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((boolean[]) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(boolean[] zArr, Continuation<? super Unit> continuation) {
                        floatWindowManager.addFloatTodoWindow(zArr);
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
    }

    public final void addFloatTodoWindow(boolean[] dockStatus) {
        Logger.INSTANCE.d(this.TAG, "addFloatTodoWindow");
        if (this.mFloatFullWindow == null) {
            BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
            Intrinsics.checkNotNull(dockStatus);
            FloatFullWindow floatFullWindow = new FloatFullWindow(companion, dockStatus);
            this.mFloatFullWindow = floatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow);
            floatFullWindow.restoreExcerptStatus(this.mExcerptSavedStatus);
            this.mFloatFullCoverView = new FloatFullCoverView(BaseNoteApp.INSTANCE.getInstance(), this.mFloatFullWindow);
            WindowManager windowManager = getWindowManager();
            Logger.INSTANCE.d(this.TAG, "addFloatTodoWindow canDrawOverlays: " + Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance()));
            if (Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance())) {
                try {
                    Intrinsics.checkNotNull(windowManager);
                    FloatFullWindow floatFullWindow2 = this.mFloatFullWindow;
                    Intrinsics.checkNotNull(floatFullWindow2);
                    windowManager.addView(floatFullWindow2, floatFullWindow2.getFloatWindowParams());
                    FloatFullCoverView floatFullCoverView = this.mFloatFullCoverView;
                    Intrinsics.checkNotNull(floatFullCoverView);
                    windowManager.addView(floatFullCoverView, floatFullCoverView.getWindowParams());
                } catch (Exception e) {
                    Logger.INSTANCE.d(this.TAG, "addView failed " + e);
                }
            }
            if (this.mWindowGlobalLayoutListener == null) {
                this.mWindowGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.floatwindow.FloatWindowManager$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        FloatWindowManager.addFloatTodoWindow$lambda$2(this.f$0);
                    }
                };
            }
            FloatFullWindow floatFullWindow3 = this.mFloatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow3);
            floatFullWindow3.getViewTreeObserver().addOnGlobalLayoutListener(this.mWindowGlobalLayoutListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addFloatTodoWindow$lambda$2(FloatWindowManager floatWindowManager) {
        FloatFullWindow floatFullWindow;
        if (floatWindowManager.mFloatFullCoverView == null || (floatFullWindow = floatWindowManager.mFloatFullWindow) == null) {
            return;
        }
        Intrinsics.checkNotNull(floatFullWindow);
        if (floatFullWindow.isShowCoverView()) {
            FloatFullCoverView floatFullCoverView = floatWindowManager.mFloatFullCoverView;
            Intrinsics.checkNotNull(floatFullCoverView);
            if (floatFullCoverView.getVisibility() == 8 && floatWindowManager.isShowCoverView) {
                FloatFullCoverView floatFullCoverView2 = floatWindowManager.mFloatFullCoverView;
                Intrinsics.checkNotNull(floatFullCoverView2);
                floatFullCoverView2.setVisibility(0);
            }
            FloatFullCoverView floatFullCoverView3 = floatWindowManager.mFloatFullCoverView;
            Intrinsics.checkNotNull(floatFullCoverView3);
            floatFullCoverView3.updateWindowParams();
            return;
        }
        FloatFullCoverView floatFullCoverView4 = floatWindowManager.mFloatFullCoverView;
        Intrinsics.checkNotNull(floatFullCoverView4);
        if (floatFullCoverView4.getVisibility() == 0) {
            FloatFullCoverView floatFullCoverView5 = floatWindowManager.mFloatFullCoverView;
            Intrinsics.checkNotNull(floatFullCoverView5);
            floatFullCoverView5.setVisibility(8);
        }
    }

    public final void hideOrShowCoverView(boolean isShow) {
        FloatFullWindow floatFullWindow;
        this.isShowCoverView = isShow;
        if (this.mFloatFullCoverView == null || (floatFullWindow = this.mFloatFullWindow) == null) {
            return;
        }
        Intrinsics.checkNotNull(floatFullWindow);
        if (floatFullWindow.isShowCoverView()) {
            com.miui.notes.base.utils.Logger.INSTANCE.d("FloatWindowManager", "hideOrShowCoverView: isShow = " + isShow);
            FloatFullCoverView floatFullCoverView = this.mFloatFullCoverView;
            Intrinsics.checkNotNull(floatFullCoverView);
            floatFullCoverView.setVisibility(isShow ? 0 : 8);
        }
    }

    public final void removeFloatTodoWindow() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow != null) {
            Intrinsics.checkNotNull(floatFullWindow);
            this.mExcerptSavedStatus = floatFullWindow.saveExcerptStatus();
            FloatFullWindow floatFullWindow2 = this.mFloatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow2);
            floatFullWindow2.removeContentOb();
            FloatFullWindow floatFullWindow3 = this.mFloatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow3);
            floatFullWindow3.stopListenHomeKey();
            if (this.mWindowGlobalLayoutListener != null) {
                FloatFullWindow floatFullWindow4 = this.mFloatFullWindow;
                Intrinsics.checkNotNull(floatFullWindow4);
                floatFullWindow4.getViewTreeObserver().removeOnGlobalLayoutListener(this.mWindowGlobalLayoutListener);
                this.mWindowGlobalLayoutListener = null;
            }
            FloatFullWindow floatFullWindow5 = this.mFloatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow5);
            removeView(floatFullWindow5);
            FloatFullWindow floatFullWindow6 = this.mFloatFullWindow;
            Intrinsics.checkNotNull(floatFullWindow6);
            floatFullWindow6.removeTipsWindow();
            this.mFloatFullWindow = null;
        }
        FloatFullCoverView floatFullCoverView = this.mFloatFullCoverView;
        if (floatFullCoverView != null) {
            Intrinsics.checkNotNull(floatFullCoverView);
            removeView(floatFullCoverView);
            this.mFloatFullCoverView = null;
        }
    }

    private final void removeView(View view) {
        WindowManager windowManager = getWindowManager();
        if (WindowInspector.getGlobalWindowViews() == null || !WindowInspector.getGlobalWindowViews().contains(view)) {
            return;
        }
        Intrinsics.checkNotNull(windowManager);
        windowManager.removeView(view);
    }

    public final Unit updateScreenOn(boolean keepScreenOn) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.updateScreenOn(keepScreenOn);
        return Unit.INSTANCE;
    }

    public final Unit changeFullLayoutToGuide() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.changeToGuide();
        return Unit.INSTANCE;
    }

    public final Unit showQuickNoteUI(boolean show) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.showQuickNoteUI(show);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.FloatWindowManager$updateDockMode$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FloatWindowManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.FloatWindowManager$updateDockMode$1", f = "FloatWindowManager.kt", i = {}, l = {R2.array.ai_tag_top_item_gradient_border_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class C02231 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C02231(Continuation<? super C02231> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FloatWindowManager.this.new C02231(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatWindowManager$updateDockMode$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FloatWindowManager.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatWindowManager$updateDockMode$1$1", f = "FloatWindowManager.kt", i = {0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
        static final class C00451 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
            private /* synthetic */ Object L$0;
            int label;

            C00451(Continuation<? super C00451> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00451 c00451 = new C00451(continuation);
                c00451.L$0 = obj;
                return c00451;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super Boolean> flowCollector, Continuation<? super Unit> continuation) {
                return ((C00451) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                    this.label = 1;
                    if (flowCollector.emit(Boxing.boxBoolean(DockUtils.getDockMode(BaseNoteApp.INSTANCE.getInstance())), this) == coroutine_suspended) {
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
                Flow flowFlowOn = FlowKt.flowOn(FlowKt.flow(new C00451(null)), Dispatchers.getDefault());
                final FloatWindowManager floatWindowManager = FloatWindowManager.this;
                this.label = 1;
                if (flowFlowOn.collect(new FlowCollector() { // from class: com.miui.floatwindow.FloatWindowManager.updateDockMode.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Boolean) obj2).booleanValue(), (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                        floatWindowManager.onDockModeChanged(z);
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
    }

    public final void updateDockMode() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C02231(null), 3, null);
    }

    public final Unit onDockModeChanged(boolean enterDock) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.onDockModeChanged(enterDock);
        return Unit.INSTANCE;
    }

    public final Unit changeGuideVisible(boolean visible) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.changeGuideLineVisible(visible);
        return Unit.INSTANCE;
    }

    public final Unit showToast(String content, long time) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.showToast(content, time);
        return Unit.INSTANCE;
    }

    public final Unit excerptInfoChange(ExcerptInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.excerptInfoChange(info);
        return Unit.INSTANCE;
    }

    public final Unit startContinuousExcerpt() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.startContinuousExcerpt();
        return Unit.INSTANCE;
    }

    public final Unit stopContinuousExcerpt() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.stopContinuousExcerpt();
        return Unit.INSTANCE;
    }

    public final long getExcerptId() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return -1L;
        }
        Intrinsics.checkNotNull(floatFullWindow);
        return floatFullWindow.getMExcerptId();
    }

    public final Unit pauseContinuousExcerpt() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.pauseContinuousExcerpt();
        return Unit.INSTANCE;
    }

    public final Unit setRecordState(boolean isInRecord) {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.setRecordState(isInRecord);
        return Unit.INSTANCE;
    }

    public final Unit resumeContinuousExcerpt() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.resumeContinuousExcerpt();
        return Unit.INSTANCE;
    }

    public final boolean isContinuousExcerpt() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow != null) {
            return floatFullWindow.getIsContinuousExcerpt();
        }
        return false;
    }

    public final boolean clickExcerptNote() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow != null) {
            return floatFullWindow.getIsClickExcerptNote();
        }
        return false;
    }

    public final Unit resetClickExcerptNote() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.resetClickExcerptNote();
        return Unit.INSTANCE;
    }

    public final void saveSingleExcerpt(UUID uuid, boolean isFromCardClick) {
        Logger.INSTANCE.d("FloatWindowManager", "saveSingleExcerpt:" + isFromCardClick + " with window: " + this.mFloatFullWindow);
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow != null) {
            floatFullWindow.saveSingleExcerpt(uuid, isFromCardClick);
        }
    }

    public final Unit notifyDataChanged() {
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        if (floatFullWindow == null) {
            return null;
        }
        floatFullWindow.onDataChanged();
        return Unit.INSTANCE;
    }

    private final WindowManager getWindowManager() {
        if (this.mWindowManager == null) {
            Object systemService = BaseNoteApp.INSTANCE.getInstance().getSystemService("window");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            this.mWindowManager = (WindowManager) systemService;
        }
        return this.mWindowManager;
    }

    /* JADX INFO: compiled from: FloatWindowManager.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/floatwindow/FloatWindowManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sFloatWindowManager", "Lcom/miui/floatwindow/FloatWindowManager;", "instance", "getInstance", "()Lcom/miui/floatwindow/FloatWindowManager;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FloatWindowManager getInstance() {
            if (FloatWindowManager.sFloatWindowManager == null) {
                FloatWindowManager.sFloatWindowManager = new FloatWindowManager(null);
            }
            FloatWindowManager floatWindowManager = FloatWindowManager.sFloatWindowManager;
            Intrinsics.checkNotNull(floatWindowManager);
            return floatWindowManager;
        }
    }
}
