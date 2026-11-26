package com.miui.notes.ai.editor;

import android.webkit.JavascriptInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: GlobalAiEditorScriptInterface.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0017¨\u0006\u0007"}, d2 = {"Lcom/miui/notes/ai/editor/GlobalAiEditorScriptInterface;", "Lcom/miui/notes/ai/editor/AiEditorScriptInterface;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getViewMode", "", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiEditorScriptInterface extends AiEditorScriptInterface {
    public static final String PANEL_MODE = "panel-v2";
    public static final String TAG = "GlobalAiEditorScriptInterface";

    @Override // com.miui.notes.ai.editor.AiEditorScriptInterface
    @JavascriptInterface
    public String getViewMode() {
        return PANEL_MODE;
    }
}
