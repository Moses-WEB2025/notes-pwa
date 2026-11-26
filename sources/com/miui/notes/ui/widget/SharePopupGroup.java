package com.miui.notes.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.OriginalViewPager;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.ResolverAdapter;
import com.miui.richeditor.theme.Theme;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class SharePopupGroup implements OriginalViewPager.OnPageChangeListener {
    public static final String TAG = "SharePopupWindow";
    private ResolverAdapter mChooserAdapter;
    private RecyclerView mChooserView;
    private View mContentView;
    private Activity mContext;
    private boolean mIsForceDark;
    private boolean mIsShowing = false;
    private FrameLayout mMaskView;
    private Theme mTheme;

    @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
    }

    public SharePopupGroup(Activity activity, View view, Theme theme) {
        this.mIsForceDark = false;
        this.mContext = activity;
        this.mContentView = view;
        this.mTheme = theme;
        if (theme != null) {
            this.mIsForceDark = theme.getStatusBarStyle(activity).mStatusBar == 2;
        }
        FrameLayout frameLayout = new FrameLayout(activity);
        this.mMaskView = frameLayout;
        frameLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ui.widget.SharePopupGroup.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                SharePopupGroup.this.dismiss();
                return true;
            }
        });
        initShareContainer(this.mContentView);
    }

    private void initShareContainer(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chooser_recycler);
        this.mChooserView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        ResolverAdapter resolverAdapter = new ResolverAdapter(this.mContext, null, this.mTheme, true);
        this.mChooserAdapter = resolverAdapter;
        this.mChooserView.setAdapter(resolverAdapter);
    }

    public void updateStyle(Theme theme) {
        Theme theme2 = this.mTheme;
        if (theme2 == null || theme2.getId() != theme.getId()) {
            this.mTheme = theme;
            this.mIsForceDark = theme.getStatusBarStyle(this.mContext).mStatusBar == 2;
        }
    }

    public void queryInfo(Intent intent) {
        ResolverAdapter resolverAdapter = this.mChooserAdapter;
        if (resolverAdapter != null) {
            resolverAdapter.requery(intent);
        }
    }

    public void setOnIntentSelectedListener(ResolverAdapter.OnIntentSelectedListener onIntentSelectedListener) {
        ResolverAdapter resolverAdapter = this.mChooserAdapter;
        if (resolverAdapter != null) {
            resolverAdapter.setOnIntentSelectedListener(onIntentSelectedListener);
        }
    }

    public void updatePaddingBottom(int i) {
        this.mContentView.setPadding(0, 0, 0, i);
        this.mContentView.requestLayout();
    }

    public void refresh(Rect rect) {
        if (this.mIsShowing) {
            int i = rect.bottom;
            View view = this.mContentView;
            view.setTranslationY(i - view.getHeight());
        }
    }

    public void show(Rect rect) {
        if (this.mIsShowing) {
            return;
        }
        this.mIsShowing = true;
        this.mContentView.setVisibility(4);
        int iArgb = Color.argb(80, 150, 150, 150);
        int iArgb2 = Color.argb(80, 150, 150, 150);
        Theme theme = this.mTheme;
        if (theme != null) {
            iArgb = theme.getEditorStyle(this.mContext).mBlurMixColor;
            iArgb2 = this.mTheme.getEditorStyle(this.mContext).mBlurMixColor2;
        }
        BlurHelper.setBlurBackground(this.mContentView, null, iArgb, iArgb2);
        ViewGroup viewGroup = (ViewGroup) UIUtils.getActionBarOverlayLayoutByDecorView(this.mContext.getWindow().getDecorView());
        viewGroup.addView(this.mMaskView);
        viewGroup.addView(this.mContentView);
        final int i = rect.bottom;
        this.mContentView.setTranslationY(i);
        this.mContentView.post(new Runnable() { // from class: com.miui.notes.ui.widget.SharePopupGroup.2
            @Override // java.lang.Runnable
            public void run() {
                SharePopupGroup.this.mContentView.setVisibility(0);
                SharePopupGroup.this.mContentView.animate().translationY(i - SharePopupGroup.this.mContentView.getHeight()).setInterpolator(new EaseManager.SpringInterpolator()).start();
            }
        });
    }

    private void restoreFocusedAccessibility() {
        int[] iArr = {R.id.header, R.id.fragment_content, R.id.action_bar_container, R.id.root, R.id.sv_note_edit, R.id.navigation_decor_wrapper};
        for (int i = 0; i < 6; i++) {
            View viewFindViewById = this.mContentView.getRootView().findViewById(iArr[i]);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(1);
            }
        }
    }

    public void dismiss() {
        this.mIsShowing = false;
        restoreFocusedAccessibility();
        this.mContentView.animate().translationY(this.mContext.getWindow().getDecorView().getHeight()).setInterpolator(new EaseManager.SpringInterpolator()).withEndAction(new Runnable() { // from class: com.miui.notes.ui.widget.SharePopupGroup.3
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup viewGroup = (ViewGroup) SharePopupGroup.this.mContentView.getParent();
                SharePopupGroup.this.mContentView.setBackground(null);
                if (viewGroup != null) {
                    viewGroup.removeView(SharePopupGroup.this.mContentView);
                    viewGroup.removeView(SharePopupGroup.this.mMaskView);
                }
            }
        }).start();
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }
}
