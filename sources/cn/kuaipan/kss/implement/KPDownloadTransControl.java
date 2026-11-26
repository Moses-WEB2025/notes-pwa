package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDownload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class KPDownloadTransControl implements KssDownload.DownloadTransControl {
    private int m_curPos = 0;
    private int m_startPos = 0;
    private KssDownload.DownloadTransControl.EndState m_endState = KssDownload.DownloadTransControl.EndState.Transing;
    private OutputStream m_outputStream = null;

    @Override // cn.kuaipan.kss.KssDownload.DownloadTransControl
    public int getStartPos() {
        return this.m_startPos;
    }

    @Override // cn.kuaipan.kss.KssDownload.DownloadTransControl
    public int addData(byte[] bArr, int i) throws IOException {
        OutputStream outputStream = this.m_outputStream;
        if (outputStream == null) {
            throw new FileNotFoundException();
        }
        outputStream.write(bArr, 0, i);
        this.m_curPos += i;
        return i;
    }

    @Override // cn.kuaipan.kss.KssDownload.DownloadTransControl
    public void setEndState(KssDownload.DownloadTransControl.EndState endState) {
        this.m_endState = endState;
    }

    public int setStartPos(int i) {
        if (i < 0) {
            return i;
        }
        int i2 = this.m_curPos;
        this.m_startPos = i;
        this.m_curPos = i;
        return i2;
    }

    public KssDownload.DownloadTransControl.EndState getEndState() {
        return this.m_endState;
    }

    public int getCurrentPos() {
        return this.m_curPos;
    }

    public void init(File file, boolean z) throws FileNotFoundException {
        _init();
        if (!z && file.exists()) {
            file.delete();
        }
        this.m_outputStream = new FileOutputStream(file, z);
        if (z) {
            setStartPos((int) file.length());
        }
    }

    public void init(OutputStream outputStream) {
        _init();
        this.m_outputStream = outputStream;
    }

    public void terminel() {
        close();
        this.m_outputStream = null;
    }

    public void close() {
        OutputStream outputStream = this.m_outputStream;
        if (outputStream != null) {
            try {
                outputStream.flush();
                this.m_outputStream.close();
            } catch (Exception unused) {
            }
        }
    }

    private void _init() {
        this.m_curPos = 0;
        this.m_startPos = 0;
        this.m_endState = KssDownload.DownloadTransControl.EndState.Transing;
        this.m_outputStream = null;
    }
}
