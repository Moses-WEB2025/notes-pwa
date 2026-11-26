package com.miui.pad.feature.notes.commonedit;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class AiChooserMenu implements IPopMenu {
    private static String TAG = "Notes:AiChooserMenu";
    private LinearLayout mAiAbstract;
    private LinearLayout mAiFormat;
    private LinearLayout mAiProof;
    private LinearLayout mAiTranslate;
    private TransitionListener mDismissListener;
    private boolean mIsDismissing;
    private View.OnClickListener mItemClickListener;
    private int mMaxContainerHeight;
    private IPopMenu.OnMenuListener mOnMenuListener;
    private View mRootView;
    private TransitionListener mShowListener;

    public AiChooserMenu(Context context) {
        this.mRootView = null;
        this.mOnMenuListener = null;
        this.mIsDismissing = false;
        this.mMaxContainerHeight = 0;
        this.mShowListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.AiChooserMenu.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (AiChooserMenu.this.mOnMenuListener != null) {
                    AiChooserMenu.this.mOnMenuListener.onShow();
                }
            }
        };
        this.mDismissListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.AiChooserMenu.2
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (AiChooserMenu.this.mRootView != null) {
                    Logger.INSTANCE.d(AiChooserMenu.TAG, "removeView");
                    ((ViewGroup) AiChooserMenu.this.mRootView.getParent()).removeView(AiChooserMenu.this.mRootView);
                }
                if (AiChooserMenu.this.mOnMenuListener != null) {
                    AiChooserMenu.this.mOnMenuListener.onDismiss();
                }
            }
        };
        this.mItemClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.AiChooserMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ai_check /* 2131361918 */:
                        if (AiChooserMenu.this.mOnMenuListener != null) {
                            AiChooserMenu.this.mOnMenuListener.onItemSelected(view, 2);
                        }
                        AiChooserMenu.this.dismiss();
                        break;
                    case R.id.ai_layout /* 2131361948 */:
                        if (AiChooserMenu.this.mOnMenuListener != null) {
                            AiChooserMenu.this.mOnMenuListener.onItemSelected(view, 1);
                        }
                        AiChooserMenu.this.dismiss();
                        break;
                    case R.id.ai_summary /* 2131361966 */:
                        if (AiChooserMenu.this.mOnMenuListener != null) {
                            AiChooserMenu.this.mOnMenuListener.onItemSelected(view, 0);
                        }
                        AiChooserMenu.this.dismiss();
                        break;
                    case R.id.ai_translate /* 2131361972 */:
                        if (AiChooserMenu.this.mOnMenuListener != null) {
                            AiChooserMenu.this.mOnMenuListener.onItemSelected(view, 3);
                        }
                        AiChooserMenu.this.dismiss();
                        break;
                    default:
                        AiChooserMenu.this.dismiss();
                        break;
                }
            }
        };
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.note_ai_chooser_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.AiChooserMenu.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AiChooserMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    public AiChooserMenu(Context context, int i) {
        this(context);
        this.mMaxContainerHeight = i;
    }

    private void initView(View view) {
        this.mAiAbstract = (LinearLayout) view.findViewById(R.id.ai_summary);
        this.mAiFormat = (LinearLayout) view.findViewById(R.id.ai_layout);
        this.mAiProof = (LinearLayout) view.findViewById(R.id.ai_check);
        this.mAiTranslate = (LinearLayout) view.findViewById(R.id.ai_translate);
        this.mAiAbstract.setOnClickListener(this.mItemClickListener);
        this.mAiFormat.setOnClickListener(this.mItemClickListener);
        this.mAiProof.setOnClickListener(this.mItemClickListener);
        this.mAiTranslate.setOnClickListener(this.mItemClickListener);
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        Logger.INSTANCE.d(TAG, "show insert ai dialog ");
        ViewGroup viewGroup = (ViewGroup) UIUtils.getActionBarOverlayLayout(view);
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.AiChooserMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                View viewFindViewById = AiChooserMenu.this.mRootView.findViewById(R.id.content);
                if (viewFindViewById == null) {
                    return;
                }
                int height = viewFindViewById.getHeight();
                if (AiChooserMenu.this.mMaxContainerHeight != 0 && height > AiChooserMenu.this.mMaxContainerHeight) {
                    View viewFindViewById2 = AiChooserMenu.this.mRootView.findViewById(R.id.menu_scroll_view);
                    ViewGroup.LayoutParams layoutParams = viewFindViewById2.getLayoutParams();
                    layoutParams.height = AiChooserMenu.this.mMaxContainerHeight;
                    viewFindViewById2.setLayoutParams(layoutParams);
                    height = AiChooserMenu.this.mMaxContainerHeight;
                }
                float width = Utils.isRTL() ? (AiChooserMenu.this.mRootView.getWidth() - viewFindViewById.getWidth()) - NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_margin_left) : NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_margin_left);
                if (view.getParent() instanceof PadEditModeToolbar) {
                    width = Utils.isRTL() ? rect.right - viewFindViewById.getWidth() : rect.left;
                }
                viewFindViewById.setX(width);
                viewFindViewById.setY((rect.top - height) - NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_icon_margin_vertical_anchor));
                Folme.useAt(AiChooserMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(AiChooserMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mIsDismissing) {
            return;
        }
        Logger.INSTANCE.d(TAG, "dismiss");
        this.mIsDismissing = true;
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
