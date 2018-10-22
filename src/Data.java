public class Data {

    int[] codes;
    int numFiles;

    public Data(int n) {
        codes = new int[n];
    }

    public void addCode(int i, int c) {
        codes[i] = c;
        numFiles++;
    }

    public void displayCode( ) {
        for(int i = 0; i < numFiles; i++) {
            System.out.println();
        }
    }
}
