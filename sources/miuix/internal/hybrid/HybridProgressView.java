package miuix.internal.hybrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import miuix.hybrid.R;

/* JADX INFO: loaded from: classes5.dex */
public class HybridProgressView extends ImageView {
    private static final int DELAY = 40;
    private static final int MAX_CUR_PROGRESS = 9500;
    private static final int MAX_PROGRESS = 10000;
    private static final int MIN_CUR_PROGRESS = 800;
    private static final int MSG_UPDATE = 42;
    private static final int STEPS = 10;
    private Rect mBounds;
    private Context mContext;
    private int mCurrentProgress;
    private Handler mHandler;
    private int mIncrement;
    private Rect mReverseBounds;
    private Drawable mReverseDrawable;
    private int mTargetProgress;

    public interface HybridCallBack {
        void run(Message message);
    }

    public int getMax() {
        return 100;
    }

    public HybridProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public HybridProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HybridProgressView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mBounds = new Rect(0, 0, 0, 0);
        this.mCurrentProgress = 0;
        this.mTargetProgress = 0;
        this.mReverseDrawable = this.mContext.getResources().getDrawable(R.drawable.hybrid_progress_reverse);
        this.mHandler = new HybridHandler(new HybridCallBack() { // from class: miuix.internal.hybrid.HybridProgressView$$ExternalSyntheticLambda0
            @Override // miuix.internal.hybrid.HybridProgressView.HybridCallBack
            public final void run(Message message) {
                this.f$0.m4273lambda$init$0$miuixinternalhybridHybridProgressView(message);
            }
        });
        this.mReverseBounds = new Rect(0, 0, 0, 0);
    }

    /* JADX INFO: renamed from: lambda$init$0$miuix-internal-hybrid-HybridProgressView, reason: not valid java name */
    /* synthetic */ void m4273lambda$init$0$miuixinternalhybridHybridProgressView(Message message) {
        if (message.what == 42) {
            int i = this.mCurrentProgress;
            int i2 = this.mTargetProgress;
            if (i < i2) {
                this.mCurrentProgress = Math.min(i2, i + this.mIncrement);
                this.mBounds.right = (getWidth() * this.mCurrentProgress) / 10000;
                invalidate();
                Handler handler = this.mHandler;
                handler.sendMessageDelayed(handler.obtainMessage(42), 40L);
                return;
            }
            if (i > 9500 || i < 800) {
                return;
            }
            this.mCurrentProgress = i + 30;
            this.mBounds.right = (getWidth() * this.mCurrentProgress) / 10000;
            invalidate();
            Handler handler2 = this.mHandler;
            handler2.sendMessageDelayed(handler2.obtainMessage(42), 40L);
        }
    }

    private static class HybridHandler extends Handler {
        private final WeakReference<HybridCallBack> mRunnable;

        public HybridHandler(HybridCallBack hybridCallBack) {
            this.mRunnable = new WeakReference<>(hybridCallBack);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            WeakReference<HybridCallBack> weakReference = this.mRunnable;
            HybridCallBack hybridCallBack = weakReference != null ? weakReference.get() : null;
            if (hybridCallBack != null) {
                hybridCallBack.run(message);
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mBounds.left = 0;
        this.mBounds.right = ((i3 - i) * this.mCurrentProgress) / 10000;
        this.mBounds.top = 0;
        this.mBounds.bottom = i4 - i2;
    }

    public void setProgress(int i) {
        int i2 = i * 100;
        int i3 = this.mTargetProgress;
        if (i3 <= 800) {
            this.mCurrentProgress = i3;
        }
        this.mTargetProgress = i2;
        this.mIncrement = (i2 - this.mCurrentProgress) / 10;
        this.mHandler.removeMessages(42);
        this.mHandler.sendEmptyMessage(42);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        drawable.setBounds(this.mBounds);
        drawable.draw(canvas);
        float width = getWidth() - ((getWidth() * this.mCurrentProgress) / 10000.0f);
        canvas.save();
        canvas.translate(-width, 0.0f);
        this.mReverseBounds.set(0, 0, getWidth(), getHeight());
        this.mReverseDrawable.setBounds(this.mReverseBounds);
        this.mReverseDrawable.draw(canvas);
        canvas.translate(width, 0.0f);
        canvas.restore();
    }
}
