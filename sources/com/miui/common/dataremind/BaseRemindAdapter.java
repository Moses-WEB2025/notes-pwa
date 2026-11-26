package com.miui.common.dataremind;

import android.content.Context;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseRemindAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0001\u0018\u00010\u00030\u0002B\u0011\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000bJ \u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J \u0010\u0014\u001a\u00020\r2\u000e\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00032\u0006\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/common/dataremind/BaseRemindAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/miui/common/dataremind/BaseRemindItemVh;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "mData", "", "setData", "", "data", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "holder", "position", "getItemCount", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseRemindAdapter<T> extends RecyclerView.Adapter<BaseRemindItemVh<T>> {
    private final Context context;
    protected List<T> mData = new ArrayList();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public abstract BaseRemindItemVh<T> onCreateViewHolder(ViewGroup parent, int viewType);

    public final Context getContext() {
        return this.context;
    }

    public BaseRemindAdapter(Context context) {
        this.context = context;
    }

    public final void setData(List<T> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData.clear();
        this.mData.addAll(data);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseRemindItemVh<T> holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.bind(this.mData.get(position), position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size();
    }
}
