package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Program extends Block {

    public Program(ASTNode parent) {
        super(parent);
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {

        var block = new Program(parent);
        ASTNode stmt = null;
        while ((stmt = Stmt.parseStmt(parent, it)) != null) {
            block.addChild(stmt);
        }

        return block;
    }

}
