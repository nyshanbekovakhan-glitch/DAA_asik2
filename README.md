

# Assignment 2: Algorithmic Analysis, Correctness and Performance Trade-offs

**Group:** SE-2539
**Name:** Khanzada Nyshanbek Adilqyzy

## 1. Overview

This assignment focuses on algorithmic analysis, correctness, benchmarking, and performance comparison.

Three data structures were implemented from scratch:

Dynamic Array

Linked List

Min-Heap

The main purpose of the assignment is to analyze the algorithms using O, Ω, and Θ notation, prove correctness using loop invariants, design controlled workloads, and compare theoretical complexity with measured performance.

The project is implemented in Java.

The experiments use four workloads with different input sizes. Execution time and additional metrics such as element accesses and comparisons are recorded.

## 2. Project Structure

```text
assignment-2/
├── src/
│       ├── DynamicArray.java
│       ├── LinkedList.java
│       ├── MinHeap.java
│       ├── Benchmark.java
│       └── Tests.java
│
├── results/
│   ├── tables/
│   │   ├── workload1.csv
│   │   ├── workload2.csv
│   │   ├── workload3.csv
│   │   └── workload4.csv
│   │
│   └── plots/
│       ├── 1graf.png
│       ├── 2graf.png
│       ├── 3graf.png
│       └── 4graf.png
│
├── pom.xml
└── README.md
```

## 3. Implemented Data Structures

### 3.1 Dynamic Array

The Dynamic Array stores elements in a contiguous array.

The implemented operations are:

```text
add(x)
add(index, x)
remove(index)
get(index)
contains(x)
```

The array automatically increases its capacity when more space is required.

The capacity is increased by creating a larger array and copying the existing elements.

### 3.2 Linked List

The Linked List stores elements in nodes.

Each node contains a value and a reference to the next node.

The implementation also keeps references to the head and tail nodes.

The implemented operations are:

```text
add(x)
add(index, x)
remove(index)
get(index)
contains(x)
```

### 3.3 Min-Heap

The Min-Heap is implemented using an array.

The minimum element is stored at the root of the heap.

The implemented operations are:

```text
insert(x)
peekMin()
extractMin()
```

The heap property is maintained after insertion using sift-up and after extraction using sift-down.

## 4. Complexity Analysis

### 4.1 Dynamic Array

| Operation     | Best Case | Average Case | Worst Case | Auxiliary Space |
| ------------- | --------- | ------------ | ---------- | --------------- |
| add(x)        | Ω(1)      | Θ(1)         | O(n)       | O(n)            |
| add(index, x) | Ω(1)      | Θ(n)         | O(n)       | O(n)            |
| remove(index) | Ω(1)      | Θ(n)         | O(n)       | O(1)            |
| get(index)    | Ω(1)      | Θ(1)         | O(1)       | O(1)            |
| contains(x)   | Ω(1)      | Θ(n)         | O(n)       | O(1)            |

The get(index) operation directly accesses an array position, so it takes constant time.

The add(x) operation is normally constant time. When the internal array becomes full, resizing and copying elements requires O(n) time.

The add(index, x) operation may need to move all elements after the selected index. Therefore, its worst-case complexity is O(n).

The remove(index) operation may also need to move elements to fill the removed position, so its worst-case complexity is O(n).

The contains(x) operation searches elements one by one. It can finish immediately if the value is at the beginning, but in the worst case all elements must be checked.

### 4.2 Linked List

| Operation     | Best Case | Average Case | Worst Case | Auxiliary Space |
| ------------- | --------- | ------------ | ---------- | --------------- |
| add(x)        | Ω(1)      | Θ(1)         | O(1)       | O(1)            |
| add(index, x) | Ω(1)      | Θ(n)         | O(n)       | O(1)            |
| remove(index) | Ω(1)      | Θ(n)         | O(n)       | O(1)            |
| get(index)    | Ω(1)      | Θ(n)         | O(n)       | O(1)            |
| contains(x)   | Ω(1)      | Θ(n)         | O(n)       | O(1)            |

The add(x) operation is constant time because the implementation keeps a tail reference.

For add(index, x), the list usually needs to traverse nodes until the required position is reached. Therefore, the worst case is O(n).

The same traversal is required for remove(index).

The get(index) operation is O(n) because the list does not support direct indexing.

