package com.miui.handwriting.common.ai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.ai.data.CreationImage;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewAdapter extends RecyclerView.Adapter<PreviewItem> {
    private Activity activity;
    private LayoutInflater mLayoutInflater;
    private List<CreationImage> mResourceItems;
    private String selectImage;
    private SelectItemListener selectItemListener;
    private int selectPosition = -1;
    private boolean isFinish = false;

    public interface SelectItemListener {
        void selectItem(String str);
    }

    public PreviewAdapter(Activity activity) {
        this.activity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }

    public void setSelectItemListener(SelectItemListener selectItemListener) {
        this.selectItemListener = selectItemListener;
    }

    public void setData(List<CreationImage> list) {
        this.mResourceItems = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PreviewItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewItem(this.mLayoutInflater.inflate(R.layout.creation_preview_item, viewGroup, false), this.activity);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final PreviewItem previewItem, int i) {
        if (this.selectPosition == -1 && this.isFinish) {
            this.selectPosition = 0;
            String imageUrl = this.mResourceItems.get(0).getImageUrl();
            this.selectImage = imageUrl;
            this.selectItemListener.selectItem(imageUrl);
        }
        previewItem.bind(this.mResourceItems.get(i).getImageUrl(), i == this.selectPosition);
        if (this.isFinish) {
            previewItem.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.PreviewAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m715x295e828d(previewItem, view);
                }
            });
        } else {
            previewItem.setOnClickListener(null);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-miui-handwriting-common-ai-PreviewAdapter, reason: not valid java name */
    /* synthetic */ void m715x295e828d(PreviewItem previewItem, View view) {
        this.selectImage = previewItem.getImageDir();
        this.selectPosition = previewItem.getAdapterPosition();
        this.selectItemListener.selectItem(this.selectImage);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CreationImage> list = this.mResourceItems;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public int getSelectPosition() {
        return this.selectPosition;
    }

    public void setLoadingFinish(boolean z) {
        this.isFinish = z;
        if (z) {
            return;
        }
        this.selectPosition = -1;
    }
}
