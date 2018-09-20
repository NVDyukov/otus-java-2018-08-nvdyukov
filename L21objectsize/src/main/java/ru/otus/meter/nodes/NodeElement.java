package ru.otus.meter.nodes;

import ru.otus.meter.visitor.NodeVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class NodeElement extends Node {

    public NodeElement(Object object) {
        super(object);
    }

    @Override
    public void accept(NodeVisitor nodeVisitor) {
        nodeVisitor.visitSimpleNode(this);
    }

    public LinkedList<NodeElement> getReferenceFields() {
        LinkedList<NodeElement> nodes = new LinkedList<>();
        Object o = this.getObject();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field e : declaredFields) {
            if (!e.getType().isPrimitive() && !Modifier.isStatic(e.getModifiers())) {
                e.setAccessible(true);
                try {
                    Object objectField = e.get(o);
                    nodes.add(new NodeElement(objectField));
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return nodes.isEmpty() ? null : nodes;
    }
}
