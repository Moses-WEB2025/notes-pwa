package androidx.constraintlayout.core.parser;

import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class CLArray extends CLContainer {
    public CLArray(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLArray(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        StringBuilder sb = new StringBuilder(getDebugName() + "[");
        boolean z = true;
        for (int i = 0; i < this.mElements.size(); i++) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(this.mElements.get(i).toJSON());
        }
        return ((Object) sb) + "]";
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        String json = toJSON();
        if (i2 <= 0 && json.length() + i < sMaxLine) {
            sb.append(json);
        } else {
            sb.append("[\n");
            boolean z = true;
            for (CLElement cLElement : this.mElements) {
                if (z) {
                    z = false;
                } else {
                    sb.append(",\n");
                }
                addIndent(sb, sBaseIndent + i);
                sb.append(cLElement.toFormattedJSON(sBaseIndent + i, i2 - 1));
            }
            sb.append(StringUtils.LF);
            addIndent(sb, i);
            sb.append("]");
        }
        return sb.toString();
    }
}
