package org.apache.commons.text.similarity;

import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class IntersectionResult {
    private final int intersection;
    private final int sizeA;
    private final int sizeB;

    public IntersectionResult(int i, int i2, int i3) {
        if (i < 0) {
            throw new IllegalArgumentException("Set size |A| is not positive: " + i);
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Set size |B| is not positive: " + i2);
        }
        if (i3 < 0 || i3 > Math.min(i, i2)) {
            throw new IllegalArgumentException("Invalid intersection of A and B: " + i3);
        }
        this.sizeA = i;
        this.sizeB = i2;
        this.intersection = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            IntersectionResult intersectionResult = (IntersectionResult) obj;
            if (this.sizeA == intersectionResult.sizeA && this.sizeB == intersectionResult.sizeB && this.intersection == intersectionResult.intersection) {
                return true;
            }
        }
        return false;
    }

    public int getIntersection() {
        return this.intersection;
    }

    public int getSizeA() {
        return this.sizeA;
    }

    public int getSizeB() {
        return this.sizeB;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.sizeA), Integer.valueOf(this.sizeB), Integer.valueOf(this.intersection));
    }

    public String toString() {
        return "Size A: " + this.sizeA + ", Size B: " + this.sizeB + ", Intersection: " + this.intersection;
    }
}
