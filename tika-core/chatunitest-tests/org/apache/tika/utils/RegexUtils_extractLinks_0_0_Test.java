package org.apache.tika.utils;

import org.apache.tika.utils.RegexUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;

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
