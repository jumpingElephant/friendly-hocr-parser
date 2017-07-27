/*
 * The MIT License
 *
 * Copyright 2017 alexander.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.hocr.parser;

import com.hocr.document.Area;
import com.hocr.document.Body;
import com.hocr.document.Direction;
import com.hocr.document.Element;
import com.hocr.document.Foo;
import com.hocr.document.Head;
import com.hocr.document.Html;
import com.hocr.document.Line;
import com.hocr.document.Meta;
import com.hocr.document.OcrClass;
import com.hocr.document.OcrLanguage;
import com.hocr.document.Page;
import com.hocr.document.Paragraph;
import com.hocr.document.Root;
import com.hocr.document.Title;
import com.hocr.document.Word;
import java.util.stream.IntStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alexander
 */
public class OcrContentHandler extends DefaultHandler {

    private Root root;

    private Element work;

    public Root getRoot() {
        return root;
    }

    @Override
    public void startDocument() throws SAXException {
        this.root = new Root();
        this.work = this.root;
        applyParent(root);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {
        switch (qualifiedName) {
            case "html":
                this.work = createHtmlElement(uri, localName, qualifiedName, attributes);
                break;

            case "head":
                this.work = createHeadElement(uri, localName, qualifiedName, attributes);
                break;

            case "title":
                this.work = createTitleElement(uri, localName, qualifiedName, attributes);
                break;

            case "meta":
                this.work = createMetaElement(uri, localName, qualifiedName, attributes);
                break;

            case "body":
                this.work = createBodyElement(uri, localName, qualifiedName, attributes);
                break;

            case "div":
            case "p":
            case "span":
                this.work = createTypesettingElement(uri, localName, qualifiedName, attributes);
                break;

            default:
                this.work = createUnknown(uri, localName, qualifiedName, attributes);
        }
    }

    private Html createHtmlElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Html html = new Html();

        applyParent(html);
        html.getParent().setHtml(html);
        return html;
    }

    private Head createHeadElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Head head = new Head();

        applyParent(head);
        Html html = head.getParent();
        html.setHead(head);
        return head;
    }

    private Title createTitleElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Title title = new Title();

        applyParent(title);
        Head head = title.getParent();
        head.setTitle(title);
        return title;
    }

    private Meta createMetaElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Meta meta = new Meta();
        IntStream.range(0, attributes.getLength())
                .forEach(i -> meta.addAttribute(attributes.getQName(i), attributes.getValue(i)));

        applyParent(meta);
        Head head = meta.getParent();
        head.addMeta(meta);
        return meta;
    }

    private Body createBodyElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Body body = new Body();

        applyParent(body);
        body.getParent().setBody(body);
        return body;
    }

    private Element createTypesettingElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        return OcrClass.forClassName(attributes.getValue("class"))
                .map(ocrClass -> {
                    switch (ocrClass) {
                        case PAGE:
                            return createPageElement(uri, localName, qualifiedName, attributes);

                        case AREA:
                            return createAreaElement(uri, localName, qualifiedName, attributes);

                        case PARAGRAPH:
                            return createParagraphElement(uri, localName, qualifiedName, attributes);

                        case LINE:
                            return createLineElement(uri, localName, qualifiedName, attributes);

                        case WORD:
                            return createWordElement(uri, localName, qualifiedName, attributes);

                    }
                    return null;
                })
                .orElseThrow(() -> new IllegalArgumentException("Unknown class type: " + attributes.getValue("class")));

    }

    private Page createPageElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Page ocrPage = new Page();
        ocrPage.setId(attributes.getValue("id"));
        ocrPage.setTitle(attributes.getValue("title"));

        applyParent(ocrPage);
        ocrPage.getParent().addPage(ocrPage);
        return ocrPage;
    }

    private Area createAreaElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Area area = new Area();
        area.setId(attributes.getValue("id"));
        area.setTitle(attributes.getValue("title"));

        applyParent(area);
        area.getParent().addChild(area);
        return area;
    }

    private Element createParagraphElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Paragraph paragraph = new Paragraph();
        paragraph.setId(attributes.getValue("id"));
        paragraph.setTitle(attributes.getValue("title"));
        Direction.forKey(attributes.getValue("dir")).ifPresent(paragraph::setDirection);

        applyParent(paragraph);
        paragraph.getParent().addChild(paragraph);
        return paragraph;
    }

    private Element createLineElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Line line = new Line();
        line.setId(attributes.getValue("id"));
        line.setTitle(attributes.getValue("title"));

        applyParent(line);
        line.getParent().addChild(line);
        return line;
    }

    private Element createWordElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        Word word = new Word();
        word.setId(attributes.getValue("id"));
        word.setTitle(attributes.getValue("title"));
        OcrLanguage.forKey(attributes.getValue("lang")).ifPresent(word::setLanguage);
        Direction.forKey(attributes.getValue("dir")).ifPresent(word::setDirection);

        applyParent(word);
        word.getParent().addChild(word);
        return word;
    }

    private Element createUnknown(String uri, String localName, String qualifiedName, Attributes attributes) {
        Foo foo = new Foo();
        foo.setQualifiedName(qualifiedName);
        IntStream.range(0, attributes.getLength())
                .forEach(i -> foo.addAttribute(attributes.getQName(i), attributes.getValue(i)));

        applyParent(foo);
        foo.getParent().addChild(foo);
        return foo;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.work = this.work.getParent();
    }

    @Override
    public void characters(char[] characters, int start, int length) throws SAXException {
        String value = new String(characters, start, length).trim();
        this.work.setValue(value);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e); //To change body of generated methods, choose Tools | Templates.
    }

    public <T extends Element> T applyParent(T element) {
        element.setParent(this.work);
        return element;
    }

}
