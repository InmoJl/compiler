package parser.ast;

import parser.util.PeekTokenIterator;

public class IfStmt extends Stmt {
    public IfStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.IF_STMT, "if");
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) {

        return null;
    }

    public static ASTNode parseIF(ASTNode parent, PeekTokenIterator it) {

        return null;
    }

    public static ASTNode parseTail(ASTNode parent, PeekTokenIterator it) {

        return null;
    }

}
