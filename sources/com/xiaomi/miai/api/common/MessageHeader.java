package com.xiaomi.miai.api.common;

import com.xiaomi.miai.api.common.MessageHeader;

/* JADX INFO: loaded from: classes4.dex */
public abstract class MessageHeader<T extends MessageHeader<?>> {
    private String name;
    private String namespace;

    protected MessageHeader() {
    }

    protected MessageHeader(String str, String str2) {
        this.namespace = str;
        this.name = str2;
    }

    public void setNamespace(String str) {
        this.namespace = str;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.namespace + "." + this.name;
    }

    public String toString() {
        return String.format("%s(%s, %s)", getClass().getName(), this.namespace, this.name);
    }
}
