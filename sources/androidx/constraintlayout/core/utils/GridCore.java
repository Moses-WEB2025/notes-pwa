package androidx.constraintlayout.core.utils;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import com.miui.maml.folme.AnimatedProperty;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class GridCore extends VirtualLayout {
    private static final int DEFAULT_SIZE = 3;
    public static final int HORIZONTAL = 0;
    private static final int MAX_COLUMNS = 50;
    private static final int MAX_ROWS = 50;
    public static final int SPANS_RESPECT_WIDGET_ORDER = 2;
    public static final int SUB_GRID_BY_COL_ROW = 1;
    public static final int VERTICAL = 1;
    private ConstraintWidget[] mBoxWidgets;
    private String mColumnWeights;
    private int mColumns;
    private int mColumnsSet;
    private int[][] mConstraintMatrix;
    ConstraintWidgetContainer mContainer;
    private int mFlags;
    private float mHorizontalGaps;
    private int mOrientation;
    private boolean[][] mPositionMatrix;
    private String mRowWeights;
    private int mRows;
    private int mRowsSet;
    private String mSkips;
    private int[][] mSpanMatrix;
    private String mSpans;
    private float mVerticalGaps;
    private boolean mExtraSpaceHandled = false;
    private int mNextAvailableIndex = 0;
    Set<String> mSpanIds = new HashSet();
    private int mSpanIndex = 0;

    public GridCore() {
        updateActualRowsAndColumns();
        initMatrices();
    }

    public GridCore(int i, int i2) {
        this.mRowsSet = i;
        this.mColumnsSet = i2;
        if (i > 50) {
            this.mRowsSet = 3;
        }
        if (i2 > 50) {
            this.mColumnsSet = 3;
        }
        updateActualRowsAndColumns();
        initMatrices();
    }

    public ConstraintWidgetContainer getContainer() {
        return this.mContainer;
    }

    public void setContainer(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mContainer = constraintWidgetContainer;
    }

    public void setSpans(CharSequence charSequence) {
        String str = this.mSpans;
        if (str == null || !str.equals(charSequence.toString())) {
            this.mExtraSpaceHandled = false;
            this.mSpans = charSequence.toString();
        }
    }

    public void setSkips(String str) {
        String str2 = this.mSkips;
        if (str2 == null || !str2.equals(str)) {
            this.mExtraSpaceHandled = false;
            this.mSkips = str;
        }
    }

    public float getHorizontalGaps() {
        return this.mHorizontalGaps;
    }

    public void setHorizontalGaps(float f) {
        if (f >= 0.0f && this.mHorizontalGaps != f) {
            this.mHorizontalGaps = f;
        }
    }

    public float getVerticalGaps() {
        return this.mVerticalGaps;
    }

    public void setVerticalGaps(float f) {
        if (f >= 0.0f && this.mVerticalGaps != f) {
            this.mVerticalGaps = f;
        }
    }

    public String getRowWeights() {
        return this.mRowWeights;
    }

    public void setRowWeights(String str) {
        String str2 = this.mRowWeights;
        if (str2 == null || !str2.equals(str)) {
            this.mRowWeights = str;
        }
    }

    public String getColumnWeights() {
        return this.mColumnWeights;
    }

    public void setColumnWeights(String str) {
        String str2 = this.mColumnWeights;
        if (str2 == null || !str2.equals(str)) {
            this.mColumnWeights = str;
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        if ((i == 0 || i == 1) && this.mOrientation != i) {
            this.mOrientation = i;
        }
    }

    public void setRows(int i) {
        if (i <= 50 && this.mRowsSet != i) {
            this.mRowsSet = i;
            updateActualRowsAndColumns();
            initVariables();
        }
    }

    public void setColumns(int i) {
        if (i <= 50 && this.mColumnsSet != i) {
            this.mColumnsSet = i;
            updateActualRowsAndColumns();
            initVariables();
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    private void handleSpans(int[][] iArr) {
        if (isSpansRespectWidgetOrder()) {
            return;
        }
        int i = 0;
        while (i < iArr.length) {
            int rowByIndex = this.getRowByIndex(iArr[i][0]);
            int colByIndex = this.getColByIndex(iArr[i][0]);
            int[] iArr2 = iArr[i];
            if (!this.invalidatePositions(rowByIndex, colByIndex, iArr2[1], iArr2[2])) {
                return;
            }
            ConstraintWidget constraintWidget = this.mWidgets[i];
            int[] iArr3 = iArr[i];
            GridCore gridCore = this;
            gridCore.connectWidget(constraintWidget, rowByIndex, colByIndex, iArr3[1], iArr3[2]);
            gridCore.mSpanIds.add(gridCore.mWidgets[i].stringId);
            i++;
            this = gridCore;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0067  */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    private void arrangeWidgets() {
        GridCore gridCore;
        int[][] iArr;
        int i;
        int i2 = 0;
        while (i2 < this.mWidgetsCount) {
            if (this.mSpanIds.contains(this.mWidgets[i2].stringId)) {
                gridCore = this;
            } else {
                int nextPosition = this.getNextPosition();
                int rowByIndex = this.getRowByIndex(nextPosition);
                int colByIndex = this.getColByIndex(nextPosition);
                if (nextPosition == -1) {
                    return;
                }
                if (!this.isSpansRespectWidgetOrder() || (iArr = this.mSpanMatrix) == null || (i = this.mSpanIndex) >= iArr.length) {
                    gridCore = this;
                    gridCore.connectWidget(gridCore.mWidgets[i2], rowByIndex, colByIndex, 1, 1);
                } else {
                    int[] iArr2 = iArr[i];
                    if (iArr2[0] == nextPosition) {
                        this.mPositionMatrix[rowByIndex][colByIndex] = true;
                        if (this.invalidatePositions(rowByIndex, colByIndex, iArr2[1], iArr2[2])) {
                            ConstraintWidget constraintWidget = this.mWidgets[i2];
                            int[] iArr3 = this.mSpanMatrix[this.mSpanIndex];
                            int i3 = iArr3[1];
                            int i4 = iArr3[2];
                            gridCore = this;
                            gridCore.connectWidget(constraintWidget, rowByIndex, colByIndex, i3, i4);
                            gridCore.mSpanIndex++;
                        } else {
                            gridCore = this;
                        }
                    } else {
                        gridCore = this;
                        gridCore.connectWidget(gridCore.mWidgets[i2], rowByIndex, colByIndex, 1, 1);
                    }
                }
            }
            i2++;
            this = gridCore;
        }
    }

    private void setupGrid(boolean z) {
        int[][] spans;
        if (this.mRows < 1 || this.mColumns < 1) {
            return;
        }
        if (z) {
            for (int i = 0; i < this.mPositionMatrix.length; i++) {
                int i2 = 0;
                while (true) {
                    boolean[][] zArr = this.mPositionMatrix;
                    if (i2 < zArr[0].length) {
                        zArr[i][i2] = true;
                        i2++;
                    }
                }
            }
            this.mSpanIds.clear();
        }
        this.mNextAvailableIndex = 0;
        String str = this.mSkips;
        if (str != null && !str.trim().isEmpty() && (spans = parseSpans(this.mSkips, false)) != null) {
            handleSkips(spans);
        }
        String str2 = this.mSpans;
        if (str2 != null && !str2.trim().isEmpty()) {
            this.mSpanMatrix = parseSpans(this.mSpans, true);
        }
        createBoxes();
        int[][] iArr = this.mSpanMatrix;
        if (iArr != null) {
            handleSpans(iArr);
        }
    }

    private int getRowByIndex(int i) {
        if (this.mOrientation == 1) {
            return i % this.mRows;
        }
        return i / this.mColumns;
    }

    private int getColByIndex(int i) {
        if (this.mOrientation == 1) {
            return i / this.mRows;
        }
        return i % this.mColumns;
    }

    private void handleSkips(int[][] iArr) {
        for (int[] iArr2 : iArr) {
            if (!invalidatePositions(getRowByIndex(iArr2[0]), getColByIndex(iArr2[0]), iArr2[1], iArr2[2])) {
                return;
            }
        }
    }

    private boolean invalidatePositions(int i, int i2, int i3, int i4) {
        for (int i5 = i; i5 < i + i3; i5++) {
            for (int i6 = i2; i6 < i2 + i4; i6++) {
                boolean[][] zArr = this.mPositionMatrix;
                if (i5 < zArr.length && i6 < zArr[0].length) {
                    boolean[] zArr2 = zArr[i5];
                    if (zArr2[i6]) {
                        zArr2[i6] = false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private float[] parseWeights(int i, String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        float[] fArr = new float[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 < strArrSplit.length) {
                try {
                    fArr[i2] = Float.parseFloat(strArrSplit[i2]);
                } catch (Exception e) {
                    System.err.println("Error parsing `" + strArrSplit[i2] + "`: " + e.getMessage());
                    fArr[i2] = 1.0f;
                }
            } else {
                fArr[i2] = 1.0f;
            }
        }
        return fArr;
    }

    private int getNextPosition() {
        boolean z = false;
        int i = 0;
        while (!z) {
            i = this.mNextAvailableIndex;
            if (i >= this.mRows * this.mColumns) {
                return -1;
            }
            int rowByIndex = getRowByIndex(i);
            int colByIndex = getColByIndex(this.mNextAvailableIndex);
            boolean[] zArr = this.mPositionMatrix[rowByIndex];
            if (zArr[colByIndex]) {
                zArr[colByIndex] = false;
                z = true;
            }
            this.mNextAvailableIndex++;
        }
        return i;
    }

    private void updateActualRowsAndColumns() {
        int i;
        int i2 = this.mRowsSet;
        if (i2 == 0 || (i = this.mColumnsSet) == 0) {
            int i3 = this.mColumnsSet;
            if (i3 > 0) {
                this.mColumns = i3;
                this.mRows = ((this.mWidgetsCount + this.mColumns) - 1) / this.mColumnsSet;
                return;
            } else {
                if (i2 > 0) {
                    this.mRows = i2;
                    int i4 = this.mWidgetsCount;
                    int i5 = this.mRowsSet;
                    this.mColumns = ((i4 + i5) - 1) / i5;
                    return;
                }
                this.mRows = (int) (Math.sqrt(this.mWidgetsCount) + 1.5d);
                int i6 = this.mWidgetsCount;
                int i7 = this.mRows;
                this.mColumns = ((i6 + i7) - 1) / i7;
                return;
            }
        }
        this.mRows = i2;
        this.mColumns = i;
    }

    private ConstraintWidget makeNewWidget() {
        ConstraintWidget constraintWidget = new ConstraintWidget();
        constraintWidget.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        constraintWidget.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        constraintWidget.stringId = String.valueOf(constraintWidget.hashCode());
        return constraintWidget;
    }

    private void connectWidget(ConstraintWidget constraintWidget, int i, int i2, int i3, int i4) {
        constraintWidget.mLeft.connect(this.mBoxWidgets[i2].mLeft, 0);
        constraintWidget.mTop.connect(this.mBoxWidgets[i].mTop, 0);
        constraintWidget.mRight.connect(this.mBoxWidgets[(i2 + i4) - 1].mRight, 0);
        constraintWidget.mBottom.connect(this.mBoxWidgets[(i + i3) - 1].mBottom, 0);
    }

    private void setBoxWidgetHorizontalChains() {
        int i;
        int iMax = Math.max(this.mRows, this.mColumns);
        ConstraintWidget constraintWidget = this.mBoxWidgets[0];
        float[] weights = parseWeights(this.mColumns, this.mColumnWeights);
        if (this.mColumns == 1) {
            clearHorizontalAttributes(constraintWidget);
            constraintWidget.mLeft.connect(this.mLeft, 0);
            constraintWidget.mRight.connect(this.mRight, 0);
            return;
        }
        int i2 = 0;
        while (true) {
            i = this.mColumns;
            if (i2 >= i) {
                break;
            }
            ConstraintWidget constraintWidget2 = this.mBoxWidgets[i2];
            clearHorizontalAttributes(constraintWidget2);
            if (weights != null) {
                constraintWidget2.setHorizontalWeight(weights[i2]);
            }
            if (i2 > 0) {
                constraintWidget2.mLeft.connect(this.mBoxWidgets[i2 - 1].mRight, 0);
            } else {
                constraintWidget2.mLeft.connect(this.mLeft, 0);
            }
            if (i2 < this.mColumns - 1) {
                constraintWidget2.mRight.connect(this.mBoxWidgets[i2 + 1].mLeft, 0);
            } else {
                constraintWidget2.mRight.connect(this.mRight, 0);
            }
            if (i2 > 0) {
                constraintWidget2.mLeft.mMargin = (int) this.mHorizontalGaps;
            }
            i2++;
        }
        while (i < iMax) {
            ConstraintWidget constraintWidget3 = this.mBoxWidgets[i];
            clearHorizontalAttributes(constraintWidget3);
            constraintWidget3.mLeft.connect(this.mLeft, 0);
            constraintWidget3.mRight.connect(this.mRight, 0);
            i++;
        }
    }

    private void setBoxWidgetVerticalChains() {
        int i;
        int iMax = Math.max(this.mRows, this.mColumns);
        ConstraintWidget constraintWidget = this.mBoxWidgets[0];
        float[] weights = parseWeights(this.mRows, this.mRowWeights);
        if (this.mRows == 1) {
            clearVerticalAttributes(constraintWidget);
            constraintWidget.mTop.connect(this.mTop, 0);
            constraintWidget.mBottom.connect(this.mBottom, 0);
            return;
        }
        int i2 = 0;
        while (true) {
            i = this.mRows;
            if (i2 >= i) {
                break;
            }
            ConstraintWidget constraintWidget2 = this.mBoxWidgets[i2];
            clearVerticalAttributes(constraintWidget2);
            if (weights != null) {
                constraintWidget2.setVerticalWeight(weights[i2]);
            }
            if (i2 > 0) {
                constraintWidget2.mTop.connect(this.mBoxWidgets[i2 - 1].mBottom, 0);
            } else {
                constraintWidget2.mTop.connect(this.mTop, 0);
            }
            if (i2 < this.mRows - 1) {
                constraintWidget2.mBottom.connect(this.mBoxWidgets[i2 + 1].mTop, 0);
            } else {
                constraintWidget2.mBottom.connect(this.mBottom, 0);
            }
            if (i2 > 0) {
                constraintWidget2.mTop.mMargin = (int) this.mVerticalGaps;
            }
            i2++;
        }
        while (i < iMax) {
            ConstraintWidget constraintWidget3 = this.mBoxWidgets[i];
            clearVerticalAttributes(constraintWidget3);
            constraintWidget3.mTop.connect(this.mTop, 0);
            constraintWidget3.mBottom.connect(this.mBottom, 0);
            i++;
        }
    }

    private void addConstraints() {
        setBoxWidgetVerticalChains();
        setBoxWidgetHorizontalChains();
        arrangeWidgets();
    }

    private void createBoxes() {
        int iMax = Math.max(this.mRows, this.mColumns);
        ConstraintWidget[] constraintWidgetArr = this.mBoxWidgets;
        int i = 0;
        if (constraintWidgetArr == null) {
            this.mBoxWidgets = new ConstraintWidget[iMax];
            while (true) {
                ConstraintWidget[] constraintWidgetArr2 = this.mBoxWidgets;
                if (i >= constraintWidgetArr2.length) {
                    return;
                }
                constraintWidgetArr2[i] = makeNewWidget();
                i++;
            }
        } else {
            if (iMax == constraintWidgetArr.length) {
                return;
            }
            ConstraintWidget[] constraintWidgetArr3 = new ConstraintWidget[iMax];
            while (i < iMax) {
                ConstraintWidget[] constraintWidgetArr4 = this.mBoxWidgets;
                if (i < constraintWidgetArr4.length) {
                    constraintWidgetArr3[i] = constraintWidgetArr4[i];
                } else {
                    constraintWidgetArr3[i] = makeNewWidget();
                }
                i++;
            }
            while (true) {
                ConstraintWidget[] constraintWidgetArr5 = this.mBoxWidgets;
                if (iMax < constraintWidgetArr5.length) {
                    this.mContainer.remove(constraintWidgetArr5[iMax]);
                    iMax++;
                } else {
                    this.mBoxWidgets = constraintWidgetArr3;
                    return;
                }
            }
        }
    }

    private void clearVerticalAttributes(ConstraintWidget constraintWidget) {
        constraintWidget.setVerticalWeight(-1.0f);
        constraintWidget.mTop.reset();
        constraintWidget.mBottom.reset();
        constraintWidget.mBaseline.reset();
    }

    private void clearHorizontalAttributes(ConstraintWidget constraintWidget) {
        constraintWidget.setHorizontalWeight(-1.0f);
        constraintWidget.mLeft.reset();
        constraintWidget.mRight.reset();
    }

    private void initVariables() {
        boolean[][] zArr = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, this.mRows, this.mColumns);
        this.mPositionMatrix = zArr;
        for (boolean[] zArr2 : zArr) {
            Arrays.fill(zArr2, true);
        }
        if (this.mWidgetsCount > 0) {
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, this.mWidgetsCount, 4);
            this.mConstraintMatrix = iArr;
            for (int[] iArr2 : iArr) {
                Arrays.fill(iArr2, -1);
            }
        }
    }

    private int[][] parseSpans(String str, boolean z) {
        try {
            String[] strArrSplit = str.split(",");
            Arrays.sort(strArrSplit, new Comparator() { // from class: androidx.constraintlayout.core.utils.GridCore$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return GridCore.lambda$parseSpans$0((String) obj, (String) obj2);
                }
            });
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, strArrSplit.length, 3);
            if (this.mRows != 1 && this.mColumns != 1) {
                for (int i = 0; i < strArrSplit.length; i++) {
                    String[] strArrSplit2 = strArrSplit[i].trim().split(":");
                    String[] strArrSplit3 = strArrSplit2[1].split(AnimatedProperty.PROPERTY_NAME_X);
                    iArr[i][0] = Integer.parseInt(strArrSplit2[0]);
                    if (isSubGridByColRow()) {
                        iArr[i][1] = Integer.parseInt(strArrSplit3[1]);
                        iArr[i][2] = Integer.parseInt(strArrSplit3[0]);
                    } else {
                        iArr[i][1] = Integer.parseInt(strArrSplit3[0]);
                        iArr[i][2] = Integer.parseInt(strArrSplit3[1]);
                    }
                }
                return iArr;
            }
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < strArrSplit.length; i4++) {
                String[] strArrSplit4 = strArrSplit[i4].trim().split(":");
                iArr[i4][0] = Integer.parseInt(strArrSplit4[0]);
                int[] iArr2 = iArr[i4];
                iArr2[1] = 1;
                iArr2[2] = 1;
                if (this.mColumns == 1) {
                    iArr2[1] = Integer.parseInt(strArrSplit4[1]);
                    i2 += iArr[i4][1];
                    if (z) {
                        i2--;
                    }
                }
                if (this.mRows == 1) {
                    iArr[i4][2] = Integer.parseInt(strArrSplit4[1]);
                    i3 += iArr[i4][2];
                    if (z) {
                        i3--;
                    }
                }
            }
            if (i2 != 0 && !this.mExtraSpaceHandled) {
                setRows(this.mRows + i2);
            }
            if (i3 != 0 && !this.mExtraSpaceHandled) {
                setColumns(this.mColumns + i3);
            }
            this.mExtraSpaceHandled = true;
            return iArr;
        } catch (Exception unused) {
            return null;
        }
    }

    static /* synthetic */ int lambda$parseSpans$0(String str, String str2) {
        return Integer.parseInt(str.split(":")[0]) - Integer.parseInt(str2.split(":")[0]);
    }

    private void fillConstraintMatrix(boolean z) {
        int[][] spans;
        int[][] spans2;
        if (z) {
            for (int i = 0; i < this.mPositionMatrix.length; i++) {
                int i2 = 0;
                while (true) {
                    boolean[][] zArr = this.mPositionMatrix;
                    if (i2 < zArr[0].length) {
                        zArr[i][i2] = true;
                        i2++;
                    }
                }
            }
            for (int i3 = 0; i3 < this.mConstraintMatrix.length; i3++) {
                int i4 = 0;
                while (true) {
                    int[][] iArr = this.mConstraintMatrix;
                    if (i4 < iArr[0].length) {
                        iArr[i3][i4] = -1;
                        i4++;
                    }
                }
            }
        }
        this.mNextAvailableIndex = 0;
        String str = this.mSkips;
        if (str != null && !str.trim().isEmpty() && (spans2 = parseSpans(this.mSkips, false)) != null) {
            handleSkips(spans2);
        }
        String str2 = this.mSpans;
        if (str2 == null || str2.trim().isEmpty() || (spans = parseSpans(this.mSpans, true)) == null) {
            return;
        }
        handleSpans(spans);
    }

    private void initMatrices() {
        boolean[][] zArr;
        int[][] iArr = this.mConstraintMatrix;
        boolean z = false;
        if (iArr != null && iArr.length == this.mWidgetsCount && (zArr = this.mPositionMatrix) != null && zArr.length == this.mRows && zArr[0].length == this.mColumns) {
            z = true;
        }
        if (!z) {
            initVariables();
        }
        fillConstraintMatrix(z);
    }

    private boolean isSubGridByColRow() {
        return (this.mFlags & 1) > 0;
    }

    private boolean isSpansRespectWidgetOrder() {
        return (this.mFlags & 2) > 0;
    }

    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    public void measure(int i, int i2, int i3, int i4) {
        super.measure(i, i2, i3, i4);
        this.mContainer = (ConstraintWidgetContainer) getParent();
        setupGrid(false);
        this.mContainer.add(this.mBoxWidgets);
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        super.addToSolver(linearSystem, z);
        addConstraints();
    }
}
