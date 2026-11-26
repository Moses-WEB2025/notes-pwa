package com.miui.notes.tool.tapshareutils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.miui.common.tool.Logger;
import com.miui.notes.backup.NoteProtos;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareNoteLoader {
    private static final String TAG = "TapShareNoteLoader";

    /* JADX INFO: Access modifiers changed from: private */
    interface DataFieldStrategy {
        void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface EntityFieldStrategy {
        void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x003b A[Catch: Exception -> 0x0097, all -> 0x00bc, TryCatch #1 {Exception -> 0x0097, blocks: (B:11:0x0035, B:18:0x006d, B:21:0x0074, B:25:0x008a, B:13:0x003b, B:15:0x004f, B:16:0x0052), top: B:45:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x004f A[Catch: Exception -> 0x0097, all -> 0x00bc, TryCatch #1 {Exception -> 0x0097, blocks: (B:11:0x0035, B:18:0x006d, B:21:0x0074, B:25:0x008a, B:13:0x003b, B:15:0x004f, B:16:0x0052), top: B:45:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:48:? A[SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 13, insn: 0x00be: MOVE (r3 I:??[OBJECT, ARRAY]) = (r13 I:??[OBJECT, ARRAY]), block:B:40:0x00bd */
    public static NoteProtos.NoteEntity loadWorkingNote(Context context, Long l) throws Throwable {
        Throwable th;
        Exception exc;
        Cursor cursorQuery;
        Cursor cursor;
        Cursor cursor2 = null;
        if (context == null || l == null || l.longValue() <= 0) {
            return null;
        }
        NoteProtos.NoteEntity.Builder builderNewBuilder = NoteProtos.NoteEntity.newBuilder();
        ContentResolver contentResolver = context.getContentResolver();
        try {
            try {
                cursorQuery = contentResolver.query(Notes.Note.CONTENT_URI, null, "_id= ? ", new String[]{String.valueOf(l)}, null);
                if (cursorQuery != null) {
                    try {
                        if (!cursorQuery.moveToFirst()) {
                            Logger.INSTANCE.d(TAG, "Normal query failed, trying privacy query for note: " + l);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            cursorQuery = contentResolver.query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), null, "_id= ? ", new String[]{String.valueOf(l)}, null);
                        }
                    } catch (Exception e) {
                        exc = e;
                        Logger.INSTANCE.e(TAG, "Error querying note entity: " + exc.getMessage());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                } else {
                    Logger.INSTANCE.d(TAG, "Normal query failed, trying privacy query for note: " + l);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    cursorQuery = contentResolver.query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), null, "_id= ? ", new String[]{String.valueOf(l)}, null);
                }
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    setNoteEntityFields(builderNewBuilder, cursorQuery);
                    loadNoteData(context, builderNewBuilder, l.longValue());
                    NoteProtos.NoteEntity noteEntityBuild = builderNewBuilder.build();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return noteEntityBuild;
                }
                Logger.INSTANCE.d(TAG, "Error querying note entity: no data found");
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Exception e2) {
                exc = e2;
                cursorQuery = null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor2 != null) {
                    throw th;
                }
                cursor2.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            cursor2 = cursor;
            if (cursor2 != null) {
                throw th;
            }
            cursor2.close();
            throw th;
        }
    }

    private static void setNoteEntityFields(NoteProtos.NoteEntity.Builder builder, Cursor cursor) {
        for (int columnCount = cursor.getColumnCount() - 1; columnCount >= 0; columnCount--) {
            setNoteEntityField(builder, cursor, columnCount);
        }
    }

    private static void setNoteEntityField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
        EntityFieldStrategy strategy = EntityFieldStrategyFactory.getStrategy(cursor.getColumnName(i));
        if (strategy != null) {
            strategy.applyField(builder, cursor, i);
        }
    }

    private static void loadNoteData(Context context, NoteProtos.NoteEntity.Builder builder, long j) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = contentResolver.query(Notes.CallData.CONTENT_URI, null, "note_id= ? ", new String[]{String.valueOf(j)}, null);
                if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                    Logger.INSTANCE.d(TAG, "Normal query failed for note data, trying privacy query for note: " + j);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    cursorQuery = contentResolver.query(NotesProvider.appendFlag(Notes.CallData.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), null, "note_id= ? ", new String[]{String.valueOf(j)}, null);
                }
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    do {
                        NoteProtos.NoteData.Builder builderNewBuilder = NoteProtos.NoteData.newBuilder();
                        setNoteDataFields(builderNewBuilder, cursorQuery);
                        builder.addData(builderNewBuilder);
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        return;
                    } else {
                        return;
                    }
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Fail to load note data, id=" + j + ",exception:" + e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    private static void setNoteDataFields(NoteProtos.NoteData.Builder builder, Cursor cursor) {
        for (int columnCount = cursor.getColumnCount() - 1; columnCount >= 0; columnCount--) {
            setNoteDataField(builder, cursor, columnCount);
        }
    }

    private static void setNoteDataField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
        DataFieldStrategy strategy = DataFieldStrategyFactory.getStrategy(cursor.getColumnName(i));
        if (strategy != null) {
            strategy.applyField(builder, cursor, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EntityFieldStrategyFactory {
        private static final Map<String, EntityFieldStrategy> STRATEGY_MAP;

        private EntityFieldStrategyFactory() {
        }

        static {
            HashMap map = new HashMap();
            STRATEGY_MAP = map;
            map.put("_id", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda0
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setLuid(String.valueOf(cursor.getLong(i)));
                }
            });
            map.put(Notes.Note.ALERTED_DATE, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda17
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setAlertDate(cursor.getLong(i));
                }
            });
            map.put(Notes.Note.BG_COLOR_ID, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda1
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setBgColorId(cursor.getInt(i));
                }
            });
            map.put("theme_id", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda2
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setThemeId(cursor.getInt(i));
                }
            });
            map.put("created_date", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda3
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setCreatedDate(cursor.getLong(i));
                }
            });
            map.put("modified_date", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda4
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setModifiedDate(cursor.getLong(i));
                }
            });
            map.put(Notes.Note.HAS_ATTACHMENT, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda5
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setHasAttachment(cursor.getInt(i) != 0);
                }
            });
            map.put("snippet", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda6
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.EntityFieldStrategyFactory.lambda$static$7(builder, cursor, i);
                }
            });
            map.put(Notes.Note.STICK_DATE, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda7
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setStickDate(cursor.getLong(i));
                }
            });
            map.put("title", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda8
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setTitle(cursor.getString(i));
                }
            });
            map.put(Notes.Note.NOTE_CONTENT_TYPE, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda9
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setNoteContentType(cursor.getString(i));
                }
            });
            map.put(Notes.Note.MIND_CONTENT, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda10
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setMindContent(cursor.getString(i));
                }
            });
            map.put(Notes.Note.MIND_CONTENT_PLAIN_TEXT, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda11
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setMindContentPlainText(cursor.getString(i));
                }
            });
            map.put("ent_name", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda12
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setEntName(cursor.getString(i));
                }
            });
            map.put(Notes.Note.IMAGES, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda13
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setImages(cursor.getString(i));
                }
            });
            map.put("thumbnail", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda14
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setThumbnail(cursor.getString(i));
                }
            });
            map.put("recognized_file_names", new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda15
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setRecognizedFileNames(cursor.getString(i));
                }
            });
            map.put(Notes.Note.IS_EMPTY, new EntityFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$EntityFieldStrategyFactory$$ExternalSyntheticLambda16
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.EntityFieldStrategy
                public final void applyField(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
                    builder.setIsEmpty(cursor.getInt(i));
                }
            });
        }

        static /* synthetic */ void lambda$static$7(NoteProtos.NoteEntity.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setSnippet(string);
            }
        }

        public static EntityFieldStrategy getStrategy(String str) {
            return STRATEGY_MAP.get(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataFieldStrategyFactory {
        private static final Map<String, DataFieldStrategy> STRATEGY_MAP;

        private DataFieldStrategyFactory() {
        }

        static {
            HashMap map = new HashMap();
            STRATEGY_MAP = map;
            map.put(Notes.Data.MIME_TYPE, new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda0
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$0(builder, cursor, i);
                }
            });
            map.put("created_date", new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda1
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    builder.setCreatedDate(cursor.getLong(i));
                }
            });
            map.put("modified_date", new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda2
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    builder.setModifiedDate(cursor.getLong(i));
                }
            });
            map.put("content", new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda3
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$3(builder, cursor, i);
                }
            });
            map.put("data1", new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda4
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    builder.setData1(cursor.getLong(i));
                }
            });
            map.put(Notes.Data.DATA2, new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda5
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    builder.setData2(cursor.getLong(i));
                }
            });
            map.put("data3", new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda6
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$6(builder, cursor, i);
                }
            });
            map.put(Notes.Data.DATA4, new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda7
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$7(builder, cursor, i);
                }
            });
            map.put(Notes.Data.DATA5, new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda8
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$8(builder, cursor, i);
                }
            });
            map.put(Notes.Data.DATA_CONTENT_TYPE, new DataFieldStrategy() { // from class: com.miui.notes.tool.tapshareutils.TapShareNoteLoader$DataFieldStrategyFactory$$ExternalSyntheticLambda9
                @Override // com.miui.notes.tool.tapshareutils.TapShareNoteLoader.DataFieldStrategy
                public final void applyField(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
                    TapShareNoteLoader.DataFieldStrategyFactory.lambda$static$9(builder, cursor, i);
                }
            });
        }

        static /* synthetic */ void lambda$static$0(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setMimeType(string);
            }
        }

        static /* synthetic */ void lambda$static$3(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setContent(string);
            }
        }

        static /* synthetic */ void lambda$static$6(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setData3(string);
            }
        }

        static /* synthetic */ void lambda$static$7(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setData4(string);
            }
        }

        static /* synthetic */ void lambda$static$8(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setData5(string);
            }
        }

        static /* synthetic */ void lambda$static$9(NoteProtos.NoteData.Builder builder, Cursor cursor, int i) {
            String string = cursor.getString(i);
            if (string != null) {
                builder.setDataContentType(string);
            }
        }

        public static DataFieldStrategy getStrategy(String str) {
            return STRATEGY_MAP.get(str);
        }
    }
}
