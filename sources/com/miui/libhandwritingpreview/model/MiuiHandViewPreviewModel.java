package com.miui.libhandwritingpreview.model;

import android.app.Activity;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.listener.PageSizeListener;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MiuiHandViewPreviewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005J\b\u0010\r\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0013J\u0010\u0010\u0019\u001a\u00020\u000b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u000fJ&\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$J\u0018\u0010%\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020\tJ\u0010\u0010)\u001a\u00020\u000b2\b\u0010*\u001a\u0004\u0018\u00010+J\u0006\u0010,\u001a\u00020\u0017J\u0006\u0010-\u001a\u00020\u0017J\b\u0010.\u001a\u0004\u0018\u00010/J\u0010\u00100\u001a\u00020\u000b2\b\u00101\u001a\u0004\u0018\u000102J \u00103\u001a\u00020\u000b2\b\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020\u000f2\u0006\u00107\u001a\u00020\u0013J&\u00108\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u000f2\u0006\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u000fJ\u0010\u0010=\u001a\u00020\u000b2\b\u0010>\u001a\u0004\u0018\u00010?J\u0010\u0010@\u001a\u00020\u000b2\b\u0010A\u001a\u0004\u0018\u00010BJ\u0006\u0010C\u001a\u00020\u000bJ\u0006\u0010D\u001a\u00020\u000bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mPageInkViewModel", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "pageStateChangeListener", "Lcom/miui/handwriting/common/listener/CommonPageStateListener;", "loadListener", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "init", "", "pageInkModel", "getPageInkModel", "getCurrentPosition", "", "jumpPage", "position", "needAnim", "", "resetScale", "isNeedReset", "scale", "", "isContentEmpty", "setPageStateListener", "pageStateListener", "saveEntFile", "pageSaveListener", "Lcom/miui/handwriting/common/listener/CommonPageSaveListener;", "getPageCount", "setSearchResult", "pageIndex", "color", "alpha", "wrapper", "Lcom/miui/handwriting/common/data/bean/CommonSearchWrapper;", "load", "entFilePath", "", "pageLoadListener", "setPreviewOnClick", "previewOnClick", "Lcom/miui/handwriting/common/listener/CommonPreviewClickListener;", "getScale", "getFactoryScale", "getCurrentItemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "setPageSizeListener", "pageSizeListener", "Lcom/miui/libhandwritingpage/listener/PageSizeListener;", "scrollOnKeyBoardHeightChanged", "activity", "Landroid/app/Activity;", "keyBoardHeight", "isPut", "setPadding", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "setStateView", "mStateView", "Lcom/miui/libhandwritingpage/listener/PageStateView;", "setTitleChangeListener", "listener", "Lcom/miui/libhandwritingpage/listener/TitleColorChangeListener;", "destroy", "clearHighLight", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiuiHandViewPreviewModel {
    private CommonPageLoadListener loadListener;
    private PageInkPreModel mPageInkViewModel;
    private CommonPageStateListener pageStateChangeListener;

    public final void setTitleChangeListener(TitleColorChangeListener listener) {
    }

    public final void init(PageInkPreModel pageInkModel) {
        Intrinsics.checkNotNullParameter(pageInkModel, "pageInkModel");
        this.mPageInkViewModel = pageInkModel;
    }

    /* JADX INFO: renamed from: getPageInkModel, reason: from getter */
    public final PageInkPreModel getMPageInkViewModel() {
        return this.mPageInkViewModel;
    }

    public final int getCurrentPosition() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.getCurrentPosition();
        }
        return 0;
    }

    public final void jumpPage(int position, boolean needAnim) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.jumpPage(position, needAnim);
        }
    }

    public final void resetScale(boolean isNeedReset, float scale) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.resetScale(isNeedReset, scale);
        }
    }

    public final boolean isContentEmpty() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.isContentEmpty();
        }
        return true;
    }

    public final void setPageStateListener(CommonPageStateListener pageStateListener) {
        this.pageStateChangeListener = pageStateListener;
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setPageStateListener(new PageStateListener() { // from class: com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel.setPageStateListener.1
                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void dataLoadFinish() {
                }

                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void onStepChanged(int stepType, int position, boolean z, boolean z2, boolean dismissLoadingDialog) {
                    CommonPageStateListener commonPageStateListener = MiuiHandViewPreviewModel.this.pageStateChangeListener;
                    if (commonPageStateListener != null) {
                        commonPageStateListener.onStepChanged(stepType, position, z, z2, dismissLoadingDialog);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void onContentChanged(int i) {
                    CommonPageStateListener commonPageStateListener = MiuiHandViewPreviewModel.this.pageStateChangeListener;
                    if (commonPageStateListener != null) {
                        commonPageStateListener.onContentChanged(i);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void onDoubleUndo() {
                    CommonPageStateListener commonPageStateListener = MiuiHandViewPreviewModel.this.pageStateChangeListener;
                    if (commonPageStateListener != null) {
                        commonPageStateListener.onDoubleUndo();
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void onPageScale(float f) {
                    CommonPageStateListener commonPageStateListener = MiuiHandViewPreviewModel.this.pageStateChangeListener;
                    if (commonPageStateListener != null) {
                        commonPageStateListener.onPageScale(f);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageStateListener
                public void onPageLoadingFinish() {
                    CommonPageStateListener commonPageStateListener = MiuiHandViewPreviewModel.this.pageStateChangeListener;
                    if (commonPageStateListener != null) {
                        commonPageStateListener.onPageLoadingFinish();
                    }
                }
            });
        }
    }

    public final void saveEntFile(final CommonPageSaveListener pageSaveListener) {
        Intrinsics.checkNotNullParameter(pageSaveListener, "pageSaveListener");
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.saveEntFile(new PageSaveListener() { // from class: com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel$$ExternalSyntheticLambda0
                @Override // com.miui.libhandwritingpage.listener.PageSaveListener
                public final void onDataSaved(boolean z, String str, boolean z2) {
                    pageSaveListener.onDataSaved(z, str, z2);
                }
            });
        }
    }

    public final int getPageCount() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.getPageCount();
        }
        return 0;
    }

    public final void setSearchResult(int pageIndex, int color, int alpha, CommonSearchWrapper wrapper) {
        Intrinsics.checkNotNullParameter(wrapper, "wrapper");
        SearchWrapper searchWrapper = new SearchWrapper(pageIndex, wrapper.searchRectMap, wrapper.searchText, wrapper.searchSpannedRect, wrapper.searchSpannedRectContent, wrapper.searchSpannedTextSize, wrapper.searchColor, wrapper.searchAlpha);
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setSearchResult(pageIndex, color, alpha, searchWrapper);
        }
    }

    public final void load(String entFilePath, CommonPageLoadListener pageLoadListener) {
        Intrinsics.checkNotNullParameter(pageLoadListener, "pageLoadListener");
        this.loadListener = pageLoadListener;
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.load(entFilePath, new PageLoadListener() { // from class: com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel.load.1
                @Override // com.miui.libhandwritingpage.listener.PageLoadListener
                public void onLoadCompleted(boolean z, boolean isPdf, String str) {
                    Intrinsics.checkNotNullParameter(str, "str");
                    CommonPageLoadListener commonPageLoadListener = MiuiHandViewPreviewModel.this.loadListener;
                    if (commonPageLoadListener != null) {
                        commonPageLoadListener.onLoadCompleted(z, isPdf, str);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageLoadListener
                public void onPageCount(int i) {
                    CommonPageLoadListener commonPageLoadListener = MiuiHandViewPreviewModel.this.loadListener;
                    if (commonPageLoadListener != null) {
                        commonPageLoadListener.onPageCount(i);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageLoadListener
                public void onPdfError(int type) {
                    CommonPageLoadListener commonPageLoadListener = MiuiHandViewPreviewModel.this.loadListener;
                    if (commonPageLoadListener != null) {
                        commonPageLoadListener.onPdfError(type);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PageLoadListener
                public String getSuniaSinglePageBgType() {
                    CommonPageLoadListener commonPageLoadListener = MiuiHandViewPreviewModel.this.loadListener;
                    if (commonPageLoadListener != null) {
                        return commonPageLoadListener.getSuniaSinglePageBgType();
                    }
                    return null;
                }
            });
        }
    }

    public final void setPreviewOnClick(final CommonPreviewClickListener previewOnClick) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setClickListener(new IPagePreInkModel.IPreviewClickListener() { // from class: com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel.setPreviewOnClick.1
                @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel.IPreviewClickListener
                public void onClick() {
                    CommonPreviewClickListener commonPreviewClickListener = previewOnClick;
                    if (commonPreviewClickListener != null) {
                        commonPreviewClickListener.onClick();
                    }
                }
            });
        }
    }

    public final float getScale() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.getScale();
        }
        return 1.0f;
    }

    public final float getFactoryScale() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.getFactoryScale();
        }
        return 1.0f;
    }

    public final PagePreItemView getCurrentItemView() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            return pageInkPreModel.getCurrentItemView();
        }
        return null;
    }

    public final void setPageSizeListener(PageSizeListener pageSizeListener) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setPageSizeListener(pageSizeListener);
        }
    }

    public final void scrollOnKeyBoardHeightChanged(Activity activity, int keyBoardHeight, boolean isPut) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.scrollOnKeyBoardHeightChanged(activity, keyBoardHeight, isPut);
        }
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setPadding(left, top, right, bottom);
        }
    }

    public final void setStateView(PageStateView mStateView) {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.setStateView(mStateView);
        }
    }

    public final void destroy() {
        this.loadListener = null;
        this.pageStateChangeListener = null;
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.destroy();
        }
        PageInkPreModel pageInkPreModel2 = this.mPageInkViewModel;
        if (pageInkPreModel2 != null) {
            pageInkPreModel2.setPageStateListener(null);
        }
    }

    public final void clearHighLight() {
        PageInkPreModel pageInkPreModel = this.mPageInkViewModel;
        if (pageInkPreModel != null) {
            pageInkPreModel.clearHighLight();
        }
    }
}
