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

import com.hocr.document.Body;
import com.hocr.document.ChildElement;
import com.hocr.document.Foo;
import com.hocr.document.Head;
import com.hocr.document.HocrClass;
import com.hocr.document.Html;
import com.hocr.document.Meta;
import com.hocr.document.Root;
import com.hocr.document.Title;
import com.hocr.document.TypesettingElement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alexander
 */
public class HocrContentHandler extends DefaultHandler {

    private Root root;

    private ChildElement work;

    public Root getRoot() {
        return root;
    }

    @Override
    public void startDocument() throws SAXException {
        this.root = new Root();
        this.work = this.root;
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
                this.work = createDocumentElement(uri, localName, qualifiedName, attributes);
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

    private ChildElement createDocumentElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        return HocrClass.forClassName(attributes.getValue("class"))
                .map(hocrClass -> {
                    switch (hocrClass) {
                        case PAGE:
                        case AREA:
                        case PARAGRAPH:
                        case LINE:
                        case WORD:
                            return createTypesettingElement(hocrClass, uri, localName, qualifiedName, attributes);

                    }

                    return null;
                })
                .orElseThrow(() -> new IllegalArgumentException("Unknown class type: " + attributes.getValue("class")));

    }

    private ChildElement createTypesettingElement(HocrClass hocrClass, String uri, String localName, String qualifiedName, Attributes attributes) {
        try {
            TypesettingElement element = hocrClass.getType().newInstance();
            element.setId(attributes.getValue("id"));
            element.setTitle(attributes.getValue("title"));

            applyBounds(element, element.getTitle());
            applyParent(element);
            element.getParent().addChild(element);
            return element;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(HocrContentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private ChildElement createUnknown(String uri, String localName, String qualifiedName, Attributes attributes) {
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

    public <T extends ChildElement> T applyParent(T element) {
        element.setParent(this.work);
        return element;
    }

    private void applyBounds(TypesettingElement element, String hocrTitleValue) {
        Bounds bounds = Bounds.fromHocrTitleValue(hocrTitleValue)
                .orElseThrow(() -> new IllegalStateException("No bounds found in title: " + hocrTitleValue));
        element.setBounds(bounds);
    }

}