The contains(x) operation checks nodes sequentially, so the worst case is O(n).

### 4.3 Min-Heap

| Operation    | Best Case | Average Case | Worst Case | Auxiliary Space |
| ------------ | --------- | ------------ | ---------- | --------------- |
| insert(x)    | Ω(1)      | Θ(log n)     | O(log n)   | O(n)            |
| peekMin()    | Ω(1)      | Θ(1)         | O(1)       | O(1)            |
| extractMin() | Ω(1)      | Θ(log n)     | O(log n)   | O(1)            |

The insert(x) operation places the new element at the end and moves it upward when necessary. The height of a binary heap is O(log n).

The peekMin() operation returns the root element, so it takes constant time.

The extractMin() operation removes the root and restores the heap property using sift-down. Its worst-case complexity is O(log n).

## 5. Correctness

Two non-trivial operations were selected for loop-invariant correctness proofs.

The first operation is Dynamic Array add(index, x).

The second operation is Min-Heap extractMin().

### 5.1 Dynamic Array add(index, x)

The operation shifts elements to the right before placing the new element at the required index.

#### Loop Invariant

Before each iteration of the shifting loop, all elements that have already been processed have been moved one position to the right, and their relative order is preserved.

#### Initialization

The loop starts from the last existing element.

Before the first iteration, no elements have been shifted yet. The last element can be moved one position to the right without overwriting an unprocessed element because the array has already been resized if necessary.

Therefore, the invariant is true before the first iteration.

#### Maintenance

During each iteration, the element at position `i - 1` is copied to position `i`.

This moves the current element one position to the right while preserving the elements that have already been processed.

Therefore, the invariant remains true after every iteration.

#### Termination

The loop terminates when the index reaches the insertion position.

At this point, every element from the insertion position to the end has been shifted one position to the right.

The required index is now free, so the new element can be placed there.

#### Correctness

After termination, all original elements remain in their original relative order and the new element is stored at the requested index.

Therefore, `add(index, x)` correctly inserts the new element.

### 5.2 Min-Heap extractMin()

The extractMin() operation removes the root and restores the heap property using sift-down.

#### Loop Invariant

Before each iteration of the sift-down loop, the heap satisfies the min-heap property everywhere except possibly at the current position.

#### Initialization

After removing the minimum element from the root, the last element is moved to the root.

All other subtrees were already valid min-heaps before the operation.

Therefore, only the new root can violate the heap property.

The invariant is true before the first iteration.

#### Maintenance

The current element is compared with its children.

If a child is smaller, the current element is swapped with the smaller child.

The possible violation is moved to the child position.

All other parts of the heap continue to satisfy the heap property.

Therefore, the invariant remains true.

#### Termination

The loop terminates when the current element is smaller than or equal to both children, or when it reaches a leaf.

At this point, the possible violation has disappeared.

Therefore, the entire structure satisfies the min-heap property again.

#### Correctness

The original root was the minimum element, so removing it returns the correct minimum.

After sift-down terminates, the heap property is restored.

Therefore, `extractMin()` correctly removes and returns the minimum element.

## 6. Experimental Setup

The experiments were performed in Java using `System.nanoTime()`.

The input sizes were:

```text
100
1000
10000
100000
```

Each experiment was repeated 5 times.

The average execution time was calculated from the five runs.

The benchmark uses a fixed random seed:

```text
Random(42)
```

Input data was generated before the timed section.

Input generation was not included in the measured execution time.

Printing was not included in the measured execution time.

The benchmark records execution time and additional metrics required by each workload.

The value of `m` depends on the workload.

For Workload 1:

```text
m = 10000 get operations
```

For Workload 2:

```text
m = 1000 search operations
```

For Workload 3:

```text
m = 1000 insertions and 1000 removals
```

For Workload 4:

```text
m = n insertions and n extractions
```

## 7. Workload 1: Random Access

The first workload compares `get(index)` for Dynamic Array and Linked List.

For each input size, a structure containing n random integers was created.

Then 10000 random indexes were generated.

The `get(index)` operation was performed for every generated index.

The total execution time and number of element accesses were recorded.

### Theoretical Analysis

Dynamic Array:

```text
get(index) = Θ(1)
```

Linked List:

```text
get(index) = Θ(n)
```

Dynamic Array can directly access an array position.

