package com.xiaomi.miai;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes4.dex */
public class SDKConstants {
    public static Charset UTF_8 = StandardCharsets.UTF_8;
    public static Charset US_ASCII = StandardCharsets.US_ASCII;

    public static class DigestAlgorithm {
        public static final String MD5 = "MD5";
        public static final String SHA_1 = "SHA-1";
        public static final String SHA_256 = "SHA-256";
    }

    public static class HttpHeader {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_DISPOSITION = "Content-Disposition";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FORWARDED = "Forwarded";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "Te";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    }

    public static class HttpStaus {
        public static final int ACCEPTED = 202;
        public static final int BAD_GATEWAY = 502;
        public static final int BAD_REQUEST = 400;
        public static final int CONFLICT = 409;
        public static final int CONTINUE = 100;
        public static final int CREATED = 201;
        public static final int EXPECTATION_FAILED = 417;
        public static final int FAILED_DEPENDENCY = 424;
        public static final int FORBIDDEN = 403;
        public static final int FOUND = 302;
        public static final int GATEWAY_TIMEOUT = 504;
        public static final int GONE = 410;
        public static final int HTTP_VERSION_NOT_SUPPORTED = 505;
        public static final int INSUFFICIENT_STORAGE = 507;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int LENGTH_REQUIRED = 411;
        public static final int LOCKED = 423;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int MOVED_PERMANENTLY = 301;
        public static final int MULTIPLE_CHOICES = 300;
        public static final int MULTI_STATUS = 207;
        public static final int NON_AUTHORITATIVE_INFORMATION = 203;
        public static final int NOT_ACCEPTABLE = 406;
        public static final int NOT_FOUND = 404;
        public static final int NOT_IMPLEMENTED = 501;
        public static final int NOT_MODIFIED = 304;
        public static final int NO_CONTENT = 204;
        public static final int OK = 200;
        public static final int PARTIAL_CONTENT = 206;
        public static final int PAYMENT_REQUIRED = 402;
        public static final int PERMANENT_REDIRECT = 308;
        public static final int PRECONDITION_FAILED = 412;
        public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
        public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
        public static final int REQUEST_ENTITY_TOO_LARGE = 413;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int REQUEST_URI_TOO_LONG = 414;
        public static final int RESET_CONTENT = 205;
        public static final int SEE_OTHER = 303;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int SWITCHING_PROTOCOLS = 101;
        public static final int TEMPORARY_REDIRECT = 307;
        public static final int TOO_MANY_REQUESTS = 429;
        public static final int UNAUTHORIZED = 401;
        public static final int UNPROCESSABLE_ENTITY = 422;
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int UPGRADE_REQUIRED = 426;
        public static final int USE_PROXY = 305;
    }

    public static class MimeType {
        public static final String BINARY = "application/octet-stream";
        public static final String CACHE_MANIFEST = "text/cache-manifest";
        public static final String CSS = "text/css";
        public static final String EVENT_STREAM = "text/event-stream";
        public static final String FORM = "application/x-www-form-urlencoded";
        public static final String HTML = "text/html";
        public static final String JAVASCRIPT = "text/javascript";
        public static final String JSON = "application/json";
        public static final String TEXT = "text/plain";
        public static final String XML = "application/xml";
    }

    public static class Network {
        public static final long DEFAULT_CONNECT_TIMEOUT = 5000;
        public static final long DEFAULT_READ_TIMEOUT = 2000;
        public static final long DEFAULT_WRITE_TIMEOUT = 2000;
    }

    public static class ContentType {
        public static final String BINARY = "application/octet-stream";
        public static final String FORM = "application/x-www-form-urlencoded";
        public static final String JSON = "application/json";
        public static final String TEXT_UTF8 = utf8("text/plain");

        public static String utf8(String str) {
            return withCharset(str, SDKConstants.UTF_8);
        }

        public static String withCharset(String str, Charset charset) {
            return String.format("%s; charset=%s", str, charset.name());
        }
    }
}
