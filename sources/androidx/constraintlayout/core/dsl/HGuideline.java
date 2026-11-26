package androidx.constraintlayout.core.dsl;

/* JADX INFO: loaded from: classes.dex */
class HGuideline extends Guideline {
    public HGuideline(String str) {
        super(str);
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.HORIZONTAL_GUIDELINE));
    }

    public HGuideline(String str, String str2) {
        super(str);
        this.config = str2;
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.HORIZONTAL_GUIDELINE));
        this.configMap = convertConfigToMap();
    }
}
