package org.jsoup.nodes;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import net.lingala.zip4j.util.InternalZipConstants;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;

/* JADX INFO: loaded from: classes6.dex */
public class Attributes implements Iterable<Attribute>, Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String EmptyString = "";
    private static final int GrowthFactor = 2;
    private static final int InitialCapacity = 3;
    static final char InternalPrefix = '/';
    static final int NotFound = -1;
    protected static final String dataPrefix = "data-";
    private int size = 0;
    String[] keys = new String[3];
    Object[] vals = new Object[3];

    private void checkCapacity(int i) {
        Validate.isTrue(i >= this.size);
        String[] strArr = this.keys;
        int length = strArr.length;
        if (length >= i) {
            return;
        }
        int i2 = length >= 3 ? this.size * 2 : 3;
        if (i <= i2) {
            i = i2;
        }
        this.keys = (String[]) Arrays.copyOf(strArr, i);
        this.vals = Arrays.copyOf(this.vals, i);
    }

    int indexOfKey(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equals(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    private int indexOfKeyIgnoreCase(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equalsIgnoreCase(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    static String checkNotNull(Object obj) {
        return obj == null ? "" : (String) obj;
    }

    public String get(String str) {
        int iIndexOfKey = indexOfKey(str);
        return iIndexOfKey == -1 ? "" : checkNotNull(this.vals[iIndexOfKey]);
    }

    public Attribute attribute(String str) {
        int iIndexOfKey = indexOfKey(str);
        if (iIndexOfKey == -1) {
            return null;
        }
        return new Attribute(str, checkNotNull(this.vals[iIndexOfKey]), this);
    }

    public String getIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        return iIndexOfKeyIgnoreCase == -1 ? "" : checkNotNull(this.vals[iIndexOfKeyIgnoreCase]);
    }

    public Attributes add(String str, String str2) {
        addObject(str, str2);
        return this;
    }

    private void addObject(String str, Object obj) {
        checkCapacity(this.size + 1);
        String[] strArr = this.keys;
        int i = this.size;
        strArr[i] = str;
        this.vals[i] = obj;
        this.size = i + 1;
    }

    public Attributes put(String str, String str2) {
        Validate.notNull(str);
        int iIndexOfKey = indexOfKey(str);
        if (iIndexOfKey != -1) {
            this.vals[iIndexOfKey] = str2;
            return this;
        }
        add(str, str2);
        return this;
    }

    Map<String, Object> userData() {
        int iIndexOfKey = indexOfKey(SharedConstants.UserDataKey);
        if (iIndexOfKey == -1) {
            HashMap map = new HashMap();
            addObject(SharedConstants.UserDataKey, map);
            return map;
        }
        return (Map) this.vals[iIndexOfKey];
    }

    public Object userData(String str) {
        Validate.notNull(str);
        if (hasKey(SharedConstants.UserDataKey)) {
            return userData().get(str);
        }
        return null;
    }

    public Attributes userData(String str, Object obj) {
        Validate.notNull(str);
        userData().put(str, obj);
        return this;
    }

    void putIgnoreCase(String str, String str2) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (iIndexOfKeyIgnoreCase != -1) {
            this.vals[iIndexOfKeyIgnoreCase] = str2;
            if (this.keys[iIndexOfKeyIgnoreCase].equals(str)) {
                return;
            }
            this.keys[iIndexOfKeyIgnoreCase] = str;
            return;
        }
        add(str, str2);
    }

    public Attributes put(String str, boolean z) {
        if (z) {
            putIgnoreCase(str, null);
            return this;
        }
        remove(str);
        return this;
    }

    public Attributes put(Attribute attribute) {
        Validate.notNull(attribute);
        put(attribute.getKey(), attribute.getValue());
        attribute.parent = this;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remove(int i) {
        Validate.isFalse(i >= this.size);
        int i2 = (this.size - i) - 1;
        if (i2 > 0) {
            String[] strArr = this.keys;
            int i3 = i + 1;
            System.arraycopy(strArr, i3, strArr, i, i2);
            Object[] objArr = this.vals;
            System.arraycopy(objArr, i3, objArr, i, i2);
        }
        int i4 = this.size - 1;
        this.size = i4;
        this.keys[i4] = null;
        this.vals[i4] = null;
    }

    public void remove(String str) {
        int iIndexOfKey = indexOfKey(str);
        if (iIndexOfKey != -1) {
            remove(iIndexOfKey);
        }
    }

    public void removeIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (iIndexOfKeyIgnoreCase != -1) {
            remove(iIndexOfKeyIgnoreCase);
        }
    }

    public boolean hasKey(String str) {
        return indexOfKey(str) != -1;
    }

    public boolean hasKeyIgnoreCase(String str) {
        return indexOfKeyIgnoreCase(str) != -1;
    }

    public boolean hasDeclaredValueForKey(String str) {
        int iIndexOfKey = indexOfKey(str);
        return (iIndexOfKey == -1 || this.vals[iIndexOfKey] == null) ? false : true;
    }

    public boolean hasDeclaredValueForKeyIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        return (iIndexOfKeyIgnoreCase == -1 || this.vals[iIndexOfKeyIgnoreCase] == null) ? false : true;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() == 0) {
            return;
        }
        checkCapacity(this.size + attributes.size);
        boolean z = this.size != 0;
        for (Attribute attribute : attributes) {
            if (z) {
                put(attribute);
            } else {
                add(attribute.getKey(), attribute.getValue());
            }
        }
    }

    public Range.AttributeRange sourceRange(String str) {
        if (!hasKey(str)) {
            return Range.AttributeRange.UntrackedAttr;
        }
        Map<String, Range.AttributeRange> ranges = getRanges();
        if (ranges == null) {
            return Range.AttributeRange.UntrackedAttr;
        }
        Range.AttributeRange attributeRange = ranges.get(str);
        return attributeRange != null ? attributeRange : Range.AttributeRange.UntrackedAttr;
    }

    Map<String, Range.AttributeRange> getRanges() {
        return (Map) userData(SharedConstants.AttrRangeKey);
    }

    public Attributes sourceRange(String str, Range.AttributeRange attributeRange) {
        Validate.notNull(str);
        Validate.notNull(attributeRange);
        Map<String, Range.AttributeRange> ranges = getRanges();
        if (ranges == null) {
            ranges = new HashMap<>();
            userData(SharedConstants.AttrRangeKey, ranges);
        }
        ranges.put(str, attributeRange);
        return this;
    }

    @Override // java.lang.Iterable
    public Iterator<Attribute> iterator() {
        return new Iterator<Attribute>() { // from class: org.jsoup.nodes.Attributes.1
            static final /* synthetic */ boolean $assertionsDisabled = false;
            int expectedSize;
            int i = 0;

            {
                this.expectedSize = Attributes.this.size;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                checkModified();
                while (this.i < Attributes.this.size && Attributes.isInternalKey(Attributes.this.keys[this.i])) {
                    this.i++;
                }
                return this.i < Attributes.this.size;
            }

            @Override // java.util.Iterator
            public Attribute next() {
                checkModified();
                if (this.i >= Attributes.this.size) {
                    throw new NoSuchElementException();
                }
                Attribute attribute = new Attribute(Attributes.this.keys[this.i], (String) Attributes.this.vals[this.i], Attributes.this);
                this.i++;
                return attribute;
            }

            private void checkModified() {
                if (Attributes.this.size != this.expectedSize) {
                    throw new ConcurrentModificationException("Use Iterator#remove() instead to remove attributes while iterating.");
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                Attributes attributes = Attributes.this;
                int i = this.i - 1;
                this.i = i;
                attributes.remove(i);
                this.expectedSize--;
            }
        };
    }

    public List<Attribute> asList() {
        ArrayList arrayList = new ArrayList(this.size);
        for (int i = 0; i < this.size; i++) {
            String str = this.keys[i];
            if (!isInternalKey(str)) {
                arrayList.add(new Attribute(str, (String) this.vals[i], this));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public String html() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        html(QuietAppendable.wrap(sbBorrowBuilder), new Document.OutputSettings());
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    final void html(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        String validKey;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            String str = this.keys[i2];
            if (!isInternalKey(str) && (validKey = Attribute.getValidKey(str, outputSettings.syntax())) != null) {
                Attribute.htmlNoValidate(validKey, (String) this.vals[i2], quietAppendable.append(' '), outputSettings);
            }
        }
    }

    public String toString() {
        return html();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (this.size != attributes.size) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            int iIndexOfKey = attributes.indexOfKey(this.keys[i]);
            if (iIndexOfKey == -1 || !Objects.equals(this.vals[i], attributes.vals[iIndexOfKey])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return (((this.size * 31) + Arrays.hashCode(this.keys)) * 31) + Arrays.hashCode(this.vals);
    }

    public Attributes clone() {
        try {
            Attributes attributes = (Attributes) super.clone();
            attributes.size = this.size;
            attributes.keys = (String[]) Arrays.copyOf(this.keys, this.size);
            attributes.vals = Arrays.copyOf(this.vals, this.size);
            int iIndexOfKey = indexOfKey(SharedConstants.UserDataKey);
            if (iIndexOfKey != -1) {
                this.vals[iIndexOfKey] = new HashMap((Map) this.vals[iIndexOfKey]);
            }
            return attributes;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void normalize() {
        for (int i = 0; i < this.size; i++) {
            String str = this.keys[i];
            if (!isInternalKey(str)) {
                this.keys[i] = Normalizer.lowerCase(str);
            }
        }
    }

    public int deduplicate(ParseSettings parseSettings) {
        int i = 0;
        if (isEmpty()) {
            return 0;
        }
        boolean zPreserveAttributeCase = parseSettings.preserveAttributeCase();
        int i2 = 0;
        while (i < this.size) {
            String str = this.keys[i];
            i++;
            int i3 = i;
            while (i3 < this.size) {
                if ((zPreserveAttributeCase && str.equals(this.keys[i3])) || (!zPreserveAttributeCase && str.equalsIgnoreCase(this.keys[i3]))) {
                    i2++;
                    remove(i3);
                    i3--;
                }
                i3++;
            }
        }
        return i2;
    }

    private static class Dataset extends AbstractMap<String, String> {
        private final Attributes attributes;

        private Dataset(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public String put(String str, String str2) {
            String strDataKey = Attributes.dataKey(str);
            String str3 = this.attributes.hasKey(strDataKey) ? this.attributes.get(strDataKey) : null;
            this.attributes.put(strDataKey, str2);
            return str3;
        }

        private class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                DatasetIterator datasetIterator = new DatasetIterator();
                int i = 0;
                while (datasetIterator.hasNext()) {
                    i++;
                }
                return i;
            }
        }

        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private final Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Dataset.this.attributes.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    Attribute next = this.attrIter.next();
                    this.attr = next;
                    if (next.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Iterator
            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(Attributes.dataPrefix.length()), this.attr.getValue());
            }

            @Override // java.util.Iterator
            public void remove() {
                Dataset.this.attributes.remove(this.attr.getKey());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dataKey(String str) {
        return dataPrefix + str;
    }

    static String internalKey(String str) {
        return InternalZipConstants.ZIP_FILE_SEPARATOR + str;
    }

    static boolean isInternalKey(String str) {
        return str.length() > 1 && str.charAt(0) == '/';
    }
}
