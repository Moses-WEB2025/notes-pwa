package com.miui.libhandwritingpreview.interfaces;

import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.view.page.HeaderView;
import kotlin.Metadata;

/* JADX INFO: compiled from: MiuiHandWritingPreviewInterface.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u0003H&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0006H&J \u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\u0006H&J\b\u0010\u0015\u001a\u00020\u0003H&J\b\u0010\u0016\u001a\u00020\u0017H&J\b\u0010\u0018\u001a\u00020\u0013H&J\b\u0010\u0019\u001a\u00020\u0013H&J\u0018\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001dH&J\u0012\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010 H&J\u0012\u0010!\u001a\u00020\u00032\b\u0010\"\u001a\u0004\u0018\u00010#H&J\u0010\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020&H&J*\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u00132\b\u0010*\u001a\u0004\u0018\u00010+H&J\u0012\u0010,\u001a\u00020\u00032\b\u0010-\u001a\u0004\u0018\u00010.H&J\b\u0010/\u001a\u00020\u0003H&J\b\u00100\u001a\u00020\u0003H&¨\u00061À\u0006\u0003"}, d2 = {"Lcom/miui/libhandwritingpreview/interfaces/MiuiHandWritingPreviewInterface;", "", "initUIProperties", "", "setTitleAndTime", "title", "", "time", "", "setTitleChangedListener", "listener", "Lcom/miui/handwriting/common/view/page/HeaderView$OnTitleChangedListener;", "clearTitleCursor", "setTitleTime", "setTitle", "savingTitle", "setTitleColor", "searchStr", "color", "", "getTitle", "showLoadingDialog", "isContentEmpty", "", "getPageCount", "getCurrentPosition", "load", "entFilePath", "pageLoadListener", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "setPreviewOnClick", "previewOnClick", "Lcom/miui/handwriting/common/listener/CommonPreviewClickListener;", "setPageStateListener", "pageStateListener", "Lcom/miui/handwriting/common/listener/CommonPageStateListener;", "updateBackgroundMargin", "scale", "", "setSearchResult", "pageIndex", "alpha", "wrapper", "Lcom/miui/handwriting/common/data/bean/CommonSearchWrapper;", "saveEntFile", "saveListener", "Lcom/miui/handwriting/common/listener/CommonPageSaveListener;", "release", "clearHighLight", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface MiuiHandWritingPreviewInterface {
    void clearHighLight();

    void clearTitleCursor();

    int getCurrentPosition();

    int getPageCount();

    String getTitle();

    void initUIProperties();

    boolean isContentEmpty();

    void load(String entFilePath, CommonPageLoadListener pageLoadListener);

    void release();

    void saveEntFile(CommonPageSaveListener saveListener);

    void setPageStateListener(CommonPageStateListener pageStateListener);

    void setPreviewOnClick(CommonPreviewClickListener previewOnClick);

    void setSearchResult(int pageIndex, int color, int alpha, CommonSearchWrapper wrapper);

    void setTitle(String savingTitle);

    void setTitleAndTime(String title, long time);

    void setTitleChangedListener(HeaderView.OnTitleChangedListener listener);

    void setTitleColor(String savingTitle, String searchStr, int color);

    void setTitleTime(long time);

    void showLoadingDialog();

    void updateBackgroundMargin(float scale);
}
