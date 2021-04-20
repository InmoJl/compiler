package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    public Variable(ASTNode _parent, PeekTokenIterator it) {
        super(_parent, it);
    }

    public Variable(Token token) {
        super(token);

        this.type = ASTNodeTypes.VARIABLE;
    }
}
