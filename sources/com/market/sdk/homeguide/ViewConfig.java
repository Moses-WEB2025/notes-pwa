package com.market.sdk.homeguide;

import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class ViewConfig implements Serializable {
    private static final long serialVersionUID = 1;
    public int iconPaddingBottom;
    public int iconPaddingLeft;
    public int iconPaddingRight;
    public int iconPaddingTop;
    public int titleHeight;
    public int titlePaddingBottom;
    public int titlePaddingLeft;
    public int titlePaddingRight;
    public int titlePaddingTop;
    public int titleTextColor;
    public float titleTextSize;
    public int titleWidth;
    public int[] titleLocation = new int[2];
    public int[] iconLocation = new int[2];

    public boolean isValid() {
        return (this.iconLocation == null || this.titleLocation == null) ? false : true;
    }
}
