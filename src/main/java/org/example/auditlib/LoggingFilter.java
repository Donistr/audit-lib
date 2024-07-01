package org.example.auditlib;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LoggingHttpServletRequestWrapper httpRequest =
                new LoggingHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        LoggingHttpServletResponseWrapper httpResponse =
                new LoggingHttpServletResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(httpRequest, httpResponse);

        String requestBody = objectMapper.writeValueAsString(objectMapper.readTree(httpRequest.getRequestBody()));
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
