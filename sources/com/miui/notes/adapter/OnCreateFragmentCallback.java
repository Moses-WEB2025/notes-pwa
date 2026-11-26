package com.miui.notes.adapter;

import androidx.fragment.app.Fragment;
import kotlin.Metadata;

/* JADX INFO: compiled from: HomepageFragmentPagerAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/notes/adapter/OnCreateFragmentCallback;", "", "onCreateFragment", "Landroidx/fragment/app/Fragment;", "position", "", "getItemCount", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface OnCreateFragmentCallback {
    int getItemCount();

    Fragment onCreateFragment(int position);
}
