package com.miui.handwriting.common.ai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.ai.data.CreationImageForOS1;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewAdapterForOS1 extends RecyclerView.Adapter<PreviewItemForOS1> {
    private Activity activity;
    private LayoutInflater mLayoutInflater;
    private List<CreationImageForOS1> mResourceItems;
    private String selectImage;
    private SelectItemListener selectItemListener;
    private int selectPosition = -1;
    private boolean isFinish = false;

    public interface SelectItemListener {
        void selectItem(String str);
    }

    public PreviewAdapterForOS1(Activity activity) {
        this.activity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }

    public void setSelectItemListener(SelectItemListener selectItemListener) {
        this.selectItemListener = selectItemListener;
    }

    public void setData(List<CreationImageForOS1> list) {
        this.mResourceItems = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PreviewItemForOS1 onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewItemForOS1(this.mLayoutInflater.inflate(R.layout.creation_preview_item, viewGroup, false), this.activity);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final PreviewItemForOS1 previewItemForOS1, int i) {
        if (this.selectPosition == -1 && this.isFinish) {
            this.selectPosition = 0;
            String imageData = this.mResourceItems.get(0).getImageData();
            this.selectImage = imageData;
            this.selectItemListener.selectItem(imageData);
        }
        previewItemForOS1.bind(this.mResourceItems.get(i).getImageData(), i == this.selectPosition);
        if (this.isFinish) {
            previewItemForOS1.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.PreviewAdapterForOS1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m716xb5881651(previewItemForOS1, view);
                }
            });
        } else {
            previewItemForOS1.setOnClickListener(null);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-miui-handwriting-common-ai-PreviewAdapterForOS1, reason: not valid java name */
    /* synthetic */ void m716xb5881651(PreviewItemForOS1 previewItemForOS1, View view) {
        this.selectImage = previewItemForOS1.getImageDir();
        this.selectPosition = previewItemForOS1.getAdapterPosition();
        this.selectItemListener.selectItem(this.selectImage);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CreationImageForOS1> list = this.mResourceItems;
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
