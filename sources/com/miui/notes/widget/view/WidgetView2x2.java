package com.miui.notes.widget.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
class WidgetView2x2 extends WidgetView {
    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getCommonNoteLayoutId() {
        return R.layout.widget_note_2x2;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getContentTextAppearance() {
        return R.style.V13_TextAppearance_Widget_Snippet_2x;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getDefaultCommonNoteLayoutId() {
        return R.layout.widget_note_2x2_default;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getDisplayableLineNumWithImage(Context context, int i, AppWidgetManager appWidgetManager) {
        return 0;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindItemLayoutId() {
        return R.layout.widget_mind_note_outline_item_2x2;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteLayoutId() {
        return R.layout.widget_mind_note_2x2;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getWidgetType() {
        return 1;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    boolean showImage(Context context, WidgetView.RichParserHandler richParserHandler, RemoteViews remoteViews, AppWidgetManager appWidgetManager, int i) {
        return false;
    }

    WidgetView2x2() {
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getWidgetRootPaddingVertical(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_vertical_2x2);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getWidgetRootPaddingHorizontal(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_horizontal_2x2);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getTitleWidth(Context context, AppWidgetManager appWidgetManager, int i) {
        return ((context.getResources().getDimensionPixelOffset(R.dimen.widget_2x2_fake_width) - (getWidgetRootPaddingHorizontal(context) * 2)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_ic_image_size)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_note_title_margin_start);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getWidgetRootPaddingTopRedress(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_top_redress2x2);
    }
}
