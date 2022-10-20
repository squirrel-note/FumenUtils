package exceptions;

public class ParseException extends Exception {
    public enum Parameter {
        FUMEN("-t or -tp required."),
        SEQ("-p or -pp required."),
        ;

        private final String str;

        Parameter(String str) {
            this.str = str;
        }
    }

    public ParseException() {
        super();
    }

    public ParseException(String s) {
        super(s);
    }

    public ParseException(Parameter o) {
        super(o.str);
    }
}
