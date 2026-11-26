package com.miui.notes.ui.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.ItemAnimHelper;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class ChooserFolderHolder extends RecyclerView.ViewHolder {
    public ItemAnimHelper.AnchorInfo mBottomAnchorInfo;
    private TextView mCount;
    private ImageView mFolderImg;
    private View mItemView;
    public ItemAnimHelper.AnchorInfo mMAnchorInfo;
    private TextView mName;
    private ViewGroup mNoteSampleGroup;
    protected View mSampleBottom;
    protected View mSampleMid;
    protected View mSampleTop;
    public ItemAnimHelper.AnchorInfo mTopAnchorInfo;

    public static ChooserFolderHolder newInstance(ViewGroup viewGroup) {
        return new ChooserFolderHolder(UIUtils.inflateView(viewGroup, R.layout.v12_choice_folder_item));
    }

    public ChooserFolderHolder(View view) {
        super(view);
        this.mItemView = view;
        Folme.useAt(view).touch().handleTouchOf(this.mItemView, new AnimConfig[0]);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.note_sample_group);
        this.mNoteSampleGroup = viewGroup;
        this.mSampleTop = viewGroup.findViewById(R.id.note_sample_top);
        this.mSampleBottom = this.mNoteSampleGroup.findViewById(R.id.note_sample_bottom);
        this.mSampleMid = this.mNoteSampleGroup.findViewById(R.id.note_sample_mid);
        this.mFolderImg = (ImageView) view.findViewById(R.id.folder_img);
        this.mName = (TextView) view.findViewById(R.id.name);
        this.mCount = (TextView) view.findViewById(R.id.count);
    }

    public void bind(int i, String str, String str2) {
        if (i == -1) {
            int i2 = Integer.parseInt(str2);
            this.mFolderImg.setVisibility(4);
            this.mNoteSampleGroup.setVisibility(0);
            this.mNoteSampleGroup.findViewById(R.id.note_sample_top).setAlpha(1.0f);
            this.mNoteSampleGroup.findViewById(R.id.note_sample_bottom).setAlpha(1.0f);
            if (i2 > 2) {
                this.mNoteSampleGroup.findViewById(R.id.note_sample_mid).setAlpha(1.0f);
            }
            this.mCount.setText(Utils.obtainLocalInteger(i2));
            this.mCount.setVisibility(0);
        } else {
            this.mFolderImg.setVisibility(0);
            this.mNoteSampleGroup.setVisibility(4);
            this.mFolderImg.setImageResource(i);
            this.mCount.setText(str2);
            this.mCount.setVisibility(4);
        }
        this.mName.setText(str);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mItemView.setOnClickListener(onClickListener);
    }

    public void updateAnchorInfo() {
        this.mTopAnchorInfo = getAnchorInfoFromView(this.mSampleTop);
        this.mBottomAnchorInfo = getAnchorInfoFromView(this.mSampleBottom);
    }

    public void expandFolderSamples(boolean z) {
        EaseManager.SpringInterpolator springInterpolator = new EaseManager.SpringInterpolator();
        if (z) {
            updateAnchorInfo();
            this.mSampleTop.animate().translationY(UIUtils.dip2px(this.itemView.getContext(), 6.0f)).setInterpolator(springInterpolator).start();
            ItemAnimHelper.AnchorInfo anchorInfo = this.mTopAnchorInfo;
            anchorInfo.setY(anchorInfo.getY() + UIUtils.dip2px(this.mSampleTop.getContext(), 6.0f));
            this.mSampleBottom.animate().translationY(UIUtils.dip2px(this.itemView.getContext(), -6.0f)).setInterpolator(springInterpolator).start();
            this.mSampleMid.animate().translationY(UIUtils.dip2px(this.itemView.getContext(), 2.0f)).setInterpolator(springInterpolator).start();
            return;
        }
        this.mSampleTop.animate().translationY(0.0f).setInterpolator(springInterpolator).start();
        ItemAnimHelper.AnchorInfo anchorInfo2 = this.mTopAnchorInfo;
        anchorInfo2.setY(anchorInfo2.getY() - UIUtils.dip2px(this.mSampleTop.getContext(), 6.0f));
        this.mSampleBottom.animate().translationY(0.0f).setInterpolator(springInterpolator).start();
        this.mSampleMid.animate().translationY(0.0f).setInterpolator(springInterpolator).start();
    }

    private ItemAnimHelper.AnchorInfo getAnchorInfoFromView(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return new ItemAnimHelper.AnchorInfo(iArr[0], iArr[1], view.getWidth(), view.getHeight(), view.getRotation());
    }
}
