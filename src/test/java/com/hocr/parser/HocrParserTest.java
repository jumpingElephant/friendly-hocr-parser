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

import com.hocr.document.Foo;
import com.hocr.document.Root;
import com.hocr.document.Word;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author alexander
 */
public class HocrParserTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testParse() throws Exception {

        // GIVEN
        File file = new File("src/main/resources/hocr6050804050300312430.xhtml");
        InputStream inputStream = new FileInputStream(file);

        HocrParser instance = new HocrParser();

        // WHEN
        Root actual = instance.parse(inputStream);

        // THEN
        Word word_1_24 = actual.getHtml().getBody().getChild(0).getChild(0).getChild(1).getChild(3).getChild(3);
        assertEquals("word_1_24", word_1_24.getId());

        assertEquals(1, word_1_24.getChildren().size());
        Foo actualFoo = (Foo) word_1_24.getChildren().get(0);
        assertEquals("strong", actualFoo.getQualifiedName());

    }

}
