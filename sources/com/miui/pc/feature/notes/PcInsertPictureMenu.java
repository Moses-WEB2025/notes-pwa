package com.miui.pc.feature.notes;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcInsertPictureMenu implements IPopMenu {
    private View mCamera;
    private View mDoodle;
    private View mPc;
    private View mPhone;
    private TextView mPhoneName;
    private View mRootView;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcInsertPictureMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pick_from_camera /* 2131363192 */:
                    if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                        PcInsertPictureMenu.this.mOnMenuListener.onItemSelected(view, 2);
                    }
                    PcInsertPictureMenu.this.dismiss();
                    break;
                case R.id.pick_from_doodle /* 2131363193 */:
                    if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                        PcInsertPictureMenu.this.mOnMenuListener.onItemSelected(view, 3);
                    }
                    PcInsertPictureMenu.this.dismiss();
                    break;
                case R.id.pick_from_pc /* 2131363194 */:
                    if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                        PcInsertPictureMenu.this.mOnMenuListener.onItemSelected(view, 0);
                    }
                    PcInsertPictureMenu.this.dismiss();
                    break;
                case R.id.pick_from_phone /* 2131363195 */:
                    if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                        PcInsertPictureMenu.this.mOnMenuListener.onItemSelected(view, 1);
                    }
                    PcInsertPictureMenu.this.dismiss();
                    break;
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcInsertPictureMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                PcInsertPictureMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcInsertPictureMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcInsertPictureMenu.this.mRootView != null && (viewGroup = (ViewGroup) PcInsertPictureMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(PcInsertPictureMenu.this.mRootView);
            }
            if (PcInsertPictureMenu.this.mOnMenuListener != null) {
                PcInsertPictureMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public PcInsertPictureMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_insert_picture_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcInsertPictureMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcInsertPictureMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.pick_from_pc);
        this.mPc = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mItemClickListener);
        View viewFindViewById2 = view.findViewById(R.id.pick_from_phone);
        this.mPhone = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mItemClickListener);
        View viewFindViewById3 = view.findViewById(R.id.pick_from_camera);
        this.mCamera = viewFindViewById3;
        viewFindViewById3.setOnClickListener(this.mItemClickListener);
        View viewFindViewById4 = view.findViewById(R.id.pick_from_doodle);
        this.mDoodle = viewFindViewById4;
        viewFindViewById4.setOnClickListener(this.mItemClickListener);
        this.mPhoneName = (TextView) view.findViewById(R.id.tv_my_phone_name);
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        TextView textView = this.mPhoneName;
        textView.setText(Utils.getDeviceName(textView.getContext()));
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcInsertPictureMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                int iDip2px = rect.left;
                int width = PcInsertPictureMenu.this.mRootView.findViewById(R.id.content).getWidth();
                int realWidth = UIUtils.getRealWidth(PcInsertPictureMenu.this.mRootView.getContext());
                int i = width + iDip2px;
                if (i > realWidth) {
                    iDip2px = (iDip2px - (i - realWidth)) - UIUtils.dip2px(PcInsertPictureMenu.this.mRootView.getContext(), 60.0f);
                }
                PcInsertPictureMenu.this.mRootView.findViewById(R.id.content).setX(iDip2px);
                PcInsertPictureMenu.this.mRootView.findViewById(R.id.content).setY(rect.bottom + UIUtils.dip2px(PcInsertPictureMenu.this.mRootView.getContext(), 20.0f));
                Folme.useAt(PcInsertPictureMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(PcInsertPictureMenu.this.mShowListener));
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
