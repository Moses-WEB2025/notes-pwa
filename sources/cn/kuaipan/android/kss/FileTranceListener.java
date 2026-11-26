package cn.kuaipan.android.kss;

import cn.kuaipan.android.http.IKscTransferListener;

/* JADX INFO: loaded from: classes.dex */
public class FileTranceListener {
    private long mOffset;
    private final boolean mSendMode;
    private final IKscTransferListener mTotalListener;

    public FileTranceListener(IKscTransferListener iKscTransferListener, boolean z) {
        this.mTotalListener = iKscTransferListener;
        this.mSendMode = z;
    }

    public IKscTransferListener getBlockListener(long j) {
        if (this.mSendMode) {
            return new BlockListener(this, j, this.mOffset);
        }
        return new BlockListener(this, this.mOffset, j);
    }

    public void setSendTotal(long j) {
        if (this.mSendMode) {
            return;
        }
        this.mTotalListener.setSendTotal(this.mOffset + j);
    }

    public void setReceiveTotal(long j) {
        if (this.mSendMode) {
            this.mTotalListener.setReceiveTotal(this.mOffset + j);
        }
    }

    public void setSendPos(long j) {
        if (!this.mSendMode) {
            this.mOffset = j;
        }
        this.mTotalListener.setSendPos(j);
    }

    public void setReceivePos(long j) {
        if (this.mSendMode) {
            this.mOffset = j;
        }
        this.mTotalListener.setReceivePos(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sended(long j) {
        if (!this.mSendMode) {
            this.mOffset += j;
        }
        this.mTotalListener.sended(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void received(long j) {
        if (this.mSendMode) {
            this.mOffset += j;
        }
        this.mTotalListener.received(j);
    }

    class BlockListener implements IKscTransferListener {
        private FileTranceListener mParent;
        private long mSendOffset = 0;
        private long mReceiveOffset = 0;

        BlockListener(FileTranceListener fileTranceListener, long j, long j2) {
            this.mParent = fileTranceListener;
            setSendPos(0L);
            setReceivePos(0L);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setSendTotal(long j) {
            this.mParent.setSendTotal(j);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setReceiveTotal(long j) {
            this.mParent.setReceiveTotal(j);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setSendPos(long j) {
            sended(j - this.mSendOffset);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setReceivePos(long j) {
            received(j - this.mReceiveOffset);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void sended(long j) {
            this.mParent.sended(j);
            this.mSendOffset += j;
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void received(long j) {
            this.mParent.received(j);
            this.mReceiveOffset += j;
        }
    }
}
