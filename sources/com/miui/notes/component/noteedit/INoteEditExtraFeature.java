package com.miui.notes.component.noteedit;

import android.content.Context;
import android.view.View;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public interface INoteEditExtraFeature {
    void dismissPopupWindow();

    Context getContext();

    View.OnClickListener getViewClickListener();

    void hide(boolean z);

    void hideToolbarAndShowInput();

    void hideWithoutAnim();

    void isNeedLock(boolean z);

    void lock();

    boolean onBackPressed();

    void onConfigChanged();

    void onDestroy();

    void onEditContentMode();

    void onEditContentMode(boolean z, boolean z2, boolean z3);

    void onEditTitleMode();

    void onFirstEditContentMode(boolean z, boolean z2, boolean z3);

    void onFocusAble(boolean z);

    void onViewMode();

    void onViewMode(boolean z);

    void removeShowSoftInputTask();

    void setExternalClickListener(View.OnClickListener onClickListener);

    void setReadOnlyActionListener(ReadOnlyActionListener readOnlyActionListener);

    void show(boolean z);

    void show(boolean z, boolean z2, boolean z3);

    void startAudioInput();

    void stopAudioInput();

    void unlock();

    void updateLayout();

    void updateNavigationHeight(int i);

    void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache);
}
