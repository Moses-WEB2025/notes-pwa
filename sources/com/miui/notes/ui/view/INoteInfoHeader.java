package com.miui.notes.ui.view;

import android.database.Cursor;
import android.view.View;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public interface INoteInfoHeader {

    public interface NoteMenuClickListener {
        void onItemChecked(long j, long[] jArr, String str);

        void onItemPerformClick(int i);

        boolean onMenuClick();
    }

    void bindFragment(Fragment fragment);

    void dismissMenu();

    View getView();

    void onDestroy();

    void onNoteMenuClick(int i);

    void setFolderGroupVisibility(int i);

    void setHeadMenuInfo(Cursor cursor, long[] jArr, long j);

    void setNoteMenuClickListener(NoteMenuClickListener noteMenuClickListener);

    void update(WorkingNote workingNote, int i);

    void updateAlarm(WorkingNote workingNote);

    void updateDataTime(WorkingNote workingNote);

    void updateFolderName(String str);

    void updateNoteHiddenState(WorkingNote workingNote);

    void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache);

    void updateTextCount(int i);
}
