package com.miui.todo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListOptionMenu implements IPopMenu {
    private TextView mDelete;
    private TextView mMultiChoice;
    private final View mRootView;
    private final View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.todo.base.TodoListOptionMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.delete) {
                if (TodoListOptionMenu.this.mOnMenuListener != null) {
                    TodoListOptionMenu.this.mOnMenuListener.onItemSelected(view, 1);
                }
                TodoListOptionMenu.this.dismiss();
            } else {
                if (id != R.id.multi_choice) {
                    return;
                }
                if (TodoListOptionMenu.this.mOnMenuListener != null) {
                    TodoListOptionMenu.this.mOnMenuListener.onItemSelected(view, 2);
                }
                TodoListOptionMenu.this.dismiss();
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private final TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.todo.base.TodoListOptionMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (TodoListOptionMenu.this.mOnMenuListener != null) {
                TodoListOptionMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private final TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.todo.base.TodoListOptionMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (TodoListOptionMenu.this.mRootView != null && (viewGroup = (ViewGroup) TodoListOptionMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(TodoListOptionMenu.this.mRootView);
            }
            if (TodoListOptionMenu.this.mOnMenuListener != null) {
                TodoListOptionMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public TodoListOptionMenu(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_todo_list_option_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.base.TodoListOptionMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TodoListOptionMenu.this.dismiss();
            }
        });
        initView(viewInflate);
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.delete);
        this.mDelete = textView;
        textView.setOnClickListener(this.mItemClickListener);
        TextView textView2 = (TextView) view.findViewById(R.id.multi_choice);
        this.mMultiChoice = textView2;
        textView2.setOnClickListener(this.mItemClickListener);
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(View view, final int[] iArr) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        viewGroup.post(new Runnable() { // from class: com.miui.todo.base.TodoListOptionMenu$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$0(iArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(int[] iArr) {
        this.mRootView.findViewById(R.id.content).setX(iArr[0]);
        this.mRootView.findViewById(R.id.content).setY(iArr[1]);
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(this.mShowListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mRootView.getAlpha() != 0.0f) {
            Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
            return;
        }
        try {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
