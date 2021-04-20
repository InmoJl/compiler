package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

public abstract class Factor extends ASTNode {

    public Factor(Token token) {
        super();

        this.lexeme = token;
        this.label = token.getValue();
    }

    public Factor(ASTNode _parent, PeekTokenIterator it) {

        super(_parent);

        var token = it.next();
        var type = token.getType();

        if (type == TokenType.VARIABLE) {
            this.type = ASTNodeTypes.VARIABLE;
        } else {
            this.type = ASTNodeTypes.SCALAR;
        }

        this.label = token.getValue();
        this.lexeme = token;

    }

    public static ASTNode parse(PeekTokenIterator it) {

        var token = it.peek();
        var type = token.getType();

        // 变量
        if (type == TokenType.VARIABLE) {
            it.next();
            return new Variable(token);
        }
        // 标量
        else if (token.isScalar()) {
            it.next();
            return new Scalar(token);
        }

        return null;
    }

}
