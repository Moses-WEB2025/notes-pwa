package xc.engine.operate;

import android.graphics.Canvas;
import android.graphics.Matrix;

/* JADX INFO: loaded from: classes6.dex */
public interface IDrawStateListener {
    void onCanvasRelease();

    void onCanvasScale(Matrix matrix);

    void onCanvasScaleModeChange(boolean z);

    void onCanvasShowSelectBitmap();

    void onCanvasVisible(boolean z);

    void onRenderByCanvas(Canvas canvas);
}
