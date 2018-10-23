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

        BTree tree = new BTree(D);

        //fileGeneration();

        parser(tree);

        //Test Code
        Node searched;
        searched = tree.search(7557243122213114113L);
        searched.displayData(searched.index);

    }

    public static void fileGeneration( ) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(WINDOWS));

        for(int i = 0; i < 10; i++) {
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
        String filenameCreator = Integer.toString(getRandomNumber(1,8)); // Patient Number

        for(int i = 0; i < 6; i++) {
            filenameCreator = filenameCreator + getRandomNumber(0, 9); // Patient Number
        }

        fm = getRandomNumber(0,1);
        filenameCreator = filenameCreator + fm;
        if(fm == 0) {
            filenameCreator = filenameCreator + getRandomNumber(1,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,2);
        }

        fd = getRandomNumber(0,2);
        filenameCreator = filenameCreator + fd;
        if(fd == 0) {
            filenameCreator = filenameCreator + getRandomNumber(1,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        fy = getRandomNumber(0,9);
        filenameCreator = filenameCreator + fy;
        if(fy == 0) {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        fh = getRandomNumber(0,2);
        filenameCreator = filenameCreator + fh;
        if(fh == 2) {
            filenameCreator = filenameCreator + getRandomNumber(0,3);
        } else {
            filenameCreator = filenameCreator + getRandomNumber(0,9);
        }

        fmi = getRandomNumber(0,5);
        filenameCreator = filenameCreator + fmi;
        filenameCreator = filenameCreator + getRandomNumber(0,9);

        fs = getRandomNumber(0,5);
        filenameCreator = filenameCreator + fs;
        filenameCreator = filenameCreator + getRandomNumber(0,9);


        filenameCreator = filenameCreator + ".";

        String baseFileName = filenameCreator;

        for(int i = 0; i < getRandomNumber(2,99); i++) {
            for(int j = 0; j < 7; j++) {
                filenameCreator = filenameCreator + getRandomNumber(0,9);
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

        //TODO: Write File Name Parser
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

        if (tree.search(Long.parseLong(output[0])) == null) {
            tree.insertNode(Long.parseLong(output[0]));
        } else {
            input = tree.search(Long.parseLong(output[0]));
            input.addData(Integer.parseInt(output[1]), input.index);
        }
    }
}
