package com.miui.doodle.collection;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Dot;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: QuadTreeNode.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0000\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\b\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001d\"\u0004\b%\u0010\u001fR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'¨\u0006("}, d2 = {"Lcom/miui/doodle/collection/QuadTreeNode;", "", "depth", "", "isLeaf", "", "region", "Landroid/graphics/RectF;", "leftTop", "leftBottom", "rightTop", "rightBottom", DoodleBrushPath.KEY_DOTS, "", "Lcom/miui/doodle/document/Dot;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IZLandroid/graphics/RectF;Lcom/miui/doodle/collection/QuadTreeNode;Lcom/miui/doodle/collection/QuadTreeNode;Lcom/miui/doodle/collection/QuadTreeNode;Lcom/miui/doodle/collection/QuadTreeNode;Ljava/util/List;)V", "getDepth", "()I", "setDepth", "(I)V", "()Z", "setLeaf", "(Z)V", "getRegion", "()Landroid/graphics/RectF;", "setRegion", "(Landroid/graphics/RectF;)V", "getLeftTop", "()Lcom/miui/doodle/collection/QuadTreeNode;", "setLeftTop", "(Lcom/miui/doodle/collection/QuadTreeNode;)V", "getLeftBottom", "setLeftBottom", "getRightTop", "setRightTop", "getRightBottom", "setRightBottom", "getDots", "()Ljava/util/List;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class QuadTreeNode {
    private int depth;
    private final List<Dot> dots;
    private boolean isLeaf;
    private QuadTreeNode leftBottom;
    private QuadTreeNode leftTop;
    private RectF region;
    private QuadTreeNode rightBottom;
    private QuadTreeNode rightTop;

    public QuadTreeNode(int i, boolean z, RectF region, QuadTreeNode quadTreeNode, QuadTreeNode quadTreeNode2, QuadTreeNode quadTreeNode3, QuadTreeNode quadTreeNode4, List<Dot> dots) {
        Intrinsics.checkNotNullParameter(region, "region");
        Intrinsics.checkNotNullParameter(dots, "dots");
        this.depth = i;
        this.isLeaf = z;
        this.region = region;
        this.leftTop = quadTreeNode;
        this.leftBottom = quadTreeNode2;
        this.rightTop = quadTreeNode3;
        this.rightBottom = quadTreeNode4;
        this.dots = dots;
    }

    public final int getDepth() {
        return this.depth;
    }

    public final void setDepth(int i) {
        this.depth = i;
    }

    /* JADX INFO: renamed from: isLeaf, reason: from getter */
    public final boolean getIsLeaf() {
        return this.isLeaf;
    }

    public final void setLeaf(boolean z) {
        this.isLeaf = z;
    }

    public final RectF getRegion() {
        return this.region;
    }

    public final void setRegion(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        this.region = rectF;
    }

    public final QuadTreeNode getLeftTop() {
        return this.leftTop;
    }

    public final void setLeftTop(QuadTreeNode quadTreeNode) {
        this.leftTop = quadTreeNode;
    }

    public final QuadTreeNode getLeftBottom() {
        return this.leftBottom;
    }

    public final void setLeftBottom(QuadTreeNode quadTreeNode) {
        this.leftBottom = quadTreeNode;
    }

    public final QuadTreeNode getRightTop() {
        return this.rightTop;
    }

    public final void setRightTop(QuadTreeNode quadTreeNode) {
        this.rightTop = quadTreeNode;
    }

    public final QuadTreeNode getRightBottom() {
        return this.rightBottom;
    }

    public final void setRightBottom(QuadTreeNode quadTreeNode) {
        this.rightBottom = quadTreeNode;
    }

    public /* synthetic */ QuadTreeNode(int i, boolean z, RectF rectF, QuadTreeNode quadTreeNode, QuadTreeNode quadTreeNode2, QuadTreeNode quadTreeNode3, QuadTreeNode quadTreeNode4, ArrayList arrayList, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, rectF, (i2 & 8) != 0 ? null : quadTreeNode, (i2 & 16) != 0 ? null : quadTreeNode2, (i2 & 32) != 0 ? null : quadTreeNode3, (i2 & 64) != 0 ? null : quadTreeNode4, (i2 & 128) != 0 ? new ArrayList() : arrayList);
    }

    public final List<Dot> getDots() {
        return this.dots;
    }
}
