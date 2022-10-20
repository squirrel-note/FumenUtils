package base.v115;

import java.util.ArrayList;
import java.util.List;

import base.Code64;
import base.Constants;
import exceptions.FumenException;

public class Page {
    private Fumen parentFumen;

    private List<Integer> table = new ArrayList<>();
    private List<Integer> diffTable = new ArrayList<>();
    private List<Integer> flagsAppliedTable = new ArrayList<>();

    private int piece = 0;
    private int pieceRotation = 0;
    private int pieceLocation = 0;

    private int rise = 0;
    private int mirror = 0;
    private int color = 0;
    private int commentFlag = 0;
    private int lock = 0;

    private String commentValue = "";


    public Page() {}

    public Page(Fumen x) {
        parentFumen = x;
    }


    public void setDiffTable(List<Integer> x) throws Exception {
        if (x.size() != 240) throw new FumenException("Invailed table size.");

        diffTable = x;
    }

    public void setEmpDiffTable() {
        for (int i = 0; i < 240; i++) diffTable.add(0);
    }

    public void setFlags(String str) throws Exception {
        if (str.length() != 3) throw new FumenException("Invailed string length.");

        int flags = Code64.decode(str);
        piece         =     flags % 8;
        pieceRotation =     Math.floorDiv(flags,      8) % 4;
        pieceLocation =     Math.floorDiv(flags,     32) % 240;
        rise          =     Math.floorDiv(flags,   7680) % 2;
        mirror        =     Math.floorDiv(flags,  15360) % 2;
        color         =     Math.floorDiv(flags,  30720) % 2;
        commentFlag   =     Math.floorDiv(flags,  61440) % 2;
        lock          = 1 - Math.floorDiv(flags, 122880) % 2;
    }

    public void setComment(String str) {
        if (commentFlag == 1) commentValue = str;
    }


    public void buildTable(List<Integer> prev) throws Exception {
        if (prev.size() != 240) throw new FumenException("Invailed table size.");

        for (int i = 0; i < 240; i++) {
            int sum = prev.get(i) + diffTable.get(i);
            if (sum < 0) sum = 0;
            if (sum > 8) sum = 8;
            table.add(sum);
        }

        applyFlags();
    }

    public void applyFlags() {
        flagsAppliedTable.clear();
        for (int i = 0; i < 240; i++) flagsAppliedTable.add(table.get(i));

        if (lock == 1) {
            if (piece > 0 && piece < 8) for (int i = 0; i < 4; i++) flagsAppliedTable.set(pieceLocation + Constants.PIECE_SHAPE[piece - 1][pieceRotation][i], piece);

            for (int i = 0; i < 230; i += 10) if (!flagsAppliedTable.subList(i, i + 10).contains(0)) {
                for (int j = 0; j < 10; j++) flagsAppliedTable.remove(i);
                for (int j = 0; j < 10; j++) flagsAppliedTable.add(0, 0);
            }

            if (rise == 1) {
                for (int i = 0; i < 10; i++) flagsAppliedTable.remove(0);
                for (int i = 0; i < 10; i++) flagsAppliedTable.add(0);
            }

            if (mirror == 1) {
                List<Integer> table_ = new ArrayList<>();
                for (int i = 0; i < 230; i += 10) for (int j = 9; j >= 0; j--) table_.add(flagsAppliedTable.get(i + j));
                for (int i = 230; i < 239; i++) table_.add(flagsAppliedTable.get(i));
                flagsAppliedTable.clear();
                for (int i = 0; i < 240; i++) flagsAppliedTable.add(table_.get(i));
            }
        }
    }

    public void buildDiffTable(List<Integer> prev) {
        diffTable.clear();
        applyFlags();
        for (int i = 0; i < 240; i++) {
            int diff = table.get(i) - prev.get(i);
            if (diff < -8) diff = -8;
            if (diff >  8) diff =  8;
            diffTable.add(diff);
        }
    }


    public List<Integer> getDiffTable() {
        return diffTable;
    }

