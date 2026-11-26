package com.miui.notes.ui.view;

import android.content.Context;
import android.content.res.Configuration;
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
import com.miui.pad.feature.notes.commonedit.HoverMotionImpl;
import com.miui.richeditor.IRichTextStyleController;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class RichTextIconHorizontalView extends ViewGroup implements View.OnClickListener {
    static final int[] ICON_IDS = {R.id.bold, R.id.italic, R.id.underline, R.id.bg_highlight, R.id.bullet, R.id.center, R.id.right, R.id.strikethrough, R.id.listbullet, R.id.split_line, R.id.quote, R.id.thrid_level, R.id.first_level, R.id.second_level, R.id.indentright, R.id.indentleft};
    static final int[] ICON_SIZE_ID = {R.id.increase_font, R.id.reduce_font};
    HoverMotionImpl hoverMotionImpl;
    private IndentForbiddenAccessiabilityDelegate indentForbiddenAccessiabilityDelegate;
    private IndentPermittedAccessiabilityDelegate indentPermittedAccessiabilityDelegate;
    private View.OnClickListener mActionListener;
    private RichTextCheckedTextView mBgHighLightView;
    private RichTextCheckedTextView mBoldView;
    private RichTextCheckedTextView mBulletView;
    private RichTextCheckedTextView mCenterView;
    private float mChildMarginVertical;
    private float mChildShadowHeight;
    private float mChildShadowWidth;
    private IRichTextStyleController mController;
    private RichTextCheckedTextView mFirstLevelView;
    private int mFlingVelocity;
    private float mIconGroupMarginStart;
    private int mIconMarginStart;
    private RichTextCheckedTextView mIncreaseFontView;
    private RichTextCheckedTextView mIndentLeftView;
    private RichTextCheckedTextView mIndentRightView;
    private boolean mIsLite;
    private boolean mIsNewTablet;
    private RichTextCheckedTextView mItalicView;
    private int mLastX;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private RichTextCheckedTextView mListBulletView;
    private RichTextCheckedTextView mQuoteView;
    private RichTextCheckedTextView mReduceFontView;
    private RichTextCheckedTextView mRightView;
    private Scroller mScroller;
    private RichTextCheckedTextView mSecondLevelView;
    private RichTextCheckedTextView mSplitlineView;
    private RichTextCheckedTextView mStrikeThroughView;
    private RichTextCheckedTextView mThridLevelView;
    private float mTotalWidth;
    private RichTextCheckedTextView mUnderlineView;
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
            HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
            if (hoverMotionImpl != null && !this.mIsLite && this.mIsNewTablet) {
                hoverMotionImpl.setOnHoverListener(checkedTextView);
            }
            checkedTextView.setOnClickListener(this);
            checkedTextView.setChecked(getState(i));
        }
        for (int i2 : ICON_SIZE_ID) {
            CheckedTextView checkedTextView2 = (CheckedTextView) findViewById(i2);
            checkedTextView2.setOnClickListener(this);
            checkedTextView2.setChecked(getType(i2));
        }
        resetFolme();
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
            case R.id.first_level /* 2131362538 */:
                return this.mController.isH1();
            case R.id.indentleft /* 2131362744 */:
                return this.mController.isIndentLeft();
            case R.id.indentright /* 2131362745 */:
                return this.mController.isIndentRight();
            case R.id.italic /* 2131362763 */:
                return this.mController.isItalic();
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

    public RichTextIconHorizontalView(Context context) {
        this(context, null);
    }

    public RichTextIconHorizontalView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RichTextIconHorizontalView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildShadowWidth = 370.0f;
        this.mChildShadowHeight = 196.0f;
        this.mChildMarginVertical = 0.0f;
        this.mIconMarginStart = 0;
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
        this.mChildShadowWidth = context.getResources().getDimension(R.dimen.notes_edit_toolbar_icon_size);
        this.mChildShadowHeight = context.getResources().getDimension(R.dimen.notes_edit_toolbar_icon_size);
        this.mChildMarginVertical = context.getResources().getDimension(R.dimen.notes_edit_toolbar_icon_margin_vertical);
        this.mIconMarginStart = (int) context.getResources().getDimension(R.dimen.notes_edit_toolbar_icon_margin_start_small);
        updateDrawParams();
        this.mScroller = new Scroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mViewConfiguration = ViewConfiguration.get(context);
    }

    private void updateDrawParams() {
        this.mIconGroupMarginStart = getContext().getResources().getDimension(R.dimen.notes_edit_rich_text_icon_group_margin_start);
        if (this.mIsLite || !this.mIsNewTablet) {
            this.mTotalWidth = (getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size) * 9) + (this.mIconMarginStart * 8) + this.mIconGroupMarginStart;
        } else {
            this.mTotalWidth = (getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_size) * 16) + (this.mIconMarginStart * 15) + this.mIconGroupMarginStart;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBgHighLightView = (RichTextCheckedTextView) findViewById(R.id.bg_highlight);
        this.mBoldView = (RichTextCheckedTextView) findViewById(R.id.bold);
        this.mItalicView = (RichTextCheckedTextView) findViewById(R.id.italic);
        this.mUnderlineView = (RichTextCheckedTextView) findViewById(R.id.underline);
        this.mFirstLevelView = (RichTextCheckedTextView) findViewById(R.id.first_level);
        this.mSecondLevelView = (RichTextCheckedTextView) findViewById(R.id.second_level);
        this.mThridLevelView = (RichTextCheckedTextView) findViewById(R.id.thrid_level);
        this.mStrikeThroughView = (RichTextCheckedTextView) findViewById(R.id.strikethrough);
        this.mListBulletView = (RichTextCheckedTextView) findViewById(R.id.listbullet);
        this.mSplitlineView = (RichTextCheckedTextView) findViewById(R.id.split_line);
        this.mQuoteView = (RichTextCheckedTextView) findViewById(R.id.quote);
        this.mCenterView = (RichTextCheckedTextView) findViewById(R.id.center);
        this.mRightView = (RichTextCheckedTextView) findViewById(R.id.right);
        this.mBulletView = (RichTextCheckedTextView) findViewById(R.id.bullet);
        this.mIndentRightView = (RichTextCheckedTextView) findViewById(R.id.indentright);
        this.mIndentLeftView = (RichTextCheckedTextView) findViewById(R.id.indentleft);
        this.mIncreaseFontView = (RichTextCheckedTextView) findViewById(R.id.increase_font);
        this.mReduceFontView = (RichTextCheckedTextView) findViewById(R.id.reduce_font);
    }

    public void resetFolme() {
        RichTextCheckedTextView richTextCheckedTextView = this.mBgHighLightView;
        if (richTextCheckedTextView != null) {
            Folme.useAt(richTextCheckedTextView).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mBgHighLightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBgHighLightView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView2 = this.mBoldView;
        if (richTextCheckedTextView2 != null) {
            Folme.useAt(richTextCheckedTextView2).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mBoldView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBoldView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView3 = this.mItalicView;
        if (richTextCheckedTextView3 != null) {
            Folme.useAt(richTextCheckedTextView3).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mItalicView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mItalicView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView4 = this.mUnderlineView;
        if (richTextCheckedTextView4 != null) {
            Folme.useAt(richTextCheckedTextView4).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mUnderlineView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mUnderlineView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView5 = this.mFirstLevelView;
        if (richTextCheckedTextView5 != null) {
            Folme.useAt(richTextCheckedTextView5).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mFirstLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mFirstLevelView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView6 = this.mSecondLevelView;
        if (richTextCheckedTextView6 != null) {
            Folme.useAt(richTextCheckedTextView6).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mSecondLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mSecondLevelView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView7 = this.mThridLevelView;
        if (richTextCheckedTextView7 != null) {
            Folme.useAt(richTextCheckedTextView7).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mThridLevelView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mThridLevelView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView8 = this.mStrikeThroughView;
        if (richTextCheckedTextView8 != null) {
            Folme.useAt(richTextCheckedTextView8).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mStrikeThroughView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mStrikeThroughView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView9 = this.mListBulletView;
        if (richTextCheckedTextView9 != null) {
            Folme.useAt(richTextCheckedTextView9).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mListBulletView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mListBulletView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView10 = this.mSplitlineView;
        if (richTextCheckedTextView10 != null) {
            Folme.useAt(richTextCheckedTextView10).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mSplitlineView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mSplitlineView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView11 = this.mQuoteView;
        if (richTextCheckedTextView11 != null) {
            Folme.useAt(richTextCheckedTextView11).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mQuoteView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mQuoteView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView12 = this.mCenterView;
        if (richTextCheckedTextView12 != null) {
            Folme.useAt(richTextCheckedTextView12).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mCenterView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mCenterView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView13 = this.mRightView;
        if (richTextCheckedTextView13 != null) {
            Folme.useAt(richTextCheckedTextView13).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mRightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRightView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView14 = this.mBulletView;
        if (richTextCheckedTextView14 != null) {
            Folme.useAt(richTextCheckedTextView14).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mBulletView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mBulletView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView15 = this.mIndentRightView;
        if (richTextCheckedTextView15 != null) {
            Folme.useAt(richTextCheckedTextView15).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mIndentRightView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIndentRightView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView16 = this.mIndentLeftView;
        if (richTextCheckedTextView16 != null) {
            Folme.useAt(richTextCheckedTextView16).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mIndentLeftView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIndentLeftView, new AnimConfig[0]);
        }
        RichTextCheckedTextView richTextCheckedTextView17 = this.mIncreaseFontView;
        if (richTextCheckedTextView17 != null) {
            Folme.useAt(richTextCheckedTextView17).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mIncreaseFontView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mIncreaseFontView, new AnimConfig[0]);
            UIUtils.setFolmeHoverEffect(this.mIncreaseFontView);
        }
        RichTextCheckedTextView richTextCheckedTextView18 = this.mReduceFontView;
        if (richTextCheckedTextView18 != null) {
            Folme.useAt(richTextCheckedTextView18).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f);
            Folme.useAt(this.mReduceFontView).touch().setScale(0.85f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mReduceFontView, new AnimConfig[0]);
            UIUtils.setFolmeHoverEffect(this.mReduceFontView);
        }
    }

    public void updateStyle(NoteThemeResCache noteThemeResCache) {
        this.mBoldView.setDrawable(noteThemeResCache.toolBarBoldIcon);
        this.mItalicView.setDrawable(noteThemeResCache.toolBarItalicIcon);
        this.mUnderlineView.setDrawable(noteThemeResCache.toolBarUnderlineIcon);
        this.mItalicView.setDrawable(noteThemeResCache.toolBarItalicIcon);
        this.mCenterView.setDrawable(noteThemeResCache.toolBarCenterIcon);
        this.mRightView.setDrawable(noteThemeResCache.toolBarRightIcon);
        this.mBgHighLightView.setDrawable(noteThemeResCache.toolBarBgHighLightIcon);
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
        this.mBoldView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mItalicView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mUnderlineView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mCenterView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mRightView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mBgHighLightView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mBulletView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        if (mode != 1073741824) {
            size = (int) this.mTotalWidth;
        }
        if (mode2 != 1073741824) {
            size2 = (int) (f + (this.mChildMarginVertical * 2.0f));
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth;
        int measuredWidth2;
        int i5;
        if (z) {
            int i6 = (int) this.mIconGroupMarginStart;
            int i7 = (int) this.mChildMarginVertical;
            RichTextCheckedTextView richTextCheckedTextView = this.mBgHighLightView;
            richTextCheckedTextView.layout(i6, i7, richTextCheckedTextView.getMeasuredWidth() + i6, this.mBgHighLightView.getMeasuredHeight() + i7);
            int measuredWidth3 = i6 + this.mBgHighLightView.getMeasuredWidth() + this.mIconMarginStart;
            if (this.mIsLite || !this.mIsNewTablet) {
                RichTextCheckedTextView richTextCheckedTextView2 = this.mIncreaseFontView;
                richTextCheckedTextView2.layout(measuredWidth3, i7, richTextCheckedTextView2.getMeasuredWidth() + measuredWidth3, this.mIncreaseFontView.getMeasuredHeight() + i7);
                measuredWidth = measuredWidth3 + this.mIncreaseFontView.getMeasuredWidth() + this.mIconMarginStart;
                RichTextCheckedTextView richTextCheckedTextView3 = this.mReduceFontView;
                richTextCheckedTextView3.layout(measuredWidth, i7, richTextCheckedTextView3.getMeasuredWidth() + measuredWidth, this.mReduceFontView.getMeasuredHeight() + i7);
                measuredWidth2 = this.mReduceFontView.getMeasuredWidth();
                i5 = this.mIconMarginStart;
            } else {
                RichTextCheckedTextView richTextCheckedTextView4 = this.mFirstLevelView;
                richTextCheckedTextView4.layout(measuredWidth3, i7, richTextCheckedTextView4.getMeasuredWidth() + measuredWidth3, this.mFirstLevelView.getMeasuredHeight() + i7);
                int measuredWidth4 = measuredWidth3 + this.mFirstLevelView.getMeasuredWidth() + this.mIconMarginStart;
                RichTextCheckedTextView richTextCheckedTextView5 = this.mSecondLevelView;
                richTextCheckedTextView5.layout(measuredWidth4, i7, richTextCheckedTextView5.getMeasuredWidth() + measuredWidth4, this.mSecondLevelView.getMeasuredHeight() + i7);
                measuredWidth = measuredWidth4 + this.mSecondLevelView.getMeasuredWidth() + this.mIconMarginStart;
                RichTextCheckedTextView richTextCheckedTextView6 = this.mThridLevelView;
                richTextCheckedTextView6.layout(measuredWidth, i7, richTextCheckedTextView6.getMeasuredWidth() + measuredWidth, this.mThridLevelView.getMeasuredHeight() + i7);
                measuredWidth2 = this.mThridLevelView.getMeasuredWidth();
                i5 = this.mIconMarginStart;
            }
            int i8 = measuredWidth + measuredWidth2 + i5;
            RichTextCheckedTextView richTextCheckedTextView7 = this.mBoldView;
            richTextCheckedTextView7.layout(i8, i7, richTextCheckedTextView7.getMeasuredWidth() + i8, this.mBoldView.getMeasuredHeight() + i7);
            int measuredWidth5 = i8 + this.mBoldView.getMeasuredWidth() + this.mIconMarginStart;
            RichTextCheckedTextView richTextCheckedTextView8 = this.mItalicView;
            richTextCheckedTextView8.layout(measuredWidth5, i7, richTextCheckedTextView8.getMeasuredWidth() + measuredWidth5, this.mItalicView.getMeasuredHeight() + i7);
            int measuredWidth6 = measuredWidth5 + this.mItalicView.getMeasuredWidth() + this.mIconMarginStart;
            RichTextCheckedTextView richTextCheckedTextView9 = this.mUnderlineView;
            richTextCheckedTextView9.layout(measuredWidth6, i7, richTextCheckedTextView9.getMeasuredWidth() + measuredWidth6, this.mUnderlineView.getMeasuredHeight() + i7);
            int measuredWidth7 = measuredWidth6 + this.mUnderlineView.getMeasuredWidth() + this.mIconMarginStart;
            if (!this.mIsLite && this.mIsNewTablet) {
                RichTextCheckedTextView richTextCheckedTextView10 = this.mStrikeThroughView;
                richTextCheckedTextView10.layout(measuredWidth7, i7, richTextCheckedTextView10.getMeasuredWidth() + measuredWidth7, this.mStrikeThroughView.getMeasuredHeight() + i7);
                measuredWidth7 += this.mStrikeThroughView.getMeasuredWidth() + this.mIconMarginStart;
            }
            RichTextCheckedTextView richTextCheckedTextView11 = this.mBulletView;
            richTextCheckedTextView11.layout(measuredWidth7, i7, richTextCheckedTextView11.getMeasuredWidth() + measuredWidth7, this.mBulletView.getMeasuredHeight() + i7);
            int measuredWidth8 = measuredWidth7 + this.mBulletView.getMeasuredWidth() + this.mIconMarginStart;
            if (!this.mIsLite && this.mIsNewTablet) {
                RichTextCheckedTextView richTextCheckedTextView12 = this.mListBulletView;
                richTextCheckedTextView12.layout(measuredWidth8, i7, richTextCheckedTextView12.getMeasuredWidth() + measuredWidth8, this.mListBulletView.getMeasuredHeight() + i7);
                int measuredWidth9 = measuredWidth8 + this.mListBulletView.getMeasuredWidth() + this.mIconMarginStart;
                RichTextCheckedTextView richTextCheckedTextView13 = this.mSplitlineView;
                richTextCheckedTextView13.layout(measuredWidth9, i7, richTextCheckedTextView13.getMeasuredWidth() + measuredWidth9, this.mSplitlineView.getMeasuredHeight() + i7);
                int measuredWidth10 = measuredWidth9 + this.mSplitlineView.getMeasuredWidth() + this.mIconMarginStart;
                RichTextCheckedTextView richTextCheckedTextView14 = this.mQuoteView;
                richTextCheckedTextView14.layout(measuredWidth10, i7, richTextCheckedTextView14.getMeasuredWidth() + measuredWidth10, this.mQuoteView.getMeasuredHeight() + i7);
                measuredWidth8 = measuredWidth10 + this.mQuoteView.getMeasuredWidth() + this.mIconMarginStart;
            }
            RichTextCheckedTextView richTextCheckedTextView15 = this.mCenterView;
            richTextCheckedTextView15.layout(measuredWidth8, i7, richTextCheckedTextView15.getMeasuredWidth() + measuredWidth8, this.mCenterView.getMeasuredHeight() + i7);
            int measuredWidth11 = measuredWidth8 + this.mCenterView.getMeasuredWidth() + this.mIconMarginStart;
            RichTextCheckedTextView richTextCheckedTextView16 = this.mRightView;
            richTextCheckedTextView16.layout(measuredWidth11, i7, richTextCheckedTextView16.getMeasuredWidth() + measuredWidth11, this.mRightView.getMeasuredHeight() + i7);
            if (this.mIsLite || !this.mIsNewTablet) {
                return;
            }
            int measuredWidth12 = measuredWidth11 + this.mRightView.getMeasuredWidth() + this.mIconMarginStart;
            RichTextCheckedTextView richTextCheckedTextView17 = this.mIndentRightView;
            richTextCheckedTextView17.layout(measuredWidth12, i7, richTextCheckedTextView17.getMeasuredWidth() + measuredWidth12, this.mIndentRightView.getMeasuredHeight() + i7);
            int measuredWidth13 = measuredWidth12 + this.mIndentRightView.getMeasuredWidth() + this.mIconMarginStart;
            RichTextCheckedTextView richTextCheckedTextView18 = this.mIndentLeftView;
            richTextCheckedTextView18.layout(measuredWidth13, i7, richTextCheckedTextView18.getMeasuredWidth() + measuredWidth13, this.mIndentLeftView.getMeasuredHeight() + i7);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.touchX = (int) ((this.mTotalWidth + this.mIconGroupMarginStart) - getWidth());
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
}
