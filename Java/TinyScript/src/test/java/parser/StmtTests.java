package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.AssignStmt;
import parser.ast.DeclareStmt;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

public class StmtTests {

    @Test
    public void declare() throws LexicalException, ParseException {

        var it = createTokenIt("var i = 100 * 2");
        var stmt = DeclareStmt.parse(null, it);

        assertEquals(ParserUtils.toPostfixExpression(stmt), "i 100 2 * =");
    }

    @Test
    public void assign() throws LexicalException, ParseException {

        var it = createTokenIt("i = 100 * 2");
        var stmt = AssignStmt.parse(null, it);

        assertEquals(ParserUtils.toPostfixExpression(stmt), "i 100 2 * =");
    }

    private PeekTokenIterator createTokenIt(String src) throws LexicalException {

        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(c -> (char)c));

        return new PeekTokenIterator(tokens.stream());
    }

}
