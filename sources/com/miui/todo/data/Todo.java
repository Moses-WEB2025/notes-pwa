package com.miui.todo.data;

/* JADX INFO: loaded from: classes4.dex */
public class Todo {
    public static final String ACCOUNT_ID = "account_id";
    public static final String AUDIO_FILE_FIELD = "audo_file_field";
    public static final String AUDIO_FILE_NAME = "audio_file_name";
    public static final String AUDIO_FILE_SIZE = "audio_file_size";
    public static final String AUTHORITY = "com.miui.todo.provider";
    public static final String CATEGORY = "category";
    public static final String COLOR_LABEL = "color_label";
    public static final String CONTENT = "content";
    public static final String CREATE_TIME = "create_time";
    public static final String CUSTOM_SORT_ID = "custom_sort_id";
    public static final String DEFAULT_AUDIO_FILE_FIELD = "";
    public static final int DEFAULT_AUDIO_FILE_SIZE = 0;
    public static final String DEFAULT_FOLDER_ETAG = "0";
    public static final int DEFAULT_FOLDER_ID = 0;
    public static final String DEFAULT_SORT_ID = "0";
    public static final long DEFAULT_SYNC_ETAG = 0;
    public static final long DEFAULT_SYNC_ID = 0;
    public static final String EXPIRE_TIME = "expire_time";
    public static final String FIRST_REMIND_TIME = "first_remind_time";
    public static final String FOLDER_ID = "folder_id";
    public static final String HIDE_TYPE = "hide_type";
    public static final String ID = "id";
    public static final String INPUT_TYPE = "input_type";
    public static final String IS_FINISH = "is_finish";
    public static final String IS_FLEX = "is_flex";
    public static final String LABEL = "label";
    public static final String LAST_MODIFIED_TIME = "last_modified_time";
    public static final String LIST_TYPE = "list_type";
    public static final String LOCAL_STAUS = "local_status";
    public static final String MARK_FINISH_TIME = "mark_finish_time";
    public static final String PARENT_ID = "parent_id";
    public static final String PLAIN_TEXT = "plain_text";
    public static final String PRE_INTEGER_DATA1 = "pre_integer1";
    public static final String PRE_INTEGER_DATA2 = "pre_integer2";
    public static final String PRE_INTEGER_DATA3 = "pre_integer3";
    public static final String PRE_INTEGER_DATA4 = "pre_integer4";
    public static final String PRE_INTEGER_DATA5 = "pre_integer5";
    public static final String PRE_INTEGER_DATA6 = "pre_integer6";
    public static final String PRE_INTEGER_DATA7 = "pre_integer7";
    public static final String PRE_INTEGER_DATA8 = "pre_integer8";
    public static final String PRE_TEXT_DATA1 = "pre_text1";
    public static final String PRE_TEXT_DATA2 = "pre_text2";
    public static final String PRE_TEXT_DATA3 = "pre_text3";
    public static final String PRE_TEXT_DATA4 = "pre_text4";
    public static final String PRE_TEXT_DATA5 = "pre_text5";
    public static final String PRE_TEXT_DATA6 = "pre_text6";
    public static final String PRE_TEXT_DATA7 = "pre_text7";
    public static final String PRE_TEXT_DATA8 = "pre_text8";
    public static final String PRIORITY = "priority";
    public static final String PUSH_TYPE = "push_type";
    public static final String REMIND_REPEAT_TYPE = "remind_repeat_type";
    public static final String REMIND_TAG = "remind_tag";
    public static final String REMIND_TIME = "remind_time";
    public static final String REMIND_TYPE = "remind_type";
    public static final String SERVER_STATUS = "server_status";
    public static final String SNIPPET = "snippet";
    public static final String SON_ID = "son_id";
    public static final String SORT_ID = "sort_id";
    public static final String SOURCE = "source";
    public static final String SUB_TODO_INDEX = "sub_todo_index";
    public static final String SYNC_ETAG = "sync_etag";
    public static final String SYNC_ID = "sync_id";
    public static final String SYNC_TAG = "sync_tag";
    public static final String TYPE = "type";
    public static final String URI_TODO_OUTER = "content://com.miui.todo.provider/todo/outer";
    public static final String URI_TODO_OUTER_FINISH = "content://com.miui.todo.provider/todo/out_finish";
    public static final String URI_TODO_OUTER_FLEX = "content://com.miui.todo.provider/todo/out_flex";
    public static final String URI_TODO_OUTER_INSERT = "content://com.miui.todo.provider/todo/out_insert";
    public static final String VERSION = "version";
    public static final String WORDS_COUNT = "words_count";

    public static class AccountStatus {
        public static final int STATUS_DEFAULT = 0;
        public static final int STATUS_KEEP_DATA = 1;
        public static final int STATUS_WIPE_DATA = 4;
        public static final int STATUS_WIPE_SYNCED_DAT = 2;
    }

    public static class Color {
        public static final int NONE = 0;
        public static final int RED = 1;
    }

    public static class FinishOperation {
        public static final int FINISH = 1;
        public static final int NO_CHANGE = 2;
        public static final int UNFINISH = 0;
    }

    public static class FinishType {
        public static final int ALL = 2;
        public static final int NO = 0;
        public static final int YES = 1;
    }

    public static class HideType {
        public static final int COMMON = 0;
        public static final int HIDE = 1;
    }

    public static class InputType {
        public static final int AUDIO = 1;
        public static final int CUSTOM = 0;
    }

    public static class ListType {
        public static final int COMMON = 0;
        public static final int HAS_SON = 1;
    }

    public static class LocalStatus {
        public static final int AFTER_SYNC = 1;
        public static final int DELETE = 3;
        public static final int JUST_SORT = 4;
        public static final int NEED_UPDATE = 2;
        public static final int NEW = 0;
    }

    public static class PushType {
        public static final int ALL = 0;
        public static final int NOTES = 1;
        public static final int TASKS = 2;
    }

    public static class RemindRepeatType {
        public static final int DAY = 1;
        public static final int HOLIDAY = 6;
        public static final int MONTH = 7;
        public static final int MONTOFRI = 3;
        public static final int ONCE = 0;
        public static final int SATTOSUN = 4;
        public static final int WEEK = 2;
        public static final int WORKINGDAY = 5;
        public static final int YEAR = 8;
    }

    public static class RemindType {
        public static final int ACCURATE_TIME = 2;
        public static final int DATE = 1;
        public static final int NO_SET = 0;
    }

    public static class ScheduleType {
        public static final int FURTHER = 3;
        public static final int HAS_EXPIRED = 0;
        public static final int HAS_FINISHED = 5;
        public static final int TODAY = 1;
        public static final int TOMORROW = 2;
        public static final int UNSCHEDULE = 4;
    }

    public static class ServerStatus {
        public static final int DELETE = 1;
        public static final int NORMAL = 0;
        public static final int PURGED = 2;
    }

    public static class Source {
        public static final int CLOUD = 1;
        public static final int LOCAL = 0;
    }

    public static class Table {
        public static final String TODO_NAME = "todo";
    }

    public static class TodoGategory {
        public static final int ALL = 2;
        public static final int NO = 0;
        public static final int SEARCH = 3;
        public static final int TODAY = 10;
        public static final int YES = 1;
    }
}
