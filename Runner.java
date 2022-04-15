import java.util.*;

public class Runner {

    // Declare global variables
    private static Scanner scan = new Scanner(System.in);
    private static int mode = -1;

    public static void main(String[] args) {
        Tree tree;
        boolean running = true;
        String [] operations = {"insert", "search", "delete"};

        // Makes new trees until user inputs to terminate loop/exit program
        while (running){
            Runner run = new Runner();
            tree = new Tree();
            boolean operating = true;

            // Receive N nodes and prints randomly created tree
            System.out.print("Enter # of Nodes: ");
            int input = scan.nextInt();
            tree.root = tree.createTree(input);
            System.out.println("Tree: " + tree.printTree(tree.root, "RT"));

            // Continues operations on tree until user input terminates loop
            while(operating){

                // Receive operation and receive k
                System.out.println("Enter operation: \n\t(1) insert k \n\t(2) search k \n\t(3) delete k");
                run.getValidMode();
                System.out.println("What key would you like to "+ operations[mode] +"?: ");
                int k = scan.nextInt();

                // Execute desired operation and print resulting tree
                switch(mode){
                    case 0: 
                        tree.root = tree.insert(tree.root, k);
                        break;
                    case 1: 
                        tree.root = tree.search(tree.root, k);
                        break;
                    case 2: 
                        tree.root = tree.delete(tree.root, k);
                        break;     
                }
                System.out.println("Tree: " + tree.printTree(tree.root, "RT"));

                // Asks if program should continue operations on tree
                System.out.print("Continue? (y/n): ");
                String yn = scan.next();
                if(!yn.equals("y") && !yn.equals("Y")){
                    operating = false;
                }
            }

            // Asks if program should continue with new tree
            System.out.println("Would you like to start a new tree? (y/n): ");
            String yn = scan.next();
            if(!yn.equals("y") && !yn.equals("Y")){
                running = false;
            }
        }
    }

    // Receives a valid input, which must be an integer 1, 2, or 3, 
    // otherwise program asks user to try again
    void getValidMode(){
        boolean valid = false;

        while(!valid){
            try{
                mode = scan.nextInt();
                if(mode > 0 && mode < 4){
                    valid = true;
                }
                else{
                    System.out.println("Invalid input! \nPlease choose an option 1 through 3: ");
                }
            } catch(Exception e){
                // catch letter inputs with error message
                System.out.println("Invalid input! \nPlease choose an option 1 through 3: ");
            }

        }
        // modes in operations array are indexed one less than input
        mode--;
    }

    
}
    

