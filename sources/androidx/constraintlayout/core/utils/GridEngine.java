package androidx.constraintlayout.core.utils;

import com.miui.maml.folme.AnimatedProperty;
import java.lang.reflect.Array;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class GridEngine {
    private static final int DEFAULT_SIZE = 3;
    public static final int HORIZONTAL = 0;
    private static final int MAX_COLUMNS = 50;
    private static final int MAX_ROWS = 50;
    public static final int VERTICAL = 1;
    private int mColumns;
    private int mColumnsSet;
    private int[][] mConstraintMatrix;
    private int mNextAvailableIndex = 0;
    private int mNumWidgets;
    private int mOrientation;
    private boolean[][] mPositionMatrix;
    private int mRows;
    private int mRowsSet;
    private String mStrSkips;
    private String mStrSpans;

    private boolean isSpansValid(CharSequence charSequence) {
        return charSequence != null;
    }

    public GridEngine() {
    }

    public GridEngine(int i, int i2) {
        this.mRowsSet = i;
        this.mColumnsSet = i2;
        if (i > 50) {
            this.mRowsSet = 3;
        }
        if (i2 > 50) {
            this.mColumnsSet = 3;
        }
        updateActualRowsAndColumns();
        initVariables();
    }

    public GridEngine(int i, int i2, int i3) {
        this.mRowsSet = i;
        this.mColumnsSet = i2;
        this.mNumWidgets = i3;
        if (i > 50) {
            this.mRowsSet = 3;
        }
        if (i2 > 50) {
            this.mColumnsSet = 3;
        }
        updateActualRowsAndColumns();
        int i4 = this.mRows;
        int i5 = this.mColumns;
        if (i3 > i4 * i5 || i3 < 1) {
            this.mNumWidgets = i4 * i5;
        }
        initVariables();
        fillConstraintMatrix(false);
    }

    private void initVariables() {
        boolean[][] zArr = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, this.mRows, this.mColumns);
        this.mPositionMatrix = zArr;
        for (boolean[] zArr2 : zArr) {
            Arrays.fill(zArr2, true);
        }
        int i = this.mNumWidgets;
        if (i > 0) {
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i, 4);
            this.mConstraintMatrix = iArr;
            for (int[] iArr2 : iArr) {
                Arrays.fill(iArr2, -1);
            }
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

    private int[][] parseSpans(String str) {
        if (!isSpansValid(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, strArrSplit.length, 3);
        for (int i = 0; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = strArrSplit[i].trim().split(":");
            String[] strArrSplit3 = strArrSplit2[1].split(AnimatedProperty.PROPERTY_NAME_X);
            iArr[i][0] = Integer.parseInt(strArrSplit2[0]);
            iArr[i][1] = Integer.parseInt(strArrSplit3[0]);
            iArr[i][2] = Integer.parseInt(strArrSplit3[1]);
        }
        return iArr;
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
        String str = this.mStrSkips;
        if (str != null && !str.trim().isEmpty() && (spans2 = parseSpans(this.mStrSkips)) != null) {
            handleSkips(spans2);
        }
        String str2 = this.mStrSpans;
        if (str2 != null && !str2.trim().isEmpty() && (spans = parseSpans(this.mStrSpans)) != null) {
            handleSpans(spans);
        }
        addAllConstraintPositions();
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

    private void addConstraintPosition(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.mConstraintMatrix[i];
        iArr[0] = i3;
        iArr[1] = i2;
        iArr[2] = (i3 + i5) - 1;
        iArr[3] = (i2 + i4) - 1;
    }

    private void handleSpans(int[][] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int rowByIndex = getRowByIndex(iArr[i][0]);
            int colByIndex = getColByIndex(iArr[i][0]);
            int[] iArr2 = iArr[i];
            if (!invalidatePositions(rowByIndex, colByIndex, iArr2[1], iArr2[2])) {
                return;
            }
            int[] iArr3 = iArr[i];
            addConstraintPosition(i, rowByIndex, colByIndex, iArr3[1], iArr3[2]);
        }
    }

    private void handleSkips(int[][] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int rowByIndex = getRowByIndex(iArr[i][0]);
            int colByIndex = getColByIndex(iArr[i][0]);
            int[] iArr2 = iArr[i];
            if (!invalidatePositions(rowByIndex, colByIndex, iArr2[1], iArr2[2])) {
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

    private void addAllConstraintPositions() {
        GridEngine gridEngine;
        int i = 0;
        while (i < this.mNumWidgets) {
            if (this.leftOfWidget(i) != -1) {
                gridEngine = this;
            } else {
                int nextPosition = this.getNextPosition();
                int rowByIndex = this.getRowByIndex(nextPosition);
                int colByIndex = this.getColByIndex(nextPosition);
                if (nextPosition == -1) {
                    return;
                }
                gridEngine = this;
                gridEngine.addConstraintPosition(i, rowByIndex, colByIndex, 1, 1);
            }
            i++;
            this = gridEngine;
        }
    }

    private void updateActualRowsAndColumns() {
        int i;
        int i2 = this.mRowsSet;
        if (i2 == 0 || (i = this.mColumnsSet) == 0) {
            int i3 = this.mColumnsSet;
            if (i3 > 0) {
                this.mColumns = i3;
                this.mRows = ((this.mNumWidgets + i3) - 1) / i3;
                return;
            } else if (i2 > 0) {
                this.mRows = i2;
                this.mColumns = ((this.mNumWidgets + i2) - 1) / i2;
                return;
            } else {
                int iSqrt = (int) (Math.sqrt(this.mNumWidgets) + 1.5d);
                this.mRows = iSqrt;
                this.mColumns = ((this.mNumWidgets + iSqrt) - 1) / iSqrt;
                return;
            }
        }
        this.mRows = i2;
        this.mColumns = i;
    }

    public void setup() {
        boolean[][] zArr;
        int[][] iArr = this.mConstraintMatrix;
        boolean z = false;
        if (iArr != null && iArr.length == this.mNumWidgets && (zArr = this.mPositionMatrix) != null && zArr.length == this.mRows && zArr[0].length == this.mColumns) {
            z = true;
        }
        if (!z) {
            initVariables();
        }
        fillConstraintMatrix(z);
    }

    public void setSpans(CharSequence charSequence) {
        String str = this.mStrSpans;
        if (str == null || !str.equals(charSequence.toString())) {
            this.mStrSpans = charSequence.toString();
        }
    }

    public void setSkips(String str) {
        String str2 = this.mStrSkips;
        if (str2 == null || !str2.equals(str)) {
            this.mStrSkips = str;
        }
    }

    public void setOrientation(int i) {
        if ((i == 0 || i == 1) && this.mOrientation != i) {
            this.mOrientation = i;
        }
    }

    public void setNumWidgets(int i) {
        if (i > this.mRows * this.mColumns) {
            return;
        }
        this.mNumWidgets = i;
    }

    public void setRows(int i) {
        if (i <= 50 && this.mRowsSet != i) {
            this.mRowsSet = i;
            updateActualRowsAndColumns();
        }
    }

    public void setColumns(int i) {
        if (i <= 50 && this.mColumnsSet != i) {
            this.mColumnsSet = i;
            updateActualRowsAndColumns();
        }
    }

    public int leftOfWidget(int i) {
        int[][] iArr = this.mConstraintMatrix;
        if (iArr == null || i >= iArr.length) {
            return 0;
        }
        return iArr[i][0];
    }

    public int topOfWidget(int i) {
        int[][] iArr = this.mConstraintMatrix;
        if (iArr == null || i >= iArr.length) {
            return 0;
        }
        return iArr[i][1];
    }

    public int rightOfWidget(int i) {
        int[][] iArr = this.mConstraintMatrix;
        if (iArr == null || i >= iArr.length) {
            return 0;
        }
        return iArr[i][2];
    }

    public int bottomOfWidget(int i) {
        int[][] iArr = this.mConstraintMatrix;
        if (iArr == null || i >= iArr.length) {
            return 0;
        }
        return iArr[i][3];
    }
}
