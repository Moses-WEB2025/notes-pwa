package com.miui.handwriting.common.data.bean;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SearchItemWrapper {
    String TextContent;
    String entPath;
    List<String> recognizedFilePaths;
    String thumbnailPath;

    public SearchItemWrapper(String str, List<String> list, String str2, String str3) {
        this.entPath = str;
        this.recognizedFilePaths = list;
        this.TextContent = str2;
        this.thumbnailPath = str3;
    }

    public String getEntPath() {
        return this.entPath;
    }

    public void setEntPath(String str) {
        this.entPath = str;
    }

    public List<String> getRecognizedFilePaths() {
        return this.recognizedFilePaths;
    }

    public void setRecognizedFilePaths(List<String> list) {
        this.recognizedFilePaths = list;
    }

    public String getTextContent() {
        return this.TextContent;
    }

    public void setTextContent(String str) {
        this.TextContent = str;
    }

    public String getThumbnailPath() {
        return this.thumbnailPath;
    }

    public void setThumbnailPath(String str) {
        this.thumbnailPath = str;
    }
}
