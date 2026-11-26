package com.miui.notes.ui;

import android.content.Context;
import com.miui.common.tool.Logger;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import io.reactivex.disposables.CompositeDisposable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class PreLoadLiteNoteThemeResTask extends PreLoadNoteThemeResTask {
    private final WeakReference<Context> contextRef;
    protected int mPreLoadThemeId;

    public PreLoadLiteNoteThemeResTask(CompositeDisposable compositeDisposable, int i, Context context) {
        super(compositeDisposable, i, context);
        this.mPreLoadThemeId = -1;
        this.contextRef = new WeakReference<>(context);
        this.mPreLoadThemeId = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.miui.notes.ui.PreLoadNoteThemeResTask, com.miui.common.tool.RxStandardAsyncTask
    public NoteThemeResCache doInBackground() {
        Logger.INSTANCE.d("PreLoadLiteNoteThemeResTask", "doInBackground");
        Context context = this.contextRef.get();
        if (context == null) {
            return null;
        }
        Theme theme = ResourceManager.getTheme(this.mPreLoadThemeId);
        NoteThemeResCache noteThemeResCache = new NoteThemeResCache(this.mPreLoadThemeId);
        if (theme != null) {
            theme.getEditorStyle(context);
            noteThemeResCache.loadActionBarRes(theme, context);
            noteThemeResCache.loadToolBarResForLite(theme, context);
            if (theme instanceof AnimTheme) {
                ((AnimTheme) theme).getBackgroundView(context);
            }
        }
        return noteThemeResCache;
    }
}
