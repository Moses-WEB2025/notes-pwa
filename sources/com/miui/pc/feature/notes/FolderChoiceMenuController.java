package com.miui.pc.feature.notes;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.component.CreateFolderDialog;
import com.miui.notes.model.FolderModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.ui.menu.MenuItemEntity;
import com.miui.notes.ui.menu.PopFolderUtils;
import java.util.ArrayList;
import miuix.popupwidget.widget.DropDownSingleChoiceMenu;

/* JADX INFO: loaded from: classes4.dex */
public class FolderChoiceMenuController {
    private long[] mCheckedNoteIds;
    private CreateFolderDialog mCreateFolderDialog;
    private long mCurrentNoteId;
    private Cursor mCursor;
    private ImageView mDirIcon;
    private TextView mDirView;
    private long[] mHeaderFolderIds;
    private int[] mHeaderFolderTypes;
    private DropDownSingleChoiceMenu mMenu;
    private View mRootView;
    private ArrayList<String> mMenuList = new ArrayList<>();
    private ArrayList<MenuItemEntity> mMenuCollections = new ArrayList<>();
    private int mMenuPos = 0;
    private boolean mNotePointer = true;
    private MenuItemCheckedListener mMenuClickListener = null;
    private ChoiceListener mChoiceListener = null;

    public interface ChoiceListener {
        void onChoose(int i);
    }

    public interface MenuItemCheckedListener {
        void onMenuItemChecked(long j, long[] jArr, String str);
    }

    public FolderChoiceMenuController(View view) {
        this.mRootView = view;
        initView(view);
    }

