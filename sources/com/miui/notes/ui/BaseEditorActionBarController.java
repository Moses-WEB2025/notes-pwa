package com.miui.notes.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.common.base.ActionCallBack;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseEditorActionBarController implements View.OnClickListener {
    protected EditCallback mActionCallback;
    protected Activity mActivity;
    protected ImageView mMoreView;
    protected Theme mTheme;

    public interface EditCallback extends ActionCallBack {
        CharSequence getOptionMenuText(int i);

        int[] getOptionResources();

        void onNavigationUp();
    }

    protected abstract int getLayoutResource();

    public boolean onBackPressed() {
        return false;
    }

    public void onEditMode() {
    }

    public void onViewMode() {
    }

    public BaseEditorActionBarController(Activity activity, EditCallback editCallback) {
        this.mActivity = activity;
        this.mActionCallback = editCallback;
    }

    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(getLayoutResource(), viewGroup, false);
    }

    public void initializeView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.more);
        this.mMoreView = imageView;
        imageView.setOnClickListener(this);
        Folme.useAt(this.mMoreView).touch().handleTouchOf(this.mMoreView, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mMoreView);
        ImageView imageView2 = this.mMoreView;
        if (imageView2 != null) {
            imageView2.setImageDrawable(Theme.getThemeDrawable(R.drawable.ic_action_more, this.mTheme.getResTheme(this.mActivity)));
        }
    }

    public void updateStyle(Theme theme) {
        this.mTheme = theme;
        ImageView imageView = this.mMoreView;
        if (imageView != null) {
            imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.ic_action_more, theme.getResTheme(this.mActivity)));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.more) {
            return;
        }
        this.mActionCallback.onPerformAction(23, this.mMoreView);
    }

    protected void changeMenuVisibility(View view, boolean z) {
        Folme.useAt(view).state().clean();
        Folme.useAt(view).state().cancel();
        if (z) {
            view.setVisibility(0);
            Folme.useAt(view).state().to(ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.ALPHA, Float.valueOf(1.0f));
        } else {
            Folme.useAt(view).state().to(ViewProperty.SCALE_X, Float.valueOf(0.6f), ViewProperty.SCALE_Y, Float.valueOf(0.6f), ViewProperty.ALPHA, Float.valueOf(0.0f));
        }
    }
}
