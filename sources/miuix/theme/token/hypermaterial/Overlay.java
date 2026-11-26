package miuix.theme.token.hypermaterial;

import com.xiaomi.verificationsdk.internal.Constants;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;

/* JADX INFO: loaded from: classes6.dex */
public class Overlay {
    public static MaterialToken Extra_Thin_Light = new MaterialToken.Builder(10, "overlay-extra-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Overlay_Extra_Thin_Light).setMaskBlur(30).build();
    public static MaterialToken Extra_Thin_Dark = new MaterialToken.Builder(10, "overlay-extra-thin", "dark").setColorBlend(ColorBlendToken.Overlay_Extra_Thin_Dark).setMaskBlur(30).build();
    public static MaterialDayNightToken Extra_Thin = new MaterialDayNightToken(Extra_Thin_Light, Extra_Thin_Dark);
    public static MaterialToken Thin_Light = new MaterialToken.Builder(30, "overlay-thin", Constants.LIGHT).setColorBlend(ColorBlendToken.Overlay_Thin_Light).setMaskBlur(70).build();
    public static MaterialToken Regular_Light = new MaterialToken.Builder(30, "overlay-regular", Constants.LIGHT).setColorBlend(ColorBlendToken.Overlay_Regular_Light).setMaskBlur(100).build();
    public static MaterialToken Thick_Light = new MaterialToken.Builder(10, "overlay-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Overlay_Thick_Light).setMaskBlur(100).build();
    public static MaterialToken Thick_Dark = new MaterialToken.Builder(30, "overlay-thick", "dark").setColorBlend(ColorBlendToken.Overlay_Thick_Dark).setMaskBlur(100).build();
    public static MaterialDayNightToken Thick = new MaterialDayNightToken(Thick_Light, Thick_Dark);
    public static MaterialToken Extra_Thick_Light = new MaterialToken.Builder(10, "overlay-extra-thick", Constants.LIGHT).setColorBlend(ColorBlendToken.Overlay_Extra_Thick_Light).setMaskBlur(100).build();
}
