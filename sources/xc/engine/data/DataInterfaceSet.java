package xc.engine.data;

/* JADX INFO: loaded from: classes6.dex */
public class DataInterfaceSet {
    public IDataSwapListener mIDataSwapListener;
    public IPenPropTransform mIPenPropTransform;

    public IPenPropTransform getPenPropTransform() {
        return this.mIPenPropTransform;
    }

    public void setPenPropTransform(IPenPropTransform iPenPropTransform) {
        this.mIPenPropTransform = iPenPropTransform;
    }

    public IDataSwapListener getDataSwapListener() {
        return this.mIDataSwapListener;
    }

    public void setDataSwapListener(IDataSwapListener iDataSwapListener) {
        this.mIDataSwapListener = iDataSwapListener;
    }
}
