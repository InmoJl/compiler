package parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleParserTests {
    @Test
    public void test() throws LexicalException, ParseException {

        var source = "1+2+3+4".chars().mapToObj(c -> (char)c);
        var lexer = new Lexer();
        var it = new PeekTokenIterator(lexer.analyse(source).stream());
        var expr = SimpleParser.parser(it);

        assertEquals(2, expr.getChildren().size());
        var v1 = (Scalar)expr.getChild(0);
        assertEquals("1", v1.getLexeme().getValue());
        assertEquals("+", expr.getLexeme().getValue());

        var e2 = (Expr)expr.getChild(1);
        var v2 = (Scalar)e2.getChild(0);
        assertEquals("2", v2.getLexeme().getValue());
        assertEquals("+", e2.getLexeme().getValue());

    }
}
