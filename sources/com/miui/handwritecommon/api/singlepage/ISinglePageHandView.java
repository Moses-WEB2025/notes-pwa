package com.miui.handwritecommon.api.singlepage;

import android.graphics.Path;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ISinglePageHandView {
    void addImage(Uri uri);

    void autoSave(String str);

    void clear();

    void exportImage(ExportListener<Uri> exportListener);

    List<String> getBitmapsPath(String str);

    float getContentRangBottom();

    int getCurrentBackgroundIndex();

    String getTitle();

    ViewGroup getViewContainer();

    void hideLoading();

    void hideToolBoxView();

    boolean isDataChanged();

    boolean isEmpty();

    void load(String str, NoteDataLoadListener noteDataLoadListener);

    void release();

    void resetDataChangedState();

    void save(String str, NotaDataSaveListener notaDataSaveListener);

    String saveThumbnail();

    WebImage saveWebImage();

    void setAutoOptimizeHandwrite();

    void setBackgroundIndex(int i);

    void setCanWrite(boolean z);

    void setPreviewOnClick(View.OnClickListener onClickListener);

    void setSearchPath(List<Path> list);

    void setSingePageOptionListener(ISinglePageLayoutListener iSinglePageLayoutListener);

    void setTitle(String str);

    void setTitleTime(String str, String str2);

    void setToHead();

    void setToolBoxBackgroundIndex(int i);

    void setToolBoxViewTopPadding(int i);

    void showLoading(int i);

    void showToolBoxView();
}
