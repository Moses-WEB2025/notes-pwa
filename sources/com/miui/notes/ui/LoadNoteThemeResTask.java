package com.miui.notes.ui;

import com.miui.common.tool.RxStandardAsyncTask;
import com.miui.notes.theme.util.NoteThemeResCache;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LoadNoteThemeResTask extends RxStandardAsyncTask<NoteThemeResCache> {
    public LoadNoteThemeResTask(CompositeDisposable compositeDisposable) {
        super(compositeDisposable, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
