package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\f\rB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J*\u0010\u0004\u001a\u00020\u00052\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\bø\u0001\u0000¢\u0006\u0002\b\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000e"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "videoMetadata", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "block", "Lkotlin/Function1;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt$Dsl;", "", "Lkotlin/ExtensionFunctionType;", "-initializevideoMetadata", "Dsl", "VideoMetadataKt", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class MediaItemKt {
    public static final MediaItemKt INSTANCE = new MediaItemKt();

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0001J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u001f\u001a\u00020\u0016J\u0006\u0010 \u001a\u00020\u0018J\u0006\u0010'\u001a\u00020\u0016J\u0006\u0010(\u001a\u00020\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8G@GX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8G@GX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u001a\u001a\u00020\u00192\u0006\u0010\b\u001a\u00020\u00198G@GX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR$\u0010\"\u001a\u00020!2\u0006\u0010\b\u001a\u00020!8G@GX\u0086\u000e¢\u0006\f\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0017\u0010)\u001a\u0004\u0018\u00010!*\u00020\u00008F¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u0006-"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$Dsl;", "", "_builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Builder;)V", "_build", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem;", "value", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Type;", "type", "getType", "()Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Type;", "setType", "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Type;)V", "", "typeValue", "getTypeValue", "()I", "setTypeValue", "(I)V", "clearType", "", "hasType", "", "", "mediaUrl", "getMediaUrl", "()Ljava/lang/String;", "setMediaUrl", "(Ljava/lang/String;)V", "clearMediaUrl", "hasMediaUrl", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "videoMetadata", "getVideoMetadata", "()Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "setVideoMetadata", "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;)V", "clearVideoMetadata", "hasVideoMetadata", "videoMetadataOrNull", "getVideoMetadataOrNull", "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$Dsl;)Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "Companion", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Dsl {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final MediaItem.Builder _builder;

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0001¨\u0006\b"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$Dsl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_create", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$Dsl;", "builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$Builder;", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final /* synthetic */ Dsl _create(MediaItem.Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "builder");
                return new Dsl(builder, null);
            }
        }

        private Dsl(MediaItem.Builder builder) {
            this._builder = builder;
        }

        public /* synthetic */ Dsl(MediaItem.Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
            this(builder);
        }

        public final /* synthetic */ MediaItem _build() {
            MediaItem mediaItemBuild = this._builder.build();
            Intrinsics.checkNotNullExpressionValue(mediaItemBuild, "build(...)");
            return mediaItemBuild;
        }

        public final void clearMediaUrl() {
            this._builder.clearMediaUrl();
        }

        public final void clearType() {
            this._builder.clearType();
        }

        public final void clearVideoMetadata() {
            this._builder.clearVideoMetadata();
        }

        public final String getMediaUrl() {
            String mediaUrl = this._builder.getMediaUrl();
            Intrinsics.checkNotNullExpressionValue(mediaUrl, "getMediaUrl(...)");
            return mediaUrl;
        }

        public final MediaItem.Type getType() {
            MediaItem.Type type = this._builder.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return type;
        }

        public final int getTypeValue() {
            return this._builder.getTypeValue();
        }

        public final MediaItem.VideoMetadata getVideoMetadata() {
            MediaItem.VideoMetadata videoMetadata = this._builder.getVideoMetadata();
            Intrinsics.checkNotNullExpressionValue(videoMetadata, "getVideoMetadata(...)");
            return videoMetadata;
        }

        public final MediaItem.VideoMetadata getVideoMetadataOrNull(Dsl dsl) {
            Intrinsics.checkNotNullParameter(dsl, "<this>");
            return MediaItemKtKt.getVideoMetadataOrNull(dsl._builder);
        }

        public final boolean hasMediaUrl() {
            return this._builder.hasMediaUrl();
        }

        public final boolean hasType() {
            return this._builder.hasType();
        }

        public final boolean hasVideoMetadata() {
            return this._builder.hasVideoMetadata();
        }

        public final void setMediaUrl(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this._builder.setMediaUrl(value);
        }

        public final void setType(MediaItem.Type value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this._builder.setType(value);
        }

        public final void setTypeValue(int i) {
            this._builder.setTypeValue(i);
        }

        public final void setVideoMetadata(MediaItem.VideoMetadata value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this._builder.setVideoMetadata(value);
        }
    }

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Dsl", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class VideoMetadataKt {
        public static final VideoMetadataKt INSTANCE = new VideoMetadataKt();

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0001J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8G@GX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt$Dsl;", "", "_builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata$Builder;)V", "_build", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "value", "", "durationMs", "getDurationMs", "()J", "setDurationMs", "(J)V", "clearDurationMs", "", "hasDurationMs", "", "Companion", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
        public static final class Dsl {

            /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
            public static final Companion INSTANCE = new Companion(null);
            private final MediaItem.VideoMetadata.Builder _builder;

            /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
            @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0001¨\u0006\b"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt$Dsl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_create", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt$Dsl;", "builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata$Builder;", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
            public static final class Companion {
                private Companion() {
                }

                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                public final /* synthetic */ Dsl _create(MediaItem.VideoMetadata.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    return new Dsl(builder, null);
                }
            }

            private Dsl(MediaItem.VideoMetadata.Builder builder) {
                this._builder = builder;
            }

            public /* synthetic */ Dsl(MediaItem.VideoMetadata.Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
                this(builder);
            }

            public final /* synthetic */ MediaItem.VideoMetadata _build() {
                MediaItem.VideoMetadata videoMetadataBuild = this._builder.build();
                Intrinsics.checkNotNullExpressionValue(videoMetadataBuild, "build(...)");
                return videoMetadataBuild;
            }

            public final void clearDurationMs() {
                this._builder.clearDurationMs();
            }

            public final long getDurationMs() {
                return this._builder.getDurationMs();
            }

            public final boolean hasDurationMs() {
                return this._builder.hasDurationMs();
            }

            public final void setDurationMs(long j) {
                this._builder.setDurationMs(j);
            }
        }

        private VideoMetadataKt() {
        }
    }

    private MediaItemKt() {
    }

    /* JADX INFO: renamed from: -initializevideoMetadata, reason: not valid java name */
    public final MediaItem.VideoMetadata m349initializevideoMetadata(Function1<? super VideoMetadataKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        VideoMetadataKt.Dsl.Companion companion = VideoMetadataKt.Dsl.INSTANCE;
        MediaItem.VideoMetadata.Builder builderNewBuilder = MediaItem.VideoMetadata.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder(...)");
        VideoMetadataKt.Dsl dsl_create = companion._create(builderNewBuilder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }
}
