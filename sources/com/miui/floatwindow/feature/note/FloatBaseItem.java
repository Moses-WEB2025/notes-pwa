package com.miui.floatwindow.feature.note;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.excerpt.ExcerptInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.IFolme;

/* JADX INFO: compiled from: FloatBaseItem.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010\f\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\rJ\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J \u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/floatwindow/feature/note/FloatBaseItem;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mItemViewFolme", "Lmiuix/animation/IFolme;", "setOnClickListener", "", "listener", "Landroid/view/View$OnClickListener;", "setOnLongClickListener", "Landroid/view/View$OnLongClickListener;", "bind", "type", "", "itemCache", "Lcom/miui/notes/cache/ItemCache;", "isSaving", "", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class FloatBaseItem extends RecyclerView.ViewHolder {
    protected IFolme mItemViewFolme;

    public abstract void bind(int type, ItemCache itemCache);

    public abstract void bind(int type, boolean isSaving, ExcerptInfo info);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatBaseItem(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
    }

    public final void setOnClickListener(View.OnClickListener listener) {
        this.itemView.setOnClickListener(listener);
    }

    public final void setOnLongClickListener(View.OnLongClickListener listener) {
        this.itemView.setOnLongClickListener(listener);
    }
}
