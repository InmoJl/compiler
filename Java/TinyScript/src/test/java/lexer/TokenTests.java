package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {

    void assertToken(Token token, String value, TokenType type) {
        assertEquals(type, token.getType());
        assertEquals(value, token.getValue());
    }

    @Test
    public void test_varOrKeyword() {

        var it1 = new PeekIterator<Character>("if abc".chars().mapToObj(c -> (char)c));
        var it2 = new PeekIterator<Character>("true abc".chars().mapToObj(c -> (char)c));
        var token1 = Token.makeVarOrKeyword(it1);
        var token2 = Token.makeVarOrKeyword(it2);

        assertToken(token1, "if", TokenType.KEYWORD);
        assertToken(token2, "true", TokenType.BOOLEAN);

        it1.next();

        var token3 = Token.makeVarOrKeyword(it1);
        assertToken(token3, "abc", TokenType.VARIABLE);

    }

    @Test
    public void test_makeString() throws LexicalException {
        String[] tests = {
                "\"123\"",
                "\'123\'",
        };

        for (String test: tests) {
            var it = new PeekIterator<Character>(test.chars().mapToObj(c -> (char)c));
            var token = Token.makeString(it);
            assertToken(token, test, TokenType.STRING);
        }
    }

    @Test
    public void test_makeOperator() throws LexicalException {
        String[] tests = {
                "+ xxx",
                "++num",
                "/=g",
                "==1",
                "&=46",
                "&1456",
                "||xxx",
                "^=111",
                "%7"
        };

        String[] results = { "+", "++", "/=", "==", "&=", "&", "||", "^=", "%" };

        int i = 0;
        for (String test: tests) {

            var it = new PeekIterator<Character>(test.chars().mapToObj(c -> (char)c));
            var token = Token.makeOp(it);

            assertToken(token, results[i++], TokenType.OPERATOR);
        }
    }

    @Test
    public void test_makeNumber() throws LexicalException {
        String[] tests = {
                "+0 aaa",
                "-0 aaa",
                ".3 aaa",
                ".555 aaa",
                "45.544 aaa",
                "-1100.1513*13"
        };

        for (String test: tests) {

            var it = new PeekIterator<Character>(test.chars().mapToObj(c -> (char)c));
            var token = Token.makeNumber(it);

            var splitValue = test.split("[* ]+");
            assertToken(token, splitValue[0],
                    (test.indexOf('.') != -1) ? TokenType.FLOAT : TokenType.INTEGER);
        }
    }
}
