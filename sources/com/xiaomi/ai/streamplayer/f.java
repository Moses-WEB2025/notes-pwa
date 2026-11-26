package com.xiaomi.ai.streamplayer;

import com.xiaomi.ai.mibrain.MibrainMp3Decoder;
import com.xiaomi.ai.utils.Log;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/* JADX INFO: loaded from: classes4.dex */
public class f extends a {
    private static final String a = "Mp3SoftDecoder";
    private PipedOutputStream d;
    private boolean f;
    private MibrainMp3Decoder b = new MibrainMp3Decoder(new g(this));
    private BlockingQueue<a.C0118a> e = new LinkedBlockingDeque();
    private Thread g = new Thread(new h(this));
    private PipedInputStream c = new PipedInputStream();

    f() {
        try {
            this.d = new PipedOutputStream(this.c);
        } catch (IOException e) {
            Log.e(a, "PIPE IO ", e);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    void a() {
        this.b.init();
        this.g.start();
    }

    public void a(byte[] bArr, int i) {
        if (bArr != null) {
            try {
                this.d.write(bArr, 0, i);
            } catch (IOException e) {
                Log.e(a, "write ", e);
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    void a(byte[] bArr, int i, int i2) {
        try {
            this.d.write(bArr, 0, i2);
        } catch (IOException e) {
            Log.e(a, "putEncodedBuffe ", e);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    void b() {
        synchronized (this) {
            if (!this.f) {
                this.b.cancel();
                this.g.interrupt();
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public void c() {
        synchronized (this) {
            if (!this.f) {
                this.b.cancel();
                this.g.interrupt();
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    public void d() {
        try {
            this.d.close();
        } catch (IOException e) {
            Log.e(a, "end ", e);
        }
    }

    @Override // com.xiaomi.ai.streamplayer.a
    a.C0118a e() {
        try {
            return this.e.take();
        } catch (InterruptedException e) {
            Log.e(a, "getDecodedBuffer ", e);
            return null;
        }
    }

    void f() {
        this.b.start();
        try {
            this.c.close();
        } catch (IOException unused) {
        }
        a.C0118a c0118a = new a.C0118a();
        c0118a.d = true;
        try {
            this.e.put(c0118a);
        } catch (InterruptedException e) {
            Log.e(a, "e", e);
        }
    }
}
