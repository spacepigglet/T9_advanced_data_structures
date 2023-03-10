public class Test {

    public static void main(String[] args) {
        //test insert sorted
    }

    public void insert(Integer... ints){
        RedBlackTree rbt = new RedBlackTree();
        Treap treap = new Treap();
        SplayTree splayTree = new SplayTree();

        for (Integer i : ints) {
            rbt.insert(i);
        }
    }

    
}
