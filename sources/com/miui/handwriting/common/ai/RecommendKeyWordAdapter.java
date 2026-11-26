package com.miui.handwriting.common.ai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.ai.data.ThemeItem;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RecommendKeyWordAdapter extends RecyclerView.Adapter<FlexBoxItem> {
    private LayoutInflater mLayoutInflater;
    private List<ThemeItem> mThemeItems;
    private ThemeItem selectThemes;

    public RecommendKeyWordAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<ThemeItem> list) {
        this.mThemeItems = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FlexBoxItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FlexBoxItem(this.mLayoutInflater.inflate(R.layout.creation_recommend_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FlexBoxItem flexBoxItem, final int i) {
        flexBoxItem.textView.setText(this.mThemeItems.get(i).getTheme());
        flexBoxItem.bind(this.mThemeItems.get(i).getTheme(), this.selectThemes != null && this.mThemeItems.get(i).getThemeId() == this.selectThemes.getThemeId());
        flexBoxItem.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.RecommendKeyWordAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m719x34a9af7c(i, view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-miui-handwriting-common-ai-RecommendKeyWordAdapter, reason: not valid java name */
    /* synthetic */ void m719x34a9af7c(int i, View view) {
        this.selectThemes = this.mThemeItems.get(i);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ThemeItem> list = this.mThemeItems;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public ThemeItem getSelectThemes() {
        return this.selectThemes;
    }

    public void setSelectThemes(ThemeItem themeItem) {
        this.selectThemes = themeItem;
    }
}
