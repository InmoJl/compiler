package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class DeclareStmt extends Stmt {

    public DeclareStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.DECLARE, "declare");
    }

    public DeclareStmt() {
        super( ASTNodeTypes.DECLARE, "declare");
    }

    /**
     * 声明语句
     * @param parent 父节点
     * @param it     迭代器
     * @return       parse好的语句
     * @throws ParseException 如果不是标量或者变量
     */
    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {

        // 创建语法声明
        var stmt = new DeclareStmt();
        // 确定下一个 token 是 var
        it.nextMatch("var");

        // parse 会吃掉一个 token，所以得先保留
        var tmpToken = it.peek();
        var factor = Factor.parse(it);

        // 不是变量跟标量
        if (factor == null) {
            throw new ParseException(tmpToken);
        }

        // 左节点
        stmt.addChild(factor);

        var lexeme = it.nextMatch("=");
        var expr = Expr.parse(it);

        // 右节点
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);
        
        return stmt;
    }

}
