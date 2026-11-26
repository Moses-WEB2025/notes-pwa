package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Status {
    private static List<String> emptyList = new ArrayList();
    private static Trace emtpyTrace = new Trace();
    private int code;
    private Optional<String> message = Optional.empty();
    private Optional<List<String>> details = Optional.empty();
    private Optional<Trace> trace = Optional.empty();

    public static class Trace {
        private String trace_id = "";

        public String getTraceId() {
            return this.trace_id;
        }
    }

    public int getCode() {
        return this.code;
    }

    public Optional<String> getMessage() {
        return this.message;
    }

    public String getMessageOrEmpty() {
        return this.message.getOrDefault("");
    }

    public List<String> getDetails() {
        return this.details.getOrDefault(emptyList);
    }

    public Optional<Trace> getTrace() {
        return this.trace;
    }

    public String getTraceIdOrEmpty() {
        return this.trace.getOrDefault(emtpyTrace).getTraceId();
    }
}
