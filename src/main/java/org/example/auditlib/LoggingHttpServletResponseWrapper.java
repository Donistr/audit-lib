package org.example.auditlib;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Класс-обёртка над {@link HttpServletResponse}, чтобы залогировать тело ответа
 */
public class LoggingHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final LoggingServletOutputStream outputStream;

    /**
     * Конструктор, оборачивает {@link HttpServletResponse} в {@link LoggingServletOutputStream}
     * @param response ответ
     * @throws IOException если {@link HttpServletResponse#getOutputStream} выбросил исключение
     */
    public LoggingHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        outputStream = new LoggingServletOutputStream(response.getOutputStream());
    }

    /**
     * @return {@link ServletOutputStream} обёрнутый в {@link LoggingServletOutputStream}
     */
    @Override
    public ServletOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Оборачивает {@link LoggingServletOutputStream} в {@link OutputStreamWriter}, который
     * оборачивается в {@link PrintWriter}
     * @return writer
     */
    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(new OutputStreamWriter(outputStream));
    }

    /**
     * @return тело ответа
     */
    public String getResponseBody() {
        return outputStream.getData();
    }

}
