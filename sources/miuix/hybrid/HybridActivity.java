package miuix.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import miuix.appcompat.app.AppCompatActivity;
import miuix.internal.hybrid.HybridManager;

/* JADX INFO: loaded from: classes5.dex */
public class HybridActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "com.miui.sdk.hybrid.extra.URL";
    private Set<HybridView> mHybridViews = new HashSet();

    protected boolean autoLoadExtraUrlFromIntent() {
        return true;
    }

    protected int getConfigResId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getContentView());
        View viewFindViewById = findViewById(R.id.hybrid_view);
        if (viewFindViewById == null || !(viewFindViewById instanceof HybridView)) {
            return;
        }
        if (bundle != null) {
            registerHybridView(viewFindViewById);
            Iterator<HybridView> it = this.mHybridViews.iterator();
            while (it.hasNext()) {
                it.next().restoreState(bundle);
            }
            return;
        }
        if (autoLoadExtraUrlFromIntent()) {
            Intent intent = getIntent();
            registerHybridView(viewFindViewById, getConfigResId(), intent != null ? intent.getStringExtra(EXTRA_URL) : null);
        } else {
            registerHybridView(viewFindViewById);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().saveState(bundle);
        }
    }

    protected View getContentView() {
        return getLayoutInflater().inflate(R.layout.hybrid_main, (ViewGroup) null);
    }

    protected final void registerHybridView(View view) {
        registerHybridView(view, getConfigResId());
    }

    protected final void registerHybridView(View view, int i) {
        registerHybridView(view, i, null);
    }

    protected final void registerHybridView(View view, int i, String str) {
        if (!(view instanceof HybridView)) {
            throw new IllegalArgumentException("view being registered is not a hybrid view");
        }
        HybridView hybridView = (HybridView) view;
        HybridManager hybridManager = new HybridManager(this, hybridView);
        hybridView.setHybridManager(hybridManager);
        this.mHybridViews.add(hybridView);
        hybridManager.init(i, str);
    }

    protected final void unregisterHybridView(View view) {
        if (!(view instanceof HybridView)) {
            throw new IllegalArgumentException("view being unregistered is not a hybrid view");
        }
        this.mHybridViews.remove(view);
    }

    private void destroyHybridView() {
        for (HybridView hybridView : this.mHybridViews) {
            if (hybridView != null) {
                if (hybridView.getParent() != null) {
                    ((ViewGroup) hybridView.getParent()).removeView(hybridView);
                }
                hybridView.destroy();
            }
        }
        this.mHybridViews.clear();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onStart();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onResume();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onPause();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onStop();
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onDestroy();
        }
        destroyHybridView();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onActivityResult(i, i2, intent);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            for (HybridView hybridView : this.mHybridViews) {
                if (hybridView.canGoBack() && !hybridView.getHybridManager().isDetached()) {
                    hybridView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(i, keyEvent);
    }
}
