/*
Andrew Driscoll
CSC 365- B Tree Implementation of X-ray Storage System

Arguments: None

Limitations:
- Tree must be of defined degree set in Global Variable.
- Tree is NOT persistent.
- Tree is NOT generic.
- Search is limited to the format "xxxxxxxmmddyyhhmmss"
- Application is a command-line implementation.
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Main {

    public static final int D = 3;  // This is the minimum degrees per node that we'll use through the B-Tree.
    // This must be changed and rebuilt if there needs to be more keys per node.

    public static final String WINDOWS = "C:\\Users\\Andrew\\Code\\Xray-Storage-System\\xrayfiles.txt";
    public static final String LINUX = "/home/andrew/Code/csc365/Xray-Storage-System/xrayfiles.txt";

    public static void main(String[] args) throws IOException {
        int option = 0;
        Scanner in = new Scanner(System.in);
        BTree tree = new BTree(D);  // Instantiates the a new B-Tree with minimum degrees

        while(option != 5) {
            System.out.println();
            System.out.println("1) Generate and Load File");
            System.out.println("2) Populate B-Tree");
            System.out.println("3) Search for Key");
            System.out.println("4) Display Files");
            System.out.println("5) Exit");
            System.out.print("Enter Command: ");
            option = in.nextInt();
            System.out.println();
            switch(option) {
                case 1:
                    fileGeneration(5000); // Generates a file with an argument of the number of files to be generated.
                    break;
                case 2:
                    parser(tree); // Parses the file and then loads the tree.
                    break;
                case 3:
                    long searchTerm;
                    Node searchedNode;
                    System.out.println("Search for (xxxxxxxMMDDYYHHMMSS): ");
                    searchTerm = in.nextLong();
                    searchedNode = tree.search(searchTerm);
                    if(searchedNode != null && searchedNode.keys[searchedNode.index].getKey() == searchTerm) {
                        System.out.println("These files exist in the tree");
                    } else {
                        System.out.println("These files do not exist in the tree");
                    }
                    break;
                case 4:
                    long search;
                    Node searched;
                    System.out.println("Search for (xxxxxxxMMDDYYHHMMSS): ");
                    search = in.nextLong();
                    searched = tree.search(search);
                    if(searched != null && searched.keys[searched.index].getKey() == search) {
                        searched.displayData(searched.index);
                    } else {
                        System.out.println("These files do not exist in the tree");
                    }
                    break;

                case 5:
                    System.out.println("Entire Tree in Numerical Order");
                    tree.displayTree();
                    break;
                default:
                    System.out.println("Incorrect Command\n");
            }
        }
    }

    public static void fileGeneration(int n) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(WINDOWS));

        for(int i = 0; i < n; i++) {
            fileNameGenerator(writer);
        }

        writer.close();
    }

    private static void fileNameGenerator(BufferedWriter writer) throws IOException {
        int fm;  // First Month
        int fd;  // First Day
        int fy;  // First Year
        int fh;  // First Hour
        int fmi; // First Minute
        int fs;  // First Second

        // Generates Patient Number
        String filenameCreator = Integer.toString(getRandomNumber(1,8));

        for(int i = 0; i < 6; i++) {
            filenameCreator = filenameCreator + getRandomNumber(0, 9);
        }

        // Generates the Month
        fm = getRandomNumber(0,1);
        filenameCreator = filenameCreator + fm;
        if(fm == 0) {
            filenameCreator = filenameCreator + getRandomNumber(1,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,2);
        }

        // Generates the Day
        fd = getRandomNumber(0,2);
        filenameCreator = filenameCreator + fd;
        if(fd == 0) {
            filenameCreator = filenameCreator + getRandomNumber(1,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        // Generates the Year
        fy = getRandomNumber(0,9);
        filenameCreator = filenameCreator + fy;
        if(fy == 0) {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        // Generates the Hour
        fh = getRandomNumber(0,2);
        filenameCreator = filenameCreator + fh;
        if(fh == 2) {
            filenameCreator = filenameCreator + getRandomNumber(0,3);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        // Generates the Minutes
        fmi = getRandomNumber(0,5);
        filenameCreator = filenameCreator + fmi;
        filenameCreator = filenameCreator + getRandomNumber(0,9);

        // Generates the Seconds
        fs = getRandomNumber(0,5);
        filenameCreator = filenameCreator + fs;
        filenameCreator = filenameCreator + getRandomNumber(0,9);

        // Adds the .
        filenameCreator = filenameCreator + ".";

        String baseFileName = filenameCreator;

        // Randomly chooses a number between 2 and 99 which dictates the amount of .ccccccc files per id.
        for(int i = 0; i < getRandomNumber(2,99); i++) {
            // Generates the ccccccc of the file name
            for(int j = 0; j < 7; j++) {
                filenameCreator = filenameCreator + getRandomNumber(1,9);
            }
            writer.write(filenameCreator);
            writer.write("\n");
            filenameCreator = baseFileName;
        }
    }

    private static int getRandomNumber(int min, int max) {

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void parser(BTree tree) throws IOException {

        String fileName;

        Scanner in = new Scanner(new File(WINDOWS));

        while(in.hasNext()) {
            fileName = in.nextLine();
            String[] output = fileName.split("\\.");
            loadTree(tree, output);
        }
    }

    public static void loadTree(BTree tree, String[] output) throws IOException {
        Key inputKey = new Key();
        Node input;
        Data inputData = new Data();

        if (tree.search(Long.parseLong(output[0])) == null) { // If the search is null, adds the file name to node.
            inputKey.addKey(Long.parseLong(output[0]));
            tree.insertNode(inputKey);
            input = tree.search(Long.parseLong(output[0]));
            inputData.addCode(Integer.parseInt(output[1]));
            input.addData(inputData);

        } else {
            input = tree.search(Long.parseLong(output[0]));
            inputData.addCode(Integer.parseInt(output[1]));
            input.addData(inputData);
        }
    }
}
