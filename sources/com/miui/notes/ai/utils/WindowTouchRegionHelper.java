package com.miui.notes.ai.utils;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes4.dex */
public class WindowTouchRegionHelper {
    private View mRootView;
    private final String TAG = "WindowTouchRegionHelper";
    private boolean isRegistered = false;
    private Region mTouchRegion = new Region();
    private final Object mOnComputeInternalInsetsListener = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{ReflectUtils.getClass("android.view.ViewTreeObserver$OnComputeInternalInsetsListener")}, new OnComputeInternalInsetsListenerInvokeHandler(new OnComputeInternalInsetsListenerImpl()));

    public WindowTouchRegionHelper(View view) {
        this.mRootView = view;
    }

    public void registerOnComputeInternalInsetsListener() {
        View view = this.mRootView;
        if (view == null) {
            return;
        }
        this.isRegistered = true;
        ReflectUtils.callMethod(view.getViewTreeObserver(), "addOnComputeInternalInsetsListener", new Class[]{ReflectUtils.getClass("android.view.ViewTreeObserver$OnComputeInternalInsetsListener")}, this.mOnComputeInternalInsetsListener);
    }

    public void unRegisterOnComputeInternalInsetsListener() {
        View view = this.mRootView;
        if (view == null) {
            return;
        }
        this.isRegistered = false;
        ReflectUtils.callMethod(view.getViewTreeObserver(), "removeOnComputeInternalInsetsListener", new Class[]{ReflectUtils.getClass("android.view.ViewTreeObserver$OnComputeInternalInsetsListener")}, this.mOnComputeInternalInsetsListener);
    }

    public boolean isRegistered() {
        return this.isRegistered;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class OnComputeInternalInsetsListenerImpl {
        private static final int TOUCHABLE_INSETS_REGION = 3;

        private OnComputeInternalInsetsListenerImpl() {
        }

        public void onComputeInternalInsets(Object obj) {
            ReflectUtils.callMethod(ReflectUtils.getClass("android.view.ViewTreeObserver$InternalInsetsInfo"), obj, "setTouchableInsets", new Class[]{Integer.TYPE}, 3);
            ReflectUtils.getFieldValue(ReflectUtils.getClass("android.view.ViewTreeObserver$InternalInsetsInfo"), obj, "touchableRegion", Region.class).ifPresent(new Consumer() { // from class: com.miui.notes.ai.utils.WindowTouchRegionHelper$OnComputeInternalInsetsListenerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    this.f$0.lambda$onComputeInternalInsets$0(obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onComputeInternalInsets$0(Object obj) {
            ((Region) obj).set(WindowTouchRegionHelper.this.mTouchRegion);
        }
    }

    private class OnComputeInternalInsetsListenerInvokeHandler implements InvocationHandler {
        private final OnComputeInternalInsetsListenerImpl mOnComputeInternalInsetsListener;

        public OnComputeInternalInsetsListenerInvokeHandler(OnComputeInternalInsetsListenerImpl onComputeInternalInsetsListenerImpl) {
            this.mOnComputeInternalInsetsListener = onComputeInternalInsetsListenerImpl;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) {
            return ReflectUtils.callMethod(OnComputeInternalInsetsListenerImpl.class, this.mOnComputeInternalInsetsListener, method.getName(), getType(method.getParameterTypes()), objArr).orElse(null);
        }

        private Class<?>[] getType(Class<?>[] clsArr) {
            Class<?>[] clsArr2 = new Class[clsArr.length];
            for (int i = 0; i < clsArr.length; i++) {
                clsArr2[i] = Object.class;
            }
            return clsArr2;
        }
    }

    public void updateTouchAreaRect(Rect rect) {
        this.mTouchRegion.set(rect);
    }

    public void updateTouchUnionRect(Rect rect) {
        this.mTouchRegion.union(rect);
    }

    public void updateTouchAreaRegion(Region region) {
        this.mTouchRegion.set(region);
    }
}
