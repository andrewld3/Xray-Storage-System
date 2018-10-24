import java.util.ArrayList;

public class Key {

    long key;
    ArrayList<Data> codes;

    public Key() {
        codes = new ArrayList<>();
    }

    public void addKey(long k) {
        key = k;
    }

    public long getKey() {
        return key;
    }

    public void addCode(Data d) {
        codes.add(d);
    }

    public void displayCode() {
        int i = 0;
        while(codes.size() > i) {
            Data temp;
            temp = codes.get(i);
            temp.displayCode();
            i++;
        }
    }
}
