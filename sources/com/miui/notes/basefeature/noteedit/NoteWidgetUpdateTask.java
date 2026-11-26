package com.miui.notes.basefeature.noteedit;

import android.content.Context;
import android.widget.Toast;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RxStandardAsyncTask;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class NoteWidgetUpdateTask extends RxStandardAsyncTask<int[]> {
    private final Context context;
    private final WeakReference<BaseNormalNoteEditFragment> mWkFragment;
    private final long noteId;

    public NoteWidgetUpdateTask(CompositeDisposable compositeDisposable, BaseNormalNoteEditFragment baseNormalNoteEditFragment, long j) {
        super(compositeDisposable, Schedulers.io(), AndroidSchedulers.mainThread());
        this.mWkFragment = new WeakReference<>(baseNormalNoteEditFragment);
        this.noteId = j;
        this.context = NoteApp.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.tool.RxStandardAsyncTask
    public int[] doInBackground() {
        Logger.INSTANCE.d("NoteWidgetUpdateTask", "NoteWidgetUpdateTask/doingBackground");
        return DataUtils.getWidgetInfoByNote(this.context, this.noteId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.tool.RxStandardAsyncTask
    public void onPost(int[] iArr) {
        BaseNormalNoteEditFragment baseNormalNoteEditFragment = this.mWkFragment.get();
        if (baseNormalNoteEditFragment != null) {
            applyWidgetResult(baseNormalNoteEditFragment.getWorkingNote(), this.noteId, iArr);
            baseNormalNoteEditFragment.mIsSendingToDesktop = false;
        }
    }

    private void applyWidgetResult(WorkingNote workingNote, long j, int[] iArr) {
        if (workingNote == null || workingNote.getNoteId() != j || iArr == null) {
            return;
        }
        int i = iArr[0];
        int i2 = iArr[1];
        if (workingNote.getWidgetId() != i) {
            workingNote.applyWidgetId(i);
            workingNote.applyWidgetType(i2);
            if (i > 0) {
                Toast.makeText(this.context, R.string.info_note_enter_desktop, 0).show();
            }
        }
    }
}
