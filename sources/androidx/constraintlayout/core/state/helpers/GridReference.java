package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.utils.GridCore;
import androidx.constraintlayout.core.widgets.HelperWidget;

/* JADX INFO: loaded from: classes.dex */
public class GridReference extends HelperReference {
    private static final String SPANS_RESPECT_WIDGET_ORDER_STRING = "spansrespectwidgetorder";
    private static final String SUB_GRID_BY_COL_ROW_STRING = "subgridbycolrow";
    private String mColumnWeights;
    private int mColumnsSet;
    private int mFlags;
    private GridCore mGrid;
    private float mHorizontalGaps;
    private int mOrientation;
    private int mPaddingBottom;
    private int mPaddingEnd;
    private int mPaddingStart;
    private int mPaddingTop;
    private String mRowWeights;
    private int mRowsSet;
    private String mSkips;
    private String mSpans;
    private float mVerticalGaps;

    public GridReference(State state, State.Helper helper) {
        super(state, helper);
        this.mPaddingStart = 0;
        this.mPaddingEnd = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        if (helper == State.Helper.ROW) {
            this.mRowsSet = 1;
        } else if (helper == State.Helper.COLUMN) {
            this.mColumnsSet = 1;
        }
    }

    public int getPaddingStart() {
        return this.mPaddingStart;
    }

    public void setPaddingStart(int i) {
        this.mPaddingStart = i;
    }

    public int getPaddingEnd() {
        return this.mPaddingEnd;
    }

    public void setPaddingEnd(int i) {
        this.mPaddingEnd = i;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public void setPaddingTop(int i) {
        this.mPaddingTop = i;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public void setPaddingBottom(int i) {
        this.mPaddingBottom = i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public void setFlags(String str) {
        if (str.isEmpty()) {
            return;
        }
        String[] strArrSplit = str.split("\\|");
        this.mFlags = 0;
        for (String str2 : strArrSplit) {
            String lowerCase = str2.toLowerCase();
            lowerCase.hashCode();
            if (lowerCase.equals(SUB_GRID_BY_COL_ROW_STRING)) {
                this.mFlags |= 1;
            } else if (lowerCase.equals(SPANS_RESPECT_WIDGET_ORDER_STRING)) {
                this.mFlags |= 2;
            }
        }
    }

    public int getRowsSet() {
        return this.mRowsSet;
    }

    public void setRowsSet(int i) {
        if (super.getType() == State.Helper.COLUMN) {
            return;
        }
        this.mRowsSet = i;
    }

    public int getColumnsSet() {
        return this.mColumnsSet;
    }

    public void setColumnsSet(int i) {
        if (super.getType() == State.Helper.ROW) {
            return;
        }
        this.mColumnsSet = i;
    }

    public float getHorizontalGaps() {
        return this.mHorizontalGaps;
    }

    public void setHorizontalGaps(float f) {
        this.mHorizontalGaps = f;
    }

    public float getVerticalGaps() {
        return this.mVerticalGaps;
    }

    public void setVerticalGaps(float f) {
        this.mVerticalGaps = f;
    }

    public String getRowWeights() {
        return this.mRowWeights;
    }

    public void setRowWeights(String str) {
        this.mRowWeights = str;
    }

    public String getColumnWeights() {
        return this.mColumnWeights;
    }

    public void setColumnWeights(String str) {
        this.mColumnWeights = str;
    }

    public String getSpans() {
        return this.mSpans;
    }

    public void setSpans(String str) {
        this.mSpans = str;
    }

    public String getSkips() {
        return this.mSkips;
    }

    public void setSkips(String str) {
        this.mSkips = str;
    }

    @Override // androidx.constraintlayout.core.state.HelperReference
    public HelperWidget getHelperWidget() {
        if (this.mGrid == null) {
            this.mGrid = new GridCore();
        }
        return this.mGrid;
    }

    @Override // androidx.constraintlayout.core.state.HelperReference
    public void setHelperWidget(HelperWidget helperWidget) {
        if (helperWidget instanceof GridCore) {
            this.mGrid = (GridCore) helperWidget;
        } else {
            this.mGrid = null;
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        getHelperWidget();
        this.mGrid.setOrientation(this.mOrientation);
        int i = this.mRowsSet;
        if (i != 0) {
            this.mGrid.setRows(i);
        }
        int i2 = this.mColumnsSet;
        if (i2 != 0) {
            this.mGrid.setColumns(i2);
        }
        float f = this.mHorizontalGaps;
        if (f != 0.0f) {
            this.mGrid.setHorizontalGaps(f);
        }
        float f2 = this.mVerticalGaps;
        if (f2 != 0.0f) {
            this.mGrid.setVerticalGaps(f2);
        }
        String str = this.mRowWeights;
        if (str != null && !str.isEmpty()) {
            this.mGrid.setRowWeights(this.mRowWeights);
        }
        String str2 = this.mColumnWeights;
        if (str2 != null && !str2.isEmpty()) {
            this.mGrid.setColumnWeights(this.mColumnWeights);
        }
        String str3 = this.mSpans;
        if (str3 != null && !str3.isEmpty()) {
            this.mGrid.setSpans(this.mSpans);
        }
        String str4 = this.mSkips;
        if (str4 != null && !str4.isEmpty()) {
            this.mGrid.setSkips(this.mSkips);
        }
        this.mGrid.setFlags(this.mFlags);
        this.mGrid.setPaddingStart(this.mPaddingStart);
        this.mGrid.setPaddingEnd(this.mPaddingEnd);
        this.mGrid.setPaddingTop(this.mPaddingTop);
        this.mGrid.setPaddingBottom(this.mPaddingBottom);
        applyBase();
    }
}
