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

public class RegexUtils_extractLinks_0_0_Test {

    @Test
    public void testExtractLinks_nullContent_returnsEmptyList() {
        List<String> result = RegexUtils.extractLinks(null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testExtractLinks_emptyContent_returnsEmptyList() {
        List<String> result = RegexUtils.extractLinks("");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testExtractLinks_validContent_returnsCorrectLinks() {
        String content = "This is a test with a link http://example.com and another link https://example.org";
        List<String> expected = Arrays.asList("http://example.com", "https://example.org");
        List<String> result = RegexUtils.extractLinks(content);
        assertEquals(expected, result);
    }
}