    private void initView(View view) {
        this.mDirView = (TextView) view.findViewById(R.id.folder_name);
        this.mDirIcon = (ImageView) view.findViewById(R.id.folder_icon);
        View viewFindViewById = view.findViewById(R.id.folder_name_group);
        this.mRootView = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.FolderChoiceMenuController.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                FolderChoiceMenuController.this.show(view2);
            }
        });
        this.mMenu = new DropDownSingleChoiceMenu(view.getContext());
        this.mCreateFolderDialog = new CreateFolderDialog(view.getContext());
    }

    public void setData(Cursor cursor, long[] jArr, long j) {
        this.mCursor = cursor;
        this.mCheckedNoteIds = jArr;
        this.mCurrentNoteId = j;
        if (isNeedShiftOut()) {
            this.mHeaderFolderTypes = new int[]{0};
            this.mHeaderFolderIds = new long[]{0};
        } else {
            this.mHeaderFolderTypes = new int[]{0, 1};
            this.mHeaderFolderIds = new long[]{0, -1};
        }
    }

    public void show(View view) {
        String systemSubject;
        this.mMenuList.clear();
        this.mMenuCollections.clear();
        this.mMenuList.add(view.getContext().getResources().getString(R.string.unclassfied_folder_name));
        this.mMenuCollections.add(null);
        Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PopFolderUtils.PROJECTION, FolderStore.SELECTION_FOLDER_USER, null, PopFolderUtils.QUERY_SORT_ORDER);
        if (cursorQuery != null) {
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
            cursorQuery.close();
        }
        this.mMenuList.add(NoteApp.getInstance().getString(R.string.menu_new_folder));
        this.mMenuCollections.add(new MenuItemEntity(-10000L, NoteApp.getInstance().getString(R.string.menu_new_folder)));
        final String[] strArr = (String[]) this.mMenuList.toArray(new String[this.mMenuList.size()]);
        if (this.mMenuList.contains(this.mDirView.getText().toString())) {
            this.mMenuPos = this.mMenuList.indexOf(this.mDirView.getText().toString());
        }
        this.mMenu.setItems(strArr);
        this.mMenu.setSelectedItem(this.mMenuPos);
        this.mMenu.setAnchorView(view);
        this.mMenu.setOnMenuListener(new DropDownSingleChoiceMenu.OnMenuListener() { // from class: com.miui.pc.feature.notes.FolderChoiceMenuController.2
            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onShow() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i) {
                if (FolderChoiceMenuController.this.mMenuPos == i) {
                    return;
                }
                FolderChoiceMenuController folderChoiceMenuController = FolderChoiceMenuController.this;
                folderChoiceMenuController.mNotePointer = folderChoiceMenuController.mMenuPos < i;
                if (i != FolderChoiceMenuController.this.mMenuList.size() - 1) {
                    FolderChoiceMenuController.this.mMenuPos = i;
                }
                FolderChoiceMenuController.this.mDirView.setText(strArr[i]);
                if (i != strArr.length - 1) {
                    FolderChoiceMenuController.this.mChoiceListener.onChoose(i);
                }
                MenuItemEntity menuItemEntity = (MenuItemEntity) FolderChoiceMenuController.this.mMenuCollections.get(i);
                if (menuItemEntity != null && menuItemEntity.getId() == -10000 && i == FolderChoiceMenuController.this.mMenuList.size() - 1) {
                    FolderChoiceMenuController folderChoiceMenuController2 = FolderChoiceMenuController.this;
                    folderChoiceMenuController2.showCreateFolderDialog(i, folderChoiceMenuController2.mMenuPos);
                }
            }
        });
        this.mMenu.show();
    }

    public void showView() {
        this.mRootView.setVisibility(0);
    }

    public void hideView() {
        this.mRootView.setVisibility(8);
    }

    public void setMenuClickListener(MenuItemCheckedListener menuItemCheckedListener) {
        this.mMenuClickListener = menuItemCheckedListener;
    }

    public void setChoiceListener(ChoiceListener choiceListener) {
        this.mChoiceListener = choiceListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateFolderDialog(int i, final int i2) {
        CreateFolderDialog createFolderDialog = this.mCreateFolderDialog;
        if (createFolderDialog != null && createFolderDialog.isShowing()) {
            this.mCreateFolderDialog.dismiss();
            this.mCreateFolderDialog = null;
        }
        CreateFolderDialog createFolderDialog2 = this.mCreateFolderDialog;
        if (createFolderDialog2 == null) {
            Logger.INSTANCE.e("NoteApp", "showCreateFolderDialog mCreateFolderDialog is null");
            return;
        }
        createFolderDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.pc.feature.notes.FolderChoiceMenuController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FolderChoiceMenuController.lambda$showCreateFolderDialog$0(dialogInterface);
            }
        });
        this.mCreateFolderDialog.setMenuUpdateListener(new CreateFolderDialog.MenuUpdateListener() { // from class: com.miui.pc.feature.notes.FolderChoiceMenuController.3
            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuList(String str) {
                String systemSubject;
                FolderChoiceMenuController.this.mDirView.setText(str);
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
                    int i4 = i3 + 1;
                    FolderChoiceMenuController.this.mMenuPos = i4;
                    FolderChoiceMenuController folderChoiceMenuController = FolderChoiceMenuController.this;
                    folderChoiceMenuController.mNotePointer = folderChoiceMenuController.mMenuPos > i2;
                    FolderChoiceMenuController.this.mChoiceListener.onChoose(i4);
                    cursorQuery.close();
                }
            }

            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuListFailed() {
                FolderChoiceMenuController.this.mMenuPos = i2;
                FolderChoiceMenuController.this.mDirView.setText((CharSequence) FolderChoiceMenuController.this.mMenuList.get(FolderChoiceMenuController.this.mMenuPos));
            }
        });
        this.mCreateFolderDialog.show();
    }

    static /* synthetic */ void lambda$showCreateFolderDialog$0(DialogInterface dialogInterface) {
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        Logger.INSTANCE.d("FolderChoiceMenu", "showCreateFolderDialog onDismiss id: " + createFolderDialog.getFolderId() + " subject:" + createFolderDialog.getFolderSubject());
    }

    public void setDirName(String str) {
        this.mDirView.setText(str);
    }

    public long getItemId(int i) {
        if (i == 0) {
            return this.mHeaderFolderIds[i];
        }
        if (!isNeedShiftOut() && !this.mNotePointer) {
            this.mCursor.moveToPosition((i + 1) - this.mHeaderFolderTypes.length);
        } else {
            this.mCursor.moveToPosition(i - this.mHeaderFolderTypes.length);
        }
        return this.mCursor.getLong(0);
    }

    public String getFolderName(int i) {
        Resources resources = NoteApp.getInstance().getResources();
        if (i == 0) {
            return resources.getString(R.string.menu_move_parent_folder);
        }
        if (!isNeedShiftOut() && !this.mNotePointer) {
            this.mCursor.moveToPosition((i + 1) - this.mHeaderFolderTypes.length);
        } else {
            this.mCursor.moveToPosition(i - this.mHeaderFolderTypes.length);
        }
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

    public void choose(int i) {
        if (this.mCursor == null) {
            return;
        }
        long itemId = getItemId(i);
        String folderName = getFolderName(i);
        MenuItemCheckedListener menuItemCheckedListener = this.mMenuClickListener;
        if (menuItemCheckedListener != null) {
            menuItemCheckedListener.onMenuItemChecked(itemId, this.mCheckedNoteIds, folderName);
        }
    }

    private boolean isNeedShiftOut() {
        long j = this.mCurrentNoteId;
        return j == 0 || j == -4097 || j == -4;
    }
}
