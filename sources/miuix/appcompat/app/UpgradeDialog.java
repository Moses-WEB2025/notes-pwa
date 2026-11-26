package miuix.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogButtonPanel;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.widget.RoundCornerImageView;

/* JADX INFO: loaded from: classes5.dex */
public class UpgradeDialog extends AlertDialog {
    public static final float RATIO = 2.3f;
    private Drawable mBannerImageDrawable;
    private int mBannerImageResource;
    private RoundCornerImageView mBannerView;
    private int mBannerViewHorizontalPadding;
    private DialogButtonPanel mButtonPanel;
    private NestedScrollView mButtonScrollView;
    private CharSequence mComment;
    private TextView mCommentView;
    private int mCommentViewLandscapeVerticalPadding;
    private ViewGroup mContentPanel;
    private float mCornerRadius;
    private TextView mDialogTitle;
    private LayoutInflater mInflater;
    private Drawable mLabelImageDrawable;
    private int mLabelImageResource;
    private View mLabelImageView;
    private CharSequence mLabelSummary;
    private TextView mLabelSummaryView;
    private CharSequence mLabelTitle;
    private TextView mLabelTitleView;
    private LinearLayout mLayoutAfterChangeHierarchy;
    private CharSequence mMessage;
    private TextView mMessageView;
    private int mMinDialogHeightWithBanner;
    private DialogParentPanel2 mParentPanel;
    private View.OnLayoutChangeListener mParentPanelLayoutChangeListener;
    private LinearLayout mRootView;
    private LinearLayout mTopPanel;

    public UpgradeDialog(Context context) {
        super(context);
        init(context);
    }

    public UpgradeDialog(Context context, int i) {
        super(context, i);
        init(context);
    }

