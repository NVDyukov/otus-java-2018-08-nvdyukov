package ru.otus;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.otus.MyArrayList;

import java.util.*;

public class TestMyArrayList {
    private static List<Integer> list = Arrays.asList(10, 20, 30);

    @Test
    public void testConstructor1() {
        MyArrayList<String> myArrayList = new MyArrayList<>();
        Assert.assertEquals(myArrayList.size(), 0);
    }

    @Test
    public void testConstructor2() {
        Set<String> strings = Set.of("Первый", "Второй");
        MyArrayList<Object> objects = new MyArrayList<>(strings);
        Assert.assertEquals(objects.size(), strings.size());
        Assert.assertEquals(objects.get(0), strings.toArray()[0]);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGet() {
        MyArrayList<Integer> integers = new MyArrayList<>(list);
        integers.get(list.size());
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testSet() {
        MyArrayList<Integer> integers = new MyArrayList<>(list);
        int value = 25;
        int index = 1;
        Assert.assertEquals(integers.set(index, value), list.get(1));
        Assert.assertEquals(integers.get(index).intValue(), value);
        integers.set(list.size(), value);
    }

    @Test
    public void testAdd() {
        MyArrayList<Integer> integers = new MyArrayList<>();
        integers.add(55);
        integers.add(55);
        integers.add(55);
        Assert.assertEquals(integers.size(), 3);
    }

    @Test
    public void testCollectionAddAll() {
        MyArrayList<Integer> integers = new MyArrayList<>();
        integers.add(55);
        integers.add(55);
        integers.add(55);
        Collections.addAll(integers, 5, 25, 33, 28);
        Assert.assertEquals(integers.get(3), Integer.valueOf(5));
        Assert.assertEquals(integers.get(4), Integer.valueOf(25));
        Assert.assertEquals(integers.get(5), Integer.valueOf(33));
        Assert.assertEquals(integers.get(6), Integer.valueOf(28));
    }

    @Test
    public void testRemoveElement() {
        MyArrayList<Integer> integers = new MyArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(5);
        integers.add(7);
        integers.add(9);
        integers.remove(2);
        integers.remove(4);
        Assert.assertEquals(integers.size(), 4);
        Assert.assertEquals(integers.get(2), Integer.valueOf(5));
        Assert.assertEquals(integers.get(3), Integer.valueOf(7));
    }

    @Test
    public void testCollectionCopy1() {
        List<String> lList = new LinkedList<>(Arrays.asList("11", "12", "13", "14",
                "15", "16", "17", "18", "0", "100"));
        List<String> myArrayList = new MyArrayList<>(Arrays.asList("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10"));
        Collections.copy(lList, myArrayList);
        Assert.assertEquals(lList, myArrayList);
    }

    @Test
    public void testCollectionCopy2() {
        MyArrayList<Integer> myArrayList1 = new MyArrayList<>();
        MyArrayList<Integer> myArrayList2 = new MyArrayList<>();
        myArrayList1.add(5);
        myArrayList1.add(10);
        myArrayList2.add(11);
        myArrayList2.add(100);
        Collections.copy(myArrayList1, myArrayList2);
        Assert.assertEquals(myArrayList1.get(0), Integer.valueOf(11));
        Assert.assertEquals(myArrayList1.get(1), Integer.valueOf(100));
    }

    @Test
    public void testCollectionSort() {
        List<String> myArrayList = new MyArrayList<>();
        myArrayList.add("Б");
        myArrayList.add("В");
        myArrayList.add("А");
        Collections.sort(myArrayList, Comparator.naturalOrder());
        Assert.assertEquals(myArrayList, Arrays.asList("А", "Б", "В"));
    }
}
