package com.miui.duokantext;

/* JADX INFO: loaded from: classes2.dex */
public class EncoderFactory {
    private static volatile EncoderFactory factory;

    private EncoderFactory() {
    }

    public static EncoderFactory getInstance() {
        if (factory == null) {
            synchronized (EncoderFactory.class) {
                if (factory == null) {
                    factory = new EncoderFactory();
                }
            }
        }
        return factory;
    }

    public Encoder create(boolean z) {
        Class<?> cls;
        try {
            if (z) {
                cls = Class.forName("com.miui.duokantext.PNGEncoder");
            } else {
                cls = Class.forName("com.miui.duokantext.JPEGEncoder");
            }
            try {
                return (Encoder) cls.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
