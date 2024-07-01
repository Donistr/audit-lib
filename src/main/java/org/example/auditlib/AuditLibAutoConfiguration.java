package org.example.auditlib;

import org.example.auditlib.properties.AuditLibBaseProperties;
import org.example.auditlib.properties.AuditLibHttpProperties;
import org.example.auditlib.properties.AuditLibMethodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuditLibBaseProperties.class, AuditLibMethodProperties.class,
        AuditLibHttpProperties.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class AuditLibAutoConfiguration {

    private final AuditLibBaseProperties auditLibBaseProperties;

    private final AuditLibMethodProperties auditLibMethodProperties;

    private final AuditLibHttpProperties auditLibHttpProperties;

    @Autowired
    public AuditLibAutoConfiguration(AuditLibBaseProperties auditLibBaseProperties,
                                     AuditLibMethodProperties auditLibMethodProperties,
                                     AuditLibHttpProperties auditLibHttpProperties) {
        this.auditLibBaseProperties = auditLibBaseProperties;
        this.auditLibMethodProperties = auditLibMethodProperties;
        this.auditLibHttpProperties = auditLibHttpProperties;
    }

}
