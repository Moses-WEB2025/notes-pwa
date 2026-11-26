package com.google.common.net;

import ch.qos.logback.core.CoreConstants;
import cn.kuaipan.android.http.multipart.FilePart;
import com.google.android.appfunctions.schema.calendar.v1.CalendarKt;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;
import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
@ElementTypesAreNonnullByDefault
public final class MediaType {
    private static final String AUDIO_TYPE = "audio";
    private static final String IMAGE_TYPE = "image";
    private static final String TEXT_TYPE = "text";
    private static final String VIDEO_TYPE = "video";
    private static final String WILDCARD = "*";

    @LazyInit
    private int hashCode;
    private final ImmutableListMultimap<String, String> parameters;

    @CheckForNull
    @LazyInit
    private Optional<Charset> parsedCharset;
    private final String subtype;

    @CheckForNull
    @LazyInit
    private String toString;
    private final String type;
    private static final String CHARSET_ATTRIBUTE = "charset";
    private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of(CHARSET_ATTRIBUTE, Ascii.toLowerCase(Charsets.UTF_8.name()));
    private static final CharMatcher TOKEN_MATCHER = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private static final CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
    private static final CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
    private static final Map<MediaType, MediaType> KNOWN_TYPES = Maps.newHashMap();
    public static final MediaType ANY_TYPE = createConstant("*", "*");
    public static final MediaType ANY_TEXT_TYPE = createConstant("text", "*");
    public static final MediaType ANY_IMAGE_TYPE = createConstant("image", "*");
    public static final MediaType ANY_AUDIO_TYPE = createConstant("audio", "*");
    public static final MediaType ANY_VIDEO_TYPE = createConstant("video", "*");
    private static final String APPLICATION_TYPE = "application";
    public static final MediaType ANY_APPLICATION_TYPE = createConstant(APPLICATION_TYPE, "*");
    private static final String FONT_TYPE = "font";
    public static final MediaType ANY_FONT_TYPE = createConstant(FONT_TYPE, "*");
    public static final MediaType CACHE_MANIFEST_UTF_8 = createConstantUtf8("text", "cache-manifest");
    public static final MediaType CSS_UTF_8 = createConstantUtf8("text", "css");
    public static final MediaType CSV_UTF_8 = createConstantUtf8("text", "csv");
    public static final MediaType HTML_UTF_8 = createConstantUtf8("text", "html");
    public static final MediaType I_CALENDAR_UTF_8 = createConstantUtf8("text", CalendarKt.APP_FUNCTION_SCHEMA_CATEGORY_CALENDAR);
    public static final MediaType PLAIN_TEXT_UTF_8 = createConstantUtf8("text", "plain");
    public static final MediaType TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8("text", "javascript");
    public static final MediaType TSV_UTF_8 = createConstantUtf8("text", "tab-separated-values");
    public static final MediaType VCARD_UTF_8 = createConstantUtf8("text", "vcard");
    public static final MediaType WML_UTF_8 = createConstantUtf8("text", "vnd.wap.wml");
    public static final MediaType XML_UTF_8 = createConstantUtf8("text", StringLookupFactory.KEY_XML);
    public static final MediaType VTT_UTF_8 = createConstantUtf8("text", "vtt");
    public static final MediaType BMP = createConstant("image", "bmp");
    public static final MediaType CRW = createConstant("image", "x-canon-crw");
    public static final MediaType GIF = createConstant("image", "gif");
    public static final MediaType ICO = createConstant("image", "vnd.microsoft.icon");
    public static final MediaType JPEG = createConstant("image", "jpeg");
    public static final MediaType PNG = createConstant("image", "png");
    public static final MediaType PSD = createConstant("image", "vnd.adobe.photoshop");
    public static final MediaType SVG_UTF_8 = createConstantUtf8("image", "svg+xml");
    public static final MediaType TIFF = createConstant("image", "tiff");
    public static final MediaType WEBP = createConstant("image", "webp");
    public static final MediaType HEIF = createConstant("image", "heif");
    public static final MediaType JP2K = createConstant("image", "jp2");
    public static final MediaType MP4_AUDIO = createConstant("audio", "mp4");
    public static final MediaType MPEG_AUDIO = createConstant("audio", "mpeg");
    public static final MediaType OGG_AUDIO = createConstant("audio", "ogg");
    public static final MediaType WEBM_AUDIO = createConstant("audio", "webm");
    public static final MediaType L16_AUDIO = createConstant("audio", "l16");
    public static final MediaType L24_AUDIO = createConstant("audio", "l24");
    public static final MediaType BASIC_AUDIO = createConstant("audio", "basic");
    public static final MediaType AAC_AUDIO = createConstant("audio", "aac");
    public static final MediaType VORBIS_AUDIO = createConstant("audio", "vorbis");
    public static final MediaType WMA_AUDIO = createConstant("audio", "x-ms-wma");
    public static final MediaType WAX_AUDIO = createConstant("audio", "x-ms-wax");
    public static final MediaType VND_REAL_AUDIO = createConstant("audio", "vnd.rn-realaudio");
    public static final MediaType VND_WAVE_AUDIO = createConstant("audio", "vnd.wave");
    public static final MediaType MP4_VIDEO = createConstant("video", "mp4");
    public static final MediaType MPEG_VIDEO = createConstant("video", "mpeg");
    public static final MediaType OGG_VIDEO = createConstant("video", "ogg");
    public static final MediaType QUICKTIME = createConstant("video", "quicktime");
    public static final MediaType WEBM_VIDEO = createConstant("video", "webm");
    public static final MediaType WMV = createConstant("video", "x-ms-wmv");
    public static final MediaType FLV_VIDEO = createConstant("video", "x-flv");
    public static final MediaType THREE_GPP_VIDEO = createConstant("video", "3gpp");
    public static final MediaType THREE_GPP2_VIDEO = createConstant("video", "3gpp2");
    public static final MediaType APPLICATION_XML_UTF_8 = createConstantUtf8(APPLICATION_TYPE, StringLookupFactory.KEY_XML);
    public static final MediaType ATOM_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "atom+xml");
    public static final MediaType BZIP2 = createConstant(APPLICATION_TYPE, "x-bzip2");
    public static final MediaType DART_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "dart");
    public static final MediaType APPLE_PASSBOOK = createConstant(APPLICATION_TYPE, "vnd.apple.pkpass");
    public static final MediaType EOT = createConstant(APPLICATION_TYPE, "vnd.ms-fontobject");
    public static final MediaType EPUB = createConstant(APPLICATION_TYPE, "epub+zip");
    public static final MediaType FORM_DATA = createConstant(APPLICATION_TYPE, "x-www-form-urlencoded");
    public static final MediaType KEY_ARCHIVE = createConstant(APPLICATION_TYPE, "pkcs12");
    public static final MediaType APPLICATION_BINARY = createConstant(APPLICATION_TYPE, FilePart.DEFAULT_TRANSFER_ENCODING);
    public static final MediaType GEO_JSON = createConstant(APPLICATION_TYPE, "geo+json");
    public static final MediaType GZIP = createConstant(APPLICATION_TYPE, "x-gzip");
    public static final MediaType HAL_JSON = createConstant(APPLICATION_TYPE, "hal+json");
    public static final MediaType JAVASCRIPT_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "javascript");
    public static final MediaType JOSE = createConstant(APPLICATION_TYPE, "jose");
    public static final MediaType JOSE_JSON = createConstant(APPLICATION_TYPE, "jose+json");
    public static final MediaType JSON_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "json");
    public static final MediaType JWT = createConstant(APPLICATION_TYPE, "jwt");
    public static final MediaType MANIFEST_JSON_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "manifest+json");
    public static final MediaType KML = createConstant(APPLICATION_TYPE, "vnd.google-earth.kml+xml");
    public static final MediaType KMZ = createConstant(APPLICATION_TYPE, "vnd.google-earth.kmz");
    public static final MediaType MBOX = createConstant(APPLICATION_TYPE, "mbox");
    public static final MediaType APPLE_MOBILE_CONFIG = createConstant(APPLICATION_TYPE, "x-apple-aspen-config");
    public static final MediaType MICROSOFT_EXCEL = createConstant(APPLICATION_TYPE, "vnd.ms-excel");
    public static final MediaType MICROSOFT_OUTLOOK = createConstant(APPLICATION_TYPE, "vnd.ms-outlook");
    public static final MediaType MICROSOFT_POWERPOINT = createConstant(APPLICATION_TYPE, "vnd.ms-powerpoint");
    public static final MediaType MICROSOFT_WORD = createConstant(APPLICATION_TYPE, "msword");
    public static final MediaType MEDIA_PRESENTATION_DESCRIPTION = createConstant(APPLICATION_TYPE, "dash+xml");
    public static final MediaType WASM_APPLICATION = createConstant(APPLICATION_TYPE, "wasm");
    public static final MediaType NACL_APPLICATION = createConstant(APPLICATION_TYPE, "x-nacl");
    public static final MediaType NACL_PORTABLE_APPLICATION = createConstant(APPLICATION_TYPE, "x-pnacl");
    public static final MediaType OCTET_STREAM = createConstant(APPLICATION_TYPE, "octet-stream");
    public static final MediaType OGG_CONTAINER = createConstant(APPLICATION_TYPE, "ogg");
    public static final MediaType OOXML_DOCUMENT = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MediaType OOXML_PRESENTATION = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MediaType OOXML_SHEET = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MediaType OPENDOCUMENT_GRAPHICS = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.graphics");
    public static final MediaType OPENDOCUMENT_PRESENTATION = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.presentation");
    public static final MediaType OPENDOCUMENT_SPREADSHEET = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.spreadsheet");
    public static final MediaType OPENDOCUMENT_TEXT = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.text");
    public static final MediaType OPENSEARCH_DESCRIPTION_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "opensearchdescription+xml");
    public static final MediaType PDF = createConstant(APPLICATION_TYPE, "pdf");
    public static final MediaType POSTSCRIPT = createConstant(APPLICATION_TYPE, "postscript");
    public static final MediaType PROTOBUF = createConstant(APPLICATION_TYPE, "protobuf");
    public static final MediaType RDF_XML_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "rdf+xml");
    public static final MediaType RTF_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "rtf");
    public static final MediaType SFNT = createConstant(APPLICATION_TYPE, "font-sfnt");
    public static final MediaType SHOCKWAVE_FLASH = createConstant(APPLICATION_TYPE, "x-shockwave-flash");
    public static final MediaType SKETCHUP = createConstant(APPLICATION_TYPE, "vnd.sketchup.skp");
    public static final MediaType SOAP_XML_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "soap+xml");
    public static final MediaType TAR = createConstant(APPLICATION_TYPE, "x-tar");
    public static final MediaType WOFF = createConstant(APPLICATION_TYPE, "font-woff");
    public static final MediaType WOFF2 = createConstant(APPLICATION_TYPE, "font-woff2");
    public static final MediaType XHTML_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "xhtml+xml");
    public static final MediaType XRD_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "xrd+xml");
    public static final MediaType ZIP = createConstant(APPLICATION_TYPE, "zip");
    public static final MediaType FONT_COLLECTION = createConstant(FONT_TYPE, "collection");
    public static final MediaType FONT_OTF = createConstant(FONT_TYPE, "otf");
    public static final MediaType FONT_SFNT = createConstant(FONT_TYPE, "sfnt");
    public static final MediaType FONT_TTF = createConstant(FONT_TYPE, "ttf");
    public static final MediaType FONT_WOFF = createConstant(FONT_TYPE, "woff");
    public static final MediaType FONT_WOFF2 = createConstant(FONT_TYPE, "woff2");
    private static final Joiner.MapJoiner PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");

    private static MediaType createConstant(String type, String subtype) {
        MediaType mediaTypeAddKnownType = addKnownType(new MediaType(type, subtype, ImmutableListMultimap.of()));
        mediaTypeAddKnownType.parsedCharset = Optional.absent();
        return mediaTypeAddKnownType;
    }

    private static MediaType createConstantUtf8(String type, String subtype) {
        MediaType mediaTypeAddKnownType = addKnownType(new MediaType(type, subtype, UTF_8_CONSTANT_PARAMETERS));
        mediaTypeAddKnownType.parsedCharset = Optional.of(Charsets.UTF_8);
        return mediaTypeAddKnownType;
    }

    private static MediaType addKnownType(MediaType mediaType) {
        KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String type, String subtype, ImmutableListMultimap<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    public String type() {
        return this.type;
    }

    public String subtype() {
        return this.subtype;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function() { // from class: com.google.common.net.MediaType$$ExternalSyntheticLambda1
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return ImmutableMultiset.copyOf((Collection) obj);
            }
        });
    }

    public Optional<Charset> charset() {
        Optional<Charset> optionalAbsent = this.parsedCharset;
        if (optionalAbsent == null) {
            optionalAbsent = Optional.absent();
            UnmodifiableIterator<String> it = this.parameters.get(CHARSET_ATTRIBUTE).iterator();
            String str = null;
            while (it.hasNext()) {
                String next = it.next();
                if (str == null) {
                    optionalAbsent = Optional.of(Charset.forName(next));
                    str = next;
                } else if (!str.equals(next)) {
                    throw new IllegalStateException("Multiple charset values defined: " + str + ", " + next);
                }
            }
            this.parsedCharset = optionalAbsent;
        }
        return optionalAbsent;
    }

    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : create(this.type, this.subtype);
    }

    public MediaType withParameters(Multimap<String, String> parameters) {
        return create(this.type, this.subtype, parameters);
    }

    public MediaType withParameters(String attribute, Iterable<String> values) {
        Preconditions.checkNotNull(attribute);
        Preconditions.checkNotNull(values);
        String strNormalizeToken = normalizeToken(attribute);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        UnmodifiableIterator<Map.Entry<String, String>> it = this.parameters.entries().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            if (!strNormalizeToken.equals(key)) {
                builder.put(key, next.getValue());
            }
        }
        Iterator<String> it2 = values.iterator();
        while (it2.hasNext()) {
            builder.put(strNormalizeToken, normalizeParameterValue(strNormalizeToken, it2.next()));
        }
        MediaType mediaType = new MediaType(this.type, this.subtype, builder.build());
        if (!strNormalizeToken.equals(CHARSET_ATTRIBUTE)) {
            mediaType.parsedCharset = this.parsedCharset;
        }
        return (MediaType) MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    public MediaType withParameter(String attribute, String value) {
        return withParameters(attribute, ImmutableSet.of(value));
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        MediaType mediaTypeWithParameter = withParameter(CHARSET_ATTRIBUTE, charset.name());
        mediaTypeWithParameter.parsedCharset = Optional.of(charset);
        return mediaTypeWithParameter;
    }

    public boolean hasWildcard() {
        return "*".equals(this.type) || "*".equals(this.subtype);
    }

    public boolean is(MediaType mediaTypeRange) {
        if (mediaTypeRange.type.equals("*") || mediaTypeRange.type.equals(this.type)) {
            return (mediaTypeRange.subtype.equals("*") || mediaTypeRange.subtype.equals(this.subtype)) && this.parameters.entries().containsAll(mediaTypeRange.parameters.entries());
        }
        return false;
    }

    public static MediaType create(String type, String subtype) {
        MediaType mediaTypeCreate = create(type, subtype, ImmutableListMultimap.of());
        mediaTypeCreate.parsedCharset = Optional.absent();
        return mediaTypeCreate;
    }

    private static MediaType create(String type, String subtype, Multimap<String, String> parameters) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(subtype);
        Preconditions.checkNotNull(parameters);
        String strNormalizeToken = normalizeToken(type);
        String strNormalizeToken2 = normalizeToken(subtype);
        Preconditions.checkArgument(!"*".equals(strNormalizeToken) || "*".equals(strNormalizeToken2), "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, String> entry : parameters.entries()) {
            String strNormalizeToken3 = normalizeToken(entry.getKey());
            builder.put(strNormalizeToken3, normalizeParameterValue(strNormalizeToken3, entry.getValue()));
        }
        MediaType mediaType = new MediaType(strNormalizeToken, strNormalizeToken2, builder.build());
        return (MediaType) MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    static MediaType createApplicationType(String subtype) {
        return create(APPLICATION_TYPE, subtype);
    }

    static MediaType createAudioType(String subtype) {
        return create("audio", subtype);
    }

    static MediaType createFontType(String subtype) {
        return create(FONT_TYPE, subtype);
    }

    static MediaType createImageType(String subtype) {
        return create("image", subtype);
    }

    static MediaType createTextType(String subtype) {
        return create("text", subtype);
    }

    static MediaType createVideoType(String subtype) {
        return create("video", subtype);
    }

    private static String normalizeToken(String token) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(token));
        Preconditions.checkArgument(!token.isEmpty());
        return Ascii.toLowerCase(token);
    }

    private static String normalizeParameterValue(String attribute, String value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(CharMatcher.ascii().matchesAllOf(value), "parameter values must be ASCII: %s", value);
        return CHARSET_ATTRIBUTE.equals(attribute) ? Ascii.toLowerCase(value) : value;
    }

    public static MediaType parse(String input) {
        String strConsumeToken;
        Preconditions.checkNotNull(input);
        Tokenizer tokenizer = new Tokenizer(input);
        try {
            CharMatcher charMatcher = TOKEN_MATCHER;
            String strConsumeToken2 = tokenizer.consumeToken(charMatcher);
            tokenizer.consumeCharacter('/');
            String strConsumeToken3 = tokenizer.consumeToken(charMatcher);
            ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
            while (tokenizer.hasMore()) {
                CharMatcher charMatcher2 = LINEAR_WHITE_SPACE;
                tokenizer.consumeTokenIfPresent(charMatcher2);
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(charMatcher2);
                CharMatcher charMatcher3 = TOKEN_MATCHER;
                String strConsumeToken4 = tokenizer.consumeToken(charMatcher3);
                tokenizer.consumeCharacter('=');
                if ('\"' == tokenizer.previewChar()) {
                    tokenizer.consumeCharacter('\"');
                    StringBuilder sb = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter(CoreConstants.ESCAPE_CHAR);
                            sb.append(tokenizer.consumeCharacter(CharMatcher.ascii()));
                        } else {
                            sb.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                        }
                    }
                    strConsumeToken = sb.toString();
                    tokenizer.consumeCharacter('\"');
                } else {
                    strConsumeToken = tokenizer.consumeToken(charMatcher3);
                }
                builder.put(strConsumeToken4, strConsumeToken);
            }
            return create(strConsumeToken2, strConsumeToken3, builder.build());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Could not parse '" + input + "'", e);
        }
    }

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String input) {
            this.input = input;
        }

        String consumeTokenIfPresent(CharMatcher matcher) {
            Preconditions.checkState(hasMore());
            int i = this.position;
            this.position = matcher.negate().indexIn(this.input, i);
            return hasMore() ? this.input.substring(i, this.position) : this.input.substring(i);
        }

        String consumeToken(CharMatcher matcher) {
            int i = this.position;
            String strConsumeTokenIfPresent = consumeTokenIfPresent(matcher);
            Preconditions.checkState(this.position != i);
            return strConsumeTokenIfPresent;
        }

        char consumeCharacter(CharMatcher matcher) {
            Preconditions.checkState(hasMore());
            char cPreviewChar = previewChar();
            Preconditions.checkState(matcher.matches(cPreviewChar));
            this.position++;
            return cPreviewChar;
        }

        char consumeCharacter(char c) {
            Preconditions.checkState(hasMore());
            Preconditions.checkState(previewChar() == c);
            this.position++;
            return c;
        }

        char previewChar() {
            Preconditions.checkState(hasMore());
            return this.input.charAt(this.position);
        }

        boolean hasMore() {
            int i = this.position;
            return i >= 0 && i < this.input.length();
        }
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MediaType) {
            MediaType mediaType = (MediaType) obj;
            if (this.type.equals(mediaType.type) && this.subtype.equals(mediaType.subtype) && parametersAsMap().equals(mediaType.parametersAsMap())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int iHashCode = Objects.hashCode(this.type, this.subtype, parametersAsMap());
        this.hashCode = iHashCode;
        return iHashCode;
    }

    public String toString() {
        String str = this.toString;
        if (str != null) {
            return str;
        }
        String strComputeToString = computeToString();
        this.toString = strComputeToString;
        return strComputeToString;
    }

    private String computeToString() {
        StringBuilder sbAppend = new StringBuilder().append(this.type).append('/').append(this.subtype);
        if (!this.parameters.isEmpty()) {
            sbAppend.append("; ");
            PARAMETER_JOINER.appendTo(sbAppend, Multimaps.transformValues((ListMultimap) this.parameters, new Function() { // from class: com.google.common.net.MediaType$$ExternalSyntheticLambda0
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return MediaType.lambda$computeToString$0((String) obj);
                }
            }).entries());
        }
        return sbAppend.toString();
    }

    static /* synthetic */ String lambda$computeToString$0(String str) {
        return (!TOKEN_MATCHER.matchesAllOf(str) || str.isEmpty()) ? escapeAndQuote(str) : str;
    }

    private static String escapeAndQuote(String value) {
        StringBuilder sbAppend = new StringBuilder(value.length() + 16).append('\"');
        for (int i = 0; i < value.length(); i++) {
            char cCharAt = value.charAt(i);
            if (cCharAt == '\r' || cCharAt == '\\' || cCharAt == '\"') {
                sbAppend.append(CoreConstants.ESCAPE_CHAR);
            }
            sbAppend.append(cCharAt);
        }
        return sbAppend.append('\"').toString();
    }
}
