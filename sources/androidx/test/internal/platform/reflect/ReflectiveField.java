package androidx.test.internal.platform.reflect;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class ReflectiveField<T> {
    private final String className;
    private final Class<?> clazz;
    private Field field;
    private final String fieldName;
    private boolean initialized;

    public ReflectiveField(String className, String fieldName) {
        this.initialized = false;
        this.clazz = null;
        this.className = className;
        this.fieldName = fieldName;
    }

    public ReflectiveField(Class<?> clazz, String fieldName) {
        this.initialized = false;
        this.clazz = clazz;
        this.className = null;
        this.fieldName = fieldName;
    }

    public T get(Object obj) throws ReflectionException {
        try {
            initIfNecessary();
            return (T) this.field.get(obj);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        } catch (IllegalAccessException e2) {
            throw new ReflectionException(e2);
        } catch (NoSuchFieldException e3) {
            throw new ReflectionException(e3);
        }
    }

    private synchronized void initIfNecessary() throws NoSuchFieldException, ClassNotFoundException {
        if (this.initialized) {
            return;
        }
        Field declaredField = getClazz().getDeclaredField(this.fieldName);
        this.field = declaredField;
        declaredField.setAccessible(true);
        this.initialized = true;
    }

    private Class<?> getClazz() throws ClassNotFoundException {
        Class<?> cls = this.clazz;
        return cls == null ? Class.forName(this.className) : cls;
    }
}
