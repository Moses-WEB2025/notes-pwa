uniform vec2 uResolution;
uniform shader uTex;

uniform vec2 forePanelWH;
uniform float sdfOffset;
uniform float rimRadius;
uniform float rimSize;
uniform float whiteRimSize;
uniform float whiteRimOffset;
uniform float whiteRimIntensity;
uniform float whiteAlpha;
uniform vec4 uRimMaskColor;

// 新版参数
uniform float uAnimTime;
uniform vec4 uBound;
uniform float uTranslateY;
uniform vec3 uPoints[4];
uniform vec4 uColors[4];
uniform float uAlphaMulti;
uniform float uNoiseScale;
uniform float uPointOffset;
uniform float uPointRadiusMulti;
uniform float uSaturateOffset;
uniform float uLightOffset;

vec3 hsl2rgb(in vec3 c)
{
    vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0, 4.0, 2.0), 6.0)-3.0)-1.0, 0.0, 1.0);

    return c.z + c.y * (rgb-0.5)*(1.0-abs(2.0*c.z-1.0));
}

vec3 HueShift (in vec3 Color, in float Shift)
{
    vec3 P = vec3(0.55735)*dot(vec3(0.55735), Color);

    vec3 U = Color-P;

    vec3 V = cross(vec3(0.55735), U);

    Color = U*cos(Shift*6.2832) + V*sin(Shift*6.2832) + P;

    return vec3(Color);
}

vec3 rgb2hsl(in vec3 c){
    float h = 0.0;
    float s = 0.0;
    float l = 0.0;
    float r = c.r;
    float g = c.g;
    float b = c.b;
    float cMin = min(r, min(g, b));
    float cMax = max(r, max(g, b));

    l = (cMax + cMin) / 2.0;
    if (cMax > cMin) {
        float cDelta = cMax - cMin;

        //s = l < .05 ? cDelta / ( cMax + cMin ) : cDelta / ( 2.0 - ( cMax + cMin ) ); Original
        s = l < .0 ? cDelta / (cMax + cMin) : cDelta / (2.0 - (cMax + cMin));

        if (r == cMax) {
            h = (g - b) / cDelta;
        } else if (g == cMax) {
            h = 2.0 + (b - r) / cDelta;
        } else {
            h = 4.0 + (r - g) / cDelta;
        }

        if (h < 0.0) {
            h += 6.0;
        }
        h = h / 6.0;
    }
    return vec3(h, s, l);
}

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float hash(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.13);
    p3 += dot(p3, p3.yzx + 3.333);
    return fract((p3.x + p3.y) * p3.z);
}

float perlin(vec2 x) {
    vec2 i = floor(x);
    vec2 f = fract(x);

    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}
//-------------------------------------------------------------
vec2 myPow(vec2 b, float e) {
    return vec2(pow(b.x, e), pow(b.y, e));
}

vec2 randVec(vec2 value)
{
    vec2 vec = vec2(dot(value, vec2(127.1, 337.1)), dot(value, vec2(269.5, 183.3)));
    vec = -1 + 2 * fract(sin(vec) * 43758.5453123);
    return vec;
}

float perlinNoise(vec2 uv)
{
    float a, b, c, d;
    float x0 = floor(uv.x);
    float x1 = ceil(uv.x);
    float y0 = floor(uv.y);
    float y1 = ceil(uv.y);
    vec2 pos = fract(uv);
    a = dot(randVec(vec2(x0, y0)), pos - vec2(0, 0));
    b = dot(randVec(vec2(x0, y1)), pos - vec2(0, 1));
    c = dot(randVec(vec2(x1, y1)), pos - vec2(1, 1));
    d = dot(randVec(vec2(x1, y0)), pos - vec2(1, 0));
    vec2 st = 6 * myPow(pos, 5.0) - 15 * myPow(pos, 4.0) + 10 * myPow(pos, 3.0);
    a = mix(a, d, st.x);
    b = mix(b, c, st.x);
    a = mix(a, b, st.y);
    return a;
}
//-------------------------------------------------------------

vec4 srcOver(vec4 src, vec4 dst){
    return src + dst * (1.0 - src.a);
}

