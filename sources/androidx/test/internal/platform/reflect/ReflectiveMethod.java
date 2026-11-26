package androidx.test.internal.platform.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ReflectiveMethod<T> {
    private final String className;
    private final Class<?> clazz;
    private boolean initialized;
    private Method method;
    private final String methodName;
    private final Class<?>[] paramTypes;

    public ReflectiveMethod(String className, String methodName, Class<?>... paramTypes) {
        this.initialized = false;
        this.className = className;
        this.clazz = null;
        this.paramTypes = paramTypes;
        this.methodName = methodName;
    }

    public ReflectiveMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        this.initialized = false;
        this.className = null;
        this.clazz = clazz;
        this.paramTypes = paramTypes;
        this.methodName = methodName;
    }

    public T invoke(Object obj, Object... objArr) throws ReflectionException {
        try {
            initIfNecessary();
            return (T) this.method.invoke(obj, objArr);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        } catch (IllegalAccessException e2) {
            throw new ReflectionException(e2);
        } catch (NoSuchMethodException e3) {
            throw new ReflectionException(e3);
        } catch (InvocationTargetException e4) {
            throw new ReflectionException(e4);
        }
    }

    public T invokeStatic(Object... paramValues) throws ReflectionException {
        return invoke(null, paramValues);
    }

    private synchronized void initIfNecessary() throws NoSuchMethodException, ClassNotFoundException {
        if (this.initialized) {
            return;
        }
        Method declaredMethod = getClazz().getDeclaredMethod(this.methodName, this.paramTypes);
        this.method = declaredMethod;
        declaredMethod.setAccessible(true);
        this.initialized = true;
    }

    private Class<?> getClazz() throws ClassNotFoundException {
        Class<?> cls = this.clazz;
        return cls == null ? Class.forName(this.className) : cls;
    }
}
