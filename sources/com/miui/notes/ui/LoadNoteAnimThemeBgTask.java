package com.miui.notes.ui;

import android.content.Context;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RxMultiAsyncTask;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LoadNoteAnimThemeBgTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0016\u0010\t\u001a\u0012\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b\u0018\u00010\nH\u0014J\r\u0010\f\u001a\u00020\u0002H\u0014¢\u0006\u0002\u0010\rJ\u0017\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/miui/notes/ui/LoadNoteAnimThemeBgTask;", "Lcom/miui/common/tool/RxMultiAsyncTask;", "", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "onPrepare", "", "observables", "", "Lio/reactivex/Observable;", "doInBackground", "()Ljava/lang/Boolean;", "onPost", "result", "(Ljava/lang/Boolean;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LoadNoteAnimThemeBgTask extends RxMultiAsyncTask<Boolean> {
    private final Context mContext;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.tool.RxMultiAsyncTask
    public void onPost(Boolean result) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoadNoteAnimThemeBgTask(Context mContext) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        onPrepare(this.mObservables);
    }

    @Override // com.miui.common.tool.RxMultiAsyncTask
    protected void onPrepare(List<Observable<Boolean>> observables) {
        int[] iArr = {11, 12, 13, 14};
        for (int i = 0; i < 4; i++) {
            final int i2 = iArr[i];
            if (observables != null) {
                Observable<Boolean> observableCreate = Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.ui.LoadNoteAnimThemeBgTask$$ExternalSyntheticLambda0
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        LoadNoteAnimThemeBgTask.onPrepare$lambda$0(i2, this, observableEmitter);
                    }
                });
                Intrinsics.checkNotNullExpressionValue(observableCreate, "create(...)");
                observables.add(observableCreate);
            }
        }
        super.onPrepare(observables);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onPrepare$lambda$0(int i, LoadNoteAnimThemeBgTask loadNoteAnimThemeBgTask, ObservableEmitter emitter) {
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        Theme theme = ResourceManager.getTheme(i);
        Intrinsics.checkNotNull(theme, "null cannot be cast to non-null type com.miui.notes.theme.AnimTheme");
        ((AnimTheme) theme).getBackgroundView(loadNoteAnimThemeBgTask.mContext);
        emitter.onNext(loadNoteAnimThemeBgTask.doInBackground());
        emitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.miui.common.tool.RxMultiAsyncTask
    public Boolean doInBackground() {
        Logger.INSTANCE.d("LoadNoteAnimThemeBgTask", "doInBackground");
        return true;
    }
}
