package com.miui.richeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.DragEvent;
import android.view.View;
import com.miui.richeditor.constant.RichEditorConfig;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.theme.Theme;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface IRichEditor {
    public static final String CLIP_LABEL_RICH_EDITOR = "Notes";
    public static final int EDIT_MODE = 1;
    public static final int READ_MODE = 0;
    public static final String SCHEME_EMAIL = "mailto:";
    public static final String SCHEME_HTTP = "http:";
    public static final String SCHEME_HTTPS = "https:";
    public static final String SCHEME_TEL = "tel:";

    public interface CursorClickListener {
        void onLongClick();
    }

    public interface DataLoadingLocker {
        void beginWait();
    }

    void addPendingTask(Runnable runnable);

    void cacheSelection(boolean z);

    boolean canAddAttachments();

    boolean canAlignCenter();

    boolean canAlignNormal();

    boolean canAlignRight();

    void clearDataParseTask();

    void clearImageInsertTask();

    void clearPendingTask();

    void clearRichTextData();

    void clearText();

    void clearTextWatcher();

    DataHtmlParseTask createDataParseTask();

    LinkCardSpanHelper createLinkCardSpanHelper();

    HtmlParser.IMediaHandler createMediaHandler();

    CharSequence createNewRichTextData();

    <T> boolean deleteSpans(Editable editable, int i, int i2, Class<T> cls);

    void doSetRichText(CharSequence charSequence, Spanned spanned);

    void drawImages(Canvas canvas);

    void endDragMode();

    void enterDragZone();

    void exitDragZone();

    void forceUpdateLayout();

    SpannableStringBuilder getBuildableText();

    int[] getCacheEditSelection();

    int getCacheSelectionStart();

    int getContentLengthMax();

    int getContentWidth();

    long getDataId();

    DataLoadingLocker getDataLoadingLocker();

    DataHtmlParseTask getDataParseTask();

    void getDragEventMimeType(DragEvent dragEvent);

    View getEditAreaView();

    RichEditorConfig getEditorConfig();

    int getEditorMode();

    ExternalTextWatcher getExternalTextWatcher();

    ArrayList<String> getHighLightKeyWords();

    int getHintSpanMarginStartByTheme(Theme theme);

    ImageInsertTask getImageInsertTask();

    InputModeRichStyleHelper getInputHelper();

    int getKeywordStyle();

    int getLineAtCoordinate(float f);

    int getLineEnd(String str, int i);

    int getLineStart(String str, int i);

    HtmlParser.IMediaHandler getMediaHandler();

    int getParagraphEnd(String str, int i);

    int getParagraphStart(String str, int i);

    int getPreferSelectionStart(boolean z);

    String getQueryContent();

    int getReadableTextCount();

    CharSequence getRichTextData();

    RichTextWatcher getRichTextWatcher();

    RichEditScrollView getScrollView();

    <T> T getSpanEndAt(Spannable spannable, Class<T> cls, int i);

    <T> T getSpanStartAt(Spannable spannable, Class<T> cls, int i);

    <T> T[] getSpansAt(Spannable spannable, Class<T> cls, int i);

    VisualStyleFactory getStyleFactory();

    SpannableStringBuilder getSubSequence(int i, int i2);

    UndoManager<RichEditUndoOperation> getUndoManager();

    int getUndoStepCapacity();

    boolean hasDataLoadingLocker();

    boolean hasShowingMenu();

    void hideInsertionPointCursorController();

    void increaseFontSize();

    void initEditorConfig();

    void initFontSettings();

    void initImageRender();

    void initStyleFactory();

    void insertAudio();

    void insertDoodleByName(String str, NormalImageSpan normalImageSpan);

    void insertImages(Runnable runnable, Uri... uriArr);

    void invalidateDeeply();

    boolean isDragEventContainImage(DragEvent dragEvent);

    boolean isFirstLoad();

    boolean isHintSpanTwoLines();

    boolean isInDragMode();

    boolean isLoadingViewVisible();

    boolean isSkipRichText();

    boolean isSkipSelectionChanged();

    boolean isValidDragEventData(DragEvent dragEvent);

    void makeSpanContinuous(List<SpanInfo> list);

    void makeTextToToast(Context context, int i, int i2);

    void notifySaveData();

    void onDataParseCancel(DataHtmlParseTask dataHtmlParseTask);

    void onDataParseComplete(DataHtmlParseTask dataHtmlParseTask, CharSequence[] charSequenceArr);

    void onDataParseStart(DataHtmlParseTask dataHtmlParseTask);

    void onPlayingRecord(boolean z);

    void onSurpassContentLengthLimit();

    void onThemeChanged(Theme theme);

    boolean overScrollByPosition(int i, int i2);

    void parseRichTextData(CharSequence charSequence);

    void prepareImages();

    void redo();

    void reduceFontSize();

    void removeSelection();

    void removeSpan(Object obj);

    void restoreInstanceState(Bundle bundle);

    void restoreTextWatcher();

    void saveInstanceState(Bundle bundle);

    void selectLineInDragMode(int i);

    boolean setAlign(Layout.Alignment alignment, int i, int i2);

    void setAlignCenter();

    boolean setAlignCenter(int i, int i2);

    void setAlignNormal();

    boolean setAlignNormal(int i, int i2);

    void setAlignRight();

    boolean setAlignRight(int i, int i2);

    void setBgHighLight();

    void setCheckbox();

    void setCursorClickListener(CursorClickListener cursorClickListener);

    void setDataId(long j);

    void setDataLoadingLocker(DataLoadingLocker dataLoadingLocker);

    void setDeline();

    void setEditAreaView(View view);

    void setEditorActionListener(EditorActionListener editorActionListener);

    void setEditorBullet();

    void setExternalClickListener(View.OnClickListener onClickListener);

    void setExternalTextWatcher(ExternalTextWatcher externalTextWatcher);

    void setFontBold();

    void setFontItalic();

    void setFontSizeLevel(int i);

    void setH1();

    void setH2();

    void setH3();

    void setHighLightKeyWords(ArrayList<String> arrayList);

    void setHr();

    void setImage(Editable editable, SpannableStringBuilder spannableStringBuilder, int i, int i2);

    void setIndent();

    void setLoadingContainer(View view);

    void setOrderList();

    void setQueryContent(String str);

    void setQuote();

    void setReadOnly(boolean z);

    void setScrollView(RichEditScrollView richEditScrollView);

    void setSkipRichText(boolean z);

    void setSkipSelectionChanged(boolean z);

    void setSpanClickListener(SpanClickListener spanClickListener);

    void setTextStyleStateListener(TextStyleStateListener textStyleStateListener);

    @Deprecated
    void setTitle(String str);

    void setUndent();

    void setUnderline();

    void startDragMode();

    SpannableStringBuilder trimImageText(SpannableStringBuilder spannableStringBuilder);

    void undo();

    void updateContent(CharSequence charSequence, String str);

    void updateContentWidth(int i);

    void updateFontSizeId(int i);
}
