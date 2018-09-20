package ru.otus.agent;

import java.lang.instrument.Instrumentation;

public class AgentMemoryCounter {
    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        AgentMemoryCounter.instrumentation = inst;
    }

    public static long getObjectSize(Object object) {
        if (instrumentation == null) {
            throw new IllegalStateException("Агент не инициализирован");
        }
        return instrumentation.getObjectSize(object);
    }
}
