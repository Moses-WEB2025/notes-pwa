package com.miui.notes.tool.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AsyncTaskWithProgressDialog<Fragment extends DialogManagedFragment, Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static final long DEFAULT_START_OFFSET = 500;
    private final Context mContext;
    private Dialog mDialog;
    protected WeakReference<Fragment> mFragmentRef;
    private final Handler mHandler;
    private long mStartOffset = DEFAULT_START_OFFSET;
    private final Runnable mRunnable = new Runnable() { // from class: com.miui.notes.tool.util.AsyncTaskWithProgressDialog$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };

    protected abstract Result doBackgroundWork(Context context, Params... paramsArr);

    protected abstract Dialog onCreateDialog(Activity activity);

    protected void onPostWork(Activity activity, Result result) {
    }

    protected void onPreWork(Activity activity) {
    }

    public AsyncTaskWithProgressDialog(Fragment fragment) {
        Logger.INSTANCE.d(getClass().getSimpleName(), "new asyn task");
        this.mFragmentRef = new WeakReference<>(fragment);
        this.mContext = NoteApp.getInstance().getApplicationContext();
        this.mHandler = new Handler();
    }

    public void setStartOffset(long j) {
        this.mStartOffset = j;
    }

    public Fragment getFragment() {
        return this.mFragmentRef.get();
    }

    public Activity getActivity() {
        Fragment fragment = this.mFragmentRef.get();
        if (fragment != null) {
            return fragment.getActivity();
        }
        return null;
    }

    @Override // android.os.AsyncTask
    protected final void onPreExecute() {
        Activity activity = getActivity();
        if (activity != null) {
            this.mHandler.postDelayed(this.mRunnable, this.mStartOffset);
            onPreWork(activity);
        }
    }

    @Override // android.os.AsyncTask
    protected final Result doInBackground(Params... paramsArr) {
        Logger.INSTANCE.d(getClass().getSimpleName(), "doInBackground");
        Thread.currentThread().setName("AsyncTaskProgressDialog");
        return doBackgroundWork(this.mContext, paramsArr);
    }

    @Override // android.os.AsyncTask
    protected final void onPostExecute(Result result) {
        Logger.INSTANCE.d(getClass().getSimpleName(), "onPostExecute");
        dismissDialog();
        Activity activity = getActivity();
        if (activity != null) {
            onPostWork(activity, result);
        }
    }

    @Override // android.os.AsyncTask
    protected void onCancelled(Result result) {
        dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: onShowDialog, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        FragmentActivity activity;
        Fragment fragment = this.mFragmentRef.get();
        if (fragment == null || (activity = fragment.getActivity()) == null || activity.isFinishing()) {
            return;
        }
        Dialog dialogOnCreateDialog = onCreateDialog(activity);
        this.mDialog = dialogOnCreateDialog;
        dialogOnCreateDialog.show();
        fragment.manageDialog(this.mDialog);
    }

    protected void onDismissDialog() {
        if (this.mDialog != null) {
            Fragment fragment = this.mFragmentRef.get();
            if (fragment != null) {
                fragment.releaseDialog(this.mDialog);
            }
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    private void dismissDialog() {
        this.mHandler.removeCallbacks(this.mRunnable);
        onDismissDialog();
    }
}
