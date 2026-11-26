package com.miui.note.algorithm.utils.mathutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class Matrix implements Cloneable, Serializable {
    private double[][] A;
    private int m;
    private int n;

    public Matrix(int i, int i2) {
        this.m = i;
        this.n = i2;
        this.A = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, i2);
    }

    public Matrix(int i, int i2, double d) {
        this.m = i;
        this.n = i2;
        this.A = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, i2);
        for (int i3 = 0; i3 < i; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                this.A[i3][i4] = d;
            }
        }
    }

    public Matrix(double[][] dArr) {
        this.m = dArr.length;
        this.n = dArr[0].length;
        for (int i = 0; i < this.m; i++) {
            if (dArr[i].length != this.n) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
        }
        this.A = dArr;
    }

    public Matrix(double[][] dArr, int i, int i2) {
        this.A = dArr;
        this.m = i;
        this.n = i2;
    }

    public Matrix(double[] dArr, int i) {
        this.m = i;
        int length = i != 0 ? dArr.length / i : 0;
        this.n = length;
        if (i * length != dArr.length) {
            throw new IllegalArgumentException("Array length must be a multiple of m.");
        }
        this.A = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, length);
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                this.A[i2][i3] = dArr[(i3 * i) + i2];
            }
        }
    }

    public static Matrix constructWithCopy(double[][] dArr) {
        int length = dArr.length;
        int length2 = dArr[0].length;
        Matrix matrix = new Matrix(length, length2);
        double[][] array = matrix.getArray();
        for (int i = 0; i < length; i++) {
            if (dArr[i].length != length2) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
            for (int i2 = 0; i2 < length2; i2++) {
                array[i][i2] = dArr[i][i2];
            }
        }
        return matrix;
    }

    public Matrix copy() {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2];
            }
        }
        return matrix;
    }

    public Object clone() {
        return copy();
    }

    public double[][] getArray() {
        return this.A;
    }

    public double[][] getArrayCopy() {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, this.m, this.n);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                dArr[i][i2] = this.A[i][i2];
            }
        }
        return dArr;
    }

    public double[] getColumnPackedCopy() {
        double[] dArr = new double[this.m * this.n];
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                dArr[(this.m * i2) + i] = this.A[i][i2];
            }
        }
        return dArr;
    }

    public double[] getRowPackedCopy() {
        double[] dArr = new double[this.m * this.n];
        for (int i = 0; i < this.m; i++) {
            int i2 = 0;
            while (true) {
                int i3 = this.n;
                if (i2 < i3) {
                    dArr[(i3 * i) + i2] = this.A[i][i2];
                    i2++;
                }
            }
        }
        return dArr;
    }

    public int getRowDimension() {
        return this.m;
    }

    public int getColumnDimension() {
        return this.n;
    }

    public double get(int i, int i2) {
        return this.A[i][i2];
    }

    public Matrix getMatrix(int i, int i2, int i3, int i4) {
        Matrix matrix = new Matrix((i2 - i) + 1, (i4 - i3) + 1);
        double[][] array = matrix.getArray();
        for (int i5 = i; i5 <= i2; i5++) {
            for (int i6 = i3; i6 <= i4; i6++) {
                try {
                    array[i5 - i][i6 - i3] = this.A[i5][i6];
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Submatrix indices");
                }
            }
        }
        return matrix;
    }

    public Matrix getMatrix(int[] iArr, int[] iArr2) {
        Matrix matrix = new Matrix(iArr.length, iArr2.length);
        double[][] array = matrix.getArray();
        for (int i = 0; i < iArr.length; i++) {
            try {
                for (int i2 = 0; i2 < iArr2.length; i2++) {
                    array[i][i2] = this.A[iArr[i]][iArr2[i2]];
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Submatrix indices");
            }
        }
        return matrix;
    }

    public Matrix getMatrix(int i, int i2, int[] iArr) {
        Matrix matrix = new Matrix((i2 - i) + 1, iArr.length);
        double[][] array = matrix.getArray();
        for (int i3 = i; i3 <= i2; i3++) {
            for (int i4 = 0; i4 < iArr.length; i4++) {
                try {
                    array[i3 - i][i4] = this.A[i3][iArr[i4]];
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Submatrix indices");
                }
            }
        }
        return matrix;
    }

    public Matrix getMatrix(int[] iArr, int i, int i2) {
        Matrix matrix = new Matrix(iArr.length, (i2 - i) + 1);
        double[][] array = matrix.getArray();
        for (int i3 = 0; i3 < iArr.length; i3++) {
            try {
                for (int i4 = i; i4 <= i2; i4++) {
                    array[i3][i4 - i] = this.A[iArr[i3]][i4];
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Submatrix indices");
            }
        }
        return matrix;
    }

    public void set(int i, int i2, double d) {
        this.A[i][i2] = d;
    }

    public void setMatrix(int i, int i2, int i3, int i4, Matrix matrix) {
        for (int i5 = i; i5 <= i2; i5++) {
            for (int i6 = i3; i6 <= i4; i6++) {
                try {
                    this.A[i5][i6] = matrix.get(i5 - i, i6 - i3);
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Submatrix indices");
                }
            }
        }
    }

    public void setMatrix(int[] iArr, int[] iArr2, Matrix matrix) {
        for (int i = 0; i < iArr.length; i++) {
            try {
                for (int i2 = 0; i2 < iArr2.length; i2++) {
                    this.A[iArr[i]][iArr2[i2]] = matrix.get(i, i2);
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Submatrix indices");
            }
        }
    }

    public void setMatrix(int[] iArr, int i, int i2, Matrix matrix) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            try {
                for (int i4 = i; i4 <= i2; i4++) {
                    this.A[iArr[i3]][i4] = matrix.get(i3, i4 - i);
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Submatrix indices");
            }
        }
    }

    public void setMatrix(int i, int i2, int[] iArr, Matrix matrix) {
        for (int i3 = i; i3 <= i2; i3++) {
            for (int i4 = 0; i4 < iArr.length; i4++) {
                try {
                    this.A[i3][iArr[i4]] = matrix.get(i3 - i, i4);
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Submatrix indices");
                }
            }
        }
    }

    public Matrix transpose() {
        Matrix matrix = new Matrix(this.n, this.m);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i2][i] = this.A[i][i2];
            }
        }
        return matrix;
    }

    public double norm1() {
        double dMax = 0.0d;
        for (int i = 0; i < this.n; i++) {
            double dAbs = 0.0d;
            for (int i2 = 0; i2 < this.m; i2++) {
                dAbs += Math.abs(this.A[i2][i]);
            }
            dMax = Math.max(dMax, dAbs);
        }
        return dMax;
    }

    public double norm2() {
        return new SingularValueDecomposition(this).norm2();
    }

    public double normInf() {
        double dMax = 0.0d;
        for (int i = 0; i < this.m; i++) {
            double dAbs = 0.0d;
            for (int i2 = 0; i2 < this.n; i2++) {
                dAbs += Math.abs(this.A[i][i2]);
            }
            dMax = Math.max(dMax, dAbs);
        }
        return dMax;
    }

    public double normF() {
        double dHypot = 0.0d;
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                dHypot = Maths.hypot(dHypot, this.A[i][i2]);
            }
        }
        return dHypot;
    }

    public Matrix uminus() {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = -this.A[i][i2];
            }
        }
        return matrix;
    }

    public Matrix plus(Matrix matrix) {
        checkMatrixDimensions(matrix);
        Matrix matrix2 = new Matrix(this.m, this.n);
        double[][] array = matrix2.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2] + matrix.A[i][i2];
            }
        }
        return matrix2;
    }

    public Matrix plusEquals(Matrix matrix) {
        checkMatrixDimensions(matrix);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = dArr[i2] + matrix.A[i][i2];
            }
        }
        return this;
    }

    public Matrix minus(Matrix matrix) {
        checkMatrixDimensions(matrix);
        Matrix matrix2 = new Matrix(this.m, this.n);
        double[][] array = matrix2.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2] - matrix.A[i][i2];
            }
        }
        return matrix2;
    }

    public Matrix minusEquals(Matrix matrix) {
        checkMatrixDimensions(matrix);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = dArr[i2] - matrix.A[i][i2];
            }
        }
        return this;
    }

    public Matrix arrayTimes(Matrix matrix) {
        checkMatrixDimensions(matrix);
        Matrix matrix2 = new Matrix(this.m, this.n);
        double[][] array = matrix2.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2] * matrix.A[i][i2];
            }
        }
        return matrix2;
    }

    public Matrix arrayTimesEquals(Matrix matrix) {
        checkMatrixDimensions(matrix);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = dArr[i2] * matrix.A[i][i2];
            }
        }
        return this;
    }

    public Matrix arrayRightDivide(Matrix matrix) {
        checkMatrixDimensions(matrix);
        Matrix matrix2 = new Matrix(this.m, this.n);
        double[][] array = matrix2.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2] / matrix.A[i][i2];
            }
        }
        return matrix2;
    }

    public Matrix arrayRightDivideEquals(Matrix matrix) {
        checkMatrixDimensions(matrix);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = dArr[i2] / matrix.A[i][i2];
            }
        }
        return this;
    }

    public Matrix arrayLeftDivide(Matrix matrix) {
        checkMatrixDimensions(matrix);
        Matrix matrix2 = new Matrix(this.m, this.n);
        double[][] array = matrix2.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = matrix.A[i][i2] / this.A[i][i2];
            }
        }
        return matrix2;
    }

    public Matrix arrayLeftDivideEquals(Matrix matrix) {
        checkMatrixDimensions(matrix);
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = matrix.A[i][i2] / dArr[i2];
            }
        }
        return this;
    }

    public Matrix times(double d) {
        Matrix matrix = new Matrix(this.m, this.n);
        double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                array[i][i2] = this.A[i][i2] * d;
            }
        }
        return matrix;
    }

    public Matrix timesEquals(double d) {
        for (int i = 0; i < this.m; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                double[] dArr = this.A[i];
                dArr[i2] = dArr[i2] * d;
            }
        }
        return this;
    }

    public Matrix times(Matrix matrix) {
        if (matrix.m != this.n) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix matrix2 = new Matrix(this.m, matrix.n);
        double[][] array = matrix2.getArray();
        double[] dArr = new double[this.n];
        for (int i = 0; i < matrix.n; i++) {
            for (int i2 = 0; i2 < this.n; i2++) {
                dArr[i2] = matrix.A[i2][i];
            }
            for (int i3 = 0; i3 < this.m; i3++) {
                double[] dArr2 = this.A[i3];
                double d = 0.0d;
                for (int i4 = 0; i4 < this.n; i4++) {
                    d += dArr2[i4] * dArr[i4];
                }
                array[i3][i] = d;
            }
        }
        return matrix2;
    }

    public double dotProduct(Matrix matrix) {
        int i;
        int i2 = this.m;
        if (i2 != matrix.m || (i = this.n) != matrix.n) {
            throw new IllegalArgumentException("Matrix dimensions must agree");
        }
        if (i2 != 1 && i != 1) {
            throw new IllegalArgumentException("Matrix is not a row or column vector");
        }
        double d = 0.0d;
        if (i == 1) {
            for (int i3 = 0; i3 < this.m; i3++) {
                d += this.A[i3][0] * matrix.A[i3][0];
            }
            return d;
        }
        if (i2 == 1) {
            for (int i4 = 0; i4 < this.n; i4++) {
                d += this.A[0][i4] * matrix.A[0][i4];
            }
        }
        return d;
    }

    public Matrix crossProduct(Matrix matrix) {
        int i;
        int i2 = this.m;
        if (i2 != matrix.m || (i = this.n) != matrix.n) {
            throw new IllegalArgumentException("Matrix dimensions must agree");
        }
        Matrix matrix2 = new Matrix(i2, i);
        int i3 = this.m;
        if (i3 == 1 && this.n == 3) {
            double[] dArr = matrix2.A[0];
            double[] dArr2 = this.A[0];
            double d = dArr2[1];
            double[] dArr3 = matrix.A[0];
            double d2 = dArr3[2];
            double d3 = dArr2[2];
            dArr[0] = (d * d2) - (dArr3[1] * d3);
            double d4 = dArr3[0];
            double d5 = dArr2[0];
            dArr[1] = (d3 * d4) - (d2 * d5);
            dArr[2] = (d5 * dArr3[1]) - (dArr2[1] * d4);
            return matrix2;
        }
        if (i3 == 3 && this.n == 1) {
            double[][] dArr4 = matrix2.A;
            double[] dArr5 = dArr4[0];
            double[][] dArr6 = this.A;
            double[] dArr7 = dArr6[1];
            double d6 = dArr7[0];
            double[][] dArr8 = matrix.A;
            double[] dArr9 = dArr8[2];
            double d7 = d6 * dArr9[0];
            double[] dArr10 = dArr6[2];
            double d8 = dArr10[0];
            double[] dArr11 = dArr8[1];
            dArr5[0] = d7 - (d8 * dArr11[0]);
            double[] dArr12 = dArr4[1];
            double d9 = dArr10[0];
            double[] dArr13 = dArr8[0];
            double d10 = d9 * dArr13[0];
            double[] dArr14 = dArr6[0];
            dArr12[0] = d10 - (dArr14[0] * dArr9[0]);
            dArr4[2][0] = (dArr14[0] * dArr11[0]) - (dArr7[0] * dArr13[0]);
            return matrix2;
        }
        throw new IllegalArgumentException("Matrix must be a 3-element row or column vector");
    }

    public LUDecomposition lu() {
        return new LUDecomposition(this);
    }

    public QRDecomposition qr() {
        return new QRDecomposition(this);
    }

    public CholeskyDecomposition chol() {
        return new CholeskyDecomposition(this);
    }

    public SingularValueDecomposition svd() {
        return new SingularValueDecomposition(this);
    }

    public EigenvalueDecomposition eig() {
        return new EigenvalueDecomposition(this);
    }

    public Matrix solve(Matrix matrix) {
        return this.m == this.n ? new LUDecomposition(this).solve(matrix) : new QRDecomposition(this).solve(matrix);
    }

    public Matrix solveTranspose(Matrix matrix) {
        return transpose().solve(matrix.transpose());
    }

    public Matrix inverse() {
        int i = this.m;
        return solve(identity(i, i));
    }

    public double det() {
        return new LUDecomposition(this).det();
    }

    public int rank() {
        return new SingularValueDecomposition(this).rank();
    }

    public double cond() {
        return new SingularValueDecomposition(this).cond();
    }

    public double trace() {
        double d = 0.0d;
        for (int i = 0; i < Math.min(this.m, this.n); i++) {
            d += this.A[i][i];
        }
        return d;
    }

    public static Matrix random(int i, int i2) {
        Matrix matrix = new Matrix(i, i2);
        double[][] array = matrix.getArray();
        for (int i3 = 0; i3 < i; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                array[i3][i4] = Math.random();
            }
        }
        return matrix;
    }

    public static Matrix identity(int i, int i2) {
        Matrix matrix = new Matrix(i, i2);
        double[][] array = matrix.getArray();
        int i3 = 0;
        while (i3 < i) {
            int i4 = 0;
            while (i4 < i2) {
                array[i3][i4] = i3 == i4 ? 1.0d : 0.0d;
                i4++;
            }
            i3++;
        }
        return matrix;
    }

    public void print(int i, int i2) {
        print(new PrintWriter((OutputStream) System.out, true), i, i2);
    }

    public void print(PrintWriter printWriter, int i, int i2) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        decimalFormat.setMinimumIntegerDigits(1);
        decimalFormat.setMaximumFractionDigits(i2);
        decimalFormat.setMinimumFractionDigits(i2);
        decimalFormat.setGroupingUsed(false);
        print(printWriter, decimalFormat, i + 2);
    }

    public void print(NumberFormat numberFormat, int i) {
        print(new PrintWriter((OutputStream) System.out, true), numberFormat, i);
    }

    public void print(PrintWriter printWriter, NumberFormat numberFormat, int i) {
        printWriter.println();
        for (int i2 = 0; i2 < this.m; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                String str = numberFormat.format(this.A[i2][i3]);
                int iMax = Math.max(1, i - str.length());
                for (int i4 = 0; i4 < iMax; i4++) {
                    printWriter.print(' ');
                }
                printWriter.print(str);
            }
            printWriter.println();
        }
        printWriter.println();
    }

    public static Matrix read(BufferedReader bufferedReader) throws IOException {
        int i;
        StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
        streamTokenizer.resetSyntax();
        streamTokenizer.wordChars(0, 255);
        streamTokenizer.whitespaceChars(0, 32);
        streamTokenizer.eolIsSignificant(true);
        Vector vector = new Vector();
        while (streamTokenizer.nextToken() == 10) {
        }
        if (streamTokenizer.ttype == -1) {
            throw new IOException("Unexpected EOF on matrix read.");
        }
        do {
            vector.addElement(Double.valueOf(streamTokenizer.sval));
        } while (streamTokenizer.nextToken() == -3);
        int size = vector.size();
        double[] dArr = new double[size];
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = ((Double) vector.elementAt(i2)).doubleValue();
        }
        vector.removeAllElements();
        vector.addElement(dArr);
        while (streamTokenizer.nextToken() == -3) {
            double[] dArr2 = new double[size];
            vector.addElement(dArr2);
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    throw new IOException("Row " + vector.size() + " is too long.");
                }
                i = i3 + 1;
                dArr2[i3] = Double.valueOf(streamTokenizer.sval).doubleValue();
                if (streamTokenizer.nextToken() != -3) {
                    break;
                }
                i3 = i;
            }
            if (i < size) {
                throw new IOException("Row " + vector.size() + " is too short.");
            }
        }
        double[][] dArr3 = new double[vector.size()][];
        vector.copyInto(dArr3);
        return new Matrix(dArr3);
    }

    public static Matrix ones(int i, int i2) {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, i2);
        for (int i3 = 0; i3 < i; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                dArr[i3][i4] = 1.0d;
            }
        }
        return new Matrix(dArr);
    }

    public static Matrix eye(int i) {
        return eye(i, i);
    }

    public static Matrix eye(int i, int i2) {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, i2);
        int iMin = Math.min(i, i2);
        for (int i3 = 0; i3 < iMin; i3++) {
            dArr[i3][i3] = 1.0d;
        }
        return new Matrix(dArr);
    }

    public Matrix diag() {
        int iMin = Math.min(this.m, this.n);
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iMin, 1);
        for (int i = 0; i < iMin; i++) {
            dArr[i][0] = get(i, i);
        }
        return new Matrix(dArr);
    }

    public boolean isRightHanded() {
        if (this.m != 3 || this.n != 3) {
            throw new IllegalArgumentException();
        }
        double d = get(0, 0);
        double d2 = get(1, 0);
        double d3 = get(2, 0);
        double d4 = get(0, 1);
        double d5 = get(1, 1);
        double d6 = get(2, 1);
        return ((((d2 * d6) - (d3 * d5)) * get(0, 2)) + (((d3 * d4) - (d6 * d)) * get(1, 2))) + (((d * d5) - (d2 * d4)) * get(2, 2)) > 0.0d;
    }

    public boolean isZFlipped() {
        return (get(2, 0) + get(2, 1)) + get(2, 2) < 0.0d;
    }

    private void checkMatrixDimensions(Matrix matrix) {
        if (matrix.m != this.m || matrix.n != this.n) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
    }
}
