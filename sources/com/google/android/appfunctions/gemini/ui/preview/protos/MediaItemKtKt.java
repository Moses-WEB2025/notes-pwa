package com.google.android.appfunctions.gemini.ui.preview.protos;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\b\u0007\u001a)\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0087\bø\u0001\u0000\u001a)\u0010\b\u001a\u00020\t*\u00020\t2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0087\bø\u0001\u0000\"\u0017\u0010\u000b\u001a\u0004\u0018\u00010\t*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"mediaItem", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem;", "block", "Lkotlin/Function1;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$Dsl;", "", "Lkotlin/ExtensionFunctionType;", "-initializemediaItem", "copy", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemKt$VideoMetadataKt$Dsl;", "videoMetadataOrNull", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemOrBuilder;", "getVideoMetadataOrNull", "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItemOrBuilder;)Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem$VideoMetadata;", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class MediaItemKtKt {
    /* JADX INFO: renamed from: -initializemediaItem, reason: not valid java name */
    public static final MediaItem m350initializemediaItem(Function1<? super MediaItemKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        MediaItemKt.Dsl.Companion companion = MediaItemKt.Dsl.INSTANCE;
        MediaItem.Builder builderNewBuilder = MediaItem.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder(...)");
        MediaItemKt.Dsl dsl_create = companion._create(builderNewBuilder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }

    public static final MediaItem.VideoMetadata copy(MediaItem.VideoMetadata videoMetadata, Function1<? super MediaItemKt.VideoMetadataKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(videoMetadata, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        MediaItemKt.VideoMetadataKt.Dsl.Companion companion = MediaItemKt.VideoMetadataKt.Dsl.INSTANCE;
        MediaItem.VideoMetadata.Builder builder = videoMetadata.toBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "toBuilder(...)");
        MediaItemKt.VideoMetadataKt.Dsl dsl_create = companion._create(builder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }

    public static final MediaItem.VideoMetadata getVideoMetadataOrNull(MediaItemOrBuilder mediaItemOrBuilder) {
        Intrinsics.checkNotNullParameter(mediaItemOrBuilder, "<this>");
        if (mediaItemOrBuilder.hasVideoMetadata()) {
            return mediaItemOrBuilder.getVideoMetadata();
        }
        return null;
    }

    public static final MediaItem copy(MediaItem mediaItem, Function1<? super MediaItemKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(mediaItem, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        MediaItemKt.Dsl.Companion companion = MediaItemKt.Dsl.INSTANCE;
        MediaItem.Builder builder = mediaItem.toBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "toBuilder(...)");
        MediaItemKt.Dsl dsl_create = companion._create(builder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }
}
