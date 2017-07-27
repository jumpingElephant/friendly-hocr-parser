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

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author alexander
 */
public enum OcrClass {
    PAGE("ocr_page", Page.class),
    AREA("ocr_carea", Area.class),
    PARAGRAPH("ocr_par", Paragraph.class),
    LINE("ocr_line", Line.class),
    WORD("ocrx_word", Word.class);

    private final String className;
    private final Class<? extends Element> type;

    private OcrClass(String className, Class<? extends Element> type) {
        this.className = className;
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public Class<? extends Element> getType() {
        return type;
    }

    public static Optional<OcrClass> forClassName(String className) {
        return Arrays.asList(OcrClass.values()).stream()
                .filter(ocrClass -> ocrClass.getClassName().equals(className))
                .findAny();
    }

}
