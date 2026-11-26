package com.miui.pc.frame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PcDataAsyncTask<Fragment extends DialogManagedFragment, Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static final long DEFAULT_START_OFFSET = 500;
    private Dialog mDialog;
    private WeakReference<Fragment> mFragmentRef;
    private long mStartOffset = DEFAULT_START_OFFSET;
    private Context mContext = NoteApp.getInstance().getApplicationContext();
    private Handler mHandler = new Handler();

    protected abstract Result doBackgroundWork(Context context, Params... paramsArr);

    @Override // android.os.AsyncTask
    protected void onCancelled(Result result) {
    }

    protected void onPostWork(Activity activity, Result result) {
    }

    protected void onPreWork(Activity activity) {
    }

    public PcDataAsyncTask(Fragment fragment) {
        this.mFragmentRef = new WeakReference<>(fragment);
        Logger.INSTANCE.d(getClass().getSimpleName(), "new task for pcdata");
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
            onPreWork(activity);
        }
    }

    @Override // android.os.AsyncTask
    protected final Result doInBackground(Params... paramsArr) {
        Logger.INSTANCE.d(getClass().getSimpleName(), "doInbackground...");
        Thread.currentThread().setName("PcDataAsyncTask");
        return doBackgroundWork(this.mContext, paramsArr);
    }

    @Override // android.os.AsyncTask
    protected final void onPostExecute(Result result) {
        Activity activity = getActivity();
        if (activity != null) {
            onPostWork(activity, result);
        }
    }
}
