package com.google.common.hash;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import javax.annotation.CheckForNull;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
abstract class Striped64 extends Number {
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    volatile transient long base;
    volatile transient int busy;

    @CheckForNull
    volatile transient Cell[] cells;
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal<>();
    static final Random rng = new Random();
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    abstract long fn(long currentValue, long newValue);

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        Cell(long x) {
            this.value = x;
        }

        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        static {
            try {
                Unsafe unsafe = Striped64.getUnsafe();
                UNSAFE = unsafe;
                valueOffset = unsafe.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    static {
        try {
            Unsafe unsafe = getUnsafe();
            UNSAFE = unsafe;
            baseOffset = unsafe.objectFieldOffset(Striped64.class.getDeclaredField("base"));
            busyOffset = unsafe.objectFieldOffset(Striped64.class.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    final boolean casBase(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, cmp, val);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0056  */
    final void retryUpdate(long x, @CheckForNull int[] hc, boolean wasUncontended) {
        int iNextInt;
        int[] iArr;
        boolean z;
        int length;
        boolean z2;
        int length2;
        if (hc == null) {
            iArr = new int[1];
            threadHashCode.set(iArr);
            iNextInt = rng.nextInt();
            if (iNextInt == 0) {
                iNextInt = 1;
            }
            iArr[0] = iNextInt;
        } else {
            iNextInt = hc[0];
            iArr = hc;
        }
        boolean z3 = false;
        int i = iNextInt;
        boolean z4 = wasUncontended;
        while (true) {
            Cell[] cellArr = this.cells;
            if (cellArr != null && (length = cellArr.length) > 0) {
                Cell cell = cellArr[(length - 1) & i];
                if (cell == null) {
                    if (this.busy == 0) {
                        Cell cell2 = new Cell(x);
                        if (this.busy == 0 && casBusy()) {
                            try {
                                Cell[] cellArr2 = this.cells;
                                if (cellArr2 == null || (length2 = cellArr2.length) <= 0) {
                                    z2 = false;
                                } else {
                                    int i2 = (length2 - 1) & i;
                                    if (cellArr2[i2] == null) {
                                        cellArr2[i2] = cell2;
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                }
                                this.busy = 0;
                                if (z2) {
                                    return;
                                }
                            } catch (Throwable th) {
                                this.busy = 0;
                                throw th;
                            }
                        }
                    }
                    z3 = false;
                    int i3 = i ^ (i << 13);
                    int i4 = i3 ^ (i3 >>> 17);
                    i = i4 ^ (i4 << 5);
                    iArr[0] = i;
                } else {
                    if (z4) {
                        long j = cell.value;
                        if (cell.cas(j, fn(j, x))) {
                            return;
                        }
                        if (length >= NCPU || this.cells != cellArr) {
                            z3 = false;
                        } else if (!z3) {
                            z3 = true;
                        } else if (this.busy == 0 && casBusy()) {
                            try {
                                if (this.cells == cellArr) {
                                    Cell[] cellArr3 = new Cell[length << 1];
                                    for (int i5 = 0; i5 < length; i5++) {
                                        cellArr3[i5] = cellArr[i5];
                                    }
                                    this.cells = cellArr3;
                                }
                                this.busy = 0;
                                z3 = false;
                            } catch (Throwable th2) {
                                this.busy = 0;
                                throw th2;
                            }
                        }
                    } else {
                        z4 = true;
                    }
                    int i6 = i ^ (i << 13);
                    int i7 = i6 ^ (i6 >>> 17);
                    i = i7 ^ (i7 << 5);
                    iArr[0] = i;
                }
            } else if (this.busy == 0 && this.cells == cellArr && casBusy()) {
                try {
                    if (this.cells == cellArr) {
                        Cell[] cellArr4 = new Cell[2];
                        cellArr4[i & 1] = new Cell(x);
                        this.cells = cellArr4;
                        z = true;
                    } else {
                        z = false;
                    }
                    this.busy = 0;
                    if (z) {
                        return;
                    }
                } catch (Throwable th3) {
                    this.busy = 0;
                    throw th3;
                }
            } else {
                long j2 = this.base;
                if (casBase(j2, fn(j2, x))) {
                    return;
                }
            }
        }
    }

    final void internalReset(long initialValue) {
        Cell[] cellArr = this.cells;
        this.base = initialValue;
        if (cellArr != null) {
            for (Cell cell : cellArr) {
                if (cell != null) {
                    cell.value = initialValue;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe getUnsafe() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (SecurityException unused) {
                return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.hash.Striped64.1
                    @Override // java.security.PrivilegedExceptionAction
                    public Unsafe run() throws Exception {
                        for (Field field : Unsafe.class.getDeclaredFields()) {
                            field.setAccessible(true);
                            Object obj = field.get(null);
                            if (Unsafe.class.isInstance(obj)) {
                                return (Unsafe) Unsafe.class.cast(obj);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            }
        } catch (PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics", e.getCause());
        }
    }
}
