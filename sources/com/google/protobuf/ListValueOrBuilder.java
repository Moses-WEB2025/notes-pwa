package com.google.protobuf;

import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ListValueOrBuilder extends MessageOrBuilder {
    Value getValues(int i);

    int getValuesCount();

    List<Value> getValuesList();

    ValueOrBuilder getValuesOrBuilder(int i);

    List<? extends ValueOrBuilder> getValuesOrBuilderList();
}
