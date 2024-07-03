package org.example.auditlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auditlib")
public class AuditLibBaseProperties {

    private boolean methodlogEnabled = true;

    private boolean httplogEnabled = false;

    public boolean isMethodlogEnabled() {
        return methodlogEnabled;
    }

    public void setMethodlogEnabled(boolean methodlogEnabled) {
        this.methodlogEnabled = methodlogEnabled;
    }

    public boolean isHttplogEnabled() {
        return httplogEnabled;
    }

    public void setHttplogEnabled(boolean httplogEnabled) {
        this.httplogEnabled = httplogEnabled;
    }

}
