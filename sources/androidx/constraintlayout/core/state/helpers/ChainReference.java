package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ChainReference extends HelperReference {
    protected float mBias;
    private HashMap<String, Float> mMapPostGoneMargin;

    @Deprecated
    protected HashMap<String, Float> mMapPostMargin;
    private HashMap<String, Float> mMapPreGoneMargin;

    @Deprecated
    protected HashMap<String, Float> mMapPreMargin;

    @Deprecated
    protected HashMap<String, Float> mMapWeights;
    protected State.Chain mStyle;

    public ChainReference(State state, State.Helper helper) {
        super(state, helper);
        this.mBias = 0.5f;
        this.mMapWeights = new HashMap<>();
        this.mMapPreMargin = new HashMap<>();
        this.mMapPostMargin = new HashMap<>();
        this.mStyle = State.Chain.SPREAD;
    }

    public State.Chain getStyle() {
        return State.Chain.SPREAD;
    }

    public ChainReference style(State.Chain chain) {
        this.mStyle = chain;
        return this;
    }

    public void addChainElement(String str, float f, float f2, float f3) {
        addChainElement(str, f, f2, f3, 0.0f, 0.0f);
    }

    public void addChainElement(Object obj, float f, float f2, float f3, float f4, float f5) {
        super.add(obj);
        String string = obj.toString();
        if (!Float.isNaN(f)) {
            this.mMapWeights.put(string, Float.valueOf(f));
        }
        if (!Float.isNaN(f2)) {
            this.mMapPreMargin.put(string, Float.valueOf(f2));
        }
        if (!Float.isNaN(f3)) {
            this.mMapPostMargin.put(string, Float.valueOf(f3));
        }
        if (!Float.isNaN(f4)) {
            if (this.mMapPreGoneMargin == null) {
                this.mMapPreGoneMargin = new HashMap<>();
            }
            this.mMapPreGoneMargin.put(string, Float.valueOf(f4));
        }
        if (Float.isNaN(f5)) {
            return;
        }
        if (this.mMapPostGoneMargin == null) {
            this.mMapPostGoneMargin = new HashMap<>();
        }
        this.mMapPostGoneMargin.put(string, Float.valueOf(f5));
    }

    protected float getWeight(String str) {
        if (this.mMapWeights.containsKey(str)) {
            return this.mMapWeights.get(str).floatValue();
        }
        return -1.0f;
    }

    protected float getPostMargin(String str) {
        if (this.mMapPostMargin.containsKey(str)) {
            return this.mMapPostMargin.get(str).floatValue();
        }
        return 0.0f;
    }

    protected float getPreMargin(String str) {
        if (this.mMapPreMargin.containsKey(str)) {
            return this.mMapPreMargin.get(str).floatValue();
        }
        return 0.0f;
    }

    float getPostGoneMargin(String str) {
        HashMap<String, Float> map = this.mMapPostGoneMargin;
        if (map == null || !map.containsKey(str)) {
            return 0.0f;
        }
        return this.mMapPostGoneMargin.get(str).floatValue();
    }

    float getPreGoneMargin(String str) {
        HashMap<String, Float> map = this.mMapPreGoneMargin;
        if (map == null || !map.containsKey(str)) {
            return 0.0f;
        }
        return this.mMapPreGoneMargin.get(str).floatValue();
    }

    public float getBias() {
        return this.mBias;
    }

    @Override // androidx.constraintlayout.core.state.ConstraintReference
    public ChainReference bias(float f) {
        this.mBias = f;
        return this;
    }
}
