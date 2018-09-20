package ru.otus.meter.visitor;

import ru.otus.meter.nodes.NodeElement;

public interface NodeVisitor {
    public void visitSimpleNode(NodeElement simpleNode);
}
