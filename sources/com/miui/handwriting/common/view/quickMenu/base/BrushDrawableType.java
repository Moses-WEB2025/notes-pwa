package com.miui.handwriting.common.view.quickMenu.base;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.tools.constants.Constants;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: BrushDrawable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/BrushDrawableType;", "", "brushName", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ILjava/lang/String;)V", "getBrushName", "()Ljava/lang/String;", "PENCIL", "INK", "MARKPEN", "BALLPOINT", "WATERCOLOR", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public enum BrushDrawableType {
    PENCIL(Constants.TOOL_BRUSH_PENCIL),
    INK(Constants.TOOL_BRUSH_INK),
    MARKPEN(Constants.TOOL_BRUSH_MARKPEN),
    BALLPOINT(Constants.TOOL_BRUSH_BALLPOINT),
    WATERCOLOR(Constants.TOOL_BRUSH_WATERCOLOR);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String brushName;

    public static EnumEntries<BrushDrawableType> getEntries() {
        return $ENTRIES;
    }

    BrushDrawableType(String str) {
        this.brushName = str;
    }

    public final String getBrushName() {
        return this.brushName;
    }
}
