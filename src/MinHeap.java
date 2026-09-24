package src;

public class MinHeap {
    private int[] data;
    private int size;
    private static final int CAP = 8;

    public static long comparisons = 0;
    public static long insertComparisons = 0;
    public static long extractComparisons = 0;

    public MinHeap() {
        this(CAP);
    }

    public MinHeap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        data = new int[capacity];
        size = 0;
    }

    public static void reset() {
        comparisons = 0;
        insertComparisons = 0;
        extractComparisons = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int x) {
        resize(size + 1);
        data[size] = x;
        size++;
        up(size - 1);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = data[0];

        size--;
        data[0] = data[size];
        data[size] = 0;

        if (size > 0) {
            down(0);
        }

        return min;
    }

    private void up(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;

            comparisons++;
            insertComparisons++;

            if (data[i] >= data[parent]) {
                break;
            }

            swap(i, parent);
            i = parent;
        }
    }

    private void down(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int small = i;

            if (left < size) {
                comparisons++;
                extractComparisons++;

                if (data[left] < data[small]) {
                    small = left;
                }
            }

            if (right < size) {
                comparisons++;
                extractComparisons++;

                if (data[right] < data[small]) {
                    small = right;
                }
            }

            if (small == i) {
                break;
            }

            swap(i, small);
            i = small;
        }
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
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
