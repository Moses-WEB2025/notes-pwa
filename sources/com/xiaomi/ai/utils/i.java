package com.xiaomi.ai.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Environment;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.Field;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public final class i implements o.a {
    private static final String a = "MIUI/debug_log/%s";
    private static final String b = ".log";
    private String c;
    private o.a d;
    private File e;
    private int f;
    private int g;
    private long h;
    private FileOutputStream i;
    private boolean j;

    public i(Context context, File file, int i, int i2, o.a aVar) {
        if (context.getApplicationContext() != context) {
            throw new IllegalArgumentException("appContext is not the application context. ");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxFileSizeInByte should >0. ");
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxFileCount should >0. ");
        }
        this.c = file.getName();
        this.d = aVar;
        this.f = i;
        this.g = i2;
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            this.e = file;
            b();
            return;
        }
        o.a aVar2 = this.d;
        if (aVar2 != null) {
            aVar2.a(6, getClass().getName(), String.format("Construction failure. Failed to create folder %s. ", file.getAbsolutePath()));
            android.util.Log.println(6, getClass().getName(), String.format("Construction failure. Failed to create folder %s. ", file.getAbsolutePath()));
        }
    }

    public i(Context context, String str, int i, int i2, o.a aVar) {
        this(context, new File(Environment.getExternalStorageDirectory(), String.format(a, str)), i, i2, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        a(6, getClass().getName(), "========================== shut down ========================== ");
        FileOutputStream fileOutputStream = this.i;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                android.util.Log.e("ContentValues", "IOException: ", e);
            }
            this.i = null;
        }
        this.j = true;
    }

    private void a(Context context) {
        context.registerReceiver(new j(this), new IntentFilter("android.intent.action.ACTION_SHUTDOWN"), null, null);
    }

    private void b() {
        FileOutputStream fileOutputStream = this.i;
        File file = null;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                android.util.Log.e("ContentValues", "IOException: ", e);
            }
            this.i = null;
        }
        boolean z = false;
        for (int i = 0; i < this.g; i++) {
            File file2 = new File(this.e, this.c + ".log." + i);
            if (!file2.exists() || file2.length() < this.f) {
                file = file2;
                break;
            }
        }
        if (file == null) {
            long jLastModified = new File(this.e, this.c + ".log.0").lastModified();
            long j = 0;
            for (int i2 = 1; i2 < this.g; i2++) {
                long jLastModified2 = new File(this.e, this.c + ".log." + i2).lastModified();
                if (jLastModified2 < jLastModified) {
                    j = i2;
                    jLastModified = jLastModified2;
                }
            }
            file = new File(this.e, this.c + ".log." + j);
        } else {
            z = true;
        }
        try {
            this.i = new FileOutputStream(file, z);
            this.h = z ? file.length() : 0L;
        } catch (FileNotFoundException e2) {
            o.a aVar = this.d;
            if (aVar != null) {
                aVar.a(6, getClass().getName(), String.format("Failed to switch to file %s, error: %s. ", file.getAbsolutePath(), e2));
                android.util.Log.println(6, getClass().getName(), String.format("Failed to switch to file %s, error: %s. ", file.getAbsolutePath(), e2));
            }
        }
    }

    @Override // com.xiaomi.ai.utils.o.a
    public synchronized void a(int i, String str, String str2) {
        o.a aVar = this.d;
        if (aVar != null) {
            aVar.a(i, str, str2);
        }
        if (a(i)) {
            if (this.j) {
                o.a aVar2 = this.d;
                if (aVar2 != null) {
                    aVar2.a(6, getClass().getName(), "Shutdown state. Skip outputing. ");
                    android.util.Log.println(6, getClass().getName(), "Shutdown state. Skip outputing. ");
                }
                return;
            }
            byte[] bytes = String.format("LV:%s, TM: %s, TAG: %s, TH: %s, MSG: %s\n", b(i), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), str, Thread.currentThread().getName(), str2).getBytes();
            this.h += (long) bytes.length;
            FileOutputStream fileOutputStream = this.i;
            if (fileOutputStream == null) {
                o.a aVar3 = this.d;
                if (aVar3 != null) {
                    aVar3.a(6, getClass().getName(), "Null output stream. Skip outputing. ");
                    android.util.Log.println(6, getClass().getName(), "Null output stream. Skip outputing. ");
                }
            } else {
                try {
                    fileOutputStream.write(bytes);
                    this.i.flush();
                } catch (IOException e) {
                    o.a aVar4 = this.d;
                    if (aVar4 != null) {
                        aVar4.a(6, getClass().getName(), String.format("Failed to output log, IOException: %s", e));
                        android.util.Log.println(6, getClass().getName(), String.format("Failed to output log, IOException: %s", e));
                    }
                }
            }
            if (this.h >= this.f) {
                b();
            }
        }
    }

    protected boolean a(int i) {
        return true;
    }

    protected String b(int i) {
        switch (i) {
            case 2:
                return "V";
            case 3:
                return Field.DOUBLE_SIGNATURE_PRIMITIVE;
            case 4:
                return Field.INT_SIGNATURE_PRIMITIVE;
            case 5:
                return ExifInterface.LONGITUDE_WEST;
            case 6:
                return ExifInterface.LONGITUDE_EAST;
            case 7:
                return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
            default:
                return String.valueOf(i);
        }
    }
}
