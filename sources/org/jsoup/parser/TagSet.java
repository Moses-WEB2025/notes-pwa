package org.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.utils.Constants;
import com.miui.common.stat.EditStat;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.ui.IFragmentController;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.util.a;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import miui.cloud.CloudPushConstants;
import miuix.smartaction.SmartAction;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;

/* JADX INFO: loaded from: classes6.dex */
public class TagSet {
    static final TagSet HtmlTagSet = initHtmlDefault();
    private ArrayList<Consumer<Tag>> customizers;
    private final TagSet source;
    private final Map<String, Map<String, Tag>> tags;

    public static TagSet Html() {
        return new TagSet(HtmlTagSet);
    }

    public TagSet() {
        this.tags = new HashMap();
        this.source = null;
    }

    public TagSet(TagSet tagSet) {
        this.tags = new HashMap();
        this.source = tagSet;
    }

    public TagSet add(Tag tag) {
        tag.set(Tag.Known);
        doAdd(tag);
        return this;
    }

    private void doAdd(Tag tag) {
        ArrayList<Consumer<Tag>> arrayList = this.customizers;
        if (arrayList != null) {
            Iterator<Consumer<Tag>> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(tag);
            }
        }
        this.tags.computeIfAbsent(tag.namespace, new Function() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TagSet.lambda$doAdd$0((String) obj);
            }
        }).put(tag.tagName, tag);
    }

    static /* synthetic */ Map lambda$doAdd$0(String str) {
        return new HashMap();
    }

    public Tag get(String str, String str2) {
        Tag tag;
        Tag tag2;
        Validate.notNull(str);
        Validate.notNull(str2);
        Map<String, Tag> map = this.tags.get(str2);
        if (map != null && (tag2 = map.get(str)) != null) {
            return tag2;
        }
        TagSet tagSet = this.source;
        if (tagSet == null || (tag = tagSet.get(str, str2)) == null) {
            return null;
        }
        Tag tagM4591clone = tag.clone();
        doAdd(tagM4591clone);
        return tagM4591clone;
    }

    Tag valueOf(String str, String str2, String str3, boolean z) {
        Validate.notNull(str);
        Validate.notNull(str3);
        String strTrim = str.trim();
        Validate.notEmpty(strTrim);
        Tag tag = get(strTrim, str3);
        if (tag != null) {
            return tag;
        }
        if (!z) {
            strTrim = str2;
        }
        Tag tag2 = get(str2, str3);
        if (tag2 != null) {
            if (!z || strTrim.equals(str2)) {
                return tag2;
            }
            Tag tagM4591clone = tag2.clone();
            tagM4591clone.tagName = strTrim;
            doAdd(tagM4591clone);
            return tagM4591clone;
        }
        Tag tag3 = new Tag(strTrim, str2, str3);
        doAdd(tag3);
        return tag3;
    }

    public Tag valueOf(String str, String str2, ParseSettings parseSettings) {
        return valueOf(str, ParseSettings.normalName(str), str2, parseSettings.preserveTagCase());
    }

    public Tag valueOf(String str, String str2) {
        return valueOf(str, str2, ParseSettings.preserveCase);
    }

    public TagSet onNewTag(Consumer<Tag> consumer) {
        Validate.notNull(consumer);
        if (this.customizers == null) {
            this.customizers = new ArrayList<>();
        }
        this.customizers.add(consumer);
        return this;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TagSet) {
            return Objects.equals(this.tags, ((TagSet) obj).tags);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.tags);
    }

    static TagSet initHtmlDefault() {
        String[] strArr = {"html", "head", "body", "frameset", StringLookupFactory.KEY_SCRIPT, "noscript", "style", "meta", "link", "title", TypedValues.AttributesType.S_FRAME, "noframes", "section", IFragmentController.TAG_NAVIGATION, "aside", "hgroup", "header", "footer", CloudPushConstants.WATERMARK_TYPE.PERSONAL, "h1", "h2", "h3", "h4", "h5", "h6", "br", "button", "ul", "ol", "pre", "div", "blockquote", HtmlParser.SplitLineElement.TAG, RemoteDeviceInfo.KEY_ADDRESS, "figure", "figcaption", "form", "fieldset", "ins", "del", "dl", "dt", "dd", "li", HtmlParser.TableElement.TAG, "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", HtmlParser.VideoElement.TAG, AITextWidgetStat.TYPE_AUDIO, "canvas", "details", "menu", "plaintext", "template", "article", "main", EditStat.ELEMENT_CENTER, "template", "dir", "applet", "marquee", "listing", "#root"};
        String[] strArr2 = {"object", "base", "font", "tt", IntegerTokenConverter.CONVERTER_KEY, "b", "u", "big", FileSDKUtils.J_SMALL_THUMB, "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "rtc", HtmlParser.LinkCardElement.TAG, HtmlParser.ImageElement.TAG, "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", HtmlParser.InputElement.TAG, SmartAction.Feature.SELECT, "textarea", Todo.LABEL, "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", a.e, "param", "source", "track", NotesNormalConstants.SUMMARY_TABLE_NAME, "command", Constants.JSON_DEVICE, a.e, "basefont", "bgsound", "menuitem", "param", "source", "track", "data", "bdi", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "strike", "nobr", "rb"};
        String[] strArr3 = {"title", HtmlParser.LinkCardElement.TAG, CloudPushConstants.WATERMARK_TYPE.PERSONAL, "h1", "h2", "h3", "h4", "h5", "h6", "pre", RemoteDeviceInfo.KEY_ADDRESS, "li", "th", "td", StringLookupFactory.KEY_SCRIPT, "style", "ins", "del", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "button"};
        String[] strArr4 = {"meta", "link", "base", TypedValues.AttributesType.S_FRAME, HtmlParser.ImageElement.TAG, "br", "wbr", "embed", HtmlParser.SplitLineElement.TAG, HtmlParser.InputElement.TAG, "keygen", "col", "command", Constants.JSON_DEVICE, a.e, "basefont", "bgsound", "menuitem", "param", "source", "track"};
        String[] strArr5 = {"pre", "plaintext", "title", "textarea", StringLookupFactory.KEY_SCRIPT};
        String[] strArr6 = {"title", "textarea"};
        String[] strArr7 = {"iframe", "noembed", "noframes", StringLookupFactory.KEY_SCRIPT, "style", "xmp"};
        return new TagSet().setupTags(Parser.NamespaceHtml, strArr, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Block);
            }
        }).setupTags(Parser.NamespaceHtml, strArr2, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(0);
            }
        }).setupTags(Parser.NamespaceHtml, strArr3, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.InlineContainer);
            }
        }).setupTags(Parser.NamespaceHtml, strArr4, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Void);
            }
        }).setupTags(Parser.NamespaceHtml, strArr5, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.PreserveWhitespace);
            }
        }).setupTags(Parser.NamespaceHtml, strArr6, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.RcData);
            }
        }).setupTags(Parser.NamespaceHtml, strArr7, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Data);
            }
        }).setupTags(Parser.NamespaceHtml, SharedConstants.FormSubmitTags, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.FormSubmittable);
            }
        }).setupTags(Parser.NamespaceMathml, new String[]{"math"}, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Block);
            }
        }).setupTags(Parser.NamespaceMathml, new String[]{"mi", "mo", "msup", "mn", "mtext"}, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(0);
            }
        }).setupTags(Parser.NamespaceSvg, new String[]{"svg", "femerge", "femergenode"}, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Block);
            }
        }).setupTags(Parser.NamespaceSvg, new String[]{"text"}, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(0);
            }
        }).setupTags(Parser.NamespaceSvg, new String[]{StringLookupFactory.KEY_SCRIPT}, new Consumer() { // from class: org.jsoup.parser.TagSet$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Tag) obj).set(Tag.Data);
            }
        });
    }

    private TagSet setupTags(String str, String[] strArr, Consumer<Tag> consumer) {
        for (String str2 : strArr) {
            Tag tag = get(str2, str);
            if (tag == null) {
                tag = new Tag(str2, str2, str);
                tag.options = 0;
                add(tag);
            }
            consumer.accept(tag);
        }
        return this;
    }
}
