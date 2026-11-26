package com.miui.doodle.base;

import com.miui.common.stat.EditStat;
import com.miui.doodle.base.core.IDoodle;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import kotlin.Metadata;

/* JADX INFO: compiled from: IDoodleListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&J\u001a\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\tH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&¨\u0006\u000fÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/IDoodleListener;", "", "onSaved", "", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "doodleFile", "Ljava/io/File;", CallMethod.ARG_CALLBACK, "Ljava/lang/Runnable;", "onNoChangeAndExit", "onReady", "onProcessing", "isFinished", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IDoodleListener {
    void onNoChangeAndExit(IDoodle doodle, Runnable callback);

    void onProcessing(boolean isFinished);

    void onReady(IDoodle doodle);

    void onSaved(IDoodle doodle, File doodleFile, Runnable callback);
}
