package com.xiaomi.passport.ui.settings.utils;

import android.os.Handler;
import android.view.WindowManager;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: classes4.dex */
public class PopNoRecordProxy implements InvocationHandler {
    private Object mWindowManager;

    public static PopNoRecordProxy instance() {
        return new PopNoRecordProxy();
    }

    public void noScreenRecord(PopupWindow popupWindow) {
        if (popupWindow == null) {
            return;
        }
        try {
            Field declaredField = PopupWindow.class.getDeclaredField("mWindowManager");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(popupWindow);
            this.mWindowManager = obj;
            if (obj == null) {
                return;
            }
            declaredField.set(popupWindow, Proxy.newProxyInstance(Handler.class.getClassLoader(), new Class[]{WindowManager.class}, this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if (method != null) {
            try {
                if (method.getName() != null && method.getName().equals("addView") && objArr != null && objArr.length == 2) {
                    setNoScreenRecord((WindowManager.LayoutParams) objArr[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method.invoke(this.mWindowManager, objArr);
    }

    private void setNoScreenRecord(WindowManager.LayoutParams layoutParams) {
        setFlags(layoutParams, 8192, 8192);
    }

    private void setAllowScreenRecord(WindowManager.LayoutParams layoutParams) {
        setFlags(layoutParams, 0, 8192);
    }

    private void setFlags(WindowManager.LayoutParams layoutParams, int i, int i2) {
        if (layoutParams == null) {
            return;
        }
        try {
            layoutParams.flags = (layoutParams.flags & (~i2)) | (i & i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
