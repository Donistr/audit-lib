package org.example.auditlib;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

@Aspect
@Component
public class AuditLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogAspect.class);

    @Around("@annotation(AuditLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Level logLevel = signature.getMethod().getAnnotation(AuditLog.class).logLevel();

        Class<?> returnType = signature.getReturnType();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Parameter[] parameters = signature.getMethod().getParameters();

        StringBuilder messageBuilder = createMethodSignature(returnType, methodName, parameters);

        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            messageBuilder
                    .append("failed with error(")
                    .append(e.getClass())
                    .append("): ")
                    .append(e.getMessage());
            LOGGER.atLevel(logLevel).log(messageBuilder.toString());

            throw e;
        }

        if (signature.getReturnType() != void.class) {
            messageBuilder
                    .append("returned: ")
                    .append(proceed);
        }
        LOGGER.atLevel(logLevel).log(messageBuilder.toString());

        return proceed;
    }

    private static StringBuilder createMethodSignature(Class<?> returnType, String methodName, Parameter[] parameters) {
        StringBuilder messageBuilder = new StringBuilder()
                .append(returnType.toString())
                .append(" ")
                .append(methodName)
                .append("(");

        for (int i = 0; i < parameters.length; ++i) {
            if (i > 0) {
                messageBuilder.append(", ");
            }

            messageBuilder.append(parameters[i].toString());
        }
        messageBuilder.append(") ");

        return messageBuilder;
    }

}
