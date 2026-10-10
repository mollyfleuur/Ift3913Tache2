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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RegexUtilsManualTest {

    @Test
    public void testExtractLinksGardeRequeteEncodageEtFragment() {
        List<String> liens = RegexUtils.extractLinks(
                "voir https://tika.apache.org/docs/a%20b?x=1&y=2#section-3 fin");
        assertEquals(Collections.singletonList("https://tika.apache.org/docs/a%20b?x=1&y=2#section-3"), liens);
    }

    @Test
    public void testExtractLinksArreteUrlSurUnEncodageInvalide() {
        List<String> liens = RegexUtils.extractLinks("http://exemple.com/%zz");
        assertEquals(Collections.singletonList("http://exemple.com/"), liens);
    }

    @Test
    public void testExtractLinksMajusculesEtPlusieursLignes() {
        List<String> liens = RegexUtils.extractLinks(
                "ligne 1\nHTTP://A.EXEMPLE.COM\nftp://b.exemple.org/f.txt");
        assertEquals(Arrays.asList("HTTP://A.EXEMPLE.COM", "ftp://b.exemple.org/f.txt"), liens);
    }

    @Test
    public void testExtractLinksGardeLePointFinal() {
        List<String> liens = RegexUtils.extractLinks("Voir http://exemple.com.");
        assertEquals(Collections.singletonList("http://exemple.com."), liens);
    }

    @Test
    public void testExtractLinksAccepteToutSchema() {
        List<String> liens = RegexUtils.extractLinks(
                "Ecrire a mailto:dev@tika.apache.org. Attention:danger");
        assertEquals(Arrays.asList("mailto:dev@tika.apache.org.", "Attention:danger"), liens);
    }
}
