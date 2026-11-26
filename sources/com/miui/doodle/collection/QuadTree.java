package com.miui.doodle.collection;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.utils.UIUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: QuadTree.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00112\u0006\u0010\u0012\u001a\u00020\u0013J$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015J\u0018\u0010\u0018\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J&\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00062\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001dH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001f"}, d2 = {"Lcom/miui/doodle/collection/QuadTree;", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, Constants.ROOT, "Lcom/miui/doodle/collection/QuadTreeNode;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IILcom/miui/doodle/collection/QuadTreeNode;)V", "getRoot", "()Lcom/miui/doodle/collection/QuadTreeNode;", "addDot", "", "dot", "Lcom/miui/doodle/document/Dot;", "removeDot", "findDot", "", "rect", "Landroid/graphics/RectF;", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "radius", "addDotInner", "node", "removeDotInner", "findDotInner", "list", "", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class QuadTree {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_NUM_DOTS = 10;
    public static final String TAG = "QuadTree";
    private final QuadTreeNode root;

    public QuadTree(int i, int i2, QuadTreeNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
    }

    public /* synthetic */ QuadTree(int i, int i2, QuadTreeNode quadTreeNode, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? new QuadTreeNode(0, true, new RectF(0.0f, 0.0f, i, i2), null, null, null, null, null, R2.attr.actionBarCutIcon, null) : quadTreeNode);
    }

    public final QuadTreeNode getRoot() {
        return this.root;
    }

    public final void addDot(Dot dot) {
        Intrinsics.checkNotNullParameter(dot, "dot");
        addDotInner(dot, this.root);
    }

    public final void removeDot(Dot dot) {
        Intrinsics.checkNotNullParameter(dot, "dot");
        removeDotInner(dot, this.root);
    }

    public final List<Dot> findDot(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        ArrayList arrayList = new ArrayList();
        findDotInner(rect, this.root, arrayList);
        return arrayList;
    }

    public final List<Dot> findDot(float x, float y, float radius) {
        return findDot(new RectF(x - radius, y - radius, x + radius, y + radius));
    }

    private final void addDotInner(Dot dot, QuadTreeNode node) {
        if (node.getIsLeaf()) {
            node.getDots().add(dot);
            if (node.getDots().size() > 10) {
                INSTANCE.splitNode(node);
                return;
            }
            return;
        }
        QuadTreeNode leftBottom = node.getLeftBottom();
        if (leftBottom != null && UIUtils.INSTANCE.detectIntersect(leftBottom.getRegion(), dot.getRect())) {
            addDotInner(dot, leftBottom);
        }
        QuadTreeNode leftTop = node.getLeftTop();
        if (leftTop != null && UIUtils.INSTANCE.detectIntersect(leftTop.getRegion(), dot.getRect())) {
            addDotInner(dot, leftTop);
        }
        QuadTreeNode rightBottom = node.getRightBottom();
        if (rightBottom != null && UIUtils.INSTANCE.detectIntersect(rightBottom.getRegion(), dot.getRect())) {
            addDotInner(dot, rightBottom);
        }
        QuadTreeNode rightTop = node.getRightTop();
        if (rightTop == null || !UIUtils.INSTANCE.detectIntersect(rightTop.getRegion(), dot.getRect())) {
            return;
        }
        addDotInner(dot, rightTop);
    }

    private final void removeDotInner(Dot dot, QuadTreeNode node) {
        if (node.getIsLeaf()) {
            node.getDots().remove(dot);
            return;
        }
        QuadTreeNode leftBottom = node.getLeftBottom();
        if (leftBottom != null && UIUtils.INSTANCE.detectIntersect(leftBottom.getRegion(), dot.getRect())) {
            removeDotInner(dot, leftBottom);
        }
        QuadTreeNode leftTop = node.getLeftTop();
        if (leftTop != null && UIUtils.INSTANCE.detectIntersect(leftTop.getRegion(), dot.getRect())) {
            removeDotInner(dot, leftTop);
        }
        QuadTreeNode rightBottom = node.getRightBottom();
        if (rightBottom != null && UIUtils.INSTANCE.detectIntersect(rightBottom.getRegion(), dot.getRect())) {
            removeDotInner(dot, rightBottom);
        }
        QuadTreeNode rightTop = node.getRightTop();
        if (rightTop == null || !UIUtils.INSTANCE.detectIntersect(rightTop.getRegion(), dot.getRect())) {
            return;
        }
        removeDotInner(dot, rightTop);
    }

    private final void findDotInner(RectF rect, QuadTreeNode node, List<Dot> list) {
        if (!node.getIsLeaf()) {
            QuadTreeNode leftBottom = node.getLeftBottom();
            if (leftBottom != null && UIUtils.INSTANCE.detectIntersect(leftBottom.getRegion(), rect)) {
                findDotInner(rect, leftBottom, list);
            }
            QuadTreeNode leftTop = node.getLeftTop();
            if (leftTop != null && UIUtils.INSTANCE.detectIntersect(leftTop.getRegion(), rect)) {
                findDotInner(rect, leftTop, list);
            }
            QuadTreeNode rightBottom = node.getRightBottom();
            if (rightBottom != null && UIUtils.INSTANCE.detectIntersect(rightBottom.getRegion(), rect)) {
                findDotInner(rect, rightBottom, list);
            }
            QuadTreeNode rightTop = node.getRightTop();
            if (rightTop == null || !UIUtils.INSTANCE.detectIntersect(rightTop.getRegion(), rect)) {
                return;
            }
            findDotInner(rect, rightTop, list);
            return;
        }
        for (Dot dot : node.getDots()) {
            if (UIUtils.INSTANCE.detectIntersect(rect, dot.getRect())) {
                list.add(dot);
            }
        }
    }

    /* JADX INFO: compiled from: QuadTree.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/doodle/collection/QuadTree$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "MAX_NUM_DOTS", "", "splitNode", "", "node", "Lcom/miui/doodle/collection/QuadTreeNode;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void splitNode(QuadTreeNode node) {
            Companion companion;
            QuadTreeNode quadTreeNode;
            Iterator it;
            QuadTreeNode quadTreeNode2;
            Intrinsics.checkNotNullParameter(node, "node");
            float f = (node.getRegion().top + node.getRegion().bottom) / 2.0f;
            float f2 = (node.getRegion().left + node.getRegion().right) / 2.0f;
            node.setLeaf(false);
            int depth = node.getDepth() + 1;
            QuadTreeNode quadTreeNode3 = new QuadTreeNode(depth, true, new RectF(f2, node.getRegion().top, node.getRegion().right, f), null, null, null, null, null, R2.attr.actionBarCutIcon, null);
            QuadTreeNode quadTreeNode4 = new QuadTreeNode(depth, true, new RectF(node.getRegion().left, node.getRegion().top, f2, f), null, null, null, null, null, R2.attr.actionBarCutIcon, null);
            QuadTreeNode quadTreeNode5 = new QuadTreeNode(depth, true, new RectF(f2, f, node.getRegion().right, node.getRegion().bottom), null, null, null, null, null, R2.attr.actionBarCutIcon, null);
            RectF rectF = new RectF(node.getRegion().left, f, f2, node.getRegion().bottom);
            QuadTreeNode quadTreeNode6 = quadTreeNode5;
            QuadTreeNode quadTreeNode7 = new QuadTreeNode(depth, true, rectF, null, null, null, null, null, R2.attr.actionBarCutIcon, null);
            node.setRightTop(quadTreeNode3);
            node.setLeftTop(quadTreeNode4);
            node.setRightBottom(quadTreeNode6);
            node.setLeftBottom(quadTreeNode7);
            Iterator it2 = node.getDots().iterator();
            while (it2.hasNext()) {
                Dot dot = (Dot) it2.next();
                float x = dot.getX();
                float y = dot.getY();
                if (quadTreeNode3.getRegion().contains(x, y)) {
                    it = it2;
                    quadTreeNode2 = quadTreeNode7;
                    quadTreeNode = quadTreeNode6;
                    Logger.INSTANCE.d(QuadTree.TAG, "rightTop: " + quadTreeNode3.getRegion().left + "," + quadTreeNode3.getRegion().top + "," + quadTreeNode3.getRegion().right + "," + quadTreeNode3.getRegion().bottom + " dot: x: " + dot.getX() + ", y: " + dot.getY());
                    quadTreeNode3.getDots().add(dot);
                } else {
                    quadTreeNode = quadTreeNode6;
                    it = it2;
                    quadTreeNode2 = quadTreeNode7;
                    if (quadTreeNode4.getRegion().contains(x, y)) {
                        Logger.INSTANCE.d(QuadTree.TAG, "leftTop: " + quadTreeNode4.getRegion().left + "," + quadTreeNode4.getRegion().top + "," + quadTreeNode4.getRegion().right + "," + quadTreeNode4.getRegion().bottom + " dot: x: " + dot.getX() + ", y: " + dot.getY());
                        quadTreeNode4.getDots().add(dot);
                    } else if (quadTreeNode2.getRegion().contains(x, y)) {
                        Logger.INSTANCE.d(QuadTree.TAG, "region: " + quadTreeNode2.getRegion().left + "," + quadTreeNode2.getRegion().top + "," + quadTreeNode2.getRegion().right + "," + quadTreeNode2.getRegion().bottom + " dot: x: " + dot.getX() + ", y: " + dot.getY());
                        quadTreeNode2.getDots().add(dot);
                    } else if (quadTreeNode.getRegion().contains(x, y)) {
                        Logger.INSTANCE.d(QuadTree.TAG, "rightBottom: " + quadTreeNode.getRegion().left + "," + quadTreeNode.getRegion().top + "," + quadTreeNode.getRegion().right + "," + quadTreeNode.getRegion().bottom + " dot: x: " + dot.getX() + ", y: " + dot.getY());
                        quadTreeNode.getDots().add(dot);
                    } else {
                        Logger.INSTANCE.d(QuadTree.TAG, "dot: x: " + dot.getX() + ", y: " + dot.getY());
                    }
                }
                it2 = it;
                quadTreeNode7 = quadTreeNode2;
                quadTreeNode6 = quadTreeNode;
            }
            QuadTreeNode quadTreeNode8 = quadTreeNode6;
            QuadTreeNode quadTreeNode9 = quadTreeNode7;
            node.getDots().clear();
            if (quadTreeNode3.getDots().size() > 10) {
                companion = this;
                companion.splitNode(quadTreeNode3);
            } else {
                companion = this;
            }
            if (quadTreeNode4.getDots().size() > 10) {
                companion.splitNode(quadTreeNode4);
            }
            if (quadTreeNode8.getDots().size() > 10) {
                companion.splitNode(quadTreeNode8);
            }
            if (quadTreeNode9.getDots().size() > 10) {
                companion.splitNode(quadTreeNode9);
            }
        }
    }
}
