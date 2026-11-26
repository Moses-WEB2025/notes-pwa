package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.miui.creation.common.R;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeSelectPopupWindow extends BaseNormalPopupWindow {
    private ShapeSelectCallBack mActionCallBack;
    private ImageView mArrowBigView;
    private ImageView mArrowView;
    private ImageView mCircleView;
    private View.OnClickListener mClickListener;
    private ImageView mLineView;
    private ImageView mRectView;
    private ImageView mStarView;
    private ImageView mTriangleView;

    public interface ShapeSelectCallBack {
        void call(int i);

        void callBackground(int i, int i2);
    }

    public ShapeSelectPopupWindow(Context context) {
        super(context);
        this.mClickListener = new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeSelectPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ShapeSelectPopupWindow.this.mActionCallBack == null) {
                    return;
                }
                int id = view.getId();
                if (id == R.id.shape_star) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(6);
                    return;
                }
                if (id == R.id.shape_line) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(1);
                    return;
                }
                if (id == R.id.shape_arrow) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(2);
                    return;
                }
                if (id == R.id.shape_arrow_big) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(7);
                    return;
                }
                if (id == R.id.shape_triangle) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(4);
                } else if (id == R.id.shape_rect) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(3);
                } else if (id == R.id.shape_circle) {
                    ShapeSelectPopupWindow.this.mActionCallBack.call(5);
                }
            }
        };
        initLayout(context, R.layout.popup_shape_select);
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initLayout(Context context, int i) {
        super.initLayout(context, i);
    }

    public void setActionCallBack(ShapeSelectCallBack shapeSelectCallBack) {
        this.mActionCallBack = shapeSelectCallBack;
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initView() {
        this.mCircleView = (ImageView) this.mContentView.findViewById(R.id.shape_circle);
        this.mRectView = (ImageView) this.mContentView.findViewById(R.id.shape_rect);
        this.mTriangleView = (ImageView) this.mContentView.findViewById(R.id.shape_triangle);
        this.mLineView = (ImageView) this.mContentView.findViewById(R.id.shape_line);
        this.mArrowView = (ImageView) this.mContentView.findViewById(R.id.shape_arrow);
        this.mArrowBigView = (ImageView) this.mContentView.findViewById(R.id.shape_arrow_big);
        this.mStarView = (ImageView) this.mContentView.findViewById(R.id.shape_star);
        this.mCircleView.setOnClickListener(this.mClickListener);
        this.mRectView.setOnClickListener(this.mClickListener);
        this.mTriangleView.setOnClickListener(this.mClickListener);
        this.mLineView.setOnClickListener(this.mClickListener);
        this.mArrowView.setOnClickListener(this.mClickListener);
        this.mArrowBigView.setOnClickListener(this.mClickListener);
        this.mStarView.setOnClickListener(this.mClickListener);
    }
}
