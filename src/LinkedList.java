package src;

public class LinkedList {
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public static long comparisons = 0;
    public static long accesses = 0;

    public static void reset() {
        comparisons = 0;
        accesses = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int x) {
        Node node = new Node(x);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }

        size++;
        accesses++;
    }

    public void add(int index, int x) {
        checkInsert(index);

        if (index == size) {
            add(x);
            return;
        }

        Node node = new Node(x);

        if (index == 0) {
            node.next = head;
            head = node;

            if (tail == null) {
                tail = node;
            }
        } else {
            Node prev = head;

            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                accesses++;
            }

            node.next = prev.next;
            prev.next = node;
            accesses += 2;
        }

        size++;
    }

    public int remove(int index) {
        check(index);

        int removed;

        if (index == 0) {
            removed = head.value;
            accesses++;

            head = head.next;

            if (head == null) {
                tail = null;
            }
        } else {
            Node prev = head;

            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                accesses++;
            }

            Node node = prev.next;
            removed = node.value;
            accesses++;

            prev.next = node.next;
            accesses++;

            if (prev.next == null) {
                tail = prev;
            }
        }

        size--;
        return removed;
    }

    public int get(int index) {
        check(index);

        Node cur = head;

        for (int i = 0; i < index; i++) {
            cur = cur.next;
            accesses++;
        }

        accesses++;
        return cur.value;
    }

    public boolean contains(int x) {
        Node cur = head;

        while (cur != null) {
            comparisons++;
            accesses++;

            if (cur.value == x) {
                return true;
            }

            cur = cur.next;
        }

        return false;
    }

    private void check(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "index " + index + ", size " + size
            );
        }
    }

    private void checkInsert(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "index " + index + ", size " + size
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node cur = head;

        while (cur != null) {
            sb.append(cur.value);

            if (cur.next != null) {
                sb.append(", ");
            }

            cur = cur.next;
        }

        return sb.append("]").toString();
    }
}