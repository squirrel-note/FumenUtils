package base.v115;

import java.util.ArrayList;
import java.util.List;

import base.Code64;
import exceptions.FumenException;

public class PageFumen {
    private String v115 = "";

    private List<Integer> table = new ArrayList<>();

    public PageFumen(String v115) {
        this.v115 = v115;
        table.clear();

        String t = v115.replace("?", "");
        int i = t.indexOf("@") + 1;

        int fill = 0;
        while (table.size() < 240) {
            fill = Code64.decode(t.substring(i, i += 2));
            int cnt = fill % 240 + 1;
            int col = Math.floorDiv(fill, 240) - 8;
            for (int j = 0; j < cnt && table.size() < 240; j++) table.add(col);
        }
    }

    public PageFumen(String table, int pageIndex) throws Exception {
        this((new Fumen(table)).getv115(pageIndex));
    }

    public PageFumen(List<Integer> table) throws Exception {
        if (table.size() != 240) throw new FumenException("Invailed table size.");

        this.table = table;
        encode();
    }

    public void encode() {
        v115 = "v115@";

        int col = 0;
        int cnt = 0;
        for (int a : table) {
            if (col == a) cnt ++;
            else {
                v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

                col = a;
                cnt = 1;
            }
        }
        v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

        if (240 * (col + 8) + cnt == 2160) v115 += "A";

        v115 += "AgH";

        if (v115.length() > 47) {
            StringBuilder sb = new StringBuilder(v115);
            for (int i = 0; i < Math.floorDiv(v115.length() - 1, 47); i++) sb.insert(47 + 48 * i, "?");
            v115 = sb.toString();
        }
    }

    public String getEncode() {
        encode();
        return v115;
    }

    public int getHeight() {
        int index = 0;
        for (int i = 0; i < 230; i++) if (table.get(i) != 0) {
            index = i;
            break;
        }
        int height = 23 - Math.floorDiv(index, 10);
        if (height < 0 || height > 22) height = 0;

        return height;
    }

    public int countClearedLine(int floor, int ceiling) {
        int output = 0;
        int[] cleared = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int h = 0;
        for (int i = 220; i >= 0; i -= 10) {
            if (!table.subList(i, i + 10).contains(0)) {
                cleared[h] ++;
            } else {
                h ++;
            }
        }

        for (int i = floor; i < ceiling; i++) output += cleared[i];

        return output;
    }

    public int countClearLine() {
        int output = 0;
        for (int i = 0; i < 230; i += 10) if (!table.subList(i, i + 10).contains(0)) output ++;
        return output;
    }

    public int countHoles() {
        int holes = 0;
        List<Integer> table_ = getLineClearedTable();
        for (int i = 0; i < 220; i++) if (table_.get(i) != 0 && table_.get(i + 10) == 0) holes ++;
        return holes;
    }

    public PageFumen dry() {
        for (Integer i : table) if (i != 0) i = 8;
        return this;
    }

    public PageFumen getDriedPageFumen() throws Exception {
        return new PageFumen(this.table).dry();
    }

    public String getDriedv115() throws Exception {
        return getDriedPageFumen().getEncode();
    }

    public List<Integer> getLineClearedTable() {
        List<Integer> table_ = new ArrayList<>();
        for (int a : table) table_.add(a);
        for (int i = 0; i < 230; i += 10) if (!table_.subList(i, i + 10).contains(0)) {
            for (int j = 0; j < 10; j++) table_.remove(i);
            for (int j = 0; j < 10; j++) table_.add(0, 0);
        }

        return table_;
    }

    public PageFumen getLineClearedPageFumen() throws Exception {
        return new PageFumen(getLineClearedTable());
    }

    public int getLineClearedHeight() {
        List<Integer> table_ = getLineClearedTable();

        int index = 0;
        for (int i = 0; i < 230; i++) if (table_.get(i) != 0) {
            index = i;
            break;
        }
        int height = 23 - Math.floorDiv(index, 10);
        if (height < 0 || height > 22) height = 0;

        return height;
    }

    public boolean isPCable(int height) {
        int height_ = getHeight();
        if (height > 22 || height < height_) return false;
        else {
            int blanks = 0;
            for (int i = 230 - 10 * height; i < 230; i++) if (table.get(i) == 0) blanks ++;
            if (blanks % 4 != 0) return false;
            else if (height > height_) return true;
            else {
                blanks = 0;
                for (int i = 0; i < 9; i++) {
                    boolean connection = false;
                    for (int j = 0; j < height; j++) if (table.get(220 - 10 * j + i) == 0) {
                        blanks ++;
                        if (table.get(221 - 10 * j + i) == 0) connection = true;
                    }
                    if (!connection && blanks % 4 != 0) return false;
                }
                return true;
            }
        }
    }

    public boolean isFiilable(String color) {
        color = color.toUpperCase();
        List<Integer> y = new ArrayList<>();
        for (char c : color.toCharArray()) y.add("ILOZTJSX".indexOf(c) + 1);
        if (y.contains(-1)) throw new IllegalArgumentException("Invailed fill order, only 'I','L','O','Z','T','J','S','X' are available");

        int fill = 0;
        for (int i = 0; i < 230; i++) if (y.contains(table.get(i))) fill ++;
        if (fill % 4 != 0) return false;
        else {
            fill = 0;
            for (int i = 0; i < 9; i++) {
                boolean connection = false;
                for (int j = 0; j < 230; j += 10) if (y.contains(table.get(i + j))) {
                    fill ++;
                    if (y.contains(table.get(i + j + 1))) connection = true;
                }
                if (!connection && fill % 4 != 0) return false;
            }
            return true;
        }
    }
}
