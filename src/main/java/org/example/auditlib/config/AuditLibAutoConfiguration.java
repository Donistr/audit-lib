package org.example.auditlib.config;

import org.example.auditlib.properties.AuditLibBaseProperties;
import org.example.auditlib.properties.AuditLibHttpProperties;
import org.example.auditlib.properties.AuditLibMethodProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuditLibBaseProperties.class, AuditLibMethodProperties.class,
        AuditLibHttpProperties.class})
@ComponentScan(basePackages = {"org.example.auditlib.methodlog", "org.example.auditlib.httplog"})
public class AuditLibAutoConfiguration {
}
