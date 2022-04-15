import java.util.*;

public class Tree {

    public Node root;

    // Creates new node
    Node newNode (int key){
        Node node = new Node();
        node.key = key;
        node.left = null;
        node.right = null;

        if(root == null){
            root = node;
        }

        return node;
    }

    // Helper function takes in only N
    Node createTree(int numNodes){
        Random rand = new Random();
        Node node = newNode(rand.nextInt(numNodes)+1);
        return createTree(node, numNodes, 1);
    }
    // Recursively generates random tree with nodes 1 through numNodes
    Node createTree(Node node, int numNodes, int count) {
        Tree tree = new Tree();

        // Base case: count reaches numNodes
        if(count >= numNodes){
            root = node;
            return node;
        }

        // Generate random key within range 1 through N
        Random rand = new Random();
        int key = rand.nextInt(numNodes)+1;
        node = tree.search(node, key);
    
        // If random key does not exist in tree, 
        // insert key and increment count
        if(node.key != key){
            node = tree.insert(node, key);
            count++;
        }
        // Otherwise calls itself
        return createTree(node, numNodes, count);
    }

    // Returns true if node does not exist
    boolean isNull(Node node){
        return node == null;
    }
    
    // Finds the minimum key of the tree
    int findMin(Node rootNode){
        if(rootNode == null){
            return -1;
        }

        if(rootNode.left == null){
            return rootNode.key;
        }
        return findMin(rootNode.left);
    }

    // Rotates splay tree right about input node
    Node rotateRight(Node node){
        Node child = node.left;
        node.left = child.right;
        child.right = node;
        return child;
    }

    // Rotates splay tree left about input node
    Node rotateLeft(Node node){
        Node child = node.right;
        node.right = child.left;
        child.left = node;
        return child;
    }

    // Recursively finds and brings key to the root
    Node search(Node node, int key){
        if(node == null || key == node.key){
            return node;
        }
        // Left Branch
        if (key < node.key){
            if(isNull(node.left)) return node;

            // Left-Left
            if(key < node.left.key){
                node.left.left   = search(node.left.left, key);
                node             = rotateRight(node);

                if(!isNull(node.left))  
                    node         = rotateRight(node);
                return node;
            }
            
            // Left-Right
            if (key > node.left.key){
                node.left.right  = search(node.left.right, key);

                if(!isNull(node.left.right)) 
                    node.left    = rotateLeft(node.left);
                if(!isNull(node.left)) 
                    node         = rotateRight(node);
                return node;
            }

            // catches any zag only cases
            if(!isNull(node.left) && key == node.left.key) 
                return rotateRight(node);
        }

        // Right Branch
        else /*(key > node.key)*/{
            if(isNull(node.right)) return node;

            // Right-Left
            if(key < node.right.key){
                node.right.left  = search(node.right.left, key);

                if(!isNull(node.right.left))
                    node.right   = rotateRight(node.right);
                if(!isNull(node.right))
                    node         = rotateLeft(node);
                return node;
            }

            // Right-Right
            if (key > node.right.key){
                node.right.right = search(node.right.right, key);
                node             = rotateLeft(node);

                if(!isNull(node.right))
                    node         = rotateLeft(node);
                return node;
            }

            // catches any zig only cases
            if(!isNull(node.right) && key == node.right.key) 
                return rotateLeft(node);
        }
        
        root = node;
        return node;


    }
    
    // Inserts new key and brings node to root
    Node insert(Node rootNode, int key){
        Node node = newNode(key);

        if (rootNode == null){ 
            return node;
        }

        // bring key to root
        rootNode = search(rootNode, key);
    
        if (rootNode.key == key){ 
            return rootNode;
        }
     
        // connect root and child to new node
        else if (rootNode.key > key){
            node.right = rootNode;
            node.left = rootNode.left;
            rootNode.left = null;
        }
        else{
            node.left = rootNode;
            node.right = rootNode.right;
            rootNode.right = null;
        }
        
        root = node;
        return node; 
    }

    // Deletes key 
    Node delete(Node rootNode, int key){
        Tree tree = new Tree();

        if(rootNode == null){
            return rootNode;
        }

        // brings k to root
        rootNode = search(rootNode, key);
        if(rootNode.key != key){
            return rootNode;
        }

        // Save and disconnect left branch
        Node leftChild = rootNode.left;
        rootNode.left = null;

        // Find min and bring to root
        int min = findMin(rootNode.right);
        rootNode.right = search(rootNode.right, min);

        // Disconnect k and reconnect left branch
        rootNode = rootNode.right;
        rootNode.left = leftChild;

        root = rootNode;
        return rootNode;
    }

    // Recursively prints preorder traversal of tree
    String outputString = "";
    String printTree(Node rootNode, String label){

        // Base case: node is null
        if(rootNode == null){
            return outputString;
        }
        // returns root followed by left branch, followed by right right branch
        return outputString + rootNode.key + label +" " + printTree(rootNode.left, "L") 
            + printTree(rootNode.right, "R");
    }
}

