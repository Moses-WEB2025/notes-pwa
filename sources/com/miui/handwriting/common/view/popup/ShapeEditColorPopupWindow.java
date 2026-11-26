package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.dataset.OperatorSet;
import com.miui.handwriting.common.data.dataset.ShapeEditSet;
import com.miui.handwriting.common.view.ArrowCardView;
import com.miui.libhandwritingpage.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeEditColorPopupWindow extends PopupWindow {
    private ShapeEditPopupWindow.OnActionClickListener actionClickListener;
    private ShapeColorSelectView mColor1;
    private ShapeColorSelectView mColor2;
    private ShapeColorSelectView mColor3;
    private ShapeColorSelectView mColor4;
    private ShapeColorSelectView mColor5;
    private ShapeColorSelectView mColor6;
    private ShapeColorSelectView mColor7;
    private ShapeColorSelectView mColor8;
    private ColorPickerPopupWindow mColorPickerPopupWindow;
    private ArrayList<ShapeColorSelectView> mColorSelectViewList;
    private View mContentView;
    private Context mContext;
    private ShapeColorSelectView mCurrentSelect;
    private boolean mIsStroke;
    private ShapeColorSelectView mNoColor;
    private View.OnClickListener mOnClickListener;
    private View mStrokeSizeContainer;
    private TextView mStrokeSizeNum;
    private SelectPropertyView mStrokeSizeSeekBar;

    public ShapeEditColorPopupWindow(Context context, ShapeEditPopupWindow.OnActionClickListener onActionClickListener, boolean z) {
        super(context);
        this.mIsStroke = false;
        this.mOnClickListener = new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditColorPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ShapeEditColorPopupWindow.this.mCurrentSelect != view) {
                    ShapeEditColorPopupWindow.this.mCurrentSelect = (ShapeColorSelectView) view;
                    int color = ShapeEditColorPopupWindow.this.mCurrentSelect.getColor();
                    int iIndexOf = ShapeEditColorPopupWindow.this.mColorSelectViewList.indexOf(ShapeEditColorPopupWindow.this.mCurrentSelect);
                    if (ShapeEditColorPopupWindow.this.mCurrentSelect == ShapeEditColorPopupWindow.this.mNoColor) {
                        ShapeEditColorPopupWindow.this.mStrokeSizeSeekBar.setEnabled(false);
                        ShapeEditColorPopupWindow.this.mStrokeSizeSeekBar.setProgress(ShapeEditColorPopupWindow.this.mStrokeSizeSeekBar.getMin());
                        color = 0;
                    } else {
                        ShapeEditColorPopupWindow.this.mStrokeSizeSeekBar.setEnabled(true);
                    }
                    ShapeEditColorPopupWindow shapeEditColorPopupWindow = ShapeEditColorPopupWindow.this;
                    shapeEditColorPopupWindow.updateColorView(shapeEditColorPopupWindow.mCurrentSelect);
                    if (ShapeEditColorPopupWindow.this.mIsStroke) {
                        ShapeEditSet.getCurShape().setCheckedStrokeColorIndex(iIndexOf);
                        ShapeEditSet.getCurShape().setStrokeColor(color);
                        ShapeEditSet.getCurShape().setStrokeAlpha(Color.alpha(color));
                        ShapeEditSet.getCurShape().setStrokeSize(ShapeEditColorPopupWindow.this.mStrokeSizeSeekBar.getProgress());
                        ShapeEditSet.getCurShape().setDefaultStrokeColorArray(iIndexOf, color);
                    } else {
                        ShapeEditSet.getCurShape().setCheckedFillColorIndex(iIndexOf);
                        ShapeEditSet.getCurShape().setFillColor(color);
                        ShapeEditSet.getCurShape().setFillAlpha(Color.alpha(color));
                        ShapeEditSet.getCurShape().setDefaultFillColorArray(iIndexOf, color);
                    }
                    if (ShapeEditColorPopupWindow.this.actionClickListener != null) {
                        ShapeEditColorPopupWindow.this.actionClickListener.onActionClick(327680);
                        return;
                    }
                    return;
                }
                if (Utils.isFastClick(view.getId()) || ShapeEditColorPopupWindow.this.mCurrentSelect == ShapeEditColorPopupWindow.this.mNoColor || ShapeEditColorPopupWindow.this.actionClickListener == null) {
                    return;
                }
                ShapeEditColorPopupWindow.this.actionClickListener.onActionClick(OperatorSet.ACTION_SHOW_SHAPE_COLOR_PICKER);
            }
        };
        this.mContext = context;
        this.actionClickListener = onActionClickListener;
        this.mIsStroke = z;
        this.mContentView = View.inflate(context, R.layout.popup_shape_edit_color_arrow, null);
        this.mColorSelectViewList = new ArrayList<>();
        setContentView(this.mContentView);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    public void initView() {
        setAnimationStyle(R.style.window_fade_anim);
        this.mColor1 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_1);
        this.mColor2 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_2);
        this.mColor3 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_3);
        this.mColor4 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_4);
        this.mColor5 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_5);
        this.mColor6 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_6);
        this.mColor7 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_7);
        this.mColor8 = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_8);
        this.mNoColor = (ShapeColorSelectView) this.mContentView.findViewById(R.id.shape_edit_color_9);
        this.mStrokeSizeContainer = this.mContentView.findViewById(R.id.shape_edit_stroke_size_container);
        this.mStrokeSizeNum = (TextView) this.mContentView.findViewById(R.id.shape_edit_stroke_size_num);
        this.mStrokeSizeSeekBar = (SelectPropertyView) this.mContentView.findViewById(R.id.shape_edit_stroke_size_seekbar);
        this.mColorSelectViewList.clear();
        this.mColorSelectViewList.add(this.mColor1);
        this.mColorSelectViewList.add(this.mColor2);
        this.mColorSelectViewList.add(this.mColor3);
        this.mColorSelectViewList.add(this.mColor4);
        this.mColorSelectViewList.add(this.mColor5);
        this.mColorSelectViewList.add(this.mColor6);
        this.mColorSelectViewList.add(this.mColor7);
        this.mColorSelectViewList.add(this.mColor8);
        if (ShapeEditSet.getCurShape().getShapeType() == 2) {
            this.mNoColor.setVisibility(4);
        } else {
            this.mColorSelectViewList.add(this.mNoColor);
        }
        initAccessibility();
        setColorViewListener(this.mOnClickListener);
        checkForbidden();
        if (this.mIsStroke || ShapeEditSet.getCurShape().getShapeType() == 2) {
            Iterator<ShapeColorSelectView> it = this.mColorSelectViewList.iterator();
            while (it.hasNext()) {
                it.next().setMode(2);
            }
            this.mStrokeSizeContainer.setVisibility(0);
            if (ShapeEditSet.getCurShape().getShapeType() == 2) {
                setColorViewDefaultColor(ShapeEditSet.getCurShape().getDefaultFillColorArray());
            } else {
                setColorViewDefaultColor(ShapeEditSet.getCurShape().getDefaultStrokeColorArray());
            }
            this.mStrokeSizeSeekBar.setProgress((int) ShapeEditSet.getCurShape().getStrokeSize());
            this.mStrokeSizeSeekBar.setMax((int) ShapeEditSet.getCurShape().getMaxSize());
            this.mStrokeSizeSeekBar.setMin((int) ShapeEditSet.getCurShape().getMinSize());
            this.mStrokeSizeNum.setText(String.valueOf((int) ShapeEditSet.getCurShape().getStrokeSize()));
            this.mStrokeSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.miui.handwriting.common.view.popup.ShapeEditColorPopupWindow.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    ShapeEditColorPopupWindow.this.mStrokeSizeNum.setText(String.valueOf(i));
                    ShapeEditSet.getCurShape().setStrokeSize(i);
                    if (ShapeEditColorPopupWindow.this.actionClickListener != null) {
                        ShapeEditColorPopupWindow.this.actionClickListener.onActionClick(OperatorSet.ACTION_UPDATE_SHAPE_TEMPORARY);
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (ShapeEditColorPopupWindow.this.actionClickListener != null) {
                        ShapeEditColorPopupWindow.this.actionClickListener.onActionClick(327680);
                    }
                }
            });
        } else {
            this.mStrokeSizeContainer.setVisibility(8);
            setColorViewDefaultColor(ShapeEditSet.getCurShape().getDefaultFillColorArray());
        }
        this.mNoColor.setShowForbiden(true);
        this.mNoColor.setColor(Color.parseColor(this.mIsStroke ? "#FFB3B3B3" : "#FFFFFFFF"));
        this.mNoColor.setMode(this.mIsStroke ? 4 : 0);
    }

    private void setColorViewDefaultColor(int[] iArr) {
        for (int i = 0; i < this.mColorSelectViewList.size(); i++) {
            this.mColorSelectViewList.get(i).setColor(iArr[i]);
        }
    }

    private void setColorViewListener(View.OnClickListener onClickListener) {
        for (int i = 0; i < this.mColorSelectViewList.size(); i++) {
            this.mColorSelectViewList.get(i).setOnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColorView(View view) {
        for (int i = 0; i < this.mColorSelectViewList.size(); i++) {
            if (this.mColorSelectViewList.get(i) == view) {
                this.mColorSelectViewList.get(i).setCheck(true);
            } else {
                this.mColorSelectViewList.get(i).setCheck(false);
            }
        }
    }

    public void setArrowMod(int i) {
        View view = this.mContentView;
        if (view != null) {
            ((ArrowCardView) view).setArrowMode(i);
            this.mContentView.invalidate();
        }
    }

    public void setArrowOffset(int i) {
        View view = this.mContentView;
        if (view != null) {
            ((ArrowCardView) view).setArrowOffset(i);
            this.mContentView.invalidate();
        }
    }

    private void checkForbidden() {
        if (this.mIsStroke) {
            if (ShapeEditSet.getCurShape().getStrokeAlpha() == 0) {
                this.mCurrentSelect = this.mNoColor;
                this.mStrokeSizeSeekBar.setEnabled(false);
                SelectPropertyView selectPropertyView = this.mStrokeSizeSeekBar;
                selectPropertyView.setProgress(selectPropertyView.getMin());
            } else {
                this.mCurrentSelect = this.mColorSelectViewList.get(ShapeEditSet.getCurShape().getCheckedStrokeColorIndex());
            }
        } else if (ShapeEditSet.getCurShape().getFillAlpha() == 0.0f) {
            this.mCurrentSelect = this.mNoColor;
            if (ShapeEditSet.getCurShape().getShapeType() == 2) {
                this.mStrokeSizeSeekBar.setEnabled(false);
                SelectPropertyView selectPropertyView2 = this.mStrokeSizeSeekBar;
                selectPropertyView2.setProgress(selectPropertyView2.getMin());
            } else {
                this.mStrokeSizeSeekBar.setEnabled(true);
            }
        } else {
            this.mCurrentSelect = this.mColorSelectViewList.get(ShapeEditSet.getCurShape().getCheckedFillColorIndex());
        }
        this.mCurrentSelect.setCheck(true);
    }

    public void dismissImmediately() {
        setAnimationStyle(android.R.style.Animation);
        update();
        View view = this.mContentView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeEditColorPopupWindow.3
                @Override // java.lang.Runnable
                public void run() {
                    ShapeEditColorPopupWindow.this.dismiss();
                }
            });
        } else {
            dismiss();
        }
    }

    public void initAccessibility() {
        for (int i = 0; i < this.mColorSelectViewList.size(); i++) {
            if (i == this.mColorSelectViewList.size() - 1) {
                this.mColorSelectViewList.get(i).setContentDescription(this.mContext.getResources().getString(R.string.creation_no_shape_talkback));
            } else {
                this.mColorSelectViewList.get(i).setContentDescription(this.mContext.getString(R.string.color_pick_board_talkback) + this.mContext.getString(R.string.color_pick_talkback, Integer.valueOf((i / 5) + 1), Integer.valueOf((i % 5) + 1)));
            }
        }
    }
}
