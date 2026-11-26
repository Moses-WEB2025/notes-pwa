package com.miui.notes.adapter;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import com.miui.notes.ui.fragment.BaseFolderChooser;
import com.miui.notes.ui.widget.ChooserFolderHolder;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChooseFolderAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 #2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001#B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u000e\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012J\u0010\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0012H\u0016J\u0018\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010!\u001a\u00020\u00192\b\u0010\"\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/miui/notes/adapter/ChooseFolderAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/miui/notes/ui/widget/ChooserFolderHolder;", "mDialogFolderChooser", "Lcom/miui/notes/ui/fragment/BaseFolderChooser;", "mCursor", "Landroid/database/Cursor;", "isRoot", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ui/fragment/BaseFolderChooser;Landroid/database/Cursor;Z)V", "mHeaderTypes", "", "mHeaderIds", "", "mNewFolderName", "", "getItemViewType", "", "position", "getItemCount", "getItemId", "", "getFolderName", "setNewFolderName", "", "name", "onCreateViewHolder", "viewGroup", "Landroid/view/ViewGroup;", "viewType", "onBindViewHolder", "chooserFolderHolder", "setCursor", "cursor", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ChooseFolderAdapter extends RecyclerView.Adapter<ChooserFolderHolder> {
    public static final int COLUMN_COUNT_INDEX = 2;
    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_NAME_INDEX = 1;
    private static final int ITEM_ID_CREATE = 0;
    private static final int ITEM_ID_SHIFT = -1;
    private static final String TAG = "ChooseFolderAdapter";
    private static final int VIEW_TYPE_CREATE = 0;
    private static final int VIEW_TYPE_FOLDER = 2;
    private static final int VIEW_TYPE_SHIFT = 1;
    private Cursor mCursor;
    private final BaseFolderChooser mDialogFolderChooser;
    private long[] mHeaderIds;
    private int[] mHeaderTypes;
    private String mNewFolderName;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] PROJECTION = {"_id", "subject", Notes.Note.NOTES_COUNT};

    @JvmStatic
    public static final Cursor getStaticCursor(Cursor cursor) {
        return INSTANCE.getStaticCursor(cursor);
    }

    public ChooseFolderAdapter(BaseFolderChooser mDialogFolderChooser, Cursor cursor, boolean z) {
        Intrinsics.checkNotNullParameter(mDialogFolderChooser, "mDialogFolderChooser");
        this.mDialogFolderChooser = mDialogFolderChooser;
        this.mCursor = cursor;
        if (z) {
            this.mHeaderTypes = new int[]{0};
            this.mHeaderIds = new long[]{0};
        } else {
            this.mHeaderTypes = new int[]{0, 1};
            this.mHeaderIds = new long[]{0, -1};
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        int[] iArr = this.mHeaderTypes;
        if (position < iArr.length) {
            return iArr[position];
        }
        return 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return this.mHeaderTypes.length;
        }
        Intrinsics.checkNotNull(cursor);
        return cursor.getCount() + this.mHeaderTypes.length;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        if (position < this.mHeaderTypes.length) {
            return this.mHeaderIds[position];
        }
        Cursor cursor = this.mCursor;
        Intrinsics.checkNotNull(cursor);
        cursor.moveToPosition(position - this.mHeaderTypes.length);
        Cursor cursor2 = this.mCursor;
        Intrinsics.checkNotNull(cursor2);
        return cursor2.getLong(0);
    }

    public final String getFolderName(int position) {
        Resources resources = BaseNoteApp.INSTANCE.getInstance().getResources();
        if (position < this.mHeaderTypes.length) {
            if (position != 0) {
                if (position == 1) {
                    String string = resources.getString(R.string.menu_move_parent_folder);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return string;
                }
                return "";
            }
            String str = this.mNewFolderName;
            if (str != null) {
                Intrinsics.checkNotNull(str);
                return str;
            }
            String string2 = resources.getString(R.string.menu_create_folder);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            return string2;
        }
        Cursor cursor = this.mCursor;
        Intrinsics.checkNotNull(cursor);
        cursor.moveToPosition(position - this.mHeaderTypes.length);
        Cursor cursor2 = this.mCursor;
        Intrinsics.checkNotNull(cursor2);
        if (cursor2.getLong(0) == -6) {
            String string3 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.excerpt);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return string3;
        }
        Cursor cursor3 = this.mCursor;
        Intrinsics.checkNotNull(cursor3);
        if (cursor3.getLong(0) == -7) {
            String string4 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.blackboard_note);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
            return string4;
        }
        Cursor cursor4 = this.mCursor;
        Intrinsics.checkNotNull(cursor4);
        if (cursor4.getLong(0) == -8) {
            String string5 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.splash_note);
            Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
            return string5;
        }
        Cursor cursor5 = this.mCursor;
        Intrinsics.checkNotNull(cursor5);
        String string6 = cursor5.getString(1);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        return string6;
    }

    public final void setNewFolderName(String name) {
        this.mNewFolderName = name;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ChooserFolderHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        ChooserFolderHolder chooserFolderHolderNewInstance = ChooserFolderHolder.newInstance(viewGroup);
        Intrinsics.checkNotNull(chooserFolderHolderNewInstance);
        return chooserFolderHolderNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ChooserFolderHolder chooserFolderHolder, final int position) {
        Intrinsics.checkNotNullParameter(chooserFolderHolder, "chooserFolderHolder");
        Resources resources = BaseNoteApp.INSTANCE.getInstance().getResources();
        if (position >= this.mHeaderTypes.length) {
            Cursor cursor = this.mCursor;
            Intrinsics.checkNotNull(cursor);
            cursor.moveToPosition(position - this.mHeaderTypes.length);
            Cursor cursor2 = this.mCursor;
            Intrinsics.checkNotNull(cursor2);
            if (cursor2.getLong(0) == -6) {
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.excerpt);
                Cursor cursor3 = this.mCursor;
                Intrinsics.checkNotNull(cursor3);
                chooserFolderHolder.bind(-1, string, cursor3.getString(2));
            } else {
                Cursor cursor4 = this.mCursor;
                Intrinsics.checkNotNull(cursor4);
                if (cursor4.getLong(0) == -7) {
                    String string2 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.blackboard_note);
                    Cursor cursor5 = this.mCursor;
                    Intrinsics.checkNotNull(cursor5);
                    chooserFolderHolder.bind(-1, string2, cursor5.getString(2));
                } else {
                    Cursor cursor6 = this.mCursor;
                    Intrinsics.checkNotNull(cursor6);
                    if (cursor6.getLong(0) == -8) {
                        String string3 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.splash_note);
                        Cursor cursor7 = this.mCursor;
                        Intrinsics.checkNotNull(cursor7);
                        chooserFolderHolder.bind(-1, string3, cursor7.getString(2));
                    } else {
                        Cursor cursor8 = this.mCursor;
                        Intrinsics.checkNotNull(cursor8);
                        String string4 = cursor8.getString(1);
                        Cursor cursor9 = this.mCursor;
                        Intrinsics.checkNotNull(cursor9);
                        chooserFolderHolder.bind(-1, string4, cursor9.getString(2));
                    }
                }
            }
        } else if (position == 0) {
            chooserFolderHolder.bind(R.drawable.folder_choose_new, resources.getString(R.string.menu_create_folder), "");
        } else if (position == 1) {
            chooserFolderHolder.bind(R.drawable.folder_choose_shift_out, resources.getString(R.string.menu_move_parent_folder), "");
        }
        chooserFolderHolder.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.adapter.ChooseFolderAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseFolderAdapter.onBindViewHolder$lambda$0(this.f$0, position, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(ChooseFolderAdapter chooseFolderAdapter, int i, View view) {
        chooseFolderAdapter.mDialogFolderChooser.onFolderItemClick(view, i);
    }

    public final void setCursor(Cursor cursor) {
        this.mCursor = cursor;
    }

    /* JADX INFO: compiled from: ChooseFolderAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/adapter/ChooseFolderAdapter$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "PROJECTION", "", "[Ljava/lang/String;", "COLUMN_ID_INDEX", "", "COLUMN_NAME_INDEX", "COLUMN_COUNT_INDEX", "getStaticCursor", "Landroid/database/Cursor;", "cursor", "VIEW_TYPE_CREATE", "VIEW_TYPE_SHIFT", "VIEW_TYPE_FOLDER", "ITEM_ID_CREATE", "ITEM_ID_SHIFT", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Cursor getStaticCursor(Cursor cursor) {
            if (cursor == null) {
                return null;
            }
            int[] iArr = new int[ChooseFolderAdapter.PROJECTION.length];
            int length = ChooseFolderAdapter.PROJECTION.length;
            for (int i = 0; i < length; i++) {
                int columnIndex = cursor.getColumnIndex(ChooseFolderAdapter.PROJECTION[i]);
                if (columnIndex < 0) {
                    return null;
                }
                iArr[i] = columnIndex;
            }
            MatrixCursor matrixCursor = new MatrixCursor(ChooseFolderAdapter.PROJECTION, cursor.getCount());
            while (cursor.moveToNext()) {
                MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
                int length2 = ChooseFolderAdapter.PROJECTION.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    rowBuilderNewRow.add(cursor.getString(iArr[i2]));
                }
            }
            return matrixCursor;
        }
    }
}
