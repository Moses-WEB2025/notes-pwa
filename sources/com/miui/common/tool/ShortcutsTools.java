package com.miui.common.tool;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.R;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.provider.Notes;
import com.miui.todo.constant.TodoIntent;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ShortcutsTools.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/miui/common/tool/ShortcutsTools;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "IS_FROM_OUTER_INTENT", "", "initShortcuts", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShortcutsTools {
    public static final ShortcutsTools INSTANCE = new ShortcutsTools();
    public static final String IS_FROM_OUTER_INTENT = "isFromOuterIntent";

    private ShortcutsTools() {
    }

    @JvmStatic
    public static final void initShortcuts() {
        Intent intent;
        Logger.INSTANCE.d("ShortCutsTools", "initShortcuts invoked");
        if (!RomUtils.isPadMode()) {
            intent = new Intent("android.intent.action.INSERT_OR_EDIT");
        } else {
            intent = new Intent(NoteIntent.ACTION_PAD_INSERT_OR_EDIT);
        }
        intent.setPackage("com.miui.notes");
        intent.putExtra("android.intent.extra.TEXT", "");
        intent.putExtra(IS_FROM_OUTER_INTENT, true);
        intent.setFlags(268468224);
        intent.setType(Notes.TextData.CONTENT_ITEM_TYPE);
        Intent intent2 = new Intent(TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT);
        intent2.setPackage("com.miui.notes");
        intent2.putExtra(IS_FROM_OUTER_INTENT, true);
        intent2.setFlags(268468224);
        intent2.setType(TodoIntent.TYPE_ADD_TEXT);
        ShortcutManager shortcutManager = (ShortcutManager) BaseNoteApp.INSTANCE.getInstance().getSystemService(ShortcutManager.class);
        ShortcutInfo shortcutInfoBuild = new ShortcutInfo.Builder(BaseNoteApp.INSTANCE.getInstance(), "textId").setShortLabel(BaseNoteApp.INSTANCE.getInstance().getString(R.string.menu_add_description)).setLongLabel(BaseNoteApp.INSTANCE.getInstance().getString(R.string.menu_add_description)).setIcon(Icon.createWithResource(BaseNoteApp.INSTANCE.getInstance(), R.drawable.ic_new_note)).setIntent(intent).build();
        Intrinsics.checkNotNullExpressionValue(shortcutInfoBuild, "build(...)");
        ShortcutInfo shortcutInfoBuild2 = new ShortcutInfo.Builder(BaseNoteApp.INSTANCE.getInstance(), "todoTextId").setShortLabel(BaseNoteApp.INSTANCE.getInstance().getString(R.string.todo_menu_add_description)).setLongLabel(BaseNoteApp.INSTANCE.getInstance().getString(R.string.todo_menu_add_description)).setIcon(Icon.createWithResource(BaseNoteApp.INSTANCE.getInstance(), R.drawable.ic_new_note)).setIntent(intent2).build();
        Intrinsics.checkNotNullExpressionValue(shortcutInfoBuild2, "build(...)");
        if (shortcutManager != null) {
            shortcutManager.setDynamicShortcuts(CollectionsKt.listOf((Object[]) new ShortcutInfo[]{shortcutInfoBuild, shortcutInfoBuild2}));
        }
        Logger.INSTANCE.d("ShortCutsTools", "initShortcuts finished");
    }
}
