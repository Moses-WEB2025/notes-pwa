package com.miui.maml.util;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.elements.ButtonScreenElement;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MamlAccessHelper extends ExploreByTouchHelper {
    private static final String TAG = "MamlAccessHelper";
    View mHostView;
    ScreenElementRoot mRoot;

    public MamlAccessHelper(ScreenElementRoot screenElementRoot, View view) {
        super(view);
        this.mRoot = screenElementRoot;
        this.mHostView = view;
        screenElementRoot.setMamlAccessHelper(this);
    }

    public void performAccessibilityAction(final int i, final int i2) {
        this.mHostView.post(new Runnable() { // from class: com.miui.maml.util.MamlAccessHelper.1
            @Override // java.lang.Runnable
            public void run() {
                MamlAccessHelper mamlAccessHelper = MamlAccessHelper.this;
                mamlAccessHelper.getAccessibilityNodeProvider(mamlAccessHelper.mHostView).performAction(i, i2, null);
            }
        });
    }

    public void removeRoot() {
        this.mRoot = null;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected int getVirtualViewAt(float f, float f2) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null) {
            return Integer.MIN_VALUE;
        }
        List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
        for (int size = accessibleElements.size() - 1; size >= 0; size--) {
            AnimatedScreenElement animatedScreenElement = accessibleElements.get(size);
            if (animatedScreenElement.isVisible() && animatedScreenElement.touched(f, f2)) {
                return size;
            }
        }
        return Integer.MIN_VALUE;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void getVisibleVirtualViews(List<Integer> list) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
            for (int i = 0; i < accessibleElements.size(); i++) {
                if (accessibleElements.get(i).isVisible()) {
                    list.add(Integer.valueOf(i));
                }
            }
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
            if (i < 0 || i >= accessibleElements.size()) {
                return;
            }
            accessibilityEvent.setContentDescription(accessibleElements.get(i).getContentDescription());
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
            String str = "";
            if (i >= 0 && i < accessibleElements.size()) {
                AnimatedScreenElement animatedScreenElement = accessibleElements.get(i);
                String contentDescription = animatedScreenElement.getContentDescription();
                if (contentDescription == null) {
                    Log.e(TAG, "element.getContentDescription() == null " + animatedScreenElement.getName());
                } else {
                    str = contentDescription;
                }
                accessibilityNodeInfoCompat.setContentDescription(str);
                accessibilityNodeInfoCompat.addAction(16);
                accessibilityNodeInfoCompat.setBoundsInParent(new Rect((int) animatedScreenElement.getAbsoluteLeft(), (int) animatedScreenElement.getAbsoluteTop(), (int) (animatedScreenElement.getAbsoluteLeft() + animatedScreenElement.getWidth()), (int) (animatedScreenElement.getAbsoluteTop() + animatedScreenElement.getHeight())));
                return;
            }
            Log.e(TAG, "virtualViewId not found " + i);
            accessibilityNodeInfoCompat.setContentDescription("");
            accessibilityNodeInfoCompat.setBoundsInParent(new Rect(0, 0, 0, 0));
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null || i2 != 16) {
            return false;
        }
        List<AnimatedScreenElement> accessibleElements = screenElementRoot.getAccessibleElements();
        if (i >= 0 && i < accessibleElements.size()) {
            AnimatedScreenElement animatedScreenElement = accessibleElements.get(i);
            animatedScreenElement.performAction("up");
            if (!(animatedScreenElement instanceof ButtonScreenElement)) {
                return true;
            }
            ((ButtonScreenElement) animatedScreenElement).onActionUp();
            return true;
        }
        return false;
    }
}
