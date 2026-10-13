/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

import org.junit.jupiter.api.Test;

public class CharsetUtilsChatUniTest {
    // Test généré par ChatUniTest pour la méthode isSupported()
    @Test
    public void testIsSupported() {
        assertTrue(CharsetUtils.isSupported("UTF-8"));
        assertFalse(CharsetUtils.isSupported("invalid-charset"));
        assertFalse(CharsetUtils.isSupported(null));
    }
    // Tests générés par ChatUniTest pour la méthode clean()
    @Test
    public void testCleanValidCharset() {
        assertEquals("UTF-8", CharsetUtils.clean("UTF-8"));
    }

    @Test
    public void testCleanInvalidCharset() {
        assertNull(CharsetUtils.clean("invalid"));
    }

    @Test
    public void testCleanNullCharset() {
        assertNull(CharsetUtils.clean(null));
    }

    // Test générés par ChatUniTest pour la méthode forName()
    @Test
    public void testForName() {
        Charset charset = CharsetUtils.forName("UTF-8");
        assertEquals("UTF-8", charset.name());

        charset = CharsetUtils.forName("ISO-8859-1");
        assertEquals("ISO-8859-1", charset.name());

        charset = CharsetUtils.forName("IBM850");
        assertEquals("IBM850", charset.name());

        charset = CharsetUtils.forName("windows-1252");
        assertEquals("windows-1252", charset.name());

        assertThrows(IllegalArgumentException.class,
                () -> CharsetUtils.forName(null));

        assertThrows(IllegalCharsetNameException.class,
                () -> CharsetUtils.forName("none"));

        assertThrows(IllegalCharsetNameException.class,
                () -> CharsetUtils.forName("no"));
    }
}
