package com.miui.richeditor;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.SpannableString;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.style.SelectableSpanListener;
import com.miui.richeditor.style.render.IEditorImageRender;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.SmoothDrawHelper;

/* JADX INFO: loaded from: classes4.dex */
public interface IEditorContext {
    public static final int FONT_SIZE_LARGE = 2;
    public static final int FONT_SIZE_MID = 1;
    public static final int FONT_SIZE_NORMAL = 0;

    SpannableString createHintEntrySpanText();

    void deleteImageSpan(BaseImageSpan baseImageSpan, boolean z);

    void deleteImageSpanAfterDragDrop(BaseImageSpan baseImageSpan);

    int getBgHighLightColor();

    int getContentWidth();

    Context getContext();

    View getEditAreaView();

    Theme getEditorTheme();

    int[] getEnabledStateSet();

    float getFontInnerScale();

    float getFontScale();

    int getFontSizeId();

    float[] getFontSizesByLevel();

    IEditorImageRender getImageRender();

    int getKeywordStyle();

    int getLineSpacing();

    int getLineTextHeight(boolean z, float f);

    LinkCardSpanHelper getLinkCardSpanHelper();

    Paint getPaint();

    int[] getPressedEnabledStateSet();

    SmoothDrawHelper getRadiusDrawHelper();

    View getRootContainer();

    SelectableSpanListener getSelectableSpanListener();

    VisualStyleFactory getStyleFactory();

    float getTextSize();

    AppCompatEditText getView();

    int getViewDensityDpi();

    Rect getVisibleRect();

    int getWindowWidth();

    boolean hasUnknownTag();

    boolean isNeedEditorLineHeight();

    boolean isReadOnlyMode();

    @Deprecated
    void onCheckboxClick(int[] iArr);

    @Deprecated
    void onContactClick(BaseImageSpan baseImageSpan, int[] iArr);

    void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr);

    @Deprecated
    void onImageAnnotationClick(BaseImageSpan baseImageSpan, int[] iArr);

    @Deprecated
    void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr);

    @Deprecated
    void onImageSpanLongClick(BaseImageSpan baseImageSpan, int[] iArr);

    @Deprecated
    void onImageSpanRightClick(BaseImageSpan baseImageSpan, int[] iArr);

    @Deprecated
    void onLinkCardClick(LinkCardSpan linkCardSpan, int[] iArr);

    @Deprecated
    void onLinkCardLongClick(LinkCardSpan linkCardSpan, int[] iArr);

    void onSpanClick(Object obj, int[] iArr);

    void onSpanEditableContentClick(Object obj, int[] iArr);

    void onSpanRightClick(Object obj, int[] iArr);

    void refreshAllImageSpans();

    void refreshContainer();

    void setEditAreaView(View view);

    void setHasUnknownTag(boolean z);

    boolean shouldNotifyEditChanged();

    void startDragLinkCardAnim(LinkCardSpan linkCardSpan, int[] iArr);

    void startDragSpanAnim(BaseImageSpan baseImageSpan, int i, int i2);
}
