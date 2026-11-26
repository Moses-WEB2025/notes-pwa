package com.miui.doodle.base.core;

import android.graphics.Rect;
import android.graphics.RectF;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;

/* JADX INFO: compiled from: IDoodleSelectableItem.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0011À\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "Lcom/miui/doodle/base/core/IDoodleItem;", "isSelected", "", "()Z", "setSelected", "(Z)V", "bounds", "Landroid/graphics/Rect;", "getBounds", "()Landroid/graphics/Rect;", "contains", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "rect", "Landroid/graphics/RectF;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IDoodleSelectableItem extends IDoodleItem {
    boolean contains(float x, float y);

    boolean contains(RectF rect);

    Rect getBounds();

    boolean isSelected();

    void setSelected(boolean z);
}
