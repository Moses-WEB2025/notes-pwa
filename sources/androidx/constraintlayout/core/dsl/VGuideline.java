package androidx.constraintlayout.core.dsl;

/* JADX INFO: loaded from: classes.dex */
public class VGuideline extends Guideline {
    public VGuideline(String str) {
        super(str);
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.VERTICAL_GUIDELINE));
    }

    public VGuideline(String str, String str2) {
        super(str);
        this.config = str2;
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.VERTICAL_GUIDELINE));
        this.configMap = convertConfigToMap();
    }
}
