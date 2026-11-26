package com.miui.todo.data.mode;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.miui.common.tool.DbQueryHelper;
import com.miui.notes.NoteApp;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoDao;
import com.miui.todo.data.utils.TodoDataUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SearchModeImpl implements SearchMode {
    @Override // com.miui.todo.data.mode.SearchMode
    public Observable<List<TodoEntity>> queryItem(Context context, String str) {
        return queryItem(context, str, 2);
    }

    @Override // com.miui.todo.data.mode.SearchMode
    public Observable<List<TodoEntity>> queryItem(Context context, final String str, int i) {
        return Observable.create(new ObservableOnSubscribe<List<TodoEntity>>() { // from class: com.miui.todo.data.mode.SearchModeImpl.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<TodoEntity>> observableEmitter) {
                ArrayList arrayList = new ArrayList();
                String str2 = str;
                if (str2 == null) {
                    observableEmitter.onNext(arrayList);
                    observableEmitter.onComplete();
                    return;
                }
                String strTrim = str2.trim();
                Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, DbQueryHelper.createLikeSelection("plain_text", DbQueryHelper.formattedKeyWords(str)) + ((Object) DbQueryHelper.wrapAndSelection("local_status <> 3 AND local_status <> 4")), null, "custom_sort_id DESC");
                if (cursorQuery == null || cursorQuery.getCount() == 0 || strTrim.length() == 0) {
                    observableEmitter.onNext(arrayList);
                    observableEmitter.onComplete();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        return;
                    }
                    return;
                }
                cursorQuery.moveToFirst();
                arrayList.add(TodoDataUtils.getTodoEntity(cursorQuery));
                int todoNumLimit = NoteApp.getInstance().getTodoNumLimit();
                for (int i2 = 0; !cursorQuery.isLast() && i2 < todoNumLimit; i2++) {
                    cursorQuery.moveToNext();
                    arrayList.add(TodoDataUtils.getTodoEntity(cursorQuery));
                }
                cursorQuery.close();
                observableEmitter.onNext(arrayList);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override // com.miui.todo.data.mode.SearchMode
    public void deleteItem(TodoEntity todoEntity) {
        TodoDao.deleteItem(todoEntity.getId());
    }

    @Override // com.miui.todo.data.mode.SearchMode
    public void updateItem(TodoEntity todoEntity) {
        TodoDao.updateItem(todoEntity.getId(), todoEntity);
    }

    @Override // com.miui.todo.data.mode.SearchMode
    public void finishItem(TodoEntity todoEntity) {
        TodoDao.updateItemFinishStatus(todoEntity.getId(), true);
    }

    @Override // com.miui.todo.data.mode.SearchMode
    public void unFinishItem(TodoEntity todoEntity) {
        TodoDao.updateItemFinishStatus(todoEntity.getId(), false);
    }
}
