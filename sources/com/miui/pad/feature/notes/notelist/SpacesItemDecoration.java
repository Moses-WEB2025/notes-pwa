package com.miui.pad.feature.notes.notelist;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Utils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpacesItemDecoration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/miui/pad/feature/notes/notelist/SpacesItemDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "space", "", "lastSpace", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(II)V", "getItemOffsets", "", "outRect", "Landroid/graphics/Rect;", "view", "Landroid/view/View;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int lastSpace;
    private final int space;

    public SpacesItemDecoration(int i, int i2) {
        this.space = i;
        this.lastSpace = i2;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x003d  */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        outRect.left = 0;
        if (parent.getAdapter() != null) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            RecyclerView.Adapter adapter = parent.getAdapter();
            Intrinsics.checkNotNull(adapter);
            if (childAdapterPosition == adapter.getItemCount() - 1 && !Utils.isRTL()) {
                outRect.right = 0;
            } else {
                outRect.right = this.space;
            }
        } else {
            outRect.right = this.space;
        }
        outRect.bottom = 0;
        outRect.top = 0;
    }
}
