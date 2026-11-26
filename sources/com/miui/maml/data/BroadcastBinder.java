package com.miui.maml.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Iterator;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class BroadcastBinder extends VariableBinder {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "BroadcastBinder";
    public static final String TAG_NAME = "BroadcastBinder";
    private String mAction;
    private IntentFilter mIntentFilter;
    private final BroadcastReceiver mIntentReceiver;
    private boolean mRegistered;

    private static class Variable extends VariableBinder.Variable {
        public String mExtraName;

        public Variable(Element element, Variables variables) {
            super(element, variables);
            this.mExtraName = element.getAttribute(CallMethod.ARG_EXTRA_STRING);
        }
    }

    public BroadcastBinder(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.miui.maml.data.BroadcastBinder.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i("BroadcastBinder", "onNotify: " + BroadcastBinder.this.toString());
                BroadcastBinder.this.onNotify(context, intent, null);
            }
        };
        load(element);
    }

    protected void onNotify(Context context, Intent intent, Object obj) {
        updateVariables(intent);
        onUpdateComplete();
    }

    protected void onRegister() {
        updateVariables(getContext().mContext.registerReceiver(this.mIntentReceiver, this.mIntentFilter));
        onUpdateComplete();
    }

    protected void onUnregister() {
        getContext().mContext.unregisterReceiver(this.mIntentReceiver);
    }

    protected void register() {
        if (this.mRegistered) {
            return;
        }
        onRegister();
        this.mRegistered = true;
    }

    protected void unregister() {
        if (this.mRegistered) {
            try {
                onUnregister();
            } catch (IllegalArgumentException unused) {
            }
            this.mRegistered = false;
        }
    }

    private void load(Element element) {
        if (element == null) {
            Log.e("BroadcastBinder", "ContentProviderBinder node is null");
            throw new NullPointerException("node is null");
        }
        String attribute = element.getAttribute("action");
        this.mAction = attribute;
        if (TextUtils.isEmpty(attribute)) {
            Log.e("BroadcastBinder", "no action in broadcast binder");
            throw new IllegalArgumentException("no action in broadcast binder element");
        }
        this.mIntentFilter = new IntentFilter(this.mAction);
        loadVariables(element);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.maml.data.VariableBinder
    public Variable onLoadVariable(Element element) {
        return new Variable(element, getContext().mVariables);
    }

    protected void addVariable(Variable variable) {
        this.mVariables.add(variable);
    }

    private void updateVariables(Intent intent) {
        String stringExtra;
        double intExtra;
        if (intent == null) {
            return;
        }
        Log.d("BroadcastBinder", "updateVariables: " + intent);
        Iterator<VariableBinder.Variable> it = this.mVariables.iterator();
        while (it.hasNext()) {
            Variable variable = (Variable) it.next();
            if (variable.mType == 2) {
                stringExtra = intent.getStringExtra(variable.mExtraName);
                variable.set(stringExtra == null ? variable.mDefStringValue : stringExtra);
            } else {
                int i = variable.mType;
                if (i == 3) {
                    intExtra = intent.getIntExtra(variable.mExtraName, (int) variable.mDefNumberValue);
                } else if (i == 4) {
                    intExtra = intent.getLongExtra(variable.mExtraName, (long) variable.mDefNumberValue);
                } else if (i == 5) {
                    intExtra = intent.getFloatExtra(variable.mExtraName, (float) variable.mDefNumberValue);
                } else if (i == 6) {
                    intExtra = intent.getDoubleExtra(variable.mExtraName, variable.mDefNumberValue);
                } else {
                    Log.w("BroadcastBinder", "invalide type" + variable.mTypeStr);
                    intExtra = 0.0d;
                }
                variable.set(intExtra);
                stringExtra = String.format("%f", Double.valueOf(intExtra));
            }
            Log.d("BroadcastBinder", "updateVariables: " + String.format("name:%s type:%s value:%s", variable.mName, variable.mTypeStr, stringExtra));
        }
    }

    @Override // com.miui.maml.data.VariableBinder
    public void init() {
        super.init();
        register();
    }

    @Override // com.miui.maml.data.VariableBinder
    public void finish() {
        super.finish();
        unregister();
    }
}
