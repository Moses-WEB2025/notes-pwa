package com.miui.duokantext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class Render {
    Bitmap mBitmap;
    Canvas mCanvas;
    private long mNativeHandle;
    StateInfo mCurrentState = new StateInfo();
    List<StateInfo> mStateInfos = new ArrayList();

    private native void nativeDestroy();

    private native void nativeInit();

    private native void nativeUpdateRender(Bitmap bitmap, int i, int i2, boolean z);

    static {
        System.loadLibrary("duokantext");
    }

    private static class StateInfo {
        float mTransX;
        float mTransY;

        void translate(float f, float f2) {
            this.mTransX += f;
            this.mTransY += f2;
        }

        StateInfo(StateInfo stateInfo) {
            this.mTransX = 0.0f;
            this.mTransY = 0.0f;
            this.mTransX = stateInfo.mTransX;
            this.mTransY = stateInfo.mTransY;
        }

        StateInfo() {
            this.mTransX = 0.0f;
            this.mTransY = 0.0f;
        }
    }

    public Render(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mCanvas = new Canvas(this.mBitmap);
        nativeInit();
    }

    public Render() {
        nativeInit();
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        Canvas canvas = this.mCanvas;
        if (canvas == null) {
            this.mCanvas = new Canvas(bitmap);
        } else {
            canvas.setBitmap(bitmap);
        }
    }

    public Canvas getCanvas() {
        return this.mCanvas;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public float getTranslateX() {
        return this.mCurrentState.mTransX;
    }

    public float getTranslateY() {
        return this.mCurrentState.mTransY;
    }

    public void translate(float f, float f2) {
        this.mCanvas.translate(f, f2);
        this.mCurrentState.translate(f, f2);
    }

    public void save() {
        this.mStateInfos.add(this.mCurrentState);
        this.mCurrentState = new StateInfo(this.mCurrentState);
        this.mCanvas.save();
    }

    public void restore() {
        int size = this.mStateInfos.size();
        if (size > 0) {
            int i = size - 1;
            this.mCurrentState = this.mStateInfos.get(i);
            this.mStateInfos.remove(i);
        }
        this.mCanvas.restore();
    }

    public void destroy() {
        nativeDestroy();
        this.mNativeHandle = 0L;
    }

    public void updateRender(int i, int i2, boolean z) {
        nativeUpdateRender(this.mBitmap, i, i2, z);
    }
}
