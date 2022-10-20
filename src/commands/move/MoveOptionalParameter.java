package commands.move;

import base.v115.PageFumen;
import base.v115.PieceFumen;

public enum MoveOptionalParameter {
    GRAY("gray") {
        @Override
        String process(PageFumen pageFumen, PieceFumen pieceFumen, int height) throws Exception {
            return pageFumen.getDriedv115();
        }
    },
    HEIGHT("height") {
        @Override
        String process(PageFumen pageFumen, PieceFumen pieceFumen, int height) throws Exception {
            return String.valueOf(pageFumen.getHeight());
        }
    },
    PCABLE("pcable") {
        @Override
        String process(PageFumen pageFumen, PieceFumen pieceFumen, int height) throws Exception {
            if (height < 1) return "-";
            if (pageFumen.isPCable(height)) return "O";
            return "X";
        }
    },
    CODE("code") {
        @Override
        String process(PageFumen pageFumen, PieceFumen pieceFumen, int height) throws Exception {
            return "&" + pieceFumen.getCode();
        }
    },
    ;

    final String str;

    MoveOptionalParameter(String str) {
        this.str = str;
    }

    abstract String process(PageFumen pageFumen, PieceFumen pieceFumen, int height) throws Exception;
}
