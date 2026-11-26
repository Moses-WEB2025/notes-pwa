package xc.richtext.api;

import android.graphics.RectF;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes6.dex */
public interface ScreenRenderProvider {
    RectF covertSSRectToWSRect(RectF rectF);

    RectF covertWSRectToSSRect(RectF rectF);

    void doMove(float f, float f2);

    void doRotate(float f);

    void doScale(float f, float f2, float f3);

    void enableEditMode();

    float getDrawRatio();

    MathSystem getMathSystem();

    int getPageIndexByScreenSpacePoint(float f, float f2);

    int getPageIndexByWorldSpacePoint(float f, float f2);

    float getSaveRatio();

    boolean isCurrentPageBgDark();

    boolean isPenEngineReleased();

    void refresh();

    void refreshEditText(SpannedData spannedData);

    void refreshSpan();

    void selected();
}
