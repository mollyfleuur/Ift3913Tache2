package org.apache.tika.utils;

import org.apache.tika.utils.ConcurrentUtils;
import org.apache.tika.parser.ParseContext;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class ConcurrentUtils_execute_0_2_Test {

    @Mock
    private ExecutorService executorService;

    @Mock
    private ParseContext context;

    @Mock
    private Future future;

    @Mock
    private FutureTask futureTask;

    @Mock
    private Runnable runnable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithNullExecutorService() {
        when(context.get(ExecutorService.class)).thenReturn(null);
        Future result = ConcurrentUtils.execute(context, runnable);
        assertEquals(futureTask, result);
        assertEquals(Thread.currentThread().getName(), "Tika Thread");
    }

    @Test
    void testExecuteWithNonNullExecutorService() {
        when(context.get(ExecutorService.class)).thenReturn(executorService);
        Future result = ConcurrentUtils.execute(context, runnable);
        assertEquals(future, result);
    }
}
