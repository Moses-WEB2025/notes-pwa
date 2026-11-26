package com.xiaomi.miai.api.common;

import com.xiaomi.common.Optional;

/* JADX INFO: loaded from: classes4.dex */
public class InstructionHeader extends MessageHeader<InstructionHeader> {
    private Optional<String> dialog_id;
    private String id;

    public InstructionHeader() {
    }

    public InstructionHeader(String str, String str2) {
        super(str, str2);
    }

    public String getId() {
        return this.id;
    }

    public InstructionHeader setId(String str) {
        this.id = str;
        return this;
    }

    public Optional<String> getDialogId() {
        Optional<String> optional = this.dialog_id;
        return Optional.ofNullable((optional == null || !optional.isPresent()) ? null : this.dialog_id.get());
    }

    public InstructionHeader setDialogId(String str) {
        this.dialog_id = Optional.ofNullable(str);
        return this;
    }
}
