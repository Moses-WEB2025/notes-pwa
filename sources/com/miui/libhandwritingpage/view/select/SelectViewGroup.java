package com.miui.libhandwritingpage.view.select;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.beanSelect.SelectCopyBean;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.PageStepHelper;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.OnSelectModelListener;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.PageSizeConfig;
import xc.engine.utils.TouchEventUtils;
import xc.richtext.api.SpannedEditModel;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.edit.ISpannedEditObject;

/* JADX INFO: loaded from: classes3.dex */
public class SelectViewGroup extends FrameLayout implements OnSelectModelListener {
    private static final String TAG = "SelectViewGroup";
    private final int SELECT_ACTION_ADD;
    private final int SELECT_ACTION_REMOVE;
    private boolean isCopyFromUnLimit;
    public boolean isInSelecting;
    private OnChangedListener mChangedListener;
    private final Context mContext;
    private PointF mCopyCurOffset;
    private List<SpannedData> mCopySpannedDataList;
    private PointF mCopySpannedOffset;
    private PageItemView mCurrentItemView;
    private PointF mDownPoint;
    private boolean mEnableEdit;
    protected boolean mEnablePenOnlyMode;
    private boolean mHasDown;
    private boolean mHasScroll;
    private boolean mIsAiModel;
    private boolean mIsMathAutoSelect;
    private boolean mIsShowPaste;
    public boolean mIsShowSelect;
    private boolean mIsSpannedSelect;
    private SelectView mMultiSelectView;
    private SelectCopyBean mPageCopyData;
    private PageInkModel mPageInkModel;
    public PageItemView mPageItemView;
    private byte[] mPasteInfo;
    private boolean mPendingCancelOnClickOutside;
    private int mSelectCount;
    private boolean mSelectEnableMove;
    private PageTextSelectListener mSelectListener;
    private String mSelectUid;
    private BaseSelectView mSelectView;
    private ShapeSelectView mShapeSelectView;
    private int mShowSelectType;
    public SpannedEditModel mSpannedEditMode;
    private boolean mStatus;
    private long mTotalSavePoint;
    private boolean pasteFromOther;

    public interface OnChangedListener {
        void cancelSoftKeyboard();

        void onCancelSelect();
    }

