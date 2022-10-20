package base;

import java.util.ArrayList;
import java.util.List;

public class Primes {
    public static final List<Integer> PRIMELIST_3726 = getPrimeList(3726);

    public static List<Integer> getPrimeList(int length) {
        List<Integer> output = new ArrayList<>();
        int i = 2;
        while (output.size() < length) {
            boolean div = false;
            for (int a : output) if (i % a == 0) {
                div = true;
                break;
            }
            if (!div) output.add(i);
            i++;
        }
        return output;
    }

    public static int getPrime(int index) {
        return getPrimeList(index).get(index - 1);
    }
}
