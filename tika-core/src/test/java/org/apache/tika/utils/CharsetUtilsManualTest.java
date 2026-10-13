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
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class CharsetUtilsManualTest {

    @Test
    void testICU4JCharsetSupport() {
        String charsetName = "UTF-8";

        assertTrue(CharsetUtils.isSupported(charsetName),
                "UTF-8 doit être reconnu comme un encodage valide");

        Charset charset = CharsetUtils.forName(charsetName);

        assertNotNull(charset,
                "forName doit retourner un Charset valide");
        assertEquals("UTF-8", charset.name());
    }

    
    @Test
    void testIsSupportedWhenICUInvocationFails() throws Exception {
        java.lang.reflect.Field field =
                CharsetUtils.class.getDeclaredField("isSupportedICU");
        field.setAccessible(true);

        java.lang.reflect.Method original =
                (java.lang.reflect.Method) field.get(null);

        try {
            java.lang.reflect.Method failingMethod =
                    String.class.getMethod("substring", int.class);

            field.set(null, failingMethod);

            assertFalse(CharsetUtils.isSupported("UTF-8"),
                    "Une erreur lors de l'appel ICU doit retourner false");
        } finally {
            field.set(null, original);
        }
    }

    
    @Test
    void testIsSupportedWithICU() throws Exception {
        java.lang.reflect.Field field =
                CharsetUtils.class.getDeclaredField("isSupportedICU");
        field.setAccessible(true);

        java.lang.reflect.Method original =
                (java.lang.reflect.Method) field.get(null);

        try {
            java.lang.reflect.Method mockMethod =
                    CharsetUtilsManualTest.class.getDeclaredMethod(
                            "simulatedICUSupport", String.class);

            field.set(null, mockMethod);

            assertTrue(CharsetUtils.isSupported("X-ICU-ONLY"),
                    "Un encodage reconnu par ICU doit être accepté");
        } finally {
            field.set(null, original);
        }
    }

    public static boolean simulatedICUSupport(String name) {
        return "X-ICU-ONLY".equals(name);
    }

    
    @Test
    void testForNameWithICU() throws Exception {
        java.lang.reflect.Field field =
                CharsetUtils.class.getDeclaredField("getCharsetICU");
        field.setAccessible(true);

        java.lang.reflect.Method original =
                (java.lang.reflect.Method) field.get(null);

        try {
            java.lang.reflect.Method simulatedMethod =
                    CharsetUtilsManualTest.class.getDeclaredMethod(
                            "simulatedICUForName", String.class);

            field.set(null, simulatedMethod);

            Charset result = CharsetUtils.forName("X-ICU-ONLY");

            assertNotNull(result);
            assertEquals("UTF-8", result.name());
        } finally {
            field.set(null, original);
        }
    }

    public static Charset simulatedICUForName(String name) {
        if ("X-ICU-ONLY".equals(name)) {
            return java.nio.charset.StandardCharsets.UTF_8;
        }
        return null;
    }


}
