package xc.richtext.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleSupplier;
import xc.richtext.EngineBindContext;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.api.SpannedEditLayout;
import xc.richtext.api.SpannedLayoutInterface;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.operate.edit.ISpannedEditOperator;
import xc.richtext.operate.edit.SpannedEditObject;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.operate.scale.IScaleOperator;
import xc.richtext.operate.scale.ScaleParams;
import xc.richtext.operate.step.ISpannedStep;
import xc.richtext.operate.step.ITextChangeStep;
import xc.richtext.operate.step.LocalStepManager;
import xc.richtext.utils.CsUtil;
import xc.richtext.utils.ScreenRenderUtil;
import xc.richtext.view.img.CsImageDrawModel;
import xc.richtext.view.img.CsImageView;
import xc.richtext.view.text.CsEditText;
import xc.richtext.view.text.CsEditViewModel;
import xc.richtext.view.text.EditTextBindContext;

/* JADX INFO: loaded from: classes3.dex */
public class SpannedLayoutModel {
    private static final String DOWNLOAD_IMG_HANDWRITE_ACTION = "com.miui.notes.handwrite.downloadImg";
    private static final String TAG = "SpannedLayoutModel";
    public boolean g;
    public boolean isEnableEdit;
    private DoubleSupplier mCameraScale;
    private final Context mContext;
    private EngineBindContext mEngineBindContext;
    public SpannedEditLayout mSpannedEditLayout;
    public SpannedEditObject mSpannedEditObject;
    public SpannedEditOperator mSpannedEditOperator;
    public CsSpannedLayoutListener mSpannedLayoutListener;
    public SpannedSelectObject mSpannedSelectObject;
    private ScreenRenderProvider screenRenderProvider;
    public int mFocusId = -1;
    public float mDegrees = 0.0f;
    public final CsImageDrawModel mCsImageDrawModel = new CsImageDrawModel();
    private final Rect mRect = new Rect();
    private final BroadcastReceiver BitmapSyncReceiver = new BroadcastReceiver() { // from class: xc.richtext.view.SpannedLayoutModel.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null || !intent.getAction().equals("com.miui.notes.handwrite.downloadImg")) {
                return;
            }
            String stringExtra = intent.getStringExtra(AITextWidgetStat.TYPE_IMAGE);
            Log.i(SpannedLayoutModel.TAG, String.format("onReceive: download image[%s] finish", stringExtra));
            SpannedLayoutModel.this.mEngineBindContext.mScaleOperator.updateVisibleBlankImage(stringExtra);
        }
    };

    public interface OnSpannedLoadListener {
        void onRefreshImage(int i);

        void onSpannedLoadFinish();
    }

    public SpannedLayoutModel(Context context, SpannedEditLayout spannedEditLayout, ScreenRenderProvider screenRenderProvider) {
        this.screenRenderProvider = screenRenderProvider;
        this.mContext = context;
        this.mSpannedEditLayout = spannedEditLayout;
        registerBroadCast();
    }

    public void setCallBack(SpannedLayoutInterface spannedLayoutInterface) {
        EngineBindContext engineBindContext = this.mEngineBindContext;
        if (engineBindContext != null) {
            engineBindContext.setCallBack(spannedLayoutInterface);
        }
    }

    private void registerBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.miui.notes.handwrite.downloadImg");
        if (Build.VERSION.SDK_INT >= 33) {
            this.mContext.registerReceiver(this.BitmapSyncReceiver, intentFilter, 4);
        } else {
            this.mContext.registerReceiver(this.BitmapSyncReceiver, intentFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadFinish(boolean z) {
        for (SpannedData spannedData : this.mEngineBindContext.getAllDataList()) {
            spannedData.setRatioData(this.mEngineBindContext.getSpannedMap().getDrawRatio());
            ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
            PointF pointF = scaleParams.mTranslatePointF;
            spannedData.mTranslatePointF.set(pointF.x, pointF.y);
            spannedData.mScale = scaleParams.mScaleReal;
        }
        if (z) {
            this.mEngineBindContext.mScaleOperator.updateVisibleList(false);
        }
        CsSpannedLayoutListener csSpannedLayoutListener = this.mSpannedLayoutListener;
        if (csSpannedLayoutListener != null) {
            csSpannedLayoutListener.onLoadFinish(z);
        }
    }

    public void onPageChanged(int i) {
        this.mEngineBindContext.mScaleOperator.updateVisibleImgList();
    }

    public void bindExportImage(int i) {
        List<SpannedData> imageListAll = this.mEngineBindContext.getSpannedMap(i).getImageListAll();
        if (imageListAll.isEmpty()) {
            return;
        }
        this.mEngineBindContext.mScaleOperator.refreshImageList(imageListAll);
    }

    public boolean handleTextUndoRedo(SpannedLayoutModel spannedLayoutModel, int i, boolean z) {
        View childAt;
        CsEditViewModel model;
        int childCount = spannedLayoutModel.mSpannedEditLayout.getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                childAt = null;
                break;
            }
            childAt = spannedLayoutModel.mSpannedEditLayout.getChildAt(i2);
            if (CsUtil.isSpanView(childAt) && ((SpannedData) childAt.getTag()).getId() == i) {
                break;
            }
            i2++;
        }
        if (childAt == null) {
            return false;
        }
        if (!(childAt instanceof CsEditText) || (model = ((CsEditText) childAt).getModel()) == null) {
            return true;
        }
        if (z) {
            handleUndo(model);
            return true;
        }
        handleRedo(model);
        return true;
    }

    private void handleRedo(CsEditViewModel csEditViewModel) {
        csEditViewModel.isIgnoreStep = true;
        LocalStepManager localStepManager = csEditViewModel.mEngineBindContext.getLocalStepManager();
        Editable editableText = csEditViewModel.mCsEditText.getEditableText();
        int size = localStepManager.mRedoSteplist.size();
        if (size > 0) {
            ISpannedStep iSpannedStep = localStepManager.mRedoSteplist.get(size - 1);
            localStepManager.mRedoSteplist.remove(iSpannedStep);
            localStepManager.mUndoStepList.add(iSpannedStep);
            ((ITextChangeStep) iSpannedStep).redo(editableText);
            EngineBindContext engineBindContext = localStepManager.mEngineBindContext;
            SpannedLayoutChanger spannedLayoutChanger = engineBindContext == null ? null : engineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onStepChanged(2, iSpannedStep.getPageIndex());
            }
        }
        csEditViewModel.isIgnoreStep = false;
    }

    private void handleUndo(CsEditViewModel csEditViewModel) {
        csEditViewModel.isIgnoreStep = true;
        LocalStepManager localStepManager = csEditViewModel.mEngineBindContext.getLocalStepManager();
        Editable editableText = csEditViewModel.mCsEditText.getEditableText();
        int size = localStepManager.mUndoStepList.size();
        if (size > 0) {
            ISpannedStep iSpannedStep = localStepManager.mUndoStepList.get(size - 1);
            localStepManager.mUndoStepList.remove(iSpannedStep);
            localStepManager.mRedoSteplist.add(iSpannedStep);
            ((ITextChangeStep) iSpannedStep).undo(editableText);
            EngineBindContext engineBindContext = localStepManager.mEngineBindContext;
            SpannedLayoutChanger spannedLayoutChanger = engineBindContext == null ? null : engineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onStepChanged(2, iSpannedStep.getPageIndex());
            }
        }
        csEditViewModel.isIgnoreStep = false;
    }

    public final void doTextClick(final CsEditText csEditText) {
        this.g = true;
        csEditText.setEnabled(true);
        csEditText.setFocusableInTouchMode(true);
        csEditText.requestFocus();
        csEditText.postDelayed(new Runnable() { // from class: xc.richtext.view.SpannedLayoutModel$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4668lambda$doTextClick$0$xcrichtextviewSpannedLayoutModel(csEditText);
            }
        }, 100L);
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.refreshEditText((SpannedData) csEditText.getTag());
        }
    }

    public final void bringTextToFront(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return;
        }
        bringTextToFront();
    }

    public void create() {
        EngineBindContext engineBindContext = new EngineBindContext(this.mSpannedEditLayout.getContext().getApplicationContext(), this.screenRenderProvider);
        this.mEngineBindContext = engineBindContext;
        engineBindContext.mSpannedEditOperator.mSpannedLayoutChanger = new SpannedLayoutChanger(this, this.screenRenderProvider);
        this.mCsImageDrawModel.mEngineBindContext = this.mEngineBindContext;
        this.mCsImageDrawModel.mSpannedLayoutModel = this;
        this.mSpannedEditOperator = this.mEngineBindContext.mSpannedEditOperator;
        this.mEngineBindContext.mScaleOperator.mCsImageDrawModel = this.mCsImageDrawModel;
        this.mEngineBindContext.getSpannedMap().mSpannedDataLoadCallBack = new SpannedMap.SpannedDataLoadCallBack() { // from class: xc.richtext.view.SpannedLayoutModel$$ExternalSyntheticLambda0
            @Override // xc.richtext.data.SpannedMap.SpannedDataLoadCallBack
            public final void onLoadFinish(boolean z) {
                this.f$0.onLoadFinish(z);
            }
        };
    }

    public SpannedMap getSpannedMap() {
        EngineBindContext engineBindContext = this.mEngineBindContext;
        if (engineBindContext == null) {
            return null;
        }
        return engineBindContext.getSpannedMap();
    }

    public SpannedMap getSpannedMap(int i) {
        Log.d(TAG, "getSpannedMap index:" + i);
        EngineBindContext engineBindContext = this.mEngineBindContext;
        if (engineBindContext == null) {
            return null;
        }
        return engineBindContext.getSpannedMap(i);
    }

    public ISpannedEditOperator getSpannedEditOperator() {
        return this.mEngineBindContext.mSpannedEditOperator;
    }

    public IScaleOperator getScaleOperator() {
        return this.mEngineBindContext.mScaleOperator;
    }

    public void setCsSpannedListener(CsSpannedLayoutListener csSpannedLayoutListener) {
        if (this.mEngineBindContext != null) {
            this.mSpannedLayoutListener = csSpannedLayoutListener;
        }
    }

    public void registerScreenRenderProvider(ScreenRenderProvider screenRenderProvider) {
        Log.i(TAG, "registerScreenRenderProvider");
        ScreenRenderUtil.setProvider(screenRenderProvider);
    }

    public void enableEditMode(boolean z) {
        if (this.mEngineBindContext == null || this.mSpannedEditOperator == null) {
            return;
        }
        this.isEnableEdit = z;
        setChildFocusStatus(z);
        if (z) {
            return;
        }
        this.g = false;
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.enableEditMode();
        }
        this.mSpannedEditLayout.invalidate();
        if (this.mSpannedEditLayout.getFocusedChild() != null) {
            View focusedChild = this.mSpannedEditLayout.getFocusedChild();
            focusedChild.clearFocus();
            CsUtil.closeSoftInput(focusedChild);
        }
        removeOtherEditText(this.mFocusId);
    }

    public void bringInSpanToFront() {
        for (int i = 0; i < this.mSpannedEditLayout.getChildCount(); i++) {
            View childAt = this.mSpannedEditLayout.getChildAt(i);
            if (!CsUtil.isSpanView(childAt) && i != this.mSpannedEditLayout.getChildCount() - 1) {
                this.mSpannedEditLayout.bringChildToFront(childAt);
                return;
            }
        }
    }

    public void setChildFocusStatus(boolean z) {
        for (int i = 0; i < this.mSpannedEditLayout.getChildCount(); i++) {
            if (CsUtil.isSpanView(this.mSpannedEditLayout.getChildAt(i))) {
                this.mSpannedEditLayout.getChildAt(i).setEnabled(z);
                this.mSpannedEditLayout.getChildAt(i).setFocusableInTouchMode(z);
            }
        }
    }

    public final void bringTextToFront() {
        int i = 0;
        for (SpannedData spannedData : this.mEngineBindContext.getAllDataList(11)) {
            if (i < this.mSpannedEditLayout.getChildCount()) {
                View childAt = this.mSpannedEditLayout.getChildAt(i);
                if (CsUtil.isSpanView(childAt) && spannedData.getId() == ((SpannedData) childAt.getTag()).mId) {
                    this.mSpannedEditLayout.bringChildToFront(childAt);
                    return;
                }
                i++;
            }
        }
    }

    public final void bringItemViewToFront(List<SpannedData> list) {
        for (SpannedData spannedData : list) {
            for (int i = 0; i < this.mSpannedEditLayout.getChildCount(); i++) {
                View childAt = this.mSpannedEditLayout.getChildAt(i);
                if ((childAt instanceof CsEditText) && spannedData.getId() == ((SpannedData) childAt.getTag()).mId) {
                    this.mSpannedEditLayout.bringChildToFront(childAt);
                    break;
                }
            }
        }
    }

    public void drawBitmap(Canvas canvas, boolean z) {
        Bitmap bitmap;
        Bitmap bitmap2;
        RectF rectF;
        if (!z || (bitmap = this.mCsImageDrawModel.mBitmap) == null || bitmap.isRecycled()) {
            return;
        }
        this.mCsImageDrawModel.mBitmap.eraseColor(0);
        for (BitmapBean bitmapBean : new ArrayList(this.mCsImageDrawModel.mBitmapBeanList)) {
            if (bitmapBean.mSpannedStatus == 0 && (bitmap2 = bitmapBean.mBitmap) != null && !bitmap2.isRecycled()) {
                try {
                    Bitmap bitmap3 = bitmapBean.mBitmap;
                    RectF rectF2 = bitmapBean.mRectF;
                    if (rectF2 == null || rectF2.isEmpty()) {
                        rectF = bitmapBean.mRectF;
                    } else {
                        rectF = new RectF(bitmapBean.mRectF.left * bitmapBean.mScale, bitmapBean.mRectF.top * bitmapBean.mScale, bitmapBean.mRectF.right * bitmapBean.mScale, bitmapBean.mRectF.bottom * bitmapBean.mScale);
                    }
                    RectF rectF3 = new RectF(0.0f, 0.0f, rectF2.width(), rectF2.height());
                    float f = rectF2.left;
                    float f2 = rectF2.top;
                    float f3 = f - rectF.left;
                    float f4 = f2 - rectF.top;
                    float fWidth = rectF.width() / 2.0f;
                    float fHeight = rectF.height() / 2.0f;
                    canvas.save();
                    if (bitmapBean.mAngle != 0.0f) {
                        canvas.rotate(bitmapBean.mAngle, ((int) f) + fWidth, ((int) f2) + fHeight);
                    }
                    canvas.translate(((int) f) + f3, ((int) f2) + f4);
                    canvas.drawBitmap(bitmap3, (Rect) null, rectF3, this.mCsImageDrawModel.mPaint);
                    canvas.restore();
                } catch (Exception unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showSoftInput, reason: merged with bridge method [inline-methods] */
    public void m4668lambda$doTextClick$0$xcrichtextviewSpannedLayoutModel(CsEditText csEditText) {
        InputMethodManager inputMethodManager = (InputMethodManager) csEditText.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(csEditText, 0);
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        SpannedEditLayout spannedEditLayout = this.mSpannedEditLayout;
        if (spannedEditLayout == null) {
            return;
        }
        int childCount = spannedEditLayout.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = this.mSpannedEditLayout.getChildAt(i5);
            if (CsUtil.isSpanView(childAt)) {
                SpannedData spannedData = (SpannedData) childAt.getTag();
                RectF drawRectF = spannedData.getDrawRectF(this.screenRenderProvider);
                if (childAt instanceof CsImageView) {
                    drawRectF = spannedData.getRotatedBoundingBox(this.screenRenderProvider);
                }
                SpannedEditObject spannedEditObject = this.mSpannedEditObject;
                if (spannedEditObject != null && spannedEditObject.mSpannedData.mId == spannedData.mId && !this.mSpannedEditObject.isOffsetEmpty()) {
                    drawRectF.set(this.mSpannedEditObject.mRectF);
                    this.mRect.set((int) drawRectF.left, (int) drawRectF.top, (int) drawRectF.right, (int) drawRectF.bottom);
                    Rect rect = this.mRect;
                    childAt.layout(rect.left, rect.top, rect.right, rect.bottom);
                }
                SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
                if (spannedSelectObject != null && spannedSelectObject.isContainId(spannedData.mId)) {
                    drawRectF.set(this.mSpannedSelectObject.getRectFById(spannedData.mId));
                }
                this.mRect.set((int) drawRectF.left, (int) drawRectF.top, (int) drawRectF.right, (int) drawRectF.bottom);
                childAt.layout(this.mRect.left, this.mRect.top, this.mRect.right, this.mRect.bottom);
            } else if (z && childAt != null) {
                childAt.layout(0, 0, i3 - i, i4 - i2);
            }
        }
    }

    public final void removeOtherEditText(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mSpannedEditLayout.getChildCount(); i2++) {
            View childAt = this.mSpannedEditLayout.getChildAt(i2);
            if (childAt instanceof CsEditText) {
                SpannedData spannedData = (SpannedData) childAt.getTag();
                if (spannedData.mId != i && TextUtils.isEmpty(spannedData.mText) && spannedData.o) {
                    spannedData.mSpannedStatus = 3;
                    arrayList.add(childAt);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mSpannedEditLayout.removeView((View) it.next());
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01e9  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e6  */
    public final void updateSpannedByIdSingle(int i, boolean z, boolean z2) {
        CsEditText csEditText;
        View childAt;
        int i2 = 0;
        while (true) {
            csEditText = null;
            if (i2 >= this.mSpannedEditLayout.getChildCount()) {
                childAt = null;
                break;
            }
            childAt = this.mSpannedEditLayout.getChildAt(i2);
            if (CsUtil.isSpanView(childAt) && i == ((SpannedData) childAt.getTag()).getId()) {
                break;
            } else {
                i2++;
            }
        }
        float f = 0.0f;
        int i3 = -1;
        if (childAt != null) {
            SpannedData spannedData = (SpannedData) childAt.getTag();
            if (!spannedData.isVisible()) {
                childAt.clearFocus();
                if (this.mSpannedEditObject != null && (childAt instanceof CsEditText) && ((SpannedData) childAt.getTag()).getId() == this.mSpannedEditObject.mSpannedData.mId) {
                    this.mSpannedEditObject.getEngineBindContext().mSpannedEditOperator.exitEditMode();
                }
                this.mSpannedEditLayout.removeView(childAt);
                return;
            }
            spannedData.setRatioData(this.mEngineBindContext.mScaleOperator.getDrawRatio());
            if (CsUtil.isSpanView(childAt)) {
                SpannedData spannedData2 = (SpannedData) childAt.getTag();
                RectF drawRectF = spannedData2.getDrawRectF(this.screenRenderProvider);
                SpannedEditObject spannedEditObject = this.mSpannedEditObject;
                float f2 = 1.0f;
                if (spannedEditObject != null && spannedEditObject.mSpannedData.mId == spannedData2.mId && !this.mSpannedEditObject.isOffsetEmpty()) {
                    drawRectF = this.mSpannedEditObject.mRectF;
                } else {
                    SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
                    if (spannedSelectObject != null && spannedSelectObject.isContainId(spannedData2.mId)) {
                        drawRectF.set(this.mSpannedSelectObject.getRectFById(spannedData2.mId));
                        f2 = this.mSpannedSelectObject.mScale;
                    }
                }
                if (childAt instanceof CsEditText) {
                    CsEditText csEditText2 = (CsEditText) childAt;
                    if (csEditText2.getModel() != null) {
                        CsEditViewModel model = csEditText2.getModel();
                        float realTextSize = spannedData2.getRealTextSize() * f2;
                        int i4 = spannedData2.mTextColor;
                        SpannedEditOperator spannedEditOperator = this.mSpannedEditOperator;
                        if (spannedEditOperator == null || !spannedEditOperator.isDark()) {
                            i3 = i4;
                        } else if (i4 != -16777216 && i4 != -16777216) {
                            if (i4 == -1) {
                                i3 = -16777216;
                            } else {
                                i3 = i4;
                            }
                        }
                        if (realTextSize != model.mCsEditText.getTextSize() && !csEditText2.isFocused()) {
                            model.refreshStaticLayout();
                            model.mCsEditText.setTextSize(0, realTextSize);
                        }
                        if (model.mStaticLayout != null && model.mStaticLayout.getPaint().getColor() != i3) {
                            model.refreshStaticLayout();
                        }
                        model.mCsEditText.setTextColor(i3);
                    }
                }
                if (z) {
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    layoutParams.width = (int) drawRectF.width();
                    layoutParams.height = (int) drawRectF.height();
                    childAt.setLayoutParams(layoutParams);
                }
            }
            if (z2 && (childAt instanceof CsImageView)) {
                CsImageView csImageView = (CsImageView) childAt;
                if (csImageView.getImageViewModel() != null) {
                    csImageView.getImageViewModel().refreshImageView();
                }
            }
            float f3 = spannedData.mDegrees;
            SpannedSelectObject spannedSelectObject2 = this.mSpannedSelectObject;
            if (spannedSelectObject2 != null && spannedSelectObject2.mSpannedDataList != null) {
                Iterator<SpannedData> it = this.mSpannedSelectObject.mSpannedDataList.iterator();
                while (it.hasNext()) {
                    if (it.next().getId() == spannedData.mId) {
                        f = this.mSpannedSelectObject.mDegrees;
                        break;
                    }
                }
            }
            float f4 = f3 + f;
            if (f4 == childAt.getRotation() || (childAt instanceof CsImageView)) {
                return;
            }
            childAt.setRotation(f4);
            return;
        }
        SpannedData spannedData3 = this.mEngineBindContext.getSpannedData(i);
        if (spannedData3 == null || spannedData3.mSpannedStatus != 0) {
            return;
        }
        if (spannedData3.getType() == 11) {
            csEditText = new CsEditText(this.mContext, this.screenRenderProvider);
            csEditText.setEnabled(false);
            CsEditViewModel model2 = csEditText.getModel();
            model2.mEditTextBindContext = new EditTextBindContext(this, csEditText);
            model2.mEngineBindContext = this.mEngineBindContext;
            model2.mSpannedData = spannedData3;
            RectF rectF = spannedData3.mRatioRectF;
            model2.mCsEditText.setLayoutParams(new ViewGroup.LayoutParams((int) rectF.width(), (int) rectF.height()));
            model2.mCsEditText.setTag(spannedData3);
            model2.mCsEditText.setTextSize(0, spannedData3.getRealTextSize());
            int i5 = spannedData3.mTextColor;
            if (!model2.mEngineBindContext.mSpannedEditOperator.isDark()) {
                i3 = i5;
            } else if (i5 != -16777216 && i5 != -16777216) {
                if (i5 == -1) {
                    i3 = -16777216;
                } else {
                    i3 = i5;
                }
            }
            model2.mCsEditText.setTextColor(i3);
            model2.mCsEditText.setBackgroundColor(spannedData3.mBgColor);
            if (!TextUtils.isEmpty(spannedData3.mText)) {
                model2.mCsEditText.setText(spannedData3.mText);
                model2.mCsEditText.setSelection(model2.mCsEditText.getEditableText().length());
            }
            model2.refreshStaticLayout();
            ViewGroup.LayoutParams layoutParams2 = csEditText.getLayoutParams();
            layoutParams2.width = (int) spannedData3.getDrawRectF(this.screenRenderProvider).width();
            layoutParams2.height = (int) spannedData3.getDrawRectF(this.screenRenderProvider).height();
            csEditText.setLayoutParams(layoutParams2);
            if (TextUtils.isEmpty(spannedData3.getText())) {
                csEditText.setText(spannedData3.getText());
            }
            model2.isIgnoreStep = false;
            this.mSpannedEditLayout.addView(csEditText);
        }
        if (csEditText == null || spannedData3.getDegrees() == 0.0f) {
            return;
        }
        csEditText.setRotation(spannedData3.getDegrees());
    }

    public EngineBindContext getEngineBindContext() {
        return this.mEngineBindContext;
    }

    public void setCameraScaleCallback(DoubleSupplier doubleSupplier) {
        this.mCameraScale = doubleSupplier;
    }

    public float getCameraScale() {
        DoubleSupplier doubleSupplier = this.mCameraScale;
        if (doubleSupplier != null) {
            return (float) doubleSupplier.getAsDouble();
        }
        return 0.0f;
    }

    public void registerLoadFinishCallback(final OnSpannedLoadListener onSpannedLoadListener) {
        if (onSpannedLoadListener != null) {
            this.mCsImageDrawModel.registerPreImageLoadCallback(new CsImageDrawModel.OnPreImageLoadCallback() { // from class: xc.richtext.view.SpannedLayoutModel.2
                @Override // xc.richtext.view.img.CsImageDrawModel.OnPreImageLoadCallback
                public void onPreLoadFinish() {
                    onSpannedLoadListener.onSpannedLoadFinish();
                }

                @Override // xc.richtext.view.img.CsImageDrawModel.OnPreImageLoadCallback
                public void onRefreshImage(int i) {
                    onSpannedLoadListener.onRefreshImage(i);
                }
            });
        }
    }

    public void release() {
        this.screenRenderProvider = null;
        EngineBindContext engineBindContext = this.mEngineBindContext;
        if (engineBindContext != null) {
            engineBindContext.release();
        }
        this.mContext.unregisterReceiver(this.BitmapSyncReceiver);
    }
}
