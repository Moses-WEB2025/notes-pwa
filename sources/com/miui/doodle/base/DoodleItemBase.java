package com.miui.doodle.base;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleItemListener;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.notes.provider.Notes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import miui.cloud.CloudPushConstants;
import miuix.android.content.MiuiIntent;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleItemBase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\b&\u0018\u0000 h2\u00020\u00012\u00020\u0002:\u0001hB!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\b\u00104\u001a\u000205H\u0016J3\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\b\u0010:\u001a\u0004\u0018\u00010;2\b\u0010<\u001a\u0004\u0018\u00010\u00172\b\u0010=\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010>J\u0010\u0010?\u001a\u0002072\u0006\u00108\u001a\u000209H\u0016J\b\u0010@\u001a\u000207H\u0016J\b\u0010A\u001a\u000207H\u0016J\u0012\u0010B\u001a\u0002072\b\u0010:\u001a\u0004\u0018\u00010;H\u0016J\u0010\u0010C\u001a\u0002072\u0006\u0010D\u001a\u00020\u0004H\u0016J\u0010\u0010O\u001a\u0002072\u0006\u0010P\u001a\u00020\u0002H\u0016J\u0010\u0010Q\u001a\u0002072\u0006\u0010P\u001a\u00020\u0002H\u0016J\u0010\u0010R\u001a\u0002072\u0006\u0010S\u001a\u00020TH\u0016J\u0010\u0010U\u001a\u0002072\u0006\u00108\u001a\u000209H\u0014J9\u0010V\u001a\u0002072\u0006\u00108\u001a\u0002092\n\b\u0002\u0010:\u001a\u0004\u0018\u00010;2\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u0017H$¢\u0006\u0002\u0010>J\u0010\u0010W\u001a\u0002072\u0006\u00108\u001a\u000209H\u0014J\u0010\u0010X\u001a\u0002072\u0006\u00108\u001a\u000209H\u0016J\b\u0010Y\u001a\u000207H\u0016J\u0010\u0010c\u001a\u0002072\u0006\u0010d\u001a\u00020[H\u0016J\b\u0010e\u001a\u000207H\u0016J\b\u0010f\u001a\u000207H\u0016J\b\u0010g\u001a\u000207H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001dR$\u0010 \u001a\u00020!2\u0006\u0010 \u001a\u00020!@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R$\u0010&\u001a\u00020!2\u0006\u0010&\u001a\u00020!@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010#\"\u0004\b(\u0010%R$\u0010+\u001a\u00020*2\u0006\u0010)\u001a\u00020*@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R$\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u0014\u0010E\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010\u001bR$\u0010G\u001a\u00020*2\u0006\u0010F\u001a\u00020*@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010-\"\u0004\bI\u0010/R$\u0010J\u001a\u00020*2\u0006\u0010J\u001a\u00020*@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010-\"\u0004\bL\u0010/R\u001e\u0010M\u001a\u00020*2\u0006\u0010M\u001a\u00020*@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\bN\u0010/R\u001c\u0010Z\u001a\u0004\u0018\u00010[X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001c\u0010`\u001a\u0004\u0018\u00010[X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010]\"\u0004\bb\u0010_¨\u0006i"}, d2 = {"Lcom/miui/doodle/base/DoodleItemBase;", "Lcom/miui/doodle/base/core/IDoodleItem;", "Lcom/miui/doodle/base/core/IDoodleItemListener;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "id", "", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Ljava/lang/String;Lcom/miui/doodle/feature/draw/DoodlePen;)V", "getDoodle", "()Lcom/miui/doodle/base/core/IDoodle;", "setDoodle", "(Lcom/miui/doodle/base/core/IDoodle;)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "cacheImageKey", "getCacheImageKey", "setCacheImageKey", "hasAdded", "", "itemListeners", "", "isDirty", "()Z", "setDirty", "(Z)V", "isNeedClipOutside", "setNeedClipOutside", "location", "Landroid/graphics/PointF;", "getLocation", "()Landroid/graphics/PointF;", "setLocation", "(Landroid/graphics/PointF;)V", "pivotPoint", "getPivotPoint", "setPivotPoint", "textRotate", "", "itemRotate", "getItemRotate", "()F", "setItemRotate", "(F)V", "getPen", "()Lcom/miui/doodle/feature/draw/DoodlePen;", "setPen", "(Lcom/miui/doodle/feature/draw/DoodlePen;)V", "toJSONObject", "Lorg/json/JSONObject;", "draw", "", "canvas", "Landroid/graphics/Canvas;", Notes.Data.DIRTY, "Landroid/graphics/Rect;", "isElementRedraw", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", MiuiIntent.COMMAND_ICON_PANEL_HIDE, "onAdd", "onRemove", "refresh", "refreshDoodle", "newDoodle", "isDoodleEditable", "scale", "itemScale", "getItemScale", "setItemScale", "minScale", "getMinScale", "setMinScale", "maxScale", "setMaxScale", "addItemListener", "listener", "removeItemListener", "onPropertyChanged", "property", "", "drawBefore", "doDraw", "drawAfter", "drawAtTheTop", "clear", "transitInfoBeforeChanged", "Lcom/miui/doodle/base/ItemTransitInfo;", "getTransitInfoBeforeChanged", "()Lcom/miui/doodle/base/ItemTransitInfo;", "setTransitInfoBeforeChanged", "(Lcom/miui/doodle/base/ItemTransitInfo;)V", "transitInfoAfterChanged", "getTransitInfoAfterChanged", "setTransitInfoAfterChanged", "updateTransitInfo", "newInfo", "cacheTransitInfoBeforeChanged", "cacheTransitInfoAfterChanged", "releaseCacheTransitInfo", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class DoodleItemBase implements IDoodleItem, IDoodleItemListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String KEY_COLOR = "color";
    public static final String KEY_HAS_ADD = "has_add";
    public static final String KEY_ID = "id";
    public static final String KEY_ITEM_ROTATION = "item_rotation";
    public static final String KEY_ITEM_SCALE = "item_scale";
    public static final String KEY_LOCATION_X = "location_x";
    public static final String KEY_LOCATION_Y = "location_y";
    public static final String KEY_PIVOT_X = "pivot_x";
    public static final String KEY_PIVOT_Y = "pivot_y";
    public static final String KEY_ROTATION = "rotation";
    public static final String KEY_SCALE = "scale";
    public static final String KEY_SIZE = "size";
    public static final String KEY_TYPE = "type";
    public static final float MAX_SCALE = 100.0f;
    public static final float MIN_SCALE = 0.01f;
    public static final int TYPE_HAND_WRITING = 0;
    public static final int TYPE_IMAGE = 11;
    public static final int TYPE_SHAPE = 13;
    public static final int TYPE_TEXT = 12;
    private String cacheImageKey;
    private IDoodle doodle;
    private boolean hasAdded;
    private String id;
    private boolean isDirty;
    private boolean isNeedClipOutside;
    private final List<IDoodleItemListener> itemListeners;
    private float itemRotate;
    private float itemScale;
    private PointF location;
    private float maxScale;
    private float minScale;
    private DoodlePen pen;
    private PointF pivotPoint;
    private ItemTransitInfo transitInfoAfterChanged;
    private ItemTransitInfo transitInfoBeforeChanged;

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void clear() {
    }

    protected abstract void doDraw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView);

    protected void drawAfter(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void drawAtTheTop(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    protected void drawBefore(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public boolean isDoodleEditable() {
        return false;
    }

    public DoodleItemBase(IDoodle doodle, String id, DoodlePen pen) {
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(pen, "pen");
        this.doodle = doodle;
        this.id = id;
        this.itemListeners = new ArrayList();
        this.isNeedClipOutside = true;
        this.location = new PointF();
        this.pivotPoint = new PointF();
        this.pen = pen.copy();
        this.itemScale = 1.0f;
        this.minScale = 0.01f;
        this.maxScale = 100.0f;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public IDoodle getDoodle() {
        return this.doodle;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setDoodle(IDoodle iDoodle) {
        Intrinsics.checkNotNullParameter(iDoodle, "<set-?>");
        this.doodle = iDoodle;
    }

    public /* synthetic */ DoodleItemBase(IDoodle iDoodle, String str, DoodlePen doodlePen, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, (i & 2) != 0 ? INSTANCE.generateId() : str, doodlePen);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getCacheImageKey() {
        return this.cacheImageKey;
    }

    public final void setCacheImageKey(String str) {
        this.cacheImageKey = str;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    /* JADX INFO: renamed from: isDirty, reason: from getter */
    public boolean getIsDirty() {
        return this.isDirty;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setDirty(boolean z) {
        this.isDirty = z;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    /* JADX INFO: renamed from: isNeedClipOutside, reason: from getter */
    public boolean getIsNeedClipOutside() {
        return this.isNeedClipOutside;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setNeedClipOutside(boolean z) {
        this.isNeedClipOutside = z;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public PointF getLocation() {
        return this.location;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setLocation(PointF location) {
        Intrinsics.checkNotNullParameter(location, "location");
        this.location = location;
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public PointF getPivotPoint() {
        return this.pivotPoint;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setPivotPoint(PointF pivotPoint) {
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.pivotPoint = pivotPoint;
        onPropertyChanged(3);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public float getItemRotate() {
        return this.itemRotate;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setItemRotate(float f) {
        this.itemRotate = f;
        onPropertyChanged(2);
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public DoodlePen getPen() {
        return this.pen;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setPen(DoodlePen pen) {
        Intrinsics.checkNotNullParameter(pen, "pen");
        this.pen = pen.copy();
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put(KEY_HAS_ADD, this.hasAdded);
        jSONObject.put(KEY_LOCATION_X, Float.valueOf(getLocation().x));
        jSONObject.put(KEY_LOCATION_Y, Float.valueOf(getLocation().y));
        jSONObject.put(KEY_PIVOT_X, Float.valueOf(getPivotPoint().x));
        jSONObject.put(KEY_PIVOT_Y, Float.valueOf(getPivotPoint().y));
        jSONObject.put(KEY_ITEM_ROTATION, Float.valueOf(getItemRotate()));
        jSONObject.put(KEY_ITEM_SCALE, Float.valueOf(getItemScale()));
        return jSONObject;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void draw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        drawBefore(canvas);
        float f = getLocation().x;
        float f2 = getLocation().y;
        int iSave = canvas.save();
        canvas.translate(f, f2);
        try {
            float itemRotate = getItemRotate();
            float f3 = getPivotPoint().x;
            float f4 = getPivotPoint().y;
            int iSave2 = canvas.save();
            canvas.rotate(itemRotate, f3, f4);
            try {
                float itemScale = getItemScale();
                float itemScale2 = getItemScale();
                float f5 = getPivotPoint().x;
                float f6 = getPivotPoint().y;
                int iSave3 = canvas.save();
                canvas.scale(itemScale, itemScale2, f5, f6);
                try {
                    doDraw(canvas, dirty, isElementRedraw, isDrawToView);
                    canvas.restoreToCount(iSave3);
                    canvas.restoreToCount(iSave2);
                    canvas.restoreToCount(iSave);
                    drawAfter(canvas);
                } catch (Throwable th) {
                    canvas.restoreToCount(iSave3);
                    throw th;
                }
            } catch (Throwable th2) {
                canvas.restoreToCount(iSave2);
                throw th2;
            }
        } catch (Throwable th3) {
            canvas.restoreToCount(iSave);
            throw th3;
        }
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void hide(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        drawBefore(canvas);
        int iSave = canvas.save();
        float f = getLocation().x;
        float f2 = getLocation().y;
        int iSave2 = canvas.save();
        canvas.translate(f, f2);
        try {
            float f3 = getPivotPoint().x;
            float f4 = getPivotPoint().y;
            float itemRotate = getItemRotate();
            int iSave3 = canvas.save();
            canvas.rotate(itemRotate, f3, f4);
            try {
                float itemScale = getItemScale();
                float itemScale2 = getItemScale();
                int iSave4 = canvas.save();
                canvas.scale(itemScale, itemScale2, f3, f4);
                try {
                    doDraw$default(this, canvas, null, null, null, 14, null);
                    canvas.restoreToCount(iSave4);
                    canvas.restoreToCount(iSave3);
                    canvas.restoreToCount(iSave);
                    canvas.restoreToCount(iSave2);
                    drawAfter(canvas);
                } catch (Throwable th) {
                    canvas.restoreToCount(iSave4);
                    throw th;
                }
            } catch (Throwable th2) {
                canvas.restoreToCount(iSave3);
                throw th2;
            }
        } catch (Throwable th3) {
            canvas.restoreToCount(iSave2);
            throw th3;
        }
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void onAdd() {
        this.hasAdded = true;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void onRemove() {
        this.hasAdded = false;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void refresh(Rect dirty) {
        getDoodle().refresh(dirty);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void refreshDoodle(IDoodle newDoodle) {
        Intrinsics.checkNotNullParameter(newDoodle, "newDoodle");
        setDoodle(newDoodle);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public float getItemScale() {
        return this.itemScale;
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0006 A[PHI: r0
  0x0006: PHI (r0v3 float) = (r0v0 float), (r0v1 float) binds: [B:3:0x0004, B:6:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setItemScale(float f) {
        float f2 = this.minScale;
        if (f <= f2) {
            f = f2;
        } else {
            f2 = this.maxScale;
            if (f > f2) {
                f = f2;
            }
        }
        this.itemScale = f;
        onPropertyChanged(1);
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    public final float getMinScale() {
        return this.minScale;
    }

    public final void setMinScale(float f) {
        if (f <= 0.0f) {
            f = 0.01f;
        } else {
            float f2 = this.maxScale;
            if (f > f2) {
                f = f2;
            }
        }
        this.minScale = f;
        setItemScale(getItemScale());
    }

    private final void setMaxScale(float f) {
        if (f <= 0.0f) {
            f = 0.01f;
        } else {
            float f2 = this.minScale;
            if (f < f2) {
                f = f2;
            }
        }
        this.maxScale = f;
        setItemScale(getItemScale());
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void addItemListener(IDoodleItemListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (this.itemListeners.contains(listener)) {
            return;
        }
        this.itemListeners.add(listener);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void removeItemListener(IDoodleItemListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.itemListeners.remove(listener);
    }

    @Override // com.miui.doodle.base.core.IDoodleItemListener
    public void onPropertyChanged(int property) {
        int size = this.itemListeners.size();
        for (int i = 0; i < size; i++) {
            this.itemListeners.get(i).onPropertyChanged(property);
        }
    }

    public static /* synthetic */ void doDraw$default(DoodleItemBase doodleItemBase, Canvas canvas, Rect rect, Boolean bool, Boolean bool2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: doDraw");
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
        doodleItemBase.doDraw(canvas, rect, bool, bool2);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public ItemTransitInfo getTransitInfoBeforeChanged() {
        return this.transitInfoBeforeChanged;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setTransitInfoBeforeChanged(ItemTransitInfo itemTransitInfo) {
        this.transitInfoBeforeChanged = itemTransitInfo;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public ItemTransitInfo getTransitInfoAfterChanged() {
        return this.transitInfoAfterChanged;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setTransitInfoAfterChanged(ItemTransitInfo itemTransitInfo) {
        this.transitInfoAfterChanged = itemTransitInfo;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void updateTransitInfo(ItemTransitInfo newInfo) {
        Intrinsics.checkNotNullParameter(newInfo, "newInfo");
        setItemScale(newInfo.getScale());
        getLocation().x = newInfo.getLocation().x;
        getLocation().y = newInfo.getLocation().y;
        setItemRotate(newInfo.getRotation());
        setAlpha(newInfo.getAlpha());
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cacheTransitInfoBeforeChanged() {
        setTransitInfoBeforeChanged(new ItemTransitInfo(getItemScale(), new PointF(getLocation().x, getLocation().y), getItemRotate(), getAlpha()));
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cacheTransitInfoAfterChanged() {
        setTransitInfoAfterChanged(new ItemTransitInfo(getItemScale(), new PointF(getLocation().x, getLocation().y), getItemRotate(), getAlpha()));
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void releaseCacheTransitInfo() {
        setTransitInfoBeforeChanged(null);
        setTransitInfoAfterChanged(null);
    }

    /* JADX INFO: compiled from: DoodleItemBase.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001a\u001a\u00020\bJN\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0016X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0086T¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/miui/doodle/base/DoodleItemBase$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MIN_SCALE", "", "MAX_SCALE", "KEY_ID", "", "KEY_HAS_ADD", "KEY_LOCATION_X", "KEY_LOCATION_Y", "KEY_PIVOT_X", "KEY_PIVOT_Y", "KEY_ITEM_ROTATION", "KEY_ITEM_SCALE", "KEY_TYPE", "KEY_COLOR", "KEY_SIZE", "KEY_SCALE", "KEY_ROTATION", "TYPE_HAND_WRITING", "", "TYPE_IMAGE", "TYPE_TEXT", "TYPE_SHAPE", "generateId", "createBaseItem", "", "id", "hasAdded", "", "locationX", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/base/DoodleItemBase;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String generateId() {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return new Regex("-").replace(string, "");
        }

        public final void createBaseItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, DoodleItemBase item) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(item, "item");
            item.setId(id);
            item.hasAdded = hasAdded;
            item.getLocation().set(locationX, locationY);
            item.getPivotPoint().set(pivotX, pivotY);
            item.setItemRotate(itemRotate);
            item.setItemScale(itemScale);
        }
    }
}
