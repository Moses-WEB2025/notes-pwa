package com.miui.handwriting.common.data.bean;

/* JADX INFO: loaded from: classes3.dex */
public class ConfigBean {
    private String fileName;
    private String name;
    private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public String toString() {
        return "ConfigBean{path='" + this.path + "', fileName='" + this.fileName + "', name='" + this.name + "'}";
    }
}
