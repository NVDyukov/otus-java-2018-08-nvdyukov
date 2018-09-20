package ru.otus.meter.visitor;

import ru.otus.meter.nodes.NodeElement;

public class SizeVisitor implements NodeVisitor {
    private long sizeObject;

    @Override
    public void visitSimpleNode(NodeElement simpleNode) {
        sizeObject += simpleNode.getSize();
    }

    public long getSizeObject() {
        return sizeObject;
    }

    public void setToZeroSize() {
        sizeObject = 0;
    }
}
