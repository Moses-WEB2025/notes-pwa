package com.miui.doodle.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleSelectableItem;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.UIUtils;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: DoodleSelectableItemBase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000 92\u00020\u00012\u00020\u0002:\u00019B?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\nH\u0016J\u0010\u0010(\u001a\u00020$2\u0006\u0010+\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0014J\u0010\u00101\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0014J\u0010\u00102\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00103\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00107\u001a\u00020.2\u0006\u0010+\u001a\u00020\u0019H\u0004J\u0010\u00108\u001a\u00020.2\u0006\u0010+\u001a\u00020\u0019H$R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u000b\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010%R\u0011\u0010&\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b'\u0010\u001bR$\u00104\u001a\u00020$2\u0006\u00104\u001a\u00020$@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010%\"\u0004\b5\u00106¨\u0006:"}, d2 = {"Lcom/miui/doodle/base/DoodleSelectableItemBase;", "Lcom/miui/doodle/base/DoodleItemBase;", "Lcom/miui/doodle/base/core/IDoodleSelectableItem;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "id", "", "itemRotate", "", "itemScale", "location", "Landroid/graphics/PointF;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Lcom/miui/doodle/feature/draw/DoodlePen;Ljava/lang/String;FFLandroid/graphics/PointF;)V", "getItemRotate", "()F", "setItemRotate", "(F)V", "getLocation", "()Landroid/graphics/PointF;", "setLocation", "(Landroid/graphics/PointF;)V", "bounds", "Landroid/graphics/Rect;", "getBounds", "()Landroid/graphics/Rect;", "rectTemp", "paint", "Landroid/graphics/Paint;", FilePathConstant.TEMP, "scale", "getItemScale", "setItemScale", "isDoodleEditable", "", "()Z", "boundsInCanvas", "getBoundsInCanvas", "contains", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "rect", "Landroid/graphics/RectF;", "drawBefore", "", "canvas", "Landroid/graphics/Canvas;", "drawAfter", "drawAtTheTop", "doDrawAtTheTop", "isSelected", "setSelected", "(Z)V", "resetBoundsScaled", "resetBounds", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class DoodleSelectableItemBase extends DoodleItemBase implements IDoodleSelectableItem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int ITEM_CAN_ROTATE_BOUND = 35;
    public static final int ITEM_HEIGHT_PADDING = 20;
    public static final int ITEM_WIDTH_PADDING = 20;
    private final Rect bounds;
    private boolean isSelected;
    private float itemRotate;
    private float itemScale;
    private PointF location;
    private final Paint paint;
    private final Rect rectTemp;
    private PointF temp;

    @Override // com.miui.doodle.base.DoodleItemBase
    protected void drawAfter(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.miui.doodle.base.DoodleItemBase
    protected void drawBefore(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public boolean isDoodleEditable() {
        return true;
    }

    protected abstract void resetBounds(Rect rect);

    public /* synthetic */ DoodleSelectableItemBase(IDoodle iDoodle, DoodlePen doodlePen, String str, float f, float f2, PointF pointF, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, doodlePen, (i & 4) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i & 8) != 0 ? 0.0f : f, (i & 16) != 0 ? 1.0f : f2, (i & 32) != 0 ? new PointF() : pointF);
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public float getItemRotate() {
        return this.itemRotate;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void setItemRotate(float f) {
        this.itemRotate = f;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public PointF getLocation() {
        return this.location;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void setLocation(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.location = pointF;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleSelectableItemBase(IDoodle doodle, DoodlePen pen, String id, float f, float f2, PointF location) {
        super(doodle, id, pen);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(pen, "pen");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        this.itemRotate = f;
        this.location = location;
        this.bounds = new Rect();
        this.rectTemp = new Rect();
        this.paint = new Paint();
        this.temp = new PointF();
        this.itemScale = f2;
    }

    @Override // com.miui.doodle.base.core.IDoodleSelectableItem
    public final Rect getBounds() {
        return this.bounds;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public final float getItemScale() {
        return this.itemScale;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public final void setItemScale(float f) {
        this.itemScale = f;
        resetBoundsScaled(this.bounds);
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    public final Rect getBoundsInCanvas() {
        Rect rect = new Rect();
        resetBoundsScaled(this.bounds);
        rect.set(this.bounds);
        if (getItemRotate() != 0.0f) {
            float f = 1;
            float f2 = rect.left - f;
            float f3 = rect.top - f;
            float f4 = rect.right + f;
            float f5 = rect.bottom + f;
            PointF pointF = new PointF();
            PointF pointF2 = new PointF();
            PointF pointF3 = new PointF();
            PointF pointF4 = new PointF();
            DrawUtil.INSTANCE.rotatePoint(pointF, getItemRotate(), f2, f3, getPivotPoint().x, getPivotPoint().y);
            DrawUtil.INSTANCE.rotatePoint(pointF2, getItemRotate(), f2, f5, getPivotPoint().x, getPivotPoint().y);
            DrawUtil.INSTANCE.rotatePoint(pointF3, getItemRotate(), f4, f3, getPivotPoint().x, getPivotPoint().y);
            DrawUtil.INSTANCE.rotatePoint(pointF4, getItemRotate(), f4, f5, getPivotPoint().x, getPivotPoint().y);
            rect.left = (int) Math.min(Math.min(pointF.x, pointF2.x), Math.min(pointF3.x, pointF4.x));
            rect.top = (int) Math.min(Math.min(pointF.y, pointF2.y), Math.min(pointF3.y, pointF4.y));
            rect.right = (int) Math.max(Math.max(pointF.x, pointF2.x), Math.max(pointF3.x, pointF4.x));
            rect.bottom = (int) Math.max(Math.max(pointF.y, pointF2.y), Math.max(pointF3.y, pointF4.y));
        }
        rect.left--;
        rect.top--;
        rect.right++;
        rect.bottom++;
        rect.offset((int) (getLocation().x + 0.5f), (int) (getLocation().y + 0.5f));
        return rect;
    }

    @Override // com.miui.doodle.base.core.IDoodleSelectableItem
    public boolean contains(float x, float y) {
        float f = x - getLocation().x;
        float f2 = y - getLocation().y;
        float f3 = getPivotPoint().x;
        float f4 = getPivotPoint().y;
        resetBoundsScaled(this.bounds);
        DrawUtil.INSTANCE.rotatePoint(this.temp, -getItemRotate(), f, f2, f3, f4);
        this.rectTemp.set(this.bounds);
        int unitSize = ((int) getDoodle().getUnitSize()) * 20;
        this.rectTemp.left -= unitSize;
        this.rectTemp.top -= unitSize;
        this.rectTemp.right += unitSize;
        this.rectTemp.bottom += unitSize;
        return this.rectTemp.contains((int) this.temp.x, (int) this.temp.y);
    }

    @Override // com.miui.doodle.base.core.IDoodleSelectableItem
    public boolean contains(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Rect rect2 = new Rect();
        resetBoundsScaled(rect2);
        return UIUtils.INSTANCE.detectIntersect(rect, new RectF(rect2));
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void drawAtTheTop(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float f = getLocation().x;
        float f2 = getLocation().y;
        int iSave = canvas.save();
        canvas.translate(f, f2);
        try {
            float itemRotate = getItemRotate();
            float f3 = getPivotPoint().x;
            float f4 = getPivotPoint().y;
            iSave = canvas.save();
            canvas.rotate(itemRotate, f3, f4);
            try {
                doDrawAtTheTop(canvas);
                canvas.restoreToCount(iSave);
            } finally {
                canvas.restoreToCount(iSave);
            }
        } catch (Throwable th) {
            canvas.restoreToCount(iSave);
            throw th;
        }
    }

    public void doDrawAtTheTop(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getIsSelected()) {
            float f = getPivotPoint().x;
            float f2 = getPivotPoint().y;
            float doodleScale = 1 / getDoodle().getDoodleScale();
            int iSave = canvas.save();
            canvas.scale(doodleScale, doodleScale, f, f2);
            try {
                resetBoundsScaled(this.rectTemp);
                DrawUtil.INSTANCE.scaleRect(this.rectTemp, getDoodle().getDoodleScale(), f, f2);
                int unitSize = (int) getDoodle().getUnitSize();
                int i = unitSize * 20;
                this.rectTemp.left -= i;
                this.rectTemp.top -= i;
                this.rectTemp.right += i;
                this.rectTemp.bottom += i;
                this.paint.setShader(null);
                this.paint.setColor(8947848);
                this.paint.setStyle(Paint.Style.FILL);
                this.paint.setStrokeWidth(1.0f);
                canvas.drawRect(this.rectTemp, this.paint);
                this.paint.setColor(-1996488705);
                this.paint.setStyle(Paint.Style.STROKE);
                float f3 = unitSize;
                this.paint.setStrokeWidth(2.0f * f3);
                canvas.drawRect(this.rectTemp, this.paint);
                this.paint.setColor(1149798536);
                this.paint.setStrokeWidth(f3 * 0.8f);
                canvas.drawRect(this.rectTemp, this.paint);
            } finally {
                canvas.restoreToCount(iSave);
            }
        }
    }

    @Override // com.miui.doodle.base.core.IDoodleSelectableItem
    /* JADX INFO: renamed from: isSelected, reason: from getter */
    public boolean getIsSelected() {
        return this.isSelected;
    }

    @Override // com.miui.doodle.base.core.IDoodleSelectableItem
    public void setSelected(boolean z) {
        this.isSelected = z;
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    protected final void resetBoundsScaled(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        resetBounds(rect);
        DrawUtil.INSTANCE.scaleRect(rect, this.itemScale, getPivotPoint().x, getPivotPoint().y);
    }

    /* JADX INFO: compiled from: DoodleSelectableItemBase.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JN\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/doodle/base/DoodleSelectableItemBase$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ITEM_CAN_ROTATE_BOUND", "", "ITEM_WIDTH_PADDING", "ITEM_HEIGHT_PADDING", "createBaseItem", "", "id", "", "hasAdded", "", "locationX", "", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/base/DoodleSelectableItemBase;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createBaseItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, DoodleSelectableItemBase item) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(item, "item");
            DoodleItemBase.INSTANCE.createBaseItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, item);
        }
    }
}
