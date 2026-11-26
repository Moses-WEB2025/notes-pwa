package com.miui.notes.ai.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.miui.notes.ai.Action;
import com.miui.notes.base.utils.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes4.dex */
public class SystemNavigationKeyClickListener {
    private static final String TAG = "SystemNavigationKeyClickListener";
    private static volatile SystemNavigationKeyClickListener sKeyClickListener;
    private final List<Action> homeClickActions = new CopyOnWriteArrayList();
    private final List<Action> menuClickActions = new CopyOnWriteArrayList();
    private NavigationKeyClickEventListener navigationKeyClickEventListener;

    public static SystemNavigationKeyClickListener getInstance() {
        if (sKeyClickListener == null) {
            synchronized (SystemNavigationKeyClickListener.class) {
                if (sKeyClickListener == null) {
                    sKeyClickListener = new SystemNavigationKeyClickListener();
                }
            }
        }
        return sKeyClickListener;
    }

    private SystemNavigationKeyClickListener() {
    }

    public void registerHomeKeyEventReceiver(Action action, Action action2, Context context) {
        if (action != null && !this.homeClickActions.contains(action)) {
            this.homeClickActions.add(action);
        }
        if (action2 != null && !this.menuClickActions.contains(action2)) {
            this.menuClickActions.add(action2);
        }
        if (this.navigationKeyClickEventListener == null) {
            if (this.homeClickActions.isEmpty() && this.menuClickActions.isEmpty()) {
                return;
            }
            this.navigationKeyClickEventListener = new NavigationKeyClickEventListener();
            context.getApplicationContext().registerReceiver(this.navigationKeyClickEventListener, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 4);
        }
    }

    public void unregisterHomeKeyEventReceiver(Context context, Action... actionArr) {
        if (actionArr != null && actionArr.length > 0) {
            Stream stream = Arrays.stream(actionArr);
            final List<Action> list = this.homeClickActions;
            Objects.requireNonNull(list);
            Stream streamFilter = stream.filter(new Predicate() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return list.contains((Action) obj);
                }
            });
            final List<Action> list2 = this.homeClickActions;
            Objects.requireNonNull(list2);
            streamFilter.forEach(new Consumer() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    list2.remove((Action) obj);
                }
            });
            Stream stream2 = Arrays.stream(actionArr);
            final List<Action> list3 = this.menuClickActions;
            Objects.requireNonNull(list3);
            Stream streamFilter2 = stream2.filter(new Predicate() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return list3.contains((Action) obj);
                }
            });
            final List<Action> list4 = this.menuClickActions;
            Objects.requireNonNull(list4);
            streamFilter2.forEach(new Consumer() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    list4.remove((Action) obj);
                }
            });
        }
        if (this.homeClickActions.isEmpty() && this.menuClickActions.isEmpty()) {
            destroySystemNavigationKeyClickListener(context);
        }
    }

    public void destroySystemNavigationKeyClickListener(Context context) {
        if (this.homeClickActions.size() > 0) {
            this.homeClickActions.clear();
        }
        if (this.menuClickActions.size() > 0) {
            this.menuClickActions.clear();
        }
        if (this.navigationKeyClickEventListener != null) {
            context.getApplicationContext().unregisterReceiver(this.navigationKeyClickEventListener);
            this.navigationKeyClickEventListener = null;
        }
    }

    private class NavigationKeyClickEventListener extends BroadcastReceiver {
        private final String SYSTEM_GESTURE;
        private final String SYSTEM_HOME_KEY;
        private final String SYSTEM_REASON;
        private final String SYSTEM_RECENTAPPS;

        private NavigationKeyClickEventListener() {
            this.SYSTEM_REASON = "reason";
            this.SYSTEM_HOME_KEY = "homekey";
            this.SYSTEM_RECENTAPPS = "recentapps";
            this.SYSTEM_GESTURE = "fs_gesture";
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logger.INSTANCE.d(SystemNavigationKeyClickListener.TAG, "NavigationKeyClickEventListener onReceive: " + action);
            if (action == null || !action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                return;
            }
            String stringExtra = intent.getStringExtra("reason");
            Logger.INSTANCE.d(SystemNavigationKeyClickListener.TAG, "NavigationKeyClickEventListener reason: " + stringExtra);
            if (stringExtra != null) {
                if (stringExtra.equals("homekey") || stringExtra.equals("fs_gesture")) {
                    SystemNavigationKeyClickListener.this.homeClickActions.forEach(new Consumer() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$NavigationKeyClickEventListener$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Action) obj).run();
                        }
                    });
                } else if (stringExtra.equals("recentapps")) {
                    SystemNavigationKeyClickListener.this.menuClickActions.forEach(new Consumer() { // from class: com.miui.notes.ai.utils.SystemNavigationKeyClickListener$NavigationKeyClickEventListener$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Action) obj).run();
                        }
                    });
                }
            }
        }
    }
}