Linked List must start from the head and follow nodes until the required index is reached.

### Results

The measured results were:

|      n | Dynamic Array Time (ns) | Dynamic Array Accesses | Linked List Time (ns) | Linked List Accesses |
| -----: | ----------------------: | ---------------------: | --------------------: | -------------------: |
|    100 |                  128999 |                  10000 |                690816 |               506935 |
|   1000 |                   74324 |                  10000 |               7055850 |              5004956 |
|  10000 |                    4041 |                  10000 |              75501225 |             50080614 |
| 100000 |                   31175 |                  10000 |             625347474 |            499473863 |

The number of Dynamic Array accesses remains 10000 because every get operation uses direct indexing.

The number of Linked List accesses increases approximately linearly with n because each random access requires traversal.

### Graph

The graph was created using Excel from the benchmark results.

![Workload 1](results/plots/1graf.png)

## 8. Workload 2: Search

The second workload compares `contains(value)` for Dynamic Array and Linked List.

For every input size, 1000 search values were generated.

The `contains(value)` operation was performed for every search value.

The total execution time and number of comparisons were recorded.

### Theoretical Analysis

Both structures perform sequential search.

Therefore:

```text
Best case = Ω(1)
Average case = Θ(n)
Worst case = O(n)
```

### Results

|      n | Dynamic Array Time (ns) | Dynamic Array Comparisons | Linked List Time (ns) | Linked List Comparisons |
| -----: | ----------------------: | ------------------------: | --------------------: | ----------------------: |
|    100 |                  235000 |                    100000 |                231733 |                  100000 |
|   1000 |                  338625 |                   1000000 |               1456925 |                 1000000 |
|  10000 |                 1858108 |                  10000000 |              14542900 |                10000000 |
| 100000 |                26368224 |                 100000000 |             119304708 |               100000000 |

The number of comparisons increases linearly with n.

For n = 100000, both structures performed 100000000 comparisons.

The execution time also increases as the input size increases.

### Graph

![Workload 2](results/plots/2graf.png)

## 9. Workload 3: Insertion and Removal

The third workload compares insertion and removal for Dynamic Array and Linked List.

For each input size, 1000 operations were performed at the beginning and at the middle position.

The number of accesses and execution time were recorded.

The middle position was calculated as:

```text
index = n / 2
```

### Theoretical Analysis

For Dynamic Array, insertion and removal may require shifting elements.

Therefore:

```text
Beginning: O(n)
Middle: O(n)
```

For Linked List, insertion and removal at the beginning can be performed by changing references.

Therefore:

```text
Beginning: O(1)
```

For the middle position, traversal is required before changing the links.

Therefore:

```text
Middle: O(n)
```

### Results

|      n | Structure     | Operation | Position | Time (ns) |  Accesses |
| -----: | ------------- | --------- | -------- | --------: | --------: |
|    100 | Dynamic Array | insert    | start    |    877699 |   1200000 |
|    100 | Dynamic Array | remove    | start    |   1113700 |   1201000 |
|    100 | Dynamic Array | insert    | middle   |     71375 |   1100000 |
|    100 | Dynamic Array | remove    | middle   |     53166 |    601000 |
|    100 | Linked List   | insert    | start    |     48391 |         0 |
|    100 | Linked List   | remove    | start    |     40341 |      1000 |
|    100 | Linked List   | insert    | middle   |     83391 |     51000 |
|    100 | Linked List   | remove    | middle   |    531508 |    301000 |
|   1000 | Dynamic Array | insert    | start    |    122649 |   3000000 |
|   1000 | Dynamic Array | remove    | start    |     80966 |   3001000 |
|   1000 | Dynamic Array | insert    | middle   |     99341 |   2000000 |
|   1000 | Dynamic Array | remove    | middle   |     73808 |   1501000 |
|   1000 | Linked List   | insert    | start    |     30033 |         0 |
|   1000 | Linked List   | remove    | start    |     21183 |      1000 |
|   1000 | Linked List   | insert    | middle   |    375783 |    501000 |
|   1000 | Linked List   | remove    | middle   |    983025 |    751000 |
|  10000 | Dynamic Array | insert    | start    |    580383 |  21000000 |
|  10000 | Dynamic Array | remove    | start    |    426324 |  21001000 |
|  10000 | Dynamic Array | insert    | middle   |    320241 |  11000000 |
|  10000 | Dynamic Array | remove    | middle   |    202183 |  10501000 |
|  10000 | Linked List   | insert    | start    |      6466 |         0 |
|  10000 | Linked List   | remove    | start    |      4533 |      1000 |
|  10000 | Linked List   | insert    | middle   |   2063333 |   5001000 |
|  10000 | Linked List   | remove    | middle   |   5370792 |   5251000 |
| 100000 | Dynamic Array | insert    | start    |   6149025 | 201000000 |
| 100000 | Dynamic Array | remove    | start    |   5877800 | 201001000 |
| 100000 | Dynamic Array | insert    | middle   |   3140166 | 101000000 |
| 100000 | Dynamic Array | remove    | middle   |   3040799 | 100501000 |
| 100000 | Linked List   | insert    | start    |      3249 |         0 |
| 100000 | Linked List   | remove    | start    |      1633 |      1000 |
| 100000 | Linked List   | insert    | middle   |  67352775 |  50001000 |
| 100000 | Linked List   | remove    | middle   |  67211583 |  50251000 |

