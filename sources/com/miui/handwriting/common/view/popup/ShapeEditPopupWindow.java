package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.dataset.ShapeEditSet;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.view.ArrowCardView;
import com.miui.libhandwritingpage.utils.Utils;
import java.lang.ref.WeakReference;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeEditPopupWindow extends PopupWindow {
    private OnActionClickListener actionClickListener;
    private Point mAnchorPoint;
    private boolean mChildShowUpside;
    private View mContentView;
    private Context mContext;
    private View mCopy;
    private View mDelete;
    private ShapeColorSelectView mFillColor;
    private ShapeEditColorPopupWindow mFillColorPopupWindow;
    private boolean mNeedShowChild;
    private WeakReference<IMiuiHandWritingModel> mPageInkViewModel;
    private ShapeColorSelectView mStrokeColor;
    private ShapeEditColorPopupWindow mStrokeColorPopupWindow;
    public WeakReference<View> mWeakReference;

    public interface OnActionClickListener {
        void onActionClick(int i);
    }

    public ShapeEditPopupWindow(Context context, OnActionClickListener onActionClickListener, IMiuiHandWritingModel iMiuiHandWritingModel) {
        super(context);
        this.mChildShowUpside = false;
        this.mNeedShowChild = false;
        this.mAnchorPoint = new Point();
        this.mContext = context;
        this.mPageInkViewModel = new WeakReference<>(iMiuiHandWritingModel);
        this.actionClickListener = onActionClickListener;
        View viewInflate = View.inflate(context, R.layout.popup_shape_edit, null);
        this.mContentView = viewInflate;
        setContentView(viewInflate);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    public void initView() {
        setAnimationStyle(R.style.window_fade_anim);
        this.mFillColor = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_fill_color);
        this.mStrokeColor = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_stroke_color);
        this.mCopy = this.mContentView.findViewById(R.id.shape_edit_copy);
        this.mDelete = this.mContentView.findViewById(R.id.shape_edit_delete);
        this.mFillColor.setMode(0);
        this.mStrokeColor.setMode(1);
        this.mFillColor.setColor(ShapeEditSet.getCurShape().getFillColor());
        this.mStrokeColor.setColor(ShapeEditSet.getCurShape().getStrokeColor());
        checkForbidden();
        createFillColorPopupWindow();
        createStrokeColorPopupWindow();
        this.mFillColor.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(view.getId())) {
                    return;
                }
                ShapeEditPopupWindow shapeEditPopupWindow = ShapeEditPopupWindow.this;
                shapeEditPopupWindow.showChildPopupWindow(shapeEditPopupWindow.mFillColorPopupWindow, ShapeEditPopupWindow.this.mFillColor);
                ShapeEditSet.getCurShape().setEditMode(0);
            }
        });
        this.mStrokeColor.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(view.getId())) {
                    return;
                }
                ShapeEditPopupWindow shapeEditPopupWindow = ShapeEditPopupWindow.this;
                shapeEditPopupWindow.showChildPopupWindow(shapeEditPopupWindow.mStrokeColorPopupWindow, ShapeEditPopupWindow.this.mStrokeColor);
                ShapeEditSet.getCurShape().setEditMode(1);
            }
        });
        this.mCopy.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(view.getId()) || ShapeEditPopupWindow.this.actionClickListener == null) {
                    return;
                }
                ShapeEditPopupWindow.this.actionClickListener.onActionClick(8192);
            }
        });
        this.mDelete.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(view.getId()) || ShapeEditPopupWindow.this.actionClickListener == null) {
                    return;
                }
                ShapeEditPopupWindow.this.actionClickListener.onActionClick(256);
            }
        });
        this.mFillColor.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.5
            @Override // java.lang.Runnable
            public void run() {
                if (ShapeEditPopupWindow.this.mPageInkViewModel.get() == null || ((IMiuiHandWritingModel) ShapeEditPopupWindow.this.mPageInkViewModel.get()).getSelectShapeProp() != null) {
                    return;
                }
                ShapeEditPopupWindow.this.dismissImmediately();
            }
        }, 300L);
        if (ShapeEditSet.getCurShape().getShapeType() == 2) {
            this.mStrokeColor.setVisibility(8);
        } else {
            this.mStrokeColor.setVisibility(0);
        }
    }

    public void setChildShowUpside(boolean z) {
        this.mChildShowUpside = z;
    }

    public void setAnchorPoint(int i, int i2) {
        this.mAnchorPoint.set(i, i2);
    }

    public boolean isNeedShowChild() {
        return this.mNeedShowChild;
    }

    public void setNeedShowChild(boolean z) {
        this.mNeedShowChild = z;
    }

    public void setWeakReference(View view) {
        this.mWeakReference = new WeakReference<>(view);
    }

    public void dismissImmediately() {
        ShapeEditColorPopupWindow shapeEditColorPopupWindow = this.mFillColorPopupWindow;
        if (shapeEditColorPopupWindow != null && shapeEditColorPopupWindow.isShowing()) {
            this.mFillColorPopupWindow.dismissImmediately();
        }
        ShapeEditColorPopupWindow shapeEditColorPopupWindow2 = this.mStrokeColorPopupWindow;
        if (shapeEditColorPopupWindow2 != null && shapeEditColorPopupWindow2.isShowing()) {
            this.mStrokeColorPopupWindow.dismissImmediately();
        }
        setAnimationStyle(0);
        update();
        View view = this.mContentView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.6
                @Override // java.lang.Runnable
                public void run() {
                    ShapeEditPopupWindow.this.dismiss();
                }
            });
        } else {
            dismiss();
        }
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(View view, int i, int i2, int i3) {
        super.showAtLocation(view, i, i2, i3);
        if (this.mNeedShowChild) {
            if (ShapeEditSet.getCurShape().getEditMode() == 0) {
                this.mFillColor.callOnClick();
            } else {
                this.mStrokeColor.callOnClick();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showChildPopupWindow(ShapeEditColorPopupWindow shapeEditColorPopupWindow, View view) {
        if (shapeEditColorPopupWindow == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
        shapeEditColorPopupWindow.setArrowOffset(view.getLeft() + (view.getMeasuredWidth() / 2));
        if (this.mChildShowUpside) {
            if (this.mWeakReference.get() != null) {
                View contentView = shapeEditColorPopupWindow.getContentView();
                contentView.measure(0, 0);
                shapeEditColorPopupWindow.setArrowMod(ArrowCardView.ARROW_MODE_DOWN);
                shapeEditColorPopupWindow.showAtLocation(this.mWeakReference.get(), 0, this.mAnchorPoint.x, (this.mAnchorPoint.y - contentView.getMeasuredHeight()) + DisplayUtils.dip2px(this.mContext, 26.0f));
                return;
            }
            return;
        }
        shapeEditColorPopupWindow.setArrowMod(ArrowCardView.ARROW_MODE_UP);
        shapeEditColorPopupWindow.showAsDropDown(getContentView(), 0, -DisplayUtils.dip2px(this.mContext, 26.0f), 0);
    }

    private void checkForbidden() {
        if (ShapeEditSet.getCurShape().getStrokeSize() == 0.0f || ShapeEditSet.getCurShape().getStrokeAlpha() == 0) {
            this.mStrokeColor.setColor(Color.parseColor("#FFB3B3B3"));
            this.mStrokeColor.setShowForbiden(true);
        } else {
            this.mStrokeColor.setShowForbiden(false);
            this.mStrokeColor.setColor(ShapeEditSet.getCurShape().getStrokeColor());
        }
        if (ShapeEditSet.getCurShape().getFillAlpha() == 0.0f) {
            this.mFillColor.setColor(-1);
            this.mFillColor.setShowForbiden(true);
        }
    }

    private void createFillColorPopupWindow() {
        this.mFillColorPopupWindow = new ShapeEditColorPopupWindow(this.mContext, new OnActionClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.7
            @Override // com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.OnActionClickListener
            public void onActionClick(int i) {
                if (ShapeEditPopupWindow.this.actionClickListener != null) {
                    ShapeEditPopupWindow.this.actionClickListener.onActionClick(i);
                }
                if (ShapeEditSet.getCurShape().getFillColor() == 0 || Color.alpha(ShapeEditSet.getCurShape().getFillColor()) == 0) {
                    ShapeEditPopupWindow.this.mFillColor.setColor(-1);
                    ShapeEditPopupWindow.this.mFillColor.setShowForbiden(true);
                } else {
                    ShapeEditPopupWindow.this.mFillColor.setShowForbiden(false);
                    ShapeEditPopupWindow.this.mFillColor.setColor(ShapeEditSet.getCurShape().getFillColor());
                }
            }
        }, false);
    }

    private void createStrokeColorPopupWindow() {
        this.mStrokeColorPopupWindow = new ShapeEditColorPopupWindow(this.mContext, new OnActionClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.8
            @Override // com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.OnActionClickListener
            public void onActionClick(int i) {
                if (ShapeEditPopupWindow.this.actionClickListener != null) {
                    ShapeEditPopupWindow.this.actionClickListener.onActionClick(i);
                }
                if (ShapeEditSet.getCurShape().getStrokeAlpha() == 0 || ShapeEditSet.getCurShape().getStrokeSize() == 0.0f) {
                    ShapeEditPopupWindow.this.mStrokeColor.setColor(Color.parseColor("#FFB3B3B3"));
                    ShapeEditPopupWindow.this.mStrokeColor.setShowForbiden(true);
                } else {
                    ShapeEditPopupWindow.this.mStrokeColor.setShowForbiden(false);
                    ShapeEditPopupWindow.this.mStrokeColor.setColor(ShapeEditSet.getCurShape().getStrokeColor());
                }
            }
        }, true);
    }
}
