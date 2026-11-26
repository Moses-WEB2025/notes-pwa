package androidx.appsearch.app;

import androidx.appsearch.exceptions.AppSearchException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface DocumentClassFactory<T> {
    T fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException;

    List<Class<?>> getDependencyDocumentClasses() throws AppSearchException;

    AppSearchSchema getSchema() throws AppSearchException;

    String getSchemaName();

    GenericDocument toGenericDocument(T t) throws AppSearchException;
}
