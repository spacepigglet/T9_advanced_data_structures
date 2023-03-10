import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Test {
    private static final int[] UNSORTED_NODUPLICATES_75 = { 100, 7, 68, 86, 76, 40, 24, 30, 73, 52, 20, 12, 10, 49, 77, 37, 62, 83, 2, 89, 63, 56, 94, 91, 21, 9, 59, 4, 71, 93, 85, 6, 66, 36, 75, 53, 35, 67, 61, 1, 5, 84, 98, 54, 17, 70, 46, 26, 55, 69, 78, 51, 88, 39, 45, 64, 80, 48, 87, 25, 8, 96, 27, 11, 16, 14, 33, 42, 44, 28, 31, 19, 82, 43, 50 };
    private static final int[] SORTED_NODUPLICATE_75 = { 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 17, 19, 20, 21, 24, 25, 26, 27, 28, 30, 31, 33, 35, 36, 37, 39, 40, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 59, 61, 62, 63, 64, 66, 67, 68, 69, 70, 71, 73, 75, 76, 77, 78, 80, 82, 83, 84, 85, 86, 87, 88, 89, 91, 93, 94, 96, 98, 100 };
    private static final int[] REVERSE_NODUPLICATE_75 = { 100, 98, 96, 94, 93, 91, 89, 88, 87, 86, 85, 84, 83, 82, 80, 78, 77, 76, 75, 73, 71, 70, 69, 68, 67, 66, 64, 63, 62, 61, 59, 56, 55, 54, 53, 52, 51, 50, 49, 48, 46, 45, 44, 43, 42, 40, 39, 37, 36, 35, 33, 31, 30, 28, 27, 26, 25, 24, 21, 20, 19, 17, 16, 14, 12, 11, 10, 9, 8, 7, 6, 5, 4, 2, 1 };

    // Testdata for contains
    private static final int[] TEST_CONTAINS_DUPLICATES_15 = { 45, 86, 45, 40, 83, 86, 61, 93, 46, 86, 1, 20 }; // Gonna be good for splay
    private static final int[] TEST_CONTAINS_NO_DUPLICATES_15 = { 52, 66, 45, 40, 83, 8, 26, 93, 46, 86, 1, 20 };

    public static void main(String[] args) {
        RedBlackTree<Integer> rbtSorted = new RedBlackTree<>();
        RedBlackTree<Integer> rbtUnsorted = new RedBlackTree<>();
        RedBlackTree<Integer> rbtReverse = new RedBlackTree<>();
        Treap<Integer> treapSorted = new Treap<>();
        Treap<Integer> treapUnsorted = new Treap<>();
        Treap<Integer> treapReverse = new Treap<>();
        SplayTree<Integer> splayTreeSorted = new SplayTree<>();
        SplayTree<Integer> splayTreeUnsorted = new SplayTree<>();
        SplayTree<Integer> splayTreeReverse = new SplayTree<>();


        //INSERT
        System.out.println("test insert SORTED:");
        insertTester(SORTED_NODUPLICATE_75, splayTreeSorted,rbtSorted, treapSorted);
        System.out.println("test insert UNSORTED:");
        insertTester(UNSORTED_NODUPLICATES_75, splayTreeUnsorted, rbtUnsorted, treapUnsorted);
        System.out.println("test insert REVERSE");
        insertTester(REVERSE_NODUPLICATE_75, splayTreeReverse, rbtReverse, treapReverse);

        //insert tests complete -> reset rotationCounter!
        resetCounter(splayTreeSorted,rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted, splayTreeReverse, rbtReverse, treapReverse);


        //CONTAINS
        //test number of rotations caused by contains
        //test contains sorted - interesting for splay tree
        System.out.println("test number of rotations caused by contains");
        System.out.println("sorted duplicates");
        containsTester(TEST_CONTAINS_DUPLICATES_15, splayTreeSorted,rbtSorted, treapSorted);
        System.out.println("sorted NO duplicates");
        containsTester(TEST_CONTAINS_NO_DUPLICATES_15, splayTreeSorted,rbtSorted, treapSorted);

        //unsorted
        System.out.println("unsorted duplicates");
        containsTester(TEST_CONTAINS_DUPLICATES_15, splayTreeUnsorted,rbtUnsorted, treapUnsorted);
        System.out.println("unsorted NO duplicates");
        containsTester(SORTED_NODUPLICATE_75, splayTreeUnsorted,rbtUnsorted, treapUnsorted);

        //reverse
        System.out.println("reverse duplicates");
        containsTester(TEST_CONTAINS_DUPLICATES_15, splayTreeReverse,rbtReverse, treapReverse);
        System.out.println("reverse NO duplicates");
        containsTester(SORTED_NODUPLICATE_75, splayTreeReverse,rbtReverse, treapReverse);

        //contains tests complete -> reset rotationCounter!
        resetCounter(splayTreeSorted,rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted, splayTreeReverse, rbtReverse, treapReverse);
        //REMOVE

    }

    private static void resetCounter(DataStructure<Integer>... structs){
        for(DataStructure<Integer> struct : structs){
            struct.resetRotationCounter();
        }
    }



    private static void insertTester(int[] data, DataStructure<Integer>... structs){
        for(DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(insert(struct, data));
        }
    }
    
    public static int insert(DataStructure<Integer> struct, int... ints) {
        for (int i : ints) {
            struct.insert(i);
        }
        return struct.getRotationCounter();
    }

    private static int testContains(DataStructure<Integer> struct, int... testData) {
        for (int i : testData) {
            struct.contains(i);
        }
        return struct.getRotationCounter();
    }

    private static void containsTester(int[] data, DataStructure<Integer>... structs) {
        for(DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(testContains(struct, data));
        }
    }


    private static void generateData() {
        int size = 75;
        int max = 100;

        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            Random random = new Random();
            arr[i] = random.ints(1, max).findFirst().getAsInt();
        }

        int[] sorted = Arrays.copyOf(arr, size);
        Arrays.sort(sorted);

        int[] reverse = Arrays.copyOf(arr, size);
        Collections.reverse(Arrays.asList(reverse));

    }

}
