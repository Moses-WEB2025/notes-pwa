package com.miui.pc.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.component.CustomMindTextView;
import com.miui.notes.component.PreviewTextView;
import com.miui.notes.model.MindDataHelper;
import com.miui.richeditor.theme.Theme;
import com.miui.todo.data.Todo;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PcMindThumbnailView extends LinearLayout {
    private static ArrayList<String> sSearchContent;
    private String firstTextId;
    private String fourthTextId;
    private String fourthTextParentId;
    private ArrayList<String> mChildrenList;
    private Bitmap mDotBitmap;
    private float mFirstIndicatorLineY;
    private float mFirstIndicatorVerticalCount;
    private float mFirstLineDrawablePadding;
    private int mFirstLineSpreadType;
    private int mFirstTextHeight;
    public CustomMindTextView mFirstTextView;
    private Paint mLinePaint;
    private int mPadLanePadding;
    private int mPadLaneWidth;
    private Paint mPaint;
    private Bitmap mReverseTriangleBitmap;
    private Bitmap mRightTriangleBitmap;
    private int mSecExtraLineHeight;
    private float mSecIndicatorLineY;
    private float mSecIndicatorVerticalCount;
    private float mSecLineDrawablePadding;
    private int mSecLineSpreadType;
    private int mSecTextHeight;
    public CustomMindTextView mSecTextView;
    private int mTextUpperLength;
    private Theme mTheme;
    private int mThirdExtraLineHeight;
    private float mThirdIndicatorLineY;
    private float mThirdIndicatorVerticalCount;
    private float mThirdLineDrawablePadding;
    private int mThirdLineSpreadType;
    private int mThirdTextHeight;
    public CustomMindTextView mThirdTextView;
    private int paddingLeft;
    private int paragraphPaddingLeft;
    private String secTextId;
    private String secTextParentId;
    private String thirdTextId;
    private String thirdTextParentId;

    public PcMindThumbnailView(Context context) {
        this(context, null);
    }

    public PcMindThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PcMindThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildrenList = new ArrayList<>();
        this.firstTextId = "";
        this.secTextId = "";
        this.secTextParentId = "";
        this.thirdTextId = "";
        this.thirdTextParentId = "";
        this.fourthTextId = "";
        this.fourthTextParentId = "";
        this.paddingLeft = (int) getResources().getDimension(R.dimen.ic_44px_size);
        this.paragraphPaddingLeft = (int) getResources().getDimension(R.dimen.ic_48px_size);
        this.mPadLaneWidth = getResources().getDimensionPixelSize(R.dimen.pc_mind_lane_total_width);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.pc_mind_lane_padding);
        this.mPadLanePadding = dimensionPixelSize;
        this.mTextUpperLength = dimensionPixelSize - (dimensionPixelSize * 2);
        createDotBitmap();
        createReverseTriangleBitmap();
        createRightTriangleBitmap();
        this.mPaint = new Paint();
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setColor(getResources().getColor(R.color.note_mind_thumbnail_line_color));
        setWillNotDraw(false);
    }

    private void createDotBitmap() {
        this.mDotBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.pc_ic_note_mind_thumbnail_dot));
    }

    private void createReverseTriangleBitmap() {
        this.mReverseTriangleBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.pc_ic_note_mind_thumbnail_expand));
    }

    private void createRightTriangleBitmap() {
        this.mRightTriangleBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.pc_ic_note_mind_thumbnail_collapse));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mFirstTextHeight = 0;
        this.mSecTextHeight = 0;
        this.mThirdTextHeight = 0;
        this.mFirstIndicatorLineY = 0.0f;
        this.mSecIndicatorLineY = 0.0f;
        this.mThirdIndicatorLineY = 0.0f;
        if (this.mSecLineSpreadType == 2 && this.mSecTextView != null && this.thirdTextParentId.equals(this.secTextId)) {
            removeView(this.mThirdTextView);
            this.mThirdTextView = null;
        }
        if (this.mFirstLineSpreadType == 2 && this.mFirstTextView != null && this.secTextParentId.equals(this.firstTextId)) {
            removeView(this.mSecTextView);
            this.mSecTextView = null;
        }
        CustomMindTextView customMindTextView = this.mFirstTextView;
        if (customMindTextView != null) {
            this.mFirstTextHeight = customMindTextView.getMeasuredHeight();
        }
        CustomMindTextView customMindTextView2 = this.mSecTextView;
        if (customMindTextView2 != null) {
            this.mSecTextHeight = customMindTextView2.getMeasuredHeight();
        }
        CustomMindTextView customMindTextView3 = this.mThirdTextView;
        if (customMindTextView3 != null) {
            this.mThirdTextHeight = customMindTextView3.getMeasuredHeight();
        }
        if (this.mSecLineSpreadType == 1) {
            float f = this.mSecIndicatorVerticalCount;
            if (f >= 2.0f) {
                this.mSecIndicatorLineY = this.mThirdTextHeight;
            } else if (f == 1.0f) {
                this.mSecIndicatorLineY = this.mThirdTextHeight + this.mThirdIndicatorLineY;
            }
        }
        if (this.mFirstLineSpreadType == 1) {
            float f2 = this.mFirstIndicatorVerticalCount;
            if (f2 >= 3.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mThirdTextHeight;
            } else if (f2 == 2.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mSecIndicatorLineY + this.mThirdTextHeight + this.mThirdIndicatorLineY;
            } else if (f2 == 1.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mSecIndicatorLineY;
            }
        }
    }

    public void setStyle(Theme theme) {
        this.mTheme = theme;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void setContent(String str, String str2) {
        int i;
        ArrayList<String> arrayList = new ArrayList<>();
        sSearchContent = arrayList;
        arrayList.add(str2);
        try {
            JSONArray jSONArray = new JSONObject(MindDataHelper.convertToCorrectFormatContent(str)).getJSONObject("data").getJSONArray("children");
            this.mChildrenList.clear();
            this.mFirstIndicatorVerticalCount = 0.0f;
            this.mSecIndicatorVerticalCount = 0.0f;
            this.mThirdIndicatorVerticalCount = 0.0f;
            parseJsonArray(jSONArray);
            int i2 = 1;
            int i3 = 1;
            for (int i4 = 0; i4 < this.mChildrenList.size(); i4++) {
                int i5 = 2;
                if (i4 > 2) {
                    return;
                }
                JSONObject jSONObject = new JSONObject(this.mChildrenList.get(i4));
                if (i4 == 0) {
                    this.firstTextId = jSONObject.getString("id");
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i5 = 0;
                    } else {
                        if (!jSONObject.getBoolean("collapsed")) {
                            i5 = 1;
                        }
                        this.mFirstIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    setFirstTextContent(jSONObject.getString(Todo.LABEL), i5);
                } else if (i4 == 1) {
                    this.secTextId = jSONObject.getString("id");
                    this.secTextParentId = jSONObject.getString(MiCloudConstants.PDC.J_PARENT_ID);
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i = 0;
                    } else {
                        i = jSONObject.getBoolean("collapsed") ? 2 : 1;
                        this.mSecIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    if (this.secTextParentId.equals(this.firstTextId)) {
                        i2 = 2;
                    }
                    setSecTextContent(jSONObject.getString(Todo.LABEL), i, i2);
                } else if (i4 == 2) {
                    this.thirdTextId = jSONObject.getString("id");
                    String string = jSONObject.getString(MiCloudConstants.PDC.J_PARENT_ID);
                    this.thirdTextParentId = string;
                    if (string.equals(this.firstTextId)) {
                        i3 = 2;
                    }
                    if (this.thirdTextParentId.equals(this.secTextId)) {
                        i3 = i2 + 1;
                    }
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i5 = 0;
                    } else {
                        if (!jSONObject.getBoolean("collapsed")) {
                            i5 = 1;
                        }
                        this.mThirdIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    setThirdTextContent(jSONObject.getString(Todo.LABEL), i5, i3);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJsonArray(JSONArray jSONArray) {
        if (jSONArray.length() == 0) {
            return;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (this.mChildrenList.size() > 2) {
                    return;
                }
                this.mChildrenList.add(jSONObject.toString());
                JSONArray jSONArray2 = jSONObject.getJSONArray("children");
                if (!jSONObject.getBoolean("collapsed")) {
                    parseJsonArray(jSONArray2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void setFirstTextContent(String str, int i) {
        this.mFirstLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.pc_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mFirstTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mFirstTextView.getLayoutParams();
        layoutParams.leftMargin = this.paddingLeft;
        Bitmap bitmap = this.mDotBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            createDotBitmap();
        }
        this.mFirstLineDrawablePadding = this.mDotBitmap.getWidth() * 0.5f;
        this.mFirstTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mFirstTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pad_note_mind_thumbnail_text_color)), 0, spannableStringBuilder.length(), 17);
        this.mFirstTextView.setMindText(spannableStringBuilder, sSearchContent);
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    public void setSecTextContent(String str, int i, int i2) {
        this.mSecLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.pc_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mSecTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mSecTextView.getLayoutParams();
        int i3 = i2 - 1;
        layoutParams.leftMargin = (this.paragraphPaddingLeft * i3) + this.paddingLeft;
        this.mSecExtraLineHeight = 7;
        Bitmap bitmap = this.mDotBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            createDotBitmap();
        }
        this.mSecLineDrawablePadding = (i3 * this.paragraphPaddingLeft) + (this.mDotBitmap.getWidth() * 0.5f);
        this.mSecTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mSecTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pad_note_mind_thumbnail_text_color)), 0, spannableStringBuilder.length(), 17);
        this.mSecTextView.setMindText(spannableStringBuilder, sSearchContent);
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    public void setThirdTextContent(String str, int i, int i2) {
        this.mThirdLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.pc_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mThirdTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mThirdTextView.getLayoutParams();
        Bitmap bitmap = this.mDotBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            createDotBitmap();
        }
        int i3 = i2 - 1;
        this.mThirdLineDrawablePadding = (this.paragraphPaddingLeft * i3) + (this.mDotBitmap.getWidth() * 0.5f);
        layoutParams.leftMargin = (i3 * this.paragraphPaddingLeft) + this.paddingLeft;
        this.mThirdExtraLineHeight = this.mSecExtraLineHeight + 5;
        this.mThirdTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mThirdTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pad_note_mind_thumbnail_text_color)), 0, spannableStringBuilder.length(), 17);
        this.mThirdTextView.setMindText(spannableStringBuilder, sSearchContent);
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        super.onDraw(canvas);
        Bitmap bitmap4 = this.mDotBitmap;
        if (bitmap4 == null || bitmap4.isRecycled()) {
            createDotBitmap();
        }
        Bitmap bitmap5 = this.mReverseTriangleBitmap;
        if (bitmap5 == null || bitmap5.isRecycled()) {
            createReverseTriangleBitmap();
        }
        Bitmap bitmap6 = this.mRightTriangleBitmap;
        if (bitmap6 == null || bitmap6.isRecycled()) {
            createRightTriangleBitmap();
        }
        CustomMindTextView customMindTextView = this.mFirstTextView;
        if (customMindTextView != null) {
            int i = this.mFirstLineSpreadType;
            if (i == 0) {
                bitmap3 = this.mDotBitmap;
            } else if (i == 1) {
                Bitmap bitmap7 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mFirstLineDrawablePadding - 1.0f, customMindTextView.getLineHeight(), this.mFirstLineDrawablePadding + 1.0f, this.mFirstIndicatorLineY + this.mFirstTextView.getLineHeight(), this.mLinePaint);
                bitmap3 = bitmap7;
            } else {
                bitmap3 = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap3, this.mFirstLineDrawablePadding - (bitmap3.getWidth() * 0.5f), (this.mFirstTextView.getLineHeight() * 0.5f) - (bitmap3.getHeight() * 0.5f), this.mPaint);
        }
        CustomMindTextView customMindTextView2 = this.mSecTextView;
        if (customMindTextView2 != null) {
            int i2 = this.mSecLineSpreadType;
            if (i2 == 0) {
                bitmap2 = this.mDotBitmap;
            } else if (i2 == 1) {
                Bitmap bitmap8 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mSecLineDrawablePadding - 1.0f, customMindTextView2.getLineHeight() + this.mFirstTextHeight, this.mSecLineDrawablePadding + 1.0f, this.mSecTextView.getLineHeight() + this.mFirstTextHeight + this.mSecIndicatorLineY, this.mLinePaint);
                bitmap2 = bitmap8;
            } else {
                bitmap2 = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap2, this.mSecLineDrawablePadding - (bitmap2.getWidth() * 0.5f), ((this.mSecTextView.getLineHeight() * 0.5f) - (bitmap2.getHeight() * 0.5f)) + this.mFirstTextHeight, this.mPaint);
        }
        CustomMindTextView customMindTextView3 = this.mThirdTextView;
        if (customMindTextView3 != null && customMindTextView3.getVisibility() != 8) {
            int i3 = this.mThirdLineSpreadType;
            if (i3 == 0) {
                bitmap = this.mDotBitmap;
            } else if (i3 == 1) {
                Bitmap bitmap9 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mThirdLineDrawablePadding - 1.0f, this.mThirdTextView.getLineHeight() + this.mFirstTextHeight + this.mSecTextHeight + this.mThirdExtraLineHeight, this.mThirdLineDrawablePadding + 1.0f, this.mThirdTextView.getLineHeight() + this.mFirstTextHeight + this.mSecTextHeight + this.mThirdIndicatorLineY, this.mLinePaint);
                bitmap = bitmap9;
            } else {
                bitmap = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap, this.mThirdLineDrawablePadding - (bitmap.getWidth() * 0.5f), ((this.mThirdTextView.getLineHeight() * 0.5f) - (bitmap.getHeight() * 0.5f)) + this.mFirstTextHeight + this.mSecTextHeight, this.mPaint);
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
        UIUtils.releaseBitmap(this.mReverseTriangleBitmap);
        UIUtils.releaseBitmap(this.mRightTriangleBitmap);
    }
}
