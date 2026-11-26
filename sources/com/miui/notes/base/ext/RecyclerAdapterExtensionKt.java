package com.miui.notes.base.ext;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: RecyclerAdapterExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u001a7\u0010\u0000\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t\u001a \u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\f"}, d2 = {"update", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "pos", "", "mode", "Lcom/miui/notes/base/ext/AdapterUpdateMode;", "adapterChangedRange", "", "(Landroidx/recyclerview/widget/RecyclerView$Adapter;ILcom/miui/notes/base/ext/AdapterUpdateMode;[I)Landroidx/recyclerview/widget/RecyclerView$Adapter;", "calculateRangeChange", "", "common_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class RecyclerAdapterExtensionKt {

    /* JADX INFO: compiled from: RecyclerAdapterExtension.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AdapterUpdateMode.values().length];
            try {
                iArr[AdapterUpdateMode.DISORDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AdapterUpdateMode.RANGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ RecyclerView.Adapter update$default(RecyclerView.Adapter adapter, int i, AdapterUpdateMode adapterUpdateMode, int[] iArr, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            adapterUpdateMode = AdapterUpdateMode.DISORDER;
        }
        return update(adapter, i, adapterUpdateMode, iArr);
    }

    public static final <T extends RecyclerView.Adapter<?>> T update(T t, int i, AdapterUpdateMode mode, int[] adapterChangedRange) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(adapterChangedRange, "adapterChangedRange");
        if (i < 0) {
            Log.e(t.getClass().getSimpleName(), "pos is invalid!!!! value should >0 ,current is " + i + "!!!");
            return t;
        }
        calculateRangeChange(i, mode, adapterChangedRange);
        int i2 = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            int i3 = adapterChangedRange[0];
            t.notifyItemRangeChanged(i3, RangesKt.coerceAtLeast(1, adapterChangedRange[1] - i3));
            return t;
        }
        for (int i4 : adapterChangedRange) {
            t.notifyItemChanged(i4);
        }
        return t;
    }

    private static final void calculateRangeChange(int i, AdapterUpdateMode adapterUpdateMode, int[] iArr) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[adapterUpdateMode.ordinal()];
        if (i2 == 1) {
            int i3 = iArr[1];
            iArr[1] = i;
            iArr[0] = i3;
        } else {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (i < iArr[0]) {
                iArr[0] = i;
            } else if (i > iArr[1]) {
                iArr[1] = i;
            }
        }
    }
}
