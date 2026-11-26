package com.miui.notes.widget.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class WidgetView8x4 extends WidgetView {
    private static final String TAG = "WidgetView4x4";

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getCommonNoteLayoutId() {
        return R.layout.widget_note_8x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getContentTextAppearance() {
        return R.style.V13_TextAppearance_Widget_Snippet_4x;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getDefaultCommonNoteLayoutId() {
        return R.layout.widget_note_8x4_default;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindItemLayoutId() {
        return R.layout.widget_mind_note_outline_item_4x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteLayoutId() {
        return R.layout.widget_mind_note_8x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteMapBottomIcon() {
        return R.drawable.ic_widget_mind_note_map_bottom_icon_4x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteMapCenterIcon() {
        return R.drawable.ic_widget_mind_note_map_center_icon_4x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteMapSingleTopIcon() {
        return R.drawable.ic_widget_mind_note_map_single_top_icon_4x4;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getMindNoteMapTopIcon() {
        return R.drawable.ic_widget_mind_note_map_top_icon_4x4;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getWidgetType() {
        return 4;
    }

    WidgetView8x4() {
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getWidgetRootPaddingVertical(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_vertical_4x4);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getWidgetRootPaddingHorizontal(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_horizontal_4x4);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getContentTextLineSpace(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_content_line_spacing_extra_4x4);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getMindNoteItemLineHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_mind_note_item_height_4x4);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public boolean showImage(Context context, WidgetView.RichParserHandler richParserHandler, RemoteViews remoteViews, AppWidgetManager appWidgetManager, int i) {
        Bitmap bitmapBuildImageThumb = buildImageThumb(context, richParserHandler.getImageList(), context.getResources().getDimensionPixelOffset(R.dimen.widget_content_image_radius), appWidgetManager, i);
        if (bitmapBuildImageThumb == null) {
            remoteViews.setViewVisibility(R.id.widget_image, 8);
            return false;
        }
        remoteViews.setViewVisibility(R.id.widget_image, 0);
        remoteViews.setBitmap(R.id.widget_image, "setImageBitmap", bitmapBuildImageThumb);
        return true;
    }

    protected Bitmap buildImageThumb(Context context, List<String> list, int i, AppWidgetManager appWidgetManager, int i2) {
        String attachmentPath;
        if (list != null && !list.isEmpty()) {
            for (String str : list) {
                if (AttachmentUtils.isDoodleSuffix(str)) {
                    attachmentPath = AttachmentUtils.getDoodleThumbnailPath(context, str);
                } else {
                    attachmentPath = AttachmentUtils.getAttachmentPath(context, str);
                }
                Bitmap bitmapClipImageAttachment = AttachmentUtils.clipImageAttachment(attachmentPath, getThumbWidth(context, appWidgetManager, i2), getThumbHeight(context), i);
                if (bitmapClipImageAttachment != null) {
                    return bitmapClipImageAttachment;
                }
            }
        }
        return null;
    }

    protected int getThumbWidth(Context context, AppWidgetManager appWidgetManager, int i) {
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        if (appWidgetOptions != null && appWidgetOptions.getInt("appWidgetMaxWidth") > 0) {
            return UIUtils.dip2px(context, appWidgetOptions.getInt("appWidgetMaxWidth")) - (getWidgetRootPaddingHorizontal(context) * 2);
        }
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_4x4_image_width);
    }

    protected int getThumbHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.widget_4x4_thumb_height);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    public int getDisplayableLineNumWithImage(Context context, int i, AppWidgetManager appWidgetManager) {
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        if (appWidgetOptions == null || appWidgetOptions.getInt("appWidgetMaxHeight") <= 0) {
            return WIDGET_CONTENT_MAX_LINE_NUM;
        }
        int i2 = appWidgetOptions.getInt("appWidgetMaxHeight");
        TextView textView = new TextView(context);
        textView.setTextAppearance(getContentTextAppearance());
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        float f = fontMetrics.descent - fontMetrics.ascent;
        float f2 = i2;
        int iDip2px = (((((UIUtils.dip2px(context, f2) - (getWidgetRootPaddingVertical(context) * 2)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_note_title_height)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_content_margin_top)) - getThumbHeight(context)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_4x4_note_image_margin_bottom)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_size_error);
        int contentTextLineSpace = (int) (((((iDip2px - (fontMetrics.descent - fontMetrics.top)) - (fontMetrics.bottom - fontMetrics.ascent)) + (2.0f * f)) + getContentTextLineSpace(context)) / (getContentTextLineSpace(context) + f));
        Logger.INSTANCE.d(TAG, "尺寸： remainSpace:" + iDip2px + " maxHeight" + i2 + " dip2px:" + UIUtils.dip2px(context, f2));
        Logger.INSTANCE.d(TAG, "single height:" + f + " 可显示行数：" + contentTextLineSpace);
        return contentTextLineSpace <= 0 ? WIDGET_CONTENT_MAX_LINE_NUM : contentTextLineSpace;
    }

    @Override // com.miui.notes.widget.view.WidgetView
    int getTitleWidth(Context context, AppWidgetManager appWidgetManager, int i) {
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.widget_8x_fake_width);
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        if (appWidgetOptions != null && appWidgetOptions.getInt("appWidgetMaxWidth") > 0) {
            dimensionPixelOffset = appWidgetOptions.getInt("appWidgetMaxWidth");
        }
        return ((dimensionPixelOffset - (getWidgetRootPaddingHorizontal(context) * 2)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_ic_image_size)) - context.getResources().getDimensionPixelOffset(R.dimen.widget_note_title_margin_start);
    }

    @Override // com.miui.notes.widget.view.WidgetView
    protected int getWidgetRootPaddingTopRedress(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.widget_note_root_padding_top_redress4x4);
    }
}
