package com.miui.richeditor.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.view.GravityCompat;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.util.WindowUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Theme {
    public static int[] ACTION_BAR_STYLE_ARRAY = {R.drawable.ic_action_back, R.drawable.vertical_line, R.drawable.ic_note_edit_info_clock, R.drawable.ic_note_edit_info_lock, R.drawable.ic_action_share, R.drawable.v12_ic_action_theme, R.drawable.ic_action_more, R.drawable.ic_action_undo, R.drawable.ic_action_redo, R.drawable.ic_action_done, R.color.divider_line, R.color.note_edit_extra_info_text_color};
    public static ActionBarStyle ACTION_BAR_STYLE_DARK;
    public static StatusBarStyle STATUS_BAR_STYLE_DARK;
    public static StatusBarStyle STATUS_BAR_STYLE_LIGHT;
    public static int[] TOOL_BAR_STYLE_ARRAY;
    public static ToolBarStyle TOOL_BAR_STYLE_DARK;
    public static ToolBarStyle TOOL_BAR_STYLE_LIGHT;
    private int mId;
    protected Resources.Theme mResTheme;

    @Deprecated
    protected boolean mUseDarkCheckBox = true;

    public static class ShareStyle {
        public Drawable mBackground;
        public int mFooterLineMarginTop;
        public int mFooterMarginBottom;
        public int mFooterMarginTop;
        public int mHeaderMarginTop;
        public int mIconColor;
        public boolean mShowFooterDivider;
        public int mTextColor;
        public int mTextGravity = GravityCompat.START;
    }

    public static class WidgetStyle {
        public int mTitleBackground;
        public int mTitleColor;
    }

    public abstract void clearEditContentStyle();

    public abstract ActionBarStyle getActionBarStyle(Context context);

    public abstract EditorStyle getEditorStyle(Context context);

    public abstract EditorStyle getEditorStyle(Context context, long j);

    public abstract Rect getForegroundBorderPadding(Context context);

    public abstract Rect getForegroundPadding(Context context);

    public abstract GridStyle getGridStyle(Context context);

    public abstract Drawable getIcon(Context context);

    public abstract Drawable getIconBg(Context context);

    public abstract LinkCardStyle getLinkCardStyle(Context context);

    public abstract String getName(Context context);

    public abstract ShareStyle getShareStyle(Context context, long j);

    public abstract StatusBarStyle getStatusBarStyle(Context context);

    public abstract int getTitleMarginBottom(Context context);

    public abstract int getTitleMarginTop(Context context);

    public abstract ToolBarStyle getToolBarStyle(Context context);

    public int getTouchDeleteForegroundColorRes() {
        return R.color.touch_delete_operation_default_foreground_color;
    }

    public abstract WidgetStyle getWidgetStyle(Context context);

    public void onDestroyView() {
    }

    static {
        TOOL_BAR_STYLE_ARRAY = new int[]{RomUtils.isSupportAi() ? R.drawable.ic_tool_ai : R.drawable.icon_ai, R.drawable.ic_tool_gallery, R.drawable.ic_tool_checkbox, R.drawable.avd_skin, R.drawable.avd_richtext, R.drawable.ic_tool_richtext, R.drawable.avd_richtext_reverse, R.drawable.ic_tool_richtext_reverse, R.drawable.v12_ic_action_keyboard, R.drawable.ic_tool_voice, R.drawable.ic_tool_doodle, R.drawable.ic_tool_center, R.drawable.ic_tool_right, R.drawable.ic_tool_underline, R.drawable.ic_tool_bold, R.drawable.ic_tool_italic, R.drawable.ic_tool_increase_size, R.drawable.ic_tool_decrease_size, R.drawable.ic_tool_background_highlight, R.drawable.ic_tool_bullet, R.drawable.v12_note_editmodebar_divide_gradient_bg, R.drawable.ic_tool_fontsize_level_first, R.drawable.ic_tool_fontsize_level_second, R.drawable.ic_tool_fontsize_level_thrid, R.drawable.ic_tool_strikethrough, R.drawable.ic_tool_listbullet, R.drawable.ic_tool_split_line, R.drawable.ic_tool_quote, R.drawable.ic_tool_indent_right, R.drawable.ic_tool_indent_left};
    }

    public static void initStyles() {
        Resources.Theme themeNewTheme = NoteApp.getInstance().getResources().newTheme();
        themeNewTheme.applyStyle(R.style.NoteTheme_Edit_Dark, true);
        Resources.Theme themeNewTheme2 = NoteApp.getInstance().getResources().newTheme();
        themeNewTheme2.applyStyle(R.style.NoteTheme_Edit_Light, true);
        ActionBarStyle actionBarStyle = new ActionBarStyle(ACTION_BAR_STYLE_ARRAY, themeNewTheme);
        ACTION_BAR_STYLE_DARK = actionBarStyle;
        actionBarStyle.setHeaderInfoColor(R.color.header_info_text_color_dark);
        TOOL_BAR_STYLE_LIGHT = new ToolBarStyle(TOOL_BAR_STYLE_ARRAY, themeNewTheme2);
        TOOL_BAR_STYLE_DARK = new ToolBarStyle(TOOL_BAR_STYLE_ARRAY, themeNewTheme);
        StatusBarStyle statusBarStyle = new StatusBarStyle();
        STATUS_BAR_STYLE_LIGHT = statusBarStyle;
        statusBarStyle.mStatusBar = 1;
        StatusBarStyle statusBarStyle2 = new StatusBarStyle();
        STATUS_BAR_STYLE_DARK = statusBarStyle2;
        statusBarStyle2.mStatusBar = 2;
    }

    public Theme(int i) {
        this.mId = i;
    }

    public Resources.Theme getResTheme(Context context) {
        if (this.mResTheme == null) {
            this.mResTheme = context.getResources().newTheme();
        }
        return this.mResTheme;
    }

    public int getId() {
        return this.mId;
    }

    public Drawable getCheckboxResource(Context context) {
        return context.getResources().getDrawable(R.drawable.ic_richtext_check, null);
    }

    public static class ActionBarStyle {
        private Drawable mAlertIcon;
        private int mAlertIconId;
        private int mDividerColor;
        private int mDivisionLineId;
        private int mDoneIconId;
        private int mHeaderInfoTextColor;
        private int mHomeIconId;
        private Drawable mLockIcon;
        private int mLockIconId;
        private int mMoreIconId;
        private int mRedoIconId;
        private int mShareIconId;
        private Resources.Theme mTheme;
        private int mThemeIconId;
        private int mUndoIconId;

        public ActionBarStyle(int[] iArr, Resources.Theme theme) {
            this.mHomeIconId = iArr[0];
            this.mDivisionLineId = iArr[1];
            this.mAlertIconId = iArr[2];
            this.mLockIconId = iArr[3];
            this.mShareIconId = iArr[4];
            this.mThemeIconId = iArr[5];
            this.mMoreIconId = iArr[6];
            this.mUndoIconId = iArr[7];
            this.mRedoIconId = iArr[8];
            this.mDoneIconId = iArr[9];
            this.mDividerColor = iArr[10];
            this.mHeaderInfoTextColor = iArr[11];
            this.mTheme = theme;
        }

        public Drawable getHomeIcon() {
            return Theme.getThemeDrawable(this.mHomeIconId, this.mTheme);
        }

        public int getDividerColor() {
            return NoteApp.getInstance().getResources().getColor(this.mDividerColor);
        }

        public int getHeaderInfoTextColor() {
            return NoteApp.getInstance().getResources().getColor(this.mHeaderInfoTextColor);
        }

        public void setHeaderInfoColor(int i) {
            this.mHeaderInfoTextColor = i;
        }

        public Drawable getDivisionLine() {
            return Theme.getFilterDrawable(this.mDivisionLineId, this.mDividerColor);
        }

        public Drawable getAlertIcon() {
            if (this.mAlertIcon == null) {
                this.mAlertIcon = NoteApp.getInstance().getResources().getDrawable(R.drawable.ic_note_edit_info_clock).mutate();
            }
            return filterDrawable(this.mAlertIcon);
        }

        public Drawable getLockIcon() {
            if (this.mLockIcon == null) {
                this.mLockIcon = NoteApp.getInstance().getResources().getDrawable(R.drawable.ic_note_edit_info_lock).mutate();
            }
            return filterDrawable(this.mLockIcon);
        }

        public Drawable filterDrawable(Drawable drawable) {
            return Theme.getFilterDrawable(drawable, this.mHeaderInfoTextColor).mutate();
        }

        public Drawable getShareIcon() {
            return Theme.getThemeDrawable(this.mShareIconId, this.mTheme);
        }

        public Drawable getThemeIcon() {
            return Theme.getThemeDrawable(this.mThemeIconId, this.mTheme);
        }

        public Drawable getMoreIcon() {
            return Theme.getThemeDrawable(this.mMoreIconId, this.mTheme);
        }

        public Drawable getUndoIcon() {
            return Theme.getThemeDrawable(this.mUndoIconId, this.mTheme);
        }

        public Drawable getRedoIcon() {
            return Theme.getThemeDrawable(this.mRedoIconId, this.mTheme);
        }

        public Drawable getDoneIcon() {
            return Theme.getThemeDrawable(this.mDoneIconId, this.mTheme);
        }

        public Resources.Theme getResTheme() {
            return this.mTheme;
        }
    }

    public static class ToolBarStyle {
        private int mAiIconId;
        private int mAudioIconId;
        private int mBackGroundHighLightIconId;
        private int mBoldIconId;
        private int mBulletIconId;
        private int mCenterIconId;
        private int mCheckIconId;
        private int mDecreaseSizeIconId;
        private int mDoodleIconId;
        private int mFirstLevelIconId;
        private int mGalleryIconId;
        private int mIncreaseSizeIconId;
        private int mIndentLeftIconId;
        private int mIndentRightIconId;
        private int mItalicIconId;
        private int mKeyboardIconId;
        private int mListBulletIconId;
        private int mQuoteIconId;
        private int mRichTextIconId;
        private int mRichTextReverseIconId;
        private int mRichTextReverseStaticIconId;
        private int mRichTextStaticIconId;
        private int mRightIconId;
        private int mSecondLevelIconId;
        private int mSplitIconId;
        private int mStrikeThroughIconId;
        private Resources.Theme mTheme;
        private int mThemeIconId;
        private int mThridLevelIconId;
        private int mToolBarDivideId;
        private int mUnderlineIconId;

        public ToolBarStyle(int[] iArr, Resources.Theme theme) {
            this.mAiIconId = iArr[0];
            this.mGalleryIconId = iArr[1];
            this.mCheckIconId = iArr[2];
            this.mThemeIconId = iArr[3];
            this.mRichTextIconId = iArr[4];
            this.mRichTextStaticIconId = iArr[5];
            this.mRichTextReverseIconId = iArr[6];
            this.mRichTextReverseStaticIconId = iArr[7];
            this.mKeyboardIconId = iArr[8];
            this.mAudioIconId = iArr[9];
            this.mDoodleIconId = iArr[10];
            this.mCenterIconId = iArr[11];
            this.mRightIconId = iArr[12];
            this.mUnderlineIconId = iArr[13];
            this.mBoldIconId = iArr[14];
            this.mItalicIconId = iArr[15];
            this.mIncreaseSizeIconId = iArr[16];
            this.mDecreaseSizeIconId = iArr[17];
            this.mBackGroundHighLightIconId = iArr[18];
            this.mBulletIconId = iArr[19];
            this.mToolBarDivideId = iArr[20];
            this.mFirstLevelIconId = iArr[21];
            this.mSecondLevelIconId = iArr[22];
            this.mThridLevelIconId = iArr[23];
            this.mStrikeThroughIconId = iArr[24];
            this.mListBulletIconId = iArr[25];
            this.mSplitIconId = iArr[26];
            this.mQuoteIconId = iArr[27];
            this.mIndentRightIconId = iArr[28];
            this.mIndentLeftIconId = iArr[29];
            this.mTheme = theme;
        }

        public Drawable getAiIcon() {
            return Theme.getThemeDrawable(this.mAiIconId, this.mTheme);
        }

        public Drawable getGalleryIcon() {
            return Theme.getThemeDrawable(this.mGalleryIconId, this.mTheme);
        }

        public Drawable getCheckIcon() {
            return Theme.getThemeDrawable(this.mCheckIconId, this.mTheme);
        }

        public Drawable getEditThemeIcon() {
            return Theme.getThemeDrawable(this.mThemeIconId, this.mTheme);
        }

        public Drawable getRichTextIcon() {
            return Theme.getThemeDrawable(this.mRichTextIconId, this.mTheme);
        }

        public Drawable getRichTextStaticIcon() {
            return Theme.getThemeDrawable(this.mRichTextStaticIconId, this.mTheme);
        }

        public Drawable getRichTextReverseIcon() {
            return Theme.getThemeDrawable(this.mRichTextReverseIconId, this.mTheme);
        }

        public Drawable getRichTextReverseStaticIcon() {
            return Theme.getThemeDrawable(this.mRichTextReverseStaticIconId, this.mTheme);
        }

        public Drawable getToolBarDivideRes() {
            return Theme.getThemeDrawable(this.mToolBarDivideId, this.mTheme);
        }

        public Drawable getKeyboardIcon() {
            return Theme.getThemeDrawable(this.mKeyboardIconId, this.mTheme);
        }

        public Drawable getAudioIcon() {
            return Theme.getThemeDrawable(this.mAudioIconId, this.mTheme);
        }

        public Drawable getDoodleIcon() {
            return Theme.getThemeDrawable(this.mDoodleIconId, this.mTheme);
        }

        public Drawable getCenterIcon() {
            return Theme.getThemeDrawable(this.mCenterIconId, this.mTheme);
        }

        public Drawable getRightIcon() {
            return Theme.getThemeDrawable(this.mRightIconId, this.mTheme);
        }

        public Drawable getUnderlineIcon() {
            return Theme.getThemeDrawable(this.mUnderlineIconId, this.mTheme);
        }

        public Drawable getBoldIcon() {
            return Theme.getThemeDrawable(this.mBoldIconId, this.mTheme);
        }

        public Drawable getItalicIcon() {
            return Theme.getThemeDrawable(this.mItalicIconId, this.mTheme);
        }

        public Drawable getIncreaseSizeIcon() {
            return Theme.getThemeDrawable(this.mIncreaseSizeIconId, this.mTheme);
        }

        public Drawable getDecreaseSizeIcon() {
            return Theme.getThemeDrawable(this.mDecreaseSizeIconId, this.mTheme);
        }

        public Drawable getBulletIcon() {
            return Theme.getThemeDrawable(this.mBulletIconId, this.mTheme);
        }

        public Drawable getBackGroundHighLightIcon() {
            return Theme.getThemeDrawable(this.mBackGroundHighLightIconId, this.mTheme);
        }

        public Drawable getFirstLevelIcon() {
            return Theme.getThemeDrawable(this.mFirstLevelIconId, this.mTheme);
        }

        public Drawable getSecondLevelIcon() {
            return Theme.getThemeDrawable(this.mSecondLevelIconId, this.mTheme);
        }

        public Drawable getThridLevelIcon() {
            return Theme.getThemeDrawable(this.mThridLevelIconId, this.mTheme);
        }

        public Drawable getStrikeThroughIcon() {
            return Theme.getThemeDrawable(this.mStrikeThroughIconId, this.mTheme);
        }

        public Drawable getSplitIcon() {
            return Theme.getThemeDrawable(this.mSplitIconId, this.mTheme);
        }

        public Drawable getListBulletIcon() {
            return Theme.getThemeDrawable(this.mListBulletIconId, this.mTheme);
        }

        public Drawable getQuoteIcon() {
            return Theme.getThemeDrawable(this.mQuoteIconId, this.mTheme);
        }

        public Drawable getIndentLeftIcon() {
            return Theme.getThemeDrawable(this.mIndentLeftIconId, this.mTheme);
        }

        public Drawable getIndentRightIcon() {
            return Theme.getThemeDrawable(this.mIndentRightIconId, this.mTheme);
        }
    }

    public static class EditorStyle extends ShareStyle {
        public int mBgHighLightColor;
        public int mBlurMixColor;
        public int mBlurMixColor2;
        public int mBlurMixColor3;
        public int mBlurRadius;
        public int mCursorColor;
        public int mDirIndicatorBgColor;
        public int mDirIndicatorBorderColor;
        public int mDirIndicatorIconColor;
        public int mEditInputBgColor;
        public int mEditorHintColor;
        public Drawable mForeground;
        public int mHandleColor;
        public int mHighLightColor;
        public int mLinkedColor;

        public Drawable getBulletsIcon() {
            return Theme.getFilterDrawable(R.drawable.ic_richtext_bullet, this.mTextColor);
        }
    }

    public static class GridStyle {
        private Drawable mAlertIcon;
        public int mAlertIconRes;
        public Drawable mBackground;
        public int mBodyStyle;
        public Drawable mBorder;
        public int mBorderColor;
        public int mBorderWidth;
        public int mIconColor;
        public boolean mShowImage;
        private Drawable mStickIcon;
        public int mStickIconRes;
        public int mTextGravity = GravityCompat.START;
        public int mTimeStyle;
        public int mTitleStyle;
        private Drawable mVoiceIcon;
        public int mVoiceIconRes;

        public Drawable getAlertIcon() {
            if (this.mAlertIcon == null) {
                this.mAlertIcon = NoteApp.getInstance().getApplicationContext().getResources().getDrawable(this.mAlertIconRes, null).mutate();
            }
            return Theme.getFilterDrawable(this.mAlertIcon, this.mIconColor);
        }

        public Drawable getStickIcon() {
            if (this.mStickIcon == null) {
                this.mStickIcon = NoteApp.getInstance().getApplicationContext().getResources().getDrawable(this.mStickIconRes, null).mutate();
            }
            return Theme.getFilterDrawable(this.mStickIcon, this.mIconColor);
        }

        public Drawable getVoiceIcon() {
            if (this.mVoiceIcon == null) {
                this.mVoiceIcon = NoteApp.getInstance().getApplicationContext().getResources().getDrawable(this.mVoiceIconRes, null).mutate();
            }
            return Theme.getFilterDrawable(this.mVoiceIcon, this.mIconColor);
        }
    }

    public static class StatusBarStyle {
        public boolean mFixed = true;
        public int mStatusBar;

        public void apply(BaseActivity baseActivity) {
            if (this.mFixed) {
                WindowUtils.setTranslucentStatus(baseActivity.getWindow(), this.mStatusBar);
            } else {
                baseActivity.setTranslucentStatus(this.mStatusBar);
                WindowUtils.setTranslucentStatus(baseActivity.getWindow(), baseActivity.getTranslucentStatus());
            }
        }
    }

    public static class LinkCardStyle {
        public int mContentColor;
        public int mIconColorInt;
        public int mLinkColor;
        private Drawable mLinkIcon;
        private Resources.Theme mTheme;
        public int mTitleColor;

        public LinkCardStyle(Resources.Theme theme, int[] iArr) {
            this.mTheme = theme;
            this.mIconColorInt = theme.getResources().getColor(R.color.link_card_icon_color, this.mTheme);
            this.mTitleColor = iArr[0];
            this.mLinkColor = iArr[1];
        }

        public Resources.Theme getResTheme() {
            return this.mTheme;
        }

        public Drawable getLinkIcon() {
            if (this.mLinkIcon == null) {
                Drawable drawableMutate = NoteApp.getInstance().getApplicationContext().getResources().getDrawable(R.drawable.ic_link_card, null).mutate();
                this.mLinkIcon = drawableMutate;
                this.mLinkIcon = Theme.getFilterDrawableWithColorInt(drawableMutate, this.mIconColorInt);
            }
            return this.mLinkIcon;
        }

        public int getTitleColor() {
            return this.mTheme.getResources().getColor(this.mTitleColor, null);
        }

        public int getLinkColor() {
            return this.mTheme.getResources().getColor(this.mLinkColor, null);
        }

        public int getContentColor() {
            return this.mTheme.getResources().getColor(this.mContentColor, null);
        }
    }

    public static Drawable getThemeDrawable(int i, Resources.Theme theme) {
        return theme.getDrawable(i).mutate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Drawable getFilterDrawable(int i, int i2) {
        return getFilterDrawable(NoteApp.getInstance().getResources().getDrawable(i).mutate(), i2);
    }

    public static Drawable getFilterDrawable(Drawable drawable, int i) {
        if (i == 0) {
            return drawable;
        }
        int color = NoteApp.getInstance().getResources().getColor(i);
        drawable.setColorFilter(new ColorMatrixColorFilter(colorToMatrix(color)));
        drawable.setAlpha((color >> 24) & 255);
        return drawable;
    }

    public static Drawable getFilterDrawableWithColorInt(Drawable drawable, int i) {
        drawable.setColorFilter(new ColorMatrixColorFilter(colorToMatrix(i)));
        return drawable;
    }

    private static ColorStateList createColorStateList(int i, int i2, int i3) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed, 1}, new int[]{android.R.attr.state_enabled, 0}, new int[0]}, new int[]{i, i2, i3});
    }

    public static float[] colorToMatrix(int i) {
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f, Color.red(i), 0.0f, 0.0f, 0.0f, 0.0f, Color.green(i), 0.0f, 0.0f, 0.0f, 0.0f, Color.blue(i), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    }
}
