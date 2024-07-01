package org.example.auditlib;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LoggingHttpServletRequestWrapper httpRequest =
                new LoggingHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        LoggingHttpServletResponseWrapper httpResponse =
                new LoggingHttpServletResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(httpRequest, httpResponse);

        String requestBody = OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readTree(httpRequest.getRequestBody()));
        String responseBody = httpResponse.getResponseBody();

        StringBuilder stringBuilder = new StringBuilder()
                .append("endpoint: ").append(httpRequest.getRequestURI()).append(" | ")
                .append("method: ").append(httpRequest.getMethod()).append(" | ")
                .append("request body: ");
        if (requestBody == null || requestBody.isEmpty()) {
            stringBuilder.append("empty | ");
        } else {
            stringBuilder.append(requestBody).append(" | ");
        }
        stringBuilder.append("status code: ").append(httpResponse.getStatus()).append(" | ");
        stringBuilder.append("responseBody: ");
        if (responseBody.isEmpty()) {
            stringBuilder.append("empty");
        } else {
            stringBuilder.append(responseBody);
        }

        LOGGER.info(stringBuilder.toString());
    }

}
