package com.miui.pc.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import com.miui.pc.frame.ExpandableInterface;
import com.miui.pc.frame.PcBaseNoteRecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class ExpandableRecyclerView extends LinearLayout {
    private Drawable mArrowIcon;
    private Context mContext;
    private ImageView mExpandArrowView;
    private View mHeaderView;
    private int mIconResId;
    private ImageView mIconView;
    private boolean mIsExpand;
    private MaxHeightRecyclerView mRecyclerView;
    private View mRootView;
    private String mTitle;
    private TextView mTitleView;

    public ExpandableRecyclerView(Context context) {
        super(context);
        this.mIsExpand = true;
        this.mContext = context;
        init();
    }

    public ExpandableRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsExpand = true;
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(this.mContext).inflate(R.layout.expandable_recycler_view, (ViewGroup) this, true);
        this.mRootView = findViewById(R.id.expandable_root_view);
        this.mHeaderView = findViewById(R.id.expand_header_view);
        this.mTitleView = (TextView) findViewById(R.id.header_title);
        this.mIconView = (ImageView) findViewById(R.id.header_icon);
        this.mExpandArrowView = (ImageView) findViewById(R.id.expand_arrow);
        this.mRecyclerView = (MaxHeightRecyclerView) findViewById(R.id.expand_list_view);
        if (!TextUtils.isEmpty(this.mTitle)) {
            this.mTitleView.setText(this.mTitle);
        }
        int i = this.mIconResId;
        if (i > 0) {
            this.mIconView.setImageResource(i);
        }
        Drawable drawable = this.mContext.getDrawable(R.drawable.pc_ic_expand_arrow);
        this.mArrowIcon = drawable;
        this.mExpandArrowView.setImageDrawable(drawable);
        this.mHeaderView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.view.ExpandableRecyclerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ExpandableRecyclerView expandableRecyclerView = ExpandableRecyclerView.this;
                expandableRecyclerView.setExpand(!expandableRecyclerView.mIsExpand);
            }
        });
    }

    public void setHeaderTitle(String str) {
        this.mTitle = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mTitleView.setText(this.mTitle);
    }

    public void setIcon(int i) {
        this.mIconResId = i;
        if (i > 0) {
            this.mIconView.setImageResource(i);
        }
    }

    public void setExpand(boolean z) {
        if (!(this.mRecyclerView.getAdapter() instanceof ExpandableInterface)) {
            this.mRecyclerView.setVisibility(z ? 0 : 8);
            return;
        }
        if (z) {
            this.mExpandArrowView.animate().cancel();
            this.mExpandArrowView.animate().rotation(0.0f).start();
            expand();
        } else {
            this.mExpandArrowView.animate().cancel();
            this.mExpandArrowView.animate().rotation(180.0f).start();
            packup();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void expand() {
        if (this.mIsExpand) {
            return;
        }
        this.mIsExpand = true;
        RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
        int iMeasureItemHeight = ((ExpandableInterface) adapter).measureItemHeight(this.mRecyclerView) * adapter.getItemCount();
        if (iMeasureItemHeight > this.mRecyclerView.getMaxHeight()) {
            iMeasureItemHeight = this.mRecyclerView.getMaxHeight();
        }
        changeViewHeightAnimatorStart(this.mRecyclerView, 0, iMeasureItemHeight);
    }

    public void packup() {
        if (this.mIsExpand) {
            this.mIsExpand = false;
            changeViewHeightAnimatorStart(this.mRecyclerView, this.mRecyclerView.getHeight(), 0);
        }
    }

    public static void changeViewHeightAnimatorStart(final View view, int i, int i2) {
        if (view == null || i < 0 || i2 < 0) {
            return;
        }
        ValueAnimator duration = ValueAnimator.ofInt(i, i2).setDuration(300L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.view.ExpandableRecyclerView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((View) view.getParent()).getLayoutParams().height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                view.requestLayout();
            }
        });
        duration.start();
    }

    public PcBaseNoteRecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public boolean isExpand() {
        return this.mIsExpand;
    }

    public void refreshRecyclerViewHeight() {
        ((View) this.mRecyclerView.getParent()).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.mRecyclerView.requestLayout();
    }
}
