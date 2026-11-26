package com.miui.notes.feature.noteedit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultCaller;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.component.CreateFolderDialog;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.menu.MenuItemEntity;
import com.miui.notes.ui.menu.PopFolderUtils;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import java.util.ArrayList;
import miuix.appcompat.app.Fragment;
import miuix.popupwidget.widget.DropDownSingleChoiceMenu;

/* JADX INFO: loaded from: classes4.dex */
public class NoteFolderHeader implements INoteInfoHeader {
    private long[] mCheckedIds;
    private Context mContext;
    private CreateFolderDialog mCreateFolderDialog;
    private long mCurrentId;
    private Cursor mCursor;
    public LinearLayout mFolderGroup;
    private ImageView mFolderIcon;
    private long[] mHeaderIds;
    private int[] mHeaderTypes;
    private DropDownSingleChoiceMenu mMenu;
    private INoteInfoHeader.NoteMenuClickListener mMenuClickListener;
    private TextView mTvFolder;
    private Fragment mBindFragment = null;
    private ArrayList<String> mMenuList = new ArrayList<>();
    private ArrayList<MenuItemEntity> mMenuCollections = new ArrayList<>();
    private int mMenuPos = 0;
    private boolean mNotePointer = true;

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void update(WorkingNote workingNote, int i) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateAlarm(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateDataTime(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateNoteHiddenState(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateTextCount(int i) {
    }

    public NoteFolderHeader(Context context, View view) {
        this.mContext = context;
        this.mFolderGroup = (LinearLayout) view;
        this.mTvFolder = (TextView) view.findViewById(R.id.folder_name);
        this.mFolderIcon = (ImageView) view.findViewById(R.id.folder_icon);
        this.mMenu = new DropDownSingleChoiceMenu(this.mContext);
        this.mFolderGroup.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.noteedit.NoteFolderHeader.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BaseHybridNoteEditFragment.removeAiFloatingWindow();
                LiveDataEventBus.with("collapse_theme_panel").setValue(true);
                if (NoteFolderHeader.this.mMenuClickListener == null || NoteFolderHeader.this.mMenuClickListener.onMenuClick()) {
                    LiveDataEventBus.with("folderClick").setValue(null);
                    NoteFolderHeader.this.showMenu(view2);
                }
            }
        });
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public View getView() {
        return this.mFolderGroup;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void dismissMenu() {
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onDestroy() {
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
        }
        this.mContext = null;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void bindFragment(Fragment fragment) {
        this.mBindFragment = fragment;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        try {
            Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mContext);
            int color = this.mContext.getColor(editorStyle.mDirIndicatorIconColor);
            int color2 = this.mContext.getColor(editorStyle.mDirIndicatorBorderColor);
            int color3 = this.mContext.getColor(editorStyle.mDirIndicatorBgColor);
            this.mTvFolder.setTextColor(color);
            LayerDrawable layerDrawable = (LayerDrawable) this.mFolderGroup.getBackground();
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(0);
            GradientDrawable gradientDrawable2 = (GradientDrawable) layerDrawable.getDrawable(1);
            gradientDrawable.setStroke(UIUtils.dip2px(this.mContext, 0.5f), color2);
            gradientDrawable2.setColor(color3);
            VectorDrawableCompat vectorDrawableCompatCreate = VectorDrawableCompat.create(this.mContext.getResources(), R.drawable.ic_note_dir_indicator, this.mContext.getTheme());
            if (vectorDrawableCompatCreate != null) {
                vectorDrawableCompatCreate.setTint(color);
            }
            this.mFolderIcon.setImageDrawable(vectorDrawableCompatCreate);
            this.mTvFolder.setAlpha(1.0f);
            this.mFolderIcon.setAlpha(1.0f);
        } catch (Exception e) {
            Logger.INSTANCE.e("NoteFolderHeader", "error in updateStyle " + e.toString());
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateFolderName(String str) {
        this.mTvFolder.setText(str);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setFolderGroupVisibility(int i) {
        this.mFolderGroup.setVisibility(i);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setHeadMenuInfo(Cursor cursor, long[] jArr, long j) {
        this.mCursor = cursor;
        this.mCheckedIds = jArr;
        this.mCurrentId = j;
        if (isNeedShiftOut()) {
            this.mHeaderTypes = new int[]{0};
            this.mHeaderIds = new long[]{0};
        } else {
            this.mHeaderTypes = new int[]{0, 1};
            this.mHeaderIds = new long[]{0, -1};
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onNoteMenuClick(int i) {
        if (this.mCursor == null) {
            return;
        }
        try {
            long itemId = getItemId(i);
            String folderName = getFolderName(i);
            INoteInfoHeader.NoteMenuClickListener noteMenuClickListener = this.mMenuClickListener;
            if (noteMenuClickListener != null) {
                noteMenuClickListener.onItemChecked(itemId, this.mCheckedIds, folderName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setNoteMenuClickListener(INoteInfoHeader.NoteMenuClickListener noteMenuClickListener) {
        this.mMenuClickListener = noteMenuClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMenu(View view) {
        String systemSubject;
        this.mMenuList.clear();
        this.mMenuCollections.clear();
        Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PopFolderUtils.PROJECTION, FolderStore.SELECTION_FOLDER_USER, null, PopFolderUtils.QUERY_SORT_ORDER);
        if (cursorQuery != null) {
            FolderStore.updateEnableNewDefaultFolderNameForPop(this.mContext, cursorQuery);
            cursorQuery.moveToPosition(-1);
            while (cursorQuery.moveToNext()) {
                long j = cursorQuery.getLong(0);
                if (j != -3) {
                    if (j > 0) {
                        systemSubject = cursorQuery.getString(1);
                    } else {
                        systemSubject = FolderModel.getSystemSubject(j);
                    }
                    this.mMenuList.add(systemSubject);
                    this.mMenuCollections.add(new MenuItemEntity(j, systemSubject));
                }
            }
        }
        this.mMenuList.add(this.mContext.getResources().getString(R.string.unclassfied_folder_name));
        this.mMenuCollections.add(null);
        this.mMenuList.add(NoteApp.getInstance().getString(R.string.menu_new_folder));
        this.mMenuCollections.add(new MenuItemEntity(-10000L, NoteApp.getInstance().getString(R.string.menu_new_folder)));
        final String[] strArr = (String[]) this.mMenuList.toArray(new String[this.mMenuList.size()]);
        if (this.mMenuList.contains(this.mTvFolder.getText().toString())) {
            this.mMenuPos = this.mMenuList.indexOf(this.mTvFolder.getText().toString());
        }
        this.mMenu.setItems(strArr);
        this.mMenu.setSelectedItem(this.mMenuPos);
        this.mMenu.setAnchorView(view);
        this.mMenu.setOnMenuListener(new DropDownSingleChoiceMenu.OnMenuListener() { // from class: com.miui.notes.feature.noteedit.NoteFolderHeader.2
            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onShow() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i) {
                if (NoteFolderHeader.this.mMenuPos == i) {
                    return;
                }
                NoteFolderHeader noteFolderHeader = NoteFolderHeader.this;
                noteFolderHeader.mNotePointer = noteFolderHeader.mMenuPos < i;
                if (i != NoteFolderHeader.this.mMenuList.size() - 1) {
                    NoteFolderHeader.this.mMenuPos = i;
                    NoteFolderHeader.this.updateFolderName(strArr[i]);
                }
                if (i != strArr.length - 1) {
                    NoteFolderHeader.this.mMenuClickListener.onItemPerformClick(i);
                }
                MenuItemEntity menuItemEntity = (MenuItemEntity) NoteFolderHeader.this.mMenuCollections.get(i);
                if (menuItemEntity != null && menuItemEntity.getId() == -10000 && i == NoteFolderHeader.this.mMenuList.size() - 1) {
                    NoteFolderHeader noteFolderHeader2 = NoteFolderHeader.this;
                    noteFolderHeader2.showCreateFolderDialog(i, noteFolderHeader2.mMenuPos);
                }
            }
        });
        this.mMenu.show();
        if (cursorQuery != null) {
            cursorQuery.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateFolderDialog(int i, final int i2) {
        Fragment fragment = this.mBindFragment;
        if (fragment == null || fragment.getContext() == null) {
            Logger.INSTANCE.e("NoteFolderHeader", "mBindFragment is null");
            return;
        }
        CreateFolderDialog createFolderDialog = this.mCreateFolderDialog;
        if (createFolderDialog != null) {
            if (createFolderDialog.isShowing()) {
                this.mCreateFolderDialog.dismiss();
            }
            this.mCreateFolderDialog = null;
        }
        CreateFolderDialog createFolderDialog2 = new CreateFolderDialog(this.mBindFragment.getContext());
        this.mCreateFolderDialog = createFolderDialog2;
        createFolderDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.feature.noteedit.NoteFolderHeader.3
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                CreateFolderDialog createFolderDialog3 = (CreateFolderDialog) dialogInterface;
                Logger.INSTANCE.d("NoteFolderHeader", "showCreateFolderDialog onDismiss id: " + createFolderDialog3.getFolderId() + " subject:" + createFolderDialog3.getFolderSubject());
            }
        });
        this.mCreateFolderDialog.setMenuUpdateListener(new CreateFolderDialog.MenuUpdateListener() { // from class: com.miui.notes.feature.noteedit.NoteFolderHeader.4
            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuList(String str) {
                String systemSubject;
                NoteFolderHeader.this.updateFolderName(str);
                Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PopFolderUtils.PROJECTION, FolderStore.SELECTION_FOLDER_USER, null, PopFolderUtils.QUERY_SORT_ORDER);
                if (cursorQuery != null) {
                    cursorQuery.moveToPosition(-1);
                    int i3 = 0;
                    while (cursorQuery.moveToNext()) {
                        long j = cursorQuery.getLong(0);
                        if (j != -3) {
                            if (j > 0) {
                                systemSubject = cursorQuery.getString(1);
                            } else {
                                systemSubject = FolderModel.getSystemSubject(j);
                            }
                            if (systemSubject.equals(str)) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                    if (i3 == 0 && NoteFolderHeader.this.mMenuList.size() > 2) {
                        NoteFolderHeader.this.mMenuPos = i3;
                        NoteFolderHeader.this.mNotePointer = false;
                    } else if (i3 + 2 == NoteFolderHeader.this.mMenuList.size()) {
                        NoteFolderHeader.this.mMenuPos = i3 + 1;
                        NoteFolderHeader noteFolderHeader = NoteFolderHeader.this;
                        noteFolderHeader.mNotePointer = noteFolderHeader.mMenuPos > i2;
                    } else {
                        NoteFolderHeader.this.mMenuPos = i3;
                        NoteFolderHeader noteFolderHeader2 = NoteFolderHeader.this;
                        noteFolderHeader2.mNotePointer = noteFolderHeader2.mMenuPos > i2;
                    }
                    NoteFolderHeader.this.mMenuClickListener.onItemPerformClick(NoteFolderHeader.this.mMenuPos);
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }

            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuListFailed() {
                NoteFolderHeader.this.mMenuPos = i2;
                NoteFolderHeader noteFolderHeader = NoteFolderHeader.this;
                noteFolderHeader.updateFolderName((String) noteFolderHeader.mMenuList.get(NoteFolderHeader.this.mMenuPos));
            }
        });
        this.mCreateFolderDialog.show();
        ActivityResultCaller activityResultCaller = this.mBindFragment;
        if (activityResultCaller == null || !(activityResultCaller instanceof DialogManagerInterface)) {
            return;
        }
        ((DialogManagerInterface) activityResultCaller).manageDialog(this.mCreateFolderDialog);
    }

    public long getItemId(int i) {
        if (i == this.mMenuList.size() - 2) {
            return this.mHeaderIds[0];
        }
        int pointerFromPos = getPointerFromPos(i);
        if (pointerFromPos >= 0) {
            this.mCursor.moveToPosition(pointerFromPos);
        } else {
            this.mCursor.moveToFirst();
        }
        return this.mCursor.getLong(0);
    }

    public String getFolderName(int i) {
        Resources resources = NoteApp.getInstance().getResources();
        if (i == this.mMenuList.size() - 2) {
            return resources.getString(R.string.menu_move_parent_folder);
        }
        this.mCursor.moveToPosition(getPointerFromPos(i));
        if (this.mCursor.getLong(0) == -6) {
            return NoteApp.getInstance().getString(R.string.excerpt);
        }
        if (this.mCursor.getLong(0) == -7) {
            return NoteApp.getInstance().getString(R.string.blackboard_note);
        }
        if (this.mCursor.getLong(0) == -8) {
            return NoteApp.getInstance().getString(R.string.splash_note);
        }
        return this.mCursor.getString(1);
    }

    private int getPointerFromPos(int i) {
        int iMax;
        if (!isNeedShiftOut() && !this.mNotePointer) {
            iMax = (i + 2) - this.mHeaderTypes.length;
        } else {
            iMax = Math.max(0, (i + 1) - this.mHeaderTypes.length);
        }
        return iMax >= this.mCursor.getCount() ? this.mCursor.getCount() - 1 : iMax;
    }

    private boolean isNeedShiftOut() {
        long j = this.mCurrentId;
        return j == 0 || j == -4097 || j == -4;
    }
}
