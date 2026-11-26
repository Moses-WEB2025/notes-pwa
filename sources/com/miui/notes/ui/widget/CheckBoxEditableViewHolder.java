package com.miui.notes.ui.widget;

import android.R;
import android.view.View;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CheckBoxEditableViewHolder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0016J\b\u0010\u001b\u001a\u00020\u0012H\u0016J\b\u0010\u001c\u001a\u00020\u0012H\u0016R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/miui/notes/ui/widget/CheckBoxEditableViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/miui/notes/ui/widget/ViewHolderEditableCallback;", "Lcom/miui/notes/ui/widget/ViewHolderRecycledCallback;", "itemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mCheck", "Landroid/widget/CheckBox;", "getMCheck", "()Landroid/widget/CheckBox;", "setMCheck", "(Landroid/widget/CheckBox;)V", "mStarted", "", "hasAnimationStarted", "onAnimationStart", "", "animateToVisible", "onAnimationUpdate", "progress", "", "onAnimationStop", "onUpdateEditable", "visible", "checked", "onClearAnimation", "onRecycled", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class CheckBoxEditableViewHolder extends RecyclerView.ViewHolder implements ViewHolderEditableCallback, ViewHolderRecycledCallback {
    private CheckBox mCheck;
    private boolean mStarted;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CheckBoxEditableViewHolder(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.mCheck = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

    protected final CheckBox getMCheck() {
        return this.mCheck;
    }

    protected final void setMCheck(CheckBox checkBox) {
        this.mCheck = checkBox;
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    /* JADX INFO: renamed from: hasAnimationStarted, reason: from getter */
    public boolean getMStarted() {
        return this.mStarted;
    }

    public void onAnimationStart(boolean animateToVisible) {
        CheckBox checkBox;
        this.mStarted = true;
        if (!animateToVisible || (checkBox = this.mCheck) == null) {
            return;
        }
        Intrinsics.checkNotNull(checkBox);
        checkBox.setVisibility(0);
        CheckBox checkBox2 = this.mCheck;
        Intrinsics.checkNotNull(checkBox2);
        checkBox2.setAlpha(0.0f);
        CheckBox checkBox3 = this.mCheck;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setScaleX(0.8f);
        CheckBox checkBox4 = this.mCheck;
        Intrinsics.checkNotNull(checkBox4);
        checkBox4.setScaleY(0.8f);
    }

    public void onAnimationUpdate(boolean animateToVisible, float progress) {
        if (!animateToVisible) {
            progress = 1 - progress;
        }
        CheckBox checkBox = this.mCheck;
        if (checkBox == null) {
            return;
        }
        Intrinsics.checkNotNull(checkBox);
        checkBox.setAlpha(progress);
        CheckBox checkBox2 = this.mCheck;
        Intrinsics.checkNotNull(checkBox2);
        float f = (progress * 0.2f) + 0.8f;
        checkBox2.setScaleX(f);
        CheckBox checkBox3 = this.mCheck;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setScaleY(f);
    }

    public void onAnimationStop(boolean animateToVisible) {
        this.mStarted = false;
        onClearAnimation();
        CheckBox checkBox = this.mCheck;
        if (checkBox == null) {
            return;
        }
        if (animateToVisible) {
            Intrinsics.checkNotNull(checkBox);
            checkBox.setVisibility(0);
        } else {
            Intrinsics.checkNotNull(checkBox);
            checkBox.setVisibility(8);
        }
    }

    public boolean onUpdateEditable(boolean visible, boolean checked) {
        CheckBox checkBox;
        if (!isRecyclable() || (checkBox = this.mCheck) == null) {
            return false;
        }
        Intrinsics.checkNotNull(checkBox);
        boolean z = checkBox.getVisibility() == 0;
        CheckBox checkBox2 = this.mCheck;
        Intrinsics.checkNotNull(checkBox2);
        boolean zIsChecked = checkBox2.isChecked();
        CheckBox checkBox3 = this.mCheck;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setChecked(checked);
        if (visible == z && (!visible || checked == zIsChecked)) {
            return false;
        }
        if (visible) {
            CheckBox checkBox4 = this.mCheck;
            Intrinsics.checkNotNull(checkBox4);
            checkBox4.setAlpha(1.0f);
            CheckBox checkBox5 = this.mCheck;
            Intrinsics.checkNotNull(checkBox5);
            checkBox5.setScaleX(1.0f);
            CheckBox checkBox6 = this.mCheck;
            Intrinsics.checkNotNull(checkBox6);
            checkBox6.setScaleY(1.0f);
            CheckBox checkBox7 = this.mCheck;
            Intrinsics.checkNotNull(checkBox7);
            checkBox7.setVisibility(0);
        } else {
            CheckBox checkBox8 = this.mCheck;
            Intrinsics.checkNotNull(checkBox8);
            checkBox8.setVisibility(8);
        }
        return true;
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        CheckBox checkBox = this.mCheck;
        if (checkBox == null) {
            return;
        }
        Intrinsics.checkNotNull(checkBox);
        checkBox.setAlpha(1.0f);
        CheckBox checkBox2 = this.mCheck;
        Intrinsics.checkNotNull(checkBox2);
        checkBox2.setScaleX(1.0f);
        CheckBox checkBox3 = this.mCheck;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setScaleY(1.0f);
    }

    public void onRecycled() {
        if (this.mStarted) {
            onAnimationStop(false);
        }
    }
}
