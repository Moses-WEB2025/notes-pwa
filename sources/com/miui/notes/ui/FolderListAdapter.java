package com.miui.notes.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.ui.menu.PopFolderUtils;

/* JADX INFO: loaded from: classes4.dex */
public class FolderListAdapter extends BaseAdapter {
    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_NAME_INDEX = 1;
    public static final int ITEM_ID_CREATE = 0;
    public static final int ITEM_ID_SHIFT = -1;
    public static final String[] PROJECTION = {"_id", "subject"};
    public static final int VIEW_TYPE_COUNT = 3;
    public static final int VIEW_TYPE_CREATE = 0;
    public static final int VIEW_TYPE_FOLDER = 2;
    public static final int VIEW_TYPE_SHIFT = 1;
    private CursorAdapter mAdapter;
    private long[] mHeaderIds;
    private int[] mHeaderTypes;

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 3;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public static Cursor getStaticCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        int[] iArr = new int[PROJECTION.length];
        int i = 0;
        while (true) {
            String[] strArr = PROJECTION;
            if (i < strArr.length) {
                int columnIndex = cursor.getColumnIndex(strArr[i]);
                if (columnIndex < 0) {
                    return null;
                }
                iArr[i] = columnIndex;
                i++;
            } else {
                MatrixCursor matrixCursor = new MatrixCursor(strArr, cursor.getCount());
                while (cursor.moveToNext()) {
                    MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
                    for (int i2 = 0; i2 < PROJECTION.length; i2++) {
                        rowBuilderNewRow.add(cursor.getString(iArr[i2]));
                    }
                }
                return matrixCursor;
            }
        }
    }

    public static Loader<Cursor> startQueryFolder(Activity activity, long j, int i, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks) {
        return activity.getLoaderManager().restartLoader(i, DataUtils.getSelectionBundle(Notes.Note.CONTENT_URI, PROJECTION, "type=? AND _id<>?", new String[]{String.valueOf(1), String.valueOf(j)}, PopFolderUtils.QUERY_SORT_ORDER), loaderCallbacks);
    }

    public FolderListAdapter(CursorAdapter cursorAdapter, boolean z) {
        this.mAdapter = cursorAdapter;
        if (z) {
            this.mHeaderTypes = new int[]{0};
            this.mHeaderIds = new long[]{0};
        } else {
            this.mHeaderTypes = new int[]{0, 1};
            this.mHeaderIds = new long[]{0, -1};
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int[] iArr = this.mHeaderTypes;
        if (i < iArr.length) {
            return iArr[i];
        }
        return 2;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mAdapter.getCount() + this.mHeaderTypes.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        int[] iArr = this.mHeaderTypes;
        if (i < iArr.length) {
            return null;
        }
        return this.mAdapter.getItem(i - iArr.length);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        int[] iArr = this.mHeaderTypes;
        if (i < iArr.length) {
            return this.mHeaderIds[i];
        }
        return this.mAdapter.getItemId(i - iArr.length);
    }

    public String getFolderName(int i) {
        Cursor cursor = (Cursor) getItem(i);
        if (cursor != null) {
            return cursor.getString(1);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            return getIconView(view, viewGroup, R.string.menu_create_folder, R.drawable.ic_folder_create_daynight);
        }
        if (itemViewType == 1) {
            return getIconView(view, viewGroup, R.string.menu_move_parent_folder, R.drawable.ic_folder_shift_daynight);
        }
        return this.mAdapter.getView(i - this.mHeaderTypes.length, view, viewGroup);
    }

    private View getIconView(View view, ViewGroup viewGroup, int i, int i2) {
        TextView textView;
        if (view == null) {
            view = this.mAdapter.newView(null, null, viewGroup);
            textView = (TextView) view.findViewById(android.R.id.text1);
            view.setTag(textView);
        } else {
            textView = (TextView) view.getTag();
        }
        textView.setText(i);
        Resources resources = viewGroup.getContext().getResources();
        Drawable drawable = resources.getDrawable(i2);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.text_drawable_padding);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
        textView.setCompoundDrawablePadding(dimensionPixelOffset);
        return view;
    }
}
