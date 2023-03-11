import java.util.*;

public class Test {
    private static Integer[] UNSORTED_NODUPLICATE; // = { 100, 7, 68, 86, 76, 40, 24, 30, 73, 52, 20, 12, 10, 49, 77, 37, 62, 83, 2, 89, 63, 56, 94, 91, 21, 9, 59, 4, 71, 93, 85, 6, 66, 36, 75, 53, 35, 67, 61, 1, 5, 84, 98, 54, 17, 70, 46, 26, 55, 69, 78, 51, 88, 39, 45, 64, 80, 48, 87, 25, 8, 96, 27, 11, 16, 14, 33, 42, 44, 28, 31, 19, 82, 43, 50 };
    private static Integer[] SORTED_NODUPLICATE; // = { 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 17, 19, 20, 21, 24, 25, 26, 27, 28, 30, 31, 33, 35, 36, 37, 39, 40, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 59, 61, 62, 63, 64, 66, 67, 68, 69, 70, 71, 73, 75, 76, 77, 78, 80, 82, 83, 84, 85, 86, 87, 88, 89, 91, 93, 94, 96, 98, 100 };
    private static Integer[] REVERSE_NODUPLICATE; // = { 100, 98, 96, 94, 93, 91, 89, 88, 87, 86, 85, 84, 83, 82, 80, 78, 77, 76, 75, 73, 71, 70, 69, 68, 67, 66, 64, 63, 62, 61, 59, 56, 55, 54, 53, 52, 51, 50, 49, 48, 46, 45, 44, 43, 42, 40, 39, 37, 36, 35, 33, 31, 30, 28, 27, 26, 25, 24, 21, 20, 19, 17, 16, 14, 12, 11, 10, 9, 8, 7, 6, 5, 4, 2, 1 };

    // Testdata for contains
    private static Integer[] SAMPLE_DUPLICATES; //= { 45, 86, 45, 40, 83, 86, 61, 93, 46, 86, 1, 20 }; // Gonna be good for splay
    private static Integer[] SAMPLE_NO_DUPLICATES; //= { 52, 66, 45, 40, 83, 8, 26, 93, 46, 86, 1, 20 };

    //Test data for mixed insert remove
    //private static final Integer[] TO_INSERT = {195, 150, 149, 157, 199, 141, 107, 175, 123, 161, 164, 165, 142, 139, 176, 110, 167, 155, 171, 117, 113, 168, 127, 172, 196, 158, 156, 200, 198, 115, 140, 112, 130, 104, 137, 192, 108, 133, 124, 159, 189, 184, 160, 121, 180, 179, 182, 105, 114, 188, 131, 163, 152, 154, 116, 185, 118, 146, 111, 151, 145, 181, 197, 162, 132, 186, 119, 201, 193, 183, 170, 138, 191, 106, 174 };
    private static List<Integer> insertList ; //= new ArrayList<>(Arrays.asList(TO_INSERT));
   
    private static List<Integer> removeList; // = new ArrayList<>(Arrays.asList(UNSORTED_NODUPLICATE));

    private static final Random rnd = new Random();


    public static void main(String[] args) {
        generateData();
        generateContainsData();
        //allOperationsOnSortedNonSortedSerial();
        insertRemoveMixed();
    }

