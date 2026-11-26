package com.miui.pad.feature.todo.navigation;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TodoCategoryAdapter extends RecyclerView.Adapter<TodoCategoryItem> {
    public static final int INVALID_NUM = -1;
    public static final int INVALID_TYPE = Integer.MIN_VALUE;
    private View.OnClickListener mOnClickListener;
    private int mUnFinishNum = -1;
    private int mFinishNum = -1;
    private List<Integer> mItems = new ArrayList<Integer>() { // from class: com.miui.pad.feature.todo.navigation.TodoCategoryAdapter.1
        {
            add(0);
            add(1);
        }
    };
    protected int mTodoListType = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public TodoCategoryItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        TodoCategoryItem todoCategoryItemNewInstance = TodoCategoryItem.newInstance(viewGroup);
        todoCategoryItemNewInstance.setOnClickListener(this.mOnClickListener);
        return todoCategoryItemNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(TodoCategoryItem todoCategoryItem, int i) {
        if (i == 0) {
            todoCategoryItem.bind(this.mItems.get(i).intValue(), this.mTodoListType, this.mUnFinishNum);
        } else if (i == 1) {
            todoCategoryItem.bind(this.mItems.get(i).intValue(), this.mTodoListType, this.mFinishNum);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setSelectedType(int i) {
        this.mTodoListType = i;
        notifyDataSetChanged();
    }

    public int getUnFinishNum() {
        return this.mUnFinishNum;
    }

    public void setUnFinishNum(int i) {
        this.mUnFinishNum = i;
    }

    public int getFinishNum() {
        return this.mFinishNum;
    }

    public void setFinishNum(int i) {
        this.mFinishNum = i;
    }
}
