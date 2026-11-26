package com.miui.handwriting.common.view.thumbnail;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailHolder> {
    private ThumbnailRecyclerManager.ThumbnailListener mListener;
    private final String TAG = "ThumbnailAdapter";
    private List<Bitmap> mImageList = new ArrayList();
    private int mSelectPosition = -1;

    public ThumbnailAdapter(ThumbnailRecyclerManager.ThumbnailListener thumbnailListener) {
        this.mListener = thumbnailListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ThumbnailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ThumbnailHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_thumbnail, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ThumbnailHolder thumbnailHolder, int i) {
        if (i == this.mSelectPosition) {
            thumbnailHolder.imageBackground.setVisibility(0);
        } else {
            thumbnailHolder.imageBackground.setVisibility(8);
        }
        if (i < this.mImageList.size()) {
            Bitmap bitmap = this.mImageList.get(i);
            if (bitmap == null) {
                thumbnailHolder.imageContent.setImageResource(R.drawable.image_thumbnail_default);
            } else {
                thumbnailHolder.imageContent.setImageBitmap(bitmap);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mImageList.size();
    }

    public List<Bitmap> getImageList() {
        return this.mImageList;
    }

    public void initAdapter(int i, int i2) {
        this.mSelectPosition = i;
        this.mImageList.clear();
        for (int i3 = 0; i3 < i2; i3++) {
            this.mImageList.add(null);
        }
        notifyDataSetChanged();
    }

    public void setBitmap(Bitmap bitmap, int i) {
        this.mImageList.set(i, bitmap);
        notifyItemChanged(i);
    }

    public void setBitmapList(List<Integer> list, List<Bitmap> list2) {
        if (list.size() != list2.size()) {
            Log.e(this.TAG, "error list size position size:" + list.size() + "-----bitmap size:" + list2.size());
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            setBitmap(list2.get(i), list.get(i).intValue());
        }
    }

    public void addNewPage(int i) {
        if (this.mImageList.size() != i - 1) {
            Log.e(this.TAG, "add page error");
        } else {
            this.mImageList.add(null);
            notifyItemInserted(this.mImageList.size() - 1);
        }
    }

    public void insertPage(int i, int i2) {
        if (this.mImageList.size() != i2 - 1) {
            Log.e(this.TAG, "add page error");
        } else {
            this.mImageList.add(i, null);
            notifyItemInserted(i);
        }
    }

    public void removePages(List<Integer> list) {
        for (Integer num : list) {
            if (num.intValue() < 0 || num.intValue() >= this.mImageList.size()) {
                Log.e(this.TAG, "remove list index error");
                return;
            }
        }
        list.sort(Comparator.reverseOrder());
        for (Integer num2 : list) {
            this.mImageList.remove(num2.intValue());
            notifyItemRemoved(num2.intValue());
        }
    }

    public void pageChange(int i) {
        Log.d(this.TAG, "page change sel:" + this.mSelectPosition + "----new position:" + i);
        if (this.mSelectPosition == i || i < 0 || i >= this.mImageList.size()) {
            Log.e(this.TAG, "error position:" + i);
            return;
        }
        int i2 = this.mSelectPosition;
        this.mSelectPosition = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.mSelectPosition);
    }

    class ThumbnailHolder extends RecyclerView.ViewHolder {
        ImageView imageBackground;
        ImageView imageContent;

        public ThumbnailHolder(View view) {
            super(view);
            this.imageBackground = (ImageView) view.findViewById(R.id.image_bg);
            this.imageContent = (ImageView) view.findViewById(R.id.image_content);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.thumbnail.ThumbnailAdapter$ThumbnailHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m800x2b8be87a(view2);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$new$0$com-miui-handwriting-common-view-thumbnail-ThumbnailAdapter$ThumbnailHolder, reason: not valid java name */
        /* synthetic */ void m800x2b8be87a(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition == ThumbnailAdapter.this.mSelectPosition) {
                return;
            }
            ThumbnailAdapter.this.pageChange(bindingAdapterPosition);
            Log.d(ThumbnailAdapter.this.TAG, "item click:" + ThumbnailAdapter.this.mSelectPosition);
            if (ThumbnailAdapter.this.mListener != null) {
                ThumbnailAdapter.this.mListener.onItemClick(ThumbnailAdapter.this.mSelectPosition);
            }
        }
    }
}
