package com.miui.todo.base;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class TodoLayoutManager extends LinearLayoutManager {
    private static final String TAG = "TodoLayoutManager";

    public TodoLayoutManager(Context context) {
        super(context);
    }

    public TodoLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public TodoLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "crash in RecyclerView " + e.toString());
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        try {
            return super.computeVerticalScrollOffset(state);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "computeVerticalScrollOffset got a crash " + e.getMessage());
            return 0;
        }
    }
}
