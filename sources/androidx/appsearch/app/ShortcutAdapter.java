package androidx.appsearch.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.safeparcel.GenericDocumentParcel;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class ShortcutAdapter {
    private static final String APPSEARCH_GENERIC_DOC_PARCEL_NAME_IN_BUNDLE = "appsearch_generic_doc_parcel";
    public static final String DEFAULT_DATABASE = "__shortcut_adapter_db__";
    public static final String DEFAULT_NAMESPACE = "__shortcut_adapter_ns__";
    private static final String FIELD_NAME = "name";
    private static final String NAMESPACE_CHECK_ERROR_MESSAGE = "Namespace of the document does not match androidx.appsearch.app.ShortcutAdapter.DEFAULT_NAMESPACE.Please use androidx.appsearch.app.ShortcutAdapter.DEFAULT_NAMESPACE as the namespace of the document if it will be used to create a shortcut.";
    private static final String SCHEME_APPSEARCH = "appsearch";

    private ShortcutAdapter() {
    }

    public static ShortcutInfoCompat.Builder createShortcutBuilderFromDocument(Context context, Object obj) throws AppSearchException {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(obj);
        GenericDocument genericDocumentFromDocumentClass = GenericDocument.fromDocumentClass(obj);
        if (!DEFAULT_NAMESPACE.equals(genericDocumentFromDocumentClass.getNamespace())) {
            throw new IllegalArgumentException(NAMESPACE_CHECK_ERROR_MESSAGE);
        }
        String propertyString = genericDocumentFromDocumentClass.getPropertyString("name");
        Bundle bundle = new Bundle();
        bundle.putParcelable(APPSEARCH_GENERIC_DOC_PARCEL_NAME_IN_BUNDLE, genericDocumentFromDocumentClass.getDocumentParcel());
        ShortcutInfoCompat.Builder builder = new ShortcutInfoCompat.Builder(context, genericDocumentFromDocumentClass.getId());
        if (TextUtils.isEmpty(propertyString)) {
            propertyString = genericDocumentFromDocumentClass.getId();
        }
        return builder.setShortLabel(propertyString).setIntent(new Intent("android.intent.action.VIEW", getDocumentUri(genericDocumentFromDocumentClass))).setExcludedFromSurfaces(1).setTransientExtras(bundle);
    }

    public static GenericDocument extractDocument(ShortcutInfoCompat shortcutInfoCompat) {
        GenericDocumentParcel genericDocumentParcel;
        Preconditions.checkNotNull(shortcutInfoCompat);
        Bundle transientExtras = shortcutInfoCompat.getTransientExtras();
        if (transientExtras == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            genericDocumentParcel = (GenericDocumentParcel) Api33Impl.getParcelableFromBundle(transientExtras, APPSEARCH_GENERIC_DOC_PARCEL_NAME_IN_BUNDLE, GenericDocumentParcel.class);
        } else {
            genericDocumentParcel = (GenericDocumentParcel) transientExtras.getParcelable(APPSEARCH_GENERIC_DOC_PARCEL_NAME_IN_BUNDLE);
        }
        if (genericDocumentParcel == null) {
            return null;
        }
        return new GenericDocument(genericDocumentParcel);
    }

    public static Uri getDocumentUri(Object obj) throws AppSearchException {
        Preconditions.checkNotNull(obj);
        return getDocumentUri(GenericDocument.fromDocumentClass(obj));
    }

    private static Uri getDocumentUri(GenericDocument genericDocument) {
        Preconditions.checkNotNull(genericDocument);
        return getDocumentUri(genericDocument.getId());
    }

    public static Uri getDocumentUri(String str) {
        Preconditions.checkNotNull(str);
        return new Uri.Builder().scheme(SCHEME_APPSEARCH).authority(DEFAULT_DATABASE).path("__shortcut_adapter_ns__/" + str).build();
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static <T> T getParcelableFromBundle(Bundle bundle, String str, Class<T> cls) {
            Preconditions.checkNotNull(bundle);
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(cls);
            return (T) bundle.getParcelable(str, cls);
        }
    }
}
