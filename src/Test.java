import java.util.*;

public class Test {
    private static RedBlackTree<Integer> rbtSorted;
    private static RedBlackTree<Integer> rbtUnsorted;
    private static RedBlackTree<Integer> rbtReverse;
    private static Treap<Integer> treapSorted;

    private static Treap<Integer> treapUnsorted;
    private static Treap<Integer> treapReverse;
    private static SplayTree<Integer> splayTreeSorted;
    private static SplayTree<Integer> splayTreeUnsorted;
    private static SplayTree<Integer> splayTreeReverse;

    private static final List<DataStructure<Integer>> structs = new ArrayList<>();

    private static final List<DataStructure<Integer>> unsortedStructs = new ArrayList<>();
    private static final List<DataStructure<Integer>> sortedStructs = new ArrayList<>();
    private static final List<DataStructure<Integer>> reverseStructs = new ArrayList<>();

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

        for(DataStructure<Integer> ds : structs){
            System.out.println();
        }
        generateData();
        generateContainsData();
        allOperationsOnSortedNonSortedSerial();
        insertRemoveMixed();
    }

    private static void initStructs(){
        rbtSorted = new RedBlackTree<>();
        rbtUnsorted = new RedBlackTree<>();
        rbtReverse = new RedBlackTree<>();
        treapSorted = new Treap<>();
        treapUnsorted = new Treap<>();
        treapReverse = new Treap<>();
        splayTreeSorted = new SplayTree<>();
        splayTreeUnsorted = new SplayTree<>();
        splayTreeReverse = new SplayTree<>();

        sortedStructs.add(rbtSorted);
        unsortedStructs.add(rbtUnsorted);
        reverseStructs.add(rbtReverse);
        sortedStructs.add(treapSorted);
        unsortedStructs.add(treapUnsorted);
        reverseStructs.add(treapReverse);
        sortedStructs.add(splayTreeSorted);
        unsortedStructs.add(splayTreeUnsorted);
        reverseStructs.add(splayTreeReverse);

        structs.add(rbtSorted);
        structs.add(rbtUnsorted);
        structs.add(rbtReverse);
        structs.add(treapSorted);
        structs.add(treapUnsorted);
        structs.add(treapReverse);
        structs.add(splayTreeSorted);
        structs.add(splayTreeUnsorted);
        structs.add(splayTreeReverse);
    }

    private static void allOperationsOnSortedNonSortedSerial() {
        initStructs();
        //INSERT
        //System.out.println("test insert SORTED:");
        insertTester(SORTED_NODUPLICATE, rbtSorted, treapSorted, splayTreeSorted);
        //System.out.println("test insert UNSORTED:");
        insertTester(UNSORTED_NODUPLICATE, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        //System.out.println("test insert REVERSE");

        insertTester(REVERSE_NODUPLICATE, rbtReverse, treapReverse, splayTreeReverse );

        System.out.println(createTable("Insert stor mängd data i rad"));

        //insert tests complete -> reset rotationCounter!
        resetCounter();

        //CONTAINS
        //test number of rotations caused by contains
        //test contains sorted - interesting for splay tree
      //  System.out.println("test number of rotations caused by contains");
       // System.out.println("sorted duplicates");

        containsTester(SAMPLE_DUPLICATES, splayTreeSorted,rbtSorted, treapSorted);
       // System.out.println("sorted NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeSorted,rbtSorted, treapSorted);


        //unsorted
        //System.out.println("unsorted duplicates");
        containsTester(SAMPLE_DUPLICATES, splayTreeUnsorted,rbtUnsorted, treapUnsorted);
        //System.out.println("unsorted NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted,rbtUnsorted, treapUnsorted);

        //reverse
        //System.out.println("reverse duplicates");
        containsTester(SAMPLE_DUPLICATES, splayTreeReverse,rbtReverse, treapReverse);
        //System.out.println("reverse NO duplicates");
        containsTester(SAMPLE_NO_DUPLICATES, splayTreeReverse,rbtReverse, treapReverse);

        System.out.println(createTable("Testa Contains"));
        //contains tests complete -> reset rotationCounter!
        resetCounter();

        //REMOVE
        //testing contains changed the splay trees - make new!
        splayTreeSorted = new SplayTree<>();
        splayTreeUnsorted = new SplayTree<>();
        splayTreeReverse = new SplayTree<>();
        List<DataStructure<Integer>> structToRemove = new ArrayList<>();

        //sorted
        //System.out.println("Test removal on SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeSorted, treapSorted);
        //System.out.println("Test removal on UNSORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted, treapUnsorted);
        //System.out.println("Test removal on REVERSE SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeReverse, treapReverse);

        System.out.println(createTable("Testa Remove"));
    }

    private static void insertRemoveMixed() {

        treapUnsorted = new Treap<>();
        splayTreeUnsorted = new SplayTree<>();

        //setup
        insert(treapUnsorted, UNSORTED_NODUPLICATE);
        insert(splayTreeUnsorted, UNSORTED_NODUPLICATE);
        resetCounter();
        //mixed insert and remove
        while (insertList.size() > 5) {
            insertMixed(treapUnsorted, splayTreeUnsorted);
            removeMixed(treapUnsorted, splayTreeUnsorted);
        }
        System.out.println("treap rotations: " + treapUnsorted.getRotationCounter() + "\nsplay rotations: "
                + splayTreeUnsorted.getRotationCounter());
        System.out.println(insertList);

    }

    private static void resetCounter() {
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
            //System.out.print(struct.getClass().getName() + ": ");
            remove(struct, data);
        }
    }

    private static void insertTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            //System.out.print(struct.getClass().getName() + ": ");
            insert(struct, data);
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
            //System.out.print(struct.getClass().getName() + ": ");
            testContains(struct, data);
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

    private static String createTable(String header) {
        StringBuilder output = new StringBuilder();

        output.append("\\begin{table}[h!]\n");
        output.append("\\begin{tabular}{|c | c c c |}\n\\hline");
        output.append(String.format("\\multicolumn{4}{|c|}{%s}\\\\\n", header));
        output.append("\\hline\\hline\n");
        output.append(" & RBT & TREAP & TDST \\\\ \\hline\n");

        output.append("Sorted ");
        for (DataStructure<Integer> ds : sortedStructs){
            output.append("&").append(ds.getRotationCounter());
        }
        output.append("\\\\ \\hline\n");
        output.append("Unsorted ");
        for (DataStructure<Integer> ds : unsortedStructs){
            output.append("&").append(ds.getRotationCounter());
        }
        output.append("\\\\ \\hline\n");
        output.append("Reverse ");
        for (DataStructure<Integer> ds : reverseStructs){
            output.append("&").append(ds.getRotationCounter());
        }

        // RESULT

        output.append("\\\\ \\hline\n");
        output.append("\\end{tabular}\n");
        output.append("\\end{table}");

        return output.toString();
    }

}
