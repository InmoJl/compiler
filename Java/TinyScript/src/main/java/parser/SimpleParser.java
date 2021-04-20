package parser;

import parser.ast.ASTNode;
import parser.ast.ASTNodeTypes;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class SimpleParser {

    public static ASTNode parser(PeekTokenIterator it) throws ParseException {

        // Expr -> digit + Expr | digit

        var expr = new Expr(null);
        var scalar = new Scalar(expr, it);

        // base condition
        if (!it.hasNext()) return scalar;

        expr.setLexeme(it.peek());
        it.nextMatch("+");
        expr.setLabel("+");
        expr.addChild(scalar);
        expr.setType(ASTNodeTypes.BINARY_EXPR);
        var rightNode = parser(it);
        expr.addChild(rightNode);

        return expr;
    }

}
