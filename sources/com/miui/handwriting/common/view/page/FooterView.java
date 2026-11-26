package com.miui.handwriting.common.view.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.libhandwritingpage.listener.PageFooterView;

/* JADX INFO: loaded from: classes3.dex */
public class FooterView implements PageFooterView {
    private static final String TAG = "FooterView";
    private final Context context;
    private final View footerView;
    private boolean mBlueToGray = true;
    private boolean mGrayToBlue = true;
    private boolean mIsMaxOffset;
    private int mLastDragHeight;
    boolean mNeedToShowToast;
    private IMiuiHandWritingModel mPageInkFunc;
    private CustomProgressView mProgressView;
    private ImageView mProgressViewArrow;
    private TextView mTextView;
    private final int maxHeight;
    private final int minHeight;

    public FooterView(Context context, IMiuiHandWritingModel iMiuiHandWritingModel) {
        this.context = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.layout_footer_view, (ViewGroup) null, false);
        this.footerView = viewInflate;
        this.minHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_footer_view_height_min);
        this.maxHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_footer_view_height_max);
        this.mProgressView = (CustomProgressView) viewInflate.findViewById(R.id.foot_progress_view);
        this.mProgressViewArrow = (ImageView) viewInflate.findViewById(R.id.foot_progress_arrow);
        this.mTextView = (TextView) viewInflate.findViewById(R.id.foot_text);
        this.mPageInkFunc = iMiuiHandWritingModel;
    }

    @Override // com.miui.libhandwritingpage.listener.PageFooterView
    public View getFooterView() {
        return this.footerView;
    }

    public int getFooterMinHeight() {
        return this.minHeight;
    }

    public boolean onDrag(int i) {
        int i2;
        ((FrameLayout.LayoutParams) this.footerView.getLayoutParams()).bottomMargin = Math.min((-this.context.getResources().getDimensionPixelOffset(R.dimen.creation_footer_view_height_min)) + i, 0);
        this.footerView.requestLayout();
        if (i > 0 && i < this.minHeight) {
            this.mProgressView.reset();
            if (this.mBlueToGray) {
                this.mProgressViewArrow.setImageResource(R.drawable.creation_action_pull_gary);
                this.mTextView.setText(R.string.creation_foot_view_text);
                this.mBlueToGray = false;
                this.mGrayToBlue = true;
            }
            this.mNeedToShowToast = true;
        } else {
            int i3 = this.minHeight;
            if (i >= i3 && i < (i2 = this.maxHeight)) {
                int i4 = ((i - i3) * 100) / (i2 - i3);
                if (this.mGrayToBlue) {
                    this.mProgressViewArrow.setImageResource(R.drawable.creation_action_pull_blue);
                    this.mTextView.setText(R.string.creation_foot_view_text);
                    this.mGrayToBlue = false;
                    this.mBlueToGray = true;
                }
                this.mNeedToShowToast = true;
                this.mProgressView.update(i4);
            } else {
                if (i >= this.maxHeight) {
                    IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkFunc;
                    if (iMiuiHandWritingModel != null && iMiuiHandWritingModel.getPageCount() >= 500 && this.mNeedToShowToast) {
                        Toast.makeText(this.context, R.string.creation_edit_limit_new_page, 0).show();
                        this.mNeedToShowToast = false;
                    }
                    this.mProgressView.update(100);
                    this.mTextView.setText(R.string.creation_foot_view_text_release);
                    this.mGrayToBlue = true;
                    this.mBlueToGray = true;
                    return true;
                }
                if (i == 0) {
                    this.mProgressView.reset();
                }
            }
        }
        return false;
    }

    public void reset() {
        this.mProgressView.reset();
    }

    @Override // com.miui.libhandwritingpage.listener.PageFooterView
    public void onConfigurationChanged() {
        this.mTextView.setTextColor(ContextCompat.getColor(this.context, R.color.creation_footer_view_text));
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0062  */
    /* JADX WARN: Code duplicated, block: B:26:0x0066  */
    /* JADX WARN: Code duplicated, block: B:43:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:45:0x00b1  */
    @Override // com.miui.libhandwritingpage.listener.PageFooterView
    public boolean UpdateFootView(float f, float f2, int i, int i2) {
        boolean zOnDrag;
        View view = this.footerView;
        if (view == null || view.getWidth() == 0 || this.footerView.getHeight() == 0 || f2 == 0.0f) {
            return false;
        }
        int footerMinHeight = (this.maxHeight - getFooterMinHeight()) / 5;
        if (this.footerView.getVisibility() != 0) {
            this.footerView.setVisibility(0);
        }
        int height = this.footerView.getHeight();
        if (f < 0.0f && f2 < i2) {
            int iMax = i2 + ((int) Math.max(f, -height));
            this.footerView.layout(0, iMax, i, height + iMax);
            int iCeil = (int) Math.ceil(-f);
            int i3 = this.maxHeight;
            if (iCeil <= i3) {
                if (i3 - iCeil < (this.mIsMaxOffset ? footerMinHeight : 5)) {
                    if (!this.mIsMaxOffset) {
                        this.mIsMaxOffset = true;
                    }
                    iCeil = i3;
                }
            } else {
                if (!this.mIsMaxOffset) {
                    this.mIsMaxOffset = true;
                }
                iCeil = i3;
            }
            if (iCeil < i3) {
                this.mIsMaxOffset = false;
            }
            boolean zOnDrag2 = onDrag(iCeil);
            this.mLastDragHeight = iCeil;
            return zOnDrag2;
        }
        if (f < 0.0f && i2 - ((int) f) > f2 && this.mPageInkFunc.isCanWrite()) {
            float f3 = i2;
            float f4 = ((f3 - f) - f2) - height;
            float f5 = f2 + f;
            int iMax2 = (int) (Math.max(f4, 0.0f) + f5);
            this.footerView.layout(0, iMax2, i, height + iMax2);
            int iCeil2 = (int) Math.ceil(f3 - f5);
            int i4 = this.maxHeight;
            if (iCeil2 <= i4) {
                if (i4 - iCeil2 < (this.mIsMaxOffset ? footerMinHeight : 5)) {
                    if (!this.mIsMaxOffset) {
                        this.mIsMaxOffset = true;
                    }
                    iCeil2 = i4;
                }
            } else {
                if (!this.mIsMaxOffset) {
                    this.mIsMaxOffset = true;
                }
                iCeil2 = i4;
            }
            if (iCeil2 < i4) {
                this.mIsMaxOffset = false;
            }
            boolean zOnDrag3 = onDrag(iCeil2);
            this.mLastDragHeight = iCeil2;
            return zOnDrag3;
        }
        if (this.mLastDragHeight != 0) {
            zOnDrag = onDrag(0);
            this.mLastDragHeight = 0;
        } else {
            zOnDrag = false;
        }
        this.mIsMaxOffset = false;
        this.footerView.layout(0, i2, i, height + i2);
        return zOnDrag;
    }
}
