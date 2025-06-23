package com.kitsuno.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class ApiLogConfig {

    @PostConstruct
    public void setupLogger() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%msg%n");
        encoder.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setName("KITSUNO_API");
        fileAppender.setFile("logs/KitsunoAPI.log");
        fileAppender.setEncoder(encoder);
        fileAppender.setAppend(true);
        fileAppender.start();

        ch.qos.logback.classic.Logger logger = context.getLogger("KitsunoAPI");
        logger.addAppender(fileAppender);
        logger.setAdditive(false);
        logger.setLevel(ch.qos.logback.classic.Level.INFO);
    }
}