The results show that Dynamic Array performs many element movements when inserting or removing at the beginning.

Linked List requires very few counted accesses for operations at the beginning.

For middle operations, Linked List requires traversal, so the number of accesses increases with n.

### Graph

![Workload 3](results/plots/3graf.png)

## 10. Workload 4: Priority Processing

The fourth workload evaluates the Min-Heap.

For each input size, n random integers were generated.

All values were inserted into an empty Min-Heap.

After all insertions, the minimum element was extracted n times.

Insertion time, extraction time, and comparisons were recorded.

The extracted values were also checked to make sure they were in non-decreasing order.

### Theoretical Analysis

The complexity of Min-Heap operations is:

```text
insert(x) = O(log n)
peekMin() = O(1)
extractMin() = O(log n)
```

The heap height is logarithmic, so sift-up and sift-down require at most O(log n) levels.

### Results

|      n | Insert Time (ns) | Extract Time (ns) | Insert Comparisons | Extract Comparisons | Total Comparisons | Non-Decreasing |
| -----: | ---------------: | ----------------: | -----------------: | ------------------: | ----------------: | -------------- |
|    100 |            14333 |             36483 |                201 |                 852 |              1053 | true           |
|   1000 |            30041 |             82074 |               2238 |               15001 |             17239 | true           |
|  10000 |           214875 |            661783 |              22683 |              216600 |            239283 | true           |
| 100000 |           987100 |           6469299 |             228142 |             2831649 |           3059791 | true           |

The results show that the number of comparisons increases as n increases.

The `nonDecreasing` value is true for every tested input size, which confirms that `extractMin()` returns elements in the required order.

### Graph

![Workload 4](results/plots/4graf.png)

## 11. Performance and Design Analysis

### 11.1 How does increasing n affect each workload?

Increasing n increases the amount of work for operations with linear or logarithmic complexity.

The strongest increase can be seen in Linked List random access because every access may require traversing many nodes.

Search also requires more comparisons as n increases.

Insertion and removal at the beginning of Dynamic Array require more element movements for larger n.

Min-Heap requires more comparisons for insertion and extraction as the heap becomes larger.

### 11.2 Which experimental results agree with theoretical complexity?

The Random Access workload agrees strongly with theory.

Dynamic Array performs a constant number of counted accesses for every n, while Linked List accesses increase with n.

Search results also agree with the theoretical O(n) complexity because the number of comparisons increases linearly.

Workload 3 agrees with the expected difference between array shifting and linked-list traversal.

Min-Heap results show increasing comparison counts consistent with logarithmic operations repeated n times.

### 11.3 Where can experimental results differ from theoretical prediction?

Measured execution time can contain noise caused by the JVM, CPU cache, garbage collection, memory allocation, operating system activity, and other running processes.

Very fast operations can also be affected by measurement overhead.

For this reason, theoretical complexity is better evaluated together with operation counts and repeated measurements instead of using execution time alone.

### 11.4 Why can two algorithms with the same Big-O complexity have different running times?

Big-O describes the growth rate of an algorithm.

It does not describe every constant factor.

Two O(n) algorithms can perform different numbers of instructions, memory accesses, pointer operations, or data movements.

Therefore, their real execution times can be different even though they have the same asymptotic complexity.

