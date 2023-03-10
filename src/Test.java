public class Test {
    private static final int[] UNSORTED_NODUPLICATES_75 = { 100, 7, 68, 86, 76, 40, 24, 30, 73, 52, 20, 12, 10, 49, 77,
            37, 62,
            83, 2, 89, 63, 56, 94, 91, 21, 9, 59, 4, 71, 93, 85, 6, 66, 36, 75, 53, 35, 67, 61, 1, 5, 84, 98, 54, 17,
            70, 46, 26, 55, 69, 78, 51, 88, 39, 45, 64, 80, 48, 87, 25, 8, 96, 27, 11, 16, 14, 33, 42, 44, 28, 31, 19,
            82, 43, 50 };
    private static final int[] SORTED_NODUPLICATE_75 = { 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 17, 19, 20, 21, 24,
            25, 26,
            27, 28, 30, 31, 33, 35, 36, 37, 39, 40, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 59, 61, 62,
            63, 64, 66, 67, 68, 69, 70, 71, 73, 75, 76, 77, 78, 80, 82, 83, 84, 85, 86, 87, 88, 89, 91, 93, 94, 96, 98,
            100 };
    private static final int[] REVERSE_NODUPLICATE_75 = { 100, 98, 96, 94, 93, 91, 89, 88, 87, 86, 85, 84, 83, 82, 80,
            78, 77,
            76, 75, 73, 71, 70, 69, 68, 67, 66, 64, 63, 62, 61, 59, 56, 55, 54, 53, 52, 51, 50, 49, 48, 46, 45, 44, 43,
            42, 40, 39, 37, 36, 35, 33, 31, 30, 28, 27, 26, 25, 24, 21, 20, 19, 17, 16, 14, 12, 11, 10, 9, 8, 7, 6, 5,
            4, 2, 1 };

    private static final int[] TEST_ALL_WITH_DULICATES_15 = { 45, 86, 45, 40, 83, 86, 61, 93, 46, 86, 1, 20 };
    private static final int[] TEST_ALL_NO_DULICATES_15 = { 52, 66, 45, 40, 83, 8, 26, 93, 46, 86, 1, 20 };

    private static final int[] TEST_SOME_WITH_DULICATES_15 = { 63, 10, 95, 32, 66, 45, 15, 83, 10, 13, 93, 3, 63, 22,
            45 };
    private static final int[] TEST_SOME_NO_DULICATES_15 = { 63, 44, 95, 32, 66, 5, 15, 83, 10, 13, 93, 3, 87, 22, 88 };

    public static void main(String[] args) {
        //test insert sorted
        insert(SORTED_NODUPLICATE_75);
        //test insert unsorted
        insert(UNSORTED_NODUPLICATES_75);
        //test insert unsorted
        insert(REVERSE_NODUPLICATE_75);
    }

    public static void insert(int... ints){
        RedBlackTree rbt = new RedBlackTree();
        Treap treap = new Treap();
        SplayTree splayTree = new SplayTree();

        for (int i : ints) {
            rbt.insert(i);
            treap.insert(i);
            splayTree.insert(i);
        }
    }

    private static void testContains(int... items) {
        // TODO Empty method 'testContains'
    }

}
