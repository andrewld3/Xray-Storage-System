public class Node {

    int numKeys;                            //Num keys currently stored in node
    Key keys[];                             //Pointer to each key inside node
    Node children[];                         //Pointer to each child node: 0 < first key, 1 > first key, < 2nd key, etc...
    boolean leaf;                           //True- leaf node, False, inside node
    int t;                                  //Minimum Degree

    int index; // Used for Search

    //Constructor
    //Parameters: d is degree, l is leaf boolean value
    public Node (int d, boolean l) {
        t = d;  //Sets min num nodes
        leaf = l; //Sets leaf value

        keys = new Key[2*t-1];      //Instantiates key array with size 2 * degrees - 1
        children = new Node[2*t];   //Instantiates children nodes with size 2 * degrees

        numKeys = 0;                //Sets current number of keys as 0
    }

    public Node search(long k) {
        int i = 0;  // This is used to find the index of the array needed for search

        while(i < numKeys && k > keys[i].getKey()){ //Looks for the key that is greater than or equal to k
            i++;
        }

        if(i != numKeys && keys[i].getKey() == k) {      //If the key is found, returns the node
            index = i;
            return this;
        }

        if(leaf) {      //If the key is not found and search hits a leaf, returns null. MUST CHECK FOR NULL!!
            return null;
        }

        return children[i].search(k);   //Recursively searches through the tree until the conditions above are met
                                        //then returns either null or the Node all the way back up to the calling function.
    }

    public void insert(Key k) {

        int i = numKeys - 1;    // Starts the index at the right most key

        if(leaf) {

            while(i >= 0 && keys[i].getKey() > k.getKey()) {  // Checks to make sure index doesn't go out of bounds and new key is less than current
                keys[i+1] = keys[i];        // Shifts key over until index spot is open
                i--;
            }

            keys[i+1] = k;    // Because of decrement at left end (-1), the insertion needs to be at index + 1 spot.
            numKeys = numKeys + 1;   // Increases the keys
        } else {    // When node is not a leaf

            while(i >= 0 && keys[i].getKey() > k.getKey()) { // Does the same check as above to find the index need for child
                i--;
            }

            if(children[i+1].numKeys == 2*t-1) { // Checks if the right child is full

                split(i+1, children[i+1]); // Splits if full

                if(keys[i+1].getKey() < k.getKey()) { // Because of the split, there is a possibility of larger key, we check for if the insertion index is still smaller
                    i++;
                }
            }
            children[i+1].insert(k); // Finally, we get to insert at the correct child node.
        }
    }

    public void split(int i, Node left) {

        Node temp = new Node(left.t, left.leaf); // Copies the left child into what eventually will be the left child
        temp.numKeys = t - 1; //Because we are removing at least one node and placing it here.

        for(int j = 0; j < t -1; j++) {      //Shifting all of the keys to the right one
            temp.keys[j] = left.keys[j+t];
        }

        if(!left.leaf) {         //Checks if child is not a leaf
            for(int j = 0; j < t; j++) {
                temp.children[j] = left.children[j+t];     // Moves 2 larger children from original node to new node.
            }
        }

        left.numKeys = t - 1;                  // Resets numChild counter to 1 as there is only one node left in here.

        for(int j = numKeys; j >= i+1; j--) {         // Starts at greatest index, moves down from index by calling function
            children[j+1] = children[j];                // Moves all children nodes one to the right.
        }

        children[i + 1] = temp;        //Assigns the new node as that right child.

        for(int j= numKeys - 1; j >= i; j--) {      // Starts at parent nodes highest key
            keys[j+1] = keys[j];                    // Moves the keys one to the right
        }

        keys[i] = left.keys[t-1];      //This moves the child middle key up to the parent node current index spot.

        numKeys = numKeys + 1;      //This increases the numKeys of the parent node because of the move from the child node.
    }

    public void addData(Data d) {
        keys[index].addCode(d);
    }

    public void displayData(int index) {
        keys[index].displayCode();
    }

    public void displayTree( ) {
        int i = 0;
        for(i = 0; i < numKeys; i++) {
            if(!leaf) {
                children[i].displayTree();
            }
            System.out.println(keys[i].getKey());
        }
        if(!leaf) {
            children[i].displayTree();
        }
    }
}
