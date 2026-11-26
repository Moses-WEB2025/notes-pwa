package xc.engine.operate.canvas.draw;

import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import java.util.ArrayList;
import java.util.List;
import xc.engine.operate.canvas.draw.helper.BitmapIndex;

/* JADX INFO: loaded from: classes6.dex */
public class BackgroundInfo {
    public List<BitmapIndex> bitmapIndexList;
    public boolean isBackgroundLineVisible;
    public boolean isBackgroundTextVisible;
    public int lineDuration;
    public Paint paint;
    public TextPaint textPaint;
    public float visibleHeight;
    public float visibleWidth;
    public Path linePath = new Path();
    public Path bgLinesPath = new Path();

    public BackgroundInfo() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.isBackgroundLineVisible = false;
        this.lineDuration = 100;
        this.isBackgroundTextVisible = false;
        this.paint.setColor(-16777216);
        this.paint.setStrokeWidth(2);
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        TextPaint textPaint = new TextPaint();
        this.textPaint = textPaint;
        textPaint.setTextSize(24.0f);
        this.textPaint.setColor(-16711936);
        this.bitmapIndexList = new ArrayList();
    }

    void updateBackgroundLines() {
        this.linePath.reset();
        int iCeil = (int) Math.ceil((this.visibleHeight / 0.2f) / this.lineDuration);
        for (int i = 0; i <= iCeil; i++) {
            if (i != 0) {
                this.linePath.moveTo(0.0f, this.lineDuration * i);
                this.linePath.lineTo(this.visibleWidth, this.lineDuration * i);
            }
        }
    }
}
