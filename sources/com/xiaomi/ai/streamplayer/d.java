package com.xiaomi.ai.streamplayer;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;
import com.xiaomi.ai.utils.Log;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public class d extends com.xiaomi.ai.streamplayer.a {
    private static final String e = "Mp3HardDecoder";
    private static final String g = "OMX.google.mp3.decoder";
    private PipedOutputStream a;
    private PipedInputStream b;
    private i c;
    private boolean d;
    private MediaCodec f;

    private class a implements j {
        private boolean b;

        private a() {
        }

        @Override // com.xiaomi.ai.streamplayer.j
        public void a(i iVar) {
        }

        @Override // com.xiaomi.ai.streamplayer.j
        public void a(i iVar, Object obj, byte[] bArr) {
            if (d.this.d) {
                d.this.c();
            } else {
                d.this.a(bArr);
                this.b = true;
            }
        }

        @Override // com.xiaomi.ai.streamplayer.j
        public void a(i iVar, String str) {
            d.this.c();
        }

        @Override // com.xiaomi.ai.streamplayer.j
        public void b(i iVar) {
            if (d.this.d || !this.b) {
                d.this.c();
            } else {
                d.this.f();
            }
        }
    }

    public d() {
        b bVar = new b();
        this.c = bVar;
        bVar.a(new a());
        this.b = new PipedInputStream(51200);
        try {
            this.a = new PipedOutputStream(this.b);
        } catch (IOException e2) {
            Log.e(e, "Mp3HardDecoder ", e2);
        }
        this.c.a(this.b);
    }

    @Override // com.xiaomi.ai.streamplayer.a
    void a() {
        this.c.a();
        try {
            try {
                this.f = MediaCodec.createByCodecName(g);
            } catch (IllegalArgumentException e2) {
                Log.e(e, " start createByCodecName ", e2);
            }
            if (this.f == null) {
                Log.e(e, "OMX.google.mp3.decoder not get");
                this.f = MediaCodec.createDecoderByType(OutputMimeType.MP3);
            }
            MediaFormat mediaFormat = new MediaFormat();
            mediaFormat.setString("mime", OutputMimeType.MP3);
            this.f.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
            this.f.start();
        } catch (IOException e3) {
            throw new RuntimeException(e3);
        }
    }

    void a(byte[] bArr) {
        try {
            int iDequeueInputBuffer = this.f.dequeueInputBuffer(-1L);
            if (iDequeueInputBuffer < 0) {
                throw new RuntimeException("dequeueInputBuffer-1");
            }
            ByteBuffer[] inputBuffers = this.f.getInputBuffers();
            if (inputBuffers.length <= iDequeueInputBuffer) {
                Log.e(e, " putBuffer byteBuffers.length > inputBufIndex false");
                return;
            }
            ByteBuffer byteBuffer = inputBuffers[iDequeueInputBuffer];
            byteBuffer.clear();
            byteBuffer.put(bArr);
            this.f.queueInputBuffer(iDequeueInputBuffer, 0, bArr.length, 0L, 0);
        } catch (IllegalStateException e2) {
            Log.e(e, "release ", e2);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public void a(byte[] bArr, int i, int i2) {
        if (bArr == null || i + i2 > bArr.length || i2 <= 0) {
            return;
        }
        try {
            this.a.write(bArr, i, i2);
        } catch (IOException e2) {
            Log.e(e, "putEncodedBuffer ", e2);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    void b() {
        try {
            this.a.close();
        } catch (IOException e2) {
            Log.e(e, "release ", e2);
        }
        try {
            this.b.close();
        } catch (IOException e3) {
            Log.e(e, "release ", e3);
        }
        try {
            this.f.stop();
        } catch (IllegalStateException e4) {
            Log.e(e, "stop ", e4);
        }
        this.f.release();
        Log.e(e, "Mp3HardDecoder release");
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public void c() {
        d();
        this.c.b();
        this.d = true;
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public void d() {
        try {
            this.a.close();
        } catch (IOException e2) {
            Log.e(e, "release " + e2);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public com.xiaomi.ai.streamplayer.a.C0118a e() {
        if (this.d) {
            return null;
        }
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.f.dequeueOutputBuffer(bufferInfo, 0L);
            com.xiaomi.ai.streamplayer.a.C0118a c0118a = new com.xiaomi.ai.streamplayer.a.C0118a();
            if (iDequeueOutputBuffer == -2) {
                MediaFormat outputFormat = this.f.getOutputFormat();
                c0118a.c = 16;
                c0118a.b = outputFormat.getInteger("channel-count");
                c0118a.a = outputFormat.getInteger("sample-rate");
            }
            if (iDequeueOutputBuffer >= 0) {
                ByteBuffer byteBuffer = this.f.getOutputBuffers()[iDequeueOutputBuffer];
                byte[] bArr = new byte[bufferInfo.size];
                c0118a.d = bufferInfo.flags == 4;
                byteBuffer.get(bArr);
                c0118a.e = bArr;
                byteBuffer.clear();
                this.f.releaseOutputBuffer(iDequeueOutputBuffer, false);
            }
            return c0118a;
        } catch (IllegalStateException e2) {
            Log.e(e, "getDecodedBuffer ", e2);
            return null;
        }
    }

    void f() {
        try {
            int iDequeueInputBuffer = this.f.dequeueInputBuffer(-1L);
            if (iDequeueInputBuffer < 0) {
                throw new RuntimeException("dequeueInputBuffer-1");
            }
            ByteBuffer[] inputBuffers = this.f.getInputBuffers();
            if (inputBuffers.length <= iDequeueInputBuffer) {
                Log.e(e, " putEofMsg byteBuffers.length > inputBufIndex false");
            } else {
                inputBuffers[iDequeueInputBuffer].clear();
                this.f.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
            }
        } catch (IllegalStateException e2) {
            Log.e(e, "release ", e2);
        }
    }
}
