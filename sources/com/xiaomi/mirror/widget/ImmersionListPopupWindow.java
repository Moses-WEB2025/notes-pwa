package com.xiaomi.mirror.widget;

import android.animation.Animator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.xiaomi.mirror.opensdk.R;

/* JADX INFO: loaded from: classes4.dex */
public class ImmersionListPopupWindow extends PopupWindow {
    public static final float DIM = 0.3f;
    public static final float OFFSET_X = 12.0f;
    public static final float OFFSET_Y = 8.0f;
    public static final String TAG = "ImmersionListPop";
    public ListAdapter mAdapter;
    public final Rect mBackgroundPadding;
    public View mContentView;
    public int mContentWidth;
    public Context mContext;
    public int mElevation;
    public boolean mHasContentWidth;
    public boolean mIsRtl;
    public ListView mListView;
    public int mMaxAllowedWidth;
    public int mMinAllowedWidth;
    public DataSetObserver mObserver;
    public final int mOffsetX;
    public final int mOffsetY;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public AdapterView.OnItemClickListener mOnItemClickListener;
    public FrameLayout mRootView;
    public WindowManager mWindowManager;

    public ImmersionListPopupWindow(Context context) {
        super(context);
        this.mObserver = new DataSetObserver() { // from class: com.xiaomi.mirror.widget.ImmersionListPopupWindow.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                ImmersionListPopupWindow.this.mHasContentWidth = false;
                if (ImmersionListPopupWindow.this.isShowing()) {
                    ImmersionListPopupWindow immersionListPopupWindow = ImmersionListPopupWindow.this;
                    immersionListPopupWindow.update(immersionListPopupWindow.computePopupContentWidth(), ImmersionListPopupWindow.this.getHeight());
                }
            }
        };
        this.mContext = context;
        float f = context.getResources().getDisplayMetrics().density;
        this.mOffsetX = 12;
        this.mOffsetY = (int) (f * 8.0f);
        this.mBackgroundPadding = new Rect();
        setFocusable(true);
        RoundFrameLayout roundFrameLayout = new RoundFrameLayout(context);
        this.mRootView = roundFrameLayout;
        roundFrameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.mirror.widget.ImmersionListPopupWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImmersionListPopupWindow.this.dismiss();
            }
        });
        prepareContentView(context);
        this.mElevation = context.getResources().getDimensionPixelSize(R.dimen.menu_elevation);
        super.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.xiaomi.mirror.widget.ImmersionListPopupWindow.3
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                if (ImmersionListPopupWindow.this.mOnDismissListener != null) {
                    ImmersionListPopupWindow.this.mOnDismissListener.onDismiss();
                }
            }
        });
    }

    private WindowManager getWindowManager() {
        if (this.mWindowManager == null) {
            this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        }
        return this.mWindowManager;
    }

    public static int measureIndividualMenuWidth(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i2 = 0;
        int i3 = 0;
        View view = null;
        for (int i4 = 0; i4 < count; i4++) {
            int itemViewType = listAdapter.getItemViewType(i4);
            if (itemViewType != i3) {
                view = null;
                i3 = itemViewType;
            }
            if (viewGroup == null) {
                viewGroup = new FrameLayout(context);
            }
            view = listAdapter.getView(i4, view, viewGroup);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i) {
                return i;
            }
            if (measuredWidth > i2) {
                i2 = measuredWidth;
            }
        }
        return i2;
    }

    public static void setPopupShadowAlpha(View view) {
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.xiaomi.mirror.widget.ImmersionListPopupWindow.5
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                if (view2.getWidth() == 0 || view2.getHeight() == 0) {
                    return;
                }
                outline.setAlpha(0.7f);
                if (view2.getBackground() != null) {
                    view2.getBackground().getOutline(outline);
                }
            }
        });
    }

    private void showWithAnchor(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        showAtLocation(view, 8388659, (view.getWidth() / 2) + iArr[0], (view.getHeight() / 2) + iArr[1]);
    }

    public int computePopupContentWidth() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.menu_item_width);
    }

    @Deprecated
    public LayoutAnimationController createDefaultFadeInAnimation() {
        return null;
    }

    @Deprecated
    public LayoutAnimationController createDefaultFadeOutAnimation() {
        return null;
    }

    public void dismiss(boolean z) {
        dismiss();
    }

    public void fastShow(View view, ViewGroup viewGroup) {
        setWidth(computePopupContentWidth());
        showWithAnchor(view);
        this.mContentView.forceLayout();
    }

    @Deprecated
    public Animator getBackgroundAnimator(LayoutAnimationController layoutAnimationController, boolean z) {
        return null;
    }

    @Deprecated
    public Drawable getBlurBackground(Context context, View view) {
        return null;
    }

    @Override // android.widget.PopupWindow
    public View getContentView() {
        return this.mContentView;
    }

    @Deprecated
    public void installHeaderView(View view, ViewGroup viewGroup) {
    }

    public void prepareContentView(Context context) {
        Drawable drawable = context.getDrawable(R.drawable.immersion_window_bg);
        if (drawable != null) {
            drawable.getPadding(this.mBackgroundPadding);
            this.mRootView.setBackground(drawable);
        }
        setBackgroundDrawable(new ColorDrawable(0));
        setPopupWindowContentView(this.mRootView);
    }

    public boolean prepareShow(View view, ViewGroup viewGroup) {
        String str;
        if (view == null) {
            str = "show: anchor is null";
        } else {
            if (this.mContentView == null) {
                ListView listView = new ListView(this.mContext);
                this.mContentView = listView;
                listView.setId(android.R.id.list);
            }
            if (this.mRootView.getChildCount() != 1 || this.mRootView.getChildAt(0) != this.mContentView) {
                this.mRootView.removeAllViews();
                this.mRootView.addView(this.mContentView);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mContentView.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -2;
                layoutParams.gravity = 16;
            }
            this.mRootView.setElevation(this.mElevation);
            setElevation(this.mElevation);
            setPopupShadowAlpha(this.mRootView);
            ListView listView2 = (ListView) this.mContentView.findViewById(android.R.id.list);
            this.mListView = listView2;
            listView2.setDividerHeight(0);
            ListView listView3 = this.mListView;
            if (listView3 != null) {
                listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.xiaomi.mirror.widget.ImmersionListPopupWindow.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                        int headerViewsCount = i - ImmersionListPopupWindow.this.mListView.getHeaderViewsCount();
                        if (ImmersionListPopupWindow.this.mOnItemClickListener == null || headerViewsCount < 0 || headerViewsCount >= ImmersionListPopupWindow.this.mAdapter.getCount()) {
                            return;
                        }
                        ImmersionListPopupWindow.this.mOnItemClickListener.onItemClick(adapterView, view2, headerViewsCount, j);
                    }
                });
                this.mListView.setAdapter(this.mAdapter);
                setWidth(computePopupContentWidth());
                return true;
            }
            str = "list not found";
        }
        Log.e(TAG, str);
        return false;
    }

    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2 = this.mAdapter;
        if (listAdapter2 != null) {
            listAdapter2.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
    }

    @Override // android.widget.PopupWindow
    public void setContentView(View view) {
        this.mContentView = view;
    }

    @Override // android.widget.PopupWindow
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setPopupWindowContentView(View view) {
        super.setContentView(view);
    }

    public void show(View view, ViewGroup viewGroup) {
        if (prepareShow(view, viewGroup)) {
            showWithAnchor(view);
        }
    }
}
