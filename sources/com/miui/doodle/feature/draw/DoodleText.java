package com.miui.doodle.feature.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.DoodleRotatableItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.notes.provider.Notes;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleText.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 02\u00020\u0001:\u00010Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007¢\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J3\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010 2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010'\u001a\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\tJ\b\u0010,\u001a\u00020\u0000H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020\u001eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u00061"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleText;", "Lcom/miui/doodle/base/DoodleRotatableItemBase;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "text", "", "textColor", "", "textSize", "", "id", "itemRotate", "itemScale", "location", "Landroid/graphics/PointF;", "pivotPoint", "alpha", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Ljava/lang/String;IFLjava/lang/String;FFLandroid/graphics/PointF;Landroid/graphics/PointF;I)V", "getAlpha", "()I", "setAlpha", "(I)V", "paint", "Landroid/graphics/Paint;", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "resetBounds", "", "rect", "Landroid/graphics/Rect;", "doDraw", "canvas", "Landroid/graphics/Canvas;", Notes.Data.DIRTY, "isElementRedraw", "", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "restLocation", "offsetX", "offsetY", "copy", "toJSONObject", "Lorg/json/JSONObject;", "cache", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleText extends DoodleRotatableItemBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String KEY_TEXT = "text";
    public static final String KEY_TEXT_COLOR = "text_color";
    public static final String KEY_TEXT_SIZE = "text_size";
    private int alpha;
    private final Paint paint;
    private String text;
    private int textColor;
    private float textSize;

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cache() {
    }

    public /* synthetic */ DoodleText(IDoodle iDoodle, String str, int i, float f, String str2, float f2, float f3, PointF pointF, PointF pointF2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0.0f : f, (i3 & 16) != 0 ? DoodleItemBase.INSTANCE.generateId() : str2, (i3 & 32) == 0 ? f2 : 0.0f, (i3 & 64) != 0 ? 1.0f : f3, (i3 & 128) != 0 ? new PointF() : pointF, (i3 & 256) != 0 ? new PointF() : pointF2, (i3 & 512) != 0 ? 255 : i2);
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public int getAlpha() {
        return this.alpha;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setAlpha(int i) {
        this.alpha = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleText(IDoodle doodle, String text, int i, float f, String id, float f2, float f3, PointF location, PointF pivotPoint, int i2) {
        super(doodle, DoodlePenManager.INSTANCE.getText(), id, f2, f3, location, pivotPoint);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.textColor = i;
        this.textSize = f;
        this.alpha = i2;
        this.paint = new Paint();
        this.text = "";
        setText(text);
    }

    public final String getText() {
        return this.text;
    }

    public final void setText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
        resetBoundsScaled(getBounds());
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase
    public void resetBounds(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        if (TextUtils.isEmpty(this.text)) {
            return;
        }
        this.paint.setAntiAlias(true);
        this.paint.setTextSize(this.textSize);
        this.paint.setStyle(Paint.Style.FILL);
        Paint paint = this.paint;
        String str = this.text;
        paint.getTextBounds(str, 0, str.length(), rect);
        rect.offset(0, rect.height());
    }

    @Override // com.miui.doodle.base.DoodleItemBase
    public void doDraw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.paint.setAntiAlias(true);
        this.paint.setColor(this.textColor);
        this.paint.setAlpha((int) (getAlpha() * getPen().getColorPatchAlpha()));
        this.paint.setTextSize(this.textSize);
        this.paint.setStyle(Paint.Style.FILL);
        float fHeight = getBounds().height() / getItemScale();
        int iSave = canvas.save();
        canvas.translate(0.0f, fHeight);
        try {
            canvas.drawText(this.text, 0.0f, 0.0f, this.paint);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public final void restLocation(float offsetX, float offsetY) {
        getLocation().x += offsetX;
        getLocation().y += offsetY;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public DoodleText copy() {
        DoodleText doodleText = new DoodleText(getDoodle(), this.text, 0, this.textSize, null, getItemRotate(), getItemScale(), new PointF(getLocation().x, getLocation().y), new PointF(getPivotPoint().x, getPivotPoint().y), 0, R2.attr.buttonBackgroundWarning, null);
        doodleText.getBounds().set(getBounds());
        return doodleText;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = super.toJSONObject();
        jSONObject.put("text", this.text);
        jSONObject.put(KEY_TEXT_SIZE, Float.valueOf(this.textSize));
        jSONObject.put(KEY_TEXT_COLOR, this.textColor);
        jSONObject.put("type", 12);
        return jSONObject;
    }

    /* JADX INFO: compiled from: DoodleText.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Jf\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleText$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_TEXT", "", "KEY_TEXT_SIZE", "KEY_TEXT_COLOR", "createTextItem", "", "id", "hasAdded", "", "locationX", "", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/feature/draw/DoodleText;", "text", "textSize", "textColor", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createTextItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, DoodleText item, String text, float textSize, int textColor) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(item, "item");
            Intrinsics.checkNotNullParameter(text, "text");
            DoodleRotatableItemBase.INSTANCE.createBaseItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, item);
            float f = item.getPivotPoint().x;
            float f2 = item.getPivotPoint().y;
            item.textSize = textSize;
            item.textColor = textColor;
            item.setText(text);
            item.getPivotPoint().set(f, f2);
        }
    }
}