### 11.5 How do constant factors and implementation details affect performance?

Implementation details can change the actual running time.

Dynamic Array uses contiguous memory and direct indexing.

Linked List uses nodes and references between nodes.

These different memory organizations affect memory access and cache behavior.

The JVM can also optimize frequently executed code during the benchmark.

### 11.6 Why is Dynamic Array preferable for some workloads?

Dynamic Array is useful when the program performs many indexed accesses.

The `get(index)` operation has Θ(1) complexity.

This makes Dynamic Array suitable when fast random access is important.

### 11.7 When can a Linked List be useful?

Linked List can be useful when many insertions or removals happen at the beginning and indexed access is not the main requirement.

The implementation can change the head reference without shifting all existing elements.

### 11.8 Why is a Heap appropriate for priority-based processing?

A Min-Heap keeps the minimum element at the root.

`peekMin()` takes O(1) time.

`insert()` and `extractMin()` take O(log n) in the worst case.

This makes the structure suitable for processing elements according to priority.

### 11.9 How does the workload influence the choice of data structure?

Different workloads require different operations.

A workload with frequent indexed access benefits from Dynamic Array.

A workload with frequent operations at the beginning can use Linked List.

A workload that repeatedly needs the minimum-priority element can use Min-Heap.

Therefore, the workload and the frequency of operations should be considered when selecting a data structure.

## 12. Testing and Correctness Validation

The project contains a `Tests` class for correctness validation.

The tests include:

```text
Empty structure
One element
Multiple elements
Duplicate values
Insertion at the beginning
Insertion in the middle
Insertion at the end
Removal
Boundary indexes
Invalid indexes
Large inputs
```

For Min-Heap, the tests also verify:

```text
Minimum element
Heap extraction
Non-decreasing extraction order
Large heap
```

The test program completed successfully.

### Test Output

The following screenshot shows the test results:
<img width="548" height="694" alt="output test" src="https://github.com/user-attachments/assets/30a5c412-5b5a-4ee7-bfc5-cf1184498e7b" />

![Uploading outout.test2.png…]()




```text
Passed: 33
Failed: 0
All tests passed!
```

## 13. Benchmark Output

The benchmark was executed for all four workloads.

The following screenshot shows the benchmark execution:
<img width="542" height="190" alt="output bench" src="https://github.com/user-attachments/assets/1b4c6b5a-cbb7-4b81-9a3a-dc9c804f9aae" />


The benchmark generated four CSV result files:

```text
results/tables/workload1.csv
results/tables/workload2.csv
results/tables/workload3.csv
results/tables/workload4.csv
```

## 14. Experimental Results and Plots

The benchmark tables are stored in the `results/tables/` directory.

The graphs were created from the measured benchmark results.

### Workload 1

![Workload 1](results/plots/1graf.png)

### Workload 2

![Workload 2](results/plots/2graf.png)

### Workload 3

![Workload 3](results/plots/3graf.png)

### Workload 4

![Workload 4](results/plots/4graf.png)

## 15. Design Recommendations

The results show that the choice of data structure depends on the workload.

Dynamic Array is appropriate when direct indexed access is frequently required.

Linked List can be useful when operations are concentrated at the beginning of the structure.

Min-Heap is appropriate for priority-based processing where the minimum element must be repeatedly accessed and removed.

The theoretical complexity and the measured workload should both be considered when choosing a data structure.

## 16. Conclusion

This assignment demonstrated the relationship between theoretical algorithmic complexity and practical performance.
Dynamic Array provided constant-time indexed access, while Linked List required traversal for indexed access.

Both Dynamic Array and Linked List required linear search for `contains(value)` in the implemented structures.

Dynamic Array insertion and removal can require many element movements.

Linked List can perform operations at the beginning without moving all existing elements, but middle operations require traversal.

Min-Heap provided efficient priority processing with O(log n) insertion and extraction and O(1) access to the minimum element.

The benchmark results generally support the theoretical complexity analysis.

The experiments also demonstrated that input size, workload, memory organization, implementation details, and JVM behavior can affect measured execution time.

## 17. Deliverables

The repository contains:

```text
README.md
Source code
Benchmark results
CSV tables
Performance graphs
Tests
```

All implemented classes compile and run successfully.

The test program completed with 33 passed tests and 0 failed tests.
