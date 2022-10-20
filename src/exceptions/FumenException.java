package exceptions;

public class FumenException extends IllegalArgumentException {
    public FumenException() {
        super();
    }

    public FumenException(String s) {
        super(s);
    }
}
