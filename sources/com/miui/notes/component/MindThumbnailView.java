package com.miui.notes.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;
import com.miui.todo.data.Todo;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class MindThumbnailView extends LinearLayout {
    private static ArrayList<String> sSearchContent;
    private String firstTextId;
    private String fourthTextParentId;
    private ArrayList<String> mChildrenList;
    public CustomMindTextView mFirstTextView;
    public CustomMindTextView mFourthTextView;
    private Bitmap mHorizonLine;
    private Bitmap mLeftBottomCorner;
    private Bitmap mLeftTopCorner;
    private float mLineDrawablePadding;
    private Paint mLinePaint;
    private int mMinTextHeight;
    private Paint mPaint;
    public CustomMindTextView mSecTextView;
    private Theme mTheme;
    public CustomMindTextView mThirdTextView;
    private int paddingLeft;
    private int paragraphPaddingLeft;
    private String secTextId;
    private String secTextParentId;
    private String thirdTextId;
    private String thirdTextParentId;

    public MindThumbnailView(Context context) {
        this(context, null);
    }

    public MindThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MindThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildrenList = new ArrayList<>();
        this.firstTextId = "";
        this.secTextId = "";
        this.secTextParentId = "";
        this.thirdTextId = "";
        this.thirdTextParentId = "";
        this.fourthTextParentId = "";
        this.paddingLeft = (int) getResources().getDimension(R.dimen.mind_note_item_mapview_text_margin_start);
        this.paragraphPaddingLeft = (int) getResources().getDimension(R.dimen.mind_note_item_mapview_text_margin_start);
        this.mMinTextHeight = (int) getResources().getDimension(R.dimen.mind_note_preview_min_line_height);
        this.mLeftTopCorner = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.v12_mind_thumbnail_left_top_corner));
        this.mLeftBottomCorner = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.v12_mind_thumbnail_left_bottom_corner));
        this.mHorizonLine = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.v12_mind_thumbnail_horizon_line));
        this.mPaint = new Paint();
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setColor(getResources().getColor(R.color.note_mind_thumbnail_line_color));
        setWillNotDraw(false);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setStyle(Theme theme) {
        this.mTheme = theme;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int iMax = (int) Math.max(0.0f, (getHeight() - calculateGraphicsHeight()) * 0.5f);
        if (iMax > 0) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(childAt.getLeft(), childAt.getTop() + iMax, childAt.getRight(), childAt.getBottom() + iMax);
            }
        }
    }

    public void resetAllViews() {
        this.mFirstTextView = null;
        this.mSecTextView = null;
        this.mThirdTextView = null;
        this.mFourthTextView = null;
    }

    public void setContent(String str, ArrayList<String> arrayList) {
        sSearchContent = arrayList;
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONObject("data").getJSONArray("children");
            this.mChildrenList.clear();
            parseJsonArray(jSONArray);
            for (int i = 0; i < this.mChildrenList.size() && i <= 3; i++) {
                JSONObject jSONObject = new JSONObject(this.mChildrenList.get(i));
                if (i == 0) {
                    setFirstTextContent(jSONObject.getString(Todo.LABEL));
                } else if (i == 1) {
                    setSecTextContent(jSONObject.getString(Todo.LABEL));
                } else if (i == 2) {
                    setThirdTextContent(jSONObject.getString(Todo.LABEL));
                } else if (i == 3) {
                    setFourthTextContent(jSONObject.getString(Todo.LABEL));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setExcerptContent(String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONObject("data").getJSONArray("children");
            this.mChildrenList.clear();
            parseExcerptJsonArray(jSONArray);
            for (int i = 0; i < this.mChildrenList.size() && i <= 2; i++) {
                JSONObject jSONObject = new JSONObject(this.mChildrenList.get(i));
                if (i == 0) {
                    setFirstTextContent(jSONObject.getString(Todo.LABEL));
                } else if (i == 1) {
                    setSecTextContent(jSONObject.getString(Todo.LABEL));
                } else if (i == 2) {
                    setThirdTextContent(jSONObject.getString(Todo.LABEL));
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
                if (this.mChildrenList.size() > 3) {
                    return;
                }
                this.mChildrenList.add(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void parseExcerptJsonArray(JSONArray jSONArray) {
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
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void setFirstTextContent(String str) {
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mFirstTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mFirstTextView.getLayoutParams();
        layoutParams.leftMargin = this.paddingLeft;
        this.mLineDrawablePadding = 2.0f;
        this.mFirstTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mFirstTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mFirstTextView.setMindText(spannableStringBuilder, sSearchContent);
    }

    public void setSecTextContent(String str) {
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mSecTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mSecTextView.getLayoutParams();
        layoutParams.leftMargin = this.paddingLeft;
        this.mSecTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mSecTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mSecTextView.setMindText(spannableStringBuilder, sSearchContent);
    }

    public void setThirdTextContent(String str) {
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mThirdTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mThirdTextView.getLayoutParams();
        layoutParams.leftMargin = this.paddingLeft;
        this.mThirdTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mThirdTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mThirdTextView.setMindText(spannableStringBuilder, sSearchContent);
    }

    public void setFourthTextContent(String str) {
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_mind_thumbnail_textview, (ViewGroup) null);
        this.mFourthTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mFourthTextView.getLayoutParams();
        layoutParams.leftMargin = this.paddingLeft;
        this.mFourthTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mFourthTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mFourthTextView.setMindText(spannableStringBuilder, sSearchContent);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        float height;
        super.onDraw(canvas);
        if (this.mHorizonLine == null || this.mLeftTopCorner == null || this.mLeftBottomCorner == null) {
            return;
        }
        float fMax = Math.max(0.0f, (getHeight() - calculateGraphicsHeight()) * 0.5f);
        CustomMindTextView customMindTextView = this.mFirstTextView;
        if (customMindTextView != null && this.mSecTextView == null && this.mThirdTextView == null && this.mFourthTextView == null) {
            canvas.drawBitmap(this.mHorizonLine, this.mLineDrawablePadding, ((customMindTextView.getLineHeight() * 0.5f) + fMax) - 3.0f, this.mPaint);
            height = (((this.mMinTextHeight * 0.5f) + fMax) + this.mHorizonLine.getHeight()) - 3.0f;
        } else {
            height = fMax;
        }
        if (this.mFirstTextView != null && this.mSecTextView != null) {
            canvas.drawBitmap(this.mLeftTopCorner, this.mLineDrawablePadding, ((this.mMinTextHeight * 0.5f) + fMax) - 3.0f, this.mPaint);
            height = ((fMax + (this.mMinTextHeight * 0.5f)) + this.mLeftTopCorner.getHeight()) - 3.0f;
        }
        float f = height;
        if (this.mSecTextView != null) {
            CustomMindTextView customMindTextView2 = this.mThirdTextView;
            if (customMindTextView2 == null && this.mFourthTextView == null) {
                float f2 = this.mLineDrawablePadding;
                canvas.drawRect(f2, f, f2 + 3.0f, (this.mMinTextHeight * 0.6f) + f, this.mLinePaint);
                canvas.drawBitmap(this.mLeftBottomCorner, this.mLineDrawablePadding, f + (this.mMinTextHeight * 0.6f), this.mPaint);
                return;
            }
            if (customMindTextView2 != null) {
                if (this.mFourthTextView == null) {
                    float f3 = this.mLineDrawablePadding;
                    canvas.drawRect(f3, f, f3 + 3.0f, (this.mMinTextHeight * 0.8f) + f + this.mHorizonLine.getHeight(), this.mLinePaint);
                    float f4 = f + (this.mMinTextHeight * 0.8f);
                    canvas.drawBitmap(this.mHorizonLine, this.mLineDrawablePadding, f4, this.mPaint);
                    float height2 = f4 + this.mHorizonLine.getHeight();
                    float f5 = this.mLineDrawablePadding;
                    canvas.drawRect(f5, height2, f5 + 3.0f, (this.mMinTextHeight * 0.6f) + height2 + 9.0f, this.mLinePaint);
                    canvas.drawBitmap(this.mLeftBottomCorner, this.mLineDrawablePadding, height2 + (this.mMinTextHeight * 0.6f) + 9.0f, this.mPaint);
                    return;
                }
                float f6 = this.mLineDrawablePadding;
                canvas.drawRect(f6, f, f6 + 3.0f, (this.mMinTextHeight * 0.8f) + f + this.mHorizonLine.getHeight(), this.mLinePaint);
                float f7 = f + (this.mMinTextHeight * 0.8f);
                canvas.drawBitmap(this.mHorizonLine, this.mLineDrawablePadding, f7, this.mPaint);
                float height3 = f7 + this.mHorizonLine.getHeight();
                float f8 = this.mLineDrawablePadding;
                canvas.drawRect(f8, height3, f8 + 3.0f, (this.mMinTextHeight * 0.8f) + height3 + 9.0f + this.mHorizonLine.getHeight(), this.mLinePaint);
                float f9 = height3 + (this.mMinTextHeight * 0.8f) + 9.0f;
                canvas.drawBitmap(this.mHorizonLine, this.mLineDrawablePadding, f9, this.mPaint);
                float height4 = f9 + this.mHorizonLine.getHeight();
                float f10 = this.mLineDrawablePadding;
                canvas.drawRect(f10, height4, f10 + 3.0f, (this.mMinTextHeight * 0.6f) + height4 + 6.0f + 9.0f, this.mLinePaint);
                canvas.drawBitmap(this.mLeftBottomCorner, this.mLineDrawablePadding, height4 + (this.mMinTextHeight * 0.6f) + 6.0f + 9.0f, this.mPaint);
            }
        }
    }

    private float calculateGraphicsHeight() {
        if (this.mFirstTextView == null) {
            return 0.0f;
        }
        if (this.mSecTextView == null) {
            return calcSingleNodeHeight();
        }
        return calcTopCornerHeight() + calcBranchHeight();
    }

    private float calcSingleNodeHeight() {
        Bitmap bitmap = this.mHorizonLine;
        if (bitmap == null) {
            return 0.0f;
        }
        return ((this.mMinTextHeight * 0.5f) + bitmap.getHeight()) - 3.0f;
    }

    private float calcTopCornerHeight() {
        Bitmap bitmap = this.mLeftTopCorner;
        if (bitmap == null) {
            return 0.0f;
        }
        return ((this.mMinTextHeight * 0.5f) + bitmap.getHeight()) - 3.0f;
    }

    private float calcBranchHeight() {
        float f;
        float f2;
        Bitmap bitmap = this.mHorizonLine;
        int height = bitmap != null ? bitmap.getHeight() : 0;
        Bitmap bitmap2 = this.mLeftBottomCorner;
        int height2 = bitmap2 != null ? bitmap2.getHeight() : 0;
        if (this.mThirdTextView == null) {
            return (this.mMinTextHeight * 0.6f) + height2;
        }
        if (this.mFourthTextView == null) {
            int i = this.mMinTextHeight;
            f = (i * 0.8f) + height;
            f2 = i * 0.6f;
        } else {
            int i2 = this.mMinTextHeight;
            float f3 = height;
            f = (i2 * 0.8f) + f3 + (i2 * 0.8f) + 9.0f + f3 + (i2 * 0.6f);
            f2 = 6.0f;
        }
        return f + f2 + 9.0f + height2;
    }
}
