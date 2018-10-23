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

        BTree tree = new BTree(D);  // Instantiates the a new B-Tree with minimum degrees

        //fileGeneration(1); // Generates a file with an argument of the number of files to be generated.

        parser(tree); // Parses the file and then loads the tree.

        //Test Code
        Node searched;
        searched = tree.search(6986961120559204343L);
        System.out.print(searched.index + " ");
        System.out.println(searched.keys[searched.index]);
        searched.displayData(searched.index);
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

        Node input;

        if (tree.search(Long.parseLong(output[0])) == null) { // If the search is null, adds the file name to node.
            tree.insertNode(Long.parseLong(output[0]));
            input = tree.search(Long.parseLong(output[0]));
            input.addData(Integer.parseInt(output[1]), input.index);

        } else {
            input = tree.search(Long.parseLong(output[0])); // If the search returns a node, adds the .ccccccc to the data node
            input.addData(Integer.parseInt(output[1]), input.index);
        }
    }
}
