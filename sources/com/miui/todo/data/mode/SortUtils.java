package com.miui.todo.data.mode;

import com.miui.notes.base.utils.Logger;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.utils.TimeUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class SortUtils {
    public static final int NO_INDEX_CODE = -1;

    private SortUtils() {
    }

    static class createTimeComparator implements Comparator<TodoEntity> {
        createTimeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            return todoEntity.getCreateTime() - todoEntity2.getCreateTime() > 0 ? -1 : 1;
        }
    }

    static class expireTimeComparator implements Comparator<TodoEntity> {
        expireTimeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            if (todoEntity.getExpireTime() > todoEntity2.getExpireTime()) {
                return 1;
            }
            return (todoEntity.getExpireTime() != todoEntity2.getExpireTime() || todoEntity.getCreateTime() - todoEntity2.getCreateTime() > 0) ? -1 : 1;
        }
    }

    static class scheduleComparator implements Comparator<TodoEntity> {
        scheduleComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            int iCompareDay = TimeUtils.compareDay(todoEntity.getExpireTime(), todoEntity2.getExpireTime());
            return iCompareDay != 0 ? iCompareDay : SortUtils.ScheduleOneDayCompare(todoEntity, todoEntity2);
        }
    }

    static class scheduleOneDayComparator implements Comparator<TodoEntity> {
        scheduleOneDayComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            return SortUtils.ScheduleOneDayCompare(todoEntity, todoEntity2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int ScheduleOneDayCompare(TodoEntity todoEntity, TodoEntity todoEntity2) {
        if (todoEntity.getRemindType() != todoEntity2.getRemindType()) {
            return todoEntity.getRemindType() == 1 ? -1 : 1;
        }
        return todoEntity.getCreateTime() - todoEntity2.getCreateTime() > 0 ? -1 : 1;
    }

    public static class markFinishTimeComparator implements Comparator<TodoEntity> {
        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            long markFinishTime = todoEntity.getMarkFinishTime() - todoEntity2.getMarkFinishTime();
            if (markFinishTime == 0) {
                return todoEntity.getCreateTime() - todoEntity2.getCreateTime() > 0 ? -1 : 1;
            }
            return markFinishTime > 0 ? -1 : 1;
        }
    }

    public static class customSortIdComparator implements Comparator<TodoEntity> {
        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            return todoEntity.getCustomSortId() - todoEntity2.getCustomSortId() > 0 ? -1 : 1;
        }
    }

    public static void refreshAndSortFromBase(List<TodoEntity> list, List<TodoEntity> list2, Comparator<TodoEntity> comparator) {
        try {
            if (comparator == null) {
                Logger.INSTANCE.e("SortUtilsError", "comparator = null !");
                return;
            }
            Collections.sort(list2, comparator);
            if (list != null) {
                list.clear();
            } else {
                list = new LinkedList<>();
            }
            try {
                list.addAll(list2);
            } catch (Exception e) {
                Logger.INSTANCE.e("SortUtils ", " error in " + e.toString());
            }
        } catch (Exception e2) {
            Logger.INSTANCE.e("SortUtilsError", e2.toString());
        }
    }

    public static int getScheduleType(TodoEntity todoEntity) {
        if (todoEntity.getIsFinish() == 1) {
            return 5;
        }
        if (todoEntity.getRemindType() == 0) {
            return 4;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long expireTime = todoEntity.getExpireTime();
        if (expireTime <= jCurrentTimeMillis) {
            return 0;
        }
        long todayStartTimeMillis = expireTime - TimeUtils.getTodayStartTimeMillis();
        if (todayStartTimeMillis < TimeUnit.DAYS.toMillis(1L)) {
            return 1;
        }
        return todayStartTimeMillis < TimeUnit.DAYS.toMillis(2L) ? 2 : 3;
    }

    public static void removeItemOp(TodoEntity todoEntity, int i, List<TodoEntity> list) {
        if (i == -1) {
            list.remove(todoEntity);
        } else {
            list.remove(i);
        }
    }

    public static void updateItemOp(TodoEntity todoEntity, TodoEntity todoEntity2, int i, List<TodoEntity> list) {
        if (i == -1) {
            list.set(list.indexOf(todoEntity), todoEntity2);
        } else {
            list.set(i, todoEntity2);
        }
    }
}
