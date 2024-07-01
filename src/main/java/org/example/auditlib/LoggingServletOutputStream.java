package org.example.auditlib;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;

public class LoggingServletOutputStream extends ServletOutputStream {

    private final ServletOutputStream servletOutputStream;

    private final StringBuilder stringBuilder = new StringBuilder();

    public LoggingServletOutputStream(ServletOutputStream servletOutputStream) {
        this.servletOutputStream = servletOutputStream;
    }

    @Override
    public void write(int b) throws IOException {
        servletOutputStream.write(b);
        stringBuilder.append((char) b);
    }

    @Override
    public boolean isReady() {
        return servletOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        servletOutputStream.setWriteListener(writeListener);
    }

    public String getData() {
        return stringBuilder.toString();
    }

}
