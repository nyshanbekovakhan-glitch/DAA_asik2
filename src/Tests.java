package src;

public class Tests {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("===== TESTS =====");

        testArray();
        testList();
        testHeap();

        System.out.println();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed == 0) {
            System.out.println("All tests passed!");
        } else {
            System.out.println("Some tests failed.");
        }
    }

    private static void testArray() {
        System.out.println();
        System.out.println("--- DynamicArray ---");

        DynamicArray a = new DynamicArray();

        check("Empty", a.isEmpty());

        a.add(10);
        check("One element", a.get(0) == 10);

        a.add(0, 5);
        check("Add beginning", a.get(0) == 5);

        a.add(a.size(), 20);
        check("Add end", a.get(a.size() - 1) == 20);

        a.add(1, 7);
        check("Add middle", a.get(1) == 7);

        a.add(7);
        check("Duplicates", a.contains(7));

        check("Remove first", a.remove(0) == 5);
        check("Remove last", a.remove(a.size() - 1) == 7);

        boolean bad = false;

        try {
            a.get(-1);
        } catch (IndexOutOfBoundsException e) {
            bad = true;
        }

        check("Negative index", bad);

        bad = false;

        try {
            a.get(100);
        } catch (IndexOutOfBoundsException e) {
            bad = true;
        }

        check("Large index", bad);

        DynamicArray large = new DynamicArray();

        for (int i = 0; i < 10000; i++) {
            large.add(i);
        }

        check("Large input", large.size() == 10000);
        check("Last element", large.get(9999) == 9999);
    }

    private static void testList() {
        System.out.println();
        System.out.println("--- LinkedList ---");

        LinkedList list = new LinkedList();

        check("Empty", list.isEmpty());

        list.add(10);
        check("One element", list.get(0) == 10);

        list.add(0, 5);
        check("Add beginning", list.get(0) == 5);

        list.add(list.size(), 20);
        check("Add end", list.get(list.size() - 1) == 20);

        list.add(1, 7);
        check("Add middle", list.get(1) == 7);

        list.add(7);
        check("Duplicates", list.contains(7));

        check("Remove first", list.remove(0) == 5);
        check("Remove last", list.remove(list.size() - 1) == 7);

        boolean bad = false;

        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            bad = true;
        }

        check("Negative index", bad);

        bad = false;

        try {
            list.get(100);
        } catch (IndexOutOfBoundsException e) {
            bad = true;
        }

        check("Large index", bad);

        LinkedList large = new LinkedList();

        for (int i = 0; i < 10000; i++) {
            large.add(i);
        }

        check("Large input", large.size() == 10000);
        check("Last element", large.get(9999) == 9999);
    }

    private static void testHeap() {
        System.out.println();
        System.out.println("--- MinHeap ---");

        MinHeap heap = new MinHeap();

        check("Empty", heap.isEmpty());

        boolean bad = false;

        try {
            heap.peekMin();
        } catch (IllegalStateException e) {
            bad = true;
        }

        check("Peek empty", bad);

        heap.insert(10);

        check("One element", heap.peekMin() == 10);
        check("Size", heap.size() == 1);
        check("Extract one", heap.extractMin() == 10);
        check("Empty after extract", heap.isEmpty());

        int[] values = {5, 3, 8, 1, 2, 7, 3, 10};

        for (int x : values) {
            heap.insert(x);
        }

        check("Minimum", heap.peekMin() == 1);

        int prev = Integer.MIN_VALUE;
        boolean sorted = true;

        while (!heap.isEmpty()) {
            int current = heap.extractMin();

            if (current < prev) {
                sorted = false;
                break;
            }

            prev = current;
        }

        check("Non-decreasing", sorted);

        MinHeap large = new MinHeap();

        for (int i = 10000; i >= 1; i--) {
            large.insert(i);
        }

        prev = Integer.MIN_VALUE;
        sorted = true;

        while (!large.isEmpty()) {
            int current = large.extractMin();

            if (current < prev) {
                sorted = false;
                break;
            }

            prev = current;
        }

        check("Large heap", sorted);
    }

    private static void check(String name, boolean ok) {
        if (ok) {
            System.out.println("[PASS] " + name);
            passed++;
        } else {
            System.out.println("[FAIL] " + name);
            failed++;
        }
    }
}