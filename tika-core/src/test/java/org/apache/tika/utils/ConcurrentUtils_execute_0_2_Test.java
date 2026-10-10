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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.apache.tika.parser.ParseContext;

class ConcurrentUtils_execute_0_2_Test {

    @Mock
    private ExecutorService executorService;

    @Mock
    private ParseContext context;

    @Mock
    private Future future;

    @Mock
    private Runnable runnable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithNullExecutorService() throws Exception {
        when(context.get(ExecutorService.class)).thenReturn(null);
        Future result = ConcurrentUtils.execute(context, runnable);
        assertTrue(result instanceof FutureTask);
        assertNull(result.get(5, TimeUnit.SECONDS));
        verify(runnable, timeout(5000)).run();
    }

    @Test
    void testExecuteWithNonNullExecutorService() {
        when(context.get(ExecutorService.class)).thenReturn(executorService);
        when(executorService.submit(runnable)).thenReturn(future);
        Future result = ConcurrentUtils.execute(context, runnable);
        assertEquals(future, result);
    }
}
