package com.miui.notes.theme.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class NoteThemeResCache {
    public Drawable actionBarAlertIcon;
    public Drawable actionBarDoneIcon;
    public Drawable actionBarHomeIcon;
    public Drawable actionBarLockIcon;
    public Drawable actionBarMoreIcon;
    public Drawable actionBarRedoIcon;
    public Drawable actionBarShareIcon;
    public Drawable actionBarThemeIcon;
    public Drawable actionBarUndoIcon;
    public int themeId;
    public Drawable toolBarAiIcon;
    public Drawable toolBarAudioIcon;
    public Drawable toolBarBgHighLightIcon;
    public Drawable toolBarBoldIcon;
    public Drawable toolBarBulletIcon;
    public Drawable toolBarCenterIcon;
    public Drawable toolBarCheckIcon;
    public Drawable toolBarDecreaseSizeIcon;
    public Drawable toolBarDivideIcon;
    public Drawable toolBarDoodleIcon;
    public Drawable toolBarFirstLevelIcon;
    public Drawable toolBarGalleryIcon;
    public Drawable toolBarIncreaseSizeIcon;
    public Drawable toolBarIndentLeftIcon;
    public Drawable toolBarIndentRightIcon;
    public Drawable toolBarItalicIcon;
    public Drawable toolBarLeftIcon;
    public Drawable toolBarListBulletIcon;
    public Drawable toolBarQuoteIcon;
    public Drawable toolBarRichTextIcon;
    public Drawable toolBarRichTextReverseIcon;
    public Drawable toolBarRightIcon;
    public Drawable toolBarSecondLevelIcon;
    public Drawable toolBarSplitLineIcon;
    public Drawable toolBarStrikeThroughIcon;
    public Drawable toolBarThridLevelIcon;
    public Drawable toolBarUnderlineIcon;

    public NoteThemeResCache(int i) {
        this.themeId = i;
    }

    public NoteThemeResCache() {
    }

    public void loadActionBarRes(Theme theme, Context context) {
        Theme.ActionBarStyle actionBarStyle = theme.getActionBarStyle(context);
        this.actionBarHomeIcon = actionBarStyle.getHomeIcon();
        this.actionBarUndoIcon = actionBarStyle.getUndoIcon();
        this.actionBarRedoIcon = actionBarStyle.getRedoIcon();
        this.actionBarDoneIcon = actionBarStyle.getDoneIcon();
        this.actionBarShareIcon = actionBarStyle.getShareIcon();
        this.actionBarThemeIcon = actionBarStyle.getThemeIcon();
        this.actionBarMoreIcon = actionBarStyle.getMoreIcon();
        this.actionBarLockIcon = actionBarStyle.getLockIcon();
        this.actionBarAlertIcon = actionBarStyle.getAlertIcon();
    }

    public void loadToolBarRes(Theme theme, Context context) {
        Theme.ToolBarStyle toolBarStyle = theme.getToolBarStyle(context);
        this.toolBarAiIcon = toolBarStyle.getAiIcon();
        this.toolBarGalleryIcon = toolBarStyle.getGalleryIcon();
        this.toolBarCheckIcon = toolBarStyle.getCheckIcon();
        this.toolBarAudioIcon = toolBarStyle.getAudioIcon();
        this.toolBarDoodleIcon = toolBarStyle.getDoodleIcon();
        this.toolBarRichTextIcon = toolBarStyle.getRichTextIcon();
        this.toolBarRichTextReverseIcon = toolBarStyle.getRichTextReverseIcon();
        this.toolBarDivideIcon = toolBarStyle.getToolBarDivideRes();
        this.toolBarBoldIcon = toolBarStyle.getBoldIcon();
        this.toolBarItalicIcon = toolBarStyle.getItalicIcon();
        this.toolBarUnderlineIcon = toolBarStyle.getUnderlineIcon();
        this.toolBarIncreaseSizeIcon = toolBarStyle.getIncreaseSizeIcon();
        this.toolBarDecreaseSizeIcon = toolBarStyle.getDecreaseSizeIcon();
        this.toolBarCenterIcon = toolBarStyle.getCenterIcon();
        this.toolBarRightIcon = toolBarStyle.getRightIcon();
        this.toolBarBgHighLightIcon = toolBarStyle.getBackGroundHighLightIcon();
        this.toolBarBulletIcon = toolBarStyle.getBulletIcon();
        this.toolBarFirstLevelIcon = toolBarStyle.getFirstLevelIcon();
        this.toolBarSecondLevelIcon = toolBarStyle.getSecondLevelIcon();
        this.toolBarThridLevelIcon = toolBarStyle.getThridLevelIcon();
        this.toolBarStrikeThroughIcon = toolBarStyle.getStrikeThroughIcon();
        this.toolBarListBulletIcon = toolBarStyle.getListBulletIcon();
        this.toolBarSplitLineIcon = toolBarStyle.getSplitIcon();
        this.toolBarQuoteIcon = toolBarStyle.getQuoteIcon();
        this.toolBarIndentRightIcon = toolBarStyle.getIndentRightIcon();
        this.toolBarIndentLeftIcon = toolBarStyle.getIndentLeftIcon();
    }

    public void loadToolBarResForLite(Theme theme, Context context) {
        Theme.ToolBarStyle toolBarStyle = theme.getToolBarStyle(context);
        this.toolBarAiIcon = toolBarStyle.getAiIcon();
        this.toolBarGalleryIcon = toolBarStyle.getGalleryIcon();
        this.toolBarCheckIcon = toolBarStyle.getCheckIcon();
        this.toolBarAudioIcon = toolBarStyle.getAudioIcon();
        this.toolBarDoodleIcon = toolBarStyle.getDoodleIcon();
        this.toolBarBoldIcon = toolBarStyle.getBoldIcon();
        this.toolBarItalicIcon = toolBarStyle.getItalicIcon();
        this.toolBarUnderlineIcon = toolBarStyle.getUnderlineIcon();
        this.toolBarIncreaseSizeIcon = toolBarStyle.getIncreaseSizeIcon();
        this.toolBarDecreaseSizeIcon = toolBarStyle.getDecreaseSizeIcon();
        this.toolBarCenterIcon = toolBarStyle.getCenterIcon();
        this.toolBarRightIcon = toolBarStyle.getRightIcon();
        this.toolBarBgHighLightIcon = toolBarStyle.getBackGroundHighLightIcon();
        this.toolBarBulletIcon = toolBarStyle.getBulletIcon();
        this.toolBarFirstLevelIcon = toolBarStyle.getFirstLevelIcon();
        this.toolBarSecondLevelIcon = toolBarStyle.getSecondLevelIcon();
        this.toolBarThridLevelIcon = toolBarStyle.getThridLevelIcon();
        this.toolBarStrikeThroughIcon = toolBarStyle.getStrikeThroughIcon();
        this.toolBarListBulletIcon = toolBarStyle.getListBulletIcon();
        this.toolBarSplitLineIcon = toolBarStyle.getSplitIcon();
        this.toolBarQuoteIcon = toolBarStyle.getQuoteIcon();
        this.toolBarIndentRightIcon = toolBarStyle.getIndentRightIcon();
        this.toolBarIndentLeftIcon = toolBarStyle.getIndentLeftIcon();
    }
}
