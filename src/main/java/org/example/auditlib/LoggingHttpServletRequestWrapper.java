package org.example.auditlib;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final LoggingServletInputStream inputStream;

    public LoggingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        inputStream = new LoggingServletInputStream(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public String getRequestBody() {
        return inputStream.getData();
    }

}
