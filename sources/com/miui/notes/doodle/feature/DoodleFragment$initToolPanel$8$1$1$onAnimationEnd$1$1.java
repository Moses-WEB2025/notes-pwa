package com.miui.notes.doodle.feature;

import android.animation.Animator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.miui.maml.ActionCommand;
import com.miui.notes.base.app.MainHandler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\t"}, d2 = {"com/miui/notes/doodle/feature/DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1", "Landroid/animation/Animator$AnimatorListener;", "onAnimationStart", "", ActionCommand.AnimationProperty.PROPERTY_NAME, "Landroid/animation/Animator;", "onAnimationEnd", "onAnimationCancel", "onAnimationRepeat", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1 implements Animator.AnimatorListener {
    final /* synthetic */ DoodleFragment this$0;

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

    DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1(DoodleFragment doodleFragment) {
        this.this$0 = doodleFragment;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Log.d("DoodleFragment", "onAnimationEnd: ");
        View view = this.this$0.pixelTipView;
        TextView textView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
            view = null;
        }
        view.setVisibility(8);
        this.this$0.getBtnUnImmerse().setImportantForAccessibility(1);
        View view2 = this.this$0.pixelTipView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelTipView");
            view2 = null;
        }
        if (!view2.isAccessibilityFocused()) {
            TextView textView2 = this.this$0.pixelTipText;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pixelTipText");
            } else {
                textView = textView2;
            }
            if (!textView.isAccessibilityFocused()) {
                return;
            }
        }
        MainHandler handler = this.this$0.getHandler();
        final DoodleFragment doodleFragment = this.this$0;
        handler.post(new Runnable() { // from class: com.miui.notes.doodle.feature.DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DoodleFragment$initToolPanel$8$1$1$onAnimationEnd$1$1.onAnimationEnd$lambda$0(doodleFragment);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAnimationEnd$lambda$0(DoodleFragment doodleFragment) {
        View view = doodleFragment.pixelEraser;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pixelEraser");
            view = null;
        }
        view.sendAccessibilityEvent(128);
    }
}
