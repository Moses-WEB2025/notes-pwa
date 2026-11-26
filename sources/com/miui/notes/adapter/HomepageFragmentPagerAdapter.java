package com.miui.notes.adapter;

import androidx.fragment.app.Fragment;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.androidbasewidget.adapter.FragmentStateAdapter;

/* JADX INFO: compiled from: HomepageFragmentPagerAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/adapter/HomepageFragmentPagerAdapter;", "Lmiuix/androidbasewidget/adapter/FragmentStateAdapter;", "fm", "Landroidx/fragment/app/Fragment;", "onCreateFragmentCallback", "Lcom/miui/notes/adapter/OnCreateFragmentCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/fragment/app/Fragment;Lcom/miui/notes/adapter/OnCreateFragmentCallback;)V", "getOnCreateFragmentCallback", "()Lcom/miui/notes/adapter/OnCreateFragmentCallback;", "createFragment", "position", "", "getItemCount", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HomepageFragmentPagerAdapter extends FragmentStateAdapter {
    private final OnCreateFragmentCallback onCreateFragmentCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomepageFragmentPagerAdapter(Fragment fm, OnCreateFragmentCallback onCreateFragmentCallback) {
        super(fm);
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(onCreateFragmentCallback, "onCreateFragmentCallback");
        this.onCreateFragmentCallback = onCreateFragmentCallback;
    }

    public final OnCreateFragmentCallback getOnCreateFragmentCallback() {
        return this.onCreateFragmentCallback;
    }

    @Override // miuix.androidbasewidget.adapter.FragmentStateAdapter
    public Fragment createFragment(int position) {
        return this.onCreateFragmentCallback.onCreateFragment(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.onCreateFragmentCallback.getItemCount();
    }
}
