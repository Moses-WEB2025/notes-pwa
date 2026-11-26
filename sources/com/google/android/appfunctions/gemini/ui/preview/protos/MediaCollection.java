package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzaj;
import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzat;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;
import com.google.android.appfunctions.schema.internal.dependencies.zzay;
import com.google.android.appfunctions.schema.internal.dependencies.zzbi;
import com.google.android.appfunctions.schema.internal.dependencies.zzbl;
import com.google.android.appfunctions.schema.internal.dependencies.zzc;
import com.google.android.appfunctions.schema.internal.dependencies.zzcn;
import com.google.android.appfunctions.schema.internal.dependencies.zzt;
import com.google.android.appfunctions.schema.internal.dependencies.zzx;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class MediaCollection extends zzay<MediaCollection, Builder> implements MediaCollectionOrBuilder {
    private static final MediaCollection DEFAULT_INSTANCE;
    public static final int MEDIA_ITEMS_FIELD_NUMBER = 1;
    private static volatile zzcn<MediaCollection> PARSER;
    private zzbi<MediaItem> mediaItems_ = emptyProtobufList();

    /* JADX INFO: renamed from: com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollection$1, reason: invalid class name */
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

    static {
        MediaCollection mediaCollection = new MediaCollection();
        DEFAULT_INSTANCE = mediaCollection;
        zzay.registerDefaultInstance(MediaCollection.class, mediaCollection);
    }

    private MediaCollection() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addAllMediaItems, reason: merged with bridge method [inline-methods] */
    public void zzd(Iterable<? extends MediaItem> iterable) {
        ensureMediaItemsIsMutable();
        zzc.addAll(iterable, this.mediaItems_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addMediaItems, reason: merged with bridge method [inline-methods] */
    public void zzc(int i, MediaItem mediaItem) {
        mediaItem.getClass();
        ensureMediaItemsIsMutable();
        this.mediaItems_.add(i, mediaItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: clearMediaItems, reason: merged with bridge method [inline-methods] */
    public void zze() {
        this.mediaItems_ = emptyProtobufList();
    }

    private void ensureMediaItemsIsMutable() {
        zzbi<MediaItem> zzbiVar = this.mediaItems_;
        if (zzbiVar.zza()) {
            return;
        }
        this.mediaItems_ = zzay.mutableCopy(zzbiVar);
    }

    public static MediaCollection getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static MediaCollection parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (MediaCollection) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MediaCollection parseFrom(zzt zztVar) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, zztVar);
    }

    public static zzcn<MediaCollection> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: removeMediaItems, reason: merged with bridge method [inline-methods] */
    public void zzf(int i) {
        ensureMediaItemsIsMutable();
        this.mediaItems_.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setMediaItems, reason: merged with bridge method [inline-methods] */
    public void zza(int i, MediaItem mediaItem) {
        mediaItem.getClass();
        ensureMediaItemsIsMutable();
        this.mediaItems_.set(i, mediaItem);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzay
    protected final Object dynamicMethod(zzax zzaxVar, Object obj, Object obj2) {
        zzcn zzatVar;
        int iOrdinal = zzaxVar.ordinal();
        if (iOrdinal == 0) {
            return (byte) 1;
        }
        if (iOrdinal == 2) {
            return newMessageInfo(DEFAULT_INSTANCE, "\u0004\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"mediaItems_", MediaItem.class});
        }
        if (iOrdinal == 3) {
            return new MediaCollection();
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
        zzcn<MediaCollection> zzcnVar = PARSER;
        if (zzcnVar != null) {
            return zzcnVar;
        }
        synchronized (MediaCollection.class) {
            zzatVar = PARSER;
            if (zzatVar == null) {
                zzatVar = new zzat(DEFAULT_INSTANCE);
                PARSER = zzatVar;
            }
        }
        return zzatVar;
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
    public MediaItem getMediaItems(int i) {
        return this.mediaItems_.get(i);
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
    public int getMediaItemsCount() {
        return this.mediaItems_.size();
    }

    @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
    public List<MediaItem> getMediaItemsList() {
        return this.mediaItems_;
    }

    public MediaItemOrBuilder getMediaItemsOrBuilder(int i) {
        return this.mediaItems_.get(i);
    }

    public List<? extends MediaItemOrBuilder> getMediaItemsOrBuilderList() {
        return this.mediaItems_;
    }

    public static Builder newBuilder(MediaCollection mediaCollection) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(mediaCollection);
    }

    public static MediaCollection parseDelimitedFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        return (MediaCollection) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
    }

    public static MediaCollection parseFrom(zzt zztVar, zzaj zzajVar) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, zztVar, zzajVar);
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    public static final class Builder extends zzas<MediaCollection, Builder> implements MediaCollectionOrBuilder {
        private Builder() {
            super(MediaCollection.DEFAULT_INSTANCE);
        }

        /* synthetic */ Builder(byte[] bArr) {
            this();
        }

        public Builder addAllMediaItems(Iterable<? extends MediaItem> iterable) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzd(iterable);
            return this;
        }

        public Builder addMediaItems(int i, MediaItem.Builder builder) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzc(i, builder.build());
            return this;
        }

        public Builder clearMediaItems() {
            copyOnWrite();
            ((MediaCollection) this.instance).zze();
            return this;
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
        public MediaItem getMediaItems(int i) {
            return ((MediaCollection) this.instance).getMediaItems(i);
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
        public int getMediaItemsCount() {
            return ((MediaCollection) this.instance).getMediaItemsCount();
        }

        @Override // com.google.android.appfunctions.gemini.ui.preview.protos.MediaCollectionOrBuilder
        public List<MediaItem> getMediaItemsList() {
            return Collections.unmodifiableList(((MediaCollection) this.instance).getMediaItemsList());
        }

        public Builder removeMediaItems(int i) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzf(i);
            return this;
        }

        public Builder setMediaItems(int i, MediaItem.Builder builder) {
            copyOnWrite();
            ((MediaCollection) this.instance).zza(i, builder.build());
            return this;
        }

        public Builder addMediaItems(int i, MediaItem mediaItem) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzc(i, mediaItem);
            return this;
        }

        public Builder setMediaItems(int i, MediaItem mediaItem) {
            copyOnWrite();
            ((MediaCollection) this.instance).zza(i, mediaItem);
            return this;
        }

        public Builder addMediaItems(MediaItem.Builder builder) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzb(builder.build());
            return this;
        }

        public Builder addMediaItems(MediaItem mediaItem) {
            copyOnWrite();
            ((MediaCollection) this.instance).zzb(mediaItem);
            return this;
        }
    }

    public static MediaCollection parseFrom(zzx zzxVar) throws IOException {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addMediaItems, reason: merged with bridge method [inline-methods] */
    public void zzb(MediaItem mediaItem) {
        mediaItem.getClass();
        ensureMediaItemsIsMutable();
        this.mediaItems_.add(mediaItem);
    }

    public static MediaCollection parseFrom(zzx zzxVar, zzaj zzajVar) throws IOException {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, zzxVar, zzajVar);
    }

    public static MediaCollection parseFrom(InputStream inputStream) throws IOException {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MediaCollection parseFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, inputStream, zzajVar);
    }

    public static MediaCollection parseFrom(ByteBuffer byteBuffer) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer);
    }

    public static MediaCollection parseFrom(ByteBuffer byteBuffer, zzaj zzajVar) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, byteBuffer, zzajVar);
    }

    public static MediaCollection parseFrom(byte[] bArr) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static MediaCollection parseFrom(byte[] bArr, zzaj zzajVar) throws zzbl {
        return (MediaCollection) zzay.parseFrom(DEFAULT_INSTANCE, bArr, zzajVar);
    }
}
