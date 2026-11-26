package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.share.element.AudioElement;
import com.miui.richeditor.style.render.AudioSpanHelper;

/* JADX INFO: loaded from: classes4.dex */
public class AudioElementRender extends ElementRender<AudioElement> {
    private static final String TAG = "AudioUtils";
    private boolean isShowSummaryIcon;
    private String mAudioDescription;
    private int mAudioTime;
    private AudioSpanHelper mDrawHelper;
    private Rect mDrawRect;
    private AudioElement mElement;

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
    }

    public AudioElementRender(Context context, AudioElement audioElement, int i, int i2, long j) {
        super(context, audioElement, i, i2, j);
        this.mDrawHelper = null;
        this.mDrawRect = new Rect();
        this.mElement = audioElement;
        this.mAudioTime = audioElement.getTime();
        this.mAudioDescription = this.mElement.getAudioDescription();
        this.isShowSummaryIcon = this.mElement.isShowSummaryIcon();
        if (this.mDrawHelper == null) {
            this.mDrawHelper = new AudioSpanHelper(ResourceManager.getTheme((int) j));
        }
        layout();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void render(Canvas canvas) {
        Bitmap bitmapCreateBitmap = createBitmap();
        if (bitmapCreateBitmap != null) {
            canvas.drawBitmap(bitmapCreateBitmap, this.mElement.getMarginLeft(), this.mElement.getMarginTop(), (Paint) null);
        }
    }

    private void layout() {
        this.mWidth = (this.mWidth - this.mElement.getMarginLeft()) - this.mElement.getMarginRight();
        int summary = this.mDrawHelper.setSummary(this.mAudioDescription, this.isShowSummaryIcon, getWidth());
        this.mHeight = this.mElement.getMarginTop() + summary + this.mElement.getMarginBottom();
        this.mDrawRect.set(0, 0, getWidth(), summary);
    }

    private Bitmap createBitmap() {
        Rect rect = this.mDrawRect;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        this.mDrawHelper.drawBg(rect, new Canvas(bitmapCreateBitmap), this.mAudioTime);
        return bitmapCreateBitmap;
    }
}
