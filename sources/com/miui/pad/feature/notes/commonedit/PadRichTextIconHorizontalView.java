package com.miui.pad.feature.notes.commonedit;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckedTextView;
import android.widget.Scroller;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.IRichTextStyleController;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PadRichTextIconHorizontalView extends ViewGroup implements View.OnClickListener {
    static final int[] ICON_IDS = {R.id.bold, R.id.italic, R.id.underline, R.id.bg_highlight, R.id.bullet, R.id.check, R.id.left, R.id.center, R.id.right, R.id.strikethrough, R.id.listbullet, R.id.split_line, R.id.quote, R.id.thrid_level, R.id.first_level, R.id.second_level, R.id.indentright, R.id.indentleft};
    static final int[] ICON_SIZE_ID = {R.id.increase_font, R.id.reduce_font};
    HoverMotionImpl hoverMotionImpl;
    private IndentForbiddenAccessiabilityDelegate indentForbiddenAccessiabilityDelegate;
    private IndentPermittedAccessiabilityDelegate indentPermittedAccessiabilityDelegate;
    private View.OnClickListener mActionListener;
    private PadRichTextCheckedTextView mBgHighLightView;
    private PadRichTextCheckedTextView mBoldView;
    private PadRichTextCheckedTextView mBulletView;
    private PadRichTextCheckedTextView mCenterView;
    private PadRichTextCheckedTextView mCheckboxView;
    private float mChildMarginVertical;
    private float mChildShadowHeight;
    private float mChildShadowWidth;
    private IRichTextStyleController mController;
    private PadRichTextCheckedTextView mFirstLevelView;
    private int mFlingVelocity;
    private int mIconMarginStart;
    private PadRichTextCheckedTextView mIncreaseFontView;
    private PadRichTextCheckedTextView mIndentLeftView;
    private PadRichTextCheckedTextView mIndentRightView;
    private boolean mIsLite;
    private boolean mIsNewTablet;
    private PadRichTextCheckedTextView mItalicView;
    private int mLastX;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private PadRichTextCheckedTextView mLeftView;
    private PadRichTextCheckedTextView mListBulletView;
    private PadRichTextCheckedTextView mQuoteView;
    private PadRichTextCheckedTextView mReduceFontView;
    private PadRichTextCheckedTextView mRightView;
    private Scroller mScroller;
    private PadRichTextCheckedTextView mSecondLevelView;
    private PadRichTextCheckedTextView mSplitlineView;
    private PadRichTextCheckedTextView mStrikeThroughView;
    private PadRichTextCheckedTextView mThridLevelView;
    private float mTotalWidth;
    private PadRichTextCheckedTextView mUnderlineView;
    private VelocityTracker mVelocityTracker;
    private ViewConfiguration mViewConfiguration;
    private int totalOffsetX;
    private int touchX;

    public void init(IRichTextStyleController iRichTextStyleController, View.OnClickListener onClickListener) {
        this.indentForbiddenAccessiabilityDelegate = new IndentForbiddenAccessiabilityDelegate();
        this.indentPermittedAccessiabilityDelegate = new IndentPermittedAccessiabilityDelegate();
        this.mController = iRichTextStyleController;
        this.mActionListener = onClickListener;
        for (int i : ICON_IDS) {
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(i);
            checkedTextView.setOnClickListener(this);
            HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
            if (hoverMotionImpl != null && !this.mIsLite && this.mIsNewTablet) {
                hoverMotionImpl.setOnHoverListener(checkedTextView);
            }
            checkedTextView.setChecked(getState(i));
        }
        for (int i2 : ICON_SIZE_ID) {
            CheckedTextView checkedTextView2 = (CheckedTextView) findViewById(i2);
            checkedTextView2.setOnClickListener(this);
            checkedTextView2.setChecked(getType(i2));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
        if (hoverMotionImpl != null) {
            hoverMotionImpl.onDetach();
        }
    }

    public void setFocusAble(boolean z) {
        this.mIndentRightView.setFocusable(z);
        this.mIndentLeftView.setFocusable(z);
    }

    public void setRichViewsEnable(boolean z) {
        this.mIncreaseFontView.setEnabled(z);
        this.mReduceFontView.setEnabled(z);
        this.mBoldView.setEnabled(z);
        this.mItalicView.setEnabled(z);
        this.mUnderlineView.setEnabled(z);
        this.mCheckboxView.setEnabled(z);
        this.mBgHighLightView.setEnabled(z);
        this.mBulletView.setEnabled(z);
        this.mLeftView.setEnabled(z);
        this.mCenterView.setEnabled(z);
        this.mRightView.setEnabled(z);
        this.mFirstLevelView.setEnabled(z);
        this.mSecondLevelView.setEnabled(z);
        this.mThridLevelView.setEnabled(z);
        this.mStrikeThroughView.setEnabled(z);
        this.mListBulletView.setEnabled(z);
        this.mSplitlineView.setEnabled(z);
        this.mQuoteView.setEnabled(z);
        this.mIndentRightView.setEnabled(z);
        this.mIndentLeftView.setEnabled(z);
    }

    public boolean updateState(int i) {
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(i);
        if (checkedTextView == null) {
            return false;
        }
        if (i == R.id.indentright || i == R.id.indentleft) {
            setAccessiabilityDelegate(i, checkedTextView);
        }
        checkedTextView.setChecked(getState(i));
        return true;
    }

    private void setAccessiabilityDelegate(int i, View view) {
        if (!getState(i)) {
            this.indentForbiddenAccessiabilityDelegate.onInitializeAccessibilityNodeInfo(view, view.createAccessibilityNodeInfo());
            view.setAccessibilityDelegate(this.indentForbiddenAccessiabilityDelegate);
        } else {
            this.indentPermittedAccessiabilityDelegate.onInitializeAccessibilityNodeInfo(view, view.createAccessibilityNodeInfo());
            view.setAccessibilityDelegate(this.indentPermittedAccessiabilityDelegate);
        }
    }

    private class IndentForbiddenAccessiabilityDelegate extends View.AccessibilityDelegate {
        public IndentForbiddenAccessiabilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setEnabled(false);
        }
    }

    private class IndentPermittedAccessiabilityDelegate extends View.AccessibilityDelegate {
        public IndentPermittedAccessiabilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClickable(true);
        }
    }

    public boolean updateSizeState(int i) {
        CheckedTextView checkedTextView;
        if ((!this.mIsLite && this.mIsNewTablet) || (checkedTextView = (CheckedTextView) findViewById(i)) == null) {
            return false;
        }
        checkedTextView.setChecked(getType(i));
        return true;
    }

    private boolean getState(int i) {
        switch (i) {
            case R.id.bg_highlight /* 2131362065 */:
                return this.mController.isBgHighLight();
            case R.id.bold /* 2131362073 */:
                return this.mController.isBold();
            case R.id.bullet /* 2131362117 */:
                return this.mController.isBullet();
            case R.id.center /* 2131362149 */:
                return this.mController.isCenter();
            case R.id.check /* 2131362157 */:
                return this.mController.isCheckbox();
            case R.id.first_level /* 2131362538 */:
                return this.mController.isH1();
            case R.id.indentleft /* 2131362744 */:
                return this.mController.isIndentLeft();
            case R.id.indentright /* 2131362745 */:
                return this.mController.isIndentRight();
            case R.id.italic /* 2131362763 */:
                return this.mController.isItalic();
            case R.id.left /* 2131362802 */:
                return this.mController.isLeft();
            case R.id.listbullet /* 2131362821 */:
                return this.mController.isListBullet();
            case R.id.quote /* 2131363263 */:
                return this.mController.isQuote();
            case R.id.right /* 2131363305 */:
                return this.mController.isRight();
            case R.id.second_level /* 2131363384 */:
                return this.mController.isH2();
            case R.id.split_line /* 2131363510 */:
                return this.mController.isCanSplitLine();
            case R.id.strikethrough /* 2131363540 */:
                return this.mController.isStrikeThrough();
            case R.id.thrid_level /* 2131363616 */:
                return this.mController.isH3();
            case R.id.underline /* 2131363734 */:
                return this.mController.isUnderline();
            default:
                return false;
        }
    }

    private boolean getType(int i) {
        if (i == R.id.increase_font) {
            return this.mController.getSizeLevel() == 2;
        }
        return i == R.id.reduce_font && this.mController.getSizeLevel() == 0;
    }

    public PadRichTextIconHorizontalView(Context context) {
        this(context, null);
    }

    public PadRichTextIconHorizontalView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PadRichTextIconHorizontalView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildShadowWidth = 0.0f;
        this.mChildShadowHeight = 0.0f;
        this.mChildMarginVertical = 0.0f;
        this.mLastX = 0;
        this.totalOffsetX = 0;
        this.mLastXIntercept = 0;
        this.mLastYIntercept = 0;
        this.hoverMotionImpl = new HoverMotionImpl();
        init(context);
    }

    private void init(Context context) {
        this.mIsLite = LiteUtils.isLiteOrMiddle();
        this.mIsNewTablet = RomUtils.isNewTablet();
        this.mChildShadowWidth = context.getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size);
        this.mChildShadowHeight = context.getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size);
        this.mChildMarginVertical = context.getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_margin_vertical);
        Resources resources = context.getResources();
        int i = R.dimen.notes_edit_toolbar_icon_margin_start_big;
        this.mIconMarginStart = resources.getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_margin_start_big);
        if (this.mIsNewTablet) {
            if (getContext().getResources().getConfiguration().orientation != 2) {
                i = R.dimen.notes_edit_toolbar_icon_margin_start_big_vertical;
            }
            this.mIconMarginStart = context.getResources().getDimensionPixelSize(i);
        }
        updateDrawParams();
        this.mScroller = new Scroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mViewConfiguration = ViewConfiguration.get(context);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        PadRichTextCheckedTextView padRichTextCheckedTextView = (PadRichTextCheckedTextView) findViewById(R.id.bg_highlight);
        this.mBgHighLightView = padRichTextCheckedTextView;
        Folme.useAt(padRichTextCheckedTextView).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mBgHighLightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBgHighLightView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView2 = (PadRichTextCheckedTextView) findViewById(R.id.first_level);
        this.mFirstLevelView = padRichTextCheckedTextView2;
        Folme.useAt(padRichTextCheckedTextView2).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mFirstLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mFirstLevelView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView3 = (PadRichTextCheckedTextView) findViewById(R.id.second_level);
        this.mSecondLevelView = padRichTextCheckedTextView3;
        Folme.useAt(padRichTextCheckedTextView3).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mSecondLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mSecondLevelView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView4 = (PadRichTextCheckedTextView) findViewById(R.id.thrid_level);
        this.mThridLevelView = padRichTextCheckedTextView4;
        Folme.useAt(padRichTextCheckedTextView4).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mThridLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mThridLevelView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView5 = (PadRichTextCheckedTextView) findViewById(R.id.strikethrough);
        this.mStrikeThroughView = padRichTextCheckedTextView5;
        Folme.useAt(padRichTextCheckedTextView5).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mStrikeThroughView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mStrikeThroughView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView6 = (PadRichTextCheckedTextView) findViewById(R.id.listbullet);
        this.mListBulletView = padRichTextCheckedTextView6;
        Folme.useAt(padRichTextCheckedTextView6).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mListBulletView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mListBulletView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView7 = (PadRichTextCheckedTextView) findViewById(R.id.split_line);
        this.mSplitlineView = padRichTextCheckedTextView7;
        Folme.useAt(padRichTextCheckedTextView7).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mSplitlineView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mSplitlineView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView8 = (PadRichTextCheckedTextView) findViewById(R.id.quote);
        this.mQuoteView = padRichTextCheckedTextView8;
        Folme.useAt(padRichTextCheckedTextView8).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mQuoteView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mQuoteView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView9 = (PadRichTextCheckedTextView) findViewById(R.id.bold);
        this.mBoldView = padRichTextCheckedTextView9;
        Folme.useAt(padRichTextCheckedTextView9).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mBoldView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBoldView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView10 = (PadRichTextCheckedTextView) findViewById(R.id.italic);
        this.mItalicView = padRichTextCheckedTextView10;
        Folme.useAt(padRichTextCheckedTextView10).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mItalicView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mItalicView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView11 = (PadRichTextCheckedTextView) findViewById(R.id.underline);
        this.mUnderlineView = padRichTextCheckedTextView11;
        Folme.useAt(padRichTextCheckedTextView11).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mUnderlineView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mUnderlineView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView12 = (PadRichTextCheckedTextView) findViewById(R.id.check);
        this.mCheckboxView = padRichTextCheckedTextView12;
        Folme.useAt(padRichTextCheckedTextView12).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mCheckboxView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mCheckboxView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView13 = (PadRichTextCheckedTextView) findViewById(R.id.left);
        this.mLeftView = padRichTextCheckedTextView13;
        Folme.useAt(padRichTextCheckedTextView13).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mLeftView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mLeftView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView14 = (PadRichTextCheckedTextView) findViewById(R.id.center);
        this.mCenterView = padRichTextCheckedTextView14;
        Folme.useAt(padRichTextCheckedTextView14).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mCenterView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mCenterView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView15 = (PadRichTextCheckedTextView) findViewById(R.id.right);
        this.mRightView = padRichTextCheckedTextView15;
        Folme.useAt(padRichTextCheckedTextView15).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mRightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRightView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView16 = (PadRichTextCheckedTextView) findViewById(R.id.bullet);
        this.mBulletView = padRichTextCheckedTextView16;
        Folme.useAt(padRichTextCheckedTextView16).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mBulletView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBulletView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView17 = (PadRichTextCheckedTextView) findViewById(R.id.indentright);
        this.mIndentRightView = padRichTextCheckedTextView17;
        Folme.useAt(padRichTextCheckedTextView17).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mIndentRightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIndentRightView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView18 = (PadRichTextCheckedTextView) findViewById(R.id.indentleft);
        this.mIndentLeftView = padRichTextCheckedTextView18;
        Folme.useAt(padRichTextCheckedTextView18).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mIndentLeftView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIndentLeftView, new AnimConfig[0]);
        PadRichTextCheckedTextView padRichTextCheckedTextView19 = (PadRichTextCheckedTextView) findViewById(R.id.increase_font);
        this.mIncreaseFontView = padRichTextCheckedTextView19;
        Folme.useAt(padRichTextCheckedTextView19).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mIncreaseFontView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIncreaseFontView, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mIncreaseFontView);
        PadRichTextCheckedTextView padRichTextCheckedTextView20 = (PadRichTextCheckedTextView) findViewById(R.id.reduce_font);
        this.mReduceFontView = padRichTextCheckedTextView20;
        Folme.useAt(padRichTextCheckedTextView20).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
        Folme.useAt(this.mReduceFontView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mReduceFontView, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mReduceFontView);
    }

    public void updateStyle(NoteThemeResCache noteThemeResCache) {
        this.mBoldView.setDrawable(noteThemeResCache.toolBarBoldIcon);
        this.mItalicView.setDrawable(noteThemeResCache.toolBarItalicIcon);
        this.mUnderlineView.setDrawable(noteThemeResCache.toolBarUnderlineIcon);
        this.mLeftView.setDrawable(noteThemeResCache.toolBarLeftIcon);
        this.mCenterView.setDrawable(noteThemeResCache.toolBarCenterIcon);
        this.mRightView.setDrawable(noteThemeResCache.toolBarRightIcon);
        this.mBgHighLightView.setDrawable(noteThemeResCache.toolBarBgHighLightIcon);
        this.mCheckboxView.setDrawable(noteThemeResCache.toolBarCheckIcon);
        this.mBulletView.setDrawable(noteThemeResCache.toolBarBulletIcon);
        this.mFirstLevelView.setDrawable(noteThemeResCache.toolBarFirstLevelIcon);
        this.mSecondLevelView.setDrawable(noteThemeResCache.toolBarSecondLevelIcon);
        this.mThridLevelView.setDrawable(noteThemeResCache.toolBarThridLevelIcon);
        this.mStrikeThroughView.setDrawable(noteThemeResCache.toolBarStrikeThroughIcon);
        this.mListBulletView.setDrawable(noteThemeResCache.toolBarListBulletIcon);
        this.mSplitlineView.setDrawable(noteThemeResCache.toolBarSplitLineIcon);
        this.mQuoteView.setDrawable(noteThemeResCache.toolBarQuoteIcon);
        this.mIndentRightView.setDrawable(noteThemeResCache.toolBarIndentRightIcon);
        this.mIndentLeftView.setDrawable(noteThemeResCache.toolBarIndentLeftIcon);
        this.mIncreaseFontView.setDrawable(noteThemeResCache.toolBarIncreaseSizeIcon);
        this.mReduceFontView.setDrawable(noteThemeResCache.toolBarDecreaseSizeIcon);
    }

    private Drawable getButtonDrawable(int i, Theme theme) {
        return theme.getResTheme(getContext()).getDrawable(i).mutate();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        float f = this.mChildShadowHeight;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) this.mChildShadowWidth, 1073741824);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((int) f, 1073741824);
        this.mBgHighLightView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mBoldView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mItalicView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mUnderlineView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mCheckboxView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mBulletView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mLeftView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mCenterView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mRightView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mIncreaseFontView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mReduceFontView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mFirstLevelView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mSecondLevelView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mThridLevelView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mStrikeThroughView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mListBulletView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mSplitlineView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mQuoteView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mIndentRightView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mIndentLeftView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        if (mode != 1073741824) {
            size = (int) this.mTotalWidth;
        }
        if (mode2 != 1073741824) {
            size2 = (int) (f + (this.mChildMarginVertical * 2.0f));
        }
        setMeasuredDimension(size, size2);
    }

    private void updateDrawParams() {
        if (this.mIsLite || !this.mIsNewTablet) {
            this.mTotalWidth = (getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size) * 11) + (this.mIconMarginStart * 12);
        } else {
            this.mTotalWidth = (getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size) * 18) + (this.mIconMarginStart * 18) + getContext().getResources().getDimensionPixelSize(R.dimen.pad_notes_edit_toolbar_icon_margin_end);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth;
        int measuredWidth2;
        int i5;
        if (z) {
            int i6 = this.mIconMarginStart;
            int i7 = (int) this.mChildMarginVertical;
            PadRichTextCheckedTextView padRichTextCheckedTextView = this.mBgHighLightView;
            padRichTextCheckedTextView.layout(i6, i7, padRichTextCheckedTextView.getMeasuredWidth() + i6, this.mBgHighLightView.getMeasuredHeight() + i7);
            int measuredWidth3 = i6 + this.mBgHighLightView.getMeasuredWidth() + this.mIconMarginStart;
            if (this.mIsLite || !this.mIsNewTablet) {
                PadRichTextCheckedTextView padRichTextCheckedTextView2 = this.mIncreaseFontView;
                padRichTextCheckedTextView2.layout(measuredWidth3, i7, padRichTextCheckedTextView2.getMeasuredWidth() + measuredWidth3, this.mIncreaseFontView.getMeasuredHeight() + i7);
                measuredWidth = measuredWidth3 + this.mIncreaseFontView.getMeasuredWidth() + this.mIconMarginStart;
                PadRichTextCheckedTextView padRichTextCheckedTextView3 = this.mReduceFontView;
                padRichTextCheckedTextView3.layout(measuredWidth, i7, padRichTextCheckedTextView3.getMeasuredWidth() + measuredWidth, this.mReduceFontView.getMeasuredHeight() + i7);
                measuredWidth2 = this.mReduceFontView.getMeasuredWidth();
                i5 = this.mIconMarginStart;
            } else {
                PadRichTextCheckedTextView padRichTextCheckedTextView4 = this.mFirstLevelView;
                padRichTextCheckedTextView4.layout(measuredWidth3, i7, padRichTextCheckedTextView4.getMeasuredWidth() + measuredWidth3, this.mFirstLevelView.getMeasuredHeight() + i7);
                int measuredWidth4 = measuredWidth3 + this.mFirstLevelView.getMeasuredWidth() + this.mIconMarginStart;
                PadRichTextCheckedTextView padRichTextCheckedTextView5 = this.mSecondLevelView;
                padRichTextCheckedTextView5.layout(measuredWidth4, i7, padRichTextCheckedTextView5.getMeasuredWidth() + measuredWidth4, this.mSecondLevelView.getMeasuredHeight() + i7);
                measuredWidth = measuredWidth4 + this.mSecondLevelView.getMeasuredWidth() + this.mIconMarginStart;
                PadRichTextCheckedTextView padRichTextCheckedTextView6 = this.mThridLevelView;
                padRichTextCheckedTextView6.layout(measuredWidth, i7, padRichTextCheckedTextView6.getMeasuredWidth() + measuredWidth, this.mThridLevelView.getMeasuredHeight() + i7);
                measuredWidth2 = this.mThridLevelView.getMeasuredWidth();
                i5 = this.mIconMarginStart;
            }
            int i8 = measuredWidth + measuredWidth2 + i5;
            PadRichTextCheckedTextView padRichTextCheckedTextView7 = this.mBoldView;
            padRichTextCheckedTextView7.layout(i8, i7, padRichTextCheckedTextView7.getMeasuredWidth() + i8, this.mBoldView.getMeasuredHeight() + i7);
            int measuredWidth5 = i8 + this.mBoldView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView8 = this.mItalicView;
            padRichTextCheckedTextView8.layout(measuredWidth5, i7, padRichTextCheckedTextView8.getMeasuredWidth() + measuredWidth5, this.mItalicView.getMeasuredHeight() + i7);
            int measuredWidth6 = measuredWidth5 + this.mItalicView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView9 = this.mUnderlineView;
            padRichTextCheckedTextView9.layout(measuredWidth6, i7, padRichTextCheckedTextView9.getMeasuredWidth() + measuredWidth6, this.mUnderlineView.getMeasuredHeight() + i7);
            int measuredWidth7 = measuredWidth6 + this.mUnderlineView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView10 = this.mCheckboxView;
            padRichTextCheckedTextView10.layout(measuredWidth7, i7, padRichTextCheckedTextView10.getMeasuredWidth() + measuredWidth7, this.mCheckboxView.getMeasuredHeight() + i7);
            int measuredWidth8 = measuredWidth7 + this.mCheckboxView.getMeasuredWidth() + this.mIconMarginStart;
            if (!this.mIsLite && this.mIsNewTablet) {
                PadRichTextCheckedTextView padRichTextCheckedTextView11 = this.mStrikeThroughView;
                padRichTextCheckedTextView11.layout(measuredWidth8, i7, padRichTextCheckedTextView11.getMeasuredWidth() + measuredWidth8, this.mStrikeThroughView.getMeasuredHeight() + i7);
                measuredWidth8 += this.mStrikeThroughView.getMeasuredWidth() + this.mIconMarginStart;
            }
            PadRichTextCheckedTextView padRichTextCheckedTextView12 = this.mBulletView;
            padRichTextCheckedTextView12.layout(measuredWidth8, i7, padRichTextCheckedTextView12.getMeasuredWidth() + measuredWidth8, this.mBulletView.getMeasuredHeight() + i7);
            int measuredWidth9 = measuredWidth8 + this.mBulletView.getMeasuredWidth() + this.mIconMarginStart;
            if (!this.mIsLite && this.mIsNewTablet) {
                PadRichTextCheckedTextView padRichTextCheckedTextView13 = this.mListBulletView;
                padRichTextCheckedTextView13.layout(measuredWidth9, i7, padRichTextCheckedTextView13.getMeasuredWidth() + measuredWidth9, this.mListBulletView.getMeasuredHeight() + i7);
                int measuredWidth10 = measuredWidth9 + this.mListBulletView.getMeasuredWidth() + this.mIconMarginStart;
                PadRichTextCheckedTextView padRichTextCheckedTextView14 = this.mSplitlineView;
                padRichTextCheckedTextView14.layout(measuredWidth10, i7, padRichTextCheckedTextView14.getMeasuredWidth() + measuredWidth10, this.mSplitlineView.getMeasuredHeight() + i7);
                int measuredWidth11 = measuredWidth10 + this.mSplitlineView.getMeasuredWidth() + this.mIconMarginStart;
                PadRichTextCheckedTextView padRichTextCheckedTextView15 = this.mQuoteView;
                padRichTextCheckedTextView15.layout(measuredWidth11, i7, padRichTextCheckedTextView15.getMeasuredWidth() + measuredWidth11, this.mQuoteView.getMeasuredHeight() + i7);
                measuredWidth9 = measuredWidth11 + this.mQuoteView.getMeasuredWidth() + this.mIconMarginStart;
            }
            PadRichTextCheckedTextView padRichTextCheckedTextView16 = this.mLeftView;
            padRichTextCheckedTextView16.layout(measuredWidth9, i7, padRichTextCheckedTextView16.getMeasuredWidth() + measuredWidth9, this.mLeftView.getMeasuredHeight() + i7);
            int measuredWidth12 = measuredWidth9 + this.mLeftView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView17 = this.mCenterView;
            padRichTextCheckedTextView17.layout(measuredWidth12, i7, padRichTextCheckedTextView17.getMeasuredWidth() + measuredWidth12, this.mCenterView.getMeasuredHeight() + i7);
            int measuredWidth13 = measuredWidth12 + this.mCenterView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView18 = this.mRightView;
            padRichTextCheckedTextView18.layout(measuredWidth13, i7, padRichTextCheckedTextView18.getMeasuredWidth() + measuredWidth13, this.mRightView.getMeasuredHeight() + i7);
            if (this.mIsLite || !this.mIsNewTablet) {
                return;
            }
            int measuredWidth14 = measuredWidth13 + this.mRightView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView19 = this.mIndentRightView;
            padRichTextCheckedTextView19.layout(measuredWidth14, i7, padRichTextCheckedTextView19.getMeasuredWidth() + measuredWidth14, this.mIndentRightView.getMeasuredHeight() + i7);
            int measuredWidth15 = measuredWidth14 + this.mIndentRightView.getMeasuredWidth() + this.mIconMarginStart;
            PadRichTextCheckedTextView padRichTextCheckedTextView20 = this.mIndentLeftView;
            padRichTextCheckedTextView20.layout(measuredWidth15, i7, padRichTextCheckedTextView20.getMeasuredWidth() + measuredWidth15, this.mIndentLeftView.getMeasuredHeight() + i7);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.touchX = (int) (this.mTotalWidth - getWidth());
        int x = (int) motionEvent.getX();
        motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mVelocityTracker.clear();
            this.mVelocityTracker.addMovement(motionEvent);
        } else if (action == 1) {
            this.mVelocityTracker.addMovement(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1000);
        } else if (action == 2) {
            this.mVelocityTracker.addMovement(motionEvent);
            int i = this.totalOffsetX + (this.mLastX - x);
            this.totalOffsetX = i;
            if (i < 0) {
                this.totalOffsetX = 0;
                scrollTo(0, 0);
            } else {
                int i2 = this.touchX;
                if (i >= i2) {
                    this.totalOffsetX = i2;
                    scrollTo(i2, 0);
                } else {
                    scrollTo(i, 0);
                }
            }
        }
        this.mLastX = x;
        return true;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mIsNewTablet) {
            this.mIconMarginStart = getContext().getResources().getDimensionPixelSize(getContext().getResources().getConfiguration().orientation == 2 ? R.dimen.notes_edit_toolbar_icon_margin_start_big : R.dimen.notes_edit_toolbar_icon_margin_start_big_vertical);
        }
        updateDrawParams();
        scrollTo(0, 0);
        requestLayout();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int currX = this.mScroller.getCurrX();
            this.totalOffsetX = currX;
            if (currX < 0) {
                this.totalOffsetX = 0;
                scrollTo(0, 0);
            } else {
                int i = this.touchX;
                if (currX >= i) {
                    this.totalOffsetX = i;
                    scrollTo(i, 0);
                } else {
                    scrollTo(currX, 0);
                }
            }
            scrollTo(this.totalOffsetX, 0);
        }
    }

    public void resetOffset() {
        this.totalOffsetX = 0;
        scrollTo(0, 0);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        motionEvent.getY();
        if (motionEvent.getAction() == 2 && Math.abs(this.mLastX - motionEvent.getX()) > this.mViewConfiguration.getScaledTouchSlop()) {
            return true;
        }
        this.mLastX = x;
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        View.OnClickListener onClickListener = this.mActionListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public void dismissPopupWindow() {
        this.hoverMotionImpl.dismissPopupWindow();
    }
}