    public Bitmap getBitmapBySize(boolean z, int i) {
        return null;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onStepChanged(int i, int i2, boolean z) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public float[] getSpanData() {
        BaseSelectView baseSelectView = this.mSelectView;
        return baseSelectView == null ? new float[4] : baseSelectView.getSpanData();
    }

    public void setSelectMenuSize(List<RectF> list, List<RectF> list2) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            ((SelectView) baseSelectView).setSelectMenuSize(list, list2);
        }
    }

    public void setShapeMenuSize(List<RectF> list, RectF rectF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof ShapeSelectView) {
            ((ShapeSelectView) baseSelectView).setShapeMenuSize(list, rectF);
        }
    }

    public SelectViewGroup(Context context, boolean z) {
        super(context);
        this.SELECT_ACTION_REMOVE = 1;
        this.SELECT_ACTION_ADD = 2;
        this.isInSelecting = false;
        this.mSelectEnableMove = true;
        this.mPendingCancelOnClickOutside = false;
        this.mIsMathAutoSelect = false;
        this.mContext = context;
        this.mIsShowSelect = false;
        this.mEnableEdit = false;
        this.mShowSelectType = 0;
        this.mDownPoint = new PointF();
        this.mIsShowPaste = false;
        this.mTotalSavePoint = 0L;
        this.mStatus = false;
        this.mCopySpannedOffset = new PointF();
        this.mCopyCurOffset = new PointF();
        this.mIsSpannedSelect = false;
        this.mSelectCount = -1;
        this.mSelectUid = "";
        this.mHasDown = false;
        this.mHasScroll = false;
        SelectView selectView = new SelectView(getContext());
        this.mMultiSelectView = selectView;
        selectView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.mMultiSelectView.setViewParent(this);
        ShapeSelectView shapeSelectView = new ShapeSelectView(getContext());
        this.mShapeSelectView = shapeSelectView;
        shapeSelectView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.mShapeSelectView.setViewParent(this);
    }

    public void setViewModel(PageItemView pageItemView, EngineConfig engineConfig) {
        this.mCurrentItemView = pageItemView;
        if (pageItemView == null) {
            return;
        }
        PageItemView pageItemView2 = this.mPageItemView;
        if (pageItemView2 != null && pageItemView2 != pageItemView) {
            cancelSelect(false);
            this.mPageItemView.invalidateDrawViewNoPost();
        }
        this.mEnablePenOnlyMode = engineConfig.enablePenOnlyMode;
        this.mIsAiModel = engineConfig.isAiModel;
        this.mPageItemView = pageItemView;
        pageItemView.setOperatedType(this.mIsShowSelect ? 1 : 0);
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.setViewModel(this.mPageItemView, pageItemView.getLayout());
            this.mSelectView.enableSelectedContour = engineConfig.enableSelectedContour;
        }
    }

    public void setSpanned(SpannedEditModel spannedEditModel) {
        SpannedEditModel spannedEditModel2;
        if (spannedEditModel != null && (spannedEditModel2 = this.mSpannedEditMode) != null && spannedEditModel2 != spannedEditModel) {
            spannedEditModel2.getSpannedSelectMode().finishSelect();
            this.mSpannedEditMode.finishEdit();
        }
        this.mSpannedEditMode = spannedEditModel;
        if (spannedEditModel != null) {
            spannedEditModel.getSpannedSelectMode().enableSelectMode(this.mIsShowSelect, this.mIsAiModel);
            this.mSpannedEditMode.enableEditMode(this.mEnableEdit);
            BaseSelectView baseSelectView = this.mSelectView;
            if (baseSelectView != null) {
                baseSelectView.mSpannedMode = this.mSpannedEditMode.getSpannedSelectMode();
            }
        }
        refreshSelectViewCopyData(this.mPageCopyData);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void setInSelect(boolean z) {
        this.mIsShowSelect = z;
        this.mIsSpannedSelect = false;
        this.mShowSelectType = 1;
        if (z) {
            switchView(true);
        } else {
            this.mEnableEdit = false;
        }
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.setSelectEnable(this.mIsShowSelect, this.mIsAiModel);
        }
        PageItemView pageItemView = this.mPageItemView;
        if (pageItemView != null) {
            pageItemView.setOperatedType(this.mIsShowSelect ? 1 : 0);
        }
        SpannedEditModel spannedEditModel = this.mSpannedEditMode;
        if (spannedEditModel != null) {
            spannedEditModel.enableEditMode(this.mEnableEdit);
            this.mSpannedEditMode.getSpannedSelectMode().enableSelectMode(z, this.mIsAiModel);
        }
    }

    public void setAiState(boolean z) {
        this.mMultiSelectView.setSelectEnable(this.mIsShowSelect, z);
        this.mShapeSelectView.setSelectEnable(this.mIsShowSelect, z);
    }

    public void amendSelectView(boolean z) {
        if (z) {
            BaseSelectView baseSelectView = this.mSelectView;
            if (!(baseSelectView instanceof SelectView)) {
                if (baseSelectView != null) {
                    updateView(1);
                }
                SelectView selectView = this.mMultiSelectView;
                SpannedEditModel spannedEditModel = this.mSpannedEditMode;
                selectView.mSpannedMode = spannedEditModel == null ? null : spannedEditModel.getSpannedSelectMode();
                this.mSelectView = this.mMultiSelectView;
                updateView(2);
            }
        } else if (this.mSelectView instanceof SelectView) {
            updateView(1);
        }
        BaseSelectView baseSelectView2 = this.mSelectView;
        if (baseSelectView2 != null) {
            baseSelectView2.setSelectEnable(this.mIsShowSelect, this.mIsAiModel);
            PageItemView pageItemView = this.mCurrentItemView;
            if (pageItemView != null) {
                this.mSelectView.setViewModel(this.mPageItemView, pageItemView.getLayout());
            }
            this.mSelectView.setTextSelectListener(this.mSelectListener);
        }
    }

    public void amendShapeView(boolean z) {
        if (z) {
            BaseSelectView baseSelectView = this.mSelectView;
            if (!(baseSelectView instanceof ShapeSelectView)) {
                if (baseSelectView != null) {
                    updateView(1);
                }
                this.mSelectView = this.mShapeSelectView;
                updateView(2);
            }
        } else if (this.mSelectView instanceof ShapeSelectView) {
            updateView(1);
        }
        BaseSelectView baseSelectView2 = this.mSelectView;
        if (baseSelectView2 != null) {
            baseSelectView2.setSelectEnable(this.mIsShowSelect, this.mIsAiModel);
            PageItemView pageItemView = this.mCurrentItemView;
            if (pageItemView != null) {
                this.mSelectView.setViewModel(this.mPageItemView, pageItemView.getLayout());
            }
            this.mSelectView.setTextSelectListener(this.mSelectListener);
        }
    }

    public void switchView(boolean z) {
        PageItemView pageItemView = this.mPageItemView;
        if (pageItemView != null) {
            pageItemView.setSearchRectList(true, 1);
        }
        int i = this.mShowSelectType;
        if (i == 1) {
            if (z && !(this.mSelectView instanceof SelectView)) {
                amendSelectView(true);
                return;
            } else {
                if (z || !(this.mSelectView instanceof SelectView)) {
                    return;
                }
                amendSelectView(false);
                return;
            }
        }
        if (i != 2) {
            return;
        }
        ShapeSelectView shapeSelectView = this.mShapeSelectView;
        if (shapeSelectView != null) {
            shapeSelectView.setIsSelect(true);
        }
        if (z && !(this.mSelectView instanceof ShapeSelectView)) {
            amendShapeView(true);
        } else {
            if (z || !(this.mSelectView instanceof ShapeSelectView)) {
                return;
            }
            amendShapeView(false);
        }
    }

    public void enableContourAntLine(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.enableSelectedContour = z;
        }
    }

    private void updateView(int i) {
        BaseSelectView baseSelectView;
        if (i != 1) {
            if (i == 2 && (baseSelectView = this.mSelectView) != null) {
                addView(baseSelectView);
                return;
            }
            return;
        }
        BaseSelectView baseSelectView2 = this.mSelectView;
        if (baseSelectView2 != null) {
            baseSelectView2.cancelSelect();
            this.mSelectView.setSelectEnable(this.mIsShowSelect, this.mIsAiModel);
        }
        removeAllViews();
        this.mSelectView = null;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mSelectEnableMove) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.mDownPoint.x = motionEvent.getX();
            this.mDownPoint.y = motionEvent.getY();
            this.mHasDown = true;
            this.mHasScroll = false;
            PageItemView pageItemView = this.mPageItemView;
            boolean z = pageItemView == null || pageItemView.isSelectReady();
            BaseSelectView baseSelectView = this.mSelectView;
            if (baseSelectView != null && !baseSelectView.isSelectReady()) {
                z = false;
            }
            if (!z) {
                this.mHasDown = false;
                return false;
            }
        }
        if (motionEvent.getPointerCount() == 1 && this.mEnablePenOnlyMode && !this.mHasScroll && motionEvent.getAction() == 2 && (motionEvent.getToolType(0) & 1) != 0) {
            float fAbs = Math.abs(motionEvent.getX() - this.mDownPoint.x);
            float fAbs2 = Math.abs(motionEvent.getY() - this.mDownPoint.y);
            boolean zIsInSelectedAction = isInSelectedAction();
            if (!isInEditAction() && !zIsInSelectedAction && (fAbs > 20.0f || fAbs2 > 20.0f)) {
                cancelSelect(true);
                this.mHasScroll = true;
            }
        }
        if (this.mSelectView == null || this.mPageItemView == null || !this.mHasDown || this.mHasScroll) {
            return false;
        }
        RectF layout = this.mCurrentItemView.getLayout();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(layout.left > 0.0f ? -layout.left : 0.0f, layout.top > 0.0f ? -layout.top : 0.0f);
        return setTouchEvent(motionEvent, motionEventObtain);
    }

    public boolean isInSelectedAction() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            return !baseSelectView.isNeedOperateAntLine();
        }
        if (baseSelectView instanceof ShapeSelectView) {
            return ((ShapeSelectView) baseSelectView).isOnAction();
        }
        return false;
    }

    public boolean isItemSelected() {
        return this.mMultiSelectView.isItemSelected();
    }

    public boolean isDownButton(MotionEvent motionEvent) {
        return this.mShapeSelectView.findSelectTypeBy(motionEvent.getX(), motionEvent.getY()) != 1 || this.mMultiSelectView.isDownButton(motionEvent);
    }

    public boolean isInEditAction() {
        PageItemView pageItemView = this.mPageItemView;
        if (pageItemView != null) {
            return pageItemView.getMode().isInEditAction();
        }
        return false;
    }

    private boolean setTouchEvent(MotionEvent motionEvent, MotionEvent motionEvent2) {
        if (motionEvent.getAction() == 0) {
            this.mStatus = this.mSelectView.mSelectTouchPath == null;
            this.mSelectView.setEnableSpanSelect(true);
            BaseSelectView baseSelectView = this.mSelectView;
            if ((baseSelectView instanceof ShapeSelectView) && baseSelectView.findSelectTypeBy(motionEvent.getX(), motionEvent.getY()) != 1) {
                this.mSelectView.setEnableSpanSelect(false);
            }
            this.mPendingCancelOnClickOutside = false;
            BaseSelectView baseSelectView2 = this.mSelectView;
            if ((baseSelectView2 instanceof SelectView) && baseSelectView2.getType() == 3 && !this.mSelectView.getRectF().isEmpty() && this.mSelectView.findSelectTypeBy(motionEvent.getX(), motionEvent.getY()) == 1) {
                this.mPendingCancelOnClickOutside = true;
            }
        } else if (motionEvent.getAction() == 1 && TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent)) {
            this.mCurrentItemView.updateSpanScaleInfo();
            boolean zIsClickShape = this.mPageItemView.isClickShape(motionEvent2.getX(), motionEvent2.getY());
            boolean zIsOnSpannedData = this.mPageItemView.isOnSpannedData(motionEvent2.getX(), motionEvent2.getY());
            boolean zIsOnTextSpannedData = this.mPageItemView.isOnTextSpannedData(motionEvent2.getX(), motionEvent2.getY(), 11);
            if (this.mSelectView.getRectF().isEmpty()) {
                this.mStatus = false;
                SpannedEditModel spannedEditModel = this.mSpannedEditMode;
                if (spannedEditModel != null && this.mPageItemView != null) {
                    if (!zIsClickShape && !spannedEditModel.isEnableEditTransient() && !zIsOnSpannedData) {
                        isDoPaste(motionEvent);
                    } else {
                        this.mSelectView.setEnableSpannedClick(zIsOnTextSpannedData || !zIsClickShape);
                        this.mSelectView.setEnableClick(!zIsOnTextSpannedData);
                    }
                } else {
                    isDoPaste(motionEvent);
                }
            } else {
                this.mSelectView.setEnableSpannedClick(false);
            }
        }
        SpannedEditModel spannedEditModel2 = this.mSpannedEditMode;
        if (spannedEditModel2 != null && spannedEditModel2.isEnableEditTransient()) {
            int editAction = motionEvent.getAction() == 1 ? this.mSpannedEditMode.getEditAction() : 0;
            this.mSpannedEditMode.setDispatchTouchEvent(motionEvent2);
            if (this.mSpannedEditMode != null && motionEvent.getAction() == 1) {
                RectF editRect = this.mSpannedEditMode.getEditRect();
                RectF layout = this.mCurrentItemView.getLayout();
                float f = this.mDownPoint.x + (layout.left > 0.0f ? -layout.left : 0.0f);
                float f2 = this.mDownPoint.y + (layout.top > 0.0f ? -layout.top : 0.0f);
                boolean z = editAction != 0;
                if (!editRect.contains(f, f2) && !z) {
                    OnChangedListener onChangedListener = this.mChangedListener;
                    if (onChangedListener != null) {
                        onChangedListener.cancelSoftKeyboard();
                    }
                    this.mSpannedEditMode.finishEdit();
                    this.mPageItemView.getCreationEngine().getCurveEngine().getTextSystem().markDirty();
                    this.mPageItemView.getCreationEngine().getCurveEngine().requestMultiBufferRender(MiCurveEngine.ERefreshReason.Force);
                    BaseSelectView baseSelectView3 = this.mSelectView;
                    if (baseSelectView3 != null) {
                        baseSelectView3.cancelSelectAction(0.0f, 0.0f);
                    }
                }
            }
            return true;
        }
        this.mSelectView.setTouchEvent(motionEvent);
        SpannedEditModel spannedEditModel3 = this.mSpannedEditMode;
        if (spannedEditModel3 != null && !this.mPendingCancelOnClickOutside) {
            spannedEditModel3.getSpannedSelectMode().onTouchEvent(motionEvent2);
        }
        SpannedEditModel spannedEditModel4 = this.mSpannedEditMode;
        if (spannedEditModel4 != null && (this.mSelectView instanceof SelectView)) {
            this.mPageItemView.setSelectLimit(spannedEditModel4.getSpannedSelectMode().isSpannedSelectLimit(), this.mSpannedEditMode.getSpannedSelectMode().getCurTotalScale());
        } else if (this.mSelectView instanceof ShapeSelectView) {
            this.mPageItemView.setSelectLimit(false, 1.0f);
        }
        if (motionEvent2.getAction() == 2 && (this.mSelectView instanceof SelectView)) {
            this.mSpannedEditMode.getSpannedSelectMode().setLimitTotalScale(this.mPageItemView.getCreationEngine().getSelectOperator().getCurScaleValue(), this.mPageItemView.getCreationEngine().getSelectOperator().checkSelectLimit(this.mSpannedEditMode.getSpannedSelectMode().calCurTotalScale(motionEvent2.getX(), motionEvent2.getY())));
        }
        int type = this.mSelectView.getType();
        this.mPageItemView.setSelectEnable((type == 1 || type == 2) ? false : true);
        boolean z2 = (motionEvent.getAction() == 1 && this.mIsAiModel) ? false : true;
        if (this.mPendingCancelOnClickOutside) {
            if (motionEvent.getAction() == 1) {
                this.mPendingCancelOnClickOutside = false;
            }
            return true;
        }
        return this.mPageItemView.onTouchEventDraw(motionEvent2, z2);
    }

    public void isDoPaste(MotionEvent motionEvent) {
        if (this.mIsShowPaste && this.mSelectListener != null) {
            this.mIsShowPaste = false;
            return;
        }
        boolean zIsClickEvent = TouchEventUtils.isClickEvent(this.mDownPoint, motionEvent);
        this.mIsShowPaste = zIsClickEvent;
        if (zIsClickEvent && this.mCurrentItemView.getLayout().contains(motionEvent.getX(), motionEvent.getY())) {
            this.mSelectListener.onPastePopWindow(motionEvent.getX(), motionEvent.getY(), (this.mPasteInfo == null && this.mCopySpannedDataList == null) ? false : true);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void setSelectStateI(boolean z) {
        this.isInSelecting = z;
        ShapeSelectView shapeSelectView = this.mShapeSelectView;
        if (shapeSelectView == null || !z) {
            return;
        }
        shapeSelectView.setIsSelect(true);
    }

    public void setSelectListener(PageTextSelectListener pageTextSelectListener) {
        this.mSelectListener = pageTextSelectListener;
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.setTextSelectListener(pageTextSelectListener);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list) {
        onObjectSelected(sRectF, list, false);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(final SRectF sRectF, final List<DataCurve> list, final boolean z) {
        if (this.mPageItemView == null) {
            setSelectStateI(false);
        } else {
            CreationPageSDK.with(getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.select.SelectViewGroup$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m871xc0fcd46e(sRectF, list, z);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onObjectSelected$0$com-miui-libhandwritingpage-view-select-SelectViewGroup, reason: not valid java name */
    /* synthetic */ void m871xc0fcd46e(SRectF sRectF, List list, boolean z) {
        if (!sRectF.isRectEmpty()) {
            if (this.mPageItemView.isShapeEditMode() && !this.mIsSpannedSelect) {
                this.mShowSelectType = 2;
            } else {
                this.mShowSelectType = 1;
            }
        } else if (!this.mIsShowSelect && !this.mEnableEdit) {
            this.mSelectView = null;
            updateView(1);
            OnChangedListener onChangedListener = this.mChangedListener;
            if (onChangedListener != null) {
                onChangedListener.onCancelSelect();
            }
            setSelectStateI(false);
            return;
        }
        int i = this.mShowSelectType;
        if (i == 2) {
            this.mShapeSelectView.setTouchPositionList(this.mSelectView.mTouchPoints);
        } else if (i == 1 && !(this.mSelectView instanceof SelectView)) {
            SelectView selectView = this.mMultiSelectView;
            selectView.setTouchPositionList(selectView.mTouchPoints);
        }
        switchView(true);
        if ((this.mSelectView instanceof SelectView) && this.mSelectCount != -1) {
            setSelectCount();
        }
        this.mSelectView.onObjectSelected(sRectF, list, z);
        setSelectStateI(false);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDataCopied(int i, byte[] bArr, int i2) {
        this.mPasteInfo = bArr;
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onDataCopied(i2);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectLimit(int i, float f) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onSelectLimit();
        }
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
        Log.d(TAG, "onPageSizeChanged config " + pageSizeConfig);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCancelSelect() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.cancelSelect();
        }
        this.mIsShowPaste = false;
    }

    public void enableMove(boolean z) {
        this.mSelectEnableMove = true;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPath(float f, float f2, Path path, boolean z) {
        if (this.mSelectView != null) {
            if (this.mIsAiModel) {
                enableMove(false);
            }
            try {
                SelectSystem selectSystem = this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem();
                SelectSystem.SelectMode selectMode = selectSystem.getSelectMode();
                if (this.mSelectEnableMove) {
                    if (selectMode == SelectSystem.SelectMode.Multi && path == null) {
                        return;
                    }
                    Log.d(TAG, "onObjectSelectedPath: ,X:" + f + ",Y:" + f2);
                    this.mSelectView.moveSelectPath(f, f2, path);
                    if (path == null || this.mSelectView.getType() == 3) {
                        return;
                    }
                    selectSystem.getOutlineDrawer().setPath(path, f, f2, z);
                }
            } catch (Exception e) {
                Log.w("SelectViewGroup", "onObjectSelectedPath: Fail", e);
            }
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPathForOutLine(float f, float f2, Path path, boolean z) {
        Log.d(TAG, "onObjectSelectedPathForOutLine: CurrentIsAI:" + this.mIsAiModel);
        SelectSystem.SelectMode selectMode = this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getSelectMode();
        if (this.mSelectView == null || selectMode == SelectSystem.SelectMode.Multi || this.mIsAiModel) {
            return;
        }
        this.mSelectView.moveSelectPath(f, f2, path);
        if (path != null) {
            this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().getOutlineDrawer().setPath(path, f, f2, z);
        }
        Log.d("MathSelect", "onObjectSelectedPathForOutLine currentMode: " + selectMode);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartAction(int i) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onStartAction();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDoingAction(int i, SRectF sRectF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onDoingAction(i, sRectF);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onEndAction(int i) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.setAction(i);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedHashCode(int i) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onSelectedHashCode(i);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartPasteFromCopy() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onStartPasteFromCopy(this.mCopySpannedDataList);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void isPointLimit() {
        if (CreationPageSDK.sErrorCallback != null) {
            CreationPageSDK.sErrorCallback.onError(0);
        }
        cancelSelect(false);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public boolean isPagePointLimit(int i) {
        if (((long) i) + this.mTotalSavePoint > 20000000) {
            cancelSelect(false);
            if (CreationPageSDK.sErrorCallback != null) {
                CreationPageSDK.sErrorCallback.onError(1);
            }
        }
        return false;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void finishSelect() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null && (baseSelectView instanceof ShapeSelectView)) {
            baseSelectView.finishSelect();
        }
        this.mIsShowPaste = false;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCanvasChanged() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.postInvalidate();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedDeletedEnd() {
        PageTextSelectListener pageTextSelectListener = this.mSelectListener;
        if (pageTextSelectListener != null) {
            pageTextSelectListener.onDeleteData();
        }
    }

    public void onSpannedSelected(RectF rectF) {
        onSpannedSelected(rectF, false);
    }

    public void onSpannedSelected(RectF rectF, boolean z) {
        boolean zIsEmpty = rectF.isEmpty();
        this.mIsSpannedSelect = !zIsEmpty;
        if (!zIsEmpty) {
            this.mShowSelectType = 1;
            if (this.mIsShowSelect || this.mEnableEdit) {
                switchView(true);
                BaseSelectView baseSelectView = this.mSelectView;
                if (baseSelectView == null || !(baseSelectView instanceof SelectView)) {
                    return;
                }
                if (this.mSelectCount != -1) {
                    setSelectCount();
                }
                ((SelectView) this.mSelectView).onSpannedSelected(rectF, z);
                return;
            }
            this.mPageItemView.cancelSelect(false);
            return;
        }
        BaseSelectView baseSelectView2 = this.mSelectView;
        if (baseSelectView2 != null && (baseSelectView2 instanceof SelectView)) {
            ((SelectView) baseSelectView2).cancelSelects(z);
        }
        int i = this.mSelectCount;
        if (i == 2) {
            this.mSelectCount = i - 1;
            setSelectCount();
        }
    }

    public void onFinishSelect() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.finishSelect();
        }
        this.mIsShowPaste = false;
        this.mIsSpannedSelect = false;
        if (this.mIsMathAutoSelect) {
            Log.w(TAG, "onFinishSelect: mIsMathAutoSelect");
            this.mIsMathAutoSelect = false;
        }
    }

    private void setSelectCount() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null || !(baseSelectView instanceof SelectView)) {
            return;
        }
        ((SelectView) baseSelectView).onSelectCount(this.mSelectCount, this.mSelectUid);
        this.mSelectCount = -1;
        this.mSelectUid = "";
    }

    public void onSelectedClick() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onSelectedClick();
        }
    }

    public void onCopyData(List<SpannedData> list) {
        this.mCopySpannedDataList = list;
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            ((SelectView) baseSelectView).onSpannedCopyData(list);
        }
    }

    public void onCopyData(boolean z, List<SpannedData> list) {
        this.mCopySpannedDataList = list;
        this.isCopyFromUnLimit = z;
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            ((SelectView) baseSelectView).onSpannedCopyData(list);
        }
    }

    public void onStartAction() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onStartAction();
        }
    }

    public void cancelSelectAction(float f, float f2) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.cancelSelectAction(f, f2);
        }
    }

    public void cancelSoftKeyboard() {
        OnChangedListener onChangedListener = this.mChangedListener;
        if (onChangedListener != null) {
            onChangedListener.cancelSoftKeyboard();
        }
    }

    public void onSelectCount(int i, String str) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null && (baseSelectView instanceof SelectView)) {
            ((SelectView) baseSelectView).onSelectCount(i, str);
        }
        this.mSelectCount = i;
        this.mSelectUid = str;
    }

    public void onSpannedEdited(ISpannedEditObject iSpannedEditObject) {
        PageTextSelectListener pageTextSelectListener;
        if (iSpannedEditObject != null || (pageTextSelectListener = this.mSelectListener) == null) {
            return;
        }
        pageTextSelectListener.onExitTextEdit();
    }

    public void cancelSelect(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null && this.mPageItemView != null) {
            if (baseSelectView.isSelectDouble()) {
                this.mSelectView.onStepCount(2);
            }
            this.mSelectView.spannedCancel();
            this.mPageItemView.cancelSelect(z);
            this.mSelectView.cancelSelect();
        }
        SpannedEditModel spannedEditModel = this.mSpannedEditMode;
        if (spannedEditModel != null) {
            spannedEditModel.getSpannedSelectMode().finishSelect();
            this.mSpannedEditMode.finishEdit();
        }
        this.mIsShowPaste = false;
    }

    public void destroy() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.destroy();
        }
    }

    public void setStepManager(PageStepHelper pageStepHelper) {
        this.mMultiSelectView.setStepHelper(pageStepHelper);
    }

    public void onPaste(float f, float f2) {
        if (this.mPageCopyData != null) {
            setCopySelectData(this.mPasteInfo, this.mCopySpannedDataList);
        }
        if (this.mPageItemView.isSelectReady()) {
            this.mIsShowPaste = false;
            BaseSelectView baseSelectView = this.mSelectView;
            if (baseSelectView != null) {
                if (baseSelectView.isPastLimit(f, f2, this.mPasteInfo, this.mCopySpannedDataList)) {
                    cancelSelect(false);
                    return;
                }
                if ((this.mSelectView instanceof ShapeSelectView) && this.mCopySpannedDataList != null) {
                    int i = 1;
                    this.mShowSelectType = 1;
                    switchView(true);
                    byte[] bArr = this.mPasteInfo;
                    if (bArr != null || this.mCopySpannedDataList != null) {
                        if (bArr != null && this.mCopySpannedDataList != null) {
                            i = 2;
                        }
                        this.mSelectCount = i;
                    }
                }
                this.mSelectView.onPaste(f, f2, this.mPasteInfo, this.mCopySpannedDataList, this.isCopyFromUnLimit);
            }
        }
    }

    public void onCopy(boolean z) {
        this.mIsShowPaste = false;
        clearPageCopyData();
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            this.pasteFromOther = false;
            baseSelectView.onCopy(z);
            this.mSelectView.getSelectedOffset(this.mCopySpannedOffset, this.mCopyCurOffset);
        }
    }

    public void onCopyPaste(boolean z) {
        this.mIsShowPaste = false;
        clearPageCopyData();
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            this.pasteFromOther = false;
            baseSelectView.onCopyPaste(z);
            this.mSelectView.getSelectedOffset(this.mCopySpannedOffset, this.mCopyCurOffset);
        }
    }

    public void onCut(boolean z) {
        this.mIsShowPaste = false;
        clearPageCopyData();
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            this.pasteFromOther = false;
            baseSelectView.onCut(z);
            this.mSelectView.getSelectedOffset(this.mCopySpannedOffset, this.mCopyCurOffset);
        }
    }

    public void onCrop() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onCrop();
        }
    }

    public void onCropFinish() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onCropFinish();
        }
    }

    public void onCropCancel() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onCropCancel();
        }
    }

    public void setEdit(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            ((SelectView) baseSelectView).setSelectState(z);
        }
        this.mPageItemView.getCreationEngine().getCurveEngine().getSelectSystem().setEdit();
    }

    public void setCopySelectData(byte[] bArr, List<SpannedData> list) {
        if (list != null) {
            onCopyData(list);
        }
        if (bArr != null) {
            onDataCopied(0, bArr, 0);
        }
    }

    public int getCount() {
        SelectView selectView = this.mMultiSelectView;
        if (selectView == null) {
            return 0;
        }
        return selectView.getCount();
    }

    public void setTotalPoint(long j) {
        this.mTotalSavePoint = j;
    }

    public SelectCopyBean getCopyData() {
        if (this.mPasteInfo == null && this.mCopySpannedDataList == null) {
            return null;
        }
        List<SpannedData> list = this.mCopySpannedDataList;
        SelectCopyBean selectCopyBean = new SelectCopyBean(this.mPasteInfo, list != null ? this.mPageItemView.toBufferBean(list) : null);
        selectCopyBean.setSelectedOffset(this.mCopySpannedOffset, this.mCopyCurOffset);
        return selectCopyBean;
    }

    public void setCopyData(SelectCopyBean selectCopyBean) {
        this.mPageCopyData = selectCopyBean;
        refreshSelectViewCopyData(selectCopyBean);
    }

    private void refreshSelectViewCopyData(SelectCopyBean selectCopyBean) {
        if (selectCopyBean != null) {
            if (selectCopyBean.getCurPasteInfo() == null && selectCopyBean.getBufferBean() == null) {
                return;
            }
            IBufferBean bufferBean = selectCopyBean.getBufferBean();
            if (bufferBean != null && this.mSpannedEditMode != null) {
                this.mCopySpannedDataList = this.mPageItemView.fromBufferBean(bufferBean);
            }
            this.mPasteInfo = selectCopyBean.getCurPasteInfo();
            this.pasteFromOther = true;
            BaseSelectView baseSelectView = this.mSelectView;
            if (baseSelectView != null) {
                baseSelectView.setSelectedOffset(selectCopyBean.getCopySpannedOffset(), selectCopyBean.getCopyCurOffset());
            }
            if (selectCopyBean.getCopySpannedOffset() != null) {
                this.mCopySpannedOffset.set(selectCopyBean.getCopySpannedOffset());
            }
            if (selectCopyBean.getCopyCurOffset() != null) {
                this.mCopyCurOffset.set(selectCopyBean.getCopyCurOffset());
            }
        }
    }

    private void clearPageCopyData() {
        SelectCopyBean selectCopyBean = this.mPageCopyData;
        if (selectCopyBean != null) {
            selectCopyBean.clear();
            this.mPageCopyData = null;
        }
        this.mPasteInfo = null;
        this.mCopySpannedDataList = null;
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof SelectView) {
            ((SelectView) baseSelectView).clearSpannedCopyData();
        }
    }

    public ShapeProp getShapeData() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof ShapeSelectView) {
            return ((ShapeSelectView) baseSelectView).getShapeData();
        }
        return null;
    }

    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView instanceof ShapeSelectView) {
            ((ShapeSelectView) baseSelectView).setSelectShapeAttr(shapeEditAttr, z);
        }
    }

    public List<TouchPosition> getTouchPositionList() {
        BaseSelectView baseSelectView = this.mSelectView;
        return baseSelectView == null ? new ArrayList() : baseSelectView.getTouchPositionList();
    }

    public List<SpannedData> getAllSpanByRectF(RectF rectF) {
        BaseSelectView baseSelectView = this.mSelectView;
        return baseSelectView == null ? new ArrayList() : baseSelectView.getAllSpanByRectF(rectF);
    }

    public void toDrawPointF(PointF pointF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.toDrawPointF(pointF);
        }
    }

    public void toDrawReactF(RectF rectF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.toDrawReactFIgnoreScale(rectF);
        }
    }

    public RectF toOriginRectF(RectF rectF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            return baseSelectView.toOriginRectF(rectF);
        }
        return null;
    }

    public void toOriginPointFIgnoreScale(PointF pointF) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.toOriginPointFIgnoreScale(pointF);
        }
    }

    public Bitmap getBitmap(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getBitmap(z);
    }

    public Bitmap getBitmap(boolean z, boolean z2) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getBitmap(z, z2);
    }

    public CanvasMaskEntity getBitmapAndMask(boolean z, boolean z2, float f, float f2, int i) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getBitMapAndMask(z, z2, f, f2, i);
    }

    public boolean isFromAi() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return false;
        }
        return baseSelectView.isFromAi();
    }

    public List<SpannedData> getAllSelectImageSpanned() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getAllSelectImageSpanned();
    }

    public List<SpannedData> getAllFromAISpanned() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getAllFromAISpanned();
    }

    public List<SpannedData> getAllFromInsertSpanned() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getAllFromInsertSpanned();
    }

    public String getAiPath() {
        BaseSelectView baseSelectView = this.mSelectView;
        return baseSelectView == null ? "false" : baseSelectView.getAiPath();
    }

    public SpannedData getClickImage() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView == null) {
            return null;
        }
        return baseSelectView.getClickImage();
    }

    public void invalidateFromDrawView() {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.invalidate();
        }
    }

    public void replaceView(List<TouchPosition> list) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.replaceView(list);
        }
    }

    public void setKeyboardChangedListener(OnChangedListener onChangedListener) {
        this.mChangedListener = onChangedListener;
    }

    public void setViewModel(PageInkModel pageInkModel) {
        this.mPageInkModel = pageInkModel;
        SelectView selectView = this.mMultiSelectView;
        if (selectView != null) {
            selectView.setViewModel(pageInkModel);
        }
        ShapeSelectView shapeSelectView = this.mShapeSelectView;
        if (shapeSelectView != null) {
            shapeSelectView.setViewModel(pageInkModel);
        }
    }

    public BaseSelectView getSelectView() {
        return this.mSelectView;
    }

    public void setButtonVisible(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.mIsShowButton = z;
            this.mSelectView.invalidate();
            this.mSelectView.postInvalidate();
            invalidate();
            Log.d("MathSelect", "setButtonVisible: invalidate&&postInvalidate");
            return;
        }
        Log.w("MathSelect", "setButtonVisible: mSelectView null");
    }

    public boolean isPasteFromOther() {
        return this.pasteFromOther;
    }

    public void onDelete(boolean z) {
        BaseSelectView baseSelectView = this.mSelectView;
        if (baseSelectView != null) {
            baseSelectView.onDelete(z);
        }
    }

    public void setMathAutoSelect(boolean z) {
        this.mIsMathAutoSelect = z;
        Log.d(TAG, "setMathAutoSelect: " + z);
    }
}
