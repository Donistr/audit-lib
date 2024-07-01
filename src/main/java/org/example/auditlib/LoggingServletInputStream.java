package org.example.auditlib;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.IOException;

/**
 * Класс-обёртка над {@link ServletInputStream}, чтобы можно было узнать что из него было прочитано
 */
public class LoggingServletInputStream extends ServletInputStream {

    private final ServletInputStream servletInputStream;

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Конструктор
     * @param servletInputStream input stream
     */
    public LoggingServletInputStream(ServletInputStream servletInputStream) {
        this.servletInputStream = servletInputStream;
    }

    /**
     * @return результат {@link ServletInputStream#isFinished()}
     */
    @Override
    public boolean isFinished() {
        return servletInputStream.isFinished();
    }

    /**
     * @return результат {@link ServletInputStream#isReady()}
     */
    @Override
    public boolean isReady() {
        return servletInputStream.isReady();
    }

    /**
     * Вызывает {@link ServletInputStream#setReadListener}
     * @param readListener listener
     */
    @Override
    public void setReadListener(ReadListener readListener) {
        servletInputStream.setReadListener(readListener);
    }

    /**
     * Читает значение и перед тем как вернуть его, сохраняет в {@link LoggingServletInputStream#stringBuilder}
     * @return прочитанное значение
     * @throws IOException если ошибка чтения
     */
    @Override
    public int read() throws IOException {
        int value = servletInputStream.read();
        if (value != -1) {
            stringBuilder.append((char) value);
        }

        return value;
    }

    /**
     * @return всё, что было прочитано из {@link LoggingServletInputStream#servletInputStream}
     */
    public String getData() {
        return stringBuilder.toString();
    }

}
