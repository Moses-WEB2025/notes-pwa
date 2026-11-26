package com.xiaomi.verificationsdk.internal;

/* JADX INFO: loaded from: classes4.dex */
public class VerifyError {
    private int code;
    private int dialogMsgId;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getDialogMsgId() {
        return this.dialogMsgId;
    }

    public VerifyError(Build build) {
        this.code = build.code;
        this.msg = build.msg;
        this.dialogMsgId = build.dialogMsgId;
    }

    public static class Build {
        private int code;
        private int dialogMsgId;
        private String msg;

        public Build code(int i) {
            this.code = i;
            return this;
        }

        public Build msg(String str) {
            this.msg = str;
            return this;
        }

        public Build dialogMsg(int i) {
            this.dialogMsgId = i;
            return this;
        }

        public VerifyError build() {
            return new VerifyError(this);
        }
    }
}
