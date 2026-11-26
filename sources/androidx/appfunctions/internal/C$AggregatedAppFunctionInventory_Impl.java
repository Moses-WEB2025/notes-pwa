package androidx.appfunctions.internal;

import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionParcelableTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.data.VariableNames;
import com.miui.notes.excerpt.NotifyUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import miuix.smartaction.SmartAction;

/* JADX INFO: renamed from: androidx.appfunctions.internal.$AggregatedAppFunctionInventory_Impl, reason: invalid class name */
/* JADX INFO: compiled from: $AggregatedAppFunctionInventory_Impl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"androidx/appfunctions/internal/$AggregatedAppFunctionInventory_Impl", "Landroidx/appfunctions/internal/AggregatedAppFunctionInventory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "inventories", "", "Landroidx/appfunctions/internal/AppFunctionInventory;", "getInventories", "()Ljava/util/List;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class C$AggregatedAppFunctionInventory_Impl extends AggregatedAppFunctionInventory {
    private final List<AppFunctionInventory> inventories = CollectionsKt.listOf(new AppFunctionInventory() { // from class: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInventory
        private final AppFunctionObjectTypeMetadata ANDROID_NET_URI_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> ANDROID_NET_URI_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionStringTypeMetadata ANDROID_NET_URI_OBJECT_DATA_TYPE_PROPERTIES_MAP_URI;
        private final List<String> ANDROID_NET_URI_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_DISPLAYNAME;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_MIMETYPE;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_URI;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS_REFERENCE_ITEM_TYPE;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_CONTENT;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_EXTERNALID;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_FOLDERID;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_TITLE;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENDDATE;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_MAXCOUNT;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_QUERY;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_STARTDATE;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS_REFERENCE_ITEM_TYPE;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_CONTENT;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_FOLDERID;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ID;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_TITLE;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_DATE;
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_TIMEOFDAY;
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_TIMEZONE;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DAY;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_MONTH;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_YEAR;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE;
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_HOURS;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_MINUTES;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_NANOS;
        private final AppFunctionIntTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_SECONDS;
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;
        private final AppFunctionComponentsMetadata componentsMetadata;
        private final Map<String, AppFunctionDataTypeMetadata> componentsMetadataDataTypesMap;
        private final Map<String, CompileTimeAppFunctionMetadata> functionIdToMetadataMap = MapsKt.mapOf(TuplesKt.to(NoteAppFunctionIds.CREATE_NOTE_ID, ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()), TuplesKt.to(NoteAppFunctionIds.FIND_NOTES_ID, ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()), TuplesKt.to(NoteAppFunctionIds.GET_NOTES_ID, ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()), TuplesKt.to(NoteAppFunctionIds.SHOW_NOTE_ID, ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()));

        {
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
            this.ANDROID_NET_URI_OBJECT_DATA_TYPE_PROPERTIES_MAP_URI = appFunctionStringTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf = MapsKt.mapOf(TuplesKt.to(CallMethod.ARG_URI, appFunctionStringTypeMetadata));
            this.ANDROID_NET_URI_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf;
            List<String> listListOf = CollectionsKt.listOf(CallMethod.ARG_URI);
            this.ANDROID_NET_URI_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = new AppFunctionObjectTypeMetadata(mapMapOf, listListOf, "android.net.Uri", true, "");
            this.ANDROID_NET_URI_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata2 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_DISPLAYNAME = appFunctionStringTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata3 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_MIMETYPE = appFunctionStringTypeMetadata3;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("android.net.Uri", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP_URI = appFunctionReferenceTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf2 = MapsKt.mapOf(TuplesKt.to("displayName", appFunctionStringTypeMetadata2), TuplesKt.to("mimeType", appFunctionStringTypeMetadata3), TuplesKt.to(CallMethod.ARG_URI, appFunctionReferenceTypeMetadata));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf2;
            List<String> listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"displayName", CallMethod.ARG_URI});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf2;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata2 = new AppFunctionObjectTypeMetadata(mapMapOf2, listListOf2, "com.google.android.appfunctions.schema.notes.v1.Attachment", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_ATTACHMENT_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata2;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.Attachment", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata2;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata2, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS = appFunctionArrayTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata4 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_CONTENT = appFunctionStringTypeMetadata4;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata5 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_EXTERNALID = appFunctionStringTypeMetadata5;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata6 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_FOLDERID = appFunctionStringTypeMetadata6;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata7 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_TITLE = appFunctionStringTypeMetadata7;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf3 = MapsKt.mapOf(TuplesKt.to("attachments", appFunctionArrayTypeMetadata), TuplesKt.to("content", appFunctionStringTypeMetadata4), TuplesKt.to("externalId", appFunctionStringTypeMetadata5), TuplesKt.to("folderId", appFunctionStringTypeMetadata6), TuplesKt.to("title", appFunctionStringTypeMetadata7));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf3;
            List<String> listListOf3 = CollectionsKt.listOf("title");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf3;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata3 = new AppFunctionObjectTypeMetadata(mapMapOf3, listListOf3, "com.google.android.appfunctions.schema.notes.v1.CreateNoteParams", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_CREATENOTEPARAMS_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata3;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata3 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.types.v1.DateTime", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENDDATE = appFunctionReferenceTypeMetadata3;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_MAXCOUNT = appFunctionIntTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata8 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_QUERY = appFunctionStringTypeMetadata8;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata4 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.types.v1.DateTime", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_STARTDATE = appFunctionReferenceTypeMetadata4;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf4 = MapsKt.mapOf(TuplesKt.to("endDate", appFunctionReferenceTypeMetadata3), TuplesKt.to("maxCount", appFunctionIntTypeMetadata), TuplesKt.to(SmartAction.Feature.QUERY, appFunctionStringTypeMetadata8), TuplesKt.to("startDate", appFunctionReferenceTypeMetadata4));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf4;
            List<String> listListOf4 = CollectionsKt.listOf("maxCount");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf4;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata4 = new AppFunctionObjectTypeMetadata(mapMapOf4, listListOf4, "com.google.android.appfunctions.schema.notes.v1.FindNotesParams", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_FINDNOTESPARAMS_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata4;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata5 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.Attachment", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata5;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata2 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata5, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ATTACHMENTS = appFunctionArrayTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata9 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_CONTENT = appFunctionStringTypeMetadata9;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata10 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_FOLDERID = appFunctionStringTypeMetadata10;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata11 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ID = appFunctionStringTypeMetadata11;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata12 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP_TITLE = appFunctionStringTypeMetadata12;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf5 = MapsKt.mapOf(TuplesKt.to("attachments", appFunctionArrayTypeMetadata2), TuplesKt.to("content", appFunctionStringTypeMetadata9), TuplesKt.to("folderId", appFunctionStringTypeMetadata10), TuplesKt.to("id", appFunctionStringTypeMetadata11), TuplesKt.to("title", appFunctionStringTypeMetadata12));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf5;
            List<String> listListOf5 = CollectionsKt.listOf((Object[]) new String[]{"id", "title"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf5;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata5 = new AppFunctionObjectTypeMetadata(mapMapOf5, listListOf5, "com.google.android.appfunctions.schema.notes.v1.Note", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_NOTES_V1_NOTE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata5;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata2 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DAY = appFunctionIntTypeMetadata2;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata3 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_MONTH = appFunctionIntTypeMetadata3;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata4 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP_YEAR = appFunctionIntTypeMetadata4;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf6 = MapsKt.mapOf(TuplesKt.to("day", appFunctionIntTypeMetadata2), TuplesKt.to(VariableNames.VAR_MONTH, appFunctionIntTypeMetadata3), TuplesKt.to(VariableNames.VAR_YEAR, appFunctionIntTypeMetadata4));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf6;
            List<String> listListOf6 = CollectionsKt.listOf((Object[]) new String[]{VariableNames.VAR_YEAR, VariableNames.VAR_MONTH, "day"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf6;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata6 = new AppFunctionObjectTypeMetadata(mapMapOf6, listListOf6, "com.google.android.appfunctions.schema.types.v1.Date", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata6;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata6 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.types.v1.Date", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_DATE = appFunctionReferenceTypeMetadata6;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata7 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.types.v1.TimeOfDay", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_TIMEOFDAY = appFunctionReferenceTypeMetadata7;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata13 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP_TIMEZONE = appFunctionStringTypeMetadata13;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf7 = MapsKt.mapOf(TuplesKt.to("date", appFunctionReferenceTypeMetadata6), TuplesKt.to("timeOfDay", appFunctionReferenceTypeMetadata7), TuplesKt.to("timeZone", appFunctionStringTypeMetadata13));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf7;
            List<String> listListOf7 = CollectionsKt.listOf((Object[]) new String[]{"timeZone", "date", "timeOfDay"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf7;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata7 = new AppFunctionObjectTypeMetadata(mapMapOf7, listListOf7, "com.google.android.appfunctions.schema.types.v1.DateTime", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_DATETIME_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata7;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata5 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_HOURS = appFunctionIntTypeMetadata5;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata6 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_MINUTES = appFunctionIntTypeMetadata6;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata7 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_NANOS = appFunctionIntTypeMetadata7;
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata8 = new AppFunctionIntTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP_SECONDS = appFunctionIntTypeMetadata8;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf8 = MapsKt.mapOf(TuplesKt.to("hours", appFunctionIntTypeMetadata5), TuplesKt.to("minutes", appFunctionIntTypeMetadata6), TuplesKt.to("nanos", appFunctionIntTypeMetadata7), TuplesKt.to("seconds", appFunctionIntTypeMetadata8));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf8;
            List<String> listListOf8 = CollectionsKt.listOf((Object[]) new String[]{"hours", "minutes", "seconds", "nanos"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf8;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata8 = new AppFunctionObjectTypeMetadata(mapMapOf8, listListOf8, "com.google.android.appfunctions.schema.types.v1.TimeOfDay", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_TYPES_V1_TIMEOFDAY_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata8;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf9 = MapsKt.mapOf(TuplesKt.to("android.net.Uri", appFunctionObjectTypeMetadata), TuplesKt.to("com.google.android.appfunctions.schema.notes.v1.Attachment", appFunctionObjectTypeMetadata2), TuplesKt.to("com.google.android.appfunctions.schema.notes.v1.CreateNoteParams", appFunctionObjectTypeMetadata3), TuplesKt.to("com.google.android.appfunctions.schema.notes.v1.FindNotesParams", appFunctionObjectTypeMetadata4), TuplesKt.to("com.google.android.appfunctions.schema.notes.v1.Note", appFunctionObjectTypeMetadata5), TuplesKt.to("com.google.android.appfunctions.schema.types.v1.Date", appFunctionObjectTypeMetadata6), TuplesKt.to("com.google.android.appfunctions.schema.types.v1.DateTime", appFunctionObjectTypeMetadata7), TuplesKt.to("com.google.android.appfunctions.schema.types.v1.TimeOfDay", appFunctionObjectTypeMetadata8));
            this.componentsMetadataDataTypesMap = mapMapOf9;
            this.componentsMetadata = new AppFunctionComponentsMetadata(mapMapOf9);
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        public Map<String, CompileTimeAppFunctionMetadata> getFunctionIdToMetadataMap() {
            return this.functionIdToMetadataMap;
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        public AppFunctionComponentsMetadata getComponentsMetadata() {
            return this.componentsMetadata;
        }

        /* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInventory$ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject */
        /* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInventory.kt */
        @Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"com/miui/notes/action/provider/$NoteAppFunction_AppFunctionInventory.ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SCHEMA_METADATA", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "PARAMETER_METADATA_CREATENOTEPARAMS_REFERENCE_DATA_TYPE", "Landroidx/appfunctions/metadata/AppFunctionReferenceTypeMetadata;", "CREATENOTEPARAMS_PARAMETER_METADATA", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "PARAMETER_METADATA_LIST", "", "REFERENCE_RESPONSE_VALUE_TYPE", "RESPONSE_METADATA", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "APP_FUNCTION_METADATA", "Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "getAPP_FUNCTION_METADATA", "()Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        private static final class ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject {
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;
            private static final AppFunctionParameterMetadata CREATENOTEPARAMS_PARAMETER_METADATA;
            public static final ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject INSTANCE = new ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject();
            private static final AppFunctionReferenceTypeMetadata PARAMETER_METADATA_CREATENOTEPARAMS_REFERENCE_DATA_TYPE;
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;
            private static final AppFunctionReferenceTypeMetadata REFERENCE_RESPONSE_VALUE_TYPE;
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            private ComMiuiNotesActionProviderNoteAppFunctionCreateNoteMetadataObject() {
            }

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("notes", "createNote", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.CreateNoteParams", false, "");
                PARAMETER_METADATA_CREATENOTEPARAMS_REFERENCE_DATA_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("createNoteParams", true, appFunctionReferenceTypeMetadata, "");
                CREATENOTEPARAMS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.Note", false, "");
                REFERENCE_RESPONSE_VALUE_TYPE = appFunctionReferenceTypeMetadata2;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionReferenceTypeMetadata2, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(NoteAppFunctionIds.CREATE_NOTE_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, null, R2.array.passport_account_user_gender_name, null);
            }

            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInventory$ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject */
        /* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInventory.kt */
        @Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"com/miui/notes/action/provider/$NoteAppFunction_AppFunctionInventory.ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SCHEMA_METADATA", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "PARAMETER_METADATA_FINDNOTESPARAMS_REFERENCE_DATA_TYPE", "Landroidx/appfunctions/metadata/AppFunctionReferenceTypeMetadata;", "FINDNOTESPARAMS_PARAMETER_METADATA", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "PARAMETER_METADATA_LIST", "", "ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE", "ARRAY_RESPONSE_VALUE_TYPE", "Landroidx/appfunctions/metadata/AppFunctionArrayTypeMetadata;", "RESPONSE_METADATA", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "APP_FUNCTION_METADATA", "Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "getAPP_FUNCTION_METADATA", "()Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        private static final class ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject {
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;
            private static final AppFunctionArrayTypeMetadata ARRAY_RESPONSE_VALUE_TYPE;
            private static final AppFunctionReferenceTypeMetadata ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE;
            private static final AppFunctionParameterMetadata FINDNOTESPARAMS_PARAMETER_METADATA;
            public static final ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject INSTANCE = new ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject();
            private static final AppFunctionReferenceTypeMetadata PARAMETER_METADATA_FINDNOTESPARAMS_REFERENCE_DATA_TYPE;
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            private ComMiuiNotesActionProviderNoteAppFunctionFindNotesMetadataObject() {
            }

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("notes", "findNotes", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.FindNotesParams", false, "");
                PARAMETER_METADATA_FINDNOTESPARAMS_REFERENCE_DATA_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("findNotesParams", true, appFunctionReferenceTypeMetadata, "");
                FINDNOTESPARAMS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.Note", false, "");
                ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata2;
                AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata2, false, "");
                ARRAY_RESPONSE_VALUE_TYPE = appFunctionArrayTypeMetadata;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionArrayTypeMetadata, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(NoteAppFunctionIds.FIND_NOTES_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, null, R2.array.passport_account_user_gender_name, null);
            }

            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInventory$ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject */
        /* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInventory.kt */
        @Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"com/miui/notes/action/provider/$NoteAppFunction_AppFunctionInventory.ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SCHEMA_METADATA", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE_PRIMITIVE_ITEM_TYPE", "Landroidx/appfunctions/metadata/AppFunctionStringTypeMetadata;", "PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE", "Landroidx/appfunctions/metadata/AppFunctionArrayTypeMetadata;", "NOTEIDS_PARAMETER_METADATA", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "PARAMETER_METADATA_LIST", "", "ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE", "Landroidx/appfunctions/metadata/AppFunctionReferenceTypeMetadata;", "ARRAY_RESPONSE_VALUE_TYPE", "RESPONSE_METADATA", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "APP_FUNCTION_METADATA", "Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "getAPP_FUNCTION_METADATA", "()Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        private static final class ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject {
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;
            private static final AppFunctionArrayTypeMetadata ARRAY_RESPONSE_VALUE_TYPE;
            private static final AppFunctionReferenceTypeMetadata ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE;
            public static final ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject INSTANCE = new ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject();
            private static final AppFunctionParameterMetadata NOTEIDS_PARAMETER_METADATA;
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;
            private static final AppFunctionArrayTypeMetadata PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE;
            private static final AppFunctionStringTypeMetadata PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE_PRIMITIVE_ITEM_TYPE;
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            private ComMiuiNotesActionProviderNoteAppFunctionGetNotesMetadataObject() {
            }

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("notes", "getNotes", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
                PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE_PRIMITIVE_ITEM_TYPE = appFunctionStringTypeMetadata;
                AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionStringTypeMetadata, false, "");
                PARAMETER_METADATA_NOTEIDS_ARRAY_DATA_TYPE = appFunctionArrayTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("noteIds", true, appFunctionArrayTypeMetadata, "");
                NOTEIDS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.notes.v1.Note", false, "");
                ARRAY_RESPONSE_VALUE_TYPE_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata2 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata, false, "");
                ARRAY_RESPONSE_VALUE_TYPE = appFunctionArrayTypeMetadata2;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionArrayTypeMetadata2, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(NoteAppFunctionIds.GET_NOTES_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, null, R2.array.passport_account_user_gender_name, null);
            }

            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInventory$ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject */
        /* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInventory.kt */
        @Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"com/miui/notes/action/provider/$NoteAppFunction_AppFunctionInventory.ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SCHEMA_METADATA", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "PARAMETER_METADATA_NOTEID_PRIMITIVE_DATA_TYPE", "Landroidx/appfunctions/metadata/AppFunctionStringTypeMetadata;", "NOTEID_PARAMETER_METADATA", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "PARAMETER_METADATA_LIST", "", "PARCELABLE_RESPONSE_VALUE_TYPE", "Landroidx/appfunctions/metadata/AppFunctionParcelableTypeMetadata;", "RESPONSE_METADATA", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "APP_FUNCTION_METADATA", "Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "getAPP_FUNCTION_METADATA", "()Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        private static final class ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject {
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;
            public static final ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject INSTANCE = new ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject();
            private static final AppFunctionParameterMetadata NOTEID_PARAMETER_METADATA;
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;
            private static final AppFunctionStringTypeMetadata PARAMETER_METADATA_NOTEID_PRIMITIVE_DATA_TYPE;
            private static final AppFunctionParcelableTypeMetadata PARCELABLE_RESPONSE_VALUE_TYPE;
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            private ComMiuiNotesActionProviderNoteAppFunctionShowNoteMetadataObject() {
            }

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("notes", "showNote", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
                PARAMETER_METADATA_NOTEID_PRIMITIVE_DATA_TYPE = appFunctionStringTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata(NotifyUtils.KEY_NOTE_ID, true, appFunctionStringTypeMetadata, "");
                NOTEID_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionParcelableTypeMetadata appFunctionParcelableTypeMetadata = new AppFunctionParcelableTypeMetadata("android.app.PendingIntent", false, "");
                PARCELABLE_RESPONSE_VALUE_TYPE = appFunctionParcelableTypeMetadata;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionParcelableTypeMetadata, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(NoteAppFunctionIds.SHOW_NOTE_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, null, R2.array.passport_account_user_gender_name, null);
            }

            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }
    });

    @Override // androidx.appfunctions.internal.AggregatedAppFunctionInventory
    public List<AppFunctionInventory> getInventories() {
        return this.inventories;
    }
}
