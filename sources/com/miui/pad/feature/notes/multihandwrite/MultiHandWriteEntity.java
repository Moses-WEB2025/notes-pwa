package com.miui.pad.feature.notes.multihandwrite;

import ch.qos.logback.core.CoreConstants;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MultiHandWriteEntity {
    public String canvasBgType;
    public long createdTime;
    private int currentPageNum;
    private float currentPageScale;
    public List<String> deleteImages;
    public String entName;
    public long folderId;
    public long id;
    public List<String> images;
    public boolean isEmpty = true;
    private boolean isSuniaSignalPage = false;
    private String mindContent;
    public long modifiedTime;
    private String noteType;
    public List<String> recognizedFile;
    public String snippet;
    public long stickTime;
    public String thumbnail;
    public String title;
    public List<WebImage> webImages;

    public int getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(int i) {
        this.currentPageNum = i;
    }

    public void setCurrentPageScale(float f) {
        this.currentPageScale = f;
    }

    public float getCurrentPageScale() {
        return this.currentPageScale;
    }

    public String observeEntDir() {
        try {
            StringBuilder sbAppend = new StringBuilder().append(AttachmentUtils.getAttachmentDir(NoteApp.getInstance())).append(File.separator);
            String str = this.entName;
            return sbAppend.append(str.substring(0, str.lastIndexOf(File.separator))).toString();
        } catch (Exception e) {
            Logger.INSTANCE.e("MultiHandWriteEntity", "Exception e = " + e);
            return "";
        }
    }

    public String getLoadPath() {
        try {
            String str = this.entName;
            return str.substring(0, str.lastIndexOf(File.separator));
        } catch (Exception e) {
            Logger.INSTANCE.e("MultiHandWriteEntity", "Exception e = " + e);
            return "";
        }
    }

    public long getSortDate() {
        if (PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0) {
            return this.createdTime;
        }
        return this.modifiedTime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getFolderId() {
        return this.folderId;
    }

    public void setFolderId(long j) {
        this.folderId = j;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(long j) {
        this.createdTime = j;
    }

    public long getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(long j) {
        this.modifiedTime = j;
    }

    public long getStickTime() {
        return this.stickTime;
    }

    public void setStickTime(long j) {
        this.stickTime = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getEntName() {
        return this.entName;
    }

    public void setEntName(String str) {
        this.entName = str;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> list) {
        this.images = list;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String str) {
        this.thumbnail = str;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String str) {
        this.snippet = str;
    }

    public void setNoteType(String str) {
        this.noteType = str;
    }

    public String getNoteType() {
        return this.noteType;
    }

    public void setMindContent(String str) {
        this.mindContent = str;
    }

    public String getMindContent() {
        return this.mindContent;
    }

    public List<String> getRecognizedFile() {
        return this.recognizedFile;
    }

    public void setRecognizedFile(List<String> list) {
        this.recognizedFile = list;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void setEmpty(boolean z) {
        this.isEmpty = z;
    }

    public String getCanvasBgType() {
        return this.canvasBgType;
    }

    public void setCanvasBgType(String str) {
        this.canvasBgType = str;
    }

    public List<WebImage> getWebImages() {
        return this.webImages;
    }

    public void setWebImages(List<WebImage> list) {
        this.webImages = list;
    }

    public void setIsSuniaSignalPage(boolean z) {
        this.isSuniaSignalPage = z;
    }

    public boolean isSuniaSignalPage() {
        return this.isSuniaSignalPage;
    }

    public String toString() {
        return "MultiHandWriteEntity{id=" + this.id + ", folderId=" + this.folderId + ", createdTime=" + this.createdTime + ", modifiedTime=" + this.modifiedTime + ", stickTime=" + this.stickTime + ", title='" + this.title + "', entName='" + this.entName + "', images=" + this.images + ", thumbnail='" + this.thumbnail + "', snippet='" + this.snippet + "', recognizedFile=" + this.recognizedFile + ", isEmpty=" + this.isEmpty + ", canvasBgType='" + this.canvasBgType + "', webImages=" + this.webImages + CoreConstants.CURLY_RIGHT;
    }
}
