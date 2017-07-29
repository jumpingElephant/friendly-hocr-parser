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

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author alexander
 */
public class BoundsTest {

    public BoundsTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testFromHocrTitleValue_page_1() {

        // GIVEN
        String ocrx_wordTitleValue = "image \"src/main/resources/output.png\"; bbox 0 0 5114 7171; ppageno 0";

        // WHEN
        Bounds actual = Bounds.fromHocrTitleValue(ocrx_wordTitleValue).get();

        // THEN
        assertEquals(0, actual.getLeft());
        assertEquals(0, actual.getTop());
        assertEquals(5114, actual.getRight());
        assertEquals(7171, actual.getBottom());

    }

    @Test
    public void testFromHocrTitleValue_block_1_1() {

        // GIVEN
        String ocrx_wordTitleValue = "bbox 0 0 5114 7171";

        // WHEN
        Bounds actual = Bounds.fromHocrTitleValue(ocrx_wordTitleValue).get();

        // THEN
        assertEquals(0, actual.getLeft());
        assertEquals(0, actual.getTop());
        assertEquals(5114, actual.getRight());
        assertEquals(7171, actual.getBottom());

    }

    @Test
    public void testFromHocrTitleValue_line_1_2() {

        // GIVEN
        String ocrx_wordTitleValue = "bbox 263 227 980 301; baseline -0.001 -15";

        // WHEN
        Bounds actual = Bounds.fromHocrTitleValue(ocrx_wordTitleValue).get();

        // THEN
        assertEquals(263, actual.getLeft());
        assertEquals(227, actual.getTop());
        assertEquals(980, actual.getRight());
        assertEquals(301, actual.getBottom());

    }

}
