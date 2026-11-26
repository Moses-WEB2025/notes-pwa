package com.miui.richeditor;

import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class EventController {
    public static final int EVENT_ACTIVE_EDIT_CHANGED = 2;
    public static final int EVENT_ACTIVE_NOTE_CHANGED = 1;
    public static final int EVENT_ADJUST_FOLDER_STATE = 4;
    public static final int EVENT_EDITOR_EXPAND = 5;
    public static final int EVENT_NOTE_LOAD_FAILED = 3;
    private static final String TAG = "EventController";
    private ArrayList<WeakReference<EventHandler>> mHandlers;

    public interface EventHandler {
        void handleEvent(int i, Object... objArr);
    }

    private static class Holder {
        static EventController INSTANCE = new EventController();

        private Holder() {
        }
    }

    public static EventController getInstance() {
        return Holder.INSTANCE;
    }

    private EventController() {
        this.mHandlers = new ArrayList<>();
    }

    public void registerHandler(EventHandler eventHandler) {
        for (int size = this.mHandlers.size() - 1; size >= 0; size--) {
            EventHandler eventHandler2 = this.mHandlers.get(size).get();
            if (eventHandler2 == null) {
                this.mHandlers.remove(size);
            } else if (eventHandler2 == eventHandler) {
                Logger.INSTANCE.w(TAG, "Duplicate handler");
                return;
            }
        }
        this.mHandlers.add(new WeakReference<>(eventHandler));
    }

    public void unregisterHandler(EventHandler eventHandler) {
        for (int size = this.mHandlers.size() - 1; size >= 0; size--) {
            EventHandler eventHandler2 = this.mHandlers.get(size).get();
            if (eventHandler2 == null) {
                this.mHandlers.remove(size);
            } else if (eventHandler2 == eventHandler) {
                this.mHandlers.remove(size);
                return;
            }
        }
    }

    public void sendEvent(int i, Object... objArr) {
        for (int size = this.mHandlers.size() - 1; size >= 0; size--) {
            EventHandler eventHandler = this.mHandlers.get(size).get();
            if (eventHandler == null) {
                this.mHandlers.remove(size);
            } else {
                eventHandler.handleEvent(i, objArr);
            }
        }
    }
}
