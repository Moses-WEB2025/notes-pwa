package androidx.constraintlayout.core.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class Chain extends Helper {
    protected static final Map<Style, String> styleMap;
    private Style mStyle;
    protected ArrayList<Ref> references;

    public enum Style {
        PACKED,
        SPREAD,
        SPREAD_INSIDE
    }

    static {
        HashMap map = new HashMap();
        styleMap = map;
        map.put(Style.SPREAD, "'spread'");
        map.put(Style.SPREAD_INSIDE, "'spread_inside'");
        map.put(Style.PACKED, "'packed'");
    }

    public Chain(String str) {
        super(str, new Helper.HelperType(""));
        this.mStyle = null;
        this.references = new ArrayList<>();
    }

    public Style getStyle() {
        return this.mStyle;
    }

    public void setStyle(Style style) {
        this.mStyle = style;
        this.configMap.put("style", styleMap.get(style));
    }

    public String referencesToString() {
        if (this.references.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[");
        Iterator<Ref> it = this.references.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public Chain addReference(Ref ref) {
        this.references.add(ref);
        this.configMap.put("contains", referencesToString());
        return this;
    }

    public Chain addReference(String str) {
        return addReference(Ref.parseStringToRef(str));
    }

    public class Anchor {
        Constraint.Anchor mConnection = null;
        int mGoneMargin = Integer.MIN_VALUE;
        int mMargin;
        final Constraint.Side mSide;

        Anchor(Constraint.Side side) {
            this.mSide = side;
        }

        public String getId() {
            return Chain.this.name;
        }

        public void build(StringBuilder sb) {
            if (this.mConnection != null) {
                sb.append(this.mSide.toString().toLowerCase()).append(":").append(this).append(",\n");
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            if (this.mConnection != null) {
                sb.append("'").append(this.mConnection.getId()).append("','").append(this.mConnection.mSide.toString().toLowerCase()).append("'");
            }
            if (this.mMargin != 0) {
                sb.append(",").append(this.mMargin);
            }
            if (this.mGoneMargin != Integer.MIN_VALUE) {
                if (this.mMargin == 0) {
                    sb.append(",0,").append(this.mGoneMargin);
                } else {
                    sb.append(",").append(this.mGoneMargin);
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
