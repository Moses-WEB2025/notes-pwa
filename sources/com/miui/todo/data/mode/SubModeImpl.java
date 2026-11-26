package com.miui.todo.data.mode;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.SubTodoList;
import com.miui.todo.utils.TodoUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class SubModeImpl implements SubMode {
    public static final int MAX_SUB_SIZE = 100;
    private static final int NO_EXIST_INDEX = -1;
    private static final String TAG = "SubModeImpl";
    private boolean isExpand;
    private int mAnimIndex;
    private String title;
    private List<SubTodoEntity> unFinishLists = new ArrayList();
    private List<SubTodoEntity> finishLists = new ArrayList();

    public SubModeImpl(String str) {
        this.mAnimIndex = 0;
        this.title = "";
        this.isExpand = true;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            SubTodoList subTodoList = (SubTodoList) new Gson().fromJson(str, SubTodoList.class);
            this.title = subTodoList.getTitle();
            this.isExpand = subTodoList.getIsExpand();
            List<SubTodoEntity> list = subTodoList.getList();
            for (int i = 0; i < list.size(); i++) {
                SubTodoEntity subTodoEntity = list.get(i);
                subTodoEntity.setId(i);
                if (subTodoEntity.getIsFinish()) {
                    this.finishLists.add(subTodoEntity);
                } else {
                    this.unFinishLists.add(subTodoEntity);
                }
            }
            this.mAnimIndex = list.size() - 1;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.todo.data.mode.SubMode
    public boolean add(SubTodoEntity subTodoEntity) {
        if (this.unFinishLists.size() + this.finishLists.size() >= 100) {
            return false;
        }
        int i = this.mAnimIndex + 1;
        this.mAnimIndex = i;
        subTodoEntity.setId(i);
        this.unFinishLists.add(subTodoEntity);
        return true;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public boolean add(SubTodoEntity subTodoEntity, int i) {
        if (this.unFinishLists.size() + this.finishLists.size() >= 100) {
            return false;
        }
        int i2 = this.mAnimIndex + 1;
        this.mAnimIndex = i2;
        subTodoEntity.setId(i2);
        if (i > this.unFinishLists.size()) {
            Logger.INSTANCE.w(TAG, "error! 数组操作越界 index = " + i);
            i = this.unFinishLists.size();
        }
        try {
            this.unFinishLists.add(i, subTodoEntity);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in add " + e);
        }
        return true;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void update(SubTodoEntity subTodoEntity, int i) {
        int size = this.unFinishLists.size();
        if (i < size) {
            this.unFinishLists.set(i, subTodoEntity);
        } else {
            this.finishLists.set(i - size, subTodoEntity);
        }
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void delete(int i) {
        int size = this.unFinishLists.size();
        if (i < size) {
            this.unFinishLists.remove(i);
        } else {
            this.finishLists.remove(i - size);
        }
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void deleteBatch(int[] iArr) {
        Arrays.sort(iArr);
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i = iArr[length];
            int size = this.unFinishLists.size();
            if (i < size) {
                this.unFinishLists.remove(i);
            } else {
                this.finishLists.remove(i - size);
            }
        }
    }

    @Override // com.miui.todo.data.mode.SubMode
    public SubTodoEntity getEntity(int i) {
        int i2;
        int size = this.unFinishLists.size();
        if (i < size) {
            return this.unFinishLists.get(i);
        }
        if (this.finishLists.size() > 0 && (i2 = i - size) <= this.finishLists.size() - 1) {
            return this.finishLists.get(i2);
        }
        return null;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public int getSubTodoSize() {
        return this.unFinishLists.size() + this.finishLists.size();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public int getFinishSubTodoSize() {
        return this.finishLists.size();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public int getUnFinishSubTodoSize() {
        return this.unFinishLists.size();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public int changeFinishStatus(int i, boolean z) {
        if (i == -1) {
            Logger.INSTANCE.d("TodoError", "changeFinishStatus isFinish = " + z);
            return 2;
        }
        int size = this.unFinishLists.size();
        if (i < size) {
            if (z) {
                SubTodoEntity subTodoEntity = this.unFinishLists.get(i);
                subTodoEntity.setIsFinish(true);
                this.unFinishLists.remove(i);
                this.finishLists.add(0, subTodoEntity);
                if (this.unFinishLists.size() == 0) {
                    ToDoStat.reportCardOperateEvent(ToDoStat.CHECK_SUB_DONE, "list", ToDoStat.FINISHED);
                    return 1;
                }
                ToDoStat.reportCardOperateEvent(ToDoStat.CHECK_SUB_DONE, "list", ToDoStat.UNFINISHED);
            }
        } else if (!z) {
            int i2 = i - size;
            SubTodoEntity subTodoEntity2 = this.finishLists.get(i2);
            subTodoEntity2.setIsFinish(false);
            this.finishLists.remove(i2);
            this.unFinishLists.add(0, subTodoEntity2);
            ToDoStat.reportCardOperateEvent(ToDoStat.CHECK_SUB_DONE, "list", ToDoStat.UNFINISHED);
            if (size == 0) {
                return 0;
            }
        }
        return 2;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void changeFinishStatusAll(boolean z) {
        if (z) {
            for (int size = this.unFinishLists.size() - 1; size >= 0; size--) {
                this.unFinishLists.get(size).setIsFinish(true);
                this.finishLists.add(0, this.unFinishLists.get(size));
            }
            this.unFinishLists.clear();
            return;
        }
        for (int i = 0; i < this.finishLists.size(); i++) {
            this.finishLists.get(i).setIsFinish(false);
        }
        this.unFinishLists.addAll(this.finishLists);
        this.finishLists.clear();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public boolean dragItem(int i, int i2, int i3) {
        int size = this.unFinishLists.size();
        if (i >= size || i2 >= size) {
            return false;
        }
        if (i2 == -1 && i3 != 0) {
            return false;
        }
        SubTodoEntity subTodoEntity = this.unFinishLists.get(i);
        this.unFinishLists.remove(i);
        if (i2 != -1) {
            this.unFinishLists.add(i2 + 1, subTodoEntity);
        } else {
            this.unFinishLists.add(i3, subTodoEntity);
        }
        return true;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public SubTodoList getSubTodoList() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.unFinishLists);
        arrayList.addAll(this.finishLists);
        return new SubTodoList(this.title, arrayList, this.isExpand);
    }

    @Override // com.miui.todo.data.mode.SubMode
    public String getCompleteSchedule() {
        int size = this.finishLists.size();
        return TodoUtils.obtainLocalInteger(size) + " \u200c/\u200c " + TodoUtils.obtainLocalInteger(this.unFinishLists.size() + size);
    }

    @Override // com.miui.todo.data.mode.SubMode
    public String getContent() {
        return new Gson().toJson(getSubTodoList());
    }

    @Override // com.miui.todo.data.mode.SubMode
    public String getTitle() {
        return this.title;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void setTitle(String str) {
        this.title = str;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public int getFinishOperation() {
        if (this.unFinishLists.size() + this.finishLists.size() == 0) {
            return 2;
        }
        return this.unFinishLists.size() == 0 ? 1 : 0;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public boolean isExpand() {
        return this.isExpand;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public void setExpand(boolean z) {
        this.isExpand = z;
    }

    @Override // com.miui.todo.data.mode.SubMode
    public String getPlainTextForSearch() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.title);
        sb2.append(StringUtils.SPACE);
        sb.append(this.title).append(StringUtils.SPACE);
        SubTodoList subTodoList = getSubTodoList();
        int size = subTodoList.getList().size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                sb2.append(subTodoList.getList().get(i).getContent().toString());
                sb.append(subTodoList.getList().get(i).getContent().toString());
                if (i != size - 1) {
                    sb.append(StringUtils.SPACE);
                    sb2.append(StringUtils.SPACE);
                }
            }
        }
        return sb.toString();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public String getPlainTextForAlert() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.title);
        SubTodoList subTodoList = getSubTodoList();
        int size = subTodoList.getList().size();
        if (size > 0) {
            sb.append(" (");
            for (int i = 0; i < size; i++) {
                sb.append(subTodoList.getList().get(i).getContent());
                if (i != size - 1) {
                    sb.append(", ");
                }
            }
            sb.append(") ");
        }
        return sb.toString();
    }

    @Override // com.miui.todo.data.mode.SubMode
    public SubMode copy() {
        return new SubModeImpl(getContent());
    }
}
