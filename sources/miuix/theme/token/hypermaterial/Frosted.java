package miuix.theme.token.hypermaterial;

import com.xiaomi.verificationsdk.internal.Constants;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;

/* JADX INFO: loaded from: classes6.dex */
public class Frosted {
    public static MaterialToken Pured_Extra_Thin_Light = new MaterialToken.Builder(30, "frosted-pured-extra-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Pured_Extra_Thin_Light).setElementBlur().build();
    public static MaterialToken Pured_Extra_Thin_Dark = new MaterialToken.Builder(10, "frosted-pured-extra-thin", "dark").setColorBlend(ColorBlendToken.Pured_Extra_Thin_Dark).setElementBlur().build();
    public static MaterialDayNightToken Pured_Extra_Thin = new MaterialDayNightToken(Pured_Extra_Thin_Light, Pured_Extra_Thin_Dark);
    public static MaterialToken Pured_Thin_Light = new MaterialToken.Builder(30, "frosted-pured-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Pured_Thin_Light).setElementBlur().build();
    public static MaterialToken Pured_Thin_Dark = new MaterialToken.Builder(30, "frosted-pured-thin", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Dark).setElementBlur().build();
    public static MaterialDayNightToken Pured_Thin = new MaterialDayNightToken(Pured_Thin_Light, Pured_Thin_Dark);
    public static MaterialToken Pured_Regular_Light = new MaterialToken.Builder(10, "frosted-pured-regular", Constants.LIGHT).setColorBlend(ColorBlendToken.Pured_Regular_Light).setElementBlur().build();
    public static MaterialToken Pured_Regular_Dark = new MaterialToken.Builder(10, "frosted-pured-regular", "dark").setColorBlend(ColorBlendToken.Pured_Regular_Dark).setElementBlur().build();
    public static MaterialDayNightToken Pured_Regular = new MaterialDayNightToken(Pured_Regular_Light, Pured_Regular_Dark);
    public static MaterialToken Pured_Thick_Light = new MaterialToken.Builder(10, "frosted-pured-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Pured_Thick_Light).setElementBlur().build();
    public static MaterialToken Pured_Thick_Dark = new MaterialToken.Builder(10, "frosted-pured-thick", "dark").setColorBlend(ColorBlendToken.Pured_Thick_Dark).setElementBlur().build();
    public static MaterialDayNightToken Pured_Thick = new MaterialDayNightToken(Pured_Thick_Light, Pured_Thick_Dark);
    public static MaterialToken Pured_Extra_Thick_Light = new MaterialToken.Builder(30, "frosted-pured-extra-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Pured_Extra_Thick_Light).setElementBlur().build();
    public static MaterialToken Pured_Extra_Thick_Dark = new MaterialToken.Builder(30, "frosted-pured-extra-thick", "dark").setColorBlend(ColorBlendToken.Pured_Extra_Thick_Dark).setElementBlur().build();
    public static MaterialDayNightToken Pured_Extra_Thick = new MaterialDayNightToken(Pured_Extra_Thick_Light, Pured_Extra_Thick_Dark);
    public static MaterialToken Colored_Extra_Thin_Light = new MaterialToken.Builder(10, "frosted-colored-extra-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Colored_Extra_Thin_Light).setElementBlur().build();
    public static MaterialToken Colored_Extra_Thin_Dark = new MaterialToken.Builder(10, "frosted-colored-extra-thin", "dark").setColorBlend(ColorBlendToken.Colored_Extra_Thin_Dark).setElementBlur().build();
    public static MaterialDayNightToken Colored_Extra_Thin = new MaterialDayNightToken(Colored_Extra_Thin_Light, Colored_Extra_Thin_Dark);
    public static MaterialToken Colored_Thin_Light = new MaterialToken.Builder(10, "frosted-colored-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Colored_Thin_Light).setElementBlur().build();
    public static MaterialToken Colored_Thin_Dark = new MaterialToken.Builder(30, "frosted-colored-thin", "dark").setColorBlend(ColorBlendToken.Colored_Thin_Dark).setElementBlur().build();
    public static MaterialDayNightToken Colored_Thin = new MaterialDayNightToken(Colored_Thin_Light, Colored_Thin_Dark);
    public static MaterialToken Colored_Regular_Light = new MaterialToken.Builder(30, "frosted-colored-regular", Constants.LIGHT).setColorBlend(ColorBlendToken.Colored_Regular_Light).setElementBlur().build();
    public static MaterialToken Colored_Regular_Dark = new MaterialToken.Builder(10, "frosted-colored-regular", "dark").setColorBlend(ColorBlendToken.Colored_Regular_Dark).setElementBlur().build();
    public static MaterialDayNightToken Colored_Regular = new MaterialDayNightToken(Colored_Regular_Light, Colored_Regular_Dark);
    public static MaterialToken Colored_Thick_Light = new MaterialToken.Builder(10, "frosted-colored-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Colored_Thick_Light).setElementBlur().build();
    public static MaterialToken Colored_Thick_Dark = new MaterialToken.Builder(10, "frosted-colored-thick", "dark").setColorBlend(ColorBlendToken.Colored_Thick_Dark).setElementBlur().build();
    public static MaterialDayNightToken Colored_Thick = new MaterialDayNightToken(Colored_Thick_Light, Colored_Thick_Dark);
    public static MaterialToken Colored_Extra_Thick_Light = new MaterialToken.Builder(30, "frosted-colored-extra-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Colored_Extra_Thick_Light).setElementBlur().build();
    public static MaterialToken Colored_Extra_Thick_Dark = new MaterialToken.Builder(30, "frosted-colored-extra-thick", "dark").setColorBlend(ColorBlendToken.Colored_Extra_Thick_Dark).setElementBlur().build();
    public static MaterialDayNightToken Colored_Extra_Thick = new MaterialDayNightToken(Colored_Extra_Thick_Light, Colored_Extra_Thick_Dark);

    public static MaterialToken Pured_Extra_Thin(boolean z) {
        return z ? Pured_Extra_Thin_Light : Pured_Extra_Thin_Dark;
    }

    public static MaterialToken Pured_Thin(boolean z) {
        return z ? Pured_Thin_Light : Pured_Thin_Dark;
    }

    public static MaterialToken Pured_Regular(boolean z) {
        return z ? Pured_Regular_Light : Pured_Regular_Dark;
    }

    public static MaterialToken Pured_Thick(boolean z) {
        return z ? Pured_Thick_Light : Pured_Thick_Dark;
    }

    public static MaterialToken Pured_Extra_Thick(boolean z) {
        return z ? Pured_Extra_Thick_Light : Pured_Extra_Thick_Dark;
    }

    public static MaterialToken Colored_Extra_Thin(boolean z) {
        return z ? Colored_Extra_Thin_Light : Colored_Extra_Thin_Dark;
    }

    public static MaterialToken Colored_Thin(boolean z) {
        return z ? Colored_Thin_Light : Colored_Thin_Dark;
    }

    public static MaterialToken Colored_Regular(boolean z) {
        return z ? Colored_Regular_Light : Colored_Regular_Dark;
    }

    public static MaterialToken Colored_Thick(boolean z) {
        return z ? Colored_Thick_Light : Colored_Thick_Dark;
    }

    public static MaterialToken Colored_Extra_Thick(boolean z) {
        return z ? Colored_Extra_Thick_Light : Colored_Extra_Thick_Dark;
    }
}
