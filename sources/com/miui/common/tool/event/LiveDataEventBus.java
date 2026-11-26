package com.miui.common.tool.event;

import android.util.ArrayMap;
import androidx.lifecycle.MutableLiveData;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class LiveDataEventBus {
    public static final String BUS_EXIT_LIST_MULTI = "exit_list_multi";
    public static final String BUS_HOMEPAGE_ARRAY_WAY = "homepage_array_way";
    public static final String BUS_HOMEPAGE_GRID_MODE = "homepage_gird_mode";
    public static final String BUS_MODIFY_FOLDER_NAME = "pad_list_modify_folder_name";
    public static final String CREATE_NEW_TODO = "create_new_todo";
    public static final String SEARCH_CLEAR_FOCUS = "search_clear_focus";
    public static final String SELECT_POSITION = "select_position";
    public static final String TEXT_COUNT = "text_count";
    private final Map<String, MutableLiveData<Object>> mObservers;

    private LiveDataEventBus() {
        this.mObservers = new ArrayMap();
    }

    private static class SingletonHolder {
        private static final LiveDataEventBus holder = new LiveDataEventBus();

        private SingletonHolder() {
        }
    }

    private static LiveDataEventBus getInstance() {
        return SingletonHolder.holder;
    }

    public static MutableLiveData<Object> with(String str) {
        return with(str, Object.class);
    }

    public static <T> MutableLiveData<T> with(String str, Class<T> cls) {
        if (!getInstance().mObservers.containsKey(str)) {
            getInstance().mObservers.put(str, new BusLiveData());
        }
        return (MutableLiveData) getInstance().mObservers.get(str);
    }
}
