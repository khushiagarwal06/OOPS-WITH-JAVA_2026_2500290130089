import java.util.*;
public class map {
    public static void main(String[] args) {
        Map<Integer, Integer> hm = new HashMap<>();
        hm.put(10, 96);
        hm.put(11, 100);
        hm.put(12, 89);
        hm.put(13, 99);
        hm.put(14, 90);

        for (Map.Entry<Integer, Integer> i : hm.entrySet()) {
            System.out.println(i.getKey() + ": " + i.getValue());
        }
        hm.put(10 , 89 );
        hm.remove(12);
    }
}
