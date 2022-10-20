package base.v115;

import base.Code64;
import base.Constants;
import exceptions.FumenException;

public record Piece(int p, int r, int l, int pr, int core) {
    public Piece(int p, int r, int l) throws Exception {
        this(p, r, l, getpr(p, r), getCoreLocation(p, r, l));
    }

    public Piece(int flags) throws Exception {
        this(flags % 8, Math.floorDiv(flags,  8) % 4, Math.floorDiv(flags, 32) % 240);
    }

    public Piece(String str) throws Exception {
        this(Code64.decode(str));
    }

    private static int getpr(int p, int r) throws Exception {
        int pr = 0;

        switch (p) {
            // O
            case 3:
                pr = 0;
                break;

            // I
            case 1:
            if (r % 2 == 0) pr = 1;
            else pr = 2;
                break;

            // Z
            case 4:
                if (r % 2 == 0) pr = 3;
                else pr = 4;
                break;

            // S
            case 7:
            if (r % 2 == 0) pr = 5;
            else pr = 6;
                break;

            // L
            case 2:
                pr = 7 + r;
                break;

            // T
            case 5:
                pr = 11 + r;
                break;

            // J
            case 6:
                pr = 15 + r;
                break;
        
            default: throw new Exception();
        }

        return pr;
    }
    
    private static int getCoreLocation(int p, int r, int l) throws Exception {
        if (isIllegalPiece(p, r, l)) throw new FumenException("Invailed piece.");

        int core = 0;
        switch (p) {
            // I
            case 1:
                if (r % 2 == 0) core = l + 2;
                else core = l + 20;
                break;

            // L,T,J
            case 2:
            case 5:
            case 6:
                switch (r) {
                    case 0:
                    case 1:
                        core = l + 11;
                        break;
                    case 2:
                        core = l + 1;
                        break;
                    case 3:
                        core = l + 10;
                        break;
    
                    default: throw new Exception();
                }
                break;

            // O,Z
            case 3:
            case 4:
                core = l + 11;
                break;

            // S
            case 7:
                if (r % 2 == 0) core = l + 11;
                else core = l + 10;
                break;
        
            default: throw new Exception();
        }

        return core;
    }

    public int getHeight() {
        return 23 - Math.floorDiv(core, 10);
    }

    public String getFlagCode(boolean color) {
        if (color) return Code64.encode(p + 8 * r + 32 * l + 30720, 3);
        return Code64.encode(p + 8 * r + 32 * l, 3);
    }

    public int getPieceIndex(int clearLine) throws Exception {
        int code = 0;
        int l_ = 229 - core + 10 * clearLine;
        int x = l_ % 10;

        if (x >= Constants.PR_WIDTH[pr]) throw new Exception();
        
        switch (x) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                code = pr + 19 * x;
                break;

            case 7:
                if (pr == 0) code = 133;
                else code = pr + 132;
                break;

            case 8:
                code = pr / 2 + 151;
                break;

            case 9:
                code = 161;
                break;
        
            default: throw new Exception();
        }

        return code + 162 * Math.floorDiv(l_, 10);
    }

    public boolean isIllegal() {
        return isIllegalPiece(p, r, l);
    }

    public static boolean isIllegalPiece(int p, int r, int l) {
        if (l > 229) return true;
        int x = l % 10;
        for (int i : Constants.PIECE_SHAPE[p - 1][r]) {
            int j = (x + i + 20) % 10;
            if (j - x > 2 || j - x < -2) return true;
        }
        return false;
    }
}
