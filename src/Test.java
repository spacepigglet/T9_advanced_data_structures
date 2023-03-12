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

    //will be auto generated for insert/remove tests
    private static Integer[] UNSORTED_NODUPLICATE;
    private static Integer[] SORTED_NODUPLICATE ;
    private static Integer[] REVERSE_NODUPLICATE ;

    // Testdata for contains
    private static Integer[] UNSORTED_CONTAINS_DATA = {3,7,5,11,4,15,1,2,9,22};
    //sorted = {1,2,3,4,5,7,8,9,11,15};
    // reverse = {15,11,9,8,7,5,4,3,2,1}
    private static Integer[] SAMPLE_DUPLICATES = {22,22,22,22,22,22,22,22,22,22}; // Gonna be good for splay
    private static Integer[] SAMPLE_NO_DUPLICATES = {2,22,3,11,5,4,7,15,9,1};

    //Test data for mixed insert remove
    private static List<Integer> insertList;
    private static List<Integer> removeList;
    private static List<String> resultTables;
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        generateData();


        resultTables = new ArrayList<>();
        insertRemoveMain();
        insertRemoveMixedMain();
        containsMain();
        printTables();
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

    }

    private static void insertRemoveMain() {
        initStructs();
        //INSERT
        insertTester(UNSORTED_NODUPLICATE, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        insertTester(SORTED_NODUPLICATE, rbtSorted, treapSorted, splayTreeSorted);
        insertTester(REVERSE_NODUPLICATE, rbtReverse, treapReverse, splayTreeReverse );

        createResultTable("Insert stor mängd data i rad", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        //REMOVE
        //System.out.println("Test removal on SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted, treapUnsorted, splayTreeSorted, treapSorted, splayTreeReverse, treapReverse);

        createResultTable("Testa borttag", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
    }

    private static void resetSplayTrees() {
        splayTreeUnsorted = new SplayTree<>();
        insert(splayTreeUnsorted, UNSORTED_CONTAINS_DATA);
        //resetCounter();
        //resetCounter(splayTreeSorted, splayTreeUnsorted, splayTreeReverse);
    }


    private static void containsMain() {
        initStructsContain();
        //CONTAINS
        //interesting for splay tree
        //No duplicates
        containsTester(SAMPLE_NO_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        createResultTable("Testa Contains utan duplicates", rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted);

        //Duplicates
        containsTester(SAMPLE_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        createResultTable("Testa Contains med duplicates", rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted);

    }

    private static void initStructsContain() {
        rbtSorted = new RedBlackTree<>();
        rbtUnsorted = new RedBlackTree<>();
        rbtReverse = new RedBlackTree<>();
        treapSorted = new Treap<>();
        treapUnsorted = new Treap<>();
        treapReverse = new Treap<>();
        splayTreeSorted = new SplayTree<>();
        splayTreeUnsorted = new SplayTree<>();
        splayTreeReverse = new SplayTree<>();
        insertTester(UNSORTED_CONTAINS_DATA, rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
    }

    private static void insertRemoveMixedMain() {

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

        createResultTable("Blandad isättning och borttag", rbtUnsorted, treapUnsorted, splayTreeUnsorted);

//        System.out.println("treap rotations: " + treapUnsorted.getCounter() + "\nsplay rotations: "
//                + splayTreeUnsorted.getCounter());
//        System.out.println(insertList);

    }

    @SafeVarargs
    private static void resetCounter(DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            struct.resetCounter();
        }
    }

    private static void remove(DataStructure<Integer> struct, Integer[] data) {
        for (int i : data) {
            struct.remove(i);
        }
    }

    @SafeVarargs
    private static void removeTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            //System.out.print(struct.getClass().getName() + ": ");
            remove(struct, data);
        }
    }

    @SafeVarargs
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
        return struct.getCounter();
    }

    private static int testContains(DataStructure<Integer> struct, Integer... testData) {
        for (int i : testData) {
            struct.contains(i);
        }
        return struct.getCounter();
    }

    @SafeVarargs
    private static void containsTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            //System.out.print(struct.getClass().getName() + ": ");
            testContains(struct, data);
        }
    }

    @SafeVarargs
    private static void removeMixed(DataStructure<Integer>... structs) {
        int randomNum = rnd.nextInt(1, 4);
        Integer[] arr = new Integer[randomNum];
        for (int i = 0; i < randomNum; i++) {
            arr[i] = removeList.remove(removeList.size() - 1);
        }
        for (DataStructure<Integer> struct : structs) {
            remove(struct, arr);
        }
    }

    @SafeVarargs
    private static void insertMixed(DataStructure<Integer>... structs) {
        int rand = rnd.nextInt(1, 6);
        Integer[] arr = new Integer[rand];
        for (int i = 0; i < rand; i++) {
            arr[i] = insertList.remove(insertList.size() - 1);
        }

        for (DataStructure<Integer> struct : structs) {
            insert(struct, arr);
        }

    }

    private static void generateData() {
        int size = 2048;
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


    @SafeVarargs
    private static void createResultTable(String header, DataStructure<Integer>... structs) {
        StringBuilder output = new StringBuilder();

        output.append(String.format("\t\\begin{datatable}[%s]\n\t\t", header));
        output.append("& RBT & TREAP & TDST");

        String[] labels = { "Unsorted", "Sorted", "Reverse" };
        int l = 0;
        for (int i = 0; i < structs.length; i++) {
            if (i % 3 == 0) {
                output.append("\\\\ \\hline\n\t\t");
                output.append(labels[l]);
                l++;
            }
            output.append("&");

            DataStructure<Integer> curr = structs[i];
            if (curr.getClass().getName().equals("RedBlackTree") && header.contains("borttag")) {
                output.append("-");
            } else {
                output.append(curr.getCounter());
            }
        }
        output.append("\n\t\\end{datatable}");

        resultTables.add(output.toString());
    }

    private static void printTables() {
        for (int i = 0; i < resultTables.size(); i++) {
            if (i % 2 == 0) {
                System.out.println("\\begin{table}[h!]");
                System.out.println(resultTables.get(i));
            } else {
                System.out.println(resultTables.get(i));
                System.out.println("\\end{table}");
            }
        }
        if (resultTables.size() % 2 == 1) {
            System.out.println("\\end{table}");
        }
    }
}
