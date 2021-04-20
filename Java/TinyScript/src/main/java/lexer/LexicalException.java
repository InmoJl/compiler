package lexer;

public class LexicalException extends Exception {

    private String msg;

    public LexicalException(char c) {
        this.msg = String.format("Unexpected character %c", c);
    }

    public LexicalException(String _msg) {
        this.msg = _msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
