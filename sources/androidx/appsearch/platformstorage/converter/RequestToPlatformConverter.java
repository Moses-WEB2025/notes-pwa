package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.RemoveByDocumentIdRequest;
import android.app.appsearch.ReportSystemUsageRequest;
import android.app.appsearch.ReportUsageRequest;
import android.app.appsearch.exceptions.AppSearchException;
import android.os.Build;
import androidx.appsearch.app.GenericDocument;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class RequestToPlatformConverter {
    private RequestToPlatformConverter() {
    }

    public static PutDocumentsRequest toPlatformPutDocumentsRequest(androidx.appsearch.app.PutDocumentsRequest putDocumentsRequest) {
        Preconditions.checkNotNull(putDocumentsRequest);
        PutDocumentsRequest.Builder builder = new PutDocumentsRequest.Builder();
        Iterator<GenericDocument> it = putDocumentsRequest.getGenericDocuments().iterator();
        while (it.hasNext()) {
            builder.addGenericDocuments(GenericDocumentToPlatformConverter.toPlatformGenericDocument(it.next()));
        }
        for (GenericDocument genericDocument : putDocumentsRequest.getTakenActionGenericDocuments()) {
            if (Build.VERSION.SDK_INT >= 35) {
                ApiHelperForV.addTakenActionGenericDocuments(builder, GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocument));
            } else {
                builder.addGenericDocuments(GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocument));
            }
        }
        return builder.build();
    }

    public static GetByDocumentIdRequest toPlatformGetByDocumentIdRequest(androidx.appsearch.app.GetByDocumentIdRequest getByDocumentIdRequest) {
        Preconditions.checkNotNull(getByDocumentIdRequest);
        GetByDocumentIdRequest.Builder builderAddIds = new GetByDocumentIdRequest.Builder(getByDocumentIdRequest.getNamespace()).addIds(getByDocumentIdRequest.getIds());
        for (Map.Entry<String, List<String>> entry : getByDocumentIdRequest.getProjections().entrySet()) {
            builderAddIds.addProjection(entry.getKey(), entry.getValue());
        }
        return builderAddIds.build();
    }

    public static RemoveByDocumentIdRequest toPlatformRemoveByDocumentIdRequest(androidx.appsearch.app.RemoveByDocumentIdRequest removeByDocumentIdRequest) {
        Preconditions.checkNotNull(removeByDocumentIdRequest);
        return new RemoveByDocumentIdRequest.Builder(removeByDocumentIdRequest.getNamespace()).addIds(removeByDocumentIdRequest.getIds()).build();
    }

    public static ReportUsageRequest toPlatformReportUsageRequest(androidx.appsearch.app.ReportUsageRequest reportUsageRequest) {
        Preconditions.checkNotNull(reportUsageRequest);
        return new ReportUsageRequest.Builder(reportUsageRequest.getNamespace(), reportUsageRequest.getDocumentId()).setUsageTimestampMillis(reportUsageRequest.getUsageTimestampMillis()).build();
    }

    public static ReportSystemUsageRequest toPlatformReportSystemUsageRequest(androidx.appsearch.app.ReportSystemUsageRequest reportSystemUsageRequest) {
        Preconditions.checkNotNull(reportSystemUsageRequest);
        return new ReportSystemUsageRequest.Builder(reportSystemUsageRequest.getPackageName(), reportSystemUsageRequest.getDatabaseName(), reportSystemUsageRequest.getNamespace(), reportSystemUsageRequest.getDocumentId()).setUsageTimestampMillis(reportSystemUsageRequest.getUsageTimestampMillis()).build();
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static void addTakenActionGenericDocuments(PutDocumentsRequest.Builder builder, android.app.appsearch.GenericDocument genericDocument) {
            try {
                builder.addTakenActionGenericDocuments(new android.app.appsearch.GenericDocument[]{genericDocument});
            } catch (AppSearchException e) {
                throw new RuntimeException("Unexpected AppSearchException which should not be possible", e);
            }
        }
    }
}
