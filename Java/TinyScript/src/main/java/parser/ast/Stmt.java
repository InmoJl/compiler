package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

// Stmt (Statement 语句)
public abstract class Stmt extends ASTNode {
    public Stmt(ASTNode _parent, ASTNodeTypes _type, String _label) {
        super(_parent, _type, _label);
    }

    public Stmt(ASTNodeTypes _type, String _label) {
        super(_type, _label);
    }

    public static ASTNode parseStmt(ASTNode parent, PeekTokenIterator it) throws ParseException {

        var token = it.next();
        var lookahead = it.peek();
        it.putBack();

        if (token.isVariable() && lookahead.getValue().equals("=")) {
            return AssignStmt.parse(parent, it);
        } else if (token.getValue().equals("var")) {
            return DeclareStmt.parse(parent, it);
        }

        return null;
    }


}
