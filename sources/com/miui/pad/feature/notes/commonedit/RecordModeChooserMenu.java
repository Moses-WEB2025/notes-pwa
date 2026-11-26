package com.miui.pad.feature.notes.commonedit;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class RecordModeChooserMenu implements IPopMenu {
    private View mMeeting;
    private View mRootView;
    private View mSingle;
    private boolean mIsDismissing = false;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.RecordModeChooserMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.mode_meeting) {
                if (RecordModeChooserMenu.this.mOnMenuListener != null) {
                    RecordModeChooserMenu.this.mOnMenuListener.onItemSelected(view, 1);
                }
                RecordModeChooserMenu.this.dismiss();
            } else {
                if (id != R.id.mode_single) {
                    return;
                }
                if (RecordModeChooserMenu.this.mOnMenuListener != null) {
                    RecordModeChooserMenu.this.mOnMenuListener.onItemSelected(view, 0);
                }
                RecordModeChooserMenu.this.dismiss();
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.RecordModeChooserMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (RecordModeChooserMenu.this.mOnMenuListener != null) {
                RecordModeChooserMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.RecordModeChooserMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (RecordModeChooserMenu.this.mRootView != null) {
                Logger.INSTANCE.d("ImageChooserMenu", "removeView");
                ((ViewGroup) RecordModeChooserMenu.this.mRootView.getParent()).removeView(RecordModeChooserMenu.this.mRootView);
            }
            if (RecordModeChooserMenu.this.mOnMenuListener != null) {
                RecordModeChooserMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public RecordModeChooserMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pad_record_mode_chooser_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.RecordModeChooserMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RecordModeChooserMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.mode_single);
        this.mSingle = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mItemClickListener);
        View viewFindViewById2 = view.findViewById(R.id.mode_meeting);
        this.mMeeting = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mItemClickListener);
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        if (view == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) UIUtils.getActionBarOverlayLayout(view);
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.RecordModeChooserMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                Rect rect2 = new Rect();
                view.getGlobalVisibleRect(rect);
                view.getLocalVisibleRect(rect2);
                int width = RecordModeChooserMenu.this.mRootView.findViewById(R.id.content).getWidth();
                int height = RecordModeChooserMenu.this.mRootView.findViewById(R.id.content).getHeight();
                int iDip2px = rect.right - width;
                if (Utils.isRTL()) {
                    iDip2px = rect.left;
                }
                if (iDip2px < 100) {
                    iDip2px = 100;
                }
                int width2 = RecordModeChooserMenu.this.mRootView.getRootView().getWidth();
                int height2 = RecordModeChooserMenu.this.mRootView.getRootView().getHeight();
                int i = width + iDip2px;
                if (i > width2) {
                    iDip2px = (iDip2px - (i - width2)) - UIUtils.dip2px(RecordModeChooserMenu.this.mRootView.getContext(), 10.0f);
                }
                RecordModeChooserMenu.this.mRootView.findViewById(R.id.content).setX(iDip2px);
                int iDip2px2 = (rect.top - height) - UIUtils.dip2px(RecordModeChooserMenu.this.mRootView.getContext(), 10.0f);
                if (iDip2px2 < 50) {
                    iDip2px2 = 50;
                }
                int i2 = height + iDip2px2;
                if (i2 > height2) {
                    iDip2px2 = (iDip2px2 - (i2 - height2)) - UIUtils.dip2px(RecordModeChooserMenu.this.mRootView.getContext(), 60.0f);
                }
                RecordModeChooserMenu.this.mRootView.findViewById(R.id.content).setY(iDip2px2);
                Folme.useAt(RecordModeChooserMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(RecordModeChooserMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mIsDismissing) {
            return;
        }
        Logger.INSTANCE.d("RecordModeChooserMenu", "dismiss");
        this.mIsDismissing = true;
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
