package com.xiaomi.passport.ui.view;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.CustomUtils;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class QueryPhoneAccountViewHolder {
    private static long MAX_WAIT_TIMEOUT_MS = ((long) CustomUtils.QUERY_PHONE_ACCOUNT_SECOND) * 1000;
    private Runnable delayUpdateRunnable;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final WeakReference<View> mRootViewRef;
    private OnActionClickListener onActionClickListener;

    public interface OnActionClickListener {
        void onSkipClicked(View view);
    }

    public QueryPhoneAccountViewHolder(View view) {
        this.mRootViewRef = new WeakReference<>(view);
        if (view == null) {
            return;
        }
        View viewFindViewById = view.findViewById(R.id.skip_query);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.QueryPhoneAccountViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QueryPhoneAccountViewHolder.this.onActionClickListener == null) {
                    return;
                }
                QueryPhoneAccountViewHolder.this.onActionClickListener.onSkipClicked(view2);
            }
        });
        viewFindViewById.setVisibility(4);
        Runnable runnable = new Runnable() { // from class: com.xiaomi.passport.ui.view.QueryPhoneAccountViewHolder.2
            @Override // java.lang.Runnable
            public void run() {
                View viewFindViewById2;
                View view2 = (View) QueryPhoneAccountViewHolder.this.mRootViewRef.get();
                if (view2 == null || (viewFindViewById2 = view2.findViewById(R.id.skip_query)) == null) {
                    return;
                }
                viewFindViewById2.setVisibility(0);
            }
        };
        this.delayUpdateRunnable = runnable;
        this.mHandler.postDelayed(runnable, MAX_WAIT_TIMEOUT_MS);
    }

    public void release() {
        Runnable runnable;
        Handler handler = this.mHandler;
        if (handler == null || (runnable = this.delayUpdateRunnable) == null) {
            return;
        }
        handler.removeCallbacks(runnable);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    public void setVisible(boolean z) {
        View view = this.mRootViewRef.get();
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }
}
