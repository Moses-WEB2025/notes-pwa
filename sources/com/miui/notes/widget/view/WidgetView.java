package com.miui.notes.widget.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.hardware.DataSpace;
import com.google.gson.Gson;
import com.miui.common.stat.NoteAppStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.feature.note.NoteOperationHelper;
import com.miui.notes.R;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.notes.widget.mindnote.MindItemData;
import com.miui.notes.widget.mindnote.MindNoteDataUtils;
import com.miui.notes.widget.notelist.PadWidgetNoteListActivity;
import com.miui.notes.widget.notelist.WidgetNoteListActivity;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.util.EditorUtils;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
abstract class WidgetView extends BaseWidgetView {
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String TAG = "WidgetView";
    protected static int WIDGET_CONTENT_MAX_LINE_NUM = 50;

    abstract int getContentTextAppearance();

    public abstract int getDisplayableLineNumWithImage(Context context, int i, AppWidgetManager appWidgetManager);

    protected abstract int getMindItemLayoutId();

    protected abstract int getMindNoteLayoutId();

    protected int getMindNoteMapBottomIcon() {
        return R.drawable.ic_widget_mind_note_map_bottom_icon;
    }

    protected int getMindNoteMapCenterIcon() {
        return R.drawable.ic_widget_mind_note_map_center_icon;
    }

    protected int getMindNoteMapSingleTopIcon() {
        return R.drawable.ic_widget_mind_note_map_single_top_icon;
    }

    protected int getMindNoteMapTopIcon() {
        return R.drawable.ic_widget_mind_note_map_top_icon;
    }

    abstract int getTitleWidth(Context context, AppWidgetManager appWidgetManager, int i);

    abstract int getWidgetRootPaddingHorizontal(Context context);

    protected abstract int getWidgetRootPaddingTopRedress(Context context);

    abstract int getWidgetRootPaddingVertical(Context context);

    abstract boolean showImage(Context context, RichParserHandler richParserHandler, RemoteViews remoteViews, AppWidgetManager appWidgetManager, int i);

    WidgetView() {
    }

    protected class RichParserHandler extends EditorUtils.SnippetMediaHandler {
        private LinkedList<String> nImageList;

        public RichParserHandler(Context context) {
            super(context);
            this.nImageList = new LinkedList<>();
        }

        public LinkedList<String> getImageList() {
            return this.nImageList;
        }

        @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
        protected CharSequence getImageText(String str) {
            this.nImageList.add(str);
            return "";
        }
    }

