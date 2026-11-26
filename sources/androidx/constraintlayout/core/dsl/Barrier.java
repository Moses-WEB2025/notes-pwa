package androidx.constraintlayout.core.dsl;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Barrier extends Helper {
    private Constraint.Side mDirection;
    private int mMargin;
    private ArrayList<Ref> references;

    public Barrier(String str) {
        super(str, new Helper.HelperType(typeMap.get(Helper.Type.BARRIER)));
        this.mDirection = null;
        this.mMargin = Integer.MIN_VALUE;
        this.references = new ArrayList<>();
    }

    public Barrier(String str, String str2) {
        super(str, new Helper.HelperType(typeMap.get(Helper.Type.BARRIER)), str2);
        this.mDirection = null;
        this.mMargin = Integer.MIN_VALUE;
        this.references = new ArrayList<>();
        this.configMap = convertConfigToMap();
        if (this.configMap.containsKey("contains")) {
            Ref.addStringToReferences(this.configMap.get("contains"), this.references);
        }
    }

    public Constraint.Side getDirection() {
        return this.mDirection;
    }

    public void setDirection(Constraint.Side side) {
        this.mDirection = side;
        this.configMap.put("direction", sideMap.get(side));
    }

    public int getMargin() {
        return this.mMargin;
    }

    public void setMargin(int i) {
        this.mMargin = i;
        this.configMap.put("margin", String.valueOf(i));
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

    public Barrier addReference(Ref ref) {
        this.references.add(ref);
        this.configMap.put("contains", referencesToString());
        return this;
    }

    public Barrier addReference(String str) {
        return addReference(Ref.parseStringToRef(str));
    }
}
