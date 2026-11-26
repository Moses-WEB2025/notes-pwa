package com.miui.doodle.feature.draw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.DoodleRotatableItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.Utils;
import com.miui.notes.provider.Notes;
import defpackage.R2;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miui.cloud.CloudPushConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleBitmap.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 52\u00020\u0001:\u00015BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0017H\u0016J3\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u00172\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010%H\u0016¢\u0006\u0002\u0010'J\u0016\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\tJ\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020\u001eH\u0016J\b\u00100\u001a\u00020\u001eH\u0016J\u0010\u00101\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H\u0016J\u0006\u00102\u001a\u00020\u001eJ\u0010\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u00020\u0007H\u0004R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R(\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u00066"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleBitmap;", "Lcom/miui/doodle/base/DoodleRotatableItemBase;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "id", "", "itemRotate", "", "itemScale", "location", "Landroid/graphics/PointF;", "pivotPoint", "alpha", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Landroid/graphics/Bitmap;Ljava/lang/String;FFLandroid/graphics/PointF;Landroid/graphics/PointF;I)V", "getAlpha", "()I", "setAlpha", "(I)V", "srcRect", "Landroid/graphics/Rect;", "dstRect", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "resetBounds", "", "rect", "doDraw", "canvas", "Landroid/graphics/Canvas;", Notes.Data.DIRTY, "isElementRedraw", "", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "restLocation", "offsetX", "offsetY", "copy", "Lcom/miui/doodle/base/core/IDoodleItem;", "toJSONObject", "Lorg/json/JSONObject;", "clear", "cache", "doDrawAtTheTop", "cacheImageToFile", "restoreImageParcelFromCache", "cachekey", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleBitmap extends DoodleRotatableItemBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String KEY_BITMAP = "bitmap";
    private int alpha;
    private Bitmap bitmap;
    private Rect dstRect;
    private Rect srcRect;

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cache() {
    }

    public /* synthetic */ DoodleBitmap(IDoodle iDoodle, Bitmap bitmap, String str, float f, float f2, PointF pointF, PointF pointF2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, (i2 & 2) != 0 ? null : bitmap, (i2 & 4) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i2 & 8) != 0 ? -iDoodle.getDoodleRotation() : f, (i2 & 16) != 0 ? 1.0f : f2, (i2 & 32) != 0 ? new PointF() : pointF, (i2 & 64) != 0 ? new PointF() : pointF2, (i2 & 128) != 0 ? 255 : i);
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
    public DoodleBitmap(IDoodle doodle, Bitmap bitmap, String id, float f, float f2, PointF location, PointF pivotPoint, int i) {
        super(doodle, DoodlePenManager.INSTANCE.getBitmap(), id, f, f2, location, pivotPoint);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.alpha = i;
        setBitmap(bitmap);
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (bitmap != null) {
            float doodleHeight = getDoodle().getDoodleHeight() / 1.5f;
            float doodleWidth = getDoodle().getDoodleWidth() / 1.5f;
            setItemScale(RangesKt.coerceAtMost(doodleWidth < ((float) bitmap.getWidth()) ? doodleWidth / bitmap.getWidth() : 1.0f, doodleHeight < ((float) bitmap.getHeight()) ? doodleHeight / bitmap.getHeight() : 1.0f));
            getLocation().set((getDoodle().getDoodleWidth() - bitmap.getWidth()) / 2.0f, (getDoodle().getDoodleHeight() - bitmap.getHeight()) / 2.0f);
            resetBounds(getBounds());
            getPivotPoint().x = getBounds().width() / 2.0f;
            getPivotPoint().y = getBounds().height() / 2.0f;
        }
        IDoodleItem.refresh$default(this, null, 1, null);
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase
    public void resetBounds(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            this.srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            this.dstRect = new Rect(0, 0, (int) (getItemScale() * bitmap.getWidth()), (int) (getItemScale() * bitmap.getHeight()));
            Rect rect2 = this.srcRect;
            if (rect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("srcRect");
                rect2 = null;
            }
            rect.set(rect2);
        }
    }

    @Override // com.miui.doodle.base.DoodleItemBase
    public void doDraw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
    }

    public final void restLocation(float offsetX, float offsetY) {
        getLocation().x += offsetX;
        getLocation().y += offsetY;
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public IDoodleItem copy() {
        DoodleBitmap doodleBitmap = new DoodleBitmap(getDoodle(), this.bitmap, DoodleItemBase.INSTANCE.generateId(), 0.0f, 0.0f, null, null, 0, R2.attr.actionBarCutIcon, null);
        doodleBitmap.getBounds().set(getBounds());
        Rect rect = doodleBitmap.srcRect;
        Rect rect2 = null;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("srcRect");
            rect = null;
        }
        Rect rect3 = this.srcRect;
        if (rect3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("srcRect");
            rect3 = null;
        }
        rect.set(rect3);
        Rect rect4 = doodleBitmap.dstRect;
        if (rect4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dstRect");
            rect4 = null;
        }
        Rect rect5 = this.dstRect;
        if (rect5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dstRect");
        } else {
            rect2 = rect5;
        }
        rect4.set(rect2);
        doodleBitmap.setItemRotate(getItemRotate());
        doodleBitmap.setItemScale(getItemScale());
        doodleBitmap.setLocation(new PointF(getLocation().x, getLocation().y));
        doodleBitmap.setPivotPoint(new PointF(getPivotPoint().x, getPivotPoint().y));
        doodleBitmap.setAlpha(getAlpha());
        return doodleBitmap;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = super.toJSONObject();
        if (this.bitmap != null) {
            jSONObject.put(KEY_BITMAP, getId());
        }
        jSONObject.put("type", 11);
        return jSONObject;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void clear() {
        super.clear();
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    @Override // com.miui.doodle.base.DoodleRotatableItemBase, com.miui.doodle.base.DoodleSelectableItemBase
    public void doDrawAtTheTop(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getIsSelected()) {
            float f = 1;
            float doodleScale = f / getDoodle().getDoodleScale();
            float doodleScale2 = f / getDoodle().getDoodleScale();
            float f2 = getPivotPoint().x;
            float f3 = getPivotPoint().y;
            int iSave = canvas.save();
            canvas.scale(doodleScale, doodleScale2, f2, f3);
            try {
                resetBoundsScaled(getRectTemp());
                DrawUtil.INSTANCE.scaleRect(getRectTemp(), getDoodle().getDoodleScale(), getPivotPoint().x, getPivotPoint().y);
                drawBorder(canvas, getRectTemp());
                drawCloseButton(canvas, getRectTemp());
                drawScaleButton(canvas, getRectTemp());
            } finally {
                canvas.restoreToCount(iSave);
            }
        }
    }

    public final void cacheImageToFile() {
        setCacheImageKey(null);
        try {
            Bitmap bitmap = this.bitmap;
            String strValueOf = String.valueOf(bitmap != null ? bitmap.hashCode() : 0);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), strValueOf));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 != null) {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            setCacheImageKey(strValueOf);
        } catch (Exception e) {
            e.printStackTrace();
            setCacheImageKey(null);
        }
    }

    protected final void restoreImageParcelFromCache(String cachekey) {
        Intrinsics.checkNotNullParameter(cachekey, "cachekey");
        File cacheDir = DoodleApplication.INSTANCE.getAppInstance().getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "getCacheDir(...)");
        try {
            setBitmap(BitmapFactory.decodeFile(new File(cacheDir, cachekey).getPath()));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: compiled from: DoodleBitmap.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Jr\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00052\u001a\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleBitmap$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_BITMAP", "", "createBitmapItem", "", "id", "hasAdded", "", "locationX", "", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/feature/draw/DoodleBitmap;", "bitmapId", "readBitmapFunc", "Lkotlin/Function2;", "Landroid/graphics/Bitmap;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createBitmapItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, DoodleBitmap item, String bitmapId, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(item, "item");
            Intrinsics.checkNotNullParameter(bitmapId, "bitmapId");
            Intrinsics.checkNotNullParameter(readBitmapFunc, "readBitmapFunc");
            if (!StringsKt.isBlank(bitmapId)) {
                item.setBitmap(readBitmapFunc.invoke(bitmapId, true));
            }
            DoodleRotatableItemBase.INSTANCE.createBaseItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, item);
        }
    }
}
