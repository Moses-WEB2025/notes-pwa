package com.miui.note.algorithm.utils.mathutils;

import java.io.Serializable;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes3.dex */
public class CholeskyDecomposition implements Serializable {
    private double[][] L;
    private boolean isspd;
    private int n;

    public CholeskyDecomposition(Matrix matrix) {
        double[][] array = matrix.getArray();
        int rowDimension = matrix.getRowDimension();
        this.n = rowDimension;
        this.L = (double[][]) Array.newInstance((Class<?>) Double.TYPE, rowDimension, rowDimension);
        this.isspd = matrix.getColumnDimension() == this.n;
        int i = 0;
        while (i < this.n) {
            double[] dArr = this.L[i];
            double d = 0.0d;
            for (int i2 = 0; i2 < i; i2++) {
                double[] dArr2 = this.L[i2];
                double d2 = 0.0d;
                for (int i3 = 0; i3 < i2; i3++) {
                    d2 += dArr2[i3] * dArr[i3];
                }
                double[] dArr3 = array[i];
                double d3 = (dArr3[i2] - d2) / this.L[i2][i2];
                dArr[i2] = d3;
                d += d3 * d3;
                this.isspd = (array[i2][i] == dArr3[i2]) & this.isspd;
            }
            double d4 = array[i][i] - d;
            this.isspd &= d4 > 0.0d;
            this.L[i][i] = Math.sqrt(Math.max(d4, 0.0d));
            int i4 = i + 1;
            for (int i5 = i4; i5 < this.n; i5++) {
                this.L[i][i5] = 0.0d;
            }
            i = i4;
        }
    }

    public boolean isSPD() {
        return this.isspd;
    }

    public Matrix getL() {
        double[][] dArr = this.L;
        int i = this.n;
        return new Matrix(dArr, i, i);
    }

    public Matrix solve(Matrix matrix) {
        int i;
        if (matrix.getRowDimension() != this.n) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isspd) {
            throw new RuntimeException("Matrix is not symmetric positive definite.");
        }
        double[][] arrayCopy = matrix.getArrayCopy();
        int columnDimension = matrix.getColumnDimension();
        int i2 = 0;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            for (int i3 = 0; i3 < columnDimension; i3++) {
                for (int i4 = 0; i4 < i2; i4++) {
                    double[] dArr = arrayCopy[i2];
                    dArr[i3] = dArr[i3] - (arrayCopy[i4][i3] * this.L[i2][i4]);
                }
                double[] dArr2 = arrayCopy[i2];
                dArr2[i3] = dArr2[i3] / this.L[i2][i2];
            }
            i2++;
        }
        for (int i5 = i - 1; i5 >= 0; i5--) {
            for (int i6 = 0; i6 < columnDimension; i6++) {
                for (int i7 = i5 + 1; i7 < this.n; i7++) {
                    double[] dArr3 = arrayCopy[i5];
                    dArr3[i6] = dArr3[i6] - (arrayCopy[i7][i6] * this.L[i7][i5]);
                }
                double[] dArr4 = arrayCopy[i5];
                dArr4[i6] = dArr4[i6] / this.L[i5][i5];
            }
        }
        return new Matrix(arrayCopy, this.n, columnDimension);
    }
}
