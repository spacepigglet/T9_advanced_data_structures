import java.util.*;

public class Test {
    private static Integer[] UNSORTED_NODUPLICATE;
    private static Integer[] SORTED_NODUPLICATE;
    private static Integer[] REVERSE_NODUPLICATE;


    // Testdata for contains
    private static Integer[] SAMPLE_DUPLICATES; // Gonna be good for splay
    private static Integer[] SAMPLE_NO_DUPLICATES;

    //Test data for mixed insert remove
    private static List<Integer> insertList ;
    private static List<Integer> removeList;
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        generateData();
        generateContainsData();
        allOperationsOnSortedNonSortedSerial();
        insertRemoveMixed();
    }

    private static void allOperationsOnSortedNonSortedSerial() {
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
        insertTester(SORTED_NODUPLICATE, splayTreeSorted,rbtSorted, treapSorted);
        System.out.println("test insert UNSORTED:");
        insertTester(UNSORTED_NODUPLICATE, splayTreeUnsorted, rbtUnsorted, treapUnsorted);
        System.out.println("test insert REVERSE");

        insertTester(REVERSE_NODUPLICATE, splayTreeReverse, rbtReverse, treapReverse);

        //createTable("Insert stor mängd data i rad")

        //insert tests complete -> reset rotationCounter!
        resetCounter(splayTreeSorted, rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted,
                splayTreeReverse, rbtReverse, treapReverse);

        //CONTAINS
        //test number of rotations caused by contains
        //test contains sorted - interesting for splay tree
        System.out.println("test number of rotations caused by contains");
        System.out.println("sorted duplicates");

        containsTester(SAMPLE_DUPLICATES, splayTreeSorted,rbtSorted, treapSorted);
        System.out.println("sorted NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeSorted,rbtSorted, treapSorted);

        //unsorted
        System.out.println("unsorted duplicates");
        containsTester(SAMPLE_DUPLICATES, splayTreeUnsorted,rbtUnsorted, treapUnsorted);
        System.out.println("unsorted NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted,rbtUnsorted, treapUnsorted);

        //reverse
        System.out.println("reverse duplicates");
        containsTester(SAMPLE_DUPLICATES, splayTreeReverse,rbtReverse, treapReverse);
        System.out.println("reverse NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeReverse,rbtReverse, treapReverse);

        //contains tests complete -> reset rotationCounter!
        resetCounter(splayTreeSorted, rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted,
                splayTreeReverse, rbtReverse, treapReverse);

        //REMOVE
        //testing contains changed the splay trees - make new!
        SplayTree<Integer> splayTreeSortedTwo = new SplayTree<>();
        SplayTree<Integer> splayTreeUnsortedTwo = new SplayTree<>();
        SplayTree<Integer> splayTreeReverseTwo = new SplayTree<>();

        //sorted
        System.out.println("Test removal on SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeSortedTwo, treapSorted);
        System.out.println("Test removal on UNSORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsortedTwo, treapUnsorted);
        System.out.println("Test removal on REVERSE SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeReverseTwo, treapReverse);
    }

    private static void insertRemoveMixed() {

        Treap<Integer> treapUnsorted = new Treap<>();
        SplayTree<Integer> splayTreeUnsorted = new SplayTree<>();

        //setup
        insert(treapUnsorted, UNSORTED_NODUPLICATE);
        insert(splayTreeUnsorted, UNSORTED_NODUPLICATE);
        resetCounter(treapUnsorted, splayTreeUnsorted);
        //mixed insert and remove
        while (insertList.size() > 5) {
            insertMixed(treapUnsorted, splayTreeUnsorted);
            removeMixed(treapUnsorted, splayTreeUnsorted);
        }
        System.out.println("treap rotations: " + treapUnsorted.getRotationCounter() + "\nsplay rotations: "
                + splayTreeUnsorted.getRotationCounter());
        System.out.println(insertList);

    }

    private static void resetCounter(DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            struct.resetRotationCounter();
        }
    }

    private static int remove(DataStructure<Integer> struct, Integer[] data) {
        for (int i : data) {
            struct.remove(i);
        }
        return struct.getRotationCounter();
    }

    private static void removeTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(remove(struct, data));
        }
    }

    private static void insertTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(insert(struct, data));
        }
    }

    public static int insert(DataStructure<Integer> struct, Integer... ints) {
        for (int i : ints) {
            struct.insert(i);
        }
        return struct.getRotationCounter();
    }

    private static int testContains(DataStructure<Integer> struct, Integer... testData) {
        for (int i : testData) {
            struct.contains(i);
        }
        return struct.getRotationCounter();
    }

    private static void containsTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(testContains(struct, data));
        }
    }

    private static void removeMixed(DataStructure<Integer>... structs) {
        int randomNum = rnd.nextInt(1, 4);
        Integer arr[] = new Integer[randomNum];
        for (int i = 0; i < randomNum; i++) {
            arr[i] = removeList.remove(removeList.size() - 1);
        }
        for (DataStructure<Integer> struct : structs) {
            remove(struct, arr);
        }
    }

    private static void insertMixed(DataStructure<Integer>... structs) {
        int rand = rnd.nextInt(1, 6);
        Integer arr[] = new Integer[rand];
        for (int i = 0; i < rand; i++) {
            arr[i] = insertList.remove(insertList.size() - 1);
        }

        for (DataStructure<Integer> struct : structs) {
            insert(struct, arr);
        }

    }

    private static void generateData() {
        int size = 2000;
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        List<Integer> unsorted = numbers.subList(0, size/2);

        UNSORTED_NODUPLICATE = unsorted.toArray(new Integer[unsorted.size()]);

        SORTED_NODUPLICATE = Arrays.copyOf(UNSORTED_NODUPLICATE, unsorted.size());
        Arrays.sort(SORTED_NODUPLICATE);

        REVERSE_NODUPLICATE = Arrays.copyOf(UNSORTED_NODUPLICATE, unsorted.size());
        Arrays.sort(REVERSE_NODUPLICATE, Collections.reverseOrder());

        insertList = numbers.subList(size/2, size);
        removeList = new ArrayList<>(Arrays.asList(UNSORTED_NODUPLICATE));
        Collections.shuffle(removeList);

    }

    private static void generateContainsData() {

        int size = 100;

        List<Integer> shuffled= new ArrayList<>(List.of(UNSORTED_NODUPLICATE));
        Collections.shuffle(shuffled);
        shuffled = shuffled.subList(0, size/2);

        SAMPLE_NO_DUPLICATES = shuffled.toArray(new Integer[shuffled.size()]);

        List<Integer> duplicates = shuffled.subList(0, shuffled.size()/2);
        duplicates.addAll(duplicates);
        Collections.shuffle(duplicates);

        SAMPLE_DUPLICATES = duplicates.toArray(new Integer[duplicates.size()]);
    }

    private static String createTable(String header, SplayTree<Integer> splay, RedBlackTree<Integer> rbt,
            Treap<Integer> treap) {
        StringBuilder output = new StringBuilder();

        output.append("\\begin{table}[]");
        output.append("\\begin{tabular}{||c || c c c||}");
        output.append(String.format("\\multicolumn{4}{|c|}{%s}\\\\", header));
        output.append("\\hline\\hline");
        output.append("& TDST & RBT & TREAP");
        output.append("\\hline");

        // RESULT

        output.append("\\hline");
        output.append("\\end{tabular}");
        output.append("\\end{table}");

        return output.toString();
    }

}
