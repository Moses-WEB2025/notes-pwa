package xc.engine.utils;

/* JADX INFO: loaded from: classes6.dex */
public class PageSizeConfig {
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int PAGE_TYPE_16_9_H = 3;
    public static final int PAGE_TYPE_16_9_V = 4;
    public static final int PAGE_TYPE_1_1 = 5;
    public static final int PAGE_TYPE_A4_H = 1;
    public static final int PAGE_TYPE_A4_V = 2;
    public static final int PAGE_TYPE_CUSTOM = 7;
    public static final int PAGE_TYPE_UNBOUND = 6;
    public static final int PAGE_TYPE_UNKNOWN = 0;
    public int exportBottom;
    public int exportLeft;
    public int exportRight;
    public int exportTop;
    public float fixWidth;
    public boolean fixedSize;
    public int height;
    public boolean isChangeFromUndo;
    public boolean isFixSize;
    public boolean isUnLimit;
    public boolean isUnLimitToNormal;
    public int maxHeight;
    public int maxWidth;
    public int orientation;
    public int pageType;
    public int position;
    public int standardWidth;
    public int type;
    public int width;

    public PageSizeConfig() {
        this.orientation = 0;
    }

    public PageSizeConfig(PageSizeConfig pageSizeConfig) {
        this.width = pageSizeConfig.width;
        this.height = pageSizeConfig.height;
        this.orientation = pageSizeConfig.orientation;
        this.isUnLimit = pageSizeConfig.isUnLimit;
        this.fixedSize = pageSizeConfig.fixedSize;
        this.type = pageSizeConfig.type;
        this.position = pageSizeConfig.position;
        this.maxWidth = pageSizeConfig.maxWidth;
        this.maxHeight = pageSizeConfig.maxHeight;
        this.exportLeft = pageSizeConfig.exportLeft;
        this.exportTop = pageSizeConfig.exportTop;
        this.exportRight = pageSizeConfig.exportRight;
        this.exportBottom = pageSizeConfig.exportBottom;
        this.standardWidth = pageSizeConfig.standardWidth;
        this.fixWidth = pageSizeConfig.fixWidth;
        this.isFixSize = pageSizeConfig.isFixSize;
        this.pageType = pageSizeConfig.pageType;
        this.isUnLimitToNormal = pageSizeConfig.isUnLimitToNormal;
    }

    public PageSizeConfig(int i, int i2, int i3) {
        this.width = i;
        this.height = i2;
        this.orientation = i3;
    }

    public String toString() {
        return "PageSizeConfig width " + this.width + ";  height " + this.height + "; isUnLimit " + this.isUnLimit + " isChangeFromUndo " + this.isChangeFromUndo + "; type " + this.type + ";orientation " + this.orientation + "; position " + this.position + " maxWidth " + this.maxWidth + "; maxHeight " + this.maxHeight;
    }
}
