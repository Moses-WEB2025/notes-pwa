package com.miui.pc.frame;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TodoCategoryAdapter extends RecyclerView.Adapter<TodoCategoryItem> implements ExpandableInterface {
    public static final int INVALID_TYPE = Integer.MIN_VALUE;
    private View.OnClickListener mOnClickListener;
    private List<Integer> mItems = new ArrayList<Integer>() { // from class: com.miui.pc.frame.TodoCategoryAdapter.1
        {
            add(0);
            add(1);
        }
    };
    protected int mPcTodoListType = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public TodoCategoryItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        TodoCategoryItem todoCategoryItemNewInstance = TodoCategoryItem.newInstance(viewGroup);
        todoCategoryItemNewInstance.setOnClickListener(this.mOnClickListener);
        return todoCategoryItemNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(TodoCategoryItem todoCategoryItem, int i) {
        todoCategoryItem.bind(this.mItems.get(i).intValue(), this.mPcTodoListType);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setSelectedType(int i) {
        this.mPcTodoListType = i;
        notifyDataSetChanged();
    }

    public int getSelectedType() {
        return this.mPcTodoListType;
    }

    public void clearSelectedType() {
        this.mPcTodoListType = Integer.MIN_VALUE;
        notifyDataSetChanged();
    }

    public boolean isTodoCategorySelected() {
        return this.mPcTodoListType != Integer.MIN_VALUE;
    }

    @Override // com.miui.pc.frame.ExpandableInterface
    public int measureItemHeight(miuix.recyclerview.widget.RecyclerView recyclerView) {
        TodoCategoryItem todoCategoryItemNewInstance = TodoCategoryItem.newInstance((ViewGroup) recyclerView);
        todoCategoryItemNewInstance.bind(this.mItems.get(0).intValue(), 0);
        recyclerView.getLayoutManager().measureChild(todoCategoryItemNewInstance.itemView, 0, 0);
        return todoCategoryItemNewInstance.itemView.getMeasuredHeight();
    }
}
