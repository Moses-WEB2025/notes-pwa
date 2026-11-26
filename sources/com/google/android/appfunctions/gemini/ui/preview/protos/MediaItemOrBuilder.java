package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzcg;
import com.google.android.appfunctions.schema.internal.dependencies.zzt;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public interface MediaItemOrBuilder extends zzcg {
    String getMediaUrl();

    zzt getMediaUrlBytes();

    MediaItem.Type getType();

    int getTypeValue();

    MediaItem.VideoMetadata getVideoMetadata();

    boolean hasMediaUrl();

    boolean hasType();

    boolean hasVideoMetadata();
}
