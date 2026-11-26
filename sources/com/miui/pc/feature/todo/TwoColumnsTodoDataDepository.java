package com.miui.pc.feature.todo;

import com.miui.todo.data.TodoDataDepository;

/* JADX INFO: loaded from: classes4.dex */
public class TwoColumnsTodoDataDepository extends TodoDataDepository {
    private static volatile TwoColumnsTodoDataDepository mInstance;

    private TwoColumnsTodoDataDepository() {
        super(true);
    }

    public static TwoColumnsTodoDataDepository getInstance() {
        if (mInstance == null) {
            synchronized (TwoColumnsTodoDataDepository.class) {
                if (mInstance == null) {
                    mInstance = new TwoColumnsTodoDataDepository();
                }
            }
        }
        return mInstance;
    }
}
