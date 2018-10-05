package ru.otus;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayList<E> implements List<E>, RandomAccess {
    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final int DEFAULT_CAPACITY = 5;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elements;

    private int size;

    public MyArrayList() {
        this.elements = EMPTY_ELEMENTDATA;
    }

    public MyArrayList(Collection<? extends E> collection) {
        elements = collection.toArray(EMPTY_ELEMENTDATA);
        if ((size = elements.length) == 0) {
            elements = EMPTY_ELEMENTDATA;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {
        return new MyIterator();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
        add(e, elements);
        return true;
    }

    private void add(E e, Object[] objects) {
        if (size == objects.length)
            elements = increase();
        elements[size] = e;
        size++;
    }

    private Object[] increase() {
        return Arrays.copyOf(elements, newCapacity(size + 1));
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elements == EMPTY_ELEMENTDATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) {
                throw new OutOfMemoryError();
            }
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }

    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    public E remove(int index) {
        Objects.checkIndex(index, size);
        Object oldElement = elements[index];
        size--;
        if (size > index)
            System.arraycopy(elements, index + 1, elements, index, size - index);
        elements[size] = null;
        return (E) oldElement;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class MyIterator implements Iterator<E> {
        int currentElement;
        int previousElement = -1;

        @Override
        public boolean hasNext() {
            return currentElement != size;
        }

        @Override
        public E next() {
            if (currentElement >= size) {
                throw new NoSuchElementException();
            }
            Object[] elements = MyArrayList.this.elements;
            return (E) MyArrayList.this.elements[previousElement = currentElement++];
        }

        @Override
        public void remove() {
            if (previousElement < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(previousElement);
            currentElement = previousElement;
            previousElement = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) elements, 0, size, c);
    }

    private class MyListIterator extends MyIterator implements ListIterator<E> {

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            if (previousElement < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.set(previousElement, e);
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
