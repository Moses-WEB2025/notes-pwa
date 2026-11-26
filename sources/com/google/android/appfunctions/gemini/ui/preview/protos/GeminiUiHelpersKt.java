package com.google.android.appfunctions.gemini.ui.preview.protos;

import android.util.Base64;
import androidx.appfunctions.AppFunctionTextResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"MEDIA_COLLECTION_MIME_TYPE", "", "toAppFunctionTextResource", "Landroidx/appfunctions/AppFunctionTextResource;", "Lcom/google/android/appfunctions/gemini/ui/preview/protos/MediaCollection;", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.gemini.ui.preview.protos_helpers"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class GeminiUiHelpersKt {
    private static final String MEDIA_COLLECTION_MIME_TYPE = "proto/com.google.gemini.ui.preview.protos.MediaCollection";

    public static final AppFunctionTextResource toAppFunctionTextResource(MediaCollection mediaCollection) {
        Intrinsics.checkNotNullParameter(mediaCollection, "<this>");
        String strEncodeToString = Base64.encodeToString(mediaCollection.toByteArray(), 0);
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
        return new AppFunctionTextResource(MEDIA_COLLECTION_MIME_TYPE, strEncodeToString);
    }
}
