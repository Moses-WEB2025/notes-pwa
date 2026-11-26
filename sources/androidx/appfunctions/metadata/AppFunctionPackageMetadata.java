package androidx.appfunctions.metadata;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import androidx.appfunctions.internal.Constants;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionPackageMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0003H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0003H\u0002J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionPackageMetadata;", "", "packageName", "", "appFunctions", "", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;)V", "getPackageName", "()Ljava/lang/String;", "getAppFunctions", "()Ljava/util/List;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "resolveAppFunctionAppMetadata", "Landroidx/appfunctions/metadata/AppFunctionAppMetadata;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getXmlAttributeValue", "xmlParser", "Landroid/content/res/XmlResourceParser;", "attributeName", "getXmlAttributeResourceValue", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionPackageMetadata {
    private static final String APP_METADATA_APPFUNCTIONS_LIBRARY_ATTRIBUTE_NAMESPACE = "http://schemas.android.com/apk/androidx.appfunctions";
    private static final String APP_METADATA_ATTRIBUTE_NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String APP_METADATA_XML_PROPERTY = "android.app.appfunctions.app_metadata";
    private static final Companion Companion = new Companion(null);
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
    private static final String DISPLAY_DESCRIPTION_ATTRIBUTE_NAME = "displayDescription";
    private final List<AppFunctionMetadata> appFunctions;
    private final String packageName;

    public AppFunctionPackageMetadata(String packageName, List<AppFunctionMetadata> appFunctions) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(appFunctions, "appFunctions");
        this.packageName = packageName;
        this.appFunctions = appFunctions;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final List<AppFunctionMetadata> getAppFunctions() {
        return this.appFunctions;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionPackageMetadata");
        AppFunctionPackageMetadata appFunctionPackageMetadata = (AppFunctionPackageMetadata) other;
        return Intrinsics.areEqual(this.packageName, appFunctionPackageMetadata.packageName) && Intrinsics.areEqual(this.appFunctions, appFunctionPackageMetadata.appFunctions);
    }

    public int hashCode() {
        return (this.packageName.hashCode() * 31) + this.appFunctions.hashCode();
    }

    public String toString() {
        return "AppFunctionPackageMetadata(packageName='" + this.packageName + "', appFunctions=" + this.appFunctions + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public final AppFunctionAppMetadata resolveAppFunctionAppMetadata(Context context) {
        String xmlAttributeValue;
        Intrinsics.checkNotNullParameter(context, "context");
        PackageManager packageManager = context.getPackageManager();
        try {
            int resourceId = packageManager.getProperty(APP_METADATA_XML_PROPERTY, this.packageName).getResourceId();
            if (resourceId == 0) {
                return null;
            }
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.packageName, 0);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
            Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo, context.getResources().getConfiguration());
            Intrinsics.checkNotNullExpressionValue(resourcesForApplication, "getResourcesForApplication(...)");
            XmlResourceParser xml = resourcesForApplication.getXml(resourceId);
            Intrinsics.checkNotNullExpressionValue(xml, "getXml(...)");
            while (xml.getEventType() != 2) {
                xml.next();
                if (xml.getEventType() == 1) {
                    return null;
                }
            }
            String xmlAttributeValue2 = getXmlAttributeValue(xml, "description");
            int xmlAttributeResourceValue = getXmlAttributeResourceValue(xml, DISPLAY_DESCRIPTION_ATTRIBUTE_NAME);
            if (xmlAttributeResourceValue != 0) {
                xmlAttributeValue = resourcesForApplication.getString(xmlAttributeResourceValue);
            } else {
                xmlAttributeValue = getXmlAttributeValue(xml, DISPLAY_DESCRIPTION_ATTRIBUTE_NAME);
            }
            if (xmlAttributeValue2 == null) {
                xmlAttributeValue2 = "";
            }
            if (xmlAttributeValue == null) {
                xmlAttributeValue = "";
            }
            return new AppFunctionAppMetadata(xmlAttributeValue2, xmlAttributeValue);
        } catch (Exception e) {
            Log.d(Constants.APP_FUNCTIONS_TAG, "Encountered an error while resolving app metadata for package: " + this.packageName + '.', e);
            return null;
        }
    }

    private final String getXmlAttributeValue(XmlResourceParser xmlParser, String attributeName) {
        String attributeValue = xmlParser.getAttributeValue(APP_METADATA_ATTRIBUTE_NAMESPACE, attributeName);
        return attributeValue == null ? xmlParser.getAttributeValue(APP_METADATA_APPFUNCTIONS_LIBRARY_ATTRIBUTE_NAMESPACE, attributeName) : attributeValue;
    }

    private final int getXmlAttributeResourceValue(XmlResourceParser xmlParser, String attributeName) {
        int attributeResourceValue = xmlParser.getAttributeResourceValue(APP_METADATA_ATTRIBUTE_NAMESPACE, attributeName, 0);
        return attributeResourceValue == 0 ? xmlParser.getAttributeResourceValue(APP_METADATA_APPFUNCTIONS_LIBRARY_ATTRIBUTE_NAMESPACE, attributeName, 0) : attributeResourceValue;
    }

    /* JADX INFO: compiled from: AppFunctionPackageMetadata.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionPackageMetadata$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "APP_METADATA_XML_PROPERTY", "", "APP_METADATA_ATTRIBUTE_NAMESPACE", "APP_METADATA_APPFUNCTIONS_LIBRARY_ATTRIBUTE_NAMESPACE", "DISPLAY_DESCRIPTION_ATTRIBUTE_NAME", "DESCRIPTION_ATTRIBUTE_NAME", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
