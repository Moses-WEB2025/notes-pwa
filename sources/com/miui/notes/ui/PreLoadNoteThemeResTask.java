package com.miui.notes.ui;

import android.content.Context;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RxStandardAsyncTask;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class PreLoadNoteThemeResTask extends RxStandardAsyncTask<NoteThemeResCache> {
    private final WeakReference<Context> contextRef;
    protected int mPreLoadThemeId;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.tool.RxStandardAsyncTask
    public void onPost(NoteThemeResCache noteThemeResCache) {
    }

    public PreLoadNoteThemeResTask(CompositeDisposable compositeDisposable, int i, Context context) {
        super(compositeDisposable, Schedulers.io(), AndroidSchedulers.mainThread());
        this.mPreLoadThemeId = -1;
        this.contextRef = new WeakReference<>(context);
        this.mPreLoadThemeId = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.miui.common.tool.RxStandardAsyncTask
    public NoteThemeResCache doInBackground() {
        Logger.INSTANCE.d("PreLoadNoteThemeResTask", "doInBackground");
        Context context = this.contextRef.get();
        if (context == null) {
            return null;
        }
        Theme theme = ResourceManager.getTheme(this.mPreLoadThemeId);
        NoteThemeResCache noteThemeResCache = new NoteThemeResCache(this.mPreLoadThemeId);
        if (theme != null) {
            theme.getEditorStyle(context);
            noteThemeResCache.loadActionBarRes(theme, context);
            noteThemeResCache.loadToolBarRes(theme, context);
            if (theme instanceof AnimTheme) {
                ((AnimTheme) theme).getBackgroundView(context);
            }
            ResourceManager.pPreLoadResCache = noteThemeResCache;
        }
        return noteThemeResCache;
    }
}
