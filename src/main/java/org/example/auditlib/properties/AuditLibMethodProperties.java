package org.example.auditlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auditlib.methodlog")
public class AuditLibMethodProperties {

    private boolean consoleEnabled;

    private boolean fileEnabled;

    private String filepath;

    public boolean isConsoleEnabled() {
        return consoleEnabled;
    }

    public void setConsoleEnabled(boolean consoleEnabled) {
        this.consoleEnabled = consoleEnabled;
    }

    public boolean isFileEnabled() {
        return fileEnabled;
    }

    public void setFileEnabled(boolean fileEnabled) {
        this.fileEnabled = fileEnabled;
    }

    public String getMethodlogFilePath() {
        return filepath;
    }

    public void setMethodlogFilePath(String methodlogFilePath) {
        this.filepath = methodlogFilePath;
    }

}
