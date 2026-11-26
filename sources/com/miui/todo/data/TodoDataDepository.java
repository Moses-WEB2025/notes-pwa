package com.miui.todo.data;

import com.miui.todo.data.mode.CustomSortModeImpl;
import com.miui.todo.data.mode.IDataSource;

/* JADX INFO: loaded from: classes4.dex */
public class TodoDataDepository {
    private static volatile TodoDataDepository mInstance;
    private final IDataSource mCurrentDataSource;

    protected TodoDataDepository(boolean z) {
        this.mCurrentDataSource = new CustomSortModeImpl(z);
    }

    public static TodoDataDepository getInstance() {
        if (mInstance == null) {
            synchronized (TodoDataDepository.class) {
                if (mInstance == null) {
                    mInstance = new TodoDataDepository(false);
                }
            }
        }
        return mInstance;
    }

    public IDataSource getDataSource() {
        return this.mCurrentDataSource;
    }
}
