package xc.richtext.api;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.List;
import xc.engine.api.listener.PageColorListener;
import xc.engine.data.CsBufferBean;
import xc.engine.data.DataInterfaceSet;
import xc.engine.data.IDataSwapListener;
import xc.engine.data.PathInfo;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;
import xc.richtext.utils.ScreenRenderUtil;
import xc.richtext.utils.ScreenShortUtil;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedRender {
    private static final String TAG = "SpannedRender";
    private final RenderListener mListener;
    private PageColorListener mPageColorListener;
    private int mPageIndex = 0;
    private final SpannedMap mSpannedMap = SpannedMap.createInstance();

    public interface DataCallback {
        void callback(List<SpannedData> list);
    }

    public interface RenderListener {
        void onCompleted(boolean z, Bitmap bitmap, RectF rectF);
    }

    public static short getDataType(int i) {
        short s = 7;
        if (i != 7) {
            s = 11;
            if (i != 11) {
                return (short) 1;
            }
        }
        return s;
    }

    public int changedToSpannedType(short s) {
        if (s == 11) {
            return 11;
        }
        return s == 7 ? 7 : 0;
    }

    public SpannedRender(RenderListener renderListener) {
        this.mListener = renderListener;
    }

    public void setPageColorListener(int i, PageColorListener pageColorListener) {
        this.mPageIndex = i;
        this.mPageColorListener = pageColorListener;
    }

    public void render(Bitmap bitmap, String str, String str2, int i) {
        RectF rectF = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        render(bitmap, str, str2, rectF, rectF.width() / 1000.0f, i);
    }

    public void render(final Bitmap bitmap, String str, final String str2, final RectF rectF, final float f, int i) {
        try {
            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmapCreateBitmap);
            DataInterfaceSet dataInterfaceSet = new DataInterfaceSet();
            final boolean[] zArr = {false};
            final RectF[] rectFArr = {null};
            dataInterfaceSet.setDataSwapListener(new IDataSwapListener() { // from class: xc.richtext.api.SpannedRender.1
                @Override // xc.engine.data.IDataSwapListener
                public CsBufferBean onSaveListener(int i2) {
                    return null;
                }

                @Override // xc.engine.data.IDataSwapListener
                public void onLoadListener(int i2, int[] iArr, int i3, List<byte[]> list) {
                    boolean z;
                    if (iArr != null && iArr.length > 0) {
                        int length = iArr.length;
                        short[] sArr = new short[length];
                        for (int i4 = 0; i4 < length; i4++) {
                            sArr[i4] = SpannedRender.getDataType(iArr[i4]);
                        }
                        int[] iArr2 = new int[length];
                        for (int i5 = 0; i5 < length; i5++) {
                            iArr2[i5] = SpannedRender.this.changedToSpannedType(sArr[i5]);
                        }
                        SpannedRender.this.mSpannedMap.loadTextFromBuffer(str2, i3, iArr2, list);
                        SpannedRender.this.mSpannedMap.setDataDrawRatio(f);
                        rectFArr[0] = SpannedRender.this.mSpannedMap.getContentRange();
                        if (SpannedRender.this.mSpannedMap.getDataListAll().isEmpty()) {
                            return;
                        }
                        if (SpannedRender.this.mPageColorListener != null) {
                            int iTransformSpannedColor = SpannedRender.this.mPageColorListener.transformSpannedColor(SpannedRender.this.mPageIndex, -16777216);
                            z = (-16777216 == iTransformSpannedColor || -16777216 == iTransformSpannedColor) ? false : true;
                        } else {
                            z = false;
                        }
                        SpannedRender spannedRender = SpannedRender.this;
                        spannedRender.rendToBitmapBySpannedMap(bitmapCreateBitmap, rectF, spannedRender.mSpannedMap, f, z, 2);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                        SpannedRender spannedRender2 = SpannedRender.this;
                        spannedRender2.rendToBitmapBySpannedMap(bitmapCreateBitmap, rectF, spannedRender2.mSpannedMap, f, z, 1);
                        zArr[0] = true;
                        return;
                    }
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                }
            });
            PathInfo.loadEntFile(str, dataInterfaceSet, i);
            RenderListener renderListener = this.mListener;
            if (renderListener != null) {
                renderListener.onCompleted(zArr[0], bitmapCreateBitmap, rectFArr[0]);
            }
        } catch (Exception unused) {
            RenderListener renderListener2 = this.mListener;
            if (renderListener2 != null) {
                renderListener2.onCompleted(false, null, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rendToBitmapBySpannedMap(Bitmap bitmap, RectF rectF, SpannedMap spannedMap, float f, boolean z, int i) {
        new ScreenShortUtil().screenToBitmap(bitmap, rectF, getDataList(f, spannedMap, rectF), z, i, ScreenRenderUtil.getProvider());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<SpannedData> getDataList(float f, SpannedMap spannedMap, RectF rectF) {
        spannedMap.setDataDrawRatio(f);
        return spannedMap.getDataList(rectF);
    }

    public void getSpannedData(String str, final String str2, int i, final float f, final RectF rectF, final DataCallback dataCallback) {
        DataInterfaceSet dataInterfaceSet = new DataInterfaceSet();
        dataInterfaceSet.setDataSwapListener(new IDataSwapListener() { // from class: xc.richtext.api.SpannedRender.2
            @Override // xc.engine.data.IDataSwapListener
            public CsBufferBean onSaveListener(int i2) {
                return null;
            }

            @Override // xc.engine.data.IDataSwapListener
            public void onLoadListener(int i2, int[] iArr, int i3, List<byte[]> list) {
                DataCallback dataCallback2;
                if (iArr == null || iArr.length <= 0) {
                    return;
                }
                int length = iArr.length;
                short[] sArr = new short[length];
                for (int i4 = 0; i4 < length; i4++) {
                    sArr[i4] = SpannedRender.getDataType(iArr[i4]);
                }
                int[] iArr2 = new int[length];
                for (int i5 = 0; i5 < length; i5++) {
                    iArr2[i5] = SpannedRender.this.changedToSpannedType(sArr[i5]);
                }
                SpannedRender.this.mSpannedMap.loadTextFromBuffer(str2, i3, iArr2, list);
                SpannedRender.this.mSpannedMap.setDataDrawRatio(f);
                if (SpannedRender.this.mSpannedMap.getDataListAll().isEmpty() || (dataCallback2 = dataCallback) == null) {
                    return;
                }
                SpannedRender spannedRender = SpannedRender.this;
                dataCallback2.callback(spannedRender.getDataList(f, spannedRender.mSpannedMap, rectF));
            }
        });
        PathInfo.loadEntFile(str, dataInterfaceSet, i);
    }
}
