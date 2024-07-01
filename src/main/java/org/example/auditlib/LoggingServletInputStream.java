package org.example.auditlib;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.IOException;

public class LoggingServletInputStream extends ServletInputStream {

    private final ServletInputStream servletInputStream;

    private final StringBuilder stringBuilder = new StringBuilder();

    public LoggingServletInputStream(ServletInputStream servletInputStream) {
        this.servletInputStream = servletInputStream;
    }

    @Override
    public boolean isFinished() {
        return servletInputStream.isFinished();
    }

    @Override
    public boolean isReady() {
        return servletInputStream.isReady();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        servletInputStream.setReadListener(readListener);
    }

    @Override
    public int read() throws IOException {
        int value = servletInputStream.read();
        if (value != -1) {
            stringBuilder.append((char) value);
        }

        return value;
    }

    public String getData() {
        return stringBuilder.toString();
    }

}
