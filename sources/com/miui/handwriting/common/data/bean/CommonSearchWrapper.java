package com.miui.handwriting.common.data.bean;

import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CommonSearchWrapper {
    public int searchPageIndex;
    public Map<String, Rect> searchRectMap;
    public float searchSpannedTextSize;
    public String searchText = null;
    public RectF searchSpannedRect = null;
    public String searchSpannedRectContent = null;
    public int searchColor = -1;
    public int searchAlpha = 255;
}
