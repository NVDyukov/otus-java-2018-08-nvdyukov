package ru.otus;

import ru.otus.meter.ObjectParser;
import ru.otus.meter.nodes.NodeElement;
import ru.otus.meter.visitor.SizeVisitor;

import java.util.LinkedList;

// java -XX:-UseCompressedOops --illegal-access=warn -javaagent:target\L21objectsize.jar -jar target\L21objectsize.jar

public class Run {
    public static void main(String[] args) {
        Object object = new Object();
        String str = "";
        int[] ints = new int[100];
        ObjectParser objectParser = new ObjectParser();
        SizeVisitor sizeVisitor = new SizeVisitor();
        LinkedList<NodeElement> parse = objectParser.parse(str);
        print(parse, sizeVisitor);
        parse = objectParser.parse(object);
        print(parse, sizeVisitor);
        parse = objectParser.parse(ints);
        print(parse, sizeVisitor);
    }

    private static void print(LinkedList<NodeElement> parse, SizeVisitor sizeVisitor) {
        System.out.println();
        System.out.println("-------------------------------------");
        for (NodeElement e : parse) {
            e.accept(sizeVisitor);
            System.out.println(e.getClassName());
            System.out.println(e.getSize());
        }
        System.out.println("-------------------------------------");
        System.out.println("ИТОГО: " + sizeVisitor.getSizeObject() + " байт");
        sizeVisitor.setToZeroSize();
    }
}
