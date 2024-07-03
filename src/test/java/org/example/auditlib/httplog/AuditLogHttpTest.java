package org.example.auditlib.httplog;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuditLogHttpTest.TestController.class, LoggingFilter.class})
@WebMvcTest(AuditLogHttpTest.TestController.class)
@AutoConfigureMockMvc
public class AuditLogHttpTest {

    @Autowired
    MockMvc mockMvc;

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeEach
    public void setListAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(LoggingFilter.class).addAppender(listAppender);
        listAppender.start();
    }

    @AfterEach
    public void clearListAppender() {
        listAppender.list.clear();
    }

    @RestController
    public static class TestController {

        @GetMapping("/get")
        public String get() {
            return "nice";
        }

        @PostMapping("/post")
        public String post(@RequestBody String body) {
            return "nice";
        }

    }

    @Test
    public void testHttpLoggingGetRequest() throws Exception {
        mockMvc.perform(get("/get"));

        String expected = "endpoint: /get | method: GET | request body: empty | status code: 200 | responseBody: nice";
        assertEquals(expected, getLastLogMessage());
    }

    @Test
    public void testHttpLoggingPOSTRequest() throws Exception {
        mockMvc.perform(post("/post")
                .content("{\"key\":\"value\"}"));

        String expected = "endpoint: /post | method: POST | request body: {\"key\":\"value\"} | status code: 200 | responseBody: nice";
        assertEquals(expected, getLastLogMessage());
    }

    private String getLastLogMessage() {
        return listAppender.list.get(listAppender.list.size() - 1).getFormattedMessage();
    }

}
