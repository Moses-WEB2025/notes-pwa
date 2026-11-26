package com.google.android.appfunctions.gemini.ui.preview.protos;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\b\u0007\u001a)\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0087\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"mediaCollection", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection;", "block", "Lkotlin/Function1;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl;", "", "Lkotlin/ExtensionFunctionType;", "-initializemediaCollection", "copy", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class MediaCollectionKtKt {
    /* JADX INFO: renamed from: -initializemediaCollection, reason: not valid java name */
    public static final MediaCollection m348initializemediaCollection(Function1<? super MediaCollectionKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        MediaCollectionKt.Dsl.Companion companion = MediaCollectionKt.Dsl.INSTANCE;
        MediaCollection.Builder builderNewBuilder = MediaCollection.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder(...)");
        MediaCollectionKt.Dsl dsl_create = companion._create(builderNewBuilder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }

    public static final MediaCollection copy(MediaCollection mediaCollection, Function1<? super MediaCollectionKt.Dsl, Unit> block) {
        Intrinsics.checkNotNullParameter(mediaCollection, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        MediaCollectionKt.Dsl.Companion companion = MediaCollectionKt.Dsl.INSTANCE;
        MediaCollection.Builder builder = mediaCollection.toBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "toBuilder(...)");
        MediaCollectionKt.Dsl dsl_create = companion._create(builder);
        block.invoke(dsl_create);
        return dsl_create._build();
    }
}
