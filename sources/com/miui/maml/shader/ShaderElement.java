package com.miui.maml.shader;

import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.util.ColorParser;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ShaderElement {
    private static final String LOG_TAG = "ShaderElement";
    protected ScreenElementRoot mRoot;
    protected Shader mShader;
    protected Shader.TileMode mTileMode;
    protected float mX;
    protected Expression mXExp;
    protected float mY;
    protected Expression mYExp;
    protected Matrix mShaderMatrix = new Matrix();
    protected GradientStops mGradientStops = new GradientStops();

    public abstract void onGradientStopsChanged();

    public abstract boolean updateShaderMatrix();

    protected final class GradientStop {
        public static final String TAG_NAME = "GradientStop";
        private ColorParser mColorParser;
        private Expression mPositionExp;

        public GradientStop(Element element, ScreenElementRoot screenElementRoot) {
            this.mColorParser = ColorParser.fromElement(ShaderElement.this.mRoot.getVariables(), element);
            Expression expressionBuild = Expression.build(ShaderElement.this.mRoot.getVariables(), element.getAttribute("position"));
            this.mPositionExp = expressionBuild;
            if (expressionBuild == null) {
                Log.e(TAG_NAME, "lost position attribute.");
            }
        }

        public int getColor() {
            return this.mColorParser.getColor();
        }

        public float getPosition() {
            return (float) this.mPositionExp.evaluate();
        }
    }

    protected final class GradientStops {
        private int[] mColors;
        protected ArrayList<GradientStop> mGradientStopArr = new ArrayList<>();
        private float[] mPositions;

        protected GradientStops() {
        }

        public void init() {
            this.mColors = new int[size()];
            this.mPositions = new float[size()];
        }

        public int[] getColors() {
            return this.mColors;
        }

        public float[] getPositions() {
            return this.mPositions;
        }

        public void update() {
            boolean z = false;
            for (int i = 0; i < size(); i++) {
                int color = this.mGradientStopArr.get(i).getColor();
                int[] iArr = this.mColors;
                if (color != iArr[i]) {
                    z = true;
                }
                iArr[i] = color;
                float position = this.mGradientStopArr.get(i).getPosition();
                float[] fArr = this.mPositions;
                if (position != fArr[i]) {
                    z = true;
                }
                fArr[i] = position;
            }
            if (z) {
                ShaderElement.this.onGradientStopsChanged();
            }
        }

        public void add(GradientStop gradientStop) {
            this.mGradientStopArr.add(gradientStop);
        }

        public int size() {
            return this.mGradientStopArr.size();
        }
    }

    public ShaderElement(Element element, ScreenElementRoot screenElementRoot) {
        this.mRoot = screenElementRoot;
        Variables variables = getVariables();
        this.mXExp = Expression.build(variables, element.getAttribute(AnimatedProperty.PROPERTY_NAME_X));
        this.mYExp = Expression.build(variables, element.getAttribute(AnimatedProperty.PROPERTY_NAME_Y));
        this.mTileMode = getTileMode(element.getAttribute("tile"));
        if (element.getTagName().equalsIgnoreCase(BitmapShaderElement.TAG_NAME)) {
            return;
        }
        loadGradientStops(element, screenElementRoot);
    }

    protected Variables getVariables() {
        return this.mRoot.getVariables();
    }

    public static Shader.TileMode getTileMode(String str) {
        if (TextUtils.isEmpty(str)) {
            return Shader.TileMode.CLAMP;
        }
        if (str.equalsIgnoreCase("mirror")) {
            return Shader.TileMode.MIRROR;
        }
        if (str.equalsIgnoreCase("repeat")) {
            return Shader.TileMode.REPEAT;
        }
        return Shader.TileMode.CLAMP;
    }

    private void loadGradientStops(Element element, ScreenElementRoot screenElementRoot) {
        NodeList elementsByTagName = element.getElementsByTagName(GradientStop.TAG_NAME);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            this.mGradientStops.add(new GradientStop((Element) elementsByTagName.item(i), screenElementRoot));
        }
        if (this.mGradientStops.size() <= 0) {
            Log.e(LOG_TAG, "lost gradient stop.");
        } else {
            this.mGradientStops.init();
        }
    }

    public float getX() {
        Expression expression = this.mXExp;
        return (float) ((expression != null ? expression.evaluate() : 0.0d) * ((double) this.mRoot.getScale()));
    }

    public float getY() {
        Expression expression = this.mYExp;
        return (float) ((expression != null ? expression.evaluate() : 0.0d) * ((double) this.mRoot.getScale()));
    }

    public void updateShader() {
        this.mGradientStops.update();
        if (updateShaderMatrix()) {
            this.mShader.setLocalMatrix(this.mShaderMatrix);
        }
    }

    public Shader getShader() {
        return this.mShader;
    }
}
