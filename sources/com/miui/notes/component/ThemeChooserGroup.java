package com.miui.notes.component;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class ThemeChooserGroup implements View.OnClickListener {
    private static final int[] sThemes = {0, 11, 12, 13, 14, 8, 7, 5, 6, 9, 2, 1, 3, 4};
    private Activity mContext;
    private OnThemeChosenListener mOnThemeChosenListener;
    private View mRootView;
    private int mThemeId;
    private ViewGroup mThemesContainer;

    public interface OnThemeChosenListener {
        void onThemeChosen(int i);
    }

    public ThemeChooserGroup(Activity activity, View view, int i) {
        this.mContext = activity;
        this.mThemeId = i;
        this.mRootView = view;
        init();
    }

    private void init() {
        this.mThemesContainer = (ViewGroup) this.mRootView.findViewById(R.id.themes);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.v12_theme_chooser_item_margin_start);
        if (this.mThemesContainer.getChildCount() != sThemes.length) {
            this.mThemesContainer.removeAllViews();
            int i = 0;
            while (true) {
                int[] iArr = sThemes;
                if (i >= iArr.length) {
                    break;
                }
                int i2 = iArr[i];
                Theme theme = ResourceManager.getTheme(i2);
                View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.v12_theme_chooser_item, this.mThemesContainer, false);
                viewInflate.setId(i2);
                viewInflate.setContentDescription(theme.getName(viewInflate.getContext()));
                AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(viewInflate, i2 == this.mThemeId);
                bindItem(theme, viewInflate);
                if (i == iArr.length - 1) {
                    ((LinearLayout.LayoutParams) viewInflate.getLayoutParams()).setMarginEnd(0);
                }
                if (i > 0 && i < iArr.length) {
                    ((LinearLayout.LayoutParams) viewInflate.getLayoutParams()).setMarginStart(dimensionPixelSize);
                }
                this.mThemesContainer.addView(viewInflate);
                i++;
            }
        }
        View viewFindViewById = this.mThemesContainer.findViewById(this.mThemeId);
        if (viewFindViewById != null) {
            View viewFindViewById2 = viewFindViewById.findViewById(R.id.theme_icon_frame);
            View viewFindViewById3 = viewFindViewById.findViewById(R.id.theme_icon);
            viewFindViewById2.setScaleX(1.0f);
            viewFindViewById2.setScaleY(1.0f);
            viewFindViewById2.setAlpha(1.0f);
            viewFindViewById3.setSelected(true);
        }
    }

    private void bindItem(Theme theme, View view) {
        view.setBackground(theme.getIconBg(this.mContext));
        view.setOnClickListener(this);
        ((ImageView) view.findViewById(R.id.theme_icon)).setBackground(theme.getIcon(this.mContext));
        view.findViewById(R.id.theme_icon_frame).setBackgroundResource(R.drawable.v12_ic_theme_cover_frame);
    }

    public void updateColor(int i) {
        int i2 = this.mThemeId;
        if (i == i2) {
            return;
        }
        View viewFindViewById = this.mThemesContainer.findViewById(i2);
        if (viewFindViewById != null) {
            AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(viewFindViewById, false);
            View viewFindViewById2 = viewFindViewById.findViewById(R.id.theme_icon_frame);
            View viewFindViewById3 = viewFindViewById.findViewById(R.id.theme_icon);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setScaleX(1.0f);
                viewFindViewById2.setScaleY(1.0f);
                viewFindViewById2.animate().scaleX(0.95f).scaleY(0.95f).alpha(0.0f).setDuration(200L).start();
                viewFindViewById3.setSelected(false);
            }
        }
        View viewFindViewById4 = this.mThemesContainer.findViewById(i);
        View viewFindViewById5 = this.mThemesContainer.findViewById(i).findViewById(R.id.theme_icon_frame);
        View viewFindViewById6 = this.mThemesContainer.findViewById(i).findViewById(R.id.theme_icon);
        if (viewFindViewById5 != null) {
            AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(viewFindViewById4, true);
            viewFindViewById5.setScaleX(0.95f);
            viewFindViewById5.setScaleY(0.95f);
            viewFindViewById5.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).start();
            viewFindViewById6.setSelected(true);
            AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(viewFindViewById5, true);
        }
        this.mThemeId = i;
    }

    public void setOnThemeChosenListener(OnThemeChosenListener onThemeChosenListener) {
        this.mOnThemeChosenListener = onThemeChosenListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mOnThemeChosenListener == null || Utils.isFastClick(view.getId(), 1000L)) {
            return;
        }
        this.mOnThemeChosenListener.onThemeChosen(view.getId());
        NotesTextStat.reportThemeClickEvent(view.getId());
    }
}
