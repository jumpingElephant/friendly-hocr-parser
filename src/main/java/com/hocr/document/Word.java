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
package com.hocr.document;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexander
 */
public class Word extends AbstractElement<Line> implements ParentElement<Element, Line> {

    private String id;

    private String title;

    private Optional<OcrLanguage> language;

    private Optional<Direction> direction;

    private final List<Element> children;

    public Word() {
        this.children = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<OcrLanguage> getLanguage() {
        return language;
    }

    public void setLanguage(OcrLanguage language) {
        this.language = Optional.ofNullable(language);
    }

    public Optional<Direction> getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = Optional.ofNullable(direction);
    }

    @Override
    public List<Element> getChildren() {
        return children;
    }

    @Override
    public void addChild(Element child) {
        children.add(child);
    }

}
