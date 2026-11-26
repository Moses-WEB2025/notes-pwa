package xc.engine.storage.ent;

import java.util.ArrayList;
import java.util.List;
import xc.engine.storage.ent.file.FileInfo;
import xc.engine.storage.ent.ink_ent.InkEnt;

/* JADX INFO: loaded from: classes6.dex */
public class PageEntInfo {
    private short mCoverIndex;
    private long mCreateTime;
    private String mExtInfo;
    private long mLastModifyTime;
    private float mPageFilletRadius;
    private short mPageOrientation;
    private String mTitle;
    private int mWidth = 0;
    private int mHeight = 0;
    private final List<FileInfo> mFileInfoList = new ArrayList();
    private List<InkEnt> mInkEntList = new ArrayList();

    public void setLastModifyTime(long j) {
        this.mLastModifyTime = j;
    }

    public long getLastModifyTime() {
        return this.mLastModifyTime;
    }

    public List<FileInfo> getFileInfoList() {
        return this.mFileInfoList;
    }

    public List<InkEnt> getInkEntList() {
        return this.mInkEntList;
    }

    public void setList(List<InkEnt> list) {
        this.mInkEntList = list;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public void setCreateTime(long j) {
        this.mCreateTime = j;
    }

    public float getPageFilletRadius() {
        return this.mPageFilletRadius;
    }

    public void setPageFilletRadius(float f) {
        this.mPageFilletRadius = f;
    }

    public short getPageOrientation() {
        return this.mPageOrientation;
    }

    public void setPageOrientation(short s) {
        this.mPageOrientation = s;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public short getCoverIndex() {
        return this.mCoverIndex;
    }

    public void setCoverIndex(short s) {
        this.mCoverIndex = s;
    }

    public String getExtInfo() {
        return this.mExtInfo;
    }

    public void setExtInfo(String str) {
        this.mExtInfo = str;
    }
}
