package com.miui.doodle.feature.tools;

import kotlin.Metadata;

/* JADX INFO: compiled from: IBrushConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J \u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH&J\b\u0010\u0018\u001a\u00020\u0003H&R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\t\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0018\u0010\r\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015¨\u0006\u0019À\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/tools/IBrushConfig;", "", "show", "", "dismiss", "isShow", "", "()Z", "updateParams", "colorInt", "", "alpha", "", "strokeLevel", "getAlpha", "()F", "setAlpha", "(F)V", "getColorInt", "()I", "setColorInt", "(I)V", "getStrokeLevel", "setStrokeLevel", "clearViewAnimator", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IBrushConfig {
    void clearViewAnimator();

    void dismiss();

    float getAlpha();

    int getColorInt();

    int getStrokeLevel();

    boolean isShow();

    void setAlpha(float f);

    void setColorInt(int i);

    void setStrokeLevel(int i);

    void show();

    void updateParams(int colorInt, float alpha, int strokeLevel);
}
