package com.miui.libhandwritingpage.newbean;

import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class SearchWrapper {
    public int searchAlpha;
    public int searchColor;
    public int searchPageIndex;
    public Map<String, Rect> searchRectMap;
    public RectF searchSpannedRect;
    public String searchSpannedRectContent;
    public float searchSpannedTextSize;
    public String searchText;

    public SearchWrapper() {
        this.searchText = null;
        this.searchSpannedRect = null;
        this.searchSpannedRectContent = null;
        this.searchColor = -1;
        this.searchAlpha = 255;
    }

    public SearchWrapper(int i, Map<String, Rect> map, String str, RectF rectF, String str2, float f, int i2, int i3) {
        this.searchPageIndex = i;
        this.searchRectMap = map;
        this.searchText = str;
        this.searchSpannedRect = rectF;
        this.searchSpannedRectContent = str2;
        this.searchSpannedTextSize = f;
        this.searchColor = i2;
        this.searchAlpha = i3;
    }
}
