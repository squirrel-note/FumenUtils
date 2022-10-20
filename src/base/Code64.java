package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code64 {
    private static final String BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static int decode(String input) {
        int output = 0;

        for (int i = 0; i < input.length(); i++) output += Math.pow(64, i) * BASE64.indexOf(input.charAt(i));

        return output;
    }

    public static int decode(char input) {
        return BASE64.indexOf(input);
    }

    public static String encode(int input, int length) {
        String output = "";

        for (int i = 0; i < length; i++) {
            output += BASE64.charAt(input % 64);
            input = Math.floorDiv(input, 64);
        }

        return output;
    }

    public static String encode(int input) {
        return (input == 0) ? "A" : encode(input, (int)(Math.ceil(Math.log(input + 1) / Math.log(64))));
    }


    public static String sum(List<String> input) {
        String output = "";
        int i = 0;
        int m = 0;
        while (true) {
            int sum = m;
            for (String str : input) if (i < str.length()) sum += decode(str.charAt(i));
            if (sum == 0) break;
            m = Math.floorDiv(sum, 64);
            output += encode(sum, 1);
            i++;
        }
        return output;
    }

    public static String product(List<String> input) {
        for (String str : input) if (str.equals("A")) return "A";

        String output = input.get(0);
        for (String str : input.subList(1, input.size())) {
            List<List<Integer>> mul = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                List<Integer> mul_ = new ArrayList<>();
                int m = 0;
                for (int j = 0; j < i; j++) mul_.add(0);
                for (char c : output.toCharArray()) {
                    int e = decode(str.charAt(i)) * decode(c) + m;
                    m = Math.floorDiv(e, 64);
                    mul_.add(e % 64);
                }
                while (m != 0) {
                    mul_.add(m % 64);
                    m = Math.floorDiv(m, 64);
                }
                mul.add(mul_);
            }

            List<Integer> sum = new ArrayList<>();
            int sum_ = 0;
            for (int i = 0; i < mul.get(mul.size() - 1).size(); i++) {
                int m = 0;
                for (int j = 0; j < mul.size(); j++) if (i < mul.get(j).size()) {
                    sum_ = mul.get(j).get(i) + sum_;
                    m += Math.floorDiv(sum_, 64);
                    sum_ = sum_ % 64;
                }
                sum.add(sum_);
                sum_ = m;
            }
            while (sum_ != 0) {
                sum.add(sum_ % 64);
                sum_ = Math.floorDiv(sum_, 64);
            }

            output = "";
            for (int deg : sum) output += encode(deg, 1);
        }
        return output;
    }
    
    public static String multiply(String a, String b) {
        return product(Arrays.asList(a, b));
    }
}
