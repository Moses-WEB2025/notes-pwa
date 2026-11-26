package com.miui.handwriting.common.tools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes3.dex */
public class SearchThreadHelper {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE;
    private static SearchThreadHelper mHelper;
    private ThreadPoolExecutor mExecutor;
    private ThreadPoolExecutor mLowPriorityExecutor;
    private LinkedBlockingQueue<Runnable> mTasks;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = Math.max(2, Math.min(iAvailableProcessors - 1, 4));
        MAXIMUM_POOL_SIZE = (iAvailableProcessors * 2) + 1;
    }
}
