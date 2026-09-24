package src;

public class DynamicArray {
    private int[] data;
    private int size;
    private static final int CAP = 8;

    public static long comparisons = 0;
    public static long accesses = 0;

    public DynamicArray() {
        this(CAP);
    }
    public DynamicArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        data = new int[capacity];
        size = 0;
    }
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
        resize(size + 1);
        data[size] = x;
        accesses++;
        size++;
    }
    public void add(int index, int x) {
        checkInsert(index);
        resize(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            accesses += 2;
        }

        data[index] = x;
        accesses++;
        size++;
    }

    public int remove(int index) {
        check(index);

        int removed = data[index];
        accesses++;

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            accesses += 2;
        }

        size--;
        data[size] = 0;
        accesses++;

        return removed;
    }

    public int get(int index) {
        check(index);
        accesses++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            comparisons++;
            accesses++;

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    private void resize(int min) {
        if (min <= data.length) {
            return;
        }

        int newCap = Math.max(min, data.length * 2);
        int[] newData = new int[newCap];

        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
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

        for (int i = 0; i < size; i++) {
            sb.append(data[i]);

            if (i < size - 1) {
                sb.append(", ");
            }
        }

        return sb.append("]").toString();
    }
}