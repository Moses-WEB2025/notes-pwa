package org.ccil.cowan.tagsoup.jaxp;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes6.dex */
public class JAXPTest {
    public static void main(String[] strArr) throws Exception {
        new JAXPTest().test(strArr);
    }

    private void test(String[] strArr) throws Exception {
        if (strArr.length != 1) {
            System.err.println("Usage: java " + getClass() + " [input-file]");
            System.exit(1);
        }
        File file = new File(strArr[0]);
        System.setProperty("javax.xml.parsers.SAXParserFactory", "org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl");
        SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
        System.out.println("Ok, SAX factory JAXP creates is: " + sAXParserFactoryNewInstance);
        System.out.println("Let's parse...");
        sAXParserFactoryNewInstance.newSAXParser().parse(file, new DefaultHandler());
        System.out.println("Done. And then DOM build:");
        System.out.println("Succesfully built DOM tree from '" + file + "', -> " + DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file));
    }
}
