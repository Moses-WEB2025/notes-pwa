package androidx.test.runner.intercepting;

import android.app.Activity;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface InterceptingActivityFactory {
    Activity create(ClassLoader classLoader, String className, Intent intent);

    boolean shouldIntercept(ClassLoader classLoader, String className, Intent intent);
}
