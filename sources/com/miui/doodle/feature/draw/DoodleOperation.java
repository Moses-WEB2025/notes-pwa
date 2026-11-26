package com.miui.doodle.feature.draw;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleSelectableItem;
import com.miui.notes.provider.Notes;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleOperation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 !2\u00020\u0001:\u0001!B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tJ3\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u0014¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u000bH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006\""}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleOperation;", "Lcom/miui/doodle/base/DoodleItemBase;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;)V", "targetItem", "Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "operation", "", "setTargetItem", "", "setOperation", "doDraw", "canvas", "Landroid/graphics/Canvas;", Notes.Data.DIRTY, "Landroid/graphics/Rect;", "isElementRedraw", "", "isDrawToView", "(Landroid/graphics/Canvas;Landroid/graphics/Rect;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "cache", "copy", "Lcom/miui/doodle/base/core/IDoodleItem;", "toJSONObject", "Lorg/json/JSONObject;", "value", "alpha", "getAlpha", "()I", "setAlpha", "(I)V", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleOperation extends DoodleItemBase {
    public static final int OP_ADD = 1;
    public static final int OP_DELETE = 2;
    public static final int OP_NONE = 0;
    public static final int OP_ROTATE = 3;
    public static final int OP_SCALE = 4;
    public static final int OP_TRANSLATE = 5;
    private int operation;
    private IDoodleSelectableItem targetItem;

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void setAlpha(int i) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleOperation(IDoodle doodle) {
        super(doodle, null, doodle.getPen(), 2, null);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
    }

    public final void setTargetItem(IDoodleSelectableItem targetItem) {
        this.targetItem = targetItem;
    }

    public final void setOperation(int operation) {
        this.operation = operation;
    }

    @Override // com.miui.doodle.base.DoodleItemBase
    protected void doDraw(Canvas canvas, Rect dirty, Boolean isElementRedraw, Boolean isDrawToView) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.operation == 2) {
            IDoodleSelectableItem iDoodleSelectableItem = this.targetItem;
            Intrinsics.checkNotNull(iDoodleSelectableItem);
            iDoodleSelectableItem.hide(canvas);
        }
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public void cache() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public IDoodleItem copy() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.miui.doodle.base.core.IDoodleItem
    public int getAlpha() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
