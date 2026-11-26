package xc.engine.core.algorithm.recognize.data;

import android.graphics.Path;

/* JADX INFO: loaded from: classes6.dex */
public abstract class RecognizeWithArrowData extends RecognizeData {
    private int arrowHeight = 30;
    private int arrowBottom = 10;

    public int getArrowHeight() {
        return this.arrowHeight;
    }

    public void setArrowHeight(int i) {
        if (i <= 0) {
            return;
        }
        this.arrowHeight = i;
    }

    public void setArrowBottom(int i) {
        if (i <= 0) {
            return;
        }
        this.arrowBottom = i;
    }

    public int getArrowBottom() {
        return this.arrowBottom;
    }

    public Path getArrowPath(Path path, float f, float f2, float f3, float f4, boolean z) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        try {
            float fSqrt = (float) Math.sqrt((f5 * f5) + (f6 * f6));
            float f7 = this.arrowHeight / fSqrt;
            float f8 = f3 - (f7 * f5);
            float f9 = f4 - (f7 * f6);
            float f10 = this.arrowBottom / fSqrt;
            if (z) {
                path.moveTo((f10 * f6) + f8, f9 - (f10 * f5));
            } else {
                path.lineTo((f10 * f6) + f8, f9 - (f10 * f5));
            }
            path.lineTo(f3, f4);
            path.lineTo(f8 - (f6 * f10), f9 + (f10 * f5));
            path.lineTo(f3, f4);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return path;
        }
    }
}
