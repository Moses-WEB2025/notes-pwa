package com.miui.common.base;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import com.miui.notes.base.utils.Logger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes2.dex */
public abstract class FragmentPagerAdapter extends PagerAdapter {
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;

    @Deprecated
    public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
    private static final boolean DEBUG = false;
    protected static String TAG = "FragmentPagerAdapter";
    private final int mBehavior;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem;
    private boolean mExecutingFinishUpdate;
    private final FragmentManager mFragmentManager;

    @Retention(RetentionPolicy.SOURCE)
    private @interface Behavior {
    }

    public abstract Fragment getItem(int i);

    public long getItemId(int i) {
        return i;
    }

    public int getPositionFromCache(int i) {
        return i;
    }

    public abstract String makeFragmentName(int i, long j);

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(ViewGroup viewGroup) {
    }

    @Deprecated
    public FragmentPagerAdapter(FragmentManager fragmentManager) {
        this(fragmentManager, 0);
    }

    public FragmentPagerAdapter(FragmentManager fragmentManager, int i) {
        this.mCurTransaction = null;
        this.mCurrentPrimaryItem = null;
        this.mFragmentManager = fragmentManager;
        this.mBehavior = i;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long itemId = getItemId(i);
        Logger.INSTANCE.d(TAG, "instantiateItem " + i + "-" + itemId);
        Fragment fragmentFindFragmentByTag = this.mFragmentManager.findFragmentByTag(makeFragmentName(getPositionFromCache(i), itemId));
        if (fragmentFindFragmentByTag != null) {
            this.mCurTransaction.attach(fragmentFindFragmentByTag);
        } else {
            fragmentFindFragmentByTag = getItem(i);
            Logger.INSTANCE.d(TAG, "fragment null " + fragmentFindFragmentByTag);
            this.mCurTransaction.add(viewGroup.getId(), fragmentFindFragmentByTag, makeFragmentName(getPositionFromCache(i), itemId));
        }
        if (fragmentFindFragmentByTag != this.mCurrentPrimaryItem) {
            Logger.INSTANCE.d(TAG, "fragment not primary item ");
            fragmentFindFragmentByTag.setMenuVisibility(false);
            fragmentFindFragmentByTag.setUserVisibleHint(false);
        }
        Logger.INSTANCE.d(TAG, "instantiateItem " + i + "--finished");
        return fragmentFindFragmentByTag;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach(fragment);
        if (fragment.equals(this.mCurrentPrimaryItem)) {
            this.mCurrentPrimaryItem = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.mCurrentPrimaryItem;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                if (this.mBehavior == 1) {
                    if (this.mCurTransaction == null) {
                        this.mCurTransaction = this.mFragmentManager.beginTransaction();
                    }
                    this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
                } else {
                    this.mCurrentPrimaryItem.setUserVisibleHint(false);
                }
            }
            fragment.setMenuVisibility(true);
            if (this.mBehavior == 1) {
                if (this.mCurTransaction == null) {
                    this.mCurTransaction = this.mFragmentManager.beginTransaction();
                }
                this.mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            } else {
                fragment.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.mCurTransaction;
        if (fragmentTransaction != null) {
            if (!this.mExecutingFinishUpdate) {
                try {
                    this.mExecutingFinishUpdate = true;
                    fragmentTransaction.commitNowAllowingStateLoss();
                    this.mExecutingFinishUpdate = false;
                } catch (Throwable th) {
                    this.mExecutingFinishUpdate = false;
                    throw th;
                }
            }
            this.mCurTransaction = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }
}
