package ua.com.alevel.hw2.list;

import ua.com.alevel.hw2.model.Plane;
import java.util.*;
import java.util.function.Consumer;

public class MyLinkedList<E extends Plane> implements Iterable<E> {

    private int size;
    private Set<Integer> versions;

    private Node head;
    private Node tail;

    private class Node {
        public E plane;
        public int version;
        public Date date;

        public Node next;
        public Node previous;

        public Node(E plane, int version, Date date, Node next, Node previous) {
            this.plane = plane;
            this.version = version;
            this.date = date;
            this.next = next;
            this.previous = previous;
        }
    }

    public MyLinkedList() {
        size = 0;
        versions = new HashSet<>();
    }

    public void addHead(E plane, int version) {
        Node current = new Node(plane, version, new Date(), null, null);

        if (head == null) {
            head = tail = current;
        }
        else {
            current.next = head;
            head.previous = current;
            head = current;
        }

        size++;
        versions.add(current.version);
    }

    public Optional<E> findByVersion(int version) {
        if (isExistVersion(version) && size != 0) {
            for (Node temp = head; temp != null; temp = temp.next) {
                if (temp.version == version) {
                    return Optional.of(temp.plane);
                }
            }
        }

        return Optional.empty();
    }

    public boolean deleteByVersion(int version) {
        if (isExistVersion(version) && size != 0) {
            for (Node temp = head; temp != null; temp = temp.next) {
                if (temp.version == version) {
                    if (temp.previous == null && temp.next != null) {
                        head = head.next;
                        head.previous = null;
                    }
                    else if (temp.previous == null && temp.next == null) {
                        head = tail = null;
                    }
                    else if (temp.previous != null && temp.next == null) {
                        tail = tail.previous;
                        tail.next = null;
                    }
                    else {
                        Node tempPrev = temp;
                        tempPrev.previous.next = temp.next;
                        tempPrev.next.previous = temp.previous;
                    }

                    versions.remove(version);
                    size--;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean setByVersion(E plane, int version) {
        if (isExistVersion(version) && size != 0) {
            for (Node temp = head; temp != null; temp = temp.next) {
                if (temp.version == version) {
                    temp.plane = plane;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isExistVersion(int version) {
        return versions.contains(version);
    }

    public int getSize() {
        return size;
    }

    public Optional<Date> getFirstDateVersion() {
        if (tail != null) {
            return Optional.of(tail.date);
        }

        return Optional.empty();
    }

    public Optional<Date> getLastDateVersion() {
        if (head != null) {
            return Optional.of(head.date);
        }

        return Optional.empty();
    }

    public int getVersionValue() {
        return versions.size();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorForLinkedList();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    private class IteratorForLinkedList implements Iterator<E> {
        private int index;
        private Node next;

        public IteratorForLinkedList() {
            index = 0;
            next = head;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (hasNext()) {
                Node temp = next;
                next = next.next;
                index++;
                return temp.plane;
            }

            throw new NoSuchElementException();
        }
    }
}
