package xc.richtext.view;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.SpannedEditObject;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.operate.scale.ScaleOperator;
import xc.richtext.utils.CsUtil;
import xc.richtext.view.img.CsImageDrawModel;
import xc.richtext.view.text.CsEditText;

/* JADX INFO: loaded from: classes3.dex */
public class SpannedLayoutChanger {
    public SpannedLayoutModel mSpannedLayoutModel;
    public ScreenRenderProvider screenRenderProvider;

    public SpannedLayoutChanger(SpannedLayoutModel spannedLayoutModel, ScreenRenderProvider screenRenderProvider) {
        this.mSpannedLayoutModel = spannedLayoutModel;
        this.screenRenderProvider = screenRenderProvider;
    }

    public void onCopyData(boolean z, List<SpannedData> list) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel == null || spannedLayoutModel.mSpannedLayoutListener == null) {
            return;
        }
        this.mSpannedLayoutModel.mSpannedLayoutListener.onCopyData(z, list);
    }

    public void onCopyData(List<SpannedData> list) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel == null || spannedLayoutModel.mSpannedLayoutListener == null) {
            return;
        }
        this.mSpannedLayoutModel.mSpannedLayoutListener.onCopyData(list);
    }

    public boolean handleTextUndoRedo(int i, boolean z) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        return spannedLayoutModel == null || spannedLayoutModel.handleTextUndoRedo(spannedLayoutModel, i, z);
    }

    public void updateSpannedLayout(boolean z, List<SpannedData> list) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            if (!z) {
                spannedLayoutModel.bringTextToFront();
                this.mSpannedLayoutModel.bringItemViewToFront(list);
            }
            this.mSpannedLayoutModel.mSpannedEditLayout.invalidate();
        }
    }

    public void onSpannedDataChanged(int[] iArr, boolean z, boolean z2) {
        BitmapBean bitmapBean;
        if (this.mSpannedLayoutModel == null || iArr == null) {
            return;
        }
        for (int i : iArr) {
            SpannedData spannedData = this.mSpannedLayoutModel.getEngineBindContext().getSpannedData(i);
            if (spannedData != null && spannedData.getType() == 7) {
                CsImageDrawModel csImageDrawModel = this.mSpannedLayoutModel.mCsImageDrawModel;
                CopyOnWriteArrayList<BitmapBean> copyOnWriteArrayList = csImageDrawModel.mBitmapBeanList;
                int i2 = 0;
                while (true) {
                    if (i2 >= copyOnWriteArrayList.size()) {
                        bitmapBean = null;
                        break;
                    }
                    bitmapBean = copyOnWriteArrayList.get(i2);
                    if (bitmapBean.mId == spannedData.mId) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (bitmapBean != null) {
                    bitmapBean.updateBean(spannedData.getDrawRectF(this.screenRenderProvider), spannedData.mDegrees, spannedData.mSpannedStatus);
                    if (copyOnWriteArrayList.contains(bitmapBean)) {
                        copyOnWriteArrayList.remove(bitmapBean);
                        copyOnWriteArrayList.add(copyOnWriteArrayList.size(), bitmapBean);
                    }
                    if (z2) {
                        csImageDrawModel.refreshBitmap(spannedData);
                    }
                } else {
                    copyOnWriteArrayList.add(new BitmapBean(spannedData.mId, spannedData.mBitmapPath, spannedData.getDrawRectF(this.screenRenderProvider), spannedData.mDegrees, spannedData.mSpannedStatus));
                    csImageDrawModel.refreshBitmap(spannedData);
                }
                if (!copyOnWriteArrayList.isEmpty() && csImageDrawModel.mBitmap == null) {
                    initDrawBitmap(csImageDrawModel);
                }
            } else {
                this.mSpannedLayoutModel.updateSpannedByIdSingle(i, z, z2);
            }
        }
        this.mSpannedLayoutModel.mSpannedEditLayout.invalidate();
        this.mSpannedLayoutModel.bringTextToFront(iArr);
        if (this.mSpannedLayoutModel.mFocusId != -1) {
            for (int i3 = 0; i3 < this.mSpannedLayoutModel.mSpannedEditLayout.getChildCount(); i3++) {
                View childAt = this.mSpannedLayoutModel.mSpannedEditLayout.getChildAt(i3);
                if (CsUtil.isSpanView(childAt) && ((SpannedData) childAt.getTag()).getId() == this.mSpannedLayoutModel.mFocusId) {
                    this.mSpannedLayoutModel.mSpannedEditLayout.bringChildToFront(childAt);
                    break;
                }
            }
        }
        if (iArr.length == 1) {
            int i4 = iArr[0];
            for (int i5 = 0; i5 < this.mSpannedLayoutModel.mSpannedEditLayout.getChildCount(); i5++) {
                View childAt2 = this.mSpannedLayoutModel.mSpannedEditLayout.getChildAt(i5);
                if (childAt2 instanceof CsEditText) {
                    SpannedData spannedData2 = (SpannedData) childAt2.getTag();
                    if (i4 == spannedData2.mId && spannedData2.o && !childAt2.hasFocus()) {
                        CsEditText csEditText = (CsEditText) childAt2;
                        if (csEditText.getModel() != null) {
                            csEditText.getModel().mCsEditText.requestFocus();
                            if (!TextUtils.isEmpty(spannedData2.mText)) {
                                spannedData2.o = false;
                            }
                        }
                    }
                }
            }
        }
        removeNoSpecificInvisibleView(iArr);
    }

    public void updateSpanDataPageIndex(List<SpannedData> list) {
        HashSet hashSet = new HashSet();
        for (SpannedData spannedData : list) {
            int pageIndex = spannedData.getPageIndex();
            int pageIndexByWorldSpacePoint = this.mSpannedLayoutModel.getEngineBindContext().getPageIndexByWorldSpacePoint(spannedData.mRatioRectF.left, spannedData.mRatioRectF.top);
            if (pageIndexByWorldSpacePoint != pageIndex) {
                hashSet.add(Integer.valueOf(pageIndexByWorldSpacePoint));
                hashSet.add(Integer.valueOf(pageIndex));
                this.mSpannedLayoutModel.getEngineBindContext().getSpannedMap(pageIndex).remove(spannedData);
                this.mSpannedLayoutModel.getEngineBindContext().getSpannedMap(pageIndexByWorldSpacePoint).add(spannedData);
                spannedData.setPageIndex(pageIndexByWorldSpacePoint);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            onStepChanged(5, ((Integer) it.next()).intValue());
        }
    }

    private void removeNoSpecificInvisibleView(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mSpannedLayoutModel.mSpannedEditLayout.getChildCount(); i++) {
            View childAt = this.mSpannedLayoutModel.mSpannedEditLayout.getChildAt(i);
            if (CsUtil.isSpanView(childAt)) {
                SpannedData spannedData = (SpannedData) childAt.getTag();
                int length = iArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (spannedData.mId == iArr[i2]) {
                            break;
                        } else {
                            i2++;
                        }
                    } else {
                        if (spannedData.mSpannedStatus != 2) {
                            break;
                        }
                        arrayList.add(childAt);
                        break;
                    }
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mSpannedLayoutModel.mSpannedEditLayout.removeView((View) it.next());
        }
    }

    private void initDrawBitmap(CsImageDrawModel csImageDrawModel) {
        ScaleOperator scaleOperator = csImageDrawModel.mEngineBindContext.mScaleOperator;
        int i = (int) scaleOperator.mWidth;
        int i2 = ((int) scaleOperator.mHeight) - 100;
        if (i <= 0 || i2 <= 0) {
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        if (csImageDrawModel.mBitmap != null && !csImageDrawModel.mBitmap.isRecycled()) {
            csImageDrawModel.mBitmap.isRecycled();
        }
        csImageDrawModel.mBitmap = bitmapCreateBitmap;
        if (bitmapCreateBitmap != null) {
            csImageDrawModel.mCanvas.setBitmap(bitmapCreateBitmap);
        }
    }

    public void onSpannedEdited(ISpannedEditObject iSpannedEditObject) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.mSpannedEditObject = (SpannedEditObject) iSpannedEditObject;
            if (iSpannedEditObject == null) {
                this.mSpannedLayoutModel.g = false;
                ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
                if (screenRenderProvider != null) {
                    screenRenderProvider.refresh();
                }
                View focusedChild = this.mSpannedLayoutModel.mSpannedEditLayout.getFocusedChild();
                if (focusedChild != null && focusedChild.hasFocus()) {
                    focusedChild.clearFocus();
                    CsUtil.closeSoftInput(focusedChild);
                }
                this.mSpannedLayoutModel.removeOtherEditText(-1);
            }
            CsSpannedLayoutListener csSpannedLayoutListener = this.mSpannedLayoutModel.mSpannedLayoutListener;
            if (csSpannedLayoutListener != null) {
                csSpannedLayoutListener.onSpannedEdited(iSpannedEditObject);
            }
        }
    }

    public void onSpannedSelected(SpannedSelectObject spannedSelectObject) {
        onSpannedSelected(spannedSelectObject, false);
    }

    public void onSpannedSelected(SpannedSelectObject spannedSelectObject, boolean z) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.mSpannedSelectObject = spannedSelectObject;
            if (spannedSelectObject != null) {
                List<SpannedData> selectedList = spannedSelectObject.getSelectedList();
                this.mSpannedLayoutModel.bringTextToFront();
                this.mSpannedLayoutModel.bringItemViewToFront(selectedList);
            }
            CsSpannedLayoutListener csSpannedLayoutListener = this.mSpannedLayoutModel.mSpannedLayoutListener;
            if (csSpannedLayoutListener != null) {
                csSpannedLayoutListener.onSpannedSelected(spannedSelectObject, z);
            }
        }
    }

    public void onStepChanged(int i, int i2) {
        onStepChanged(i, i2, true);
    }

    public void onStepChanged(int i, int i2, boolean z) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel == null || spannedLayoutModel.mSpannedLayoutListener == null) {
            return;
        }
        this.mSpannedLayoutModel.mSpannedLayoutListener.onStepChanged(i, i2, z);
    }

    public void refreshTextLayout(int i) {
        View childAt;
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            int childCount = spannedLayoutModel.mSpannedEditLayout.getChildCount();
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    childAt = null;
                    break;
                }
                childAt = this.mSpannedLayoutModel.mSpannedEditLayout.getChildAt(i2);
                if (CsUtil.isSpanView(childAt) && ((SpannedData) childAt.getTag()).getId() == i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (childAt != null) {
                ((CsEditText) childAt).getModel().refreshStaticLayout();
            }
        }
    }
}
