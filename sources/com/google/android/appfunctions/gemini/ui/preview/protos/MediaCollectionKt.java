package com.google.android.appfunctions.gemini.ui.preview.protos;

import com.google.android.appfunctions.schema.internal.dependencies.zzdw;
import com.google.android.appfunctions.schema.internal.dependencies.zzdx;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Dsl", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class MediaCollectionKt {
    public static final MediaCollectionKt INSTANCE = new MediaCollectionKt();

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001f B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0001J%\u0010\u000e\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u0010\u001a\u00020\nH\u0007¢\u0006\u0002\b\u0011J&\u0010\u0012\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u0010\u001a\u00020\nH\u0087\n¢\u0006\u0002\b\u0013J+\u0010\u0014\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u0016H\u0007¢\u0006\u0002\b\u0017J,\u0010\u0012\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u0016H\u0087\n¢\u0006\u0002\b\u0018J.\u0010\u0019\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001cJ\u001d\u0010\u001d\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tH\u0007¢\u0006\u0002\b\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006!"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl;", "", "_builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection$Builder;)V", "_build", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection;", "mediaItems", "Lcom/google/protobuf/kotlin/DslList;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaItem;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl$MediaItemsProxy;", "getMediaItems", "()Lcom/google/protobuf/kotlin/DslList;", "add", "", "value", "addMediaItems", "plusAssign", "plusAssignMediaItems", "addAll", "values", "", "addAllMediaItems", "plusAssignAllMediaItems", "set", "index", "", "setMediaItems", "clear", "clearMediaItems", "Companion", "MediaItemsProxy", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Dsl {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final MediaCollection.Builder _builder;

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0001¨\u0006\b"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_create", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl;", "builder", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection$Builder;", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final /* synthetic */ Dsl _create(MediaCollection.Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "builder");
                return new Dsl(builder, null);
            }
        }

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollectionKt$Dsl$MediaItemsProxy;", "Lcom/google/protobuf/kotlin/DslProxy;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "assistant.boq.lamda.proto.ui_templates.preview_gemini_ui_preview_protos_kt_proto_lite"}, k = 1, mv = {2, 1, 0}, xi = 48)
        public static final class MediaItemsProxy extends zzdx {
            private MediaItemsProxy() {
            }
        }

        private Dsl(MediaCollection.Builder builder) {
            this._builder = builder;
        }

        public /* synthetic */ Dsl(MediaCollection.Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
            this(builder);
        }

        public final /* synthetic */ MediaCollection _build() {
            MediaCollection mediaCollectionBuild = this._builder.build();
            Intrinsics.checkNotNullExpressionValue(mediaCollectionBuild, "build(...)");
            return mediaCollectionBuild;
        }

        public final /* synthetic */ void addAllMediaItems(zzdw zzdwVar, Iterable values) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            this._builder.addAllMediaItems(values);
        }

        public final /* synthetic */ void addMediaItems(zzdw zzdwVar, MediaItem value) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            Intrinsics.checkNotNullParameter(value, "value");
            this._builder.addMediaItems(value);
        }

        public final /* synthetic */ void clearMediaItems(zzdw zzdwVar) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            this._builder.clearMediaItems();
        }

        public final /* synthetic */ zzdw getMediaItems() {
            List<MediaItem> mediaItemsList = this._builder.getMediaItemsList();
            Intrinsics.checkNotNullExpressionValue(mediaItemsList, "getMediaItemsList(...)");
            return new zzdw(mediaItemsList);
        }

        public final /* synthetic */ void plusAssignAllMediaItems(zzdw<MediaItem, MediaItemsProxy> zzdwVar, Iterable<MediaItem> values) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            addAllMediaItems(zzdwVar, values);
        }

        public final /* synthetic */ void plusAssignMediaItems(zzdw<MediaItem, MediaItemsProxy> zzdwVar, MediaItem value) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            Intrinsics.checkNotNullParameter(value, "value");
            addMediaItems(zzdwVar, value);
        }

        public final /* synthetic */ void setMediaItems(zzdw zzdwVar, int i, MediaItem value) {
            Intrinsics.checkNotNullParameter(zzdwVar, "<this>");
            Intrinsics.checkNotNullParameter(value, "value");
            this._builder.setMediaItems(i, value);
        }
    }

    private MediaCollectionKt() {
    }
}
