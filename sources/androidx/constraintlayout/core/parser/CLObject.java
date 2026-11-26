package androidx.constraintlayout.core.parser;

import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public class CLObject extends CLContainer implements Iterable<CLKey> {
    public CLObject(char[] cArr) {
        super(cArr);
    }

    public static CLObject allocate(char[] cArr) {
        return new CLObject(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toJSON() {
        StringBuilder sb = new StringBuilder(getDebugName() + "{ ");
        boolean z = true;
        for (CLElement cLElement : this.mElements) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(cLElement.toJSON());
        }
        sb.append(" }");
        return sb.toString();
    }

    public String toFormattedJSON() {
        return toFormattedJSON(0, 0);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toFormattedJSON(int i, int i2) {
        StringBuilder sb = new StringBuilder(getDebugName());
        sb.append("{\n");
        boolean z = true;
        for (CLElement cLElement : this.mElements) {
            if (z) {
                z = false;
            } else {
                sb.append(",\n");
            }
            sb.append(cLElement.toFormattedJSON(sBaseIndent + i, i2 - 1));
        }
        sb.append(StringUtils.LF);
        addIndent(sb, i);
        sb.append(StringSubstitutor.DEFAULT_VAR_END);
        return sb.toString();
    }

    @Override // java.lang.Iterable
    public Iterator<CLKey> iterator() {
        return new CLObjectIterator(this);
    }

    private static class CLObjectIterator implements Iterator<CLKey> {
        int mIndex = 0;
        CLObject mObject;

        CLObjectIterator(CLObject cLObject) {
            this.mObject = cLObject;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIndex < this.mObject.size();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public CLKey next() {
            CLKey cLKey = (CLKey) this.mObject.mElements.get(this.mIndex);
            this.mIndex++;
            return cLKey;
        }
    }

    @Override // androidx.constraintlayout.core.parser.CLContainer, androidx.constraintlayout.core.parser.CLElement
    /* JADX INFO: renamed from: clone */
    public CLObject mo47clone() {
        return (CLObject) super.mo47clone();
    }
}