    public String getFlags() {
        if (lock   != 1) lock   = 0;
        if (color  != 1) color  = 0;
        if (mirror != 1) mirror = 0;
        if (rise   != 1) rise   = 0;

        if (piece         >   7 || piece         < 0) piece = 0;
        if (pieceRotation >   3 || pieceRotation < 0) piece = 0;
        if (pieceLocation > 239 || pieceLocation < 0) piece = 0;
        if (piece == 0) pieceRotation = pieceLocation = 0;

        if (commentValue != null || commentValue.length() != 2 + 5 * Math.floorDiv(Code64.decode(commentValue.substring(0, 2)), 4) || !commentValue.matches("[A-Za-z0-9+/]+")) commentFlag = 0;

        int flags = 0;
        flags += (1 - lock)    * 122880;
        flags += commentFlag   *  61440;
        flags += color         *  30720;
        flags += mirror        *  15360;
        flags += rise          *   7680;
        flags += pieceLocation *     32;
        flags += pieceRotation *      8;
        flags += piece;

        return (commentFlag == 1) ? Code64.encode(flags, 3) + commentValue : Code64.encode(flags, 3);
    }

    public List<Integer> getFlagsAppliedTable() throws Exception {
        if (flagsAppliedTable.isEmpty()) parentFumen.buildTable();
        return flagsAppliedTable;
    }

    public boolean commentExist() {
        return (commentFlag == 1) ? true : false;
    }

    public boolean hasIllegalPiece() throws Exception {
        return new Piece(piece + 8 * pieceRotation + 32 * pieceLocation).isIllegal();
    }


    public String getv115() throws Exception {
        if (table.isEmpty()) parentFumen.buildTable();

        String v115 = "v115@";

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

        if (lock   != 1) lock   = 0;
        if (color  != 1) color  = 0;
        if (mirror != 1) mirror = 0;
        if (rise   != 1) rise   = 0;

        if (piece         >   7 || piece         < 0) piece = 0;
        if (pieceRotation >   3 || pieceRotation < 0) piece = 0;
        if (pieceLocation > 239 || pieceLocation < 0) piece = 0;
        if (piece == 0) pieceRotation = pieceLocation = 0;

        if (commentValue != null || commentValue.length() != 2 + 5 * Math.floorDiv(Code64.decode(commentValue.substring(0, 2)), 4) || !commentValue.matches("[A-Za-z0-9+/]+")) commentFlag = 0;

        int flags = 0;
        flags += (1 - lock)    * 122880;
        flags += commentFlag   *  61440;
        flags += color         *  30720;
        flags += mirror        *  15360;
        flags += rise          *   7680;
        flags += pieceLocation *     32;
        flags += pieceRotation *      8;
        flags += piece;
        v115 += Code64.encode(flags, 3);

        if (commentFlag == 1) v115 += commentValue;

        return v115;
    }

    public int getHeight() throws Exception {
        int index = 0;
        for (int i = 0; i < 230; i++) if (table.get(i) != 0) {
            index = i;
            break;
        }
        int height = 23 - Math.floorDiv(index, 10);
        if (height < 0 || height > 22) height = 0;

        return height;
    }

    public int countClearLine() throws Exception {
        if (table.isEmpty()) parentFumen.buildTable();
        if (lock == 0) return 0;

        int clearLine = 0;
        List<Integer> table_ = new ArrayList<>();
        for (int i = 0; i < 240; i++) table_.add(table.get(i));

        if (piece > 0 && piece < 8) for (int i = 0; i < 4; i++) table_.set(pieceLocation + Constants.PIECE_SHAPE[piece - 1][pieceRotation][i], piece);
        for (int i = 0; i < 230; i += 10) if (table_.subList(i, i + 9).indexOf(0) == -1) clearLine ++;
        return clearLine;
    }

    public int countHoles() throws Exception {
        if (table.isEmpty()) parentFumen.buildTable();

        int holes = 0;
        List<Integer> table_ = new ArrayList<>();
        for (int i = 0; i < 240; i++) table_.add(table.get(i));

        if (piece > 0 && piece < 8) for (int i = 0; i < 4; i++) table_.set(pieceLocation + Constants.PIECE_SHAPE[piece - 1][pieceRotation][i], piece);
        for (int i = 0; i < 230; i += 10) if (!table_.subList(i, i + 10).contains(0)) {
            for (int j = 0; j < 10; j++) table_.remove(i);
            for (int j = 0; j < 10; j++) table_.add(0, 0);
        }

        for (int i = 0; i < 220; i++) if (table_.get(i) != 0 && table_.get(i + 10) == 0) holes ++;
        return holes;
    }

    public void clearComment() {
        commentFlag = 0;
        commentValue = "";
    }

    public void dry() {
        for (int i = 0; i < 240; i++) if (table.get(i) != 0) table.set(i, 8);
    }
}
