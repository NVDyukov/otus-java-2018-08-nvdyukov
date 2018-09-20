package ru.otus.meter.nodes;

import ru.otus.agent.AgentMemoryCounter;
import ru.otus.meter.visitor.NodeVisitor;

public abstract class Node {
    private final String className;
    private Object object;
    private long size;

    public Node(Object object) {
        this.object = object;
        className = this.object.getClass().getName();
        size = AgentMemoryCounter.getObjectSize(object);
    }

    public String getClassName() {
        return className;
    }

    public Object getObject() {
        return object;
    }

    public long getSize() {
        return size;
    }

    public abstract void accept(NodeVisitor nodeVisitor);

    @Override
    public String toString() {
        return "Node {" +
                "className='" + className + '\'' +
                ", object=" + object +
                ", size=" + size +
                "байт }";
    }
}
