package com.miui.todo.data.mode;

import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class DataBrowseConfig {
    public int mShowListType;
    public boolean[] pGroupExpandArray;
    public boolean[] pGroupVisibleArray;
    public boolean pShowFinished;
    public boolean pShowGroup;

    private DataBrowseConfig() {
        this.pShowFinished = false;
        this.pShowGroup = true;
        this.pGroupVisibleArray = null;
        this.pGroupExpandArray = null;
        this.mShowListType = -1;
    }

    public DataBrowseConfig(int i) {
        this.pShowFinished = false;
        this.pShowGroup = true;
        this.pGroupVisibleArray = null;
        this.pGroupExpandArray = null;
        this.mShowListType = -1;
        boolean[] zArr = new boolean[i];
        this.pGroupVisibleArray = zArr;
        Arrays.fill(zArr, true);
        boolean[] zArr2 = new boolean[i];
        this.pGroupExpandArray = zArr2;
        Arrays.fill(zArr2, true);
    }

    public void setShowListType(int i) {
        this.mShowListType = i;
    }

    public boolean isShowGroup() {
        return this.pShowGroup;
    }

    public void setShowFinishedData(boolean z) {
        this.pShowFinished = z;
    }

    public void setGroupVisible(int i, boolean z) {
        this.pGroupVisibleArray[i] = z;
    }

    public void setGroupExpand(int i, boolean z) {
        this.pGroupExpandArray[i] = z;
    }

    public void refresh(DataBrowseConfig dataBrowseConfig) {
        setShowFinishedData(dataBrowseConfig.pShowFinished);
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean[] zArr = this.pGroupVisibleArray;
            if (i2 >= zArr.length) {
                break;
            }
            zArr[i2] = dataBrowseConfig.pGroupVisibleArray[i2];
            i2++;
        }
        while (true) {
            boolean[] zArr2 = this.pGroupExpandArray;
            if (i >= zArr2.length) {
                return;
            }
            zArr2[i] = dataBrowseConfig.pGroupExpandArray[i];
            i++;
        }
    }
}
