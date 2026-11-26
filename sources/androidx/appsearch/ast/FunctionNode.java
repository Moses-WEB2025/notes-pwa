package androidx.appsearch.ast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public interface FunctionNode extends Node {
    public static final String FUNCTION_NAME_GET_SEARCH_STRING_PARAMETER = "getSearchStringParameter";
    public static final String FUNCTION_NAME_HAS_PROPERTY = "hasProperty";
    public static final String FUNCTION_NAME_PROPERTY_DEFINED = "propertyDefined";
    public static final String FUNCTION_NAME_SEARCH = "search";
    public static final String FUNCTION_NAME_SEMANTIC_SEARCH = "semanticSearch";

    @Retention(RetentionPolicy.SOURCE)
    public @interface FunctionName {
    }

    String getFunctionName();
}
