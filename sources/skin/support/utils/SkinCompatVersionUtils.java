package skin.support.utils;

import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes6.dex */
public final class SkinCompatVersionUtils {
    private static final String TAG = "SkinCompatUtils";
    private static Class<?> sV4DrawableWrapperClass;
    private static Method sV4DrawableWrapperGetM;
    private static Method sV4DrawableWrapperSetM;
    private static Class<?> sV4WrappedDrawableClass;
    private static Method sV4WrappedDrawableGetM;
    private static Method sV4WrappedDrawableSetM;
    private static Class<?> sV7DrawableWrapperClass;
    private static Method sV7DrawableWrapperGetM;
    private static Method sV7DrawableWrapperSetM;

    static {
        try {
            sV4WrappedDrawableClass = Class.forName("android.support.v4.graphics.drawable.WrappedDrawable");
        } catch (ClassNotFoundException unused) {
            if (Slog.DEBUG) {
                Slog.i(TAG, "hasV4WrappedDrawable = false");
            }
        }
        try {
            sV4DrawableWrapperClass = Class.forName("android.support.v4.graphics.drawable.DrawableWrapper");
        } catch (ClassNotFoundException unused2) {
            if (Slog.DEBUG) {
                Slog.i(TAG, "hasV4DrawableWrapper = false");
            }
        }
        try {
            sV7DrawableWrapperClass = Class.forName("android.support.v7.graphics.drawable.DrawableWrapper");
        } catch (ClassNotFoundException unused3) {
            if (Slog.DEBUG) {
                Slog.i(TAG, "hasV7DrawableWrapper = false");
            }
        }
    }

    public static boolean hasV4WrappedDrawable() {
        return sV4WrappedDrawableClass != null;
    }

    public static boolean isV4WrappedDrawable(Drawable drawable) {
        Class<?> cls = sV4WrappedDrawableClass;
        return cls != null && cls.isAssignableFrom(drawable.getClass());
    }

    public static Drawable getV4WrappedDrawableWrappedDrawable(Drawable drawable) {
        Class<?> cls = sV4WrappedDrawableClass;
        if (cls != null) {
            if (sV4WrappedDrawableGetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("getWrappedDrawable", new Class[0]);
                    sV4WrappedDrawableGetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV4WrappedDrawableWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV4WrappedDrawableGetM;
            if (method != null) {
                try {
                    return (Drawable) method.invoke(drawable, new Object[0]);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV4WrappedDrawableWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
        return drawable;
    }

    public static void setV4WrappedDrawableWrappedDrawable(Drawable drawable, Drawable drawable2) {
        Class<?> cls = sV4WrappedDrawableClass;
        if (cls != null) {
            if (sV4WrappedDrawableSetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("setWrappedDrawable", Drawable.class);
                    sV4WrappedDrawableSetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV4WrappedDrawableWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV4WrappedDrawableSetM;
            if (method != null) {
                try {
                    method.invoke(drawable, drawable2);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV4WrappedDrawableWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
    }

    public static boolean hasV4DrawableWrapper() {
        return sV4DrawableWrapperClass != null;
    }

    public static boolean isV4DrawableWrapper(Drawable drawable) {
        Class<?> cls = sV4DrawableWrapperClass;
        return cls != null && cls.isAssignableFrom(drawable.getClass());
    }

    public static Drawable getV4DrawableWrapperWrappedDrawable(Drawable drawable) {
        Class<?> cls = sV4DrawableWrapperClass;
        if (cls != null) {
            if (sV4DrawableWrapperGetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("getWrappedDrawable", new Class[0]);
                    sV4DrawableWrapperGetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV4DrawableWrapperWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV4DrawableWrapperGetM;
            if (method != null) {
                try {
                    return (Drawable) method.invoke(drawable, new Object[0]);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV4DrawableWrapperWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
        return drawable;
    }

    public static void setV4DrawableWrapperWrappedDrawable(Drawable drawable, Drawable drawable2) {
        Class<?> cls = sV4DrawableWrapperClass;
        if (cls != null) {
            if (sV4DrawableWrapperSetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("setWrappedDrawable", Drawable.class);
                    sV4DrawableWrapperSetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV4DrawableWrapperWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV4DrawableWrapperSetM;
            if (method != null) {
                try {
                    method.invoke(drawable, drawable2);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV4DrawableWrapperWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
    }

    public static boolean hasV7DrawableWrapper() {
        return sV7DrawableWrapperClass != null;
    }

    public static boolean isV7DrawableWrapper(Drawable drawable) {
        Class<?> cls = sV7DrawableWrapperClass;
        return cls != null && cls.isAssignableFrom(drawable.getClass());
    }

    public static Drawable getV7DrawableWrapperWrappedDrawable(Drawable drawable) {
        Class<?> cls = sV7DrawableWrapperClass;
        if (cls != null) {
            if (sV7DrawableWrapperGetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("getWrappedDrawable", new Class[0]);
                    sV7DrawableWrapperGetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV7DrawableWrapperWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV7DrawableWrapperGetM;
            if (method != null) {
                try {
                    return (Drawable) method.invoke(drawable, new Object[0]);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "getV7DrawableWrapperWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
        return drawable;
    }

    public static void setV7DrawableWrapperWrappedDrawable(Drawable drawable, Drawable drawable2) {
        Class<?> cls = sV7DrawableWrapperClass;
        if (cls != null) {
            if (sV7DrawableWrapperSetM == null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("setWrappedDrawable", Drawable.class);
                    sV7DrawableWrapperSetM = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV7DrawableWrapperWrappedDrawable No Such Method");
                    }
                }
            }
            Method method = sV7DrawableWrapperSetM;
            if (method != null) {
                try {
                    method.invoke(drawable, drawable2);
                } catch (Exception e) {
                    if (Slog.DEBUG) {
                        Slog.i(TAG, "setV7DrawableWrapperWrappedDrawable invoke error: " + e);
                    }
                }
            }
        }
    }
}
