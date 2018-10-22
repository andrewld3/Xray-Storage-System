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

    public static final int D = 2;  // This is the minimum degrees per node that we'll use through the B-Tree.
                                    // This must be changed and rebuilt if there needs to be more keys per node.

    public static final String WINDOWS = "C:\\Users\\Andrew\\Code\\Xray-Storage-System\\xrayfiles.txt";
    public static final String LINUX = " ";

    public static void main(String[] args) throws IOException {
        BTree tree = new BTree(D);
        BufferedWriter writer = new BufferedWriter(new FileWriter(WINDOWS));

        //TODO: Write File Name Generator
        for(int i = 0; i < 10; i++) {
            fileNameGenerator(writer);
        }
        writer.close();

        //TODO: Write File Input
        String fileName;
        Scanner in = new Scanner(new File(WINDOWS));
        while(in.hasNext()) {
            fileName = in.nextLine();
            tree.insertNode(Long.parseLong(fileName));
        }
        //TODO: Write File Name Parser

        //TODO: Write Operations

    }

    public static void fileNameGenerator(BufferedWriter writer) throws IOException {
        Random fileNameGenerator = new Random();
        int filename;
        int fm; // First Month
        int fd;
        int fy;
        String filenameCreator = Integer.toString(getRandomNumber(0,9)); // Patient Number

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

        writer.write(filenameCreator);
        writer.write("\n");
    }

    private static int getRandomNumber(int min, int max) {
        if (min >= max) {

        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void parser( ) throws IOException {

        String fileName;
        Scanner in = new Scanner(new File("C:\\Users\\Andrew\\Code\\Xray-Storage-System\\xrayfiles.txt"));
    }
}
