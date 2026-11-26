package com.miui.pad.richeditor;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.RichEditScrollView;

/* JADX INFO: loaded from: classes4.dex */
public class PadRichEditScrollView extends RichEditScrollView {
    public PadRichEditScrollView(Context context) {
        super(context);
    }

    public PadRichEditScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PadRichEditScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.richeditor.RichEditScrollView, miuix.core.widget.NestedScrollView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // com.miui.richeditor.RichEditScrollView, miuix.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Logger.INSTANCE.d("PadRichEditScrollView", "requestChildRectangleOnScreen rectangle " + rect.toString() + " child " + view.toString());
        if (this.mEditor.hasShowingMenu() || this.forbidScroll) {
            return true;
        }
        if (this.mEditor.isInDragMode()) {
            return false;
        }
        getLocalVisibleRect(this.mVisibleRect);
        if (rect.top < this.mVisibleRect.top) {
            int i = rect.top - this.mVisibleRect.top;
            this.forbidComputeScroll = false;
            scrollBy(0, i);
            return false;
        }
        return super.requestChildRectangleOnScreen(view, rect, z);
    }
}
