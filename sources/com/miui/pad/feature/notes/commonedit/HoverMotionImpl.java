package com.miui.pad.feature.notes.commonedit;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.popupwidget.widget.GuidePopupWindow;

/* JADX INFO: loaded from: classes4.dex */
public class HoverMotionImpl implements View.OnHoverListener {
    public static final String TAG = "HoverMotionImpl";
    private GuidePopupWindow popupWindow = null;
    private final Handler handler = new Handler(NoteApp.getInstance().getMainLooper());

    public void setOnHoverListener(View view) {
        view.setOnHoverListener(this);
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(final View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.audio && view.isSelected()) {
            Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.NORMAL).setCorner(18.0f).onMotionEvent(motionEvent);
            return true;
        }
        Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.NORMAL).setCorner(18.0f).onMotionEvent(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.handler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.HoverMotionImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    HoverMotionImpl.this.showPopWindow(view);
                }
            }, 200L);
        } else if (actionMasked == 10) {
            dismissPopupWindow();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopWindow(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (this.popupWindow == null) {
            view.getContext().setTheme(R.style.NavigatorSecondaryContentTheme);
            GuidePopupWindow guidePopupWindow = new GuidePopupWindow(view.getContext());
            this.popupWindow = guidePopupWindow;
            guidePopupWindow.setTouchable(false);
            this.popupWindow.setInputMethodMode(2);
            this.popupWindow.setArrowMode(16);
            this.popupWindow.setFocusable(false);
        }
        if (view instanceof PadRichTextCheckedTextView) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            this.popupWindow.setArrowMode(16);
            if (Utils.isRTL()) {
                if (i < 50 && i > 0) {
                    this.popupWindow.setArrowMode(17);
                } else if (i <= 0) {
                    return;
                }
            }
            int realWidth = DisplayUtils.getRealWidth(view.getContext()) - i;
            if (realWidth < 125 && realWidth > 50) {
                this.popupWindow.setArrowMode(17);
            } else if (realWidth <= 50) {
                return;
            }
        }
        View contentView = this.popupWindow.getContentView();
        if (contentView instanceof LinearLayout) {
            ((LinearLayout) contentView).removeAllViews();
            if (view.getId() == R.id.rich_text_switch) {
                this.popupWindow.setGuideText((String) view.getContentDescription());
            } else {
                setText(view);
            }
            this.popupWindow.show(view, 0, -20);
        }
    }

    private void setText(View view) {
        if (view == null || this.popupWindow == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.audio /* 2131362036 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.toolbar_voice_description));
                break;
            case R.id.bg_highlight /* 2131362065 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_hightlight_description));
                break;
            case R.id.bold /* 2131362073 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_bold_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_bold));
                break;
            case R.id.bullet /* 2131362117 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_unorderlist_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_unorderlist));
                break;
            case R.id.center /* 2131362149 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_center_description));
                break;
            case R.id.check /* 2131362157 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_tasklist_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_tasklist));
                break;
            case R.id.edit_image /* 2131362464 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.doodle_feature));
                break;
            case R.id.first_level /* 2131362538 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_font_first_level_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_font_first_level));
                break;
            case R.id.gallery /* 2131362611 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_gallery));
                break;
            case R.id.indentleft /* 2131362744 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_indent_left_description));
                break;
            case R.id.indentright /* 2131362745 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_indent_right_description));
                break;
            case R.id.italic /* 2131362763 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_italic_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_italic));
                break;
            case R.id.left /* 2131362802 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_left_description));
                break;
            case R.id.listbullet /* 2131362821 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_orderlist_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_orderlist));
                break;
            case R.id.quote /* 2131363263 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_quote_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_quote));
                break;
            case R.id.right /* 2131363305 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_right_description));
                break;
            case R.id.second_level /* 2131363384 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_font_second_level_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_font_second_level));
                break;
            case R.id.split_line /* 2131363510 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_split_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_split_line));
                break;
            case R.id.strikethrough /* 2131363540 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_strikethrough_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_strikethrough));
                break;
            case R.id.thrid_level /* 2131363616 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_font_third_level_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_font_third_level));
                break;
            case R.id.underline /* 2131363734 */:
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.rich_text_underline_description));
                this.popupWindow.setGuideText(NoteApp.getInstance().getString(R.string.hover_underline));
                break;
        }
    }

    public void onDetach() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void resetPopupWindowDes(final View view) {
        GuidePopupWindow guidePopupWindow = this.popupWindow;
        if (guidePopupWindow == null || !guidePopupWindow.isShowing()) {
            return;
        }
        dismissPopupWindow();
        this.handler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.HoverMotionImpl.2
            @Override // java.lang.Runnable
            public void run() {
                HoverMotionImpl.this.showPopWindow(view);
            }
        }, 200L);
    }

    public void dismissPopupWindow() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        GuidePopupWindow guidePopupWindow = this.popupWindow;
        if (guidePopupWindow == null || !guidePopupWindow.isShowing()) {
            return;
        }
        this.popupWindow.dismiss();
    }
}
