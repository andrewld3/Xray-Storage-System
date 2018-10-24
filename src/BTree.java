public class BTree {

    Node root;  //First node of the tree
    int t;      //Minimum degrees per node

    //Constructor
    //Parameters: d is the min num of degrees per node
    public BTree(int d) {
        root = null;    //Sets the first node as null to handle first insertion
        t = d;          //Sets the min degrees to t.
    }

    public void insertNode(Key key) {

        if(root == null) {
            root = new Node(t, true); // Makes the root with degrees from constant and makes root a leaf.
            root.keys[0] = key; // Assigns the key to the node.
            root.numKeys = 1; // Sets numKeys to 1.
        } else {  // This is if the root is not null.
            if(root.numKeys == 2*t-1) { // If the root is full.
                Node temp = new Node(t, false); // Because temp is going to be the new root, we are setting it as false
                temp.children[0] = root; // Setting up root to be temp.
                temp.split(0, root); // Splits the full child (prev. root)

                int i = 0;
                if(temp.keys[0].getKey() < key.getKey()) { // This increases the index by one if the key is greater than the lowest key of root.
                    i++;
                }

                temp.children[i].insert(key); // Inserts the key.

                root = temp; // Reassigns the root.
            } else {
                root.insert(key);
            }
        }
    }

    public Node search(long key) {
        if(root == null) { // Checks if the BTree exists and has at least one key
            return null;
        } else {
            return root.search(key); // Recursive function in Node. CHECK FOR NULL!!
        }
    }

    public void displayTree( ) {
        if(root != null) {
            root.displayTree();
        }
    }
}