    public int getContentTextLineSpace(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_content_line_spacing_extra);
    }

    public int getMindNoteItemLineHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_mind_note_item_height);
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    public RemoteViews getRemoteViewsByWidgetId(Context context, int i, AppWidgetManager appWidgetManager, NoteEntity noteEntity, int i2) {
        RemoteViews commonRemoteViews;
        Logger.INSTANCE.i(TAG, "getRemoteViewsByWidgetId noteType:" + (noteEntity != null ? noteEntity.getNoteType() : "null"));
        if (noteEntity == null) {
            commonRemoteViews = getDefaultRemoteViews(context, i, appWidgetManager);
        } else if (TextUtils.equals(noteEntity.getNoteType(), "common") || TextUtils.isEmpty(noteEntity.getNoteType())) {
            commonRemoteViews = getCommonRemoteViews(context, i, noteEntity, appWidgetManager);
        } else {
            commonRemoteViews = TextUtils.equals(noteEntity.getNoteType(), NoteModel.NoteType.TYPE_MIND) ? getMindRemoteViews(context, i, noteEntity, appWidgetManager) : null;
        }
        Logger.INSTANCE.i(TAG, "getRemoteViewsByWidgetId remoteViews:" + commonRemoteViews);
        return commonRemoteViews;
    }

    private RemoteViews getDefaultRemoteViews(Context context, int i, AppWidgetManager appWidgetManager) {
        Bundle appWidgetOptions;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getDefaultCommonNoteLayoutId());
        if (!UIUtils.isMiuiWidgetSupported(context) && !UIUtils.isMIUI14AndAbove() && ((!RomUtils.isInternationalBuild() || Utils.getPackageCode(NoteAppStat.APP_PKG.desktop) < 425000000) && (!RomUtils.isPadMode() || !LiteUtils.isLiteOrMiddle()))) {
            if (UIUtils.isNightMode(context)) {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_dark);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_hint_color_dark));
                remoteViews.setTextColor(R.id.widget_text_default, ContextCompat.getColor(context, R.color.widget_note_text_hint_color_dark));
            } else {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_light);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_hint_color_light));
                remoteViews.setTextColor(R.id.widget_text_default, ContextCompat.getColor(context, R.color.widget_note_text_hint_color_light));
            }
        }
        Logger.INSTANCE.i(TAG, "getDefaultRemoteViews");
        Intent intent = new Intent();
        if (RomUtils.isPadDevice()) {
            intent.setClass(context, PadWidgetNoteListActivity.class);
        } else {
            intent.setClass(context, WidgetNoteListActivity.class);
        }
        intent.putExtra("com.miui.notes.widget_id", i);
        intent.putExtra("com.miui.notes.widget_type", getWidgetType());
        intent.setAction(NoteIntent.ACTION_BIND_WIDGET);
        remoteViews.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(context, i, intent, UIUtils.isAboveAndroidS() ? 167772160 : DataSpace.RANGE_FULL));
        if (RomUtils.isPadMode() && (appWidgetOptions = appWidgetManager.getAppWidgetOptions(i)) != null && appWidgetOptions.getBoolean("miuiLargeScreenDevice", false)) {
            Bundle bundle = new Bundle();
            bundle.putInt("miuiWidgetId", i);
            remoteViews.setBundle(android.R.id.background, "supportLargeScreenEditPreviewMode", bundle);
        }
        int displayableLineNum = getDisplayableLineNum(context, i, appWidgetManager);
        Logger.INSTANCE.i(TAG, "getDefaultRemoteViews: maxShowLines" + displayableLineNum);
        int actualShowLines = getActualShowLines(context.getResources().getString(R.string.widget_empty_content), displayableLineNum, context, i, appWidgetManager);
        Logger.INSTANCE.i(TAG, "getDefaultRemoteViews: actualShowNum" + actualShowLines);
        setRootViewParam(context, remoteViews, displayableLineNum, actualShowLines);
        remoteViews.setInt(R.id.widget_text_default, "setMaxLines", displayableLineNum);
        return remoteViews;
    }

    private RemoteViews getCommonRemoteViews(Context context, int i, NoteEntity noteEntity, AppWidgetManager appWidgetManager) {
        String title;
        String content;
        NoteEntity noteEntity2;
        int displayableLineNum;
        RichParserHandler richParserHandler = new RichParserHandler(context);
        Intent intent = new Intent();
        intent.putExtra("com.miui.notes.widget_id", i);
        intent.putExtra("com.miui.notes.widget_type", getWidgetType());
        intent.putExtra(NoteWidgetProvider.INTENT_EXTRA_IS_HIDDEN, noteEntity.isHidden());
        intent.putExtra("android.intent.extra.UID", noteEntity.getId());
        intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, NoteOperationHelper.hasNewStyle(noteEntity.getContent()));
        intent.putExtra("com.miui.notes.folder_id", noteEntity.getParentId());
        intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteEntity.getThemeId());
        intent.setAction("android.intent.action.VIEW");
        if (RomUtils.isPadMode()) {
            intent.setClass(context, NotesListActivity.class);
            intent.setFlags(603979776);
            intent.putExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, noteEntity.getThemeId());
        } else {
            intent.setClass(context, EditActivity.class);
            intent.setFlags(268484608);
        }
        Logger.INSTANCE.i(TAG, "getCommonRemoteViews");
        if (noteEntity.isHidden()) {
            title = context.getResources().getString(R.string.edit_note_hint_title);
            content = context.getString(R.string.hint_cannot_view_hidden_note_content);
            noteEntity2 = noteEntity;
        } else {
            title = noteEntity.getTitle();
            content = getContent(richParserHandler, context, noteEntity.getContent());
            if (TextUtils.isEmpty(title)) {
                String[] strArr = {title, content};
                if (!TextUtils.isEmpty(content)) {
                    setTitleFromContent(context, strArr, getTitleWidth(context, appWidgetManager, i));
                    title = strArr[0];
                    content = strArr[1];
                }
                if (TextUtils.isEmpty(title)) {
                    noteEntity2 = noteEntity;
                    int contentType = parseContentType(noteEntity2);
                    if (contentType == 3) {
                        title = context.getResources().getString(R.string.snippet_audio_stub);
                    } else {
                        title = contentType == 2 ? context.getResources().getString(R.string.snippet_image_stub) : context.getResources().getString(R.string.pc_action_create_new_note);
                    }
                } else {
                    noteEntity2 = noteEntity;
                }
            } else {
                noteEntity2 = noteEntity;
                if (TextUtils.isEmpty(content)) {
                    String[] strArr2 = {content, title};
                    if (!TextUtils.isEmpty(title)) {
                        setTitleFromContent(context, strArr2, getTitleWidth(context, appWidgetManager, i));
                        title = strArr2[0];
                        content = strArr2[1];
                    }
                }
            }
            if (TextUtils.isEmpty(content)) {
                content = context.getResources().getString(R.string.note_not_text_stub);
            }
        }
        String str = content;
        String str2 = title;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getCommonNoteLayoutId());
        if (!UIUtils.isMiuiWidgetSupported(context) && !UIUtils.isMIUI14AndAbove() && ((!RomUtils.isInternationalBuild() || Utils.getPackageCode(NoteAppStat.APP_PKG.desktop) < 425000000) && (!RomUtils.isPadMode() || !LiteUtils.isLiteOrMiddle()))) {
            if (UIUtils.isNightMode(context)) {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_dark);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_color_dark));
                remoteViews.setTextColor(R.id.widget_text_1, ContextCompat.getColor(context, R.color.widget_note_text_color_dark));
            } else {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_light);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_color_light));
                remoteViews.setTextColor(R.id.widget_text_1, ContextCompat.getColor(context, R.color.widget_note_text_color_light));
            }
        }
        boolean zShowImage = showImage(context, richParserHandler, remoteViews, appWidgetManager, i);
        Logger.INSTANCE.i(TAG, "isShowImage:" + zShowImage);
        remoteViews.setTextViewText(R.id.widget_title, str2);
        if (noteEntity2.isHidden() || !zShowImage) {
            displayableLineNum = getDisplayableLineNum(context, i, appWidgetManager);
        } else {
            displayableLineNum = getDisplayableLineNumWithImage(context, i, appWidgetManager);
        }
        Logger.INSTANCE.i(TAG, "widgetId:" + i + " snippet length:" + (str != null ? Integer.valueOf(str.length()) : "null"));
        int actualShowLines = getActualShowLines(str, displayableLineNum, context, i, appWidgetManager);
        setRootViewParam(context, remoteViews, displayableLineNum, actualShowLines);
        Logger.INSTANCE.i(TAG, "widgetId:" + i + " maxShowLines:" + displayableLineNum + " actualShowNum:" + actualShowLines);
        remoteViews.setInt(R.id.widget_text_1, "setMaxLines", displayableLineNum);
        remoteViews.setTextViewText(R.id.widget_text_1, str);
        remoteViews.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(context, i, intent, 201326592));
        return remoteViews;
    }

    private RemoteViews getMindRemoteViews(Context context, int i, NoteEntity noteEntity, AppWidgetManager appWidgetManager) {
        String title = noteEntity.getTitle();
        Intent intent = new Intent();
        if (RomUtils.isPadMode()) {
            intent.setClass(context, NotesListActivity.class);
        } else {
            intent.setClass(context, WebViewBrainActivity.class);
        }
        intent.setFlags(268484608);
        intent.putExtra("android.intent.extra.UID", noteEntity.getId());
        intent.putExtra(NoteWidgetProvider.INTENT_EXTRA_IS_HIDDEN, noteEntity.isHidden());
        intent.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 1);
        intent.putExtra("com.miui.notes.widget_type", getWidgetType());
        if (!TextUtils.isEmpty(noteEntity.getMindContent())) {
            if (((MindEntity) new Gson().fromJson(noteEntity.getMindContent().substring(15), MindEntity.class)).isMap) {
                intent.putExtra(NoteIntent.KEY_MIND_OR_OVERVIEW, 0);
            } else {
                intent.putExtra(NoteIntent.KEY_MIND_OR_OVERVIEW, 1);
            }
        } else {
            intent.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 0);
            intent.putExtra(NoteIntent.KEY_MIND_OR_OVERVIEW, 0);
        }
        intent.setAction("android.intent.action.VIEW");
        PendingIntent activity = PendingIntent.getActivity(context, i, intent, 201326592);
        Logger.INSTANCE.i(TAG, "getMindRemoteViews");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getMindNoteLayoutId());
        if (!UIUtils.isMiuiWidgetSupported(context) && !UIUtils.isMIUI14AndAbove() && ((!RomUtils.isInternationalBuild() || Utils.getPackageCode(NoteAppStat.APP_PKG.desktop) < 425000000) && (!RomUtils.isPadMode() || !LiteUtils.isLiteOrMiddle()))) {
            if (UIUtils.isNightMode(context)) {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_dark);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_color_dark));
                remoteViews.setTextColor(R.id.widget_text, ContextCompat.getColor(context, R.color.widget_note_text_color_dark));
            } else {
                remoteViews.setInt(android.R.id.background, "setBackgroundResource", R.drawable.ic_widget_bg_simple_light);
                remoteViews.setTextColor(R.id.widget_title, ContextCompat.getColor(context, R.color.widget_note_text_color_light));
                remoteViews.setTextColor(R.id.widget_text, ContextCompat.getColor(context, R.color.widget_note_text_color_light));
            }
        }
        if (noteEntity.isHidden()) {
            title = context.getResources().getString(R.string.edit_note_hint_title);
            remoteViews.setTextViewText(R.id.widget_text, context.getString(R.string.hint_cannot_view_hidden_note_content));
            remoteViews.setViewVisibility(R.id.widget_text, 0);
            remoteViews.setViewVisibility(R.id.widget_note_listview, 8);
            remoteViews.setInt(R.id.widget_note_root_layout, "setGravity", 48);
            remoteViews.setViewPadding(R.id.widget_note_root_layout, getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingVertical(context), getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingVertical(context));
        } else {
            remoteViews.removeAllViews(R.id.widget_note_listview);
            remoteViews.setViewVisibility(R.id.widget_text, 8);
            remoteViews.setViewVisibility(R.id.widget_note_listview, 0);
            addMindItem(context, noteEntity.getMindContent(), remoteViews, i, appWidgetManager);
        }
        Logger.INSTANCE.i(TAG, "addMindItem success");
        if (!TextUtils.isEmpty(title) && title.contains(StringUtils.LF)) {
            title = title.replace(StringUtils.LF, "");
        }
        remoteViews.setTextViewText(R.id.widget_title, title);
        remoteViews.setOnClickPendingIntent(16908288, activity);
        return remoteViews;
    }

    public void addMindItem(Context context, String str, RemoteViews remoteViews, int i, AppWidgetManager appWidgetManager) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        MindEntity mindEntity = (MindEntity) new Gson().fromJson(str.substring(15), MindEntity.class);
        if (!MindDataHelper.isCorrectFormatContent(str)) {
            mindEntity.content = MindDataHelper.convertToCorrectFormatContent(mindEntity.content);
        }
        if (mindEntity != null) {
            List<MindItemData> mindNoteList = MindNoteDataUtils.getMindNoteList(mindEntity);
            int iObtainMindNoteDisplayableLines = obtainMindNoteDisplayableLines(context, i, appWidgetManager);
            int iMin = Math.min(mindNoteList.size(), iObtainMindNoteDisplayableLines);
            setRootViewParam(context, remoteViews, iObtainMindNoteDisplayableLines, iMin);
            for (int i2 = 0; i2 < iMin; i2++) {
                RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), getMindItemLayoutId());
                MindItemData mindItemData = mindNoteList.get(i2);
                remoteViews2.removeAllViews(R.id.widget_mind_note_outline_item_line_container);
                String strReplace = mindItemData.label;
                if (!TextUtils.isEmpty(strReplace) && strReplace.contains(StringUtils.LF)) {
                    strReplace = strReplace.replace(StringUtils.LF, "");
                }
                remoteViews2.setTextViewText(R.id.widget_mind_note_item_content, strReplace);
                if (!mindEntity.isMap) {
                    if (mindItemData.hasChildren) {
                        remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, R.drawable.ic_widget_mind_note_prefix_triangle);
                    } else {
                        remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, R.drawable.ic_widget_mind_note_prefix_circle);
                    }
                    for (int i3 = 0; i3 < mindItemData.depth - 1; i3++) {
                        remoteViews2.addView(R.id.widget_mind_note_outline_item_line_container, new RemoteViews(context.getPackageName(), R.layout.widget_mind_note_outline_item_line));
                    }
                } else if (i2 == 0) {
                    if (iMin == 1) {
                        remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, getMindNoteMapSingleTopIcon());
                    } else {
                        remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, getMindNoteMapTopIcon());
                    }
                } else if (i2 == iMin - 1) {
                    remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, getMindNoteMapBottomIcon());
                } else {
                    remoteViews2.setImageViewResource(R.id.widget_mind_note_outline_item_icon, getMindNoteMapCenterIcon());
                }
                remoteViews.addView(R.id.widget_note_listview, remoteViews2);
            }
        }
    }

    private int obtainMindNoteDisplayableLines(Context context, int i, AppWidgetManager appWidgetManager) {
        int i2 = WIDGET_CONTENT_MAX_LINE_NUM;
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        if (appWidgetOptions == null || appWidgetOptions.getInt("appWidgetMaxHeight") <= 0) {
            return i2;
        }
        int i3 = appWidgetOptions.getInt("appWidgetMaxHeight");
        int mindNoteItemLineHeight = getMindNoteItemLineHeight(context);
        float f = i3;
        int iDip2px = ((UIUtils.dip2px(context, f) - (getWidgetRootPaddingVertical(context) * 2)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_note_title_height)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_mind_list_margin_top);
        int i4 = iDip2px / mindNoteItemLineHeight;
        Logger.INSTANCE.d(TAG, "尺寸： 内容高度:" + iDip2px + " maxHeight:" + i3 + " dip2px:" + UIUtils.dip2px(context, f));
        Logger.INSTANCE.d(TAG, "single height:" + mindNoteItemLineHeight + " 可显示行数:" + i4);
        return i4;
    }

    private String getContent(RichParserHandler richParserHandler, Context context, String str) {
        return TextProcessUtils.removeNoteWidgetSpans(TextProcessUtils.trimEmptyLineSequence(EditorUtils.normalizeSnippet(str, richParserHandler, context))).toString();
    }

    private void setTitleFromContent(Context context, String[] strArr, int i) {
        CharSequence[] charSequenceArrSplitText = splitText(strArr[1]);
        CharSequence charSequence = charSequenceArrSplitText[0];
        if (!TextUtils.isEmpty(charSequenceArrSplitText[1])) {
            String str = (String) charSequence;
            strArr[0] = str;
            strArr[1] = strArr[1].substring(str.length() + 1);
            return;
        }
        int length = charSequence.length();
        TextView textView = new TextView(context);
        textView.setTextAppearance(R.style.V13_TextAppearance_Widget_Title);
        TextPaint paint = textView.getPaint();
        TextPaint textPaint = new TextPaint();
        textPaint.set(paint);
        StaticLayout staticLayoutCreateStaticLayout = createStaticLayout(charSequence, 0, length, paint, i, TextUtils.TruncateAt.END, 1);
        int ellipsisStart = staticLayoutCreateStaticLayout.getEllipsisStart(0);
        if (ellipsisStart == 0) {
            ellipsisStart = staticLayoutCreateStaticLayout.getLineEnd(0);
        }
        int i2 = ellipsisStart;
        if (i2 == length) {
            strArr[0] = (String) charSequence;
            strArr[1] = "";
            return;
        }
        StaticLayout staticLayoutCreateStaticLayout2 = createStaticLayout(charSequence, i2, length, textPaint, i, null, 3);
        int lineCount = staticLayoutCreateStaticLayout2.getLineCount();
        if (lineCount == 1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(charSequence.subSequence(0, Math.max(i2, 0))).append((CharSequence) ELLIPSIS_NORMAL);
            strArr[0] = spannableStringBuilder.toString();
            strArr[1] = "";
            return;
        }
        int i3 = lineCount == 2 ? 0 : 1;
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        spannableStringBuilder2.append(charSequence, 0, Math.max(i2, 0)).append((CharSequence) ELLIPSIS_NORMAL);
        strArr[0] = spannableStringBuilder2.toString();
        SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
        spannableStringBuilder3.append((CharSequence) ELLIPSIS_NORMAL);
        spannableStringBuilder3.append(charSequence, staticLayoutCreateStaticLayout2.getLineStart(i3), length);
        strArr[1] = spannableStringBuilder3.toString();
    }

    protected CharSequence[] splitText(CharSequence charSequence) {
        CharSequence[] charSequenceArr = new CharSequence[2];
        int iIndexOf = charSequence.toString().indexOf(10);
        if (iIndexOf >= 0) {
            charSequenceArr[0] = charSequence.subSequence(0, iIndexOf);
            CharSequence charSequenceTrimEmptyLineSequence = TextProcessUtils.trimEmptyLineSequence(charSequence.subSequence(iIndexOf + 1, charSequence.length()));
            int iIndexOf2 = charSequenceTrimEmptyLineSequence.toString().indexOf(10);
            if (iIndexOf2 >= 0) {
                charSequenceArr[1] = charSequenceTrimEmptyLineSequence.subSequence(0, iIndexOf2);
                return charSequenceArr;
            }
            charSequenceArr[1] = charSequenceTrimEmptyLineSequence;
            return charSequenceArr;
        }
        charSequenceArr[0] = charSequence;
        charSequenceArr[1] = "";
        return charSequenceArr;
    }

    protected static StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
        return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setMaxLines(i4).setEllipsize(truncateAt).build();
    }

    private int parseContentType(NoteEntity noteEntity) {
        int i;
        String strRetrieveFirstImage = NoteSchema.retrieveFirstImage(noteEntity.getContent());
        if (AudioUtils.hasAudioLabel(noteEntity.getContent())) {
            return 3;
        }
        String strReplaceAll = TextProcessUtils.removeCheckboxTag(NoteSchema.removeImagesTag(noteEntity.getContent())).replaceAll(StringUtils.LF, "").replaceAll(StringUtils.SPACE, "").replaceAll("<textindent=\"1\"></text>", "");
        if (TextUtils.isEmpty(strRetrieveFirstImage)) {
            i = 1;
        } else {
            i = strReplaceAll.length() == 0 ? 2 : 4;
        }
        if (noteEntity.getNoteType().equals(NoteModel.NoteType.TYPE_MIND)) {
            return 5;
        }
        return i;
    }

    private int getDisplayableLineNum(Context context, int i, AppWidgetManager appWidgetManager) {
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        if (appWidgetOptions == null || appWidgetOptions.getInt("appWidgetMaxHeight") <= 0) {
            return WIDGET_CONTENT_MAX_LINE_NUM;
        }
        int i2 = appWidgetOptions.getInt("appWidgetMaxHeight");
        TextView textView = new TextView(context);
        textView.setTextAppearance(getContentTextAppearance());
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        float f = fontMetrics.bottom - fontMetrics.top;
        float f2 = i2;
        int iDip2px = ((UIUtils.dip2px(context, f2) - (getWidgetRootPaddingVertical(context) * 2)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_note_title_height)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_content_margin_top);
        int contentTextLineSpace = (int) (iDip2px / (getContentTextLineSpace(context) + f));
        Logger.INSTANCE.d(TAG, "尺寸： 内容高度:" + iDip2px + " maxHeight:" + UIUtils.dip2px(context, f2) + " 行间距:" + getContentTextLineSpace(context));
        Logger.INSTANCE.d(TAG, "single height:" + f + " 可显示行数:" + contentTextLineSpace);
        return contentTextLineSpace <= 0 ? WIDGET_CONTENT_MAX_LINE_NUM : contentTextLineSpace;
    }

    private int getActualShowLines(String str, int i, Context context, int i2, AppWidgetManager appWidgetManager) {
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i2);
        if (appWidgetOptions == null || appWidgetOptions.getInt("appWidgetMaxWidth") <= 0) {
            return -1;
        }
        int i3 = appWidgetOptions.getInt("appWidgetMaxWidth");
        TextView textView = new TextView(context);
        textView.setTextAppearance(getContentTextAppearance());
        return createStaticLayout(str, 0, str.length(), textView.getPaint(), UIUtils.dip2px(context, i3) - (getWidgetRootPaddingHorizontal(context) * 2), TextUtils.TruncateAt.END, i).getLineCount();
    }

    private void setRootViewParam(Context context, RemoteViews remoteViews, int i, int i2) {
        if (UIUtils.isMiuiWidgetSupported(context) && i2 == i) {
            remoteViews.setInt(R.id.widget_note_root_layout, "setGravity", 16);
            remoteViews.setViewPadding(R.id.widget_note_root_layout, getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingTopRedress(context), getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingVertical(context));
        } else {
            remoteViews.setInt(R.id.widget_note_root_layout, "setGravity", 48);
            remoteViews.setViewPadding(R.id.widget_note_root_layout, getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingVertical(context), getWidgetRootPaddingHorizontal(context), getWidgetRootPaddingVertical(context));
        }
    }
}
