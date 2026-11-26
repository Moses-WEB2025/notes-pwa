package com.xiaomi.opensdk.file.model;

import cn.kuaipan.android.kss.thumbnail.ThumbnailUploadParameter;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;

/* JADX INFO: loaded from: classes4.dex */
public class UploadContext {
    private boolean isNeedRequestUpload;
    private String mCommitString;
    private final UploadEncryptInfo mEncryptInfo;
    private String mKssString;
    private final MiCloudFileListener mListener;
    private final KssUploadFile mLocalFile;
    private String mSha1;
    private final MiCloudTransferStopper mStopper;
    private String mThumbnailCommitString;
    private ThumbnailUploadParameter mThumbnailUploadParam;
    private String mUploadID;
    private UploadParameter mUploadParam;

    public UploadContext(KssUploadFile kssUploadFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper, UploadEncryptInfo uploadEncryptInfo) {
        this.mLocalFile = kssUploadFile;
        this.mListener = miCloudFileListener;
        this.mStopper = miCloudTransferStopper;
        this.mEncryptInfo = uploadEncryptInfo;
    }

    public UploadEncryptInfo getEncryptInfo() {
        return this.mEncryptInfo;
    }

    public KssUploadFile getLocalFile() {
        return this.mLocalFile;
    }

    public String getFilePath() {
        return this.mLocalFile.fileAbsPath;
    }

    public long getFileSize() {
        return this.mLocalFile.fileSize;
    }

    public MiCloudFileListener getListener() {
        return this.mListener;
    }

    public MiCloudTransferStopper getStopper() {
        return this.mStopper;
    }

    public UploadParameter getUploadParam() {
        return this.mUploadParam;
    }

    public void setThumbnailUploadParam(ThumbnailUploadParameter thumbnailUploadParameter) {
        this.mThumbnailUploadParam = thumbnailUploadParameter;
    }

    public ThumbnailUploadParameter getThumbnailUploadParam() {
        return this.mThumbnailUploadParam;
    }

    public void setUploadParam(UploadParameter uploadParameter) {
        this.mUploadParam = uploadParameter;
    }

    public boolean isNeedRequestUpload() {
        return this.isNeedRequestUpload;
    }

    public void setNeedRequestUpload(boolean z) {
        this.isNeedRequestUpload = z;
    }

    public String getKssString() {
        return this.mKssString;
    }

    public void setKssString(String str) {
        this.mKssString = str;
    }

    public String getSha1() {
        return this.mSha1;
    }

    public void setSha1(String str) {
        this.mSha1 = str;
    }

    public String getCommitString() {
        return this.mCommitString;
    }

    public void setCommitString(String str) {
        this.mCommitString = str;
    }

    public void setThumbnailCommitString(String str) {
        this.mThumbnailCommitString = str;
    }

    public String getThumbnailCommitString() {
        return this.mThumbnailCommitString;
    }

    public void setUploadId(String str) {
        this.mUploadID = str;
    }

    public String getUploadId() {
        return this.mUploadID;
    }
}
