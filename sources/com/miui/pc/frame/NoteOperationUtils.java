package com.miui.pc.frame;

import com.miui.notes.NoteApp;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;

/* JADX INFO: loaded from: classes4.dex */
public class NoteOperationUtils {
    public static void stick(long[] jArr, boolean z) {
        ItemStore.stick(NoteApp.getInstance(), jArr, z);
    }

    public static void move(long[] jArr, long j) {
        NoteStore.move(NoteApp.getInstance(), jArr, j);
    }

    public static void delete(long[] jArr, boolean z) {
        NoteStore.delete(NoteApp.getInstance(), jArr, z);
    }
}
