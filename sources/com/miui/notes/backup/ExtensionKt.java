package com.miui.notes.backup;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Extension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\n\u0010\u0000\u001a\u00020\u0003*\u00020\u0003\u001a\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002¨\u0006\u000b"}, d2 = {"clone", "Lcom/miui/notes/backup/TodoProtos$Todos;", "todos", "Lcom/miui/notes/backup/NoteProtos$Notes;", "upgradeFolders", "", "oldVersion", "", "folders", "", "Lcom/miui/notes/backup/NoteProtos$NoteFolder;", "app_PhoneGlobalRelease"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class ExtensionKt {
    public static final TodoProtos.Todos clone(TodoProtos.Todos todos, TodoProtos.Todos todos2) {
        Intrinsics.checkNotNullParameter(todos, "<this>");
        Intrinsics.checkNotNullParameter(todos2, "todos");
        TodoProtos.Todos.Builder builderNewBuilder = TodoProtos.Todos.newBuilder();
        builderNewBuilder.setVersion(todos2.getVersion());
        builderNewBuilder.addAllTodo(new ArrayList(todos2.getTodoList()));
        TodoProtos.Todos todosBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(todosBuild, "build(...)");
        return todosBuild;
    }

    public static final NoteProtos.Notes clone(NoteProtos.Notes notes) {
        Intrinsics.checkNotNullParameter(notes, "<this>");
        NoteProtos.Notes.Builder builderNewBuilder = NoteProtos.Notes.newBuilder();
        builderNewBuilder.setVersion(notes.getVersion());
        ArrayList arrayList = new ArrayList(notes.getFolderList());
        upgradeFolders(notes.getVersion(), arrayList);
        builderNewBuilder.addAllFolder(arrayList);
        builderNewBuilder.addAllNote(new ArrayList(notes.getNoteList()));
        NoteProtos.Notes notesBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(notesBuild, "build(...)");
        return notesBuild;
    }

    private static final void upgradeFolders(int i, List<NoteProtos.NoteFolder> list) {
        if (i >= 13) {
            return;
        }
        if (i < 9) {
            final Function2 function2 = new Function2() { // from class: com.miui.notes.backup.ExtensionKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return Integer.valueOf(ExtensionKt.upgradeFolders$lambda$0((NoteProtos.NoteFolder) obj, (NoteProtos.NoteFolder) obj2));
                }
            };
            CollectionsKt.sortWith(list, new Comparator() { // from class: com.miui.notes.backup.ExtensionKt$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ExtensionKt.upgradeFolders$lambda$1(function2, obj, obj2);
                }
            });
        }
        ListIterator<NoteProtos.NoteFolder> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            NoteProtos.NoteFolder next = listIterator.next();
            NoteProtos.NoteFolder.Builder builderNewBuilder = NoteProtos.NoteFolder.newBuilder(next);
            if (i < 9 && TextUtils.isEmpty(next.getSubject())) {
                builderNewBuilder.setSubject(next.getSnippet());
                builderNewBuilder.setSnippet("");
            }
            NoteProtos.NoteFolder noteFolderBuild = builderNewBuilder.build();
            Intrinsics.checkNotNullExpressionValue(noteFolderBuild, "build(...)");
            listIterator.set(noteFolderBuild);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int upgradeFolders$lambda$1(Function2 function2, Object obj, Object obj2) {
        return ((Number) function2.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int upgradeFolders$lambda$0(NoteProtos.NoteFolder lhs, NoteProtos.NoteFolder rhs) {
        Intrinsics.checkNotNullParameter(lhs, "lhs");
        Intrinsics.checkNotNullParameter(rhs, "rhs");
        long modifiedDate = lhs.getModifiedDate() - rhs.getModifiedDate();
        if (modifiedDate > 0) {
            return 1;
        }
        return modifiedDate == 0 ? 0 : -1;
    }
}
