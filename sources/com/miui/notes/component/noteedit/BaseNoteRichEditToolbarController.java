package com.miui.notes.component.noteedit;

import android.app.Activity;
import android.view.View;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.base.app.MainHandler;
import com.miui.richeditor.theme.Theme;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseNoteRichEditToolbarController implements IBaseNoteEditToolbarController {
    protected static String TAG = "BaseNoteRichEditToolbarController";
    private final WeakReference<Activity> mActivity;
    protected boolean mCanIndent;
    protected boolean mCanSplitLine;
    protected boolean mCanUndent;
    protected MainHandler<Activity> mHandler;
    protected boolean mIsAllH1;
    protected boolean mIsAllH2;
    protected boolean mIsAllH3;
    protected boolean mIsBgHighLight;
    protected boolean mIsBold;
    protected boolean mIsBullet;
    protected boolean mIsCenter;
    protected boolean mIsCheck;
    protected boolean mIsItalic;
    protected boolean mIsLeft;
    protected boolean mIsListBullet;
    protected boolean mIsQuote;
    protected boolean mIsRight;
    protected boolean mIsStrikeThrough;
    protected boolean mIsUnderline;
    protected Theme mNoteTheme;
    protected int mSizeLevelNow;
    protected View mToolbarRootView;
    protected static final int[] STYLE_VIEW_IDS = {R.id.bold, R.id.italic, R.id.underline, R.id.bg_highlight, R.id.left, R.id.center, R.id.right, R.id.check, R.id.indentleft, R.id.indentright, R.id.bullet, R.id.strikethrough, R.id.listbullet, R.id.split_line, R.id.quote, R.id.thrid_level, R.id.first_level, R.id.second_level};
    protected static final int[] FONT_SIZE_VIEW_ID = {R.id.increase_font, R.id.reduce_font};

    protected abstract boolean canAddNewElement(int i, int i2);

    protected abstract boolean canAlignCenter();

    protected abstract boolean canAlignNormal();

    protected abstract boolean canAlignRight();

    protected abstract void doSetBullet();

    protected abstract void doSetCheckbox();

    protected abstract void doSetListBullet();

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentLeft() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentRight() {
    }

    protected void initToolbar(View view) {
    }

    public void initView(View view) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void isAiCanUse(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onGallery(boolean z) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void splitLine() {
    }

    protected abstract boolean updateSizeState(int i);

    protected abstract boolean updateState(int i);

    public BaseNoteRichEditToolbarController(Activity activity, View view) {
        this.mActivity = new WeakReference<>(activity);
        this.mHandler = new MainHandler<>(activity.getMainLooper(), new WeakReference(activity));
        this.mToolbarRootView = view;
        initView(view);
        updateLayout();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public Activity getContext() {
        return this.mActivity.get();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H1() {
        onH1(!this.mIsAllH1);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H2() {
        onH2(!this.mIsAllH2);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H3() {
        onH3(!this.mIsAllH3);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bold() {
        onBold(!this.mIsBold);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void italic() {
        onItalic(!this.mIsItalic);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void left() {
        if (canAlignNormal()) {
            doSetLeft();
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void center() {
        if (canAlignCenter()) {
            doSetCenter();
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void right() {
        if (canAlignRight()) {
            doSetRight();
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize() {
        onFontSize(this.mSizeLevelNow);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize(int i) {
        onFontSize(i);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void underline() {
        onUnderline(!this.mIsUnderline);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bgHighLight() {
        onBgHighLight(!this.mIsBgHighLight);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void strikeThrough() {
        onStrikeThrough(!this.mIsStrikeThrough);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void quote() {
        onQuote(!this.mIsQuote);
        isNeedLock(!this.mIsQuote);
    }

    public boolean listBullet() {
        if (!isListBullet() && !canAddNewElement(10, R.string.warn_cannot_add_new_bullet)) {
            return false;
        }
        doSetListBullet();
        return true;
    }

    public boolean bullet() {
        if (!isBullet() && !canAddNewElement(10, R.string.warn_cannot_add_new_bullet)) {
            return false;
        }
        doSetBullet();
        return true;
    }

    public boolean checkbox() {
        if (!isCheckbox() && !canAddNewElement(40, R.string.warn_cannot_add_new_checkbox)) {
            return false;
        }
        doSetCheckbox();
        return true;
    }

    protected void doSetLeft() {
        onLeft(!this.mIsLeft);
    }

    protected void doSetCenter() {
        onCenter(!this.mIsCenter);
    }

    protected void doSetRight() {
        onRight(!this.mIsRight);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH1() {
        return this.mIsAllH1;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH2() {
        return this.mIsAllH2;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH3() {
        return this.mIsAllH3;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBold() {
        return this.mIsBold;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isItalic() {
        return this.mIsItalic;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isLeft() {
        return this.mIsLeft;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCenter() {
        return this.mIsCenter;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public int getSizeLevel() {
        return this.mSizeLevelNow;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isUnderline() {
        return this.mIsUnderline;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isRight() {
        return this.mIsRight;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBgHighLight() {
        return this.mIsBgHighLight;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCheckbox() {
        return this.mIsCheck;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBullet() {
        return this.mIsBullet;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isStrikeThrough() {
        return this.mIsStrikeThrough;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isListBullet() {
        return this.mIsListBullet;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCanSplitLine() {
        return this.mCanSplitLine;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isQuote() {
        return this.mIsQuote;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentRight() {
        return this.mCanIndent;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentLeft() {
        return this.mCanUndent;
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH1(boolean z) {
        this.mIsAllH1 = z;
        updateState(R.id.first_level);
        if (this.mIsAllH1) {
            boolean z2 = this.mIsAllH2;
            if (z2 || this.mIsAllH3) {
                if (z2) {
                    onH2(!z2);
                }
                boolean z3 = this.mIsAllH3;
                if (z3) {
                    onH3(!z3);
                }
            }
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH2(boolean z) {
        this.mIsAllH2 = z;
        updateState(R.id.second_level);
        if (this.mIsAllH2) {
            boolean z2 = this.mIsAllH1;
            if (z2 || this.mIsAllH3) {
                if (z2) {
                    onH1(!z2);
                }
                boolean z3 = this.mIsAllH3;
                if (z3) {
                    onH3(!z3);
                }
            }
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH3(boolean z) {
        this.mIsAllH3 = z;
        updateState(R.id.thrid_level);
        if (this.mIsAllH3) {
            boolean z2 = this.mIsAllH2;
            if (z2 || this.mIsAllH1) {
                if (z2) {
                    onH2(!z2);
                }
                boolean z3 = this.mIsAllH1;
                if (z3) {
                    onH1(!z3);
                }
            }
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onStrikeThrough(boolean z) {
        this.mIsStrikeThrough = z;
        updateState(R.id.strikethrough);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onSpliteLine(boolean z) {
        this.mCanSplitLine = z;
        updateState(R.id.split_line);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onQuote(boolean z) {
        this.mIsQuote = z;
        updateState(R.id.quote);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBold(boolean z) {
        this.mIsBold = z;
        updateState(R.id.bold);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onItalic(boolean z) {
        this.mIsItalic = z;
        updateState(R.id.italic);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onLeft(boolean z) {
        this.mIsLeft = z;
        if (updateState(R.id.left)) {
            if (this.mIsLeft && this.mIsRight) {
                onRight(false);
            }
            if (this.mIsLeft && this.mIsCenter) {
                onCenter(false);
            }
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCenter(boolean z) {
        this.mIsCenter = z;
        updateState(R.id.center);
        if (this.mIsCenter && this.mIsRight) {
            onRight(false);
        }
        if (this.mIsCenter && this.mIsLeft) {
            onLeft(false);
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canIndent(boolean z) {
        this.mCanIndent = z;
        updateState(R.id.indentright);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canUndent(boolean z) {
        this.mCanUndent = z;
        updateState(R.id.indentleft);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canInsert(boolean z) {
        isNeedLock(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onRight(boolean z) {
        this.mIsRight = z;
        updateState(R.id.right);
        if (this.mIsRight && this.mIsCenter) {
            onCenter(false);
        }
        if (this.mIsRight && this.mIsLeft) {
            onLeft(false);
        }
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onUnderline(boolean z) {
        this.mIsUnderline = z;
        updateState(R.id.underline);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBgHighLight(boolean z) {
        this.mIsBgHighLight = z;
        updateState(R.id.bg_highlight);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBullet(boolean z) {
        this.mIsBullet = z;
        updateState(R.id.bullet);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onListBullet(boolean z) {
        this.mIsListBullet = z;
        updateState(R.id.listbullet);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCheckbox(boolean z) {
        this.mIsCheck = z;
        updateState(R.id.check);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        this.mSizeLevelNow = i;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void resetRichTextStatus() {
        this.mIsAllH1 = false;
        this.mIsAllH2 = false;
        this.mIsAllH3 = false;
        this.mIsStrikeThrough = false;
        this.mIsListBullet = false;
        this.mCanSplitLine = true;
        this.mIsQuote = false;
        this.mCanIndent = true;
        this.mCanUndent = false;
        this.mIsBold = false;
        this.mIsItalic = false;
        this.mSizeLevelNow = 0;
        this.mIsUnderline = false;
        this.mIsBgHighLight = false;
        this.mIsBullet = false;
        this.mIsLeft = true;
        this.mIsRight = false;
        this.mIsCenter = false;
        for (int i : STYLE_VIEW_IDS) {
            updateState(i);
        }
        for (int i2 : FONT_SIZE_VIEW_ID) {
            updateSizeState(i2);
        }
    }

    protected boolean getRichStyleState(int i) {
        switch (i) {
            case R.id.bg_highlight /* 2131362065 */:
                return isBgHighLight();
            case R.id.bold /* 2131362073 */:
                return isBold();
            case R.id.bullet /* 2131362117 */:
                return isBullet();
            case R.id.center /* 2131362149 */:
                return isCenter();
            case R.id.check /* 2131362157 */:
                return isCheckbox();
            case R.id.first_level /* 2131362538 */:
                return isH1();
            case R.id.indentleft /* 2131362744 */:
                return isIndentLeft();
            case R.id.indentright /* 2131362745 */:
                return isIndentRight();
            case R.id.italic /* 2131362763 */:
                return isItalic();
            case R.id.left /* 2131362802 */:
                return isLeft();
            case R.id.listbullet /* 2131362821 */:
                return isListBullet();
            case R.id.quote /* 2131363263 */:
                return isQuote();
            case R.id.right /* 2131363305 */:
                return isRight();
            case R.id.second_level /* 2131363384 */:
                return isH2();
            case R.id.split_line /* 2131363510 */:
                return isCanSplitLine();
            case R.id.strikethrough /* 2131363540 */:
                return isStrikeThrough();
            case R.id.thrid_level /* 2131363616 */:
                return isH3();
            case R.id.underline /* 2131363734 */:
                return isUnderline();
            default:
                return false;
        }
    }

    protected boolean getFontSizeType(int i) {
        if (i == R.id.increase_font) {
            return getSizeLevel() == 2;
        }
        return i == R.id.reduce_font && getSizeLevel() == 0;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        Logger.INSTANCE.d(TAG, "onDestroy-toolBar");
    }
}
