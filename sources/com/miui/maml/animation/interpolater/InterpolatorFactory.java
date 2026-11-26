package com.miui.maml.animation.interpolater;

import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Interpolator;
import com.miui.maml.data.Expression;

/* JADX INFO: loaded from: classes3.dex */
public class InterpolatorFactory {
    public static final String LOG_TAG = "InterpolatorFactory";

    public static Interpolator create(String str, Expression[] expressionArr) {
        boolean z;
        float f;
        String strSubstring;
        String strSubstring2;
        float f2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        boolean z2 = false;
        boolean z3 = expressionArr != null && expressionArr.length > 0;
        int iIndexOf = str.indexOf(40);
        int iIndexOf2 = str.indexOf(41);
        float f3 = 0.0f;
        if (iIndexOf == -1 || iIndexOf2 == -1) {
            z = false;
            f = 0.0f;
        } else {
            String strSubstring3 = str.substring(iIndexOf + 1, iIndexOf2);
            int iIndexOf3 = strSubstring3.indexOf(",");
            if (iIndexOf3 == -1) {
                strSubstring = "";
                z = false;
                strSubstring2 = strSubstring3;
            } else {
                strSubstring2 = strSubstring3.substring(0, iIndexOf3);
                strSubstring = strSubstring3.substring(iIndexOf3 + 1);
                z = true;
            }
            try {
                f2 = Float.parseFloat(strSubstring2);
                if (z) {
                    try {
                        f3 = Float.parseFloat(strSubstring);
                    } catch (NumberFormatException unused) {
                        Log.d(LOG_TAG, "parse error:" + strSubstring3);
                    }
                }
            } catch (NumberFormatException unused2) {
                f2 = 0.0f;
            }
            float f4 = f3;
            f3 = f2;
            z2 = true;
            f = f4;
        }
        if ("BackEaseIn".equalsIgnoreCase(str)) {
            return new BackEaseInInterpolater(expressionArr);
        }
        if ("BackEaseOut".equalsIgnoreCase(str)) {
            return new BackEaseOutInterpolater(expressionArr);
        }
        if ("BackEaseInOut".equalsIgnoreCase(str)) {
            return new BackEaseInOutInterpolater(expressionArr);
        }
        if (str.startsWith("BackEaseInOut")) {
            if (z3) {
                return new BackEaseInOutInterpolater(expressionArr);
            }
            if (z2) {
                return new BackEaseInOutInterpolater(f3);
            }
        } else if (str.startsWith("BackEaseIn")) {
            if (z3) {
                return new BackEaseInInterpolater(expressionArr);
            }
            if (z2) {
                return new BackEaseInInterpolater(f3);
            }
        } else if (str.startsWith("BackEaseOut")) {
            if (z3) {
                return new BackEaseOutInterpolater(expressionArr);
            }
            if (z2) {
                return new BackEaseOutInterpolater(f3);
            }
        } else {
            if ("BounceEaseIn".equalsIgnoreCase(str)) {
                return new BounceEaseInInterpolater();
            }
            if ("BounceEaseOut".equalsIgnoreCase(str)) {
                return new BounceEaseOutInterpolater();
            }
            if ("BounceEaseInOut".equalsIgnoreCase(str)) {
                return new BounceEaseInOutInterpolater();
            }
            if ("CircEaseIn".equalsIgnoreCase(str)) {
                return new CircEaseInInterpolater();
            }
            if ("CircEaseOut".equalsIgnoreCase(str)) {
                return new CircEaseOutInterpolater();
            }
            if ("CircEaseInOut".equalsIgnoreCase(str)) {
                return new CircEaseInOutInterpolater();
            }
            if ("CubicEaseIn".equalsIgnoreCase(str)) {
                return new CubicEaseInInterpolater();
            }
            if ("CubicEaseOut".equalsIgnoreCase(str)) {
                return new CubicEaseOutInterpolater();
            }
            if ("CubicEaseInOut".equalsIgnoreCase(str)) {
                return new CubicEaseInOutInterpolater();
            }
            if ("ElasticEaseIn".equalsIgnoreCase(str)) {
                return new ElasticEaseInInterpolater(expressionArr);
            }
            if ("ElasticEaseOut".equalsIgnoreCase(str)) {
                return new ElasticEaseOutInterpolater(expressionArr);
            }
            if ("ElasticEaseInOut".equalsIgnoreCase(str)) {
                return new ElasticEaseInOutInterpolater(expressionArr);
            }
            if (str.startsWith("ElasticEaseInOut")) {
                if (z3) {
                    return new ElasticEaseInOutInterpolater(expressionArr);
                }
                if (z) {
                    return new ElasticEaseInOutInterpolater(f3, f);
                }
            } else if (str.startsWith("ElasticEaseIn")) {
                if (z3) {
                    return new ElasticEaseInInterpolater(expressionArr);
                }
                if (z) {
                    return new ElasticEaseInInterpolater(f3, f);
                }
            } else if (str.startsWith("ElasticEaseOut")) {
                if (z3) {
                    return new ElasticEaseOutInterpolater(expressionArr);
                }
                if (z) {
                    return new ElasticEaseOutInterpolater(f3, f);
                }
            } else {
                if ("ExpoEaseIn".equalsIgnoreCase(str)) {
                    return new ExpoEaseInInterpolater();
                }
                if ("ExpoEaseOut".equalsIgnoreCase(str)) {
                    return new ExpoEaseOutInterpolater();
                }
                if ("ExpoEaseInOut".equalsIgnoreCase(str)) {
                    return new ExpoEaseInOutInterpolater();
                }
                if ("QuadEaseIn".equalsIgnoreCase(str)) {
                    return new QuadEaseInInterpolater();
                }
                if ("QuadEaseOut".equalsIgnoreCase(str)) {
                    return new QuadEaseOutInterpolater();
                }
                if ("QuadEaseInOut".equalsIgnoreCase(str)) {
                    return new QuadEaseInOutInterpolater();
                }
                if ("QuartEaseIn".equalsIgnoreCase(str)) {
                    return new QuartEaseInInterpolater();
                }
                if ("QuartEaseOut".equalsIgnoreCase(str)) {
                    return new QuartEaseOutInterpolater();
                }
                if ("QuartEaseInOut".equalsIgnoreCase(str)) {
                    return new QuartEaseInOutInterpolater();
                }
                if ("QuintEaseIn".equalsIgnoreCase(str)) {
                    return new QuintEaseInInterpolater();
                }
                if ("QuintEaseOut".equalsIgnoreCase(str)) {
                    return new QuintEaseOutInterpolater();
                }
                if ("QuintEaseInOut".equalsIgnoreCase(str)) {
                    return new QuintEaseInOutInterpolater();
                }
                if ("SineEaseIn".equalsIgnoreCase(str)) {
                    return new SineEaseInInterpolater();
                }
                if ("SineEaseOut".equalsIgnoreCase(str)) {
                    return new SineEaseOutInterpolater();
                }
                if ("SineEaseInOut".equalsIgnoreCase(str)) {
                    return new SineEaseInOutInterpolater();
                }
                if ("Linear".equalsIgnoreCase(str)) {
                    return new LinearInterpolater();
                }
                if ("PhysicBased".equalsIgnoreCase(str)) {
                    return new PhysicBasedInterpolator(expressionArr);
                }
                if (str.startsWith("PhysicBased")) {
                    if (z3) {
                        return new PhysicBasedInterpolator(expressionArr);
                    }
                    if (z) {
                        return new PhysicBasedInterpolator(f3, f);
                    }
                }
            }
        }
        return null;
    }
}
