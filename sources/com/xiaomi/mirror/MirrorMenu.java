package com.xiaomi.mirror;

import android.app.PendingIntent;
import android.net.Uri;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public class MirrorMenu {
    public static final int TYPE_COMMON = 0;
    public static final int TYPE_NEW_DISPLAY_OPEN = 1;
    public static final int TYPE_PC_OPEN = 2;

    public static class Builder {
        public MirrorMenu build() {
            throw new UnsupportedOperationException("Stub!");
        }

        public Builder setCallback(Callback callback) {
            throw new UnsupportedOperationException("Stub!");
        }

        public Builder setLabel(CharSequence charSequence) {
            throw new UnsupportedOperationException("Stub!");
        }
    }

    public interface Callback {
        void onClick(View view, MirrorMenu mirrorMenu);
    }

    public static class NewDisplayOpenBuilder {
        public MirrorMenu build() {
            throw new UnsupportedOperationException("Stub!");
        }

        public NewDisplayOpenBuilder setLabel(CharSequence charSequence) {
            throw new UnsupportedOperationException("Stub!");
        }

        public NewDisplayOpenBuilder setPendingIntent(PendingIntent pendingIntent) {
            throw new UnsupportedOperationException("Stub!");
        }
    }

    public static class PcOpenBuilder {
        public MirrorMenu build() {
            throw new UnsupportedOperationException("Stub!");
        }

        public PcOpenBuilder setExtra(String str) {
            throw new UnsupportedOperationException("Stub!");
        }

        public PcOpenBuilder setLabel(CharSequence charSequence) {
            throw new UnsupportedOperationException("Stub!");
        }

        public PcOpenBuilder setUri(Uri uri) {
            throw new UnsupportedOperationException("Stub!");
        }
    }

    public CharSequence getLabel() {
        throw new UnsupportedOperationException("Stub!");
    }

    public Callback getListener() {
        throw new UnsupportedOperationException("Stub!");
    }

    public PendingIntent getPendingIntent() {
        throw new UnsupportedOperationException("Stub!");
    }

    public int getType() {
        throw new UnsupportedOperationException("Stub!");
    }

    public Uri getUri() {
        throw new UnsupportedOperationException("Stub!");
    }

    public boolean needCallRemote() {
        throw new UnsupportedOperationException("Stub!");
    }
}
