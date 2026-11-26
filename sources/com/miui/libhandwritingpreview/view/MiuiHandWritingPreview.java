package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface;
import com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel;
import com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel;
import java.util.ArrayList;
import java.util.Arrays;
import xc.engine.utils.FeatureManager;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiHandWritingPreview extends FrameLayout implements MiuiHandWritingPreviewInterface {
    private View mPaintView;
    private MiuiHandViewPreviewModel miuiHandViewPreviewModel;
    private MiuiHandWritingPreviewModel miuiHandWritingPreviewModel;

    public MiuiHandWritingPreview(Context context) {
        this(context, null);
    }

    public MiuiHandWritingPreview(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MiuiHandWritingPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaintView = null;
        this.miuiHandViewPreviewModel = new MiuiHandViewPreviewModel();
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = new MiuiHandWritingPreviewModel(this.miuiHandViewPreviewModel);
        this.miuiHandWritingPreviewModel = miuiHandWritingPreviewModel;
        this.mPaintView = miuiHandWritingPreviewModel.initView(context, attributeSet, i, this.miuiHandViewPreviewModel);
        addView(this.mPaintView, new FrameLayout.LayoutParams(-1, -1));
        this.miuiHandWritingPreviewModel.init();
        FeatureManager.getInstance().setEnableMathRecognition(isSupportCalcHandwrite(context));
    }

    private /* synthetic */ void lambda$new$0(boolean z) {
        ThumbnailRecyclerManager thumbnailManager = this.miuiHandWritingPreviewModel.getThumbnailManager();
        if (thumbnailManager != null) {
            thumbnailManager.pageLimitChange(z);
        }
    }

    public boolean isSupportCalcHandwrite(Context context) {
        try {
            return isCalcHandwriteSupportedDevice(context);
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isCalcHandwriteSupportedDevice(Context context) {
        String[] stringArray;
        String str = Build.DEVICE;
        Resources resources = context.getResources();
        if (miuix.os.Build.IS_INTERNATIONAL_BUILD) {
            stringArray = resources.getStringArray(R.array.calc_handwrite_global_device_whitelist);
        } else {
            stringArray = resources.getStringArray(R.array.calc_handwrite_device_whitelist);
        }
        return new ArrayList(Arrays.asList(stringArray)).contains(str);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mPaintView.layout(0, 0, i3, i4);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.onConfigurationChanged(configuration);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void initUIProperties() {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.initUIProperties();
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setTitleAndTime(String str, long j) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.setTitleAndTime(str, j);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setTitleChangedListener(HeaderView.OnTitleChangedListener onTitleChangedListener) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.setTitleChangedListener(onTitleChangedListener);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public int getCurrentPosition() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.getCurrentPosition();
        }
        return -1;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void load(String str, CommonPageLoadListener commonPageLoadListener) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.load(str, commonPageLoadListener);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void clearTitleCursor() {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.clearTitleCursor();
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setTitleTime(long j) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.setTitleTime(j);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setTitle(String str) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.setTitle(str);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setTitleColor(String str, String str2, int i) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.setTitleColor(str, str2, i);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public String getTitle() {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            return miuiHandWritingPreviewModel.getTitle();
        }
        return "";
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setPreviewOnClick(CommonPreviewClickListener commonPreviewClickListener) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.setPreviewOnClick(commonPreviewClickListener);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void showLoadingDialog() {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.showLoadingDialog();
        }
    }

    public void jumpPage(int i, boolean z) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.jumpPage(i, z);
        }
    }

    public void resetScale(Boolean bool, Float f) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.resetScale(bool.booleanValue(), f.floatValue());
        }
    }

    public float getScale() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.getScale();
        }
        return 1.0f;
    }

    public float getFactoryScale() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.getFactoryScale();
        }
        return 1.0f;
    }

    public PagePreItemView getCurrentItemView() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.getCurrentItemView();
        }
        return null;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            onKeyDown(keyEvent.getKeyCode(), keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            onKeyUp(keyEvent.getKeyCode(), keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setPageStateListener(CommonPageStateListener commonPageStateListener) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.setPageStateListener(commonPageStateListener);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public boolean isContentEmpty() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.isContentEmpty();
        }
        return true;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void updateBackgroundMargin(float f) {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.updateBackgroundMargin(f);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void setSearchResult(int i, int i2, int i3, CommonSearchWrapper commonSearchWrapper) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.setSearchResult(i, i2, i3, commonSearchWrapper);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public int getPageCount() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            return miuiHandViewPreviewModel.getPageCount();
        }
        return 0;
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void saveEntFile(CommonPageSaveListener commonPageSaveListener) {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.saveEntFile(commonPageSaveListener);
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void release() {
        MiuiHandWritingPreviewModel miuiHandWritingPreviewModel = this.miuiHandWritingPreviewModel;
        if (miuiHandWritingPreviewModel != null) {
            miuiHandWritingPreviewModel.release();
        }
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.destroy();
        }
    }

    @Override // com.miui.libhandwritingpreview.interfaces.MiuiHandWritingPreviewInterface
    public void clearHighLight() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel != null) {
            miuiHandViewPreviewModel.clearHighLight();
        }
    }
}
