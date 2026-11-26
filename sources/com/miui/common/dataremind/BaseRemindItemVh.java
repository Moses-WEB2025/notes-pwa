package com.miui.common.dataremind;

import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseRemindItemVh.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/common/dataremind/BaseRemindItemVh;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mTvContent", "Landroid/widget/TextView;", "bind", "", "data", "position", "", "(Ljava/lang/Object;I)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseRemindItemVh<T> extends RecyclerView.ViewHolder {
    protected TextView mTvContent;

    public void bind(T data, int position) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseRemindItemVh(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.mTvContent = (TextView) itemView.findViewById(R.id.content);
    }
}
