package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final long SEED = 42;
    private static final String OUT = "results/tables/";

    public static void main(String[] args) throws IOException {
        new File(OUT).mkdirs();

        randomAccess();
        search();
        insertRemove();
        priority();

        System.out.println("Benchmark completed.");
        System.out.println("Results: " + OUT);
    }

    private static void randomAccess() throws IOException {
        String file = OUT + "workload1.csv";

        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            w.println("n,structure,avgTimeNs,accesses");

            for (int n : SIZES) {
                long arrayTime = 0;
                long listTime = 0;
                long arrayAccess = 0;
                long listAccess = 0;

                for (int run = 0; run < RUNS; run++) {
                    Random r = new Random(SEED + run);

                    DynamicArray a = new DynamicArray();
                    LinkedList l = new LinkedList();

                    for (int i = 0; i < n; i++) {
                        int x = r.nextInt();
                        a.add(x);
                        l.add(x);
                    }

                    int[] indexes = new int[10000];

                    for (int i = 0; i < indexes.length; i++) {
                        indexes[i] = r.nextInt(n);
                    }

                    DynamicArray.reset();

                    long start = System.nanoTime();

                    for (int index : indexes) {
                        a.get(index);
                    }

                    long end = System.nanoTime();

                    arrayTime += end - start;
                    arrayAccess += DynamicArray.accesses;

                    LinkedList.reset();

                    start = System.nanoTime();

                    for (int index : indexes) {
                        l.get(index);
                    }

                    end = System.nanoTime();

                    listTime += end - start;
                    listAccess += LinkedList.accesses;
                }

                long avgArrayTime = arrayTime / RUNS;
                long avgListTime = listTime / RUNS;
                long avgArrayAccess = arrayAccess / RUNS;
                long avgListAccess = listAccess / RUNS;

                w.println(n + ",DynamicArray," + avgArrayTime + "," + avgArrayAccess);
                w.println(n + ",LinkedList," + avgListTime + "," + avgListAccess);
            }
        }
    }

    private static void search() throws IOException {
        String file = OUT + "workload2.csv";

        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            w.println("n,structure,avgTimeNs,comparisons");

            for (int n : SIZES) {
                long arrayTime = 0;
                long listTime = 0;
                long arrayComp = 0;
                long listComp = 0;

                for (int run = 0; run < RUNS; run++) {
                    Random r = new Random(SEED + run);

                    DynamicArray a = new DynamicArray();
                    LinkedList l = new LinkedList();

                    for (int i = 0; i < n; i++) {
                        int x = r.nextInt();
                        a.add(x);
                        l.add(x);
                    }

                    int[] values = new int[1000];

                    for (int i = 0; i < values.length; i++) {
                        values[i] = r.nextInt();
                    }

                    DynamicArray.reset();

                    long start = System.nanoTime();

                    for (int x : values) {
                        a.contains(x);
                    }

                    long end = System.nanoTime();

                    arrayTime += end - start;
                    arrayComp += DynamicArray.comparisons;

                    LinkedList.reset();

                    start = System.nanoTime();

                    for (int x : values) {
                        l.contains(x);
                    }

                    end = System.nanoTime();

                    listTime += end - start;
                    listComp += LinkedList.comparisons;
                }

                long avgArrayTime = arrayTime / RUNS;
                long avgListTime = listTime / RUNS;
                long avgArrayComp = arrayComp / RUNS;
                long avgListComp = listComp / RUNS;

                w.println(n + ",DynamicArray," + avgArrayTime + "," + avgArrayComp);
                w.println(n + ",LinkedList," + avgListTime + "," + avgListComp);
            }
        }
    }

    private static void insertRemove() throws IOException {
        String file = OUT + "workload3.csv";

        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            w.println("n,structure,operation,position,avgTimeNs,avgAccesses");

            for (int n : SIZES) {
                int ops = 1000;

                runInsertRemove(w, n, ops, "DynamicArray");
                runInsertRemove(w, n, ops, "LinkedList");
            }
        }
    }

    private static void runInsertRemove(
            PrintWriter w,
            int n,
            int ops,
            String type
    ) {
        String[] positions = {"start", "middle"};

        for (String pos : positions) {
            long insertTime = 0;
            long removeTime = 0;
            long insertAccess = 0;
            long removeAccess = 0;

            for (int run = 0; run < RUNS; run++) {
                if (type.equals("DynamicArray")) {
                    DynamicArray a = new DynamicArray();

                    for (int i = 0; i < n; i++) {
                        a.add(i);
                    }

                    int index = pos.equals("start") ? 0 : n / 2;

                    DynamicArray.reset();

                    long start = System.nanoTime();

                    for (int i = 0; i < ops; i++) {
                        int current = Math.min(index, a.size());
                        a.add(current, i);
                    }

                    long end = System.nanoTime();

                    insertTime += end - start;
                    insertAccess += DynamicArray.accesses;

                    DynamicArray.reset();

                    start = System.nanoTime();

                    for (int i = 0; i < ops; i++) {
                        int current = pos.equals("start") ? 0 : a.size() / 2;
                        a.remove(current);
                    }

                    end = System.nanoTime();

                    removeTime += end - start;
                    removeAccess += DynamicArray.accesses;

                } else {
                    LinkedList l = new LinkedList();

                    for (int i = 0; i < n; i++) {
                        l.add(i);
                    }

                    int index = pos.equals("start") ? 0 : n / 2;

                    LinkedList.reset();

                    long start = System.nanoTime();

                    for (int i = 0; i < ops; i++) {
                        int current = Math.min(index, l.size());
                        l.add(current, i);
                    }

                    long end = System.nanoTime();

                    insertTime += end - start;
                    insertAccess += LinkedList.accesses;

                    LinkedList.reset();

                    start = System.nanoTime();

                    for (int i = 0; i < ops; i++) {
                        int current = pos.equals("start") ? 0 : l.size() / 2;
                        l.remove(current);
                    }

                    end = System.nanoTime();

                    removeTime += end - start;
                    removeAccess += LinkedList.accesses;
                }
            }

            long avgInsertTime = insertTime / RUNS;
            long avgRemoveTime = removeTime / RUNS;
            long avgInsertAccess = insertAccess / RUNS;
            long avgRemoveAccess = removeAccess / RUNS;

            w.println(n + "," + type + ",insert," + pos + "," + avgInsertTime + "," + avgInsertAccess);
            w.println(n + "," + type + ",remove," + pos + "," + avgRemoveTime + "," + avgRemoveAccess);
        }
    }

    private static void priority() throws IOException {
        String file = OUT + "workload4.csv";

        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            w.println("n,insertTimeNs,extractTimeNs,insertComparisons,extractComparisons,totalComparisons,nonDecreasing");

            for (int n : SIZES) {
                long insertTime = 0;
                long extractTime = 0;
                long insertComp = 0;
                long extractComp = 0;
                boolean sorted = true;

                for (int run = 0; run < RUNS; run++) {
                    Random r = new Random(SEED + run);
                    int[] values = new int[n];

                    for (int i = 0; i < n; i++) {
                        values[i] = r.nextInt();
                    }

                    MinHeap heap = new MinHeap();

                    MinHeap.reset();

                    long start = System.nanoTime();

                    for (int x : values) {
                        heap.insert(x);
                    }

                    long end = System.nanoTime();

                    insertTime += end - start;
                    insertComp += MinHeap.insertComparisons;

                    MinHeap.reset();

                    start = System.nanoTime();

                    int previous = Integer.MIN_VALUE;

                    for (int i = 0; i < n; i++) {
                        int current = heap.extractMin();

                        if (current < previous) {
                            sorted = false;
                        }

                        previous = current;
                    }

                    end = System.nanoTime();

                    extractTime += end - start;
                    extractComp += MinHeap.extractComparisons;
                }

                long avgInsertTime = insertTime / RUNS;
                long avgExtractTime = extractTime / RUNS;
                long avgInsertComp = insertComp / RUNS;
                long avgExtractComp = extractComp / RUNS;
                long totalComp = avgInsertComp + avgExtractComp;

                w.println(n + "," + avgInsertTime + "," + avgExtractTime + "," + avgInsertComp + "," + avgExtractComp + "," + totalComp + "," + sorted);
            }
        }
    }
}
