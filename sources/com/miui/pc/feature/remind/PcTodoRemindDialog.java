package com.miui.pc.feature.remind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import java.util.ArrayList;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoRemindDialog {
    private static final String ALARM_ACTION_REPEAT = "com.miui.notes.ui.AlarmAlertActivity.REPEAT";
    private static final String ALARM_ACTION_SKIP = "com.miui.notes.ui.AlarmAlertActivity.SKIP";
    private static final String ALARM_ACTION_VIEW = "com.miui.notes.ui.AlarmAlertActivity.VIEW";
    private static final String EXTRA_FORMATTED_BUNDLES = "extra_formatted_bundles";
    private static final String EXTRA_FORMATTED_SNIPPET = "extra_formatted_snippet";
    private static final String EXTRA_TODO_ID = "extra_todo_id";
    private View mBtnCancel;
    private View mBtnOk;
    private ArrayList<Bundle> mBundles;
    private ActionListener mListener;
    private PcTodoRemindAdapter mRemindAdapter;
    private View mRootView;
    private RecyclerView mRvList;
    private View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case 16908313:
                    if (PcTodoRemindDialog.this.mListener != null) {
                        PcTodoRemindDialog.this.mListener.onConfirm();
                    }
                    PcTodoRemindDialog.this.dismiss();
                    break;
                case 16908314:
                    if (PcTodoRemindDialog.this.mListener != null) {
                        PcTodoRemindDialog.this.mListener.onCancel();
                    }
                    PcTodoRemindDialog.this.dismiss();
                    break;
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcTodoRemindDialog.this.mRootView == null || (viewGroup = (ViewGroup) PcTodoRemindDialog.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcTodoRemindDialog.this.mRootView);
        }
    };

    public interface ActionListener {
        void onCancel();

        void onConfirm();
    }

    public PcTodoRemindDialog(Context context, int i, ActionListener actionListener) {
        View viewInflate = LayoutInflater.from(context).inflate(i, (ViewGroup) null);
        this.mRootView = viewInflate;
        initView(viewInflate);
        this.mListener = actionListener;
    }

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PcTodoRemindDialog.this.mListener != null) {
                    PcTodoRemindDialog.this.mListener.onCancel();
                }
                PcTodoRemindDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        View viewFindViewById = view.findViewById(16908313);
        this.mBtnOk = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this.mViewClickListener);
        }
        View viewFindViewById2 = view.findViewById(16908314);
        this.mBtnCancel = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(this.mViewClickListener);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_data);
        this.mRvList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PcTodoRemindAdapter pcTodoRemindAdapter = new PcTodoRemindAdapter(view.getContext());
        this.mRemindAdapter = pcTodoRemindAdapter;
        this.mRvList.setAdapter(pcTodoRemindAdapter);
    }

    public void show(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.5
            @Override // java.lang.Runnable
            public void run() {
                Folme.useAt(PcTodoRemindDialog.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
            }
        });
    }

    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z) {
            this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.remind.PcTodoRemindDialog.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PcTodoRemindDialog.this.mListener != null) {
                        PcTodoRemindDialog.this.mListener.onCancel();
                    }
                    PcTodoRemindDialog.this.dismiss();
                }
            });
        } else {
            this.mRootView.setOnClickListener(null);
        }
    }

    public View getView() {
        return this.mRootView;
    }

    public void addData(Intent intent) {
        ArrayList<Bundle> parcelableArrayListExtra = intent.getParcelableArrayListExtra("extra_formatted_bundles");
        this.mBundles = parcelableArrayListExtra;
        if (parcelableArrayListExtra == null) {
            Logger.INSTANCE.e("PcTodoRemindDialog", "mBundles is null");
            return;
        }
        ArrayList arrayList = new ArrayList(this.mBundles.size());
        for (Bundle bundle : this.mBundles) {
            PcTodoRemindData pcTodoRemindData = new PcTodoRemindData();
            pcTodoRemindData.mId = bundle.getLong("extra_todo_id");
            pcTodoRemindData.mContent = bundle.getCharSequence("extra_formatted_snippet").toString();
            arrayList.add(pcTodoRemindData);
        }
        this.mRemindAdapter.setData(arrayList);
        this.mRemindAdapter.notifyDataSetChanged();
    }
}
