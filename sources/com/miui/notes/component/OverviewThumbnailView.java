package com.miui.notes.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import com.miui.todo.data.Todo;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OverviewThumbnailView extends LinearLayout {
    private static final String TAG = "OverviewThumbnailView";
    private static ArrayList<String> sSearchContent;
    private String firstTextId;
    private String fourthTextId;
    private String fourthTextParentId;
    private boolean isFirstTextDoubleLine;
    private boolean isSecTextDoubleLine;
    private boolean isThirdTextDoubleLine;
    private ArrayList<String> mChildrenList;
    private Bitmap mDotBitmap;
    private float mFirstIndicatorLineY;
    private float mFirstIndicatorVerticalCount;
    private float mFirstLineDrawablePadding;
    private int mFirstLineSpreadType;
    private int mFirstTextHeight;
    public CustomMindTextView mFirstTextView;
    private int mFourthExtraLineHeight;
    private float mFourthLineDrawablePadding;
    private int mFourthLineSpreadType;
    private int mFourthTextHeight;
    public CustomMindTextView mFourthTextView;
    private int mGridItemSpace;
    private int mLineMarginTop;
    private Paint mLinePaint;
    private int mMinTextHeight;
    private Paint mPaint;
    private int mRemainingLine;
    private Bitmap mReverseTriangleBitmap;
    private Bitmap mRightTriangleBitmap;
    private int mRvPadding;
    private int mSecExtraLineHeight;
    private float mSecIndicatorLineY;
    private float mSecIndicatorVerticalCount;
    private float mSecLineDrawablePadding;
    private int mSecLineSpreadType;
    private int mSecTextHeight;
    public CustomMindTextView mSecTextView;
    private int mTextCount;
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

    public OverviewThumbnailView(Context context) {
        this(context, null);
    }

    public OverviewThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OverviewThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRemainingLine = 4;
        this.mTextCount = 4;
        this.isFirstTextDoubleLine = false;
        this.isSecTextDoubleLine = false;
        this.isThirdTextDoubleLine = false;
        this.mLineMarginTop = 0;
        this.mChildrenList = new ArrayList<>();
        this.firstTextId = "";
        this.secTextId = "";
        this.secTextParentId = "";
        this.thirdTextId = "";
        this.thirdTextParentId = "";
        this.fourthTextId = "";
        this.fourthTextParentId = "";
        this.paddingLeft = (int) getResources().getDimension(R.dimen.mind_note_item_overview_text_margin_start);
        this.paragraphPaddingLeft = (int) getResources().getDimension(R.dimen.mind_note_item_overview_text_margin_start);
        this.mMinTextHeight = (int) getResources().getDimension(R.dimen.mind_note_preview_min_line_height);
        if (RomUtils.isFoldDevice() && !UIUtils.isInNarrowScreen(context)) {
            this.mGridItemSpace = getResources().getDimensionPixelSize(R.dimen.common_grid_item_three_column_space);
            this.mRvPadding = getResources().getDimensionPixelOffset(R.dimen.common_three_column_recycler_horizontal_padding);
            this.mTextUpperLength = (((DisplayUtils.getRealWidth(getContext()) - (this.mRvPadding * 2)) - (this.mGridItemSpace * 3)) / 3) - (((int) getResources().getDimension(R.dimen.v12_item_grid_padding_start)) * 2);
            Logger.INSTANCE.d(TAG, "mTextUpperLength:" + this.mTextUpperLength);
        } else {
            this.mGridItemSpace = getResources().getDimensionPixelSize(R.dimen.common_grid_item_two_column_space);
            this.mRvPadding = getResources().getDimensionPixelOffset(R.dimen.common_two_column_recycler_horizontal_padding);
            this.mTextUpperLength = (((DisplayUtils.getRealWidth(getContext()) - (this.mRvPadding * 2)) - (this.mGridItemSpace * 2)) / 2) - (((int) getResources().getDimension(R.dimen.v12_item_grid_padding_start)) * 2);
        }
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
        this.mDotBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_note_overview_thumbnail_dot));
    }

    private void createReverseTriangleBitmap() {
        this.mReverseTriangleBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_note_overview_thumbnail_nabla));
    }

    private void createRightTriangleBitmap() {
        this.mRightTriangleBitmap = UIUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_note_overview_thumbnail_right_triangle));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        CustomMindTextView customMindTextView;
        super.onLayout(z, i, i2, i3, i4);
        this.mFirstTextHeight = 0;
        this.mSecTextHeight = 0;
        this.mThirdTextHeight = 0;
        this.mFourthTextHeight = 0;
        this.mFirstIndicatorLineY = 0.0f;
        this.mSecIndicatorLineY = 0.0f;
        this.mThirdIndicatorLineY = 0.0f;
        if (this.mThirdLineSpreadType == 2 && this.mThirdTextView != null && this.fourthTextParentId.equals(this.thirdTextId) && (customMindTextView = this.mFourthTextView) != null) {
            removeView(customMindTextView);
            this.mFourthTextView = null;
        }
        if (this.mSecLineSpreadType == 2 && this.mSecTextView != null && this.thirdTextParentId.equals(this.secTextId)) {
            removeView(this.mThirdTextView);
            this.mThirdTextView = null;
        }
        if (this.mFirstLineSpreadType == 2 && this.mFirstTextView != null && this.secTextParentId.equals(this.firstTextId)) {
            removeView(this.mSecTextView);
            this.mSecTextView = null;
        }
        CustomMindTextView customMindTextView2 = this.mFirstTextView;
        if (customMindTextView2 != null) {
            this.mFirstTextHeight = customMindTextView2.getMeasuredHeight();
        }
        CustomMindTextView customMindTextView3 = this.mSecTextView;
        if (customMindTextView3 != null) {
            this.mSecTextHeight = customMindTextView3.getMeasuredHeight();
        }
        CustomMindTextView customMindTextView4 = this.mThirdTextView;
        if (customMindTextView4 != null) {
            this.mThirdTextHeight = customMindTextView4.getMeasuredHeight();
        }
        CustomMindTextView customMindTextView5 = this.mFourthTextView;
        if (customMindTextView5 != null) {
            this.mFourthTextHeight = customMindTextView5.getMeasuredHeight();
        }
        if (this.mThirdLineSpreadType == 1) {
            if (this.mThirdIndicatorVerticalCount >= 1.0f) {
                this.mThirdIndicatorLineY = this.mFourthTextHeight;
            }
            if (this.isThirdTextDoubleLine && !this.isFirstTextDoubleLine && !this.isSecTextDoubleLine) {
                this.mThirdIndicatorLineY += this.mThirdTextHeight * 0.5f;
            }
        }
        if (this.mSecLineSpreadType == 1) {
            float f = this.mSecIndicatorVerticalCount;
            if (f >= 2.0f) {
                this.mSecIndicatorLineY = this.mThirdTextHeight + this.mFourthTextHeight;
            } else if (f == 1.0f) {
                this.mSecIndicatorLineY = this.mThirdTextHeight + this.mThirdIndicatorLineY;
            }
            if (this.isSecTextDoubleLine) {
                this.mSecIndicatorLineY += this.mSecTextHeight * 0.5f;
            }
        }
        if (this.mFirstLineSpreadType == 1) {
            float f2 = this.mFirstIndicatorVerticalCount;
            if (f2 >= 3.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mThirdTextHeight + this.mFourthTextHeight;
            } else if (f2 == 2.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mSecIndicatorLineY + this.mThirdTextHeight + this.mThirdIndicatorLineY;
            } else if (f2 == 1.0f) {
                this.mFirstIndicatorLineY = this.mSecTextHeight + this.mSecIndicatorLineY;
            }
            if (this.isFirstTextDoubleLine) {
                this.mFirstIndicatorLineY += this.mFirstTextHeight * 0.5f;
            }
            if (this.isSecTextDoubleLine && this.mThirdTextView == null) {
                this.mFirstIndicatorLineY += this.mSecTextHeight * 0.5f;
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

    public void setContent(String str, ArrayList<String> arrayList) {
        int i;
        sSearchContent = arrayList;
        this.isFirstTextDoubleLine = false;
        this.isSecTextDoubleLine = false;
        this.isThirdTextDoubleLine = false;
        this.mRemainingLine = 4;
        this.mTextCount = 4;
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONObject("data").getJSONArray("children");
            this.mChildrenList.clear();
            this.mFirstIndicatorVerticalCount = 0.0f;
            this.mSecIndicatorVerticalCount = 0.0f;
            this.mThirdIndicatorVerticalCount = 0.0f;
            parseJsonArray(jSONArray);
            int i2 = 1;
            int i3 = 1;
            int i4 = 1;
            for (int i5 = 0; i5 < this.mChildrenList.size() && i5 <= 3; i5++) {
                JSONObject jSONObject = new JSONObject(this.mChildrenList.get(i5));
                int i6 = 2;
                if (i5 == 0) {
                    this.firstTextId = jSONObject.getString("id");
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i6 = 0;
                    } else {
                        if (!jSONObject.getBoolean("collapsed")) {
                            i6 = 1;
                        }
                        this.mFirstIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    setFirstTextContent(Utils.trimStringEnd(jSONObject.getString(Todo.LABEL)), i6, false);
                } else if (i5 == 1) {
                    this.secTextId = jSONObject.getString("id");
                    this.secTextParentId = jSONObject.getString(MiCloudConstants.PDC.J_PARENT_ID);
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i = 0;
                    } else {
                        i = jSONObject.getBoolean("collapsed") ? 2 : 1;
                        this.mSecIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    if (this.secTextParentId.equals(this.firstTextId)) {
                        i3 = 2;
                    }
                    setSecTextContent(Utils.trimStringEnd(jSONObject.getString(Todo.LABEL)), i, i3, false);
                } else if (i5 == 2) {
                    this.thirdTextId = jSONObject.getString("id");
                    String string = jSONObject.getString(MiCloudConstants.PDC.J_PARENT_ID);
                    this.thirdTextParentId = string;
                    if (string.equals(this.firstTextId)) {
                        i2 = 2;
                    }
                    if (this.thirdTextParentId.equals(this.secTextId)) {
                        i2 = i3 + 1;
                    }
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i6 = 0;
                    } else {
                        if (!jSONObject.getBoolean("collapsed")) {
                            i6 = 1;
                        }
                        this.mThirdIndicatorVerticalCount = jSONObject.getJSONArray("children").length();
                    }
                    setThirdTextContent(Utils.trimStringEnd(jSONObject.getString(Todo.LABEL)), i6, i2, false);
                } else if (i5 == 3) {
                    this.fourthTextId = jSONObject.getString("id");
                    String string2 = jSONObject.getString(MiCloudConstants.PDC.J_PARENT_ID);
                    this.fourthTextParentId = string2;
                    if (string2.equals(this.thirdTextId)) {
                        i4 = i2 + 1;
                    }
                    if (this.fourthTextParentId.equals(this.secTextId)) {
                        i4 = i3 + 1;
                    } else if (this.fourthTextParentId.equals(this.firstTextId)) {
                        i4 = 2;
                    }
                    if (jSONObject.getJSONArray("children").length() == 0) {
                        i6 = 0;
                    } else if (!jSONObject.getBoolean("collapsed")) {
                        i6 = 1;
                    }
                    setFourthTextContent(Utils.trimStringEnd(jSONObject.getString(Todo.LABEL)), i6, i4);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setExcerptContent(String str) {
        int i;
        this.isFirstTextDoubleLine = false;
        this.isSecTextDoubleLine = false;
        this.isThirdTextDoubleLine = false;
        this.mRemainingLine = 4;
        this.mTextCount = 4;
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONObject("data").getJSONArray("children");
            this.mChildrenList.clear();
            this.mFirstIndicatorVerticalCount = 0.0f;
            this.mSecIndicatorVerticalCount = 0.0f;
            this.mThirdIndicatorVerticalCount = 0.0f;
            parseExcerptJsonArray(jSONArray);
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
                    setFirstTextContent(jSONObject.getString(Todo.LABEL), i5, true);
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
                    setSecTextContent(jSONObject.getString(Todo.LABEL), i, i2, true);
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
                    setThirdTextContent(jSONObject.getString(Todo.LABEL), i5, i3, true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    public void setFirstTextContent(String str, int i, boolean z) {
        PreviewTextView.PreviewTextSpan previewTextSpan;
        checkThemeIsNull();
        this.mFirstLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_overview_thumbnail_textview, (ViewGroup) null);
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
        if (z) {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), R.style.V12_DefaultTheme_TextAppearance_Grid_Secondary_Excerpt_DayNight);
        } else {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mFirstTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mFirstTextView.setMindText(spannableStringBuilder, sSearchContent);
        TextPaint paint = this.mFirstTextView.getPaint();
        paint.setTextSize(this.mFirstTextView.getTextSize());
        if (((int) paint.measureText(str)) > this.mTextUpperLength - this.paddingLeft) {
            this.mTextCount--;
            this.isFirstTextDoubleLine = true;
            this.mRemainingLine -= 2;
        } else {
            this.isFirstTextDoubleLine = false;
            this.mRemainingLine--;
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    public void setSecTextContent(String str, int i, int i2, boolean z) {
        PreviewTextView.PreviewTextSpan previewTextSpan;
        checkThemeIsNull();
        this.mSecLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_overview_thumbnail_textview, (ViewGroup) null);
        this.mSecTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mSecTextView.getLayoutParams();
        int i3 = i2 - 1;
        layoutParams.leftMargin = (this.paragraphPaddingLeft * i3) + this.paddingLeft;
        this.mSecExtraLineHeight = 5;
        Bitmap bitmap = this.mDotBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            createDotBitmap();
        }
        this.mSecLineDrawablePadding = (i3 * this.paragraphPaddingLeft) + (this.mDotBitmap.getWidth() * 0.5f);
        this.mSecTextView.setParams(layoutParams);
        if (z) {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), R.style.V12_DefaultTheme_TextAppearance_Grid_Secondary_Excerpt_DayNight);
        } else {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mSecTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mSecTextView.setMindText(spannableStringBuilder, sSearchContent);
        TextPaint paint = this.mSecTextView.getPaint();
        paint.setTextSize(this.mSecTextView.getTextSize());
        if (((int) paint.measureText(str)) > this.mTextUpperLength - layoutParams.leftMargin) {
            this.mTextCount--;
            this.isSecTextDoubleLine = true;
            this.mRemainingLine -= 2;
        } else {
            this.isSecTextDoubleLine = false;
            this.mRemainingLine--;
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    public void setThirdTextContent(String str, int i, int i2, boolean z) {
        PreviewTextView.PreviewTextSpan previewTextSpan;
        checkThemeIsNull();
        this.mThirdLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_overview_thumbnail_textview, (ViewGroup) null);
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
        if (z) {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), R.style.V12_DefaultTheme_TextAppearance_Grid_Secondary_Excerpt_DayNight);
        } else {
            previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mThirdTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mThirdTextView.setMindText(spannableStringBuilder, sSearchContent);
        if (this.mTextCount < 3) {
            removeView(this.mThirdTextView);
            this.mThirdTextView.setVisibility(8);
            this.mThirdTextView = null;
        } else {
            TextPaint paint = this.mThirdTextView.getPaint();
            paint.setTextSize(this.mThirdTextView.getTextSize());
            if (((int) paint.measureText(this.mThirdTextView.getText().toString())) > this.mTextUpperLength - layoutParams.leftMargin) {
                this.mTextCount--;
                this.isThirdTextDoubleLine = true;
                if (this.mRemainingLine < 2) {
                    this.mThirdTextView.setLines(1);
                }
            } else {
                this.isThirdTextDoubleLine = false;
            }
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    public void setFourthTextContent(String str, int i, int i2) {
        checkThemeIsNull();
        this.mFourthLineSpreadType = i;
        CustomMindTextView customMindTextView = (CustomMindTextView) LayoutInflater.from(getContext()).inflate(R.layout.v12_note_overview_thumbnail_textview, (ViewGroup) null);
        this.mFourthTextView = customMindTextView;
        addView(customMindTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mFourthTextView.getLayoutParams();
        Bitmap bitmap = this.mDotBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            createDotBitmap();
        }
        int i3 = i2 - 1;
        this.mFourthLineDrawablePadding = (this.paragraphPaddingLeft * i3) + (this.mDotBitmap.getWidth() * 0.5f);
        layoutParams.leftMargin = (i3 * this.paragraphPaddingLeft) + this.paddingLeft;
        this.mFourthExtraLineHeight = this.mThirdExtraLineHeight + 5;
        this.mFourthTextView.setParams(layoutParams);
        PreviewTextView.PreviewTextSpan previewTextSpan = new PreviewTextView.PreviewTextSpan(getContext(), this.mTheme.getGridStyle(getContext()).mBodyStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        previewTextSpan.updateMeasureState(this.mFourthTextView.getPaint());
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mFourthTextView.setMindText(spannableStringBuilder, sSearchContent);
        if (this.mTextCount < 4) {
            removeView(this.mFourthTextView);
            this.mFourthTextView.setVisibility(8);
            this.mFourthTextView = null;
        } else {
            this.mFourthTextView.setLines(1);
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        Bitmap bitmap4;
        super.onDraw(canvas);
        Bitmap bitmap5 = this.mDotBitmap;
        if (bitmap5 == null || bitmap5.isRecycled()) {
            createDotBitmap();
        }
        Bitmap bitmap6 = this.mReverseTriangleBitmap;
        if (bitmap6 == null || bitmap6.isRecycled()) {
            createReverseTriangleBitmap();
        }
        Bitmap bitmap7 = this.mRightTriangleBitmap;
        if (bitmap7 == null || bitmap7.isRecycled()) {
            createRightTriangleBitmap();
        }
        CustomMindTextView customMindTextView = this.mFirstTextView;
        if (customMindTextView != null) {
            int i = this.mFirstLineSpreadType;
            if (i == 0) {
                bitmap4 = this.mDotBitmap;
            } else if (i == 1) {
                Bitmap bitmap8 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mFirstLineDrawablePadding - 1.0f, (customMindTextView.getLineHeight() * 0.5f) + 32.0f, this.mFirstLineDrawablePadding + 1.0f, this.mFirstIndicatorLineY + (this.mFirstTextView.getLineHeight() * 0.5f) + 32.0f, this.mLinePaint);
                bitmap4 = bitmap8;
            } else {
                bitmap4 = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap4, this.mFirstLineDrawablePadding - (bitmap4.getWidth() * 0.5f), (this.mFirstTextView.getY() + (this.mMinTextHeight * 0.5f)) - (bitmap4.getHeight() * 0.5f), this.mPaint);
        }
        CustomMindTextView customMindTextView2 = this.mSecTextView;
        if (customMindTextView2 != null) {
            int i2 = this.mSecLineSpreadType;
            if (i2 == 0) {
                bitmap3 = this.mDotBitmap;
            } else if (i2 == 1) {
                Bitmap bitmap9 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mSecLineDrawablePadding - 1.0f, (customMindTextView2.getLineHeight() * 0.5f) + 32.0f + this.mFirstTextHeight + this.mSecExtraLineHeight, this.mSecLineDrawablePadding + 1.0f, (this.mSecTextView.getLineHeight() * 0.5f) + 32.0f + this.mFirstTextHeight + this.mSecIndicatorLineY, this.mLinePaint);
                bitmap3 = bitmap9;
            } else {
                bitmap3 = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap3, this.mSecLineDrawablePadding - (bitmap3.getWidth() * 0.5f), (this.mSecTextView.getY() + (this.mMinTextHeight * 0.5f)) - (bitmap3.getHeight() * 0.5f), this.mPaint);
        }
        CustomMindTextView customMindTextView3 = this.mThirdTextView;
        if (customMindTextView3 != null && customMindTextView3.getVisibility() != 8) {
            int i3 = this.mThirdLineSpreadType;
            if (i3 == 0) {
                bitmap2 = this.mDotBitmap;
            } else if (i3 == 1) {
                Bitmap bitmap10 = this.mReverseTriangleBitmap;
                canvas.drawRect(this.mThirdLineDrawablePadding - 1.0f, (this.mThirdTextView.getLineHeight() * 0.5f) + 32.0f + this.mFirstTextHeight + this.mSecTextHeight + this.mThirdExtraLineHeight, this.mThirdLineDrawablePadding + 1.0f, (this.mThirdTextView.getLineHeight() * 0.5f) + 32.0f + this.mFirstTextHeight + this.mSecTextHeight + this.mThirdIndicatorLineY, this.mLinePaint);
                bitmap2 = bitmap10;
            } else {
                bitmap2 = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap2, this.mThirdLineDrawablePadding - (bitmap2.getWidth() * 0.5f), (this.mThirdTextView.getY() + (this.mMinTextHeight * 0.5f)) - (bitmap2.getHeight() * 0.5f), this.mPaint);
        }
        if (this.mFourthTextView != null) {
            int i4 = this.mFourthLineSpreadType;
            if (i4 == 0) {
                bitmap = this.mDotBitmap;
            } else if (i4 == 1) {
                bitmap = this.mReverseTriangleBitmap;
            } else {
                bitmap = this.mRightTriangleBitmap;
            }
            canvas.drawBitmap(bitmap, this.mFourthLineDrawablePadding - (bitmap.getWidth() * 0.5f), (this.mFourthTextView.getY() + (this.mMinTextHeight * 0.5f)) - (bitmap.getHeight() * 0.5f), this.mPaint);
        }
        UIUtils.releaseBitmap(this.mDotBitmap);
        UIUtils.releaseBitmap(this.mReverseTriangleBitmap);
        UIUtils.releaseBitmap(this.mRightTriangleBitmap);
    }

    private void checkThemeIsNull() {
        if (this.mTheme == null) {
            this.mTheme = ResourceManager.getTheme(0);
        }
    }
}
