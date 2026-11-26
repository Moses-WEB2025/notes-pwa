package com.miui.richeditor;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class FullScreenRichEditScrollView extends RichEditScrollView {
    protected int mRequestFocusChildAdd;

    public FullScreenRichEditScrollView(Context context) {
        super(context);
        this.mRequestFocusChildAdd = 0;
    }

    public FullScreenRichEditScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRequestFocusChildAdd = 0;
    }

    public FullScreenRichEditScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRequestFocusChildAdd = 0;
    }

    @Override // com.miui.richeditor.RichEditScrollView, miuix.core.widget.NestedScrollView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mRequestFocusChildAdd = getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_request_child_focus_add);
    }

    @Override // com.miui.richeditor.RichEditScrollView, miuix.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
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
        this.forbidComputeScroll = rect.bottom <= this.mVisibleRect.bottom;
        if (!this.forbidComputeScroll) {
            int i2 = this.mRequestFocusChildAdd;
            rect.top += i2;
            rect.bottom += i2;
        }
        return super.requestChildRectangleOnScreen(view, rect, z);
    }
}
