package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Scalar extends Factor {
    public Scalar(ASTNode _parent, PeekTokenIterator it) {
        super(_parent, it);
    }

    public Scalar(Token token) {
        super(token);

        this.type = ASTNodeTypes.SCALAR;
    }
}
