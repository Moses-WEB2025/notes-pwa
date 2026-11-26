package cn.kuaipan.android.http;

import cn.kuaipan.android.exception.KscTransferStopByCallerException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ProcessMonitorOutputStream extends FilterOutputStream {
    private final IKscTransferListener mListener;
    private final KscSpeedMonitor mMonitor;
    private final boolean mSendMode;
    private final KssTransferStopper mStopper;

    public ProcessMonitorOutputStream(OutputStream outputStream, KscSpeedMonitor kscSpeedMonitor, IKscTransferListener iKscTransferListener, boolean z) {
        this(outputStream, kscSpeedMonitor, iKscTransferListener, null, z);
    }

    public ProcessMonitorOutputStream(OutputStream outputStream, KscSpeedMonitor kscSpeedMonitor, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, boolean z) {
        super(outputStream);
        this.mMonitor = kscSpeedMonitor;
        this.mListener = iKscTransferListener;
        this.mStopper = kssTransferStopper;
        this.mSendMode = z;
    }

    private void process(long j) throws IOException {
        KssTransferStopper kssTransferStopper = this.mStopper;
        if (kssTransferStopper != null && kssTransferStopper.checkStop()) {
            throw new IOException("@ ProcessMonitorOutputStream::process()", new KscTransferStopByCallerException());
        }
        if (j >= 0) {
            KscSpeedMonitor kscSpeedMonitor = this.mMonitor;
            if (kscSpeedMonitor != null) {
                kscSpeedMonitor.recode(j);
            }
            IKscTransferListener iKscTransferListener = this.mListener;
            if (iKscTransferListener != null) {
                if (this.mSendMode) {
                    iKscTransferListener.sended(j);
                } else {
                    iKscTransferListener.received(j);
                }
            }
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        process(i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        super.write(i);
        process(1L);
    }
}
