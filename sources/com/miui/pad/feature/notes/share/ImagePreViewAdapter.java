package com.miui.pad.feature.notes.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.notes.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes4.dex */
public class ImagePreViewAdapter extends RecyclerView.Adapter<ItemHolder> {
    private static final String MIN_DIMENSION_RATIO = "112:80";
    private static final float MIN_WIDTH_HEIGHT_SCALE = 1.4f;
    private static final String TAG = "ImageAdapter";
    private ImagePreviewClickCallback callback;
    private Context context;
    private Point imageSize;
    private final CheckedCallBack mCheckedCallBack;
    private List<Boolean> checkStatusList = new ArrayList();
    private List<Bitmap> mBitmapList = new ArrayList();
    private long updateTime = System.currentTimeMillis();

    public interface ImagePreviewClickCallback {
        void onPreviewClick(int i);
    }

    public ImagePreViewAdapter(Context context, CheckedCallBack checkedCallBack) {
        this.context = context;
        this.mCheckedCallBack = checkedCallBack;
    }

    public List<Integer> createCheckList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.checkStatusList.size(); i++) {
            if (this.checkStatusList.get(i).booleanValue()) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    public void setImagePreviewClickCallback(ImagePreviewClickCallback imagePreviewClickCallback) {
        this.callback = imagePreviewClickCallback;
    }

    public void setImageList(List<Bitmap> list) {
        this.mBitmapList = list;
        for (int i = 0; i < list.size(); i++) {
            this.checkStatusList.add(true);
        }
        if (list.size() > 0) {
            this.imageSize = BitmapUtil.getImageSize(list.get(0));
        }
        notifyDataSetChanged();
    }

    public void selectIndex(List<Integer> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < this.checkStatusList.size(); i++) {
            this.checkStatusList.set(i, false);
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (i == list.get(i2).intValue()) {
                    this.checkStatusList.set(i, true);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemHolder itemHolder = new ItemHolder(viewGroup, getItemCount() == 1);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) itemHolder.mIcon.getLayoutParams();
        layoutParams.dimensionRatio = this.imageSize.x + ":" + this.imageSize.y;
        float f = (this.imageSize.x * 1.0f) / this.imageSize.y;
        if (f > 0.0f) {
            Log.d(TAG, "value:" + f);
            if (f > MIN_WIDTH_HEIGHT_SCALE) {
                layoutParams.dimensionRatio = MIN_DIMENSION_RATIO;
            }
        }
        return itemHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ItemHolder itemHolder, int i) {
        Log.d(TAG, "this.imageList" + this.mBitmapList.size());
        Log.d(TAG, "holder.mIcon:" + itemHolder.mIcon);
        itemHolder.mIcon.setImageBitmap(this.mBitmapList.get(i));
        if (getItemCount() > 1) {
            StringBuilder sb = new StringBuilder(String.format("%d/%d", Integer.valueOf(i + 1), Integer.valueOf(getItemCount())));
            TextView textView = itemHolder.mTvIndex;
            if (UIUtils.isRTL()) {
                sb = sb.reverse();
            }
            textView.setText(sb.toString());
            itemHolder.mCb.setChecked(this.checkStatusList.get(i).booleanValue());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mBitmapList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public CheckBox mCb;
        public ImageView mIcon;
        public TextView mTvIndex;

        public ItemHolder(ViewGroup viewGroup, boolean z) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(z ? R.layout.item_share_preview_single : R.layout.item_share_preview, viewGroup, false));
            this.mIcon = (ImageView) this.itemView.findViewById(R.id.iv);
            if (z) {
                return;
            }
            this.mTvIndex = (TextView) this.itemView.findViewById(R.id.tv_index);
            CheckBox checkBox = (CheckBox) this.itemView.findViewById(R.id.cb);
            this.mCb = checkBox;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.pad.feature.notes.share.ImagePreViewAdapter$ItemHolder$$ExternalSyntheticLambda1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    this.f$0.lambda$new$0(compoundButton, z2);
                }
            });
            this.mIcon.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.share.ImagePreViewAdapter$ItemHolder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$1(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(CompoundButton compoundButton, boolean z) {
            int adapterPosition = getAdapterPosition();
            Log.d(ImagePreViewAdapter.TAG, "adapterPosition" + z);
            ImagePreViewAdapter.this.checkStatusList.set(adapterPosition, Boolean.valueOf(z));
            ImagePreViewAdapter.this.mCheckedCallBack.onImageNumberChecked(ImagePreViewAdapter.this.isOneItemChecked());
            if (ImagePreViewAdapter.this.callback != null) {
                ImagePreViewAdapter.this.callback.onPreviewClick((int) ImagePreViewAdapter.this.checkStatusList.stream().filter(new Predicate() { // from class: com.miui.pad.feature.notes.share.ImagePreViewAdapter$ItemHolder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Boolean) obj).booleanValue();
                    }
                }).count());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            if (ImagePreViewAdapter.this.getItemCount() > 1) {
                CheckBox checkBox = this.mCb;
                checkBox.setChecked(!checkBox.isChecked());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOneItemChecked() {
        Iterator<Boolean> it = this.checkStatusList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().booleanValue() && (i = i + 1) > 1) {
                return false;
            }
        }
        return true;
    }
}
