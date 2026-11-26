package com.miui.common.tool.async;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: TaskExecutingException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0017\u0012\u000e\u0010\u0003\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u0005¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/miui/common/tool/async/TaskExecutingException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Exception;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TaskExecutingException extends RuntimeException {
    public TaskExecutingException(Exception exc) {
        super(exc);
    }
}
