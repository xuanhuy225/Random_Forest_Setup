package me.common.spring.utils;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.HashMap;
import java.util.Map;


public class MDCAsyncTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        if (contextMap == null) {
            contextMap = new HashMap<>();
        }
        try {
            MDC.setContextMap(contextMap);
            runnable.run();
        } finally {
            MDC.clear();
        }
        return runnable;
    }
}