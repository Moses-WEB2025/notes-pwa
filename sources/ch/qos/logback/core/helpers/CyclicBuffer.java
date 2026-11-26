package ch.qos.logback.core.helpers;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CyclicBuffer<E> {
    E[] ea;
    int first;
    int last;
    int maxSize;
    int numElems;

    public CyclicBuffer(int i) throws IllegalArgumentException {
        if (i < 1) {
            throw new IllegalArgumentException("The maxSize argument (" + i + ") is not a positive integer.");
        }
        init(i);
    }

    public CyclicBuffer(CyclicBuffer<E> cyclicBuffer) {
        int i = cyclicBuffer.maxSize;
        this.maxSize = i;
        E[] eArr = (E[]) new Object[i];
        this.ea = eArr;
        System.arraycopy(cyclicBuffer.ea, 0, eArr, 0, i);
        this.last = cyclicBuffer.last;
        this.first = cyclicBuffer.first;
        this.numElems = cyclicBuffer.numElems;
    }

    private void init(int i) {
        this.maxSize = i;
        this.ea = (E[]) new Object[i];
        this.first = 0;
        this.last = 0;
        this.numElems = 0;
    }

    public void add(E e) {
        E[] eArr = this.ea;
        int i = this.last;
        eArr[i] = e;
        int i2 = i + 1;
        this.last = i2;
        int i3 = this.maxSize;
        if (i2 == i3) {
            this.last = 0;
        }
        int i4 = this.numElems;
        if (i4 < i3) {
            this.numElems = i4 + 1;
            return;
        }
        int i5 = this.first + 1;
        this.first = i5;
        if (i5 == i3) {
            this.first = 0;
        }
    }

    public List<E> asList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length(); i++) {
            arrayList.add(get(i));
        }
        return arrayList;
    }

    public void clear() {
        init(this.maxSize);
    }

    public E get() {
        int i = this.numElems;
        if (i <= 0) {
            return null;
        }
        this.numElems = i - 1;
        E[] eArr = this.ea;
        int i2 = this.first;
        E e = eArr[i2];
        eArr[i2] = null;
        int i3 = i2 + 1;
        this.first = i3;
        if (i3 == this.maxSize) {
            this.first = 0;
        }
        return e;
    }

    public E get(int i) {
        if (i < 0 || i >= this.numElems) {
            return null;
        }
        return this.ea[(this.first + i) % this.maxSize];
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public int length() {
        return this.numElems;
    }

    public void resize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Negative array size [" + i + "] not allowed.");
        }
        int i2 = this.numElems;
        if (i == i2) {
            return;
        }
        E[] eArr = (E[]) new Object[i];
        if (i < i2) {
            i2 = i;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            E[] eArr2 = this.ea;
            int i4 = this.first;
            eArr[i3] = eArr2[i4];
            eArr2[i4] = null;
            int i5 = i4 + 1;
            this.first = i5;
            if (i5 == this.numElems) {
                this.first = 0;
            }
        }
        this.ea = eArr;
        this.first = 0;
        this.numElems = i2;
        this.maxSize = i;
        if (i2 == i) {
            this.last = 0;
        } else {
            this.last = i2;
        }
    }
}
