package androidx.constraintlayout.core.dsl;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Keys {
    protected String unpack(String[] strArr) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (i < strArr.length) {
            sb.append(i == 0 ? "'" : ",'");
            sb.append(strArr[i]);
            sb.append("'");
            i++;
        }
        sb.append("]");
        return sb.toString();
    }

    protected void append(StringBuilder sb, String str, int i) {
        if (i != Integer.MIN_VALUE) {
            sb.append(str);
            sb.append(":'").append(i).append("',\n");
        }
    }

    protected void append(StringBuilder sb, String str, String str2) {
        if (str2 != null) {
            sb.append(str);
            sb.append(":'").append(str2).append("',\n");
        }
    }

    protected void append(StringBuilder sb, String str, float f) {
        if (Float.isNaN(f)) {
            return;
        }
        sb.append(str);
        sb.append(":").append(f).append(",\n");
    }

    protected void append(StringBuilder sb, String str, String[] strArr) {
        if (strArr != null) {
            sb.append(str);
            sb.append(":").append(unpack(strArr)).append(",\n");
        }
    }

    protected void append(StringBuilder sb, String str, float[] fArr) {
        if (fArr != null) {
            sb.append(str);
            sb.append("percentWidth:").append(Arrays.toString(fArr)).append(",\n");
        }
    }
}
