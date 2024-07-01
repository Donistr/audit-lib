package org.example.auditlib;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LoggingHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final LoggingServletOutputStream outputStream;

    public LoggingHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        outputStream = new LoggingServletOutputStream(response.getOutputStream());
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(new OutputStreamWriter(outputStream));
    }

    public String getResponseBody() {
        return outputStream.getData();
    }

}
