package xc.engine.storage.ent.ink_ent;

/* JADX INFO: loaded from: classes6.dex */
public class InkEnt {
    public int exportBottom;
    public int exportLeft;
    public int exportRight;
    public int exportTop;
    public float fixWidth;
    public int heightMM;
    public boolean isCustom;
    public boolean isFixSize;
    public boolean isUnLimit;
    public int mBackgroundColor;
    public short mBgTextureIndex;
    public byte[] mDataBytes;
    public String mDataCachePath;
    public short mHeight;
    public String mPageType;
    public String mPdfFileId;
    public int mPdfPageIndex;
    public short mWidth;
    public int maxHeight;
    public int maxWidth;
    public int pageSizeType;
    public int sizeUnit;
    public int standardWidth;
    public int unLimitBottom;
    public int unLimitEnd;
    public int unLimitStart;
    public boolean unLimitToNormal;
    public int unLimitTop;
    public boolean useNewSize;
    public int widthMM;

    public InkEnt() {
    }

    public InkEnt(byte[] bArr, String str) {
        this.mDataBytes = bArr;
        this.mPageType = str;
    }

    public InkEnt(String str, String str2) {
        this.mDataCachePath = str;
        this.mPageType = str2;
    }

    public String getDataCachePath() {
        return this.mDataCachePath;
    }

    public void setDataCachePath(String str) {
        this.mDataCachePath = str;
    }

    public short getWidth() {
        return this.mWidth;
    }

    public void setWidth(short s) {
        this.mWidth = s;
    }

    public short getHeight() {
        return this.mHeight;
    }

    public void setHeight(short s) {
        this.mHeight = s;
    }

    public byte[] getDataBytes() {
        return this.mDataBytes;
    }

    public void setDataBytes(byte[] bArr) {
        this.mDataBytes = bArr;
    }

    public String getPageType() {
        return this.mPageType;
    }

    public void setPageType(String str) {
        this.mPageType = str;
    }

    public String getPdfFileId() {
        return this.mPdfFileId;
    }

    public void setPdfFileId(String str) {
        this.mPdfFileId = str;
    }

    public int getPdfPageIndex() {
        return this.mPdfPageIndex;
    }

    public void setPdfPageIndex(int i) {
        this.mPdfPageIndex = i;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public short getBgTextureIndex() {
        return this.mBgTextureIndex;
    }

    public void setBgTextureIndex(short s) {
        this.mBgTextureIndex = s;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean z) {
        this.isCustom = z;
    }

    public boolean isUseNewSize() {
        return this.useNewSize;
    }

    public void setUseNewSize(boolean z) {
        this.useNewSize = z;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxWidth(int i) {
        this.maxWidth = i;
    }

    public int getSizeUnit() {
        return this.sizeUnit;
    }

    public void setSizeUnit(int i) {
        this.sizeUnit = i;
    }

    public int getWidthMM() {
        return this.widthMM;
    }

    public void setWidthMM(int i) {
        this.widthMM = i;
    }

    public int getHeightMM() {
        return this.heightMM;
    }

    public void setHeightMM(int i) {
        this.heightMM = i;
    }

    public boolean isUnLimit() {
        return this.isUnLimit;
    }

    public void setUnLimit(boolean z) {
        this.isUnLimit = z;
    }

    public int getUnLimitStart() {
        return this.unLimitStart;
    }

    public void setUnLimitStart(int i) {
        this.unLimitStart = i;
    }

    public int getUnLimitEnd() {
        return this.unLimitEnd;
    }

    public void setUnLimitEnd(int i) {
        this.unLimitEnd = i;
    }

    public int getUnLimitTop() {
        return this.unLimitTop;
    }

    public void setUnLimitTop(int i) {
        this.unLimitTop = i;
    }

    public int getUnLimitBottom() {
        return this.unLimitBottom;
    }

    public void setUnLimitBottom(int i) {
        this.unLimitBottom = i;
    }

    public int getStandardWidth() {
        return this.standardWidth;
    }

    public void setStandardWidth(int i) {
        this.standardWidth = i;
    }

    public int getExportLeft() {
        return this.exportLeft;
    }

    public void setExportLeft(int i) {
        this.exportLeft = i;
    }

    public int getExportTop() {
        return this.exportTop;
    }

    public void setExportTop(int i) {
        this.exportTop = i;
    }

    public int getExportRight() {
        return this.exportRight;
    }

    public void setExportRight(int i) {
        this.exportRight = i;
    }

    public int getExportBottom() {
        return this.exportBottom;
    }

    public void setExportBottom(int i) {
        this.exportBottom = i;
    }

    public boolean isFixSize() {
        return this.isFixSize;
    }

    public void setFixSize(boolean z) {
        this.isFixSize = z;
    }

    public float getFixWidth() {
        return this.fixWidth;
    }

    public void setFixWidth(float f) {
        this.fixWidth = f;
    }

    public boolean getUnLimitToNormal() {
        return this.unLimitToNormal;
    }

    public void setUnLimitToNormal(boolean z) {
        this.unLimitToNormal = z;
    }

    public int getPageSizeType() {
        return this.pageSizeType;
    }

    public void setPageSizeType(int i) {
        this.pageSizeType = i;
    }
}