    public UpgradeDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mInflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
        this.mCornerRadius = resources.getDimension(R.dimen.miuix_appcompat_upgrade_dialog_banner_corner_radius);
        this.mCommentViewLandscapeVerticalPadding = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_upgrade_dialog_comment_landscape_vertical_padding);
        this.mBannerViewHorizontalPadding = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_upgrade_dialog_banner_horizontal_padding);
        this.mMinDialogHeightWithBanner = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_upgrade_dialog_min_height_with_banner);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        View viewInflate = this.mInflater.inflate(R.layout.miuix_appcompat_upgrade_dialog, (ViewGroup) null);
        beforeInstallDialogContent(viewInflate);
        setupMessageView();
        setupCommentView();
        setupLabelImageView();
        setupLabelTitle();
        setupLabelSummary();
        setupBannerView();
        setView(viewInflate);
        super.onCreate(bundle);
        afterInstallDialogContent();
    }

    private void beforeInstallDialogContent(View view) {
        this.mLabelImageView = view.findViewById(R.id.upgrade_dialog_label_image);
        this.mLabelTitleView = (TextView) view.findViewById(R.id.upgrade_dialog_label_title);
        this.mLabelSummaryView = (TextView) view.findViewById(R.id.upgrade_dialog_label_summary);
        this.mMessageView = (TextView) view.findViewById(R.id.upgrade_dialog_message);
        this.mCommentView = (TextView) this.mInflater.inflate(R.layout.miuix_appcompat_upgrade_dialog_comment, (ViewGroup) null);
        RoundCornerImageView roundCornerImageView = new RoundCornerImageView(getContext()) { // from class: miuix.appcompat.app.UpgradeDialog.1
            @Override // android.view.View
            protected void onMeasure(int i, int i2) {
                int size = View.MeasureSpec.getSize(i);
                setMeasuredDimension(size, (int) (size / 2.3f));
            }
        };
        this.mBannerView = roundCornerImageView;
        int i = this.mBannerViewHorizontalPadding;
        roundCornerImageView.setPadding(i, 0, i, 0);
        if (view instanceof LinearLayout) {
            this.mRootView = (LinearLayout) view;
        }
        setPreferLandscape(true);
    }

    private void afterInstallDialogContent() {
        Context context = getContext();
        this.mParentPanel = (DialogParentPanel2) findViewById(R.id.parentPanel);
        this.mButtonPanel = (DialogButtonPanel) findViewById(R.id.buttonPanel);
        this.mButtonScrollView = (NestedScrollView) this.mParentPanel.findViewById(R.id.button_scroll_view);
        addCommentView();
        addBannerView();
        setContentForceFullScroll(MiuixUIUtils.isInMultiWindowMode(context) && !MiuixUIUtils.isFreeformMode(context), MiuixUIUtils.getFontLevel(context) == 2, hasBanner() && this.mBannerView.getVisibility() == 0);
    }

    private void setContentForceFullScroll(boolean z, boolean z2, boolean z3) {
        boolean z4 = z && (z2 || z3);
        if (z && z2 && z3) {
            if (this.mDialogTitle == null) {
                this.mDialogTitle = (TextView) this.mParentPanel.findViewById(R.id.alertTitle);
            }
            if (this.mTopPanel == null) {
                this.mTopPanel = (LinearLayout) this.mParentPanel.findViewById(R.id.topPanel);
            }
            if (this.mContentPanel == null) {
                this.mContentPanel = (ViewGroup) this.mParentPanel.findViewById(R.id.contentPanel);
            }
            this.mParentPanel.removeView(this.mTopPanel);
            this.mContentPanel.addView(this.mTopPanel, 0);
        }
        this.mAlert.setContentForceFullScroll(z4);
    }

    private void addCommentView() {
        if (this.mCommentView == null || TextUtils.isEmpty(this.mComment)) {
            return;
        }
        if (this.mButtonScrollView != null) {
            changeHierarchyIfNeed();
            TextView textView = this.mCommentView;
            textView.setPadding(textView.getPaddingLeft(), this.mCommentViewLandscapeVerticalPadding, this.mCommentView.getCompoundPaddingRight(), this.mCommentViewLandscapeVerticalPadding);
            this.mLayoutAfterChangeHierarchy.addView(this.mCommentView, 0);
            return;
        }
        LinearLayout linearLayout = this.mRootView;
        if (linearLayout != null) {
            linearLayout.addView(this.mCommentView);
        }
    }

    private void addBannerView() {
        if (hasBanner()) {
            if (this.mButtonScrollView != null) {
                changeHierarchyIfNeed();
                this.mBannerView.setClipToPadding(true);
                this.mBannerView.setCornerRadiusEnabled(true);
                this.mLayoutAfterChangeHierarchy.addView(this.mBannerView, 0);
                return;
            }
            DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
            if (dialogParentPanel2 != null) {
                int paddingTop = dialogParentPanel2.getPaddingTop();
                ViewUtils.resetPaddingTop(this.mParentPanel, 0);
                TextView textView = (TextView) this.mParentPanel.findViewById(R.id.alertTitle);
                if (textView != null) {
                    ViewUtils.resetPaddingTop(textView, paddingTop);
                }
                this.mParentPanel.addView(this.mBannerView, 0);
                this.mBannerView.setClipToPadding(false);
                this.mBannerView.setCornerRadiusEnabled(false);
            }
        }
    }

    private void changeHierarchyIfNeed() {
        if (this.mLayoutAfterChangeHierarchy == null) {
            this.mButtonScrollView.removeAllViews();
            LinearLayout linearLayout = new LinearLayout(getContext());
            this.mLayoutAfterChangeHierarchy = linearLayout;
            linearLayout.setOrientation(1);
            this.mLayoutAfterChangeHierarchy.addView(this.mButtonPanel);
            this.mButtonScrollView.addView(this.mLayoutAfterChangeHierarchy, -1, -2);
        }
    }

    private void setupMessageView() {
        if (this.mMessageView == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.mMessage)) {
            this.mMessageView.setText(this.mMessage);
            this.mMessageView.setVisibility(0);
        } else {
            this.mMessageView.setVisibility(8);
        }
    }

    private void setupCommentView() {
        if (this.mCommentView == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.mComment)) {
            this.mCommentView.setText(this.mComment);
            this.mCommentView.setVisibility(0);
        } else {
            this.mCommentView.setVisibility(8);
        }
    }

    private void setupLabelImageView() {
        View view = this.mLabelImageView;
        if (view == null) {
            return;
        }
        Drawable drawable = this.mLabelImageDrawable;
        if (drawable != null) {
            view.setBackground(drawable);
            this.mLabelImageView.setVisibility(0);
            return;
        }
        int i = this.mLabelImageResource;
        if (i != 0) {
            view.setBackgroundResource(i);
            this.mLabelImageView.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
    }

    private void setupLabelTitle() {
        if (this.mLabelTitleView == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.mLabelTitle)) {
            this.mLabelTitleView.setText(this.mLabelTitle);
            this.mLabelTitleView.setVisibility(0);
        } else {
            this.mLabelTitleView.setVisibility(8);
        }
    }

    private void setupLabelSummary() {
        if (this.mLabelSummaryView == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.mLabelSummary)) {
            this.mLabelSummaryView.setText(this.mLabelSummary);
            this.mLabelSummaryView.setVisibility(0);
        } else {
            this.mLabelSummaryView.setVisibility(8);
        }
    }

    private void setupBannerView() {
        if (hasBanner()) {
            this.mBannerView.setCornerRadius(this.mCornerRadius);
            Drawable drawable = this.mBannerImageDrawable;
            if (drawable != null) {
                this.mBannerView.setImageDrawable(drawable);
                return;
            }
            int i = this.mBannerImageResource;
            if (i != 0) {
                this.mBannerView.setImageDrawable(i);
            }
        }
    }

    private View.OnLayoutChangeListener getOnDialogParentPanelLayoutChangeListener() {
        return new View.OnLayoutChangeListener() { // from class: miuix.appcompat.app.UpgradeDialog.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                UpgradeDialog.this.mBannerView.setVisibility(i4 - i2 < UpgradeDialog.this.mMinDialogHeightWithBanner ? 8 : 0);
            }
        };
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!hasBanner() || this.mBannerView == null) {
            return;
        }
        if (this.mParentPanelLayoutChangeListener == null) {
            this.mParentPanelLayoutChangeListener = getOnDialogParentPanelLayoutChangeListener();
        }
        this.mParentPanel.addOnLayoutChangeListener(this.mParentPanelLayoutChangeListener);
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mParentPanel.removeOnLayoutChangeListener(this.mParentPanelLayoutChangeListener);
    }

    public boolean hasBanner() {
        return (this.mBannerImageDrawable == null && this.mBannerImageResource == 0) ? false : true;
    }

    public void setLabel(Drawable drawable, CharSequence charSequence, CharSequence charSequence2) {
        this.mLabelImageDrawable = drawable;
        this.mLabelImageResource = 0;
        this.mLabelTitle = charSequence;
        this.mLabelSummary = charSequence2;
    }

    public void setLabel(int i, CharSequence charSequence, CharSequence charSequence2) {
        this.mLabelImageDrawable = null;
        this.mLabelImageResource = i;
        this.mLabelTitle = charSequence;
        this.mLabelSummary = charSequence2;
    }

    public void setBannerImageDrawable(Drawable drawable) {
        this.mBannerImageDrawable = drawable;
        this.mBannerImageResource = 0;
    }

    public void setBannerImageResource(int i) {
        this.mBannerImageDrawable = null;
        this.mBannerImageResource = i;
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
    }

    public void setComment(CharSequence charSequence) {
        this.mComment = charSequence;
    }

    public LinearLayout getRootView() {
        return this.mRootView;
    }

    public View getLabelImageView() {
        return this.mLabelImageView;
    }

    public TextView getLabelTitleView() {
        return this.mLabelTitleView;
    }

    public TextView getLabelSummaryView() {
        return this.mLabelSummaryView;
    }

    @Override // miuix.appcompat.app.AlertDialog
    public TextView getMessageView() {
        return this.mMessageView;
    }

    public TextView getCommentView() {
        return this.mCommentView;
    }

    public RoundCornerImageView getBannerView() {
        return this.mBannerView;
    }
}
