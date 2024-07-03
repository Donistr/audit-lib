package org.example.auditlib.methodlog;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuditLogAspectTest {

    @InjectMocks
    AuditLogAspect loggingAspect;

    @Mock
    AuditLog auditLog;

    @Mock
    ProceedingJoinPoint joinPoint;

    @Mock
    MethodSignature signature;

    @Mock
    Method method;

    @Mock
    Parameter parameter1;

    @Mock
    Parameter parameter2;

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeEach
    public void mockObjects() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(auditLog.logLevel()).thenReturn(Level.INFO);
        Mockito.when(signature.getReturnType()).thenReturn(int.class);
        Mockito.when(signature.getDeclaringTypeName()).thenReturn("a.b.c.TestClass");
        Mockito.when(signature.getName()).thenReturn("testMethodName");
        Mockito.when(signature.getMethod()).thenReturn(method);
        Mockito.when(method.getParameters()).thenReturn(new Parameter[]{parameter1, parameter2});

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(AuditLogAspect.class).addAppender(listAppender);
        listAppender.start();
    }

    @AfterEach
    public void clearListAppender() {
        listAppender.list.clear();
    }

    @Test
    public void testMethodLoggingReturnValue() throws Throwable {
        Mockito.when(joinPoint.proceed()).thenReturn(1234);

        loggingAspect.logExecutionTime(joinPoint, auditLog);

        String expected = "int a.b.c.TestClass.testMethodName("
                +parameter1.toString()+", "+parameter2.toString()+") returned: 1234";
        assertEquals(expected, getLastLogMessage());
    }

    @Test
    public void testMethodLoggingThrowException() throws Throwable {
        Mockito.when(joinPoint.proceed()).thenThrow(new RuntimeException("message"));

        String expected = "int a.b.c.TestClass.testMethodName("
                +parameter1.toString()+", "+parameter2.toString()+") failed with error("+RuntimeException.class+
                "): message";

        Assertions.assertThrows(RuntimeException.class, () -> loggingAspect.logExecutionTime(joinPoint, auditLog));
        Assertions.assertEquals(expected, getLastLogMessage());
    }

    private String getLastLogMessage() {
        return listAppender.list.get(listAppender.list.size() - 1).getFormattedMessage();
    }

}
