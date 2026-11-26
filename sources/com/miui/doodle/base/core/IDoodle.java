package com.miui.doodle.base.core;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.notes.provider.Notes;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: IDoodle.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0003H&J\u0018\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003H&J\u001a\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020$H&J\u001a\u0010%\u001a\u00020\r2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020$H&J\"\u0010&\u001a\u00020\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\t2\b\b\u0002\u0010#\u001a\u00020$H&J\u0010\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020,H&J\b\u0010;\u001a\u00020\rH&J\b\u0010<\u001a\u00020\rH&J\b\u0010=\u001a\u00020$H&J\u0010\u0010=\u001a\u00020$2\u0006\u0010>\u001a\u00020\tH&J\u0010\u0010?\u001a\u00020$2\u0006\u0010>\u001a\u00020\tH&J\u0014\u0010R\u001a\u00020\r2\n\b\u0002\u0010S\u001a\u0004\u0018\u00010TH&J\u001e\u0010U\u001a\u00020\r2\n\b\u0002\u0010S\u001a\u0004\u0018\u00010T2\b\b\u0002\u0010V\u001a\u00020$H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\u0011\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0005R\u0018\u0010\u0013\u001a\u00020\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0012\u0010\u001c\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005R\u0012\u0010\u001e\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005R\u0012\u0010-\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u000bR\u0012\u0010/\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u000bR\u0018\u00101\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u0018\u00105\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b5\u00102\"\u0004\b6\u00104R\u0018\u00107\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b7\u00102\"\u0004\b8\u00104R\u0018\u00109\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b9\u00102\"\u0004\b:\u00104R&\u0010@\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010B0AX¦\u000e¢\u0006\f\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0014\u0010G\u001a\u0004\u0018\u00010HX¦\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0018\u0010K\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\bL\u0010\u000b\"\u0004\bM\u0010NR\u0018\u0010O\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\bP\u0010\u000b\"\u0004\bQ\u0010N¨\u0006WÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/core/IDoodle;", "", "unitSize", "", "getUnitSize", "()F", "screenDensity", "getScreenDensity", "doodleRotation", "", "getDoodleRotation", "()I", "setDoodleScale", "", "scale", "pivotX", "pivotY", "doodleScale", "getDoodleScale", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "getPen", "()Lcom/miui/doodle/feature/draw/DoodlePen;", "setPen", "(Lcom/miui/doodle/feature/draw/DoodlePen;)V", "setDoodleTranslation", "transX", "transY", "doodleTranslationX", "getDoodleTranslationX", "doodleTranslationY", "getDoodleTranslationY", "addItem", "doodleItem", "Lcom/miui/doodle/base/core/IDoodleItem;", "needSaveToHistory", "", "removeItem", "removeLayerBitmap", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "index", "eraserPathItem", "eraserDot", "Lcom/miui/doodle/document/Dot;", "undoItemCount", "getUndoItemCount", "redoItemCount", "getRedoItemCount", "isDrawableOutside", "()Z", "setDrawableOutside", "(Z)V", "isClipImageOnSave", "setClipImageOnSave", "isCreateNewDoodle", "setCreateNewDoodle", "isFromRebuild", "setFromRebuild", "save", "clear", NotesTextStat.UNDO, "step", NotesTextStat.REDO, "markDoodleOperation", "Lkotlin/Function1;", "Ljava/lang/Void;", "getMarkDoodleOperation", "()Lkotlin/jvm/functions/Function1;", "setMarkDoodleOperation", "(Lkotlin/jvm/functions/Function1;)V", "doodleBitmap", "Landroid/graphics/Bitmap;", "getDoodleBitmap", "()Landroid/graphics/Bitmap;", "doodleWidth", "getDoodleWidth", "setDoodleWidth", "(I)V", "doodleHeight", "getDoodleHeight", "setDoodleHeight", "refresh", Notes.Data.DIRTY, "Landroid/graphics/Rect;", "reset", "fast", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IDoodle {
    void addItem(IDoodleItem doodleItem, boolean needSaveToHistory);

    void clear();

    void eraserPathItem(Dot eraserDot);

    Bitmap getDoodleBitmap();

    int getDoodleHeight();

    int getDoodleRotation();

    float getDoodleScale();

    float getDoodleTranslationX();

    float getDoodleTranslationY();

    int getDoodleWidth();

    Function1<Integer, Void> getMarkDoodleOperation();

    DoodlePen getPen();

    int getRedoItemCount();

    float getScreenDensity();

    int getUndoItemCount();

    float getUnitSize();

    /* JADX INFO: renamed from: isClipImageOnSave */
    boolean getIsClipImageOnSave();

    /* JADX INFO: renamed from: isCreateNewDoodle */
    boolean getIsCreateNewDoodle();

    /* JADX INFO: renamed from: isDrawableOutside */
    boolean getIsDrawableOutside();

    /* JADX INFO: renamed from: isFromRebuild */
    boolean getIsFromRebuild();

    boolean redo(int step);

    void refresh(Rect dirty);

    void removeItem(IDoodleItem doodleItem, boolean needSaveToHistory);

    void removeLayerBitmap(LayerBitmap layerBitmap, int index, boolean needSaveToHistory);

    void reset(Rect dirty, boolean fast);

    void save();

    void setClipImageOnSave(boolean z);

    void setCreateNewDoodle(boolean z);

    void setDoodleHeight(int i);

    void setDoodleScale(float scale, float pivotX, float pivotY);

    void setDoodleTranslation(float transX, float transY);

    void setDoodleWidth(int i);

    void setDrawableOutside(boolean z);

    void setFromRebuild(boolean z);

    void setMarkDoodleOperation(Function1<? super Integer, Void> function1);

    void setPen(DoodlePen doodlePen);

    boolean undo();

    boolean undo(int step);

    /* JADX INFO: compiled from: IDoodle.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
    }

    static /* synthetic */ void addItem$default(IDoodle iDoodle, IDoodleItem iDoodleItem, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addItem");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        iDoodle.addItem(iDoodleItem, z);
    }

    static /* synthetic */ void removeItem$default(IDoodle iDoodle, IDoodleItem iDoodleItem, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: removeItem");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        iDoodle.removeItem(iDoodleItem, z);
    }

    static /* synthetic */ void removeLayerBitmap$default(IDoodle iDoodle, LayerBitmap layerBitmap, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: removeLayerBitmap");
        }
        if ((i2 & 4) != 0) {
            z = true;
        }
        iDoodle.removeLayerBitmap(layerBitmap, i, z);
    }

    static /* synthetic */ void refresh$default(IDoodle iDoodle, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: refresh");
        }
        if ((i & 1) != 0) {
            rect = null;
        }
        iDoodle.refresh(rect);
    }

    static /* synthetic */ void reset$default(IDoodle iDoodle, Rect rect, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reset");
        }
        if ((i & 1) != 0) {
            rect = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        iDoodle.reset(rect, z);
    }
}
