package com.miui.notes.component.noteedit;

import android.animation.Animator;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.model.WorkingNote;

/* JADX INFO: loaded from: classes4.dex */
public interface INoteEditToolBar extends INoteEditExtraFeature {

    public interface OnVisibleChangedListener {
        void onVisibilityChanged(boolean z);
    }

    void checkAndCollapseThemePanel();

    void closeAudioRecordPanel();

    void collapsePanel(Animator.AnimatorListener animatorListener);

    void collapsePanel(Animator.AnimatorListener animatorListener, boolean z);

    boolean isTextStylePanelShowed();

    boolean ismIsShowThemeChooser();

    boolean ismToolbarIsShow();

    void resetEditBarStatus();

    void setMarginStart();

    void setNeedDelayShowInput(boolean z);

    void setOnThemeChosenListener(ThemeChooserGroup.OnThemeChosenListener onThemeChosenListener);

    void setOnVisibleChangedListener(OnVisibleChangedListener onVisibleChangedListener);

    void setTextStylePanelVisible(boolean z, boolean z2);

    void setWorkingNote(WorkingNote workingNote);

    boolean shouldHideSoftInput();

    void toggleAudioRecordPanel();

    void toggleCheckBox();

    void toggleInsertDoodle();

    void toggleInsertImage();

    void toggleThemeChooserFromHead();

    void updateAudioVolume(float f);
}
