package org.jsoup.nodes;

import java.util.Objects;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class Range {
    static final Range Untracked;
    private static final Position UntrackedPos;
    private final Position end;
    private final Position start;

    static {
        Position position = new Position(-1, -1, -1);
        UntrackedPos = position;
        Untracked = new Range(position, position);
    }

    public Range(Position position, Position position2) {
        this.start = position;
        this.end = position2;
    }

    public Position start() {
        return this.start;
    }

    public int startPos() {
        return this.start.pos;
    }

    public Position end() {
        return this.end;
    }

    public int endPos() {
        return this.end.pos;
    }

    public boolean isTracked() {
        return this != Untracked;
    }

    public boolean isImplicit() {
        if (isTracked()) {
            return this.start.equals(this.end);
        }
        return false;
    }

    static Range of(Node node, boolean z) {
        Object objUserData;
        String str = z ? SharedConstants.RangeKey : SharedConstants.EndRangeKey;
        if (node.hasAttributes() && (objUserData = node.attributes().userData(str)) != null) {
            return (Range) objUserData;
        }
        return Untracked;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Range range = (Range) obj;
        if (this.start.equals(range.start)) {
            return this.end.equals(range.end);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    public String toString() {
        return this.start + "-" + this.end;
    }

    public static class Position {
        private final int columnNumber;
        private final int lineNumber;
        private final int pos;

        public Position(int i, int i2, int i3) {
            this.pos = i;
            this.lineNumber = i2;
            this.columnNumber = i3;
        }

        public int pos() {
            return this.pos;
        }

        public int lineNumber() {
            return this.lineNumber;
        }

        public int columnNumber() {
            return this.columnNumber;
        }

        public boolean isTracked() {
            return this != Range.UntrackedPos;
        }

        public String toString() {
            return this.lineNumber + "," + this.columnNumber + ":" + this.pos;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Position position = (Position) obj;
                if (this.pos == position.pos && this.lineNumber == position.lineNumber && this.columnNumber == position.columnNumber) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.pos), Integer.valueOf(this.lineNumber), Integer.valueOf(this.columnNumber));
        }
    }

    public static class AttributeRange {
        static final AttributeRange UntrackedAttr = new AttributeRange(Range.Untracked, Range.Untracked);
        private final Range nameRange;
        private final Range valueRange;

        public AttributeRange(Range range, Range range2) {
            this.nameRange = range;
            this.valueRange = range2;
        }

        public Range nameRange() {
            return this.nameRange;
        }

        public Range valueRange() {
            return this.valueRange;
        }

        public String toString() {
            return StringUtil.releaseBuilder(StringUtil.borrowBuilder().append(this.nameRange).append('=').append(this.valueRange));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AttributeRange attributeRange = (AttributeRange) obj;
            if (this.nameRange.equals(attributeRange.nameRange)) {
                return this.valueRange.equals(attributeRange.valueRange);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.nameRange, this.valueRange);
        }
    }
}
