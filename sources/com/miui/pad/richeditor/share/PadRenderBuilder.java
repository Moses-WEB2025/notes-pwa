package com.miui.pad.richeditor.share;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.notes.R;
import com.miui.richeditor.share.RenderBuilder;
import com.miui.richeditor.share.RenderConfig;
import com.miui.richeditor.share.element.AudioElement;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.element.TextElement;
import com.miui.richeditor.share.render.CommonRender;
import com.miui.richeditor.share.render.Render;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PadRenderBuilder extends RenderBuilder {
    public PadRenderBuilder(Context context) {
        super(context);
    }

    @Override // com.miui.richeditor.share.RenderBuilder
    protected RenderConfig createConfig(Context context) {
        return PadRenderConfig.getConfig(context);
    }

    @Override // com.miui.richeditor.share.RenderBuilder
    public Render buildHandWrite(List<Uri> list, int i, String str) {
        Drawable colorDrawable;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(new ImageElement.Builder().setRadius(0).setUri(list.get(i2)).build());
        }
        ContextCompat.getColor(this.mContext, i == HandViewProvider.getSinglePageBlackBg() ? R.color.pad_hand_write_share_title_color_dark : R.color.pad_hand_write_share_title_color_normal);
        ContextCompat.getColor(this.mContext, i == HandViewProvider.getSinglePageBlackBg() ? R.color.pad_hand_write_share_time_color_dark : R.color.pad_hand_write_share_time_color_normal);
        this.mContext.getResources().getDimension(R.dimen.pad_hand_write_share_text_horizontal_padding);
        if (i == 2) {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R.color.singlepage_bg_black));
        } else if (i == 0) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.singlepage_doodle_canvas_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R.color.singlepage_bg_white));
        }
        CommonRender commonRender = new CommonRender(this.mContext, arrayList, colorDrawable, this.mConfig.getWidth(), this.mThemeId);
        commonRender.setStrokeStyle(ContextCompat.getColor(this.mContext, R.color.phone_hand_write_share_stroke_color), this.mContext.getResources().getDimensionPixelSize(R.dimen.phone_hand_write_share_stroke_width));
        return commonRender;
    }

    @Override // com.miui.richeditor.share.RenderBuilder
    protected void addHandWriteTitleElements(List<Element> list, String str, int i, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        list.add(0, new TextElement.Builder().setFontSize(this.mContext.getResources().getDimensionPixelOffset(R.dimen.sns_title_font_size_max)).setFontColor(i).setBold(true).setText(str).setMarginLeft(i2).setMarginRight(i3).setMarginBottom(this.mContext.getResources().getDimensionPixelOffset(R.dimen.pad_hand_write_share_title_margin_bottom)).setGravity(1).build());
    }

    @Override // com.miui.richeditor.share.RenderBuilder
    protected void addHandWriteTimeElements(List<Element> list, String str, int i, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        list.add(0, new TextElement.Builder().setFontSize(this.mContext.getResources().getDimensionPixelOffset(R.dimen.sns_content_font_size)).setFontColor(i).setBold(false).setText(str).setMarginLeft(i2).setMarginRight(i3).setMarginTop(this.mContext.getResources().getDimensionPixelOffset(R.dimen.pad_hand_write_share_time_margin_top)).setMarginBottom(this.mContext.getResources().getDimensionPixelOffset(R.dimen.pad_hand_write_share_time_margin_bottom)).setGravity(1).build());
    }

    @Override // com.miui.richeditor.share.RenderBuilder
    protected void addAudioElements(List<Element> list, Uri uri, File file, int i, int i2) {
        list.add(new AudioElement.Builder().setUri(uri).setAudioFile(file).setMarginLeft(i).setMarginRight((int) this.mContext.getResources().getDimension(R.dimen.pad_audio_share_margin_right)).setMarginBottom((int) this.mContext.getResources().getDimension(R.dimen.audio_share_margin_bottom)).setMarginTop((int) this.mContext.getResources().getDimension(R.dimen.audio_share_margin_top)).build());
    }
}
