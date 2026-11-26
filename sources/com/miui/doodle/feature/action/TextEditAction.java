package com.miui.doodle.feature.action;

import android.graphics.Rect;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.feature.draw.DoodleText;
import com.miui.doodle.feature.draw.DoodleView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextEditAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u000fH\u0016J\b\u0010\u0014\u001a\u00020\u000fH\u0016J\b\u0010\u0015\u001a\u00020\u000fH\u0016J\b\u0010\u0016\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u0017"}, d2 = {"Lcom/miui/doodle/feature/action/TextEditAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "doodleText", "Lcom/miui/doodle/feature/draw/DoodleText;", "oldText", "", "newText", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/draw/DoodleText;Ljava/lang/String;Ljava/lang/String;)V", "getDoodleText", "()Lcom/miui/doodle/feature/draw/DoodleText;", "getOldText", "()Ljava/lang/String;", "getNewText", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TextEditAction extends BaseAction {
    private final DoodleText doodleText;
    private final String newText;
    private final String oldText;

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void destroy() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
    }

    public final DoodleText getDoodleText() {
        return this.doodleText;
    }

    public final String getOldText() {
        return this.oldText;
    }

    public TextEditAction(DoodleText doodleText, String oldText, String newText) {
        Intrinsics.checkNotNullParameter(doodleText, "doodleText");
        Intrinsics.checkNotNullParameter(oldText, "oldText");
        Intrinsics.checkNotNullParameter(newText, "newText");
        this.doodleText = doodleText;
        this.oldText = oldText;
        this.newText = newText;
    }

    public final String getNewText() {
        return this.newText;
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        Rect rect = new Rect();
        rect.union(this.doodleText.getBoundsInCanvas());
        this.doodleText.setText(this.oldText);
        rect.union(this.doodleText.getBoundsInCanvas());
        doodleView.resetTextOrBitmap(this.doodleText, rect);
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        Rect rect = new Rect();
        rect.union(this.doodleText.getBoundsInCanvas());
        this.doodleText.setText(this.newText);
        rect.union(this.doodleText.getBoundsInCanvas());
        doodleView.resetTextOrBitmap(this.doodleText, rect);
    }
}
