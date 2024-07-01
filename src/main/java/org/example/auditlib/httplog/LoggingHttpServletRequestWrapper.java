package org.example.auditlib.httplog;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс-обёртка над {@link HttpServletRequest}, чтобы залогировать тело запроса
 */
public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final LoggingServletInputStream inputStream;

    /**
     * Конструктор, вызывает конструктор супер класса и оборачивает {@link ServletInputStream} в
     * {@link LoggingServletInputStream}
     * @param request запрос
     * @throws IOException если {@link HttpServletRequest#getInputStream} выбросил исключение
     */
    public LoggingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        inputStream = new LoggingServletInputStream(request.getInputStream());
    }

    /**
     * @return {@link ServletInputStream} обёрнутый в {@link LoggingServletInputStream}
     */
    @Override
    public ServletInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Оборачивает {@link LoggingServletInputStream} в {@link InputStreamReader}, который
     * оборачивается в {@link BufferedReader}
     * @return reader
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * @return тело запроса
     */
    public String getRequestBody() {
        return inputStream.getData();
    }

}
