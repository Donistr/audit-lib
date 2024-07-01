package org.example.auditlib.httplog;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;

/**
 * Класс-обёртка над классом {@link ServletOutputStream}, чтобы можно было узнать что в него было записано
 */
public class LoggingServletOutputStream extends ServletOutputStream {

    private final ServletOutputStream servletOutputStream;

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Конструктор
     * @param servletOutputStream output stream
     */
    public LoggingServletOutputStream(ServletOutputStream servletOutputStream) {
        this.servletOutputStream = servletOutputStream;
    }

    /**
     * Записывает байт в {@link LoggingServletOutputStream#servletOutputStream} и
     * в {@link LoggingServletOutputStream#stringBuilder}
     * @param b байт
     * @throws IOException если ошибка записи
     */
    @Override
    public void write(int b) throws IOException {
        servletOutputStream.write(b);
        stringBuilder.append((char) b);
    }

    /**
     * @return значение, возвращаемом {@link ServletOutputStream#isReady()}
     */
    @Override
    public boolean isReady() {
        return servletOutputStream.isReady();
    }

    /**
     * Вызывает метод {@link ServletOutputStream#setWriteListener}
     * @param writeListener listener
     */
    @Override
    public void setWriteListener(WriteListener writeListener) {
        servletOutputStream.setWriteListener(writeListener);
    }

    /**
     * @return всё, что было записано в {@link LoggingServletOutputStream#servletOutputStream}
     */
    public String getData() {
        return stringBuilder.toString();
    }

}
