package com.miui.handwriting.common.view.quickMenu.animationManager;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StateMachine.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\u0003J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateMachine;", "", "currentState", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateEnum;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateEnum;)V", "stateTransactionList", "", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateTransaction;", "addStateTransaction", "getCurrentState", "transaction", "", "action", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/ActionEnum;", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StateMachine {
    public static final String TAG = "StateMachine";
    private StateEnum currentState;
    private final List<StateTransaction> stateTransactionList;

    public StateMachine(StateEnum currentState) {
        Intrinsics.checkNotNullParameter(currentState, "currentState");
        this.currentState = currentState;
        this.stateTransactionList = new ArrayList();
    }

    public final StateTransaction addStateTransaction() {
        StateTransaction stateTransaction = new StateTransaction();
        this.stateTransactionList.add(stateTransaction);
        return stateTransaction;
    }

    public final StateEnum getCurrentState() {
        return this.currentState;
    }

    public final void transaction(ActionEnum action) {
        Object next;
        Intrinsics.checkNotNullParameter(action, "action");
        Log.d(TAG, "currentState: " + this.currentState);
        Iterator<T> it = this.stateTransactionList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            StateTransaction stateTransaction = (StateTransaction) next;
            if (this.currentState == stateTransaction.getCurrentState() && action == stateTransaction.getAction()) {
                break;
            }
        }
        StateTransaction stateTransaction2 = (StateTransaction) next;
        if (stateTransaction2 != null) {
            final StateEnum nextState = stateTransaction2.getNextState();
            ActionEvent event = stateTransaction2.getEvent();
            if (nextState != null) {
                if (stateTransaction2.getIsStateTransactionFrontEvent()) {
                    Log.d(TAG, "transaction action: " + action);
                    this.currentState = nextState;
                    Log.d(TAG, "transaction state to: " + this.currentState);
                    if (event != null) {
                        ActionEvent.handle$default(event, null, 1, null);
                        return;
                    }
                    return;
                }
                Log.d(TAG, "transaction action: " + action);
                if (event == null) {
                    this.currentState = nextState;
                    Log.d(TAG, "transaction state to: " + this.currentState);
                } else {
                    event.handle(new onEventFinished() { // from class: com.miui.handwriting.common.view.quickMenu.animationManager.StateMachine$transaction$1$1
                        @Override // com.miui.handwriting.common.view.quickMenu.animationManager.onEventFinished
                        public void run() {
                            this.this$0.currentState = nextState;
                            Log.d(StateMachine.TAG, "transaction state to: " + this.this$0.currentState);
                        }
                    });
                }
            }
        }
    }
}
