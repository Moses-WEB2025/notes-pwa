package com.miui.note.algorithm.utils.mathutils;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class LUDecomposition implements Serializable {
    private double[][] LU;
    private int m;
    private int n;
    private int[] piv;
    private int pivsign;

    public LUDecomposition(Matrix matrix) {
        int i;
        this.LU = matrix.getArrayCopy();
        this.m = matrix.getRowDimension();
        this.n = matrix.getColumnDimension();
        this.piv = new int[this.m];
        int i2 = 0;
        while (true) {
            i = this.m;
            if (i2 >= i) {
                break;
            }
            this.piv[i2] = i2;
            i2++;
        }
        this.pivsign = 1;
        double[] dArr = new double[i];
        int i3 = 0;
        while (i3 < this.n) {
            for (int i4 = 0; i4 < this.m; i4++) {
                dArr[i4] = this.LU[i4][i3];
            }
            int i5 = 0;
            while (true) {
                double d = 0.0d;
                if (i5 >= this.m) {
                    break;
                }
                double[] dArr2 = this.LU[i5];
                int iMin = Math.min(i5, i3);
                for (int i6 = 0; i6 < iMin; i6++) {
                    d += dArr2[i6] * dArr[i6];
                }
                double d2 = dArr[i5] - d;
                dArr[i5] = d2;
                dArr2[i3] = d2;
                i5++;
            }
            int i7 = i3 + 1;
            int i8 = i3;
            for (int i9 = i7; i9 < this.m; i9++) {
                if (Math.abs(dArr[i9]) > Math.abs(dArr[i8])) {
                    i8 = i9;
                }
            }
            if (i8 != i3) {
                for (int i10 = 0; i10 < this.n; i10++) {
                    double[][] dArr3 = this.LU;
                    double[] dArr4 = dArr3[i8];
                    double d3 = dArr4[i10];
                    double[] dArr5 = dArr3[i3];
                    dArr4[i10] = dArr5[i10];
                    dArr5[i10] = d3;
                }
                int[] iArr = this.piv;
                int i11 = iArr[i8];
                iArr[i8] = iArr[i3];
                iArr[i3] = i11;
                this.pivsign = -this.pivsign;
            }
            if ((i3 < this.m) & (this.LU[i3][i3] != 0.0d)) {
                for (int i12 = i7; i12 < this.m; i12++) {
                    double[][] dArr6 = this.LU;
                    double[] dArr7 = dArr6[i12];
                    dArr7[i3] = dArr7[i3] / dArr6[i3][i3];
                }
            }
            i3 = i7;
        }
    }

    public boolean isNonsingular() {
        for (int i = 0; i < this.n; i++) {
            if (this.LU[i][i] == 0.0d) {
                return false;
            }
        }
        return true;
    }

    public Matrix getL() {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                if (i > i2) {
                    array[i][i2] = this.LU[i][i2];
                } else if (i == i2) {
                    array[i][i2] = 1.0d;
                } else {
                    array[i][i2] = 0.0d;
                }
            }
        }
        return matrix;
    }

    public Matrix getU() {
        int i = this.n;
        Matrix matrix = new Matrix(i, i);
        double[][] array = matrix.getArray();
        for (int i2 = 0; i2 < this.n; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                if (i2 <= i3) {
                    array[i2][i3] = this.LU[i2][i3];
                } else {
                    array[i2][i3] = 0.0d;
                }
            }
        }
        return matrix;
    }

    public int[] getPivot() {
        int[] iArr = new int[this.m];
        for (int i = 0; i < this.m; i++) {
            iArr[i] = this.piv[i];
        }
        return iArr;
    }

    public double[] getDoublePivot() {
        double[] dArr = new double[this.m];
        for (int i = 0; i < this.m; i++) {
            dArr[i] = this.piv[i];
        }
        return dArr;
    }

    public double det() {
        if (this.m != this.n) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        double d = this.pivsign;
        for (int i = 0; i < this.n; i++) {
            d *= this.LU[i][i];
        }
        return d;
    }

    public Matrix solve(Matrix matrix) {
        int i;
        if (matrix.getRowDimension() != this.m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!isNonsingular()) {
            throw new RuntimeException("Matrix is singular.");
        }
        int columnDimension = matrix.getColumnDimension();
        Matrix matrix2 = matrix.getMatrix(this.piv, 0, columnDimension - 1);
        double[][] array = matrix2.getArray();
        int i2 = 0;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < this.n; i4++) {
                for (int i5 = 0; i5 < columnDimension; i5++) {
                    double[] dArr = array[i4];
                    dArr[i5] = dArr[i5] - (array[i2][i5] * this.LU[i4][i2]);
                }
            }
            i2 = i3;
        }
        for (int i6 = i - 1; i6 >= 0; i6--) {
            for (int i7 = 0; i7 < columnDimension; i7++) {
                double[] dArr2 = array[i6];
                dArr2[i7] = dArr2[i7] / this.LU[i6][i6];
            }
            for (int i8 = 0; i8 < i6; i8++) {
                for (int i9 = 0; i9 < columnDimension; i9++) {
                    double[] dArr3 = array[i8];
                    dArr3[i9] = dArr3[i9] - (array[i6][i9] * this.LU[i8][i6]);
                }
            }
        }
        return matrix2;
    }
}
