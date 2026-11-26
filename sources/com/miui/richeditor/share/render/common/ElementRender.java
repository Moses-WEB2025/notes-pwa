package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.miui.richeditor.share.element.AudioElement;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.element.LineElement;
import com.miui.richeditor.share.element.LinkCardElement;
import com.miui.richeditor.share.element.ListElement;
import com.miui.richeditor.share.element.ScaledTextElement;
import com.miui.richeditor.share.element.TextElement;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ElementRender<T extends Element> {
    protected Rect mBounds;
    protected Context mContext;
    protected T mElement;
    protected int mHeight;
    protected boolean mIsDestroying = false;
    protected long mThemeId;
    protected int mTop;
    protected int mWidth;

    public abstract void draw(Canvas canvas);

    public static ElementRender<? extends Element> create(Context context, Element element, int i, int i2, long j) {
        switch (element.getType()) {
            case 0:
                return new TextElementRender(context, (TextElement) element, i, i2, j);
            case 1:
                return new ImageElementRender(context, (ImageElement) element, i, i2, j);
            case 2:
                return new ListElementRender(context, (ListElement) element, i, i2, j);
            case 3:
                return new ScaledTextElementRender(context, (ScaledTextElement) element, i, i2, j);
            case 4:
                return new LineElementRender(context, (LineElement) element, i, i2, j);
            case 5:
                return new LinkCardElementRender(context, (LinkCardElement) element, i, i2, j);
            case 6:
                return new AudioElementRender(context, (AudioElement) element, i, i2, j);
            default:
                throw new IllegalArgumentException("Unknown type: " + element.getType());
        }
    }

    public ElementRender(Context context, T t, int i, int i2, long j) {
        this.mContext = context;
        this.mElement = t;
        this.mTop = i;
        this.mWidth = i2;
        this.mThemeId = j;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Element getElement() {
        return this.mElement;
    }

    public int getTop() {
        return this.mTop;
    }

    public int getBottom() {
        return this.mTop + this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Rect getBounds() {
        if (this.mBounds == null) {
            this.mBounds = new Rect(0, this.mTop, this.mWidth, getBottom());
        }
        return this.mBounds;
    }

    public void render(Canvas canvas) {
        draw(canvas);
    }

    public void destroy() {
        this.mIsDestroying = true;
        this.mContext = null;
    }
}
