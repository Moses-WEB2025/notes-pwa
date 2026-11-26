package com.miui.maml.elements;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.util.Log;
import android.view.MotionEvent;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.folme.AnimatedProperty;
import java.util.ArrayList;
import miuix.animation.FolmeEase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class ElementGroup extends AnimatedScreenElement {
    private static final String LOG_TAG = "MAML_ElementGroup";
    public static final String TAG_NAME = "ElementGroup";
    public static final String TAG_NAME1 = "Group";
    protected boolean mClip;
    protected ArrayList<ScreenElement> mElements;
    private boolean mHovered;
    private IndexedVariable mIndexVar;
    private boolean mLayered;
    private LinearDirection mLinearDirection;
    private boolean mTouched;

    private enum LinearDirection {
        None,
        Horizontal,
        Vertical
    }

    public ElementGroup(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mElements = new ArrayList<>();
        this.mLinearDirection = LinearDirection.None;
        load(element);
    }

    private ElementGroup(ScreenElementRoot screenElementRoot, IndexedVariable indexedVariable) {
        super(null, screenElementRoot);
        this.mElements = new ArrayList<>();
        this.mLinearDirection = LinearDirection.None;
        this.mIndexVar = indexedVariable;
    }

    public static ElementGroup createArrayGroup(ScreenElementRoot screenElementRoot, IndexedVariable indexedVariable) {
        return new ElementGroup(screenElementRoot, indexedVariable);
    }

    public static boolean isArrayGroup(ScreenElement screenElement) {
        return (screenElement instanceof ElementGroup) && ((ElementGroup) screenElement).isArray();
    }

    public boolean isArray() {
        return this.mIndexVar != null;
    }

    private void load(Element element) {
        if (element == null) {
            return;
        }
        this.mClip = Boolean.parseBoolean(getAttr(element, "clip"));
        this.mLayered = Boolean.parseBoolean(getAttr(element, "layered"));
        String attr = getAttr(element, FolmeEase.LINEAR);
        if (AnimatedProperty.PROPERTY_NAME_H.equalsIgnoreCase(attr)) {
            this.mLinearDirection = LinearDirection.Horizontal;
        } else if ("v".equalsIgnoreCase(attr)) {
            this.mLinearDirection = LinearDirection.Vertical;
        }
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            if (childNodes.item(i).getNodeType() == 1) {
                addElement(onCreateChild((Element) childNodes.item(i)));
            }
        }
    }

    public void addElement(ScreenElement screenElement) {
        if (screenElement != null) {
            screenElement.setParent(this);
            this.mElements.add(screenElement);
        }
    }

    public void removeElement(ScreenElement screenElement) {
        this.mElements.remove(screenElement);
        requestUpdate();
    }

    public void removeAllElements() {
        this.mElements.clear();
        requestUpdate();
    }

    protected ScreenElement onCreateChild(Element element) {
        return getContext().mFactory.createInstance(element, this.mRoot);
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            this.mElements.get(i).init();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void setAnim(String[] strArr) {
        super.setAnim(strArr);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            this.mElements.get(i).setAnim(strArr);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        super.playAnim(j, j2, j3, z, z2);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            this.mElements.get(i).playAnim(j, j2, j3, z, z2);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pauseAnim(long j) {
        super.pauseAnim(j);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).pauseAnim(j);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resumeAnim(long j) {
        super.resumeAnim(j);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).resumeAnim(j);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).reset(j);
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).pause();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).resume();
        }
    }

    public ScreenElement getChild(int i) {
        if (i < 0 || i >= this.mElements.size()) {
            return null;
        }
        return this.mElements.get(i);
    }

    public int getSize() {
        return this.mElements.size();
    }

    public boolean isLayered() {
        return this.mLayered;
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
        Canvas canvas2;
        int iSave;
        float width = getWidth();
        float height = getHeight();
        float left = getLeft(0.0f, width);
        float top = getTop(0.0f, height);
        if (this.mLayered && width > 0.0f && height > 0.0f) {
            canvas2 = canvas;
            iSave = canvas2.saveLayerAlpha(left, top, left + width, top + height, getAlpha(), 31);
        } else {
            canvas2 = canvas;
            iSave = canvas2.save();
        }
        canvas2.translate(left, top);
        if (width > 0.0f && height > 0.0f && this.mClip) {
            canvas2.clipRect(0.0f, 0.0f, width, height);
        }
        doRenderChildren(canvas2);
        canvas2.restoreToCount(iSave);
    }

    protected void doRenderChildren(Canvas canvas) {
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            ScreenElement screenElement = this.mElements.get(i);
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            screenElement.render(canvas);
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        doTickChildren(j);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0079 A[PHI: r1 r4
  0x0079: PHI (r1v5 float) = (r1v4 float), (r1v7 float) binds: [B:22:0x0077, B:19:0x005b] A[DONT_GENERATE, DONT_INLINE]
  0x0079: PHI (r4v6 float) = (r4v5 float), (r4v11 float) binds: [B:22:0x0077, B:19:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    protected void doTickChildren(long j) {
        float height;
        int size = this.mElements.size();
        float width = 0.0f;
        float f = 0.0f;
        for (int i = 0; i < size; i++) {
            ScreenElement screenElement = this.mElements.get(i);
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            screenElement.tick(j);
            if (this.mLinearDirection != LinearDirection.None && (screenElement instanceof AnimatedScreenElement) && screenElement.isVisible()) {
                AnimatedScreenElement animatedScreenElement = (AnimatedScreenElement) screenElement;
                int i2 = AnonymousClass1.$SwitchMap$com$miui$maml$elements$ElementGroup$LinearDirection[this.mLinearDirection.ordinal()];
                if (i2 == 1) {
                    float marginLeft = width + animatedScreenElement.getMarginLeft();
                    animatedScreenElement.setX(marginLeft);
                    width = marginLeft + animatedScreenElement.getWidth() + animatedScreenElement.getMarginRight();
                    height = animatedScreenElement.getHeight();
                    if (f < height) {
                        f = height;
                    }
                } else if (i2 == 2) {
                    float marginTop = width + animatedScreenElement.getMarginTop();
                    animatedScreenElement.setY(marginTop);
                    width = marginTop + animatedScreenElement.getHeight() + animatedScreenElement.getMarginBottom();
                    height = animatedScreenElement.getWidth();
                    if (f < height) {
                        f = height;
                    }
                }
            }
        }
        int i3 = AnonymousClass1.$SwitchMap$com$miui$maml$elements$ElementGroup$LinearDirection[this.mLinearDirection.ordinal()];
        if (i3 == 1) {
            double d = width;
            setWidth(d);
            double d2 = f;
            setHeight(d2);
            setActualWidth(descale(d));
            setActualHeight(descale(d2));
            return;
        }
        if (i3 != 2) {
            return;
        }
        double d3 = width;
        setHeight(d3);
        double d4 = f;
        setWidth(d4);
        setActualHeight(descale(d3));
        setActualWidth(descale(d4));
    }

    /* JADX INFO: renamed from: com.miui.maml.elements.ElementGroup$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ElementGroup$LinearDirection;

        static {
            int[] iArr = new int[LinearDirection.values().length];
            $SwitchMap$com$miui$maml$elements$ElementGroup$LinearDirection = iArr;
            try {
                iArr[LinearDirection.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ElementGroup$LinearDirection[LinearDirection.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public boolean onTouch(MotionEvent motionEvent) {
        boolean z = false;
        if (!isVisible()) {
            return false;
        }
        int action = motionEvent.getAction();
        boolean z2 = touched(motionEvent.getX(), motionEvent.getY());
        if (this.mClip && !z2) {
            if (!this.mTouched) {
                return false;
            }
            motionEvent.setAction(3);
        }
        int size = this.mElements.size();
        if (this.mRoot.version() >= 2) {
            for (int i = size - 1; i >= 0; i--) {
                ScreenElement screenElement = this.mElements.get(i);
                IndexedVariable indexedVariable = this.mIndexVar;
                if (indexedVariable != null) {
                    indexedVariable.set(i);
                }
                if (screenElement.onTouch(motionEvent)) {
                    z = true;
                    break;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                ScreenElement screenElement2 = this.mElements.get(i2);
                IndexedVariable indexedVariable2 = this.mIndexVar;
                if (indexedVariable2 != null) {
                    indexedVariable2.set(i2);
                }
                if (screenElement2.onTouch(motionEvent)) {
                    z = true;
                    break;
                }
            }
        }
        motionEvent.setAction(action);
        boolean zOnTouch = z ? true : super.onTouch(motionEvent);
        this.mTouched = zOnTouch;
        return zOnTouch;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public boolean onHover(MotionEvent motionEvent) {
        if (!isVisible()) {
            return false;
        }
        boolean z = touched(motionEvent.getX(), motionEvent.getY());
        if (this.mClip && !z) {
            if (!this.mHovered) {
                return false;
            }
            motionEvent.setAction(10);
        }
        boolean zOnHover = true;
        for (int size = this.mElements.size() - 1; size >= 0; size--) {
            ScreenElement screenElement = this.mElements.get(size);
            IndexedVariable indexedVariable = this.mIndexVar;
            if (indexedVariable != null) {
                indexedVariable.set(size);
            }
            if (screenElement.onHover(motionEvent)) {
                this.mHovered = zOnHover;
                return zOnHover;
            }
        }
        zOnHover = super.onHover(motionEvent);
        this.mHovered = zOnHover;
        return zOnHover;
    }

    protected float getParentLeft() {
        return getLeft() + (this.mParent == null ? 0.0f : this.mParent.getParentLeft());
    }

    protected float getParentTop() {
        return getTop() + (this.mParent == null ? 0.0f : this.mParent.getParentTop());
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            try {
                this.mElements.get(i).finish();
            } catch (Exception e) {
                Log.e(LOG_TAG, e.toString());
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void showCategory(String str, boolean z) {
        super.showCategory(str, z);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).showCategory(str, z);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public ScreenElement findElement(String str) {
        ScreenElement screenElementFindElement = super.findElement(str);
        if (screenElementFindElement != null) {
            return screenElementFindElement;
        }
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            ScreenElement screenElementFindElement2 = this.mElements.get(i).findElement(str);
            if (screenElementFindElement2 != null) {
                return screenElementFindElement2;
            }
        }
        return null;
    }

    public ArrayList<ScreenElement> getElements() {
        return this.mElements;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void acceptVisitor(ScreenElementVisitor screenElementVisitor) {
        super.acceptVisitor(screenElementVisitor);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).acceptVisitor(screenElementVisitor);
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onVisibilityChange(boolean z) {
        super.onVisibilityChange(z);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).updateVisibility();
        }
    }

    public void setClip(boolean z) {
        this.mClip = z;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        int size = this.mElements.size();
        for (int i = 0; i < size; i++) {
            this.mElements.get(i).setColorFilter(colorFilter);
        }
    }
}
