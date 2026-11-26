package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.CustomUtils;

/* JADX INFO: loaded from: classes4.dex */
public class QueryPhoneAccountLayout extends FrameLayout {
    private static long MAX_WAIT_TIMEOUT_MS = ((long) CustomUtils.QUERY_PHONE_ACCOUNT_SECOND) * 1000;
    private Runnable delayUpdateRunnable;
    private OnActionClickListener onActionClickListener;

    public interface OnActionClickListener {
        void onSkipClicked(View view);
    }

    public QueryPhoneAccountLayout(Context context) {
        super(context);
        initView(context);
    }

    public QueryPhoneAccountLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public QueryPhoneAccountLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.passport_layout_query_phone_account, this);
        final View viewFindViewById = findViewById(R.id.query_skip);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.QueryPhoneAccountLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (QueryPhoneAccountLayout.this.onActionClickListener == null) {
                    return;
                }
                QueryPhoneAccountLayout.this.onActionClickListener.onSkipClicked(view);
            }
        });
        viewFindViewById.setVisibility(4);
        Runnable runnable = new Runnable() { // from class: com.xiaomi.passport.ui.view.QueryPhoneAccountLayout.2
            @Override // java.lang.Runnable
            public void run() {
                viewFindViewById.setVisibility(0);
            }
        };
        this.delayUpdateRunnable = runnable;
        postDelayed(runnable, MAX_WAIT_TIMEOUT_MS);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.delayUpdateRunnable);
    }
}
