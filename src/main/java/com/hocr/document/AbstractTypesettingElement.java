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

import com.hocr.parser.Bounds;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexander
 * @param <C> child type
 * @param <P> parent type
 */
public abstract class AbstractTypesettingElement<C extends HocrElement, P extends HocrElement> extends AbstractElement<P> implements TypesettingElement<C, P>, HocrElement<C, P> {

    private String id;

    private String title;

    private Optional<HocrLanguage> language;

    private Optional<Direction> direction;

    private Bounds bounds;

    private final List<C> children;

    public AbstractTypesettingElement() {
        this.children = new LinkedList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Optional<HocrLanguage> getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(HocrLanguage language) {
        this.language = Optional.ofNullable(language);
    }

    @Override
    public Optional<Direction> getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = Optional.ofNullable(direction);
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public List<C> getChildren() {
        return this.children;
    }

    @Override
    public void addChild(C child) {
        this.children.add(child);
    }

}
