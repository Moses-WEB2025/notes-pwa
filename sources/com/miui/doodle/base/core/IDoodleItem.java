package com.miui.doodle.base.core;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.ItemTransitInfo;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.notes.provider.Notes;
import kotlin.Metadata;
import miuix.android.content.MiuiIntent;
import org.json.JSONObject;

/* JADX INFO: compiled from: IDoodleItem.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J9\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0019H&¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&J\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&J\b\u00103\u001a\u00020\u0013H&J\b\u00104\u001a\u00020\u0013H&J\u0014\u00105\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017H&J\u0010\u00106\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u0003H&J\u0010\u0010<\u001a\u00020\u00132\u0006\u0010=\u001a\u00020>H&J\u0010\u0010?\u001a\u00020\u00132\u0006\u0010=\u001a\u00020>H&J\b\u0010@\u001a\u00020\u0013H&J\b\u0010A\u001a\u00020\u0013H&J\u0010\u0010K\u001a\u00020\u00132\u0006\u0010L\u001a\u00020CH&J\b\u0010M\u001a\u00020\u0013H&J\b\u0010N\u001a\u00020\u0013H&J\b\u0010O\u001a\u00020\u0013H&J\b\u0010P\u001a\u00020\u0000H&J\b\u0010Q\u001a\u00020RH&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u001e\u001a\u00020\u001fX¦\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020\u001fX¦\u000e¢\u0006\f\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#R\u0018\u0010'\u001a\u00020(X¦\u000e¢\u0006\f\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0018\u0010-\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0018\u00101\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b1\u0010.\"\u0004\b2\u00100R\u0012\u00108\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u0010.R\u0018\u00109\u001a\u00020(X¦\u000e¢\u0006\f\u001a\u0004\b:\u0010*\"\u0004\b;\u0010,R\u001a\u0010B\u001a\u0004\u0018\u00010CX¦\u000e¢\u0006\f\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010H\u001a\u0004\u0018\u00010CX¦\u000e¢\u0006\f\u001a\u0004\bI\u0010E\"\u0004\bJ\u0010GR\u0018\u0010S\u001a\u00020TX¦\u000e¢\u0006\f\u001a\u0004\bU\u0010V\"\u0004\bW\u0010X¨\u0006YÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/core/IDoodleItem;", "", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "getDoodle", "()Lcom/miui/doodle/base/core/IDoodle;", "setDoodle", "(Lcom/miui/doodle/base/core/IDoodle;)V", "id", "", "getId", "()Ljava/lang/String;", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "getPen", "()Lcom/miui/doodle/feature/draw/DoodlePen;", "setPen", "(Lcom/miui/doodle/feature/draw/DoodlePen;)V", "draw", "", "canvas", "Landroid/graphics/Canvas;", Notes.Data.DIRTY, "Landroid/graphics/Rect;", "isElementRedraw", "", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", MiuiIntent.COMMAND_ICON_PANEL_HIDE, "drawAtTheTop", "location", "Landroid/graphics/PointF;", "getLocation", "()Landroid/graphics/PointF;", "setLocation", "(Landroid/graphics/PointF;)V", "pivotPoint", "getPivotPoint", "setPivotPoint", "itemRotate", "", "getItemRotate", "()F", "setItemRotate", "(F)V", "isNeedClipOutside", "()Z", "setNeedClipOutside", "(Z)V", "isDirty", "setDirty", "onAdd", "onRemove", "refresh", "refreshDoodle", "newDoodle", "isDoodleEditable", "itemScale", "getItemScale", "setItemScale", "addItemListener", "listener", "Lcom/miui/doodle/base/core/IDoodleItemListener;", "removeItemListener", "clear", "cache", "transitInfoBeforeChanged", "Lcom/miui/doodle/base/ItemTransitInfo;", "getTransitInfoBeforeChanged", "()Lcom/miui/doodle/base/ItemTransitInfo;", "setTransitInfoBeforeChanged", "(Lcom/miui/doodle/base/ItemTransitInfo;)V", "transitInfoAfterChanged", "getTransitInfoAfterChanged", "setTransitInfoAfterChanged", "updateTransitInfo", "newInfo", "cacheTransitInfoBeforeChanged", "cacheTransitInfoAfterChanged", "releaseCacheTransitInfo", "copy", "toJSONObject", "Lorg/json/JSONObject;", "alpha", "", "getAlpha", "()I", "setAlpha", "(I)V", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IDoodleItem {
    void addItemListener(IDoodleItemListener listener);

    void cache();

    void cacheTransitInfoAfterChanged();

    void cacheTransitInfoBeforeChanged();

    void clear();

    IDoodleItem copy();

    void draw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView);

    void drawAtTheTop(Canvas canvas);

    int getAlpha();

    IDoodle getDoodle();

    String getId();

    float getItemRotate();

    float getItemScale();

    PointF getLocation();

    DoodlePen getPen();

    PointF getPivotPoint();

    ItemTransitInfo getTransitInfoAfterChanged();

    ItemTransitInfo getTransitInfoBeforeChanged();

    void hide(Canvas canvas);

    boolean isDirty();

    boolean isDoodleEditable();

    boolean isNeedClipOutside();

    void onAdd();

    void onRemove();

    void refresh(Rect dirty);

    void refreshDoodle(IDoodle newDoodle);

    void releaseCacheTransitInfo();

    void removeItemListener(IDoodleItemListener listener);

    void setAlpha(int i);

    void setDirty(boolean z);

    void setDoodle(IDoodle iDoodle);

    void setItemRotate(float f);

    void setItemScale(float f);

    void setLocation(PointF pointF);

    void setNeedClipOutside(boolean z);

    void setPen(DoodlePen doodlePen);

    void setPivotPoint(PointF pointF);

    void setTransitInfoAfterChanged(ItemTransitInfo itemTransitInfo);

    void setTransitInfoBeforeChanged(ItemTransitInfo itemTransitInfo);

    JSONObject toJSONObject();

    void updateTransitInfo(ItemTransitInfo newInfo);

    /* JADX INFO: compiled from: IDoodleItem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
    }

    static /* synthetic */ void draw$default(IDoodleItem iDoodleItem, Canvas canvas, Rect rect, Boolean bool, Boolean bool2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: draw");
        }
        if ((i & 2) != 0) {
            rect = null;
        }
        if ((i & 4) != 0) {
            bool = false;
        }
        if ((i & 8) != 0) {
            bool2 = false;
        }
        iDoodleItem.draw(canvas, rect, bool, bool2);
    }

    static /* synthetic */ void refresh$default(IDoodleItem iDoodleItem, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: refresh");
        }
        if ((i & 1) != 0) {
            rect = null;
        }
        iDoodleItem.refresh(rect);
    }
}
