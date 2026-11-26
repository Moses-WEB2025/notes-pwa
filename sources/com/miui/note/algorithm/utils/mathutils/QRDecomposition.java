package com.miui.note.algorithm.utils.mathutils;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class QRDecomposition implements Serializable {
    private static final long serialVersionUID = 1;
    private double[][] QR;
    private double[] Rdiag;
    private int m;
    private int n;

    public QRDecomposition(Matrix matrix) {
        this.QR = matrix.getArrayCopy();
        this.m = matrix.getRowDimension();
        int columnDimension = matrix.getColumnDimension();
        this.n = columnDimension;
        this.Rdiag = new double[columnDimension];
        for (int i = 0; i < this.n; i++) {
            double dHypot = 0.0d;
            for (int i2 = i; i2 < this.m; i2++) {
                dHypot = Maths.hypot(dHypot, this.QR[i2][i]);
            }
            if (dHypot != 0.0d) {
                dHypot = this.QR[i][i] < 0.0d ? -dHypot : dHypot;
                for (int i3 = i; i3 < this.m; i3++) {
                    double[] dArr = this.QR[i3];
                    dArr[i] = dArr[i] / dHypot;
                }
                double[] dArr2 = this.QR[i];
                dArr2[i] = dArr2[i] + 1.0d;
                for (int i4 = i + 1; i4 < this.n; i4++) {
                    double d = 0.0d;
                    for (int i5 = i; i5 < this.m; i5++) {
                        double[] dArr3 = this.QR[i5];
                        d += dArr3[i] * dArr3[i4];
                    }
                    double d2 = (-d) / this.QR[i][i];
                    for (int i6 = i; i6 < this.m; i6++) {
                        double[] dArr4 = this.QR[i6];
                        dArr4[i4] = dArr4[i4] + (dArr4[i] * d2);
                    }
                }
            }
            this.Rdiag[i] = -dHypot;
        }
    }

    public boolean isFullRank() {
        for (int i = 0; i < this.n; i++) {
            if (this.Rdiag[i] == 0.0d) {
                return false;
            }
        }
        return true;
    }

    public Matrix getH() {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                if (i >= i2) {
                    array[i][i2] = this.QR[i][i2];
                } else {
                    array[i][i2] = 0.0d;
                }
            }
        }
        return matrix;
    }

    public Matrix getR() {
        int i = this.n;
        Matrix matrix = new Matrix(i, i);
        double[][] array = matrix.getArray();
        for (int i2 = 0; i2 < this.n; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                if (i2 < i3) {
                    array[i2][i3] = this.QR[i2][i3];
                } else if (i2 == i3) {
                    array[i2][i3] = this.Rdiag[i2];
                } else {
                    array[i2][i3] = 0.0d;
                }
            }
        }
        return matrix;
    }

    public Matrix getQ() {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = this.n - 1; i >= 0; i--) {
            for (int i2 = 0; i2 < this.m; i2++) {
                array[i2][i] = 0.0d;
            }
            array[i][i] = 1.0d;
            for (int i3 = i; i3 < this.n; i3++) {
                if (this.QR[i][i] != 0.0d) {
                    double d = 0.0d;
                    for (int i4 = i; i4 < this.m; i4++) {
                        d += this.QR[i4][i] * array[i4][i3];
                    }
                    double d2 = (-d) / this.QR[i][i];
                    for (int i5 = i; i5 < this.m; i5++) {
                        double[] dArr = array[i5];
                        dArr[i3] = dArr[i3] + (this.QR[i5][i] * d2);
                    }
                }
            }
        }
        return matrix;
    }

    public Matrix solve(Matrix matrix) {
        int i;
        if (matrix.getRowDimension() != this.m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!isFullRank()) {
            throw new RuntimeException("Matrix is rank deficient.");
        }
        int columnDimension = matrix.getColumnDimension();
        double[][] arrayCopy = matrix.getArrayCopy();
        int i2 = 0;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            for (int i3 = 0; i3 < columnDimension; i3++) {
                double d = 0.0d;
                for (int i4 = i2; i4 < this.m; i4++) {
                    d += this.QR[i4][i2] * arrayCopy[i4][i3];
                }
                double d2 = (-d) / this.QR[i2][i2];
                for (int i5 = i2; i5 < this.m; i5++) {
                    double[] dArr = arrayCopy[i5];
                    dArr[i3] = dArr[i3] + (this.QR[i5][i2] * d2);
                }
            }
            i2++;
        }
        for (int i6 = i - 1; i6 >= 0; i6--) {
            for (int i7 = 0; i7 < columnDimension; i7++) {
                double[] dArr2 = arrayCopy[i6];
                dArr2[i7] = dArr2[i7] / this.Rdiag[i6];
            }
            for (int i8 = 0; i8 < i6; i8++) {
                for (int i9 = 0; i9 < columnDimension; i9++) {
                    double[] dArr3 = arrayCopy[i8];
                    dArr3[i9] = dArr3[i9] - (arrayCopy[i6][i9] * this.QR[i8][i6]);
                }
            }
        }
        return new Matrix(arrayCopy, this.n, columnDimension).getMatrix(0, this.n - 1, 0, columnDimension - 1);
    }
}
