package com.miui.notes.ai.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PopupRvAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001fB)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0016J\b\u0010\u0018\u001a\u00020\tH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\tH\u0016J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0011R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0013¨\u0006 "}, d2 = {"Lcom/miui/notes/ai/ui/PopupRvAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/miui/notes/ai/ui/PopupRvAdapter$PopupRvViewHolder;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "popupDataList", "", "", "selectIndex", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Ljava/util/List;Ljava/lang/Integer;)V", "mContext", "getMContext", "()Landroid/content/Context;", "mPopupDataList", "mItemListener", "Lcom/miui/notes/ai/ui/PopupRvAdapterListener;", "mSelectedPosition", "Ljava/lang/Integer;", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "getItemCount", "onBindViewHolder", "", "holder", "position", "setOnItemClickListener", "itemListener", "PopupRvViewHolder", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PopupRvAdapter extends RecyclerView.Adapter<PopupRvViewHolder> {
    private final Context mContext;
    private PopupRvAdapterListener mItemListener;
    private final List<String> mPopupDataList;
    private Integer mSelectedPosition;

    public /* synthetic */ PopupRvAdapter(Context context, List list, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, (i & 4) != 0 ? 0 : num);
    }

    public PopupRvAdapter(Context context, List<String> popupDataList, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(popupDataList, "popupDataList");
        this.mContext = context;
        this.mPopupDataList = popupDataList;
        this.mSelectedPosition = num;
    }

    public final Context getMContext() {
        return this.mContext;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PopupRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.global_panel_popup_item, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new PopupRvViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mPopupDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final PopupRvViewHolder holder, final int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final String str = this.mPopupDataList.get(position);
        ViewGroup.LayoutParams layoutParams = holder.getMContentText().getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        if (position == 0) {
            layoutParams2.topMargin = DisplayUtil.dip2px(this.mContext, 6.0f);
            layoutParams2.bottomMargin = 0;
        } else if (position == getItemCount() - 1) {
            layoutParams2.topMargin = 0;
            layoutParams2.bottomMargin = DisplayUtil.dip2px(this.mContext, 6.0f);
        } else {
            layoutParams2.topMargin = 0;
            layoutParams2.bottomMargin = 0;
        }
        holder.getMContentText().setLayoutParams(layoutParams2);
        holder.getMContentText().setText(str);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.ui.PopupRvAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupRvAdapter.onBindViewHolder$lambda$0(holder, this, position, str, view);
            }
        });
        Integer num = this.mSelectedPosition;
        if (num != null && num.intValue() == position) {
            holder.getMContentText().setTextColor(this.mContext.getColor(R.color.miuix_color_blue_light_level1));
            holder.getMContentText().setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ai_tag_popup_item_pick, 0);
            holder.getMContentText().setCompoundDrawablePadding((int) this.mContext.getResources().getDimension(R.dimen.global_ai_text_popup_margin));
        } else {
            holder.getMContentText().setTextColor(this.mContext.getColor(R.color.global_ai_panel_style_text));
            holder.getMContentText().setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(PopupRvViewHolder popupRvViewHolder, PopupRvAdapter popupRvAdapter, int i, String str, View view) {
        int bindingAdapterPosition = popupRvViewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition != -1) {
            popupRvAdapter.mSelectedPosition = Integer.valueOf(bindingAdapterPosition);
        }
        PopupRvAdapterListener popupRvAdapterListener = popupRvAdapter.mItemListener;
        if (popupRvAdapterListener != null) {
            popupRvAdapterListener.onItemClick(i, str);
        }
    }

    public final void setOnItemClickListener(PopupRvAdapterListener itemListener) {
        Intrinsics.checkNotNullParameter(itemListener, "itemListener");
        this.mItemListener = itemListener;
    }

    /* JADX INFO: compiled from: PopupRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/miui/notes/ai/ui/PopupRvAdapter$PopupRvViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mContentText", "Landroid/widget/TextView;", "getMContentText", "()Landroid/widget/TextView;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PopupRvViewHolder extends RecyclerView.ViewHolder {
        private final TextView mContentText;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PopupRvViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.global_panel_popup_text);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.mContentText = (TextView) viewFindViewById;
        }

        public final TextView getMContentText() {
            return this.mContentText;
        }
    }
}
