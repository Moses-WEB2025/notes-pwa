package com.miui.todo.data.utils;

import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.provider.TodoDatabaseAccessUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DragSort {
    public static final long OVER_FLOW_CODE = -1;
    public static final long SEED_NUM = 1048576;

    public static long getBigSortId() {
        long maxSortID = PreferenceUtils.getMaxSortID() + 1048576;
        PreferenceUtils.setMaxSortID(maxSortID);
        return maxSortID;
    }

    public static long getMiddleSortId(long j, long j2) {
        if (j2 - j <= 1) {
            return -1L;
        }
        return (j + j2) / 2;
    }

    public static long getLittleSortId(long j) {
        if (j <= 1) {
            return -1L;
        }
        return j / 2;
    }

    public static void UpdateAllUnFinished() {
        PreferenceUtils.setMaxSortID(TodoDatabaseAccessUtils.UpdateAllUnFinished());
    }
}
