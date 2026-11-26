package com.miui.notes.basefeature.noteedit;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.richeditor.TextStyleStateListener;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public interface INoteRichEditor {

    public interface OverType {
        public static final int BULLET = 3;
        public static final int CHECKBOX = 2;
        public static final int DEFAULT = 0;
        public static final int DOODLE = 5;
        public static final int INPUT = -1;
        public static final int INPUT_WITH_CUT = -2;
        public static final int LINK_CARD = 6;
        public static final int PICTURE = 4;
        public static final int STYLE = 1;
    }

    boolean canAddNewElement(int i);

    boolean canAlignCenter();

    boolean canAlignNormal();

    boolean canAlignRight();

    void clearFocus();

    void done();

    Context getContext();

    void getMarkdown(WebRichEditor.OnWriteFinishedListener onWriteFinishedListener);

    void getMarkdown(WebRichEditor.OnWriteFinishedListener onWriteFinishedListener, String str);

    CharSequence getRichTextData();

    String getTitle();

    View getView();

    boolean isCursorVisible();

    void notifySaveData();

    void pasteMarkdown(String str, String str2);

    void redo();

    boolean requestViewFocus();

    boolean requestViewFocus(int i);

    void setActivity(Activity activity);

    void setAlignCenter();

    void setAlignNormal();

    void setAlignRight();

    void setBgHighLight();

    void setCheckbox();

    void setCount(long j);

    void setCursorVisible(boolean z);

    void setData(String str, String str2, String str3, Boolean bool, ArrayList<String> arrayList);

    void setDataId(long j);

    void setDeline();

    void setEditorBullet();

    void setEnableEdit(boolean z);

    void setEnableToggleAlignment(boolean z);

    void setExternalClickListener(View.OnClickListener onClickListener);

    void setFolderId(long j);

    void setFontBold();

    void setFontItalic();

    void setFontSizeLevel(int i);

    void setH1();

    void setH2();

    void setH3();

    void setHr();

    void setIndent();

    void setListBullet();

    void setOrderList();

    void setQuote();

    void setSelection(int i);

    void setSelection(int i, int i2);

    void setSelectionToEnd();

    void setTextStyleStateListener(TextStyleStateListener textStyleStateListener);

    void setUndent();

    void setUnderline();

    void setViewShowSoftInputOnFocus(boolean z);

    void startAudioInput();

    void stopAudioInput();

    void undo();

    void updateFontSizeId(int i);
}
