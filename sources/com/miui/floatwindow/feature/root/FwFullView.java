package com.miui.floatwindow.feature.root;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.floatwindow.frame.IFloatWindowView;
import com.miui.maml.ActionCommand;
import com.miui.notes.R;
import com.miui.todo.data.Todo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FwFullView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\u001a\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u000eH\u0016J\u001a\u0010 \u001a\u00020\u000e2\b\u0010!\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\"\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006#"}, d2 = {"Lcom/miui/floatwindow/feature/root/FwFullView;", "Lcom/miui/floatwindow/frame/IFloatWindowView;", "mRootView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mContext", "Landroid/content/Context;", "mNoteHelper", "mTodoHelper", "mFwToastGroup", "mTvToastMes", "Landroid/widget/TextView;", "setModule", "", "note", Todo.Table.TODO_NAME, "preToFullWindow", "onFullWindowShow", "preChangeToGuide", "visibleViewWidth", "", "getVisibleViewWidth", "()I", "showToast", "content", "", "time", "", "canToGuide", "", "onDetachFromWindow", "showFwToast", "msg", "showDuration", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FwFullView implements IFloatWindowView {
    private final Context mContext;
    private final View mFwToastGroup;
    private IFloatWindowView mNoteHelper;
    private final View mRootView;
    private IFloatWindowView mTodoHelper;
    private final TextView mTvToastMes;

    public FwFullView(View mRootView) {
        Intrinsics.checkNotNullParameter(mRootView, "mRootView");
        this.mRootView = mRootView;
        BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        this.mContext = companion;
        View viewFindViewById = mRootView.findViewById(R.id.fw_toast_group);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mFwToastGroup = viewFindViewById;
        View viewFindViewById2 = viewFindViewById.findViewById(android.R.id.message);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mTvToastMes = (TextView) viewFindViewById2;
        View viewFindViewById3 = mRootView.findViewById(R.id.data_content);
        if (RomUtils.isPadMode()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewFindViewById3.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.topMargin = UIUtils.getStatusBarHeight(companion) + 40;
        viewFindViewById3.setLayoutParams(marginLayoutParams);
    }

    public final void setModule(IFloatWindowView note, IFloatWindowView todo) {
        this.mNoteHelper = note;
        this.mTodoHelper = todo;
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preToFullWindow() {
        IFloatWindowView iFloatWindowView = this.mNoteHelper;
        Intrinsics.checkNotNull(iFloatWindowView);
        iFloatWindowView.preToFullWindow();
        IFloatWindowView iFloatWindowView2 = this.mTodoHelper;
        Intrinsics.checkNotNull(iFloatWindowView2);
        iFloatWindowView2.preToFullWindow();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onFullWindowShow() {
        IFloatWindowView iFloatWindowView = this.mNoteHelper;
        Intrinsics.checkNotNull(iFloatWindowView);
        iFloatWindowView.onFullWindowShow();
        IFloatWindowView iFloatWindowView2 = this.mTodoHelper;
        Intrinsics.checkNotNull(iFloatWindowView2);
        iFloatWindowView2.onFullWindowShow();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preChangeToGuide() {
        IFloatWindowView iFloatWindowView = this.mNoteHelper;
        Intrinsics.checkNotNull(iFloatWindowView);
        iFloatWindowView.preChangeToGuide();
        IFloatWindowView iFloatWindowView2 = this.mTodoHelper;
        Intrinsics.checkNotNull(iFloatWindowView2);
        iFloatWindowView2.preChangeToGuide();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public int getVisibleViewWidth() {
        return DisplayUtils.getRealWidth(this.mContext);
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void showToast(String content, long time) {
        showFwToast(content, time);
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public boolean canToGuide() {
        IFloatWindowView iFloatWindowView = this.mNoteHelper;
        Intrinsics.checkNotNull(iFloatWindowView);
        if (!iFloatWindowView.canToGuide()) {
            return false;
        }
        IFloatWindowView iFloatWindowView2 = this.mTodoHelper;
        Intrinsics.checkNotNull(iFloatWindowView2);
        return iFloatWindowView2.canToGuide();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onDetachFromWindow() {
        IFloatWindowView iFloatWindowView = this.mNoteHelper;
        if (iFloatWindowView != null) {
            Intrinsics.checkNotNull(iFloatWindowView);
            iFloatWindowView.onDetachFromWindow();
        }
        IFloatWindowView iFloatWindowView2 = this.mTodoHelper;
        if (iFloatWindowView2 != null) {
            Intrinsics.checkNotNull(iFloatWindowView2);
            iFloatWindowView2.onDetachFromWindow();
        }
    }

    private final void showFwToast(String msg, long showDuration) {
        if (this.mFwToastGroup.getVisibility() != 0) {
            this.mTvToastMes.setText(msg);
            this.mFwToastGroup.setAlpha(0.0f);
            this.mFwToastGroup.setVisibility(0);
            this.mFwToastGroup.requestLayout();
            this.mFwToastGroup.animate().alpha(1.0f).setDuration(200L).setListener(new AnonymousClass1(showDuration)).start();
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.root.FwFullView$showFwToast$1, reason: invalid class name */
    /* JADX INFO: compiled from: FwFullView.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\t"}, d2 = {"com/miui/floatwindow/feature/root/FwFullView$showFwToast$1", "Landroid/animation/Animator$AnimatorListener;", "onAnimationStart", "", ActionCommand.AnimationProperty.PROPERTY_NAME, "Landroid/animation/Animator;", "onAnimationEnd", "onAnimationCancel", "onAnimationRepeat", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass1 implements Animator.AnimatorListener {
        final /* synthetic */ long $showDuration;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
        }

        AnonymousClass1(long j) {
            this.$showDuration = j;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
            View view = FwFullView.this.mFwToastGroup;
            final FwFullView fwFullView = FwFullView.this;
            view.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.FwFullView$showFwToast$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FwFullView.AnonymousClass1.onAnimationEnd$lambda$0(fwFullView);
                }
            }, this.$showDuration);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onAnimationEnd$lambda$0(final FwFullView fwFullView) {
            fwFullView.mFwToastGroup.animate().alpha(0.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.root.FwFullView$showFwToast$1$onAnimationEnd$1$1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation1) {
                    Intrinsics.checkNotNullParameter(animation1, "animation1");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animation1) {
                    Intrinsics.checkNotNullParameter(animation1, "animation1");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation1) {
                    Intrinsics.checkNotNullParameter(animation1, "animation1");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation1) {
                    Intrinsics.checkNotNullParameter(animation1, "animation1");
                    fwFullView.mFwToastGroup.setVisibility(8);
                }
            });
        }
    }
}
