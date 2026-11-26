package com.android.soundrecorder.ai.base.aop;

import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InterceptorChain.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J!\u0010\u0007\u001a\u00020\u00002\u0014\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\t¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001J\u0006\u0010\f\u001a\u00020\rR\u0018\u0010\u0003\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/android/soundrecorder/ai/base/aop/InterceptorChain;", "", "()V", "head", "Ljava/lang/Class;", "Lcom/android/soundrecorder/ai/base/aop/BaseInterceptor;", "next", "buildChain", "interceptors", "", "([Ljava/lang/Class;)Lcom/android/soundrecorder/ai/base/aop/InterceptorChain;", "interceptReturnValue", "process", "", "base_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class InterceptorChain {
    private Class<? extends BaseInterceptor> head;
    private Class<? extends BaseInterceptor> next;

    public final InterceptorChain buildChain(Class<? extends BaseInterceptor>[] interceptors) {
        Constructor<? extends BaseInterceptor> declaredConstructor;
        BaseInterceptor baseInterceptorNewInstance;
        Constructor<? extends BaseInterceptor> declaredConstructor2;
        Intrinsics.checkNotNullParameter(interceptors, "interceptors");
        if (interceptors.length == 0) {
            return this;
        }
        Class<? extends BaseInterceptor> next = null;
        for (Class<? extends BaseInterceptor> cls : interceptors) {
            if (this.head == null) {
                this.head = cls;
                next = cls;
            } else {
                BaseInterceptor baseInterceptorNewInstance2 = (next == null || (declaredConstructor2 = next.getDeclaredConstructor(new Class[0])) == null) ? null : declaredConstructor2.newInstance(new Object[0]);
                if (baseInterceptorNewInstance2 != null) {
                    baseInterceptorNewInstance2.setNext(cls);
                }
                next = (next == null || (declaredConstructor = next.getDeclaredConstructor(new Class[0])) == null || (baseInterceptorNewInstance = declaredConstructor.newInstance(new Object[0])) == null) ? null : baseInterceptorNewInstance.getNext();
            }
        }
        return this;
    }

    public final boolean process() throws NoSuchMethodException {
        BaseInterceptor baseInterceptorNewInstance;
        BaseInterceptor baseInterceptorNewInstance2;
        Class<? extends BaseInterceptor> next = this.head;
        while (next != null) {
            Constructor<? extends BaseInterceptor> declaredConstructor = next.getDeclaredConstructor(new Class[0]);
            if (declaredConstructor != null && (baseInterceptorNewInstance2 = declaredConstructor.newInstance(new Object[0])) != null && baseInterceptorNewInstance2.process()) {
                return true;
            }
            Constructor<? extends BaseInterceptor> declaredConstructor2 = next.getDeclaredConstructor(new Class[0]);
            next = (declaredConstructor2 == null || (baseInterceptorNewInstance = declaredConstructor2.newInstance(new Object[0])) == null) ? null : baseInterceptorNewInstance.getNext();
        }
        return false;
    }

    public final Object interceptReturnValue() {
        Constructor<? extends BaseInterceptor> declaredConstructor;
        BaseInterceptor baseInterceptorNewInstance;
        Class<? extends BaseInterceptor> cls = this.head;
        if (cls == null || (declaredConstructor = cls.getDeclaredConstructor(new Class[0])) == null || (baseInterceptorNewInstance = declaredConstructor.newInstance(new Object[0])) == null) {
            return null;
        }
        return baseInterceptorNewInstance.interceptReturnValue();
    }
}
