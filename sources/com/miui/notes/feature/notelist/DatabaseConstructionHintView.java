package com.miui.notes.feature.notelist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.utils.RomUtils;
import miuix.popupwidget.widget.GuidePopupWindow;

/* JADX INFO: loaded from: classes4.dex */
public class DatabaseConstructionHintView extends RelativeLayout {
    private static final int MULTI_LINE = 2;
    private static final int NEXT_LINE = 3;
    private static final int SINGLE_LINE = 1;
    private Runnable closePopupRunnable;
    private final Handler handler;
    private int imageViewLeft;
    private int imageViewRight;
    boolean isRtl;
    private float lastLineLeft;
    private float lastLineRight;
    private int lastLineTop;
    private ImageView mHintImageView;
    private TextView mHintTextView;
    private int mPopupWidth;
    private GuidePopupWindow mPopupWindow;
    private int textViewLeft;
    private int textViewRight;
    private int type;

    public DatabaseConstructionHintView(Context context) {
        super(context);
        this.mPopupWidth = 0;
        this.isRtl = RomUtils.isRTL();
        this.closePopupRunnable = new Runnable() { // from class: com.miui.notes.feature.notelist.DatabaseConstructionHintView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DatabaseConstructionHintView.this.mPopupWindow == null || !DatabaseConstructionHintView.this.mPopupWindow.isShowing()) {
                    return;
                }
                DatabaseConstructionHintView.this.mPopupWindow.dismiss();
            }
        };
        this.handler = new Handler(NoteApp.getInstance().getMainLooper());
    }

    public DatabaseConstructionHintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPopupWidth = 0;
        this.isRtl = RomUtils.isRTL();
        this.closePopupRunnable = new Runnable() { // from class: com.miui.notes.feature.notelist.DatabaseConstructionHintView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DatabaseConstructionHintView.this.mPopupWindow == null || !DatabaseConstructionHintView.this.mPopupWindow.isShowing()) {
                    return;
                }
                DatabaseConstructionHintView.this.mPopupWindow.dismiss();
            }
        };
        this.handler = new Handler(NoteApp.getInstance().getMainLooper());
    }

    public DatabaseConstructionHintView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPopupWidth = 0;
        this.isRtl = RomUtils.isRTL();
        this.closePopupRunnable = new Runnable() { // from class: com.miui.notes.feature.notelist.DatabaseConstructionHintView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DatabaseConstructionHintView.this.mPopupWindow == null || !DatabaseConstructionHintView.this.mPopupWindow.isShowing()) {
                    return;
                }
                DatabaseConstructionHintView.this.mPopupWindow.dismiss();
            }
        };
        this.handler = new Handler(NoteApp.getInstance().getMainLooper());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mHintTextView = (TextView) findViewById(R.id.hint_text);
        ImageView imageView = (ImageView) findViewById(R.id.hint_image);
        this.mHintImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.notelist.DatabaseConstructionHintView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DatabaseConstructionHintView databaseConstructionHintView = DatabaseConstructionHintView.this;
                databaseConstructionHintView.showPopupWindow(databaseConstructionHintView.mHintImageView);
            }
        });
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = (View.MeasureSpec.getSize(i) - getPaddingRight()) - getPaddingLeft();
        initTextParams(this.mHintTextView.getText(), this.mHintTextView.getMeasuredWidth(), this.mHintTextView.getPaint());
        if (this.mHintTextView.getMeasuredWidth() + this.mHintImageView.getMeasuredWidth() <= size) {
            setMeasuredDimension(this.mHintTextView.getMeasuredWidth() + this.mHintImageView.getMeasuredWidth(), Math.max(this.mHintTextView.getMeasuredHeight(), this.mHintImageView.getMeasuredHeight()));
            this.type = 1;
        } else if (this.lastLineRight + this.mHintImageView.getMeasuredWidth() > size) {
            setMeasuredDimension(this.mHintTextView.getMeasuredWidth(), this.mHintTextView.getMeasuredHeight() + this.mHintTextView.getMeasuredHeight());
            this.type = 3;
        } else {
            setMeasuredDimension(this.mHintTextView.getMeasuredWidth(), Math.max(this.mHintTextView.getMeasuredHeight(), this.lastLineTop + this.mHintImageView.getMeasuredHeight()));
            this.type = 2;
        }
    }

    private void initTextParams(CharSequence charSequence, int i, TextPaint textPaint) {
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, i, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        int lineCount = staticLayout.getLineCount() - 1;
        this.lastLineTop = staticLayout.getLineTop(lineCount);
        this.lastLineRight = staticLayout.getLineRight(lineCount);
        this.lastLineLeft = staticLayout.getLineLeft(lineCount);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.type;
        if (i5 != 1 && i5 != 2) {
            if (i5 == 3) {
                this.textViewLeft = 0;
                this.textViewRight = this.mHintTextView.getMeasuredWidth();
                int measuredWidth = (this.mHintTextView.getMeasuredWidth() / 2) - (this.mHintImageView.getMeasuredWidth() / 2);
                this.imageViewLeft = measuredWidth;
                this.imageViewRight = measuredWidth + this.mHintImageView.getMeasuredWidth();
                TextView textView = this.mHintTextView;
                textView.layout(this.textViewLeft, 0, this.textViewRight, textView.getMeasuredHeight());
                this.mHintImageView.layout(this.imageViewLeft, this.mHintTextView.getMeasuredHeight(), this.imageViewRight, this.mHintTextView.getMeasuredHeight() + this.mHintImageView.getMeasuredHeight());
                return;
            }
            return;
        }
        if (i5 == 1) {
            this.textViewLeft = this.isRtl ? this.mHintImageView.getMeasuredWidth() : 0;
            this.imageViewLeft = this.isRtl ? 0 : (int) this.lastLineRight;
        } else {
            this.textViewLeft = 0;
            this.imageViewLeft = (int) (this.isRtl ? this.lastLineLeft - this.mHintImageView.getMeasuredWidth() : this.lastLineRight);
        }
        this.textViewRight = this.textViewLeft + this.mHintTextView.getMeasuredWidth();
        this.imageViewRight = this.imageViewLeft + this.mHintImageView.getMeasuredWidth();
        TextView textView2 = this.mHintTextView;
        textView2.layout(this.textViewLeft, 0, this.textViewRight, textView2.getMeasuredHeight());
        int measuredHeight = this.lastLineTop;
        int bottom = (this.mHintTextView.getBottom() - this.mHintTextView.getPaddingBottom()) - this.lastLineTop;
        if (this.mHintImageView.getMeasuredHeight() < bottom) {
            measuredHeight = this.lastLineTop + ((bottom - this.mHintImageView.getMeasuredHeight()) / 2);
        }
        ImageView imageView = this.mHintImageView;
        imageView.layout(this.imageViewLeft, measuredHeight, this.imageViewRight, imageView.getMeasuredHeight() + measuredHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopupWindow(final View view) {
        if (this.handler == null || view == null || view.getContext() == null) {
            return;
        }
        this.handler.removeCallbacks(this.closePopupRunnable);
        GuidePopupWindow guidePopupWindow = new GuidePopupWindow(this.mHintImageView.getContext());
        this.mPopupWindow = guidePopupWindow;
        guidePopupWindow.setGuideText(R.string.note_ai_search_database_building_tip);
        if (view instanceof ImageView) {
            Utils.hideSoftInput(view);
            this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.notelist.DatabaseConstructionHintView.3
                @Override // java.lang.Runnable
                public void run() {
                    if (DatabaseConstructionHintView.this.mPopupWindow == null) {
                        return;
                    }
                    DatabaseConstructionHintView.this.mPopupWindow.show(view, 0, 0);
                }
            }, 50L);
            this.handler.postDelayed(this.closePopupRunnable, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        GuidePopupWindow guidePopupWindow = this.mPopupWindow;
        if (guidePopupWindow != null) {
            guidePopupWindow.dismiss();
        }
    }
}
