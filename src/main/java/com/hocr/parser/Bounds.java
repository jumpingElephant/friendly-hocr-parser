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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexander
 */
public class Bounds {

    private final int bottom;
    private final int left;
    private final int right;
    private final int top;

    private Bounds(final int bottom, final int left, final int right, final int top) {
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.top = top;
    }

    /**
     * Creates a Bounds instance from the hOCR title value, where the order of
     * the values ist x0 y0 x1 y1 = "left top right bottom".
     *
     * @param hocrTitleValue
     * @return
     */
    public static Optional<Bounds> fromHocrTitleValue1(String hocrTitleValue) {

        Pattern bboxPattern = Pattern.compile("bbox (\\d+) (\\d+) (\\d+) (\\d+);");

        final Matcher bboxMatcher = bboxPattern.matcher(hocrTitleValue);

        if (bboxMatcher.find()) {
            String bboxGroup = bboxMatcher.group();

            Matcher boundsMatcher = Pattern.compile("\\d+").matcher(bboxGroup);
            List<Integer> bboxValues = new LinkedList<>();
            while (boundsMatcher.find()) {
                bboxValues.add(Integer.valueOf(boundsMatcher.group()));
            }
            return Optional.of(Bounds.builder()
                    .left(bboxValues.get(0))
                    .top(bboxValues.get(1))
                    .right(bboxValues.get(2))
                    .bottom(bboxValues.get(3))
                    .build());

        }
        return Optional.empty();
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public static class Builder {

        private int bottom;
        private int left;
        private int right;
        private int top;

        private Builder() {
        }

        public Builder bottom(final int value) {
            this.bottom = value;
            return this;
        }

        public Builder left(final int value) {
            this.left = value;
            return this;
        }

        public Builder right(final int value) {
            this.right = value;
            return this;
        }

        public Builder top(final int value) {
            this.top = value;
            return this;
        }

        public Bounds build() {
            return new com.hocr.parser.Bounds(bottom, left, right, top);
        }
    }

    public static Bounds.Builder builder() {
        return new Bounds.Builder();
    }

}
