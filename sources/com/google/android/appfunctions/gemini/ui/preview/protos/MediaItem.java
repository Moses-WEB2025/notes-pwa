package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzaj;
import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzat;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;
import com.google.android.appfunctions.schema.internal.dependencies.zzay;
import com.google.android.appfunctions.schema.internal.dependencies.zzbc;
import com.google.android.appfunctions.schema.internal.dependencies.zzbd;
import com.google.android.appfunctions.schema.internal.dependencies.zzbe;
import com.google.android.appfunctions.schema.internal.dependencies.zzbl;
import com.google.android.appfunctions.schema.internal.dependencies.zzcg;
import com.google.android.appfunctions.schema.internal.dependencies.zzcn;
import com.google.android.appfunctions.schema.internal.dependencies.zzt;
import com.google.android.appfunctions.schema.internal.dependencies.zzx;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class MediaItem extends zzay<MediaItem, Builder> implements MediaItemOrBuilder {
    private static final MediaItem DEFAULT_INSTANCE;
    public static final int MEDIA_URL_FIELD_NUMBER = 2;
    private static volatile zzcn<MediaItem> PARSER = null;
    public static final int TYPE_FIELD_NUMBER = 1;
    public static final int VIDEO_METADATA_FIELD_NUMBER = 3;
    private int bitField0_;
    private String mediaUrl_ = "";
    private int type_;
    private VideoMetadata videoMetadata_;

    /* JADX INFO: renamed from: com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem$1, reason: invalid class name */
    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[zzax.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[zzax.zzd.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zze.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zzc.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zzf.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zzg.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zza.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[zzax.zzb.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public enum Type implements zzbc {
        TYPE_UNSPECIFIED(0),
        IMAGE(1),
        VIDEO(2),
        UNRECOGNIZED(-1);

        public static final int IMAGE_VALUE = 1;
        public static final int TYPE_UNSPECIFIED_VALUE = 0;
        public static final int VIDEO_VALUE = 2;
        private static final zzbd<Type> internalValueMap = new AnonymousClass1();
        private final int value;

        /* JADX INFO: renamed from: com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem$Type$1, reason: invalid class name */
        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        class AnonymousClass1 implements zzbd<Type> {
            AnonymousClass1() {
            }

            @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbd
            public Type findValueByNumber(int i) {
                return Type.forNumber(i);
            }
        }

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        private static final class TypeVerifier implements zzbe {
            static final zzbe INSTANCE = new TypeVerifier();

            private TypeVerifier() {
            }

            @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbe
            public boolean isInRange(int i) {
                return Type.forNumber(i) != null;
            }
        }

        Type(int i) {
            this.value = i;
        }

        public static Type forNumber(int i) {
            if (i == 0) {
                return TYPE_UNSPECIFIED;
            }
            if (i == 1) {
                return IMAGE;
            }
            if (i != 2) {
                return null;
            }
            return VIDEO;
        }

        public static zzbd<Type> internalGetValueMap() {
            return internalValueMap;
        }

        public static zzbe internalGetVerifier() {
            return TypeVerifier.INSTANCE;
        }

        @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbc
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Override // java.lang.Enum
        public final String toString() {
            return Integer.toString(this.value);
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static final class VideoMetadata extends zzay<VideoMetadata, Builder> implements VideoMetadataOrBuilder {
        private static final VideoMetadata DEFAULT_INSTANCE;
        public static final int DURATION_MS_FIELD_NUMBER = 1;
        private static volatile zzcn<VideoMetadata> PARSER;
        private int bitField0_;
        private long durationMs_;

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        public static final class Builder extends zzas<VideoMetadata, Builder> implements VideoMetadataOrBuilder {
            private Builder() {
                super(VideoMetadata.DEFAULT_INSTANCE);
            }

            /* synthetic */ Builder(byte[] bArr) {
                this();
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((VideoMetadata) this.instance).zzb();
                return this;
            }

            @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem.VideoMetadataOrBuilder
            public long getDurationMs() {
                return ((VideoMetadata) this.instance).getDurationMs();
            }

            @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem.VideoMetadataOrBuilder
            public boolean hasDurationMs() {
                return ((VideoMetadata) this.instance).hasDurationMs();
            }

            public Builder setDurationMs(long j) {
                copyOnWrite();
                ((VideoMetadata) this.instance).zza(j);
                return this;
            }
        }

        static {
            VideoMetadata videoMetadata = new VideoMetadata();
            DEFAULT_INSTANCE = videoMetadata;
            zzay.registerDefaultInstance(VideoMetadata.class, videoMetadata);
        }

        private VideoMetadata() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: clearDurationMs, reason: merged with bridge method [inline-methods] */
        public void zzb() {
            this.bitField0_ &= -2;
            this.durationMs_ = 0L;
        }

        public static VideoMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static VideoMetadata parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (VideoMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static VideoMetadata parseFrom(zzt zztVar) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, zztVar);
        }

        public static zzcn<VideoMetadata> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: setDurationMs, reason: merged with bridge method [inline-methods] */
        public void zza(long j) {
            this.bitField0_ |= 1;
            this.durationMs_ = j;
        }

        @Override // com.google.android.appfunctions.schema.internal.dependencies.zzay
        protected final Object dynamicMethod(zzax zzaxVar, Object obj, Object obj2) {
            zzcn zzatVar;
            int iOrdinal = zzaxVar.ordinal();
            if (iOrdinal == 0) {
                return (byte) 1;
            }
            if (iOrdinal == 2) {
                return newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဂ\u0000", new Object[]{"bitField0_", "durationMs_"});
            }
            if (iOrdinal == 3) {
                return new VideoMetadata();
            }
            byte[] bArr = null;
            if (iOrdinal == 4) {
                return new Builder(bArr);
            }
            if (iOrdinal == 5) {
                return DEFAULT_INSTANCE;
            }
            if (iOrdinal != 6) {
                throw null;
            }
            zzcn<VideoMetadata> zzcnVar = PARSER;
            if (zzcnVar != null) {
                return zzcnVar;
            }
            synchronized (VideoMetadata.class) {
                zzatVar = PARSER;
                if (zzatVar == null) {
                    zzatVar = new zzat(DEFAULT_INSTANCE);
                    PARSER = zzatVar;
                }
            }
            return zzatVar;
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem.VideoMetadataOrBuilder
        public long getDurationMs() {
            return this.durationMs_;
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItem.VideoMetadataOrBuilder
        public boolean hasDurationMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public static Builder newBuilder(VideoMetadata videoMetadata) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(videoMetadata);
        }

        public static VideoMetadata parseDelimitedFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
            return (VideoMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
        }

        public static VideoMetadata parseFrom(zzt zztVar, zzaj zzajVar) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, zztVar, zzajVar);
        }

        public static VideoMetadata parseFrom(zzx zzxVar) throws IOException {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar);
        }

        public static VideoMetadata parseFrom(zzx zzxVar, zzaj zzajVar) throws IOException {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar, zzajVar);
        }

        public static VideoMetadata parseFrom(InputStream inputStream) throws IOException {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static VideoMetadata parseFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
        }

        public static VideoMetadata parseFrom(ByteBuffer byteBuffer) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static VideoMetadata parseFrom(ByteBuffer byteBuffer, zzaj zzajVar) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer, zzajVar);
        }

        public static VideoMetadata parseFrom(byte[] bArr) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static VideoMetadata parseFrom(byte[] bArr, zzaj zzajVar) throws zzbl {
            return (VideoMetadata) zzay.parseFrom(DEFAULT_INSTANCE, bArr, zzajVar);
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public interface VideoMetadataOrBuilder extends zzcg {
        long getDurationMs();

        boolean hasDurationMs();
    }

    static {
        MediaItem mediaItem = new MediaItem();
        DEFAULT_INSTANCE = mediaItem;
        zzay.registerDefaultInstance(MediaItem.class, mediaItem);
    }

    private MediaItem() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearMediaUrl, reason: merged with bridge method [inline-methods] */
    public void zze() {
        this.bitField0_ &= -3;
        this.mediaUrl_ = getDefaultInstance().getMediaUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearType, reason: merged with bridge method [inline-methods] */
    public void zzc() {
        this.bitField0_ &= -2;
        this.type_ = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearVideoMetadata, reason: merged with bridge method [inline-methods] */
    public void zzi() {
        this.videoMetadata_ = null;
        this.bitField0_ &= -5;
    }

    public static MediaItem getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: mergeVideoMetadata, reason: merged with bridge method [inline-methods] */
    public void zzh(VideoMetadata videoMetadata) {
        videoMetadata.getClass();
        VideoMetadata videoMetadata2 = this.videoMetadata_;
        if (videoMetadata2 != null && videoMetadata2 != VideoMetadata.getDefaultInstance()) {
            VideoMetadata.Builder builderNewBuilder = VideoMetadata.newBuilder(videoMetadata2);
            builderNewBuilder.mergeFrom(videoMetadata);
            videoMetadata = builderNewBuilder.buildPartial();
        }
        this.videoMetadata_ = videoMetadata;
        this.bitField0_ |= 4;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static MediaItem parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (MediaItem) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MediaItem parseFrom(zzt zztVar) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, zztVar);
    }

    public static zzcn<MediaItem> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setMediaUrl, reason: merged with bridge method [inline-methods] */
    public void zzd(String str) {
        str.getClass();
        this.bitField0_ |= 2;
        this.mediaUrl_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setMediaUrlBytes, reason: merged with bridge method [inline-methods] */
    public void zzf(zzt zztVar) {
        checkByteStringIsUtf8(zztVar);
        this.mediaUrl_ = zztVar.zzp();
        this.bitField0_ |= 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setType, reason: merged with bridge method [inline-methods] */
    public void zzb(Type type) {
        this.type_ = type.getNumber();
        this.bitField0_ |= 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setTypeValue, reason: merged with bridge method [inline-methods] */
    public void zza(int i) {
        this.bitField0_ |= 1;
        this.type_ = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setVideoMetadata, reason: merged with bridge method [inline-methods] */
    public void zzg(VideoMetadata videoMetadata) {
        videoMetadata.getClass();
        this.videoMetadata_ = videoMetadata;
        this.bitField0_ |= 4;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzay
    protected final Object dynamicMethod(zzax zzaxVar, Object obj, Object obj2) {
        zzcn zzatVar;
        int iOrdinal = zzaxVar.ordinal();
        if (iOrdinal == 0) {
            return (byte) 1;
        }
        if (iOrdinal == 2) {
            return newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ለ\u0001\u0003ဉ\u0002", new Object[]{"bitField0_", "type_", "mediaUrl_", "videoMetadata_"});
        }
        if (iOrdinal == 3) {
            return new MediaItem();
        }
        byte[] bArr = null;
        if (iOrdinal == 4) {
            return new Builder(bArr);
        }
        if (iOrdinal == 5) {
            return DEFAULT_INSTANCE;
        }
        if (iOrdinal != 6) {
            throw null;
        }
        zzcn<MediaItem> zzcnVar = PARSER;
        if (zzcnVar != null) {
            return zzcnVar;
        }
        synchronized (MediaItem.class) {
            zzatVar = PARSER;
            if (zzatVar == null) {
                zzatVar = new zzat(DEFAULT_INSTANCE);
                PARSER = zzatVar;
            }
        }
        return zzatVar;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public String getMediaUrl() {
        return this.mediaUrl_;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public zzt getMediaUrlBytes() {
        return zzt.zzo(this.mediaUrl_);
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public Type getType() {
        Type typeForNumber = Type.forNumber(this.type_);
        return typeForNumber == null ? Type.UNRECOGNIZED : typeForNumber;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public int getTypeValue() {
        return this.type_;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public VideoMetadata getVideoMetadata() {
        VideoMetadata videoMetadata = this.videoMetadata_;
        return videoMetadata == null ? VideoMetadata.getDefaultInstance() : videoMetadata;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public boolean hasMediaUrl() {
        return (this.bitField0_ & 2) != 0;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public boolean hasType() {
        return (this.bitField0_ & 1) != 0;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
    public boolean hasVideoMetadata() {
        return (this.bitField0_ & 4) != 0;
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static final class Builder extends zzas<MediaItem, Builder> implements MediaItemOrBuilder {
        private Builder() {
            super(MediaItem.DEFAULT_INSTANCE);
        }

        /* synthetic */ Builder(byte[] bArr) {
            this();
        }

        public Builder clearMediaUrl() {
            copyOnWrite();
            ((MediaItem) this.instance).zze();
            return this;
        }

        public Builder clearType() {
            copyOnWrite();
            ((MediaItem) this.instance).zzc();
            return this;
        }

        public Builder clearVideoMetadata() {
            copyOnWrite();
            ((MediaItem) this.instance).zzi();
            return this;
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public String getMediaUrl() {
            return ((MediaItem) this.instance).getMediaUrl();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public zzt getMediaUrlBytes() {
            return ((MediaItem) this.instance).getMediaUrlBytes();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public Type getType() {
            return ((MediaItem) this.instance).getType();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public int getTypeValue() {
            return ((MediaItem) this.instance).getTypeValue();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public VideoMetadata getVideoMetadata() {
            return ((MediaItem) this.instance).getVideoMetadata();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public boolean hasMediaUrl() {
            return ((MediaItem) this.instance).hasMediaUrl();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public boolean hasType() {
            return ((MediaItem) this.instance).hasType();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaItemOrBuilder
        public boolean hasVideoMetadata() {
            return ((MediaItem) this.instance).hasVideoMetadata();
        }

        public Builder mergeVideoMetadata(VideoMetadata videoMetadata) {
            copyOnWrite();
            ((MediaItem) this.instance).zzh(videoMetadata);
            return this;
        }

        public Builder setMediaUrl(String str) {
            copyOnWrite();
            ((MediaItem) this.instance).zzd(str);
            return this;
        }

        public Builder setMediaUrlBytes(zzt zztVar) {
            copyOnWrite();
            ((MediaItem) this.instance).zzf(zztVar);
            return this;
        }

        public Builder setType(Type type) {
            copyOnWrite();
            ((MediaItem) this.instance).zzb(type);
            return this;
        }

        public Builder setTypeValue(int i) {
            copyOnWrite();
            ((MediaItem) this.instance).zza(i);
            return this;
        }

        public Builder setVideoMetadata(VideoMetadata.Builder builder) {
            copyOnWrite();
            ((MediaItem) this.instance).zzg(builder.build());
            return this;
        }

        public Builder setVideoMetadata(VideoMetadata videoMetadata) {
            copyOnWrite();
            ((MediaItem) this.instance).zzg(videoMetadata);
            return this;
        }
    }

    public static Builder newBuilder(MediaItem mediaItem) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(mediaItem);
    }

    public static MediaItem parseDelimitedFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        return (MediaItem) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
    }

    public static MediaItem parseFrom(zzt zztVar, zzaj zzajVar) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, zztVar, zzajVar);
    }

    public static MediaItem parseFrom(zzx zzxVar) throws IOException {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar);
    }

    public static MediaItem parseFrom(zzx zzxVar, zzaj zzajVar) throws IOException {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar, zzajVar);
    }

    public static MediaItem parseFrom(InputStream inputStream) throws IOException {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MediaItem parseFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
    }

    public static MediaItem parseFrom(ByteBuffer byteBuffer) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer);
    }

    public static MediaItem parseFrom(ByteBuffer byteBuffer, zzaj zzajVar) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer, zzajVar);
    }

    public static MediaItem parseFrom(byte[] bArr) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static MediaItem parseFrom(byte[] bArr, zzaj zzajVar) throws zzbl {
        return (MediaItem) zzay.parseFrom(DEFAULT_INSTANCE, bArr, zzajVar);
    }
}
