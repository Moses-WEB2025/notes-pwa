package com.miui.pc.feature.notes;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.IPopMenu;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcImageEditMenu implements IPopMenu {
    private View mAnnotate;
    private View mCopy;
    private View mCut;
    private View mDelete;
    private View mDoodle;
    private View mRootView;
    private View mSaveAs;
    private View mZoom;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcImageEditMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cut:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 3);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case R.id.copy:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 4);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case com.miui.notes.R.id.annotate_image /* 2131362007 */:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 0);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case com.miui.notes.R.id.delete /* 2131362396 */:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 6);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case com.miui.notes.R.id.edit_image /* 2131362464 */:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 1);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case com.miui.notes.R.id.save_as /* 2131363336 */:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 5);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
                case com.miui.notes.R.id.scale_image /* 2131363347 */:
                    if (PcImageEditMenu.this.mOnMenuListener != null) {
                        PcImageEditMenu.this.mOnMenuListener.onItemSelected(view, 2);
                    }
                    PcImageEditMenu.this.dismiss();
                    break;
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcImageEditMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (PcImageEditMenu.this.mOnMenuListener != null) {
                PcImageEditMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcImageEditMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcImageEditMenu.this.mRootView != null && (viewGroup = (ViewGroup) PcImageEditMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(PcImageEditMenu.this.mRootView);
            }
            if (PcImageEditMenu.this.mOnMenuListener != null) {
                PcImageEditMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public PcImageEditMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(com.miui.notes.R.layout.pc_note_image_edit_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcImageEditMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcImageEditMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(com.miui.notes.R.id.annotate_image);
        this.mAnnotate = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mItemClickListener);
        View viewFindViewById2 = view.findViewById(com.miui.notes.R.id.edit_image);
        this.mDoodle = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mItemClickListener);
        View viewFindViewById3 = view.findViewById(com.miui.notes.R.id.scale_image);
        this.mZoom = viewFindViewById3;
        viewFindViewById3.setOnClickListener(this.mItemClickListener);
        View viewFindViewById4 = view.findViewById(R.id.cut);
        this.mCut = viewFindViewById4;
        viewFindViewById4.setOnClickListener(this.mItemClickListener);
        View viewFindViewById5 = view.findViewById(R.id.copy);
        this.mCopy = viewFindViewById5;
        viewFindViewById5.setOnClickListener(this.mItemClickListener);
        View viewFindViewById6 = view.findViewById(com.miui.notes.R.id.save_as);
        this.mSaveAs = viewFindViewById6;
        viewFindViewById6.setOnClickListener(this.mItemClickListener);
        View viewFindViewById7 = view.findViewById(com.miui.notes.R.id.delete);
        this.mDelete = viewFindViewById7;
        viewFindViewById7.setOnClickListener(this.mItemClickListener);
    }

    public void setImageShowState(boolean z) {
        if (z) {
            ((TextView) this.mZoom).setText(com.miui.notes.R.string.image_action_zoom_out);
        } else {
            ((TextView) this.mZoom).setText(com.miui.notes.R.string.image_action_zoom_in);
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, final int[] iArr) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcImageEditMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                Rect rect2 = new Rect();
                view.getGlobalVisibleRect(rect);
                view.getLocalVisibleRect(rect2);
                int iDip2px = rect.left + iArr[0];
                int width = PcImageEditMenu.this.mRootView.findViewById(com.miui.notes.R.id.container).getWidth();
                int height = PcImageEditMenu.this.mRootView.findViewById(com.miui.notes.R.id.container).getHeight();
                int realWidth = UIUtils.getRealWidth(PcImageEditMenu.this.mRootView.getContext());
                int realHeight = UIUtils.getRealHeight(PcImageEditMenu.this.mRootView.getContext());
                int i = width + iDip2px;
                if (i > realWidth) {
                    iDip2px = (iDip2px - (i - realWidth)) - UIUtils.dip2px(PcImageEditMenu.this.mRootView.getContext(), 10.0f);
                }
                PcImageEditMenu.this.mRootView.findViewById(com.miui.notes.R.id.container).setX(iDip2px);
                int iDip2px2 = (iArr[1] - rect2.top) + rect.top;
                int i2 = height + iDip2px2;
                if (i2 > realHeight) {
                    iDip2px2 = (iDip2px2 - (i2 - realHeight)) - UIUtils.dip2px(PcImageEditMenu.this.mRootView.getContext(), 60.0f);
                }
                PcImageEditMenu.this.mRootView.findViewById(com.miui.notes.R.id.container).setY(iDip2px2);
                Folme.useAt(PcImageEditMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(PcImageEditMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
