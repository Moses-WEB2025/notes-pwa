package com.xiaomi.miai.api.common;

/* JADX INFO: loaded from: classes4.dex */
public class EventHeader extends MessageHeader<EventHeader> {
    private String id;

    public EventHeader() {
    }

    public EventHeader(String str, String str2) {
        super(str, str2);
    }

    public String getId() {
        return this.id;
    }

    public EventHeader setId(String str) {
        this.id = str;
        return this;
    }
}
