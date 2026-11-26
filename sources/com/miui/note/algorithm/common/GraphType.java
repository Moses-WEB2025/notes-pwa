package com.miui.note.algorithm.common;

import com.miui.common.stat.NoteAppStat;

/* JADX INFO: loaded from: classes3.dex */
public enum GraphType {
    STRAIGHT_LINE("直线"),
    BROKEN_LINE("折线"),
    ARC_LINE("弧线"),
    TRIANGLE("三角形"),
    SQUARE("正方形"),
    RECTANGLE("矩形"),
    PENTAGON("五边形"),
    PENTAGRAM("五角星"),
    CIRCLE("圆"),
    ELLIPSE("椭圆"),
    OTHER(NoteAppStat.APP_NAME.other);

    public String desc;

    GraphType(String str) {
        this.desc = str;
    }
}
