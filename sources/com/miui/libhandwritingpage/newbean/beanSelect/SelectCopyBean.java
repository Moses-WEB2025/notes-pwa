package com.miui.libhandwritingpage.newbean.beanSelect;

import android.graphics.PointF;
import xc.richtext.data.bean.IBufferBean;

/* JADX INFO: loaded from: classes3.dex */
public class SelectCopyBean {
    private IBufferBean mBuffer;
    private byte[] mPasteInfo;
    private PointF mSpannedOffset = new PointF();
    private PointF mCurOffset = new PointF();

    public SelectCopyBean(byte[] bArr, IBufferBean iBufferBean) {
        this.mPasteInfo = bArr;
        this.mBuffer = iBufferBean;
    }

    public byte[] getCurPasteInfo() {
        return this.mPasteInfo;
    }

    public IBufferBean getBufferBean() {
        return this.mBuffer;
    }

    public void setSelectedOffset(PointF pointF, PointF pointF2) {
        this.mSpannedOffset = pointF;
        this.mCurOffset = pointF2;
    }

    public PointF getCopySpannedOffset() {
        return this.mSpannedOffset;
    }

    public PointF getCopyCurOffset() {
        return this.mCurOffset;
    }

    public void clear() {
        this.mBuffer = null;
        this.mPasteInfo = null;
        this.mSpannedOffset = null;
        this.mCurOffset = null;
    }
}
