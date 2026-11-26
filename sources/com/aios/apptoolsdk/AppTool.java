package com.aios.apptoolsdk;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class AppTool {
    private final String description;
    private final String name;
    private final List<AppParam> parameters;

    public abstract AppResult execute(ToolArgs toolArgs);

    protected AppTool(String str, String str2, List<AppParam> list) {
        this.name = str;
        this.description = str2;
        this.parameters = list == null ? Collections.emptyList() : list;
    }

    protected AppTool(String str, String str2) {
        this(str, str2, Collections.emptyList());
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<AppParam> getParameters() {
        return this.parameters;
    }
}
