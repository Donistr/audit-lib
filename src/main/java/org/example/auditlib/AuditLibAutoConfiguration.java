package org.example.auditlib;

import org.example.auditlib.properties.AuditLibBaseProperties;
import org.example.auditlib.properties.AuditLibHttpProperties;
import org.example.auditlib.properties.AuditLibMethodProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuditLibBaseProperties.class, AuditLibMethodProperties.class,
        AuditLibHttpProperties.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class AuditLibAutoConfiguration {
}