    private static void allOperationsOnSortedNonSortedSerial(){
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

        //insert tests complete -> reset rotationCounter!
        resetCounter(splayTreeSorted,rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted, splayTreeReverse, rbtReverse, treapReverse);


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
        resetCounter(splayTreeSorted,rbtSorted, treapSorted, splayTreeUnsorted, rbtUnsorted, treapUnsorted, splayTreeReverse, rbtReverse, treapReverse);

        //REMOVE
        //testing contains changed the splay trees - make new!
        SplayTree<Integer> splayTreeSortedTwo= new SplayTree<>();
        SplayTree<Integer> splayTreeUnsortedTwo = new SplayTree<>();
        SplayTree<Integer> splayTreeReverseTwo = new SplayTree<>();

        //sorted
        System.out.println("Test removal on SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeSortedTwo, treapSorted);
        System.out.println("Test removal on UNSORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeUnsortedTwo, treapUnsorted);
        System.out.println("Test removal on REVERSE SORTED");
        removeTester(SAMPLE_NO_DUPLICATES, splayTreeReverseTwo, rbtReverse, treapReverse);
    }

    private static void insertRemoveMixed(){

        Treap<Integer> treapUnsorted = new Treap<>();
        SplayTree<Integer> splayTreeUnsorted = new SplayTree<>();

        //setup
        insert(treapUnsorted, UNSORTED_NODUPLICATE);
        insert(splayTreeUnsorted, UNSORTED_NODUPLICATE);
        resetCounter(treapUnsorted, splayTreeUnsorted);
        //mixed insert and remove
        while(insertList.size() > 5){
            insertMixed(treapUnsorted, splayTreeUnsorted);
            removeMixed(treapUnsorted, splayTreeUnsorted);
        }
        System.out.println("treap rotations: " + treapUnsorted.getRotationCounter() + "\nsplay rotations: " + splayTreeUnsorted.getRotationCounter());
        System.out.println(insertList);

    }

    private static void resetCounter(DataStructure<Integer>... structs){
        for(DataStructure<Integer> struct : structs){
            struct.resetRotationCounter();
        }
    }

    private static int remove(DataStructure<Integer> struct, Integer [] data) {
        for (int i : data) {
            struct.remove(i);
        }
        return struct.getRotationCounter();
    }

    private static void removeTester(Integer[] data, DataStructure<Integer>... structs){
        for(DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(remove(struct, data));
        }
    }


    private static void insertTester(Integer[] data, DataStructure<Integer>... structs){
        for(DataStructure<Integer> struct : structs) {
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
        for(DataStructure<Integer> struct : structs) {
            System.out.print(struct.getClass().getName() + ": ");
            System.out.println(testContains(struct, data));
        }
    }


    private static void removeMixed(DataStructure<Integer> ... structs) {
        int randomNum = rnd.nextInt(1, 4);
        Integer arr[] = new Integer[randomNum];
        for(int i = 0; i < randomNum; i++) {
            arr[i] = removeList.remove(removeList.size()-1);
        }
        for(DataStructure<Integer> struct : structs) {
            remove(struct, arr);
        }
    }



    private static void insertMixed(DataStructure<Integer>... structs){
        int rand = rnd.nextInt(1, 6);
        Integer arr[] = new Integer[rand];
        for (int i = 0; i < rand; i++) {
            arr[i] = insertList.remove(insertList.size()-1);
        }

        for (DataStructure<Integer> struct : structs){
            insert(struct, arr);
        }

    }


    private static void generateData() {
        int size = 2000;


        Integer[] arr = new Integer[size];
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        List<Integer> unsorted = numbers.subList(0, size/2);
        insertList = numbers.subList(size/2, size);
        removeList = new ArrayList<>(Arrays.asList(UNSORTED_NODUPLICATE));
        Collections.shuffle(removeList);

        UNSORTED_NODUPLICATE = unsorted.toArray(new Integer[unsorted.size()]);

        SORTED_NODUPLICATE = Arrays.copyOf(UNSORTED_NODUPLICATE, size);
        Arrays.sort(SORTED_NODUPLICATE);

        REVERSE_NODUPLICATE = Arrays.copyOf(UNSORTED_NODUPLICATE, size);
        Arrays.sort(REVERSE_NODUPLICATE, Collections.reverseOrder());

    }

    private static void generateContainsData() {

        int size = 100;

        List<Integer> shuffled= new ArrayList<>(List.of(UNSORTED_NODUPLICATE));
        Collections.shuffle(shuffled);
        shuffled = shuffled.subList(0, size);

        SAMPLE_NO_DUPLICATES = shuffled.toArray(new Integer[shuffled.size()]);

        List<Integer> duplicates = shuffled.subList(0, shuffled.size()/2);
        duplicates.addAll(duplicates);
        Collections.shuffle(duplicates);

        SAMPLE_DUPLICATES = duplicates.toArray(new Integer[duplicates.size()]);

    }

}
