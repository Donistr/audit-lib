package org.example.auditlib.methodlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.slf4j.event.Level;

/**
 * Метод помеченный этой аннотацией начинает логироваться аспектом {@link AuditLogAspect}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditLog {

    /**
     * @return уровень логирования для данного метода
     */
    Level logLevel();

}