vec4 blendSrcOver(vec4 src, vec4 dst) {
    if (src.a == 0.0) {
        return dst;
    }

    float srcAlpha = src.a;
    float dstAlpha = dst.a * (1.0 - srcAlpha);
    float outAlpha = srcAlpha + dstAlpha;

    if (outAlpha == 0.0) {
        return vec4(0, 0, 0, 0);
    }

    vec4 outColor = (src * srcAlpha + dst * dstAlpha) / outAlpha;
    return vec4(outColor.rgb, outAlpha);
}

float udRoundRect(vec2 p, vec2 b, float r)
{
    return length(max(abs(p) - b, 0.0)) - r;
}

vec4 main(vec2 fragCoord){

    vec2 vUv = fragCoord/uResolution;
    vUv.y = 1.0-vUv.y;
    vec2 uv = vUv;
    uv -= vec2(0., uTranslateY);

    uv.xy -= uBound.xy;
    uv.xy /= uBound.zw;

    vec3 hsv;

    vec4 color = vec4(0., 0., 0., 0.);
    float noiseValue = perlin(vUv * uNoiseScale + vec2(-uAnimTime, -uAnimTime));

    // draw circles
    for (int i = 0; i < 4; i++){
        vec4 pointColor = uColors[i];
        pointColor.rgb *= pointColor.a;

        vec2 point = uPoints[i].xy;
        float rad = uPoints[i].z * uPointRadiusMulti;

        point.x += sin(uAnimTime + point.y) * uPointOffset;
        point.y += cos(uAnimTime + point.x) * uPointOffset;

        float d = distance(uv, point);
        float pct = smoothstep(rad, 0., d);

        color.rgb = mix(color.rgb, pointColor.rgb, pct);
        color.a = mix(color.a, pointColor.a, pct);
    }

    float oppositeNoise = smoothstep(0., 1., noiseValue);

    color.rgb /= color.a;

    hsv = rgb2hsv(color.rgb);
    hsv.y = mix(hsv.y, 0.0, oppositeNoise * uSaturateOffset);
    // hsv.y += oppositeNoise * uSaturateOffset;
    color.rgb = hsv2rgb(hsv);

    color.rgb += oppositeNoise * uLightOffset;
    // color.a += noiseValue * uAlphaOffset;


    // SDF计算窗口阴影mask
    vec2 center = uResolution.xy * 0.5;
    vec2 hsize = forePanelWH.xy / 2.0;
    float radius = rimRadius;
    float shadow = 1.0 - smoothstep(0.0, 50.0, udRoundRect(fragCoord - center, hsize - radius + sdfOffset, radius));
    color.a *= shadow;
    // windowMask输入框范围为1，其他范围为0
    float windowMask = 1.0 - smoothstep(0.0, 0.0, udRoundRect(fragCoord - center, hsize - radius, radius));

    // 白色外发光
    float white = 1.0 - smoothstep(0.0, whiteRimSize, udRoundRect(fragCoord - center, hsize - radius + whiteRimOffset,
    radius));
    white = pow(white, 2.0);
    white *= 1.0 - windowMask;
    white = clamp(white - (1.0 - whiteRimIntensity), 0.0, 1.0);

    color.a = clamp(color.a, 0., 1.);
    color.a *= uAlphaMulti;

    vec4 bgColor = uTex.eval(vec2(vUv.x, 1.0 - vUv.y)*uResolution);

    vec4 fragColor;

    //    if (bgColor.a < 0.99) {
    if (windowMask < 0.1) {
        //                fragColor = blendSrcOver(color, vec4(vec3(1.0), white));
        fragColor = color;
        //        fragColor = vec4(shadow.rrr,1.0);
    } else {
        fragColor = bgColor;
    }

    fragColor = blendSrcOver(fragColor, vec4(vec3(uRimMaskColor.rgb), white * whiteAlpha));
//    fragColor = blendSrcOver(vec4(vec3(1.0), white * whiteAlpha), fragColor);

    //    return vec4(shadow.rrr, 1.0);
    //        return vec4(windowMask.rrr, 1.0);
    //        return vec4(test.rrr, 1.0);
    //        return vec4(white.rrr, 1.0);
    //    return vec4(0.0);
    //    return vec4(1.0,1.0,1.0, 1.0);
    //    return vec4(noiseValue.rrr,1.0);
//            return vec4(color.rgb*color.a, color.a);
    return vec4(fragColor.rgb*fragColor.a, fragColor.a);
}