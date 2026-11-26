package xc.engine.math.iink.manager;

import android.util.Log;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.MathSystem;

/* JADX INFO: loaded from: classes6.dex */
public class EraserOperationManager {
    private static final String TAG = "EraserOperationManager";
    private static EraserOperationManager instance;
    private MiCurveEngine curveEngine;
    private boolean isEraserOperationActive = false;
    private boolean isPointEraserMode = false;
    private MathSystem mathSystem;
    private OnEraserOperationStateChangeListener stateChangeListener;

    public interface OnEraserOperationStateChangeListener {
        void onEraserOperationEnded(boolean z);

        void onEraserOperationStarted(boolean z);
    }

    private EraserOperationManager() {
    }

    public static EraserOperationManager getInstance() {
        if (instance == null) {
            synchronized (EraserOperationManager.class) {
                if (instance == null) {
                    instance = new EraserOperationManager();
                }
            }
        }
        return instance;
    }

    public void init(MiCurveEngine miCurveEngine) {
        this.curveEngine = miCurveEngine;
        this.mathSystem = miCurveEngine.getMathSystem();
        Log.d(TAG, "EraserOperationManager initialized");
    }

    public void setStateChangeListener(OnEraserOperationStateChangeListener onEraserOperationStateChangeListener) {
        this.stateChangeListener = onEraserOperationStateChangeListener;
    }

    public void startPointEraserOperation() {
        if (this.isEraserOperationActive && this.isPointEraserMode) {
            return;
        }
        this.isEraserOperationActive = true;
        this.isPointEraserMode = true;
        Log.d(TAG, "Point eraser operation started");
        MathSystem mathSystem = this.mathSystem;
        if (mathSystem != null) {
            mathSystem.setRenderingEnabled(false);
            Log.d(TAG, "MathSystem rendering disabled");
        }
        OnEraserOperationStateChangeListener onEraserOperationStateChangeListener = this.stateChangeListener;
        if (onEraserOperationStateChangeListener != null) {
            onEraserOperationStateChangeListener.onEraserOperationStarted(true);
        }
    }

    public void endPointEraserOperation() {
        if (this.isEraserOperationActive && this.isPointEraserMode) {
            this.isEraserOperationActive = false;
            this.isPointEraserMode = false;
            Log.d(TAG, "Point eraser operation ended");
            MathSystem mathSystem = this.mathSystem;
            if (mathSystem != null) {
                mathSystem.setRenderingEnabled(true);
                Log.d(TAG, "MathSystem rendering enabled");
            }
            OnEraserOperationStateChangeListener onEraserOperationStateChangeListener = this.stateChangeListener;
            if (onEraserOperationStateChangeListener != null) {
                onEraserOperationStateChangeListener.onEraserOperationEnded(true);
            }
        }
    }

    public void startLineEraserOperation() {
        if (!this.isEraserOperationActive || this.isPointEraserMode) {
            this.isEraserOperationActive = true;
            this.isPointEraserMode = false;
            Log.d(TAG, "Line eraser operation started");
            MathSystem mathSystem = this.mathSystem;
            if (mathSystem != null) {
                mathSystem.setRenderingEnabled(false);
                Log.d(TAG, "MathSystem rendering disabled");
            }
            OnEraserOperationStateChangeListener onEraserOperationStateChangeListener = this.stateChangeListener;
            if (onEraserOperationStateChangeListener != null) {
                onEraserOperationStateChangeListener.onEraserOperationStarted(false);
            }
        }
    }

    public void endLineEraserOperation() {
        if (!this.isEraserOperationActive || this.isPointEraserMode) {
            return;
        }
        this.isEraserOperationActive = false;
        this.isPointEraserMode = false;
        Log.d(TAG, "Line eraser operation ended");
        MathSystem mathSystem = this.mathSystem;
        if (mathSystem != null) {
            mathSystem.setRenderingEnabled(true);
            Log.d(TAG, "MathSystem rendering enabled");
        }
        OnEraserOperationStateChangeListener onEraserOperationStateChangeListener = this.stateChangeListener;
        if (onEraserOperationStateChangeListener != null) {
            onEraserOperationStateChangeListener.onEraserOperationEnded(false);
        }
    }

    public boolean isEraserOperationActive() {
        return this.isEraserOperationActive;
    }

    public boolean isPointEraserMode() {
        return this.isPointEraserMode;
    }

    public void forceEndAllEraserOperations() {
        if (this.isEraserOperationActive) {
            Log.d(TAG, "Force ending all eraser operations");
            this.isEraserOperationActive = false;
            this.isPointEraserMode = false;
            MathSystem mathSystem = this.mathSystem;
            if (mathSystem != null) {
                mathSystem.setRenderingEnabled(true);
                Log.d(TAG, "MathSystem rendering enabled");
            }
            OnEraserOperationStateChangeListener onEraserOperationStateChangeListener = this.stateChangeListener;
            if (onEraserOperationStateChangeListener != null) {
                onEraserOperationStateChangeListener.onEraserOperationEnded(this.isPointEraserMode);
            }
        }
    }
}
