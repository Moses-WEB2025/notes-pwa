package com.miui.handwriting.common.view.quickMenu.animationManager;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StateTransaction.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\r\u001a\u0004\u0018\u00010\u0005J\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007J\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005J\b\u0010\u0010\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u0011\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u0016\u001a\u00020\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateTransaction;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "currentState", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateEnum;", "action", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/ActionEnum;", "nextState", "event", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/ActionEvent;", "isStateTransactionFrontEvent", "", "getCurrentState", "getAction", "getNextState", "getEvent", "isPreTransaction", "source", "trigger", "to", "doAction", "preTransaction", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StateTransaction {
    private ActionEnum action;
    private StateEnum currentState;
    private ActionEvent event;
    private boolean isStateTransactionFrontEvent;
    private StateEnum nextState;

    public final StateEnum getCurrentState() {
        return this.currentState;
    }

    public final ActionEnum getAction() {
        return this.action;
    }

    public final StateEnum getNextState() {
        return this.nextState;
    }

    public final ActionEvent getEvent() {
        return this.event;
    }

    /* JADX INFO: renamed from: isPreTransaction, reason: from getter */
    public final boolean getIsStateTransactionFrontEvent() {
        return this.isStateTransactionFrontEvent;
    }

    public final StateTransaction source(StateEnum currentState) {
        Intrinsics.checkNotNullParameter(currentState, "currentState");
        this.currentState = currentState;
        return this;
    }

    public final StateTransaction trigger(ActionEnum action) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
        return this;
    }

    public final StateTransaction to(StateEnum nextState) {
        Intrinsics.checkNotNullParameter(nextState, "nextState");
        this.nextState = nextState;
        return this;
    }

    public final StateTransaction doAction(ActionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.event = event;
        return this;
    }

    public final StateTransaction preTransaction() {
        this.isStateTransactionFrontEvent = true;
        return this;
    }
}
