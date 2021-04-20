package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Block extends Stmt {
    public Block(ASTNode _parent) {
        super(_parent, ASTNodeTypes.BLOCK, "block");
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        it.nextMatch("{");

        var block = new Block(parent);
        ASTNode stmt = null;
        while ((stmt = Stmt.parseStmt(parent, it)) != null) {
            block.addChild(stmt);
        }

        it.nextMatch("}");

        return block;
    }
}
