package com.miui.common.base;

import android.animation.Animator;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import kotlin.Metadata;
import miuix.appcompat.app.Fragment;

/* JADX INFO: compiled from: BaseFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/common/base/BaseFragment;", "Lmiuix/appcompat/app/Fragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreateAnimator", "Landroid/animation/Animator;", "transit", "", "enter", "", "nextAnim", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseFragment extends Fragment {
    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        try {
            return super.onCreateAnimator(transit, enter, nextAnim);
        } catch (Exception e) {
            Logger.INSTANCE.e("BaseFragment", "onCreateAnimator error" + e.getMessage());
            return null;
        }
    }
}
