package ru.otus.meter;

import ru.otus.meter.nodes.NodeElement;

import java.util.LinkedList;

public class ObjectParser {
    public LinkedList<NodeElement> parse(Object object) {
        NodeElement nodeElement = new NodeElement(object);
        LinkedList<NodeElement> nodeElements = new LinkedList<>();
        nodeElements.add(nodeElement);
        LinkedList<NodeElement> referenceFields = nodeElement.getReferenceFields();
        if (referenceFields != null) {
            nodeElements.addAll(referenceFields);
        }
        return nodeElements;
    }
}
