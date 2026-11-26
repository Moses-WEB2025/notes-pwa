package com.miui.libhandwritingpage.newbean.beanCurve;

import com.miui.libhandwritingpage.newbean.beanTouch.TData;
import java.util.List;
import xc.engine.core.graphics.pen.PenType;

/* JADX INFO: loaded from: classes3.dex */
public class CurveBean {
    public int curveColor;
    public PenType curveType;
    public float curveWidth;
    public boolean isShapeType;
    public List<TData> pointDataList;

    public CurveBean(List<TData> list, float f, PenType penType, int i, boolean z) {
        this.pointDataList = list;
        this.curveWidth = f;
        this.curveType = penType;
        this.curveColor = i;
        this.isShapeType = z;
    }
}
