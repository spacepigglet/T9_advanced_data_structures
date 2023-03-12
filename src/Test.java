import java.util.*;

/*
* Sara Nordström - sano8531
* Jessica Borg - jebo0161
* Amanda Sjöberg - amsj1785
*
* Vi använder @SafeVarargs på flera ställen. Vi är medvetna om att Varargs inte är bra att använda men nu
* blev det så! Vi ska inte göra om det!
* */

public class Test {
    // Testdata for contains
    private static final Integer[] UNSORTED_CONTAINS_DATA = {3,7,5,11,4,15,1,2,9,22,12,33};
    private static final Integer[] SAMPLE_DUPLICATES = {22,22,22,22,22,22,22,22,22,22,22,22}; // Gonna be good for splay
    private static final Integer[] SAMPLE_NO_DUPLICATES = {2,22,3,11,5,4,7,15,9,1,12,33};

    //Test data for mixed insert remove
    private static final Random rnd = new Random();
    private static final int INSERT_MAX = 6;
    private static final int REMOVE_MAX = 4;

    // Data structures
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

    //Test data for mixed insert remove
    private static List<Integer> insertList;
    private static List<Integer> removeList;
    private static List<String> resultTables;

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

        createResultTable("Insättning av stor mängd data i rad", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        //REMOVE
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted, treapUnsorted, splayTreeSorted, treapSorted, splayTreeReverse, treapReverse);

        createResultTable("Test av borttag", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
    }

    private static void resetSplayTrees() {
        splayTreeUnsorted = new SplayTree<>();
        insert(splayTreeUnsorted, UNSORTED_CONTAINS_DATA);
    }

    private static void containsMain() {
        initStructsContain();
        
        //interesting for splay tree
        //No duplicates
        containsTester(SAMPLE_NO_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        createResultTable("Testa Contains utan dubbletter", rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted);

        //Duplicates
        containsTester(SAMPLE_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        createResultTable("Testa Contains med dubbletter", rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted);
    }

    private static void initStructsContain() {
        initStructs();
        insertTester(UNSORTED_CONTAINS_DATA, rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);
    }

    private static void insertRemoveMixedMain() {
        treapUnsorted = new Treap<>();
        splayTreeUnsorted = new SplayTree<>();

        //setup
        insert(treapUnsorted, UNSORTED_NODUPLICATE);
        insert(splayTreeUnsorted, UNSORTED_NODUPLICATE);
        resetCounter(treapUnsorted, splayTreeUnsorted);

        //mixed insert and remove
        while (insertList.size() > INSERT_MAX) {
            insertMixed(treapUnsorted, splayTreeUnsorted);
            removeMixed(treapUnsorted, splayTreeUnsorted);
        }

        createResultTable("Blandad isättning och borttag", rbtUnsorted, treapUnsorted, splayTreeUnsorted);
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
            remove(struct, data);
        }
    }

    @SafeVarargs
    private static void insertTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            insert(struct, data);
        }
    }

    private static void insert(DataStructure<Integer> struct, Integer... ints) {
        for (int i : ints) {
            struct.insert(i);
        }
    }

    @SafeVarargs
    private static void containsTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            for (int i : data) {
                struct.contains(i);
            }
        }
    }

    @SafeVarargs
    private static void removeMixed(DataStructure<Integer>... structs) {
        int randomNum = rnd.nextInt(1, REMOVE_MAX);
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
        int rand = rnd.nextInt(1, INSERT_MAX);
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

        UNSORTED_NODUPLICATE = unsorted.toArray(new Integer[0]);

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
