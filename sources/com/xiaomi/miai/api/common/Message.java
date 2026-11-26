package com.xiaomi.miai.api.common;

import com.xiaomi.miai.api.common.MessageHeader;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Message<H extends MessageHeader<?>, P> {
    private H header;
    private P payload;

    protected Message() {
    }

    protected Message(H h, P p) {
        this.header = h;
        this.payload = p;
    }

    public H getHeader() {
        return this.header;
    }

    public void setHeader(H h) {
        this.header = h;
    }

    public String getNamespace() {
        return getHeader().getNamespace();
    }

    public String getName() {
        return getHeader().getName();
    }

    public String getFullName() {
        return getHeader().getFullName();
    }

    public P getPayload() {
        return this.payload;
    }

    public void setPayload(P p) {
        this.payload = p;
    }

    public String toString() {
        return APIUtils.toJsonString(this);
    }
}
