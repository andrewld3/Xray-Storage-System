public class Node {

    //TODO: Write Node Structure
    int numKeys;                            //Num keys currently stored in node
    int keys[];                             //Pointer to each key inside node
    Node children[];                         //Pointer to each child node: 0 < first key, 1 > first key, < 2nd key, etc...
    boolean leaf;                           //True- leaf node, False, inside node
    int t;                                  //Minimum Degree

    //TODO: Write Node Operations
    //Constructor
    //Parameters: d is degree, l is leaf boolean value
    public Node (int d, boolean l) {
        t = d;  //Sets min num nodes
        leaf = l; //Sets leaf value

        keys = new int[2*t-1];      //Instantiates key array with size 2 * degrees - 1
        children = new Node[2*t];   //Instantiates children nodes with size 2 * degrees

        numKeys = 0;                //Sets current number of keys as 0
    }

    public Node search(int k) {
        int i = 0;  // This is used to find the index of the array needed for search

        while(i < numKeys && k > keys[i]){ //Looks for the key that is greater than or equal to k
            i++;
        }

        if(keys[i] == k) {      //If the key is found, returns the node
            return this;
        }

        if(leaf == true) {      //If the key is not found and search hits a leaf, returns null. MUST CHECK FOR NULL!!
            return null;
        }

        return children[i].search(k);   //Recursively searches through the tree until the conditions above are met
                                        //then returns either null or the Node all the way back up to the calling function.
    }

    public void insert(int k) {
        int i = numKeys - 1;    // Starts the index at the right most key

        if(leaf == true) {

            while(i >= 0 && keys[i] > k) {  // Checks to make sure index doesn't go out of bounds and new key is less than current
                keys[i+1] = keys[i];        // Shifts key over until index spot is open
                i--;
            }

            keys[i+1] = k;    // Because of decrement at left end (-1), the insertion needs to be at index + 1 spot.
            numKeys = numKeys +1;   // Increases the keys
        } else {    // When node is not a leaf

            while(i >= 0 && keys[i] > k) { // Does the same check as above to find the index need for child
                i--;
            }

            if(children[i+1].numKeys == 2*t-1) { // Checks if the right child is full

                split(i+1, children[i+1]); // Splits if full

                if(keys[i+1] < k) { // Because of the split, there is a possibility of larger key, we check for if the insertion index is still smaller
                    i++;
                }
            }

            children[i+1].insert(k); // Finally, we get to insert at the correct child node.
        }
    }

    private void split(int i, Node right) {
        Node temp = new Node(right.t, right.leaf); // Copies the right child into what eventually will be the left child
        temp.numKeys = t - 1; //Because we are removing at least one node and placing it here.

        for(int j = 0; j < numKeys; j++) { 
            right.keys[j] = right.keys[j+t];
        }

        if(right.leaf == false) {
            for(int j = 0; j < t; j++) {
                right.children[j] = right.children[j+t];
            }
        }

        right.numKeys = t - 1;

        for(int j = numKeys - 1; j >= i; j--) {
            keys[j+1] = keys[j];
        }

        keys[i] = right.keys[t-1];

        numKeys = numKeys + 1;
    }
}
