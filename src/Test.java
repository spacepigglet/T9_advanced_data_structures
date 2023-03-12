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

    private static Integer[] UNSORTED_NODUPLICATE = {7,3,5,9,8,1,4,2,15,11};
    private static Integer[] SORTED_NODUPLICATE = {1,2,3,4,5,7,8,9,11,15};
    private static Integer[] REVERSE_NODUPLICATE = {15,11,9,8,7,5,4,3,2,1};

    // Testdata for contains
    private static Integer[] SAMPLE_DUPLICATES = {8,11,8,11,5,3,5}; // Gonna be good for splay
    private static Integer[] SAMPLE_NO_DUPLICATES = {2,8,3,11,5,4};

    //Test data for mixed insert remove
    private static List<Integer> insertList;
    private static List<Integer> removeList;
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        //generateData();
        //generateContainsData();
        insertList = new ArrayList<>();
        insertList.add(12);
        insertList.add(10);
        insertList.add(20);


        removeList = new ArrayList<>();
        removeList.add(2);
        removeList.add(8);
        removeList.add(3);

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

    }

    private static void allOperationsOnSortedNonSortedSerial() {
        initStructs();
        //INSERT
        insertTester(UNSORTED_NODUPLICATE, rbtUnsorted, treapUnsorted, splayTreeUnsorted);
        insertTester(SORTED_NODUPLICATE, rbtSorted, treapSorted, splayTreeSorted);
        insertTester(REVERSE_NODUPLICATE, rbtReverse, treapReverse, splayTreeReverse );

        System.out.println("\\begin{table}[h!]");
        System.out.println(createTable("Insert stor mängd data i rad", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse));

        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        //CONTAINS
        //test number of rotations caused by contains
        //test contains sorted - interesting for splay tree
        containsTester(SAMPLE_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);


        System.out.println(createTable("Testa Contains med duplicates", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse));
        System.out.println("\\end{table}");
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        //unsorted
        containsTester(SAMPLE_NO_DUPLICATES, rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        System.out.println("\\begin{table}[h!]");
        System.out.println(createTable("Testa Contains utan duplicates", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse));

        //contains tests complete -> reset rotationCounter! it is done in resetSplayTrees()
        //testing contains changed the splay trees - make new!
        resetSplayTrees();
        resetCounter(rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse);

        //REMOVE
        //System.out.println("Test removal on SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsorted, treapUnsorted, splayTreeSorted, treapSorted, splayTreeReverse, treapReverse);


        System.out.println(createTable("Testa Remove", rbtUnsorted, treapUnsorted, splayTreeUnsorted, rbtSorted, treapSorted, splayTreeSorted, rbtReverse, treapReverse, splayTreeReverse));
        System.out.println("\\end{table}");
    }

    private static void resetSplayTrees() {
        splayTreeSorted = new SplayTree<>();
        splayTreeUnsorted = new SplayTree<>();
        splayTreeReverse = new SplayTree<>();
        insert(splayTreeSorted, SORTED_NODUPLICATE);
        insert(splayTreeUnsorted, UNSORTED_NODUPLICATE);
        insert(splayTreeReverse, REVERSE_NODUPLICATE);
        //resetCounter();
        //resetCounter(splayTreeSorted, splayTreeUnsorted, splayTreeReverse);
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

        System.out.println("\\begin{table}[h!]");
        System.out.println(createTable("Blandad isättning och borttag", rbtUnsorted, treapUnsorted, splayTreeUnsorted));
        System.out.println("\\end{table}");

//        System.out.println("treap rotations: " + treapUnsorted.getCounter() + "\nsplay rotations: "
//                + splayTreeUnsorted.getCounter());
//        System.out.println(insertList);

    }

    private static void resetCounter(DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            struct.resetCounter();
        }
    }

    private static void insertTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            //System.out.print(struct.getClass().getName() + ": ");
            insert(struct, data);
        }
    }

    private static void removeTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            remove(struct, data);
        }
    }

    private static void containsTester(Integer[] data, DataStructure<Integer>... structs) {
        for (DataStructure<Integer> struct : structs) {
            for (int i : data) {
                struct.contains(i);
            }
        }
    }

    private static void insert(DataStructure<Integer> struct, Integer[] ints) {
        for (int i : ints) {
            struct.insert(i);
        }
    }

    private static void remove(DataStructure<Integer> struct, Integer[] data) {
        for (int i : data) {
            struct.remove(i);
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

    private static String createTable(String header, DataStructure<Integer>... structs) {
        StringBuilder output = new StringBuilder();

        output.append(String.format("\t\\begin{datatable}[%s]\n\t\t", header));
        //output.append(String.format("\\multicolumn{4}{|c|}{%s}\\\\\n\t\t", header));
        //output.append("\\hline\\hline\n\t\t");
        output.append("& RBT & TREAP & TDST");

        String[] labels = {"Unsorted", "Sorted", "Reverse"};
        int l = 0;
        for (int i = 0; i < structs.length; i++) {
            if (i%3==0) {
                output.append("\\\\ \\hline\n\t\t");
                output.append(labels[l]);
                l++;
            }
            output.append("&");

            DataStructure<Integer> curr = structs[i];
            if (curr.getClass().getName().equals("RedBlackTree") && header.contains("Remove") || curr.getClass().getName().equals("RedBlackTree") && structs.length ==3){
                output.append("-");
            } else {
                output.append(curr.getCounter());
            }
        }
        output.append("\n\t\\end{datatable}");

        return output.toString();
    }


}
