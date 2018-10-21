public class BTree {

    Node root;  //First node of the tree
    int t;      //Minimum degrees per node

    //Constuctor
    //Parameters: d is the min num of degrees per node
    public BTree(int d) {
        root = null;    //Sets the first node as null to handle first insertion
        t = d;          //Sets the min degrees to t.
    }

    //TODO: Write Add Node
    //TODO: Write Search
    //TODO: Write Iteration
}
