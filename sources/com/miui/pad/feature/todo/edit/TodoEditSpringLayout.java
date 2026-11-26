package com.miui.pad.feature.todo.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.lang.reflect.Field;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class TodoEditSpringLayout extends SpringBackLayout {
    private View targetView;

    public TodoEditSpringLayout(Context context) {
        this(context, null);
    }

    public TodoEditSpringLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void setTarget() {
        try {
            Field declaredField = TodoEditSpringLayout.class.getSuperclass().getDeclaredField("mTarget");
            declaredField.setAccessible(true);
            this.targetView = (View) declaredField.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    @Override // miuix.springback.view.SpringBackLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        setTarget();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.targetView.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
    }
}
