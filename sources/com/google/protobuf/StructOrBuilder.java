package com.google.protobuf;

import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public interface StructOrBuilder extends MessageOrBuilder {
    boolean containsFields(String str);

    @Deprecated
    Map<String, Value> getFields();

    int getFieldsCount();

    Map<String, Value> getFieldsMap();

    Value getFieldsOrDefault(String str, Value value);

    Value getFieldsOrThrow(String str);
}
