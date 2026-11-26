package com.xiaomi.miai.api;

import com.xiaomi.miai.api.common.EventPayload;
import com.xiaomi.miai.api.common.InstructionPayload;
import com.xiaomi.miai.api.common.NamespaceName;
import com.xiaomi.miai.api.common.Required;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechGeneral {

    @NamespaceName(name = "RecognizeStreamEnded", namespace = AIApiConstants.SpeechGeneral.NAME)
    public static class RecognizeStreamEnded implements EventPayload {
    }

    @NamespaceName(name = "RecognizeStreamStarted", namespace = AIApiConstants.SpeechGeneral.NAME)
    public static class RecognizeStreamStarted implements EventPayload {
    }

    public enum Vendor {
        XiaoMi("XiaoMi", 1001);

        private String _name;
        private int id;

        Vendor(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    @NamespaceName(name = "Exception", namespace = AIApiConstants.SpeechGeneral.NAME)
    public static class Exception implements InstructionPayload {

        @Required
        private Status status;

        public Exception() {
        }

        public Exception(Status status) {
            this.status = status;
        }

        @Required
        public Exception setStatus(Status status) {
            this.status = status;
            return this;
        }

        @Required
        public Status getStatus() {
            return this.status;
        }
    }
}
